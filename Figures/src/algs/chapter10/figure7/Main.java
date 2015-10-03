package algs.chapter10.figure7;

/**
 * Create data for figure 10-7 (2ed).
 */
import java.text.NumberFormat;
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


	public static void main (String []args) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// Number of points to search for.
		int numSearches = 128;
		int scale = 1;

		for (int n = 131072; n <= 524288; n*= 2) {
			System.out.println("#d\tsingle\tdouble");
			for (int d = 2; d <= 32; d++) {
				// create n random points in d dimensions drawn from [0,1] uniformly
				IMultiPoint[] points = randomPoints (n, d, scale);

				// Perform a number of searches drawn from same [0,1] uniformly.
				IMultiPoint[] searchPoints = randomPoints (numSearches, d, scale);

				// This forms the basis for the kd-tree. These are the points p. Note
				// that the KDTree generate method will likely shuffle the points. 
				KDTree tree= KDFactory.generate(points);

				DimensionalNode.numDoubleRecursions=0;
				DimensionalNode.numRecursions=0;
				for (IMultiPoint imp : searchPoints) {
					tree.nearest(imp);
				}
				double dr = DimensionalNode.numDoubleRecursions/(1.0*numSearches);
				double r = DimensionalNode.numRecursions/(1.0*numSearches);

				System.out.println(d + "\t" + nf.format(r) + "\t" + nf.format(dr));
			}
			
			// separation between tables
			System.out.println();
			System.out.println();
		}
	}
}
