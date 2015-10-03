package algs.chapter9.table7;


/**
 * Try to show O(n^(1-1/d)+k) behavior where n is the number of elements in the
 * kd-tree being searched for the range query and k is the number of found
 * points.
 * 
 * Test on three setups:
 * 
 * (a) WHOLE TREE: query will be [-scale*2, scale*2, -scale*2, scale*2]
 * (b) QUARTER TREE: query will be [ scale*.52, scale, scale*.52, scale]
 *      upper 23% of the tree range
 * (c) RANGE with no points (or at least, will be a range == a single point)
 */
import java.text.NumberFormat;
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.IVisitKDNode;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.nd.Hyperpoint;
import algs.model.problems.rangeQuery.BruteForceRangeQuery;
import algs.model.tests.common.TrialSuite;

class Counter implements IVisitKDNode {
	int ct;

	public void visit(DimensionalNode node) {
		ct++;					
	}

	public void drain (DimensionalNode node) {
		ct++;
	}
}

public class Main {
	// random number generator.
	static Random rGen;

	/** 
	 * generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	private static IMultiPoint[] randomPoints (int n, int d, int scale) {
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
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 128;
		int NUM_TRIALS = 100;
		int maxN = 131072;
		int maxD = 5;
		int scale = 4000;

		System.out.println("n\td=2 RQ\td=3 RQ\td=4 RQ\td=5 RQ\td=2 BF\td=3 BF\td=4 BF\td=5 BF");
		
		for (int n=4096; n <= maxN; n*=2) {
			double results_RQ[] = new double[maxD+1];  // +1 for easier coding later
			double results_BF[] = new double[maxD+1];
			for (int d = 2; d <= maxD; d++) {
				TrialSuite kdSearch1 = new TrialSuite();
				TrialSuite bfSearch1 = new TrialSuite();

				Counter kd_count = new Counter();
				Counter bf_count = new Counter();

				for (int t = 1; t <= NUM_TRIALS; t++) {
					long now, done;

					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d, scale);

					// Perform a number of searches drawn from same [0,scale] uniformly.
					System.gc();

					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					now = System.currentTimeMillis();
					KDTree tree = KDFactory.generate(points);
					done = System.currentTimeMillis();

					// space1: Entire tree
					double lows[] = new double[d], highs[] = new double[d];
					for (int k = 0; k < d; k++) {
						lows[k] = -2*scale;
						highs[k] = 2*scale;
					}
					Hypercube space1 = new Hypercube (lows, highs);

					System.gc();
					now = System.currentTimeMillis();
					for (int ns = 0; ns < numSearches; ns++) {
						/* results1 = */ tree.range(space1, kd_count);
					}
					done = System.currentTimeMillis();
					kdSearch1.addTrial(n, now, done);

					BruteForceRangeQuery bfrq = new BruteForceRangeQuery(points);
					System.gc();
					now = System.currentTimeMillis();
					for (int ns = 0; ns < numSearches; ns++) {
						bfrq.search(space1, bf_count);
					}
					done = System.currentTimeMillis();
					bfSearch1.addTrial(n, now, done);

					// weak form of comparison
					if (kd_count.ct != bf_count.ct) {
						System.err.println("result1 fails");
					}
				}

				results_RQ[d] = Double.valueOf(kdSearch1.getAverage(n));
				results_BF[d] = Double.valueOf(bfSearch1.getAverage(n));
			}

			System.out.print(n + "\t");
			for (int d = 2; d <= 5; d++) {
				System.out.print(nf.format(results_RQ[d]) + "\t");
			}
			for (int d = 2; d <= 5; d++) {
				System.out.print(nf.format(results_BF[d]) + "\t");
			}
			System.out.println();
		}
	}
}
