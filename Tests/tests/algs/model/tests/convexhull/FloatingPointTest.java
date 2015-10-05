package algs.model.tests.convexhull;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.slowhull.SlowHull;

public class FloatingPointTest extends TestCase {

	@Test
	public void testDifference() {
		
		int NUM = 1;   // was 10
		
		int val = 10;
		
		for (int i = 0; i < NUM; i++) {
			Generator<IPoint> g = new UniformGenerator();
			
			IPoint[] points = g.generate(val);
			
			IPoint[] hull1 = new ConvexHullScan().compute(points);
			IPoint[] hull2 = new SlowHull().compute(points);
			
			if (!Arrays.deepEquals(hull1, hull2)) {
				System.out.println ("Andrew has hull of size:" + hull1.length);
				System.out.println ("SlowHull has hull of size:" + hull2.length);
			}
			
			val *= 7;
		}
	}
}
