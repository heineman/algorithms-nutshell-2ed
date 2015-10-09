package algs.blog.multithread.nearestNeighbor.bruteforce;

import algs.model.IMultiPoint;

/**
 * Brute Force implementation of Nearest Neighbor Query, modified to support a number
 * of threads.
 * <p>
 * Many brute force algorithms can immediately be improved by multithreading, since 
 * the large number of problems can be subdivided into independent sets.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MultiThreadedBruteForceNearestNeighbor {
	/** Input points are converted into raw format. */
	final double[][] points;
	
	/** Original points are kept here. */ 
	final IMultiPoint results[];

	/** Set number of helper threads to use. Default is one helper. */
	int numThreads = 1;

	/** Computed results by sub-threads. */
	int indices[];
	
	/**
	 * Store all points to compute nearest neighbor queries.
	 * 
	 * @param points   points forming the input set P.
	 */
	public MultiThreadedBruteForceNearestNeighbor(IMultiPoint[] points) {
		if (points == null || points.length == 0) {
			throw new IllegalArgumentException ("BruteForce requires at least one point.");
		}
		
		// convert all points into raw format.
		int maxd = points[0].dimensionality();
		this.points = new double[points.length][maxd];
		for (int i = 0; i < points.length; i++) {
			IMultiPoint imp = points[i];
			for (int d = 1; d <= maxd; d++) {
				this.points[i][d-1] = imp.getCoordinate(d);
			}
		}
		
		this.results = points;
		
		indices = new int[numThreads];
	}

	/**
	 * Set number of threads to use.
	 * <p>
	 * Do not call this method while nearest() method is being invoked otherwise
	 * unknown behavior may result.
	 * 
	 * @param nt   number of threads to use (must be greater than 0).
	 */
	public void setNumberThreads(int nt) {
		if (nt <= 0) {
			throw new IllegalArgumentException ("Must have >0 helper threads.");
		}
		
		numThreads = nt;
		indices = new int [numThreads];
	}
	
	
	/**
	 * Return the closest point to x within the input set P.
	 * 
	 * @param x   search point.
	 */
	public IMultiPoint nearest (IMultiPoint x) {
		final double[] xraw = x.raw();
		
		// start thread for each
		BruteForceThread[] threads = new BruteForceThread[numThreads];
		int size = points.length/numThreads;
		int offset = 0;
		
		for (int t = 0; t < threads.length - 1; t++) {
			threads[t] = new BruteForceThread(points, xraw, offset, size);
			threads[t].start();
			offset += size;
		}
		
		// remainder computed specially.
		threads [threads.length-1] = new BruteForceThread (points, xraw, offset, points.length - offset);
		threads [threads.length-1].start();
		
		// wait until all done, and compute min along the way
		double minValue = Double.MAX_VALUE;
		int bestIndex = -1;
		for (int t = 0; t < threads.length; t++) {
			try {
				threads [t].join();
			} catch (InterruptedException e) {
				System.err.println ("Multi Thread Brute Force interrupted. Unexpected results may occur.");
			}
			
			if (threads[t].best < minValue) {
				minValue = threads[t].best;
				bestIndex = threads[t].bestIndex;
			}
		}
		
		return results[bestIndex];
	}
	
}
