package algs.model.tests.convexhull;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.andrew.ConvexHullScanLinkedList;

/**
 * Given random point selections, what is the average number of points
 * in the convex hull?
 *
 * 10,0,31
 * 100,0,0
 * 1000,16,31
 * 10000,15,859
 * 100000,141,169079
 * 
 * @author George Heineman
 */
public class LinkedListComparisonTest extends TestCase {

	@Test
	public void testDegeneratCases() {
		Generator<IPoint> g = new UniformGenerator();
		IPoint[] points = g.generate(2);
			
		IPoint[] hull = new ConvexHullScan().compute(points);
		DoubleLinkedList<IPoint> hull2 = new ConvexHullScanLinkedList().compute(points);
		
		assertEquals (hull[0], hull2.first().value());
		assertEquals (hull[1], hull2.first().next().value());
	}
	
	@Test
	public void testCompare() {
		int idx = 10;
		
		int NUM = 3;  // was 6
		Generator<IPoint> g = new UniformGenerator();
		System.out.println ("Size of random convex hull.");
		
		System.out.println ("size,numEliminated,finalSize");
		for (int i = 1; i < NUM; i++) {
			IPoint[] points = g.generate(idx);
			
			int num = points.length;
			points = AklToussaint.reduce(points);
			num = num - points.length;
			
			System.gc();
			long now1 = System.currentTimeMillis();
			IPoint[] hull = new ConvexHullScan().compute(points);
			long now2 = System.currentTimeMillis();
			
			System.gc();
			long now3 = System.currentTimeMillis();
			DoubleLinkedList<IPoint> hull2 = new ConvexHullScanLinkedList().compute(points);
			long now4 = System.currentTimeMillis();
			
			DoubleNode<IPoint> node = hull2.first();
			for (int j = 0; j < hull.length; j++) {
				assertEquals (hull[j], node.value());
				node = node.next();
			}
			
			System.out.println (idx + "," + (now2-now1) + "," + (now4-now3));
			
			idx *= 10;
		}
	}
}
