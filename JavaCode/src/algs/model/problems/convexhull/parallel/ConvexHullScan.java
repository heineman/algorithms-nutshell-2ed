package algs.model.problems.convexhull.parallel;

import algs.model.IPoint;
import algs.model.array.QuickSortExternal;
import algs.model.problems.convexhull.IConvexHull;

/**
 * Computes Convex Hull following Andrew's Algorithm using a multi-threaded
 * implementation.
 * <p>
 * Note how the upper and lower hulls are constructed each in its own thread. 
 * The partial hulls can be joined together once the threads complete their 
 * execution. This sort of "obvious" parallelism occurs quite frequently and
 * can take advantage of dual-core chips that are becoming increasingly popular.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class ConvexHullScan implements IConvexHull {
	
	/** How many helper threads are available. */
	int numThreads;
	
	/** 
	 * Construct with a given number of helper threads to use.
	 * @param numThreads    number of helper threads to use 
	 */
	public ConvexHullScan (int numThreads) {
		this.numThreads = numThreads;
	}
	
	/**
	 * Use Andrew's algorithm to return the computed convex hull for 
	 * the input set of points using threads.
	 * <p>
	 * Points must have at least three points to do anything meaningful. If
	 * it does not, then the sorted array is returned as the "hull".
	 * <p>
	 * This algorithm will still work if duplicate points are found in
	 * the input set of points.
	 *
	 * @param points     a set of (n &ge; 3) two dimensional points.
	 */
	public IPoint[] compute (final IPoint[] points) {
		// sort by x-coordinate (and if ==, by y-coordinate). 
		final int n = points.length;
		
		// sort using as many threads as are available, using R=4 sweet spot.
		QuickSortExternal<IPoint> qs = 
			new QuickSortExternal<IPoint>(points, IPoint.xy_sorter);
		qs.setPivotMethod(qs.lastSelector());
		qs.setNumberHelperThreads(numThreads);
		qs.setThresholdRatio(4);
		
		// trivial cases can return now.
		if (n < 3) { return points; }
	
		// from this point on, we only use two threads.
		final PartialHull upper = new PartialHull(points[0], points[1]);
		final PartialHull lower = new PartialHull(points[n-1], points[n-2]);
		
		Thread up = new Thread() {
			public void run() {
				// Compute upper hull by starting with leftmost two points
				for (int i = 2; i < n; i++) {
					upper.add(points[i]);
					while (upper.hasThree() && upper.areLastThreeNonRight()) {
						upper.removeMiddleOfLastThree();
					}
				}
			}
		};
		
		Thread down = new Thread() {
			public void run() {
				// Compute lower hull by starting with rightmost two points
				for (int i = n-3; i >=0; i--) {
					lower.add(points[i]);
					while (lower.hasThree() && lower.areLastThreeNonRight()) {
						lower.removeMiddleOfLastThree();
					}
				}
			}
		};
		
		// start both threads and wait until both are done.
		up.start();
		down.start();
		try {
			up.join();
			down.join();
		} catch (InterruptedException ie) {
			System.err.println("Multithread convex hull execution interrupted. Unexpected results may ensue.");
		}
		
		// remove duplicate end points when combining. Transcribe the partial
		// hulls into the array return value.
		IPoint[] hull = new IPoint[upper.size()+lower.size()-2];
		int num = upper.transcribe (hull, 0);
		lower.transcribe (hull, num-1, lower.size() - 2);
		
		return hull;
	}
}