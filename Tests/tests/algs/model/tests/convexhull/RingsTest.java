package algs.model.tests.convexhull;


import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;

public class RingsTest extends TestCase {
	
	@Test
	public void testSizeOfRandomHull() {
		int idx = 2;
		
		int NUM = 9;  // was 20
		
		Generator<IPoint> g = new UniformGenerator();
		System.out.println ("Size of random convex hull.");
		
		System.out.println ("size,numEliminated,finalSize");
		for (int i = 1; i < NUM; i++) {
			IPoint[] points = g.generate(idx);
			
			int num = points.length;
			IPoint[] newPoints = AklToussaint.reduce(points);
			num = num - newPoints.length;
			
			IPoint[] hull = new ConvexHullScan().compute(newPoints);
			
			System.out.println (idx + "," + num + "," + hull.length);
			
			//LinkedList<TwoDPoint[]> rings = Rings.compute(points);
			//Rings.info(rings);
			
			idx *= 2;
		}
	}
}
