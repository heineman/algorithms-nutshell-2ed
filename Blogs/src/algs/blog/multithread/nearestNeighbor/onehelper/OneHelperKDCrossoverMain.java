package algs.blog.multithread.nearestNeighbor.onehelper;
/**
 * This just validates brute force solution is same to kd-tree solution.
 */
import java.util.Random;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.tests.common.TrialSuite;

/**
 * Compute crossover effect (when no longer efficient as dimensions increase)
 * for the multi-threaded nearest neighbor queries.
 * <p>
 * In this case, a helper thread is spawned the first time a double-recursion
 * is needed.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class OneHelperKDCrossoverMain {

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

		for (d=2; d < maxD; d++) {
			System.out.println(d + " ... ");
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
			OneHelperKDTree ttree = OneHelperKDFactory.generate(points);
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
			waiting[d].addTrial(d, 0, OneHelperKDNode.waiting);
			OneHelperKDNode.waiting = 0;

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

		System.out.println("KD search");
		for (d = 2; d < kdSearch.length; d++) {
			System.out.println(kdSearch[d].computeTable());
		}
		System.out.println("PKD search");
		for (d = 2; d < pkdSearch.length; d++) {
			System.out.println(pkdSearch[d].computeTable());
		}
		System.out.println("Waiting times");
		for (d = 2; d < waiting.length; d++) {
			System.out.println(waiting[d].computeTable());
		}
	}
}
