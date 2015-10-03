package algs.chapter9.figure23;


/**
 * Try to find dimension 'n' cutoff beyond which bruteforce comparison of 
 * all n points is as efficient as kd-tree search. Try also to account the
 * time of constructing the kd tree in the first place!
 */
import java.text.NumberFormat;
import java.util.Random;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.tests.common.TrialSuite;

public class Main {
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
	
	
    /** This method comes with JDK 1.6; replace here for 1.5 compatibility. */
    public static IMultiPoint[] copyOf(IMultiPoint[] original, int newLength) {
       IMultiPoint[] copy = new IMultiPoint[newLength];
       System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
       return copy;
    }

	public static void main (String []args) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int maxD = 32;
		int numSearches = 128;
		int NUM_TRIALS = 100;   // a bunch of trials to compare....
		
		for (int n = 4096; n <= 131072; n *= 32) {
			System.out.println("Dim\tKD build\tKD search\tBF search");
			for (int d = 2; d < maxD; d++) {
				TrialSuite kd_build_ts = new TrialSuite();
				TrialSuite kd_search_ts = new TrialSuite();
				TrialSuite bf_search_ts = new TrialSuite();
				
				for (int t = 0; t < NUM_TRIALS; t++) {
					long now, done;
					
					// create n random points in d dimensions drawn from [0,1] uniformly. Create copy
					// for kd-tree and brute-force, so there is no bleed between them.
					IMultiPoint[] points = randomPoints (n, d);
					
					IMultiPoint[] points1 = copyOf(points, points.length);
					IMultiPoint[] points2 = copyOf(points, points.length);
	
					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();
					IMultiPoint[] searchPoints = randomPoints (numSearches, d);
					
					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					now = System.currentTimeMillis();
					KDTree tree = KDFactory.generate(points1);
					done = System.currentTimeMillis();
					kd_build_ts.addTrial(n, now, done);
					
					IMultiPoint[] resultsKD = new IMultiPoint[numSearches];
					IMultiPoint[] resultsBF = new IMultiPoint[numSearches];
					
					int idx = 0;
					System.gc();
					now = System.currentTimeMillis();
					for (IMultiPoint imp : searchPoints) {
						resultsKD[idx++] = tree.nearest(imp);
					}
					done = System.currentTimeMillis();
					kd_search_ts.addTrial(n, now, done);
					
					idx = 0;
					System.gc();
					now = System.currentTimeMillis();
					BruteForceNearestNeighbor bfnn = new BruteForceNearestNeighbor(points2);
					for (IMultiPoint imp : searchPoints) {
						resultsBF[idx++] = bfnn.nearest(imp);
					}
					done = System.currentTimeMillis();
					bf_search_ts.addTrial(n, now, done);
					
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
						System.err.println(d + " has " + numDiff + " differences!");
					}
				}
				
				System.out.print(d + "\t" + nf.format(Double.valueOf(kd_build_ts.getAverage(n))));
				System.out.print("\t" + nf.format(Double.valueOf(kd_search_ts.getAverage(n)))); 
				System.out.print("\t" + nf.format(Double.valueOf(bf_search_ts.getAverage(n))));
				System.out.println();
			}
			
			// spacing between tables
			System.out.println();
			System.out.println();
		}
	}
}
