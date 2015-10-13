package algs.model.performance.convexhull;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.slowhull.SlowHull;

/**
 * Detect when Slow Hull produces different hull from regular comparison.
 * Used to see when floating point computations become an issue, and if 
 * the result is noticeable.
 * 
 * It is only a matter of time before the code examples detects a situation
 * where the convex hull computations between BruteForce and Andrew's scan
 * are different.
 */
public class SmallDivergenceMain {

	/** 
	 * This code is designed to fail, to show issues with floating-point. 
	 */
	public static void main(String[] args) {
		Generator<IPoint> g = new UniformGenerator();

		for (int n = 10; n < 100; n++) {
			for (int t = 0; t < 10; t++) {
				System.out.println(n + "...");
				IPoint[] max = g.generate(n);

				IPoint[] hull = new ConvexHullScan().compute(max);

				// Compute Andrew scan with heuristic
				IPoint[] points = AklToussaint.reduce(max);
				IPoint[] hullRegular = new ConvexHullScan().compute(points);

				points = AklToussaint.reduce(max);
				IPoint hullSlow[]= new SlowHull().compute(points);

				if (hull.length != hullSlow.length) {
					for (int j = 0; j < max.length; j++) {
						System.out.println(max[j]);
					}
					System.err.println("We have a discrepancy...");
					System.exit(-1);
				}

				assert (hull.length == hullRegular.length);
				assert (hull.length == hullSlow.length);

				// point for point. EXCEPT for hull slow, which we have
				// to do containment checks.
				for (int i = 0; i < hull.length; i++) {
					assert (hull[i].equals(hullRegular[i]));
				}

				// if there never is a match... PROBLEM!
				for (int i = 0; i < hull.length; i++) {
					boolean match = false;
					for (int j = 0; j < hullSlow.length; j++) {
						if (hull[i].equals (hullSlow[i])) {
							match = true;
							break;
						}
					}

					if (!match) {
						System.err.println ("Slow failed to have point:" + hull[i]);
						System.exit(-1);
					}
				}
			}
		}
	}
}
