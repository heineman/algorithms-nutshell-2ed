package algs.blog.multithread.nearestNeighbor.smallhelpers;

import algs.model.IHypercube;
import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;

/**
 * Multi-threaded nearest neighbor implementation.
 * <p>
 * Extends {@link DimensionalNode} so we can do a straight-up comparison.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class SmallProblemsKDNode extends algs.model.kdtree.DimensionalNode {

	/** Cached volume value. */
	private double cachedVolume = -1;

	/** Fix work size below which we use separate thread. */
	static double threshold;

	/** Set number of helper threads to use. Default is one helper. */
	static int numThreads = 1;

	/** Determines if help has been requested. */
	static volatile transient int helpersWorking = 0;

	/** Compute total waiting time. */
	public static long waiting = 0;

	/** 
	 * Mutex for accessing helpRequested. To avoid delaying the primary
	 * computation, this mutex is only accessed within the helper threads.
	 */
	static volatile transient Object helpRequestedMutex = new Object();

	/**
	 * Constructor builds off of superclass.
	 * 
	 * @param dimension
	 * @param pt
	 */
	public SmallProblemsKDNode(int dimension, IMultiPoint pt) {
		super(dimension, pt);
	}

	/**
	 * Set the threshold value below which a separate thread is used
	 * on problem sizes.
	 * 
	 * @param t   new threshold
	 */
	public static void setThreshold (double t) {
		threshold = t;
	}

	/**
	 * Set number of helper threads (may be zero).
	 * <p>
	 * Behavior is unspecified if this method is invoked while the nearest 
	 * method is in progress.
	 * 
	 * @param nht
	 */
	public static void setNumberHelperThreads (int nht) {
		numThreads = nht;
	}

	/**
	 * Return volume of hypercube region associated with node.
	 * <p>
	 * Volume is useful to be able to set threshold values when considering
	 * when to apply threads to sub-problems.
	 * <p>
	 * Compute using lazy evaluation and store for use; also assume points are drawn from
	 * within the unit square, so convert infinities into -1 and +1.
	 */
	public double volume() {
		if (cachedVolume < 0) {
			cachedVolume = 1.0;
			IHypercube cube = this.region;
			int nd = cube.dimensionality(); 

			for (int d = 1; d <= nd; d++) {
				double right = cube.getRight(d);
				double left = cube.getLeft(d);
				if (Double.isInfinite(right)) {
					right = +1; 
				}
				if (Double.isInfinite(left)) {
					left = -1; 
				}
				
				if (left == -1 && right == +1) {
					// do nothing when both are INF
				} else {
					cachedVolume *= (right - left);
				}
			}
		}

		return cachedVolume;
	}

	/**
	 * Single-threaded implementation, taken directly from {@link DimensionalNode}
	 */
	protected IMultiPoint nearestN (double[] rawTarget, double min[]) {
		// Update minimum if we are closer.
		IMultiPoint result = null;

		// double d = target.distance(node.point); O(d) computation
		// if shorter, update minimum
		double d = shorter(rawTarget, min[0]);
		if (d >= 0 && d < min[0]) {
			min[0] = d;
			result = point;
		}

		// determine if we must dive into the subtrees by computing direct 
		// perpendicular distance to the axis along which node separates
		// the plane. If d is smaller than the current smallest distance, 
		// we could "bleed" over the plane so we must check both.
		double dp = Math.abs(coord - rawTarget[dimension-1]);
		IMultiPoint newResult = null;

		int numDblRec = 0; /* stats */
		if (dp < min[0]) {
			// must dive into both. Return closest one.
			if (above != null) {
				numDblRec++; /* stats */
				newResult = ((SmallProblemsKDNode)above).nearestN (rawTarget, min); 
				if (newResult != null) { result = newResult; }
			}

			if (below != null) {
				numDblRec++; /* stats */
				newResult = ((SmallProblemsKDNode)below).nearestN(rawTarget, min);
				if (newResult != null) { result = newResult; }
			}
			if (numDblRec == 2) {          /* stats */
				numDoubleRecursions++;     /* stats */
			} else if (numDblRec == 1) {   /* stats */
				numRecursions++;           /* stats */
			}
		} else {
			// only need to go in one! Determine which one now.
			numRecursions++;               /* stats */
			if (rawTarget[dimension-1] < coord) {
				if (below != null) {
					newResult = ((SmallProblemsKDNode)below).nearestN (rawTarget, min); 
				}
			} else {
				if (above != null) {
					newResult = ((SmallProblemsKDNode)above).nearestN (rawTarget, min); 
				}
			}

			// Use smaller result, if found.
			if (newResult != null) { return newResult; }
		}
		return result;
	}

	/**
	 * Storage for each node for multithreaded. Waste of space in general. Done
	 * to ensure we have thread-safe location to store results. 
	 */
	private IMultiPoint[] newResult = new IMultiPoint[2];
	
	/** Computed new minimum value by thread which processes above sub-problem. */
	private double[] aboveMin = new double[1];

	/** Computed new minimum value by thread which processes below sub-problem. */
	private double[] belowMin = new double[1];

	/**
	 * Add helper threads when problem size is sufficiently small as to 
	 * warrant the extra help.
	 * 
	 * (non-Javadoc)
	 * @see algs.model.kdtree.DimensionalNode#nearest(double[], double[])
	 */
	@Override
	protected IMultiPoint nearest (final double[]rawTarget, final double[]min) {
		// Update minimum if we are closer.
		IMultiPoint result = null;

		// double d = target.distance(node.point); O(d) computation
		// if shorter, update minimum
		double d = shorter(rawTarget, min[0]);
		if (d >= 0 && d < min[0]) {
			min[0] = d;
			result = point;
		}

		// determine if we must dive into the subtrees by computing direct 
		// perpendicular distance to the axis along which node separates
		// the plane. If d is smaller than the current smallest distance, 
		// we could "bleed" over the plane so we must check both.
		double dp = Math.abs(coord - rawTarget[dimension-1]);

		int numDblRec = 0; /* stats */
		Thread aboveThread = null;
		Thread belowThread = null;
		newResult[0] = newResult[1] = null;
		if (dp < min[0]) {
			// must dive into both. Return closest one.
			if (above != null) {
				aboveMin[0] = min[0];
				numDblRec++; /* stats */

				if (helpersWorking == numThreads || volume() >= threshold) {
					newResult[0] = ((SmallProblemsKDNode)above).nearest (rawTarget, aboveMin); 
				} else {
					// complete in separate thread
					synchronized(helpRequestedMutex) {
						helpersWorking++;
					}

					aboveThread = new Thread () {
						public void run () {
							newResult[0] = ((SmallProblemsKDNode)above).nearestN (rawTarget, aboveMin); 

							synchronized(helpRequestedMutex) {
								helpersWorking--;
							}
						}
					};
					aboveThread.start();
				}
			}


			if (below != null) {
				belowMin[0] = min[0];

				numDblRec++; /* stats */

				if (helpersWorking == numThreads || volume() >= threshold) {
					newResult[1] = ((SmallProblemsKDNode)below).nearest (rawTarget, belowMin); 
				} else {
					// complete in separate thread
					synchronized(helpRequestedMutex) {
						helpersWorking++;
					}
					belowThread = new Thread () {
						public void run () {
							newResult[1] = ((SmallProblemsKDNode)below).nearestN (rawTarget, belowMin); 

							synchronized(helpRequestedMutex) {
								helpersWorking--;
							}
						}
					};
					belowThread.start();
				}

			}

			// if multi-threaded, must wait until both threads complete.
			try {
				long now = System.currentTimeMillis();
				if (aboveThread != null) {
					aboveThread.join();
				}
				if (belowThread != null) {
					belowThread.join();
				}
				waiting += System.currentTimeMillis()-now;
			} catch (InterruptedException ie) {
				System.err.println("Thread interrupted. Computation may be incorrect");
				ie.printStackTrace();
			}

			// we have improved above.
			if (newResult[0] != null) {
				min[0] = aboveMin[0];
				result = newResult[0];
			}
			// see if below is an improvement
			if (newResult[1] != null) {
				if (belowMin[0] < min[0]) { 
					min[0] = belowMin[0];
					result = newResult[1];
				}
			}

			if (numDblRec == 2) {          /* stats */
				numDoubleRecursions++;     /* stats */
			} else if (numDblRec == 1) {   /* stats */
				numRecursions++;           /* stats */
			}
		} else {
			// only need to go in one! Determine which one now.
			numRecursions++;               /* stats */
			IMultiPoint oneResult = null;
			if (rawTarget[dimension-1] < coord) {
				if (below != null) {
					oneResult = ((SmallProblemsKDNode)below).nearest (rawTarget, min); 
				}
			} else {
				if (above != null) {
					oneResult = ((SmallProblemsKDNode)above).nearest (rawTarget, min); 
				}
			}

			// Use smaller result, if found.
			if (oneResult != null) { return oneResult; }
		}
		return result;
	}

}
