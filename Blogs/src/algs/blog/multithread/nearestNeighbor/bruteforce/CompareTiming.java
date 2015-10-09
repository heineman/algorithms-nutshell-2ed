package algs.blog.multithread.nearestNeighbor.bruteforce;

import java.util.Random;

import algs.model.IMultiPoint;
import algs.blog.multithread.nearestNeighbor.bruteforce.MultiThreadedBruteForceNearestNeighbor;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.tests.common.TrialSuite;


public class CompareTiming {
	/** random number generator. */
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

		
	/**
	 * Args[0] reflects number of threads to use.
	 * @param args
	 */
	public static void main (String []args) {
		
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.
		long now, before;
		
		int numSearches = 1024;
		int numThreads = 2;
		int size = 262144;
		try {
			numThreads = Integer.valueOf(args[0]);
			size = Integer.valueOf(args[1]);
		} catch (Exception e) {
			
		}
		
		int NUM_TRIALS = 10;
		
		System.out.println("Num Threads:" + numThreads);
		System.out.println("Size:" + size);
		for (int d = 2; d < 10; d++) {
			System.out.println("d = " + d);
			TrialSuite bf_ts = new TrialSuite();
			TrialSuite mtbf_ts = new TrialSuite();
			
			for (int T = 0; T < NUM_TRIALS; T++) {
				
				// Perform a number of searches drawn from same [0,1] uniformly.
				IMultiPoint[] searchPoints = randomPoints (numSearches, d);
				
				for (int n = size; n >= 4; n /= 2) {
				
					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d);
					
					BruteForceNearestNeighbor bnn = new BruteForceNearestNeighbor(points);
					
					MultiThreadedBruteForceNearestNeighbor mt_bnn = new MultiThreadedBruteForceNearestNeighbor(points);
					mt_bnn.setNumberThreads(numThreads);
					
					IMultiPoint results[] = new IMultiPoint[searchPoints.length];
					IMultiPoint resultsm[] = new IMultiPoint[searchPoints.length];
					
					// compute native BF
					System.gc();
					before = System.currentTimeMillis();
					for (int t = 0; t < searchPoints.length; t++) { 
						results[t] = bnn.nearest(searchPoints[t]);
					}
					now = System.currentTimeMillis();
					bf_ts.addTrial(n, before, now);
					
					// compute native BF
					System.gc();
					before = System.currentTimeMillis();
					for (int t = 0; t < searchPoints.length; t++) { 
						resultsm[t] = mt_bnn.nearest(searchPoints[t]);
					}
					now = System.currentTimeMillis();
					mtbf_ts.addTrial(n, before, now);
					
					// compare
					for (int k = 0; k < results.length; k++) {
						assert (results[k].equals(results[k]));
					}
				}
			}
			
			System.out.println("Brute Force");
			System.out.println(bf_ts.computeTable());
			System.out.println("Multi Threaded Brute Force");
			System.out.println(mtbf_ts.computeTable());
			
		}
	}
}
