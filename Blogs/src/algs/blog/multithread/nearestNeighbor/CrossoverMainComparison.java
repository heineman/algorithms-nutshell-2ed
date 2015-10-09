package algs.blog.multithread.nearestNeighbor;


import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.blog.multithread.nearestNeighbor.onehelper.OneHelperKDFactory;
import algs.blog.multithread.nearestNeighbor.onehelper.OneHelperKDNode;
import algs.blog.multithread.nearestNeighbor.onehelper.OneHelperKDTree;
import algs.blog.multithread.nearestNeighbor.smallhelpers.SmallProblemsKDFactory;
import algs.blog.multithread.nearestNeighbor.smallhelpers.SmallProblemsKDNode;
import algs.blog.multithread.nearestNeighbor.smallhelpers.SmallProblemsKDTree;
import algs.model.nd.Hyperpoint;

/**
 * Compute crossover effect in direct comparison between:
 * 
 * 1. Native KD Tree
 * 2. One-helper KD Tree (with wait time)
 * 3. SmallHelper KD Tree with 0 to 10 threads (with wait time)
 * <p>
 * 
 * Because there are so many parameters in the ensuing test cases, we are simply 
 * aggregating over all {@link #NUM_TRIALS} test cases, rather than trying to identify 
 * outlier scenarios (best and worst) which are then thrown out. To find the average times, 
 * simply divide all tabular numbers by the number of trials.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class CrossoverMainComparison {

	/** Number of trials to run. */
	public static final int NUM_TRIALS = 25;

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
	 * Run the comparison. 
	 */
	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// maximum parameters for trials
		int maxD = 35;
		int maxT = 2;  // max number of threads for smallHelpers.
		int maxDT = 9;  // when to stop smallHelpers from running (saves TIME)
		int n = 4096; 
		int numSearches = 1024; 
		
		
		System.out.println("NUM TRIALS:" + NUM_TRIALS);
		
		double fracts[] = new double[] { 0.00625, 0.0125, 0.025, 0.05, 0.1, 0.2, 0.4, 0.8 };
		for (int f = 0; f < fracts.length; f++) {
			double fract = fracts[f];
			System.out.println(fract + "...");
			
			// create set of points. Note that the search will always ensure that no
			// points are found.
			long kdSearch[] = new long[maxD];
			long othSearch[] = new long [maxD];
			long othWaiting[] = new long [maxD]; 
			        
			long sthSearch[][] = new long [maxD][maxT];
			long sthWaiting[][] = new long [maxD][maxT];
			
			for (int t = 0; t < NUM_TRIALS; t++) {
				
				System.out.println("  " + t + "...");
				long now, done;
				for (int d=2; d < maxD; d++) {
					
					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d);
		
					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();
					IMultiPoint[] searchPoints = randomPoints (numSearches, d);
		
					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					KDTree tree = KDFactory.generate(points);
					
					IMultiPoint[] resultsKD = new IMultiPoint[numSearches];
					
					// compute the search for native tree. Store results since those are going 
					// to be used for comparing final implementation (i.e., not timing)
					// --------------------------
					int idx = 0;
					System.gc();
					now = System.currentTimeMillis();
					for (IMultiPoint imp : searchPoints) {
						resultsKD[idx++] = tree.nearest(imp);
					}
					done = System.currentTimeMillis();
					kdSearch[d] += (done - now);
					
					// only do one OneHelper 
					// --------------------------
					OneHelperKDTree ohtree = OneHelperKDFactory.generate(points);
					
					IMultiPoint[] resultsOTH = new IMultiPoint[numSearches];
					
					idx = 0;
					System.gc();
					OneHelperKDNode.waiting = 0;
					now = System.currentTimeMillis();
					for (IMultiPoint imp : searchPoints) {
						resultsOTH[idx++] = ohtree.nearest(imp);
					}
					done = System.currentTimeMillis();
					othSearch[d] += (done - now);
					othWaiting[d] += OneHelperKDNode.waiting;
					
					// assert Equals for all returned points (i.e., same computation!)
					for (int x = 0; x < resultsOTH.length; x++) {
						assert (resultsOTH[x].equals(resultsKD[x]));
					}
					
					// the above points and searchPoint are used consistently for each of the remaining 
					// parameters. 
					if (d <= maxDT) {
						for (int nt = 0; nt < maxT; nt++) {
							
							// Small Problems
							SmallProblemsKDTree ttree = SmallProblemsKDFactory.generate(points);
							SmallProblemsKDNode.setNumberHelperThreads(nt);  // set num threads
							SmallProblemsKDTree.fract = fract;   // set fraction for volume
							
							IMultiPoint[] resultsSTH = new IMultiPoint[numSearches];
				
							idx = 0;
							System.gc();
							SmallProblemsKDNode.waiting = 0;
							now = System.currentTimeMillis();
							for (IMultiPoint imp : searchPoints) {
								resultsSTH[idx++] = ttree.nearest(imp);
							}
							done = System.currentTimeMillis();
							
							sthSearch[d][nt] += (done - now);
							sthWaiting[d][nt] += SmallProblemsKDNode.waiting; 
							
							// compare results?
							// assert Equals for all returned points (i.e., same computation!)
							for (int x = 0; x < resultsOTH.length; x++) {
								assert (resultsSTH[x].equals(resultsKD[x]));
							}
						}
					}					
				}
			}
		
			// output large table
			//   d, kd, othd, othwait, tkd0, wait0, tkd1, wait1, ..., tkd10, wait10
			// one for each fract value
			System.out.print("d,kd,othd,othwait,");
			for (int nt = 0; nt < maxT; nt++) {
				System.out.print("tkd" + nt + ",wait" + nt + ",");
			}
			System.out.println();
			for (int d = 2; d < maxD; d++) {
				System.out.print(d+","+kdSearch[d]+","+othSearch[d]+","+othWaiting[d]+",");
				for (int nt = 0; nt < maxT; nt++) {
					System.out.print(sthSearch[d][nt] + "," + sthWaiting[d][nt] + ",");
				}
				System.out.println();
			}
			System.out.println();
			
		}
	}
}
