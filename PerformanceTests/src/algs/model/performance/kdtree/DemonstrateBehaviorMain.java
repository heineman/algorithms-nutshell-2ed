package algs.model.performance.kdtree;


/**
 * Try to show O(log n) behavior where n is the number of elements in the
 * kd-tree being searched for nearest neighbor.
 */
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.tests.common.TrialSuite;

public class DemonstrateBehaviorMain {
	// random number generator.
	static Random rGen;

	
	/** 
	 * generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	public static IMultiPoint[] randomPoints (int n, int d, int scale) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < d; j++) {
				sb.append(rGen.nextDouble()*scale);
				if (j < d-1) { sb.append (","); }
			}
			points[i] = new Hyperpoint(sb.toString());
		}
		
		return points;
	}	
	
	
	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 4096;
		int NUM_TRIALS = 10;
		
		int scale = 4000;
		
		for (int d = 2; d <= 5; d++) {
			TrialSuite kdSearch = new TrialSuite();
			for (int n=1048576; n <= 1048576; n*=2) {
			System.out.println("  " + n + "...");
			// for brute force we create here...
			IMultiPoint[] points = randomPoints (n, d, scale);
			for (int t = 1; t <= NUM_TRIALS; t++) {
				long now, done;
				
				// Perform a number of searches drawn from same [0,1] uniformly.
				System.gc();
				IMultiPoint[] searchPoints = randomPoints (numSearches, d, scale);
				
				// make the number of trials.
				BruteForceNearestNeighbor bfnn = new BruteForceNearestNeighbor(points);
				System.gc();
				now = System.currentTimeMillis();
				for (IMultiPoint imp : searchPoints) {
					bfnn.nearest(imp);
				}
				done = System.currentTimeMillis();
				kdSearch.addTrial(n, now, done);
			}
		}
		System.out.println("KD search info:" );
		System.out.println(kdSearch.computeTable());
		}
	}
}
