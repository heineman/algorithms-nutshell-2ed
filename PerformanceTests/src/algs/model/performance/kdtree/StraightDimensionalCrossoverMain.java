package algs.model.performance.kdtree;


/**
 * Try to find dimension 'n' cutoff beyond which bruteforce comparison of 
 * all n points is as efficient as kd-tree search. Try also to account the
 * time of constructing the kd tree in the first place!
 */
import java.util.Random;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;

public class StraightDimensionalCrossoverMain {
	// random number generator.
	static Random rGen;

	
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
	
	
	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int d = 2;
		int maxD = 128;
		int n = 32768;    // figure results are based on 32768.
		int numSearches = 128;
		
		// 
		System.out.println("Dim\tKD build\tKD search\tBF search\tDelta");
		for (d=2; d < maxD; d++) {
			long now, done;
			
			// create n random points in d dimensions drawn from [0,1] uniformly
			IMultiPoint[] points = randomPoints (n, d);
		
			// Perform a number of searches drawn from same [0,1] uniformly.
			System.gc();
			IMultiPoint[] searchPoints = randomPoints (numSearches, d);
			
			// This forms the basis for the kd-tree. These are the points p. Note
			// that the KDTree generate method will likely shuffle the points. 
			now = System.currentTimeMillis();
			KDTree tree = KDFactory.generate(points);
			done = System.currentTimeMillis();
			long kdBuilt = done - now;
			
			IMultiPoint[] resultsKD = new IMultiPoint[numSearches];
			IMultiPoint[] resultsBF = new IMultiPoint[numSearches];
			
			int idx = 0;
			System.gc();
			now = System.currentTimeMillis();
			for (IMultiPoint imp : searchPoints) {
				resultsKD[idx++] = tree.nearest(imp);
			}
			done = System.currentTimeMillis();
			long kdSearch = done - now;
			
			idx = 0;
			System.gc();
			now = System.currentTimeMillis();
			BruteForceNearestNeighbor bfnn = new BruteForceNearestNeighbor(points);
			for (IMultiPoint imp : searchPoints) {
				resultsBF[idx++] = bfnn.nearest(imp);
			}
			done = System.currentTimeMillis();
			long bfSearch = done - now;
			
			// compare results?
			int numDiff = 0;
			for (int i = 0; i < searchPoints.length; i++) {
				if (resultsBF[i] != resultsKD[i]) {
					double bf = resultsBF[i].distance(searchPoints[i]);
					double kd = resultsKD[i].distance(searchPoints[i]);
					if (!FloatingPoint.same(bf, kd)) {
						numDiff++;
					}
				}
			}
			
			if (numDiff != 0) {
				System.out.println(d + " has " + numDiff + " differences!");
			}
			
			long kdCost = kdBuilt + kdSearch;
			System.out.println(d + "\t" + kdBuilt + "\t" + kdSearch + "\t" + bfSearch + "\t" + (kdCost - bfSearch));

		}
		
	}
}
