package algs.model.performance.convexhull;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.bucket.BucketAndrew;
import algs.model.problems.convexhull.slowhull.SlowHull;
import algs.model.tests.common.TrialSuite;

/**
 * note that SLOW still may return different hull for even 80 points. Must
 * still check into floating point computations to see why...
 *  
 */
public class HullComparisonsMain {

	
	public static void main (String[] args) {
		Generator<IPoint> g = new UniformGenerator();
		TrialSuite hullTS = new TrialSuite();
		TrialSuite hullAklTS = new TrialSuite();
		TrialSuite hullRegularTS = new TrialSuite();
		TrialSuite hullSlowTS = new TrialSuite();
		TrialSuite hullBucketTS = new TrialSuite();
		TrialSuite hullBalancedTS = new TrialSuite();
		
		int numTrials = 5;
		
		for (int n = 5; n <= 131072*8; n *= 2) {
			System.out.println(n + "...");
			for (int t = 0; t < numTrials; t++) {
				IPoint[] max = g.generate(n);
				
				long now, done;
				// standard comparison w/out Akl-Toussaint heuristic
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] hull = new ConvexHullScan().compute(max);
				done = System.currentTimeMillis();
				hullTS.addTrial(n, now, done);
				
				// Compute Andrew scan with heuristic
				System.gc();
				now = System.currentTimeMillis();
				IPoint[] points = AklToussaint.reduce(max);
				IPoint[] hullRegular = new ConvexHullScan().compute(points);
				done = System.currentTimeMillis();
				hullAklTS.addTrial(n, now, done);
				
				// compute slow hull
				if (n < 100) {
					System.gc();
					now = System.currentTimeMillis();
					points = AklToussaint.reduce(max);
					/* IPoint hullSlow[]= */ new SlowHull().compute(points);
					done = System.currentTimeMillis();
					hullSlowTS.addTrial(n, now, done);
				}
				
				// compute balanced  
				System.gc();
				now = System.currentTimeMillis();
				points = AklToussaint.reduce(max);
				IPoint hullBalanced[]= new BucketAndrew().compute(points);
				done = System.currentTimeMillis();
				hullBalancedTS.addTrial(n, now, done);
				
				// Fast enough that it could compare favorably w/ AKL
				System.gc();
				now = System.currentTimeMillis();
				points = AklToussaint.reduce(max);
				IPoint hullBucket[]= new BucketAndrew().compute(points);
				done = System.currentTimeMillis();
				hullBucketTS.addTrial(n, now, done);
				
				assert(hull.length == hullRegular.length);
				assert(hull.length == hullBucket.length);
				assert(hull.length == hullBalanced.length);
				//assertEquals(hull.length, hullSlow.length);
				
				// point for point. EXCEPT for hull slow, which we have
				// to do containment checks.
				for (int i = 0; i < hull.length; i++) {
					assert (hull[i].equals(hullRegular[i]));
					assert (hull[i].equals(hullBucket[i]));
					assert (hull[i].equals(hullBalanced[i]));
				}
	
// COMMENT OUT: TOO SLOW!	
//				// if there never is a match... PROBLEM!
//				for (int i = 0; i < hull.length; i++) {
//					boolean match = false;
//					for (int j = 0; j < hullSlow.length; j++) {
//						if (hull[i].equals (hullSlow[i])) {
//							match = true;
//							break;
//						}
//					}
//					
//					if (!match) {
//						fail ("Slow failed to have point:" + hull[i]);
//					}
//				}
			}
		}
		
		System.out.println("Hull TrialSuite");
		System.out.println(hullTS.computeTable());
		
		System.out.println("HullAkl TrialSuite");
		System.out.println(hullAklTS.computeTable());
		
		System.out.println("Hull Regular TrialSuite");
		System.out.println(hullRegularTS.computeTable());
		
		System.out.println("Hull Slow TrialSuite");
		System.out.println(hullSlowTS.computeTable());
		
		System.out.println("Hull Bucket TrialSuite");
		System.out.println(hullBucketTS.computeTable());
		
		System.out.println("Hull Balanced TrialSuite");
		System.out.println(hullBalancedTS.computeTable());
	}
}
