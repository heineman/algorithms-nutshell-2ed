package algs.blog.multithread.nearestNeighbor.smallhelpers;
/**
 * This just validates brute force solution is same to kd-tree solution.
 */
import java.util.Random;
import java.util.StringTokenizer;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.tests.common.TrialSuite;

/**
 * Compute crossover effect (when no longer efficient as dimensions increase)
 * for the multi-threaded nearest neighbor queries.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class SmallProblemsKDCrossoverMain {

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

		// dimension for points.
		int d = 2;
		int maxD = 25;
		int n = 4096; 
		int numSearches = 1024; 

		// create set of points. Note that the search will always ensure that no
		// points are found.
		TrialSuite []kdSearch = new TrialSuite[maxD];
		TrialSuite []pkdSearch = new TrialSuite[maxD];
		TrialSuite []waiting = new TrialSuite[maxD];

		double fracts[] = new double[] { 0.00625, 0.0125, 0.025, 0.05, 0.1, 0.2, 0.4, 0.8 };
		int maxT = 10;
		for (int f = 0; f < fracts.length; f++) {
			double fract = fracts[f];
			double [][] kdtable = new double[maxD][maxT];
			double [][] pkdtable = new double[maxD][maxT];
			int [][] waitingtable = new int[maxD][maxT];
			
			System.out.println(fract + " ... ");
			for (int nt = 0; nt < maxT; nt++) {
				for (d=2; d < maxD; d++) {

					long now, done;
					kdSearch[d] = new TrialSuite();
					pkdSearch[d] = new TrialSuite();
					waiting[d] = new TrialSuite();
		
					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d);
		
					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();
					IMultiPoint[] searchPoints = randomPoints (numSearches, d);
		
					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					KDTree tree = KDFactory.generate(points);
					
					SmallProblemsKDTree ttree = SmallProblemsKDFactory.generate(points);
					SmallProblemsKDNode.setNumberHelperThreads(nt);  // set num threads
					SmallProblemsKDTree.fract = fract;   // set fraction for volume
					
					IMultiPoint[] resultsKD = new IMultiPoint[numSearches];
					IMultiPoint[] resultsPKD = new IMultiPoint[numSearches];
		
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
					for (IMultiPoint imp : searchPoints) {
						resultsPKD[idx++] = ttree.nearest(imp);
					}
					done = System.currentTimeMillis();
					pkdSearch[d].addTrial(d, now, done);
					waiting[d].addTrial(d, 0, SmallProblemsKDNode.waiting);
					SmallProblemsKDNode.waiting = 0;
		
					// compare results?
					int numDiff = 0;
					for (int i = 0; i < searchPoints.length; i++) {
						if (resultsKD[i] != resultsPKD[i]) {
							double bf = resultsKD[i].distance(searchPoints[i]);
							double kd = resultsPKD[i].distance(searchPoints[i]);
							if (!FloatingPoint.same(bf, kd)) {
								numDiff++;
							}
						}
					}
		
					if (numDiff != 0) {
						System.out.println(d + " has " + numDiff + " differences!");
					}
				}
		
				for (d = 2; d < kdSearch.length; d++) {
					String output = kdSearch[d].computeTable();
					int idx = output.indexOf('\n');
					output = output.substring(idx+1);
					StringTokenizer st = new StringTokenizer(output, ",");
					st.nextToken();
					Double dd = Double.valueOf(st.nextToken());
					kdtable[d][nt] = dd.doubleValue();
				}
				for (d = 2; d < pkdSearch.length; d++) {
					String output = pkdSearch[d].computeTable();
					int idx = output.indexOf('\n');
					output = output.substring(idx+1);
					StringTokenizer st = new StringTokenizer(output, ",");
					st.nextToken();
					Double dd = Double.valueOf(st.nextToken());
					pkdtable[d][nt] = dd.doubleValue();
				}
				for (d = 2; d < waiting.length; d++) {
					String output = waiting[d].computeTable();
					int idx = output.indexOf('\n');
					output = output.substring(idx+1);
					StringTokenizer st = new StringTokenizer(output, ",");
					st.nextToken();
					Integer ii = Integer.valueOf(st.nextToken());
					waitingtable[d][nt] = ii.intValue();
				}		
			}

			// output large table
			//   d, kd, tkd0, wait0, tkd1, wait1, ..., tkd10, wait10
			// one for each fract value
			System.out.print("d,kd,");
			for (int nt = 0; nt < maxT; nt++) {
				System.out.print("tkd" + nt + ",wait" + nt + ",");
			}
			System.out.println();
			for (d = 0; d < maxD; d++) {
				System.out.print(d+","+kdtable[d][0]+",");
				for (int nt = 0; nt < maxT; nt++) {
					System.out.print(pkdtable[d][nt] + "," + waitingtable[d][nt] + ",");
				}
				System.out.println();
			}
			System.out.println();
			
		}
		
	}
}
