package algs.blog.multithread.convexhull;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.tests.common.TrialSuite;
import algs.model.twod.TwoDPoint;

/**
 * Compute ConvexHull implementations with several variations: with and 
 * without using the {@link AklToussaint} heuristic; with and without
 * using threads.
 * <p>
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class Main { 

	/** Number of available threads to use. Default to 1. */
	public static int numThreads = 1;
	
	/**
	 * Run comparison with given number of threads available (args[0]).
	 * 
	 * @param args
	 */
	public static void main (String []args) {
		if (args.length > 0) {
			numThreads = Integer.valueOf(args[0]);
		}
		
		int NUM_TRIALS = 100;
		
		Generator<IPoint> g = new UniformGenerator();
		System.out.println("Num Threads:" + numThreads);
		
		TrialSuite tsNative = new TrialSuite();      // cost of native hull with HeapSort
		TrialSuite tsMulti = new TrialSuite();       // cost of multi-threaded with QuickSort numThreads
		TrialSuite tsHeur = new TrialSuite();        // cost of akl-toussaint
		TrialSuite tsMultiHeur = new TrialSuite();   // cost of multi-thread akl-toussaint
		TrialSuite tsBoth = new TrialSuite();        // cost of akl-toussaint+hull
		TrialSuite tsMultiBoth = new TrialSuite();   // cost of multi-thread akl-toussaint+hull
		
		for (int n = 2048; n <= 1048576; n*= 2) {  // = 131072
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
				IPoint[] hull1 = new algs.model.problems.convexhull.andrew.ConvexHullScan().compute(points);
				long after = System.currentTimeMillis();
				tsNative.addTrial(n, now, after);			
				
				// compute multithread.
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] hull1a = new algs.blog.multithread.convexhull.ConvexHullScan(numThreads).compute(points);
				after = System.currentTimeMillis();
				tsMulti.addTrial(n, now, after);			
				
				// compute heuristic (single thread)
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] reduced = algs.model.problems.convexhull.AklToussaint.reduce(points);
				long now2 = System.currentTimeMillis();
				IPoint[] hull2 = new algs.model.problems.convexhull.andrew.ConvexHullScan().compute(reduced);
				after = System.currentTimeMillis();
				tsHeur.addTrial(n, now, now2);
				tsBoth.addTrial(n, now, after);
				
				// compute full Parallel Heuristic (both multi-thread)
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] reduced2a = algs.blog.multithread.convexhull.AklToussaint.reduce(points);
				now2 = System.currentTimeMillis();
				IPoint[] hull2a = new algs.blog.multithread.convexhull.ConvexHullScan(numThreads).compute(reduced2a);
				after = System.currentTimeMillis();
				tsMultiHeur.addTrial(n, now, now2);
				tsMultiBoth.addTrial(n, now, after);
				
				// sanity check.
				assert (hull1.length == hull2.length);
				assert (hull1a.length == hull1.length);
				assert (hull2a.length == hull2.length);
				assert (reduced2a.length == reduced.length);
				
				numRemoved += (master.length - reduced.length);
				numPointsOnHull += hull1.length;
			}			
			System.out.println("NumRemoved:" + (1.0f*numRemoved)/NUM_TRIALS);
			System.out.println("NumPointsOnHull:" + (1.0f*numPointsOnHull)/NUM_TRIALS);
		}
		
		System.out.println("native:" + tsNative.computeTable());
		System.out.println("heuristic:" + tsHeur.computeTable());
		System.out.println("both:" + tsBoth.computeTable());
		System.out.println();
		System.out.println("multithread:" + tsMulti.computeTable());
		System.out.println("multi-threadheuristic:" + tsMultiHeur.computeTable());
		System.out.println("multi-thread both:" + tsMultiBoth.computeTable());
		System.out.println();
	}
}
