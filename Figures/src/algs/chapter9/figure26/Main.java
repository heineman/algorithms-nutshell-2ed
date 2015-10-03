package algs.chapter9.figure26;


/**
 * Try to show O(n^(1-1/d)+k) behavior where n is the number of elements in the
 * kd-tree being searched for the range query and k is the number of found
 * points.
 * 
 * Test on three setups:
 * 
 * (a) WHOLE TREE: query will be [-scale*2, scale*2, -scale*2, scale*2]
 * 
 * (b) QUARTER TREE: We want to guarantee roughly a quarter of the tree is 
 *     within the query. Now, to do this properly we don't want there to be 
 *     an even division that nicely aligns with the median, so we start with
 *     the premise that we want 23% of the original space to be searched. Let's
 *     call the original square area R and the smaller (23% to be exact) square 
 *     region T. We know T=.23*R which means the base t (where t*t = T) is equal
 *     to sqrt(.23) since t^2=.23*r^2 where r is the base of R. The square root
 *     of .23 is t=.47958... so take query [(1-t)*scale,scale,(1-t)*scale,scale]
 *     to cover 23% of the region.  Note this scales to multiple dimensions as  
 *     well, which is CRITICAL since we must ensure we are searching the same
 *     relative space when we increase dimensions, otherwise we are changing 
 *     two factors at once. So for three dimensions, we take the cube
 *     root of .23 which gives t=.61269 and thus we query (roughly)
 *     [.387*scale,scale,*.387*scale]. So we must query a larger range on the 
 *     axis, but this ensures the size of the (in this case CUBED) space is the 
 *     same proportion as our original.     
 * 
 * (c) RANGE where the target percentage drops down to very small 1% range
 * 
 * Contrast these results with the Fixed table which shows what happens when
 * we simply query smaller and smaller percentages of the query space.
 * 
 */
import java.text.NumberFormat;
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.CounterKDTree;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.nd.Hyperpoint;
import algs.model.problems.rangeQuery.BruteForceRangeQuery;
import algs.model.tests.common.TrialSuite;

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
		int numSearches = 6; // 128;
		int NUM_TRIALS = 4;
		int maxN = 131072;
		int scale = 4000;

		// start with permanent ratio to maintain:
		double ratio = .23;
		while (ratio > .01) {
			
			System.out.println("d\tKD4096\tKD8192\tKD16384\tKD32768\tKD65536\tKD131072\tBF4096\tBF8192\tBF16384\tBF32768\tBF65536\tBF131072");

			for (int d = 2; d <= 15; d++) {
				TrialSuite kdSearch2 = new TrialSuite();
				TrialSuite bfSearch2 = new TrialSuite();
				TrialSuite hitsSearch2 = new TrialSuite();

				// record one for each of the six columns
				double record_kd[] = new double[6];
				double record_bf[] = new double[6];
				
				for (int idx=0,n=4096; n <= maxN; n*=2,idx++) {
					for (int tn = 1; tn <= NUM_TRIALS; tn++) {
						long now, done;

						CounterKDTree kd_count = new CounterKDTree();
						CounterKDTree bf_count = new CounterKDTree();

						// create n random points in d dimensions drawn from [0,1] uniformly
						IMultiPoint[] points = randomPoints (n, d, scale);

						// Perform a number of searches drawn from same [0,1] uniformly.
						System.gc();

						// This forms the basis for the kd-tree. These are the points p. Note
						// that the KDTree generate method will likely shuffle the points. 
						KDTree tree = KDFactory.generate(points);

						double lows[] = new double[d], highs[] = new double[d];

						// space2: Construct query bounds based upon d and fixed ratio
						// center query around (scale/2,scale/2) center point.
						double t = Math.pow(ratio, 1.0/d);
						for (int k = 0; k < d; k++) {
							lows[k] = (.5 - t/2.0)*scale;
							highs[k] = (.5 + t/2.0)*scale;
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
							System.out.println("result1 fails");
						}

						// simply keep track of the number of found points.
						hitsSearch2.addTrial(n, 0, kd_count.getCount());
					}
					
					record_kd[idx] = Double.valueOf(kdSearch2.getAverage(n));
					record_bf[idx] = Double.valueOf(bfSearch2.getAverage(n));
				}

				
				System.out.print(d + "\t");
				for (int idx = 0; idx < 6; idx++) {
					System.out.print(record_kd[idx]);
					System.out.print("\t");
				}
				System.out.print("\t");
				for (int idx = 0; idx < 6; idx++) {
					System.out.print(record_bf[idx]);
					System.out.print("\t");
				}
				
				System.out.println();
			}
			
			ratio /= 2;
			
			// extra spacing in output for GnuPlot separation 
			System.out.println();
			System.out.println();
		}
	}
}
