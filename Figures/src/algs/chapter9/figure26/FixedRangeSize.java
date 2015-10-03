package algs.chapter9.figure26;

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
 *  
 * We cover in the book why this FixedRangeSize is not sufficient to be able
 * to describe differences as d increases. The main reason is that we do not
 * isolate the changes to d.
 */
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.CounterKDTree;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.nd.Hyperpoint;
import algs.model.problems.rangeQuery.BruteForceRangeQuery;
import algs.model.tests.common.TrialSuite;


public class FixedRangeSize {
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
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 128;
		int NUM_TRIALS = 100;
		int maxN = 131072;
		int scale = 4000;

		System.out.println("NUM_TRIALS:" + NUM_TRIALS + ", numSearches=" + numSearches);
		for (int d = 2; d <= 15; d++) {
			System.out.println(d + "...");
			TrialSuite kdSearch2 = new TrialSuite();
			TrialSuite bfSearch2 = new TrialSuite();
			TrialSuite hitsSearch2 = new TrialSuite();

			CounterKDTree kd_count = new CounterKDTree ();
			CounterKDTree bf_count = new CounterKDTree ();

			for (int n=4096; n <= maxN; n*=2) {
				System.out.println (n);
				for (int t = 1; t <= NUM_TRIALS; t++) {
					long now, done;

					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d, scale);

					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();

					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					KDTree tree = KDFactory.generate(points);
					
					double lows[] = new double[d], highs[] = new double[d];

					// space2: Quarter tree
					for (int k = 0; k < d; k++) {
						lows[k] = .5*scale;
						highs[k] = scale;
					}
					Hypercube space2 = new Hypercube (lows, highs);


					System.gc();
					now = System.currentTimeMillis();
					for (int ns = 0; ns < numSearches; ns++) {
						/* results2 = */ tree.range(space2, kd_count);
					}
					done = System.currentTimeMillis();
					kdSearch2.addTrial(n, now, done);


					BruteForceRangeQuery bfrq = new BruteForceRangeQuery(points);
					System.gc();
					now = System.currentTimeMillis();
					for (int ns = 0; ns < numSearches; ns++) {
						/* results2_bf = */ bfrq.search(space2, bf_count);
					}
					done = System.currentTimeMillis();
					bfSearch2.addTrial(n, now, done);

					// weak form of comparison
					if (kd_count.getCount() != bf_count.getCount()) {
						System.err.println("result1 fails");
					}

					// simply keep track of the number of found points.
					hitsSearch2.addTrial(n, 0, kd_count.getCount());
				}
			}

			System.out.println("KD-2 search info:" );
			System.out.println(kdSearch2.computeTable());

			System.out.println("BF-2 search info:" );
			System.out.println(bfSearch2.computeTable());

			System.out.println("found points search info:" );
			System.out.println(hitsSearch2.computeTable());
		}
	}
}
