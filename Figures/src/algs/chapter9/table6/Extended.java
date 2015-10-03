package algs.chapter9.table6;

/**
 * Produce explanation for degenerate worst case for Nearest Neighbor using
 * the circle generator.
 */
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.data.points.CircleGenerator;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.tests.common.TrialSuite;
import algs.model.twod.TwoDPoint;

public class Extended {
	/** random number generator. */
	static Random rGen;

	/** 
	 * Generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	public static IMultiPoint[] randomSearchPoints (int n, int scale) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			double d1 = rGen.nextDouble()*scale;
			double d2 = rGen.nextDouble()*scale;
			
			points[i] = new TwoDPoint(d1, d2);
		}

		return points;
	}
	
	/** 
	 * Generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	public static IMultiPoint[] randomPoints (int n, int scale) {
		CircleGenerator circle = new CircleGenerator(1);
		
		IPoint[] pts = circle.generate(n);
		
		IMultiPoint points[] = new IMultiPoint[pts.length];
		for (int i = 0; i < points.length; i++) {
			points[i] = new TwoDPoint (pts[i].getX(), pts[i].getY()); 
		}
		
		return points;
	}	

	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 50;
		int NUM_TRIALS = 1;

		int scale = 10;

		//  n=4 to 131,072 random two dimensional 
		TrialSuite kdSearch = new TrialSuite();
		for (int n=4; n <= 131072; n*=2) { /* start from 4 for table 9-7 */
			for (int t = 1; t <= NUM_TRIALS; t++) {
				long now, done;

				// create n random points in d dimensions drawn from [0,1] uniformly
				IMultiPoint[] points = randomSearchPoints (n, 1);
				
				// Perform a number of searches drawn from same [0,1] uniformly.
				System.gc();
				IMultiPoint[] searchPoints = randomPoints (numSearches, scale);

				// This forms the basis for the kd-tree. These are the points p. Note
				// that the KDTree generate method will likely shuffle the points. 
				KDTree tree= KDFactory.generate(points);

				DimensionalNode.numDoubleRecursions=0;
				DimensionalNode.numRecursions=0;
				System.gc();
				now = System.currentTimeMillis();
				for (IMultiPoint imp : searchPoints) {
					tree.nearest(imp);
				}
				done = System.currentTimeMillis();
				kdSearch.addTrial(n, now, done);
				double dr = DimensionalNode.numDoubleRecursions/(1.0*numSearches);
				double r = DimensionalNode.numRecursions/(1.0*numSearches);

				System.out.println(n + "," + r + "," + dr);
			}
		}
	}
}
