package algs.chapter10.table1;

/**
 * Generate results of table showing results at d={2,10} for points in 
 * n={4, 131072} and # of recursions (single and double).
 */
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;

public class Main {
	/** random number generator. */
	static Random rGen;

	/** 
	 * Generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	public static IMultiPoint[] randomPoints (int n, int d, int scale) {
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

	/**
	 * Compute and display the given table.
	 * 
	 * @param args
	 */
	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 50;
		int NUM_TRIALS = 100;
		int scale = 1;

		System.out.println("n\tRec(2)\tDbl(2)\tRec(10)\tDbl(10)");

		// this is table 9-6.
		//  n=4 to 131,072 random with dimensions {2, 10}. 
		for (int n=4; n <= 131072; n*=2) { /* start from 4 for table 9-7 */
			double dr[] = new double[2];
			double r[] = new double[2];

			for (int d = 2; d <= 10; d *= 5) {
				for (int t = 1; t <= NUM_TRIALS; t++) {
					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d, scale);

					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();
					IMultiPoint[] searchPoints = randomPoints (numSearches, d, scale);

					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					KDTree tree= KDFactory.generate(points);

					DimensionalNode.numDoubleRecursions=0;
					DimensionalNode.numRecursions=0;
					for (IMultiPoint imp : searchPoints) {
						tree.nearest(imp);
					}
					dr[d/10] = DimensionalNode.numDoubleRecursions/(1.0*numSearches);
					r[d/10] = DimensionalNode.numRecursions/(1.0*numSearches);
				}
			}

			System.out.println(n + "\t" + r[0] + "\t" + dr[0] + "\t" + r[1] + "\t" + dr[1]);
		}
	}
}
