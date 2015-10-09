package algs.blog.multithread;

import java.util.Random;

import junit.framework.TestCase;

import algs.model.IMultiPoint;
import algs.blog.multithread.nearestNeighbor.bruteforce.MultiThreadedBruteForceNearestNeighbor;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;


public class TestBruteForce extends TestCase {
	/** random number generator. */
	static Random rGen = new Random();

	/** 
	 * generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. 1
	 */
	public static IMultiPoint[] randomPoints (int n, int d) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < d; j++) {
				sb.append(rGen.nextDouble());
				if (j < d-1) { sb.append (","); }
			}
			points[i] = new Hyperpoint(sb.toString());
		}

		return points;
	}	

		
	public void testThreading() {
		int numSearches = 256;
		
		for (int d = 2; d < 10; d++) {
			System.out.println("dimension " + d + "...");
			// Perform a number of searches drawn from same [0,1] uniformly.
			IMultiPoint[] searchPoints = randomPoints (numSearches, d);
			
			for (int n = 16; n <= 32768; n *= 2) {
			
				// create n random points in d dimensions drawn from [0,1] uniformly
				IMultiPoint[] points = randomPoints (n, d);
				
				BruteForceNearestNeighbor bnn = new BruteForceNearestNeighbor(points);
				
				MultiThreadedBruteForceNearestNeighbor mt_bnn = new MultiThreadedBruteForceNearestNeighbor(points);
				
				IMultiPoint results_n[] = new IMultiPoint[searchPoints.length];
				IMultiPoint results_mt[] = new IMultiPoint[searchPoints.length];
				
				// compute native BF
				for (int t = 0; t < searchPoints.length; t++) { 
					results_n[t] = bnn.nearest(searchPoints[t]);
				}
				
				// compute multi-thread BF
				for (int t = 0; t < searchPoints.length; t++) { 
					results_mt[t] = mt_bnn.nearest(searchPoints[t]);
				}
				
				// assert same
				for (int i = 0; i < results_n.length; i++) {
					assertEquals (results_n[i], results_mt[i]);
				}
			}
		}
	}
}
