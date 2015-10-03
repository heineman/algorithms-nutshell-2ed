package algs.chapter9.table2;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.tests.common.TrialSuite;
import algs.model.twod.TwoDPoint;

/**
 * Compute performance savings on using Akl-Toussaint heuristic.
 */
public class Main { 

	public static void main (String []args) {
		int NUM_TRIALS = 100;
		
		Generator<IPoint> g = new UniformGenerator();
		System.out.println ("size,avg. points on hull,avg. time to compute straight,avg. points removed,time for heuristic plus hull");

		TrialSuite tsNative = new TrialSuite(); // cost of native hull 
		TrialSuite tsHeur = new TrialSuite();   // cost of akl-toussaint
		TrialSuite tsBoth = new TrialSuite();   // cost of akl-toussaint+hull
		
		for (int n = 4096; n <= 1048576; n*= 2) {
			System.out.println(n + "...");
			long numRemoved = 0;
			long numPointsOnHull = 0;
			for (int t = 0; t < NUM_TRIALS; t++) {
				IPoint[] master = g.generate(n);
				
				// make copy to keep original in its shape.
				IPoint[] points = new IPoint[master.length];
				for (int i = 0; i < master.length; i++) {
					points[i] = new TwoDPoint(master[i]);
				}
				
				// compute natively.
				System.gc();
				long now = System.currentTimeMillis();
				IPoint[] hull1 = new ConvexHullScan().compute(points);
				long after = System.currentTimeMillis();
				tsNative.addTrial(n, now, after);			
				
				// compute heuristic
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] reduced = AklToussaint.reduce(points);
				long now2 = System.currentTimeMillis();
				IPoint[] hull2 = new ConvexHullScan().compute(reduced);
				after = System.currentTimeMillis();
				tsHeur.addTrial(n, now, now2);
				tsBoth.addTrial(n, now, after);
				
				// sanity check.
				assert (hull1.length == hull2.length);
				
				numRemoved += (master.length - reduced.length);
				numPointsOnHull += hull1.length;
			}			
			System.out.println("NumRemoved:" + (1.0f*numRemoved)/NUM_TRIALS);
			System.out.println("NumPointsOnHull:" + (1.0f*numPointsOnHull)/NUM_TRIALS);
		}
		
		System.out.println("native:" + tsNative.computeTable());
		System.out.println("heuristic:" + tsHeur.computeTable());
		System.out.println("both:" + tsBoth.computeTable());
	}
}
