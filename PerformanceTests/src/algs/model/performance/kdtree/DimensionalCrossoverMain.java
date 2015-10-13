package algs.model.performance.kdtree;


/**
 * This just validates brute force solution is same to kd-tree solution.
 */
import java.util.Random;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.tests.common.TrialSuite;

public class DimensionalCrossoverMain {
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
		int maxD = 40;
		int n = 4096;   // 65536;
		int numSearches = 256; // 8192;
		
		// create set of points. Note that the search will always ensure that no
		// points are found.
		TrialSuite []kdSearch = new TrialSuite[maxD];
		TrialSuite []kdBuild = new TrialSuite[maxD];
		TrialSuite []bfSearch = new TrialSuite[maxD];
		
		for (d=2; d < maxD; d++) {
			System.out.println(d + " ... ");
			long now, done;
			kdSearch[d] = new TrialSuite();
			kdBuild[d] = new TrialSuite();
			bfSearch[d] = new TrialSuite();
			
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
			kdBuild[d].addTrial(d, now, done);
			
			IMultiPoint[] resultsKD = new IMultiPoint[numSearches];
			IMultiPoint[] resultsBF = new IMultiPoint[numSearches];
			
			int idx = 0;
			System.gc();
			now = System.currentTimeMillis();
			for (IMultiPoint imp : searchPoints) {
				resultsKD[idx++] = tree.nearest(imp);
			}
			done = System.currentTimeMillis();
			kdSearch[d].addTrial(d, now, done);
			
			idx = 0;
			System.gc();
			now = System.currentTimeMillis();
			BruteForceNearestNeighbor bfnn = new BruteForceNearestNeighbor(points);
			for (IMultiPoint imp : searchPoints) {
				resultsBF[idx++] = bfnn.nearest(imp);
			}
			done = System.currentTimeMillis();
			bfSearch[d].addTrial(d, now, done);
			
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
		}
		
		System.out.println("KD search");
		for (d = 2; d < kdSearch.length; d++) {
			System.out.println(kdSearch[d].computeTable());
		}

		System.out.println("KD build");
		for (d = 2; d < kdBuild.length; d++) {
			System.out.println(kdBuild[d].computeTable());
		}

		System.out.println("BF search");
		for (d = 2; d < bfSearch.length; d++) {
			System.out.println(bfSearch[d].computeTable());
		}
	}
}
