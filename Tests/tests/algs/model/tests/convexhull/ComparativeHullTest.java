package algs.model.tests.convexhull;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.twod.TwoDPoint;
import junit.framework.TestCase;

public class ComparativeHullTest extends TestCase {

	@Test
	public void testConstruction() {
		int n = 256;
		int numThreads = 2;
		Generator<IPoint> g = new UniformGenerator();
		IPoint[] master = g.generate(n);

		// make copy to keep original in its shape.
		IPoint[] points = new IPoint[master.length];
		for (int i = 0; i < master.length; i++) {
			points[i] = new TwoDPoint(master[i]);
		}

		// compute natively.
		IPoint[] hull1 = new algs.model.problems.convexhull.andrew.ConvexHullScan().compute(points);

		// compute multithread.
		IPoint[] hull1a = new algs.model.problems.convexhull.parallel.ConvexHullScan(numThreads).compute(points);

		// compute heuristic (single thread)
		IPoint[] reduced = algs.model.problems.convexhull.AklToussaint.reduce(points);
		IPoint[] hull2 = new algs.model.problems.convexhull.andrew.ConvexHullScan().compute(reduced);

		// compute full Parallel Heuristic (both multi-thread)
		IPoint[] reduced2a = algs.model.problems.convexhull.AklToussaint.reduce(points);
		IPoint[] hull2a = new algs.model.problems.convexhull.parallel.ConvexHullScan(numThreads).compute(reduced2a);

		// sanity check.
		assertEquals (hull1.length, hull2.length);
		assertEquals (hull1a.length, hull1.length);
		assertEquals (hull2a.length, hull2.length);
		assertEquals (reduced2a.length, reduced.length);
	}
	
	@Test
	public void testParallel() {
		int n = 256;
		Generator<IPoint> g = new UniformGenerator();
		IPoint[] master = g.generate(n);

		// make copy to keep original in its shape.
		IPoint[] points = new IPoint[master.length];
		IPoint[] points2 = new IPoint[master.length];
		for (int i = 0; i < master.length; i++) {
			points[i] = new TwoDPoint(master[i]);
			points2[i] = new TwoDPoint(master[i]);
		}
		
		// compute heuristic (single thread)
		IPoint[] reduced = algs.model.problems.convexhull.AklToussaint.reduce(points);
		
		// compute multithread.
		IPoint[] reduced2 = algs.model.problems.convexhull.parallel.AklToussaint.reduce(points2);

		// fix this!
		assertEquals (reduced2.length, reduced.length);
	}		
}
