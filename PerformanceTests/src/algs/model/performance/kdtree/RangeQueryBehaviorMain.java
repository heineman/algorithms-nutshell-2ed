package algs.model.performance.kdtree;


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
	int numDrained;
	
	public void visit(DimensionalNode node) {
		ct++;					
	}
	public void drain (DimensionalNode node) {
		ct++;
		numDrained++;
	}
}

public class RangeQueryBehaviorMain {
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
		int NUM_TRIALS = 10;
		int maxN = 131072;
		int scale = 4000;
		
		for (int d = 2; d <= 5; d++) {
			TrialSuite kdSearch1 = new TrialSuite();
			TrialSuite kdSearch2 = new TrialSuite();
			TrialSuite kdSearch3 = new TrialSuite();
			
			TrialSuite bfSearch1 = new TrialSuite();
			TrialSuite bfSearch2 = new TrialSuite();
			TrialSuite bfSearch3 = new TrialSuite();
			
			Counter kd_count = new Counter();
			Counter bf_count = new Counter();
			
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
				
				// space1: Entire tree
				double lows[] = new double[d], highs[] = new double[d];
				for (int k = 0; k < d; k++) {
					lows[k] = -2*scale;
					highs[k] = 2*scale;
				}
				Hypercube space1 = new Hypercube (lows, highs);
				
				// space2: Quarter tree
				for (int k = 0; k < d; k++) {
					lows[k] = .5*scale;
					highs[k] = scale;
				}
				Hypercube space2 = new Hypercube (lows, highs);
				
				// empty: unlikely to find another point.
				for (int k = 0; k < d; k++) {
					lows[k] = rGen.nextDouble();
					highs[k] = lows[k];
				}
				Hypercube space3 = new Hypercube (lows, highs);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results1 = */ tree.range(space1, kd_count);
				}
				done = System.currentTimeMillis();
				kdSearch1.addTrial(n, now, done);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results2 = */ tree.range(space2, kd_count);
				}
				done = System.currentTimeMillis();
				kdSearch2.addTrial(n, now, done);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results3 = */ tree.range(space3, kd_count);
				}
				done = System.currentTimeMillis();
				kdSearch3.addTrial(n, now, done);
				
				//System.out.println("tree height:" + tree.height());
				BruteForceRangeQuery bfrq = new BruteForceRangeQuery(points);
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results1_bf = */ bfrq.search(space1, bf_count);
				}
				done = System.currentTimeMillis();
				bfSearch1.addTrial(n, now, done);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results2_bf = */ bfrq.search(space2, bf_count);
				}
				done = System.currentTimeMillis();
				bfSearch2.addTrial(n, now, done);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int ns = 0; ns < numSearches; ns++) {
					/* results3_bf = */ bfrq.search(space3, bf_count);
				}
				done = System.currentTimeMillis();
				bfSearch3.addTrial(n, now, done);
				
				// weak form of comparison
				if (kd_count.ct != bf_count.ct) {
					System.out.println("result1 fails");
				}
			}
		}
		System.out.println("KD-1 search info:" );
		System.out.println(kdSearch1.computeTable());
		System.out.println("KD-2 search info:" );
		System.out.println(kdSearch2.computeTable());
		System.out.println("KD-3 search info:" );
		System.out.println(kdSearch3.computeTable());

		System.out.println("BF-1 search info:" );
		System.out.println(bfSearch1.computeTable());
		System.out.println("BF-2 search info:" );
		System.out.println(bfSearch2.computeTable());
		System.out.println("BF-3 search info:" );
		System.out.println(bfSearch3.computeTable());
		
		
		}
	}
}
