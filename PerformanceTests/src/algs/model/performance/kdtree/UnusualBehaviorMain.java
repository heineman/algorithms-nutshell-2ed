package algs.model.performance.kdtree;


/**
 * Try to show O(n^(1-1/d)) behavior where n is the number of elements in the
 * kd-tree being searched for the range query.
 * 
 * Test on three setups:
 * 
 * (a) WHOLE TREE: query will be [-scale*2, scale*2, -scale*2, scale*2]
 * (b) QUARTER TREE: query will be [ scale*.52, scale, scale*.52, scale]
 *      upper 23% of the tree range
 * (c) RANGE with no points (or at least, will be a range == a single point)
 */
import java.util.Random;

import algs.model.IHypercube;
import algs.model.IPoint;
import algs.model.data.points.UnusualGenerator;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.problems.rangeQuery.BruteForceRangeQuery;
import algs.model.tests.common.TrialSuite;

public class UnusualBehaviorMain {
	// random number generator.
	static Random rGen;


	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 128;
		int NUM_TRIALS = 10;
		int maxN = 131072;

		// all queries will be within +/- 1 unit square, thus no points
		// will be found.
		UnusualGenerator gen = new UnusualGenerator(3);

		TrialSuite kdSearch1 = new TrialSuite();
		TrialSuite bfSearch1 = new TrialSuite();

		Counter kd_count = new Counter();
		Counter bf_count = new Counter();

		for (int n=4096; n <= maxN; n*=2) {
			System.out.println (n);
			for (int t = 1; t <= NUM_TRIALS; t++) {
				long now, done;

				// create n random points in d dimensions drawn from [0,1] uniformly
				IPoint[] points = gen.generate (n);

				// Perform a number of searches drawn from same [0,1] uniformly.
				System.gc();

				// This forms the basis for the kd-tree. These are the points p. Note
				// that the KDTree generate method will likely shuffle the points. 
				KDTree tree = KDFactory.generate(points);
				
				// all interior and none should be found.
				IHypercube space = new Hypercube(-1, 1, -1, 1);

				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					tree.range(space, kd_count);
				}
				done = System.currentTimeMillis();
				kdSearch1.addTrial(n, now, done);

				BruteForceRangeQuery bfrq = new BruteForceRangeQuery(points);
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					bfrq.search(space, bf_count);
				}
				done = System.currentTimeMillis();
				bfSearch1.addTrial(n, now, done);

				// weak form of comparison
				assert (0 == kd_count.ct);
				assert (0 == bf_count.ct);
			}
		}
		System.out.println("KD-1 search info:" );
		System.out.println(kdSearch1.computeTable());

		System.out.println("BF-1 search info:" );
		System.out.println(bfSearch1.computeTable());
	}
}
