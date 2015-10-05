package algs.model.tests.convexhull;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.balanced.BalancedTreeAndrew;
import algs.model.problems.convexhull.slowhull.SlowHull;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;
import algs.model.problems.convexhull.heap.HeapAndrew;
public class HullTest extends TestCase {
	
	@Test
	public void testBoundaryCase() {
		HeapAndrew ha = new HeapAndrew();
		TwoDPoint []points = new TwoDPoint[2];
		points[0] = new TwoDPoint(-4,  0); 
		points[1] = new TwoDPoint(0,  4);
		IPoint[]res = ha.compute(points);
		assertEquals (res, points);
		
		BalancedTreeAndrew bta = new BalancedTreeAndrew();
		points = new TwoDPoint[2];
		points[0] = new TwoDPoint(-4,  0); 
		points[1] = new TwoDPoint(0,  4);
		res = bta.compute(points);
		assertEquals (res, points);
	}
	
	// AKL worst case. When intersection is along maximal point, but the 
	// maximal point is not the one being removed (only 0,0 is removed)
	@Test
	public void testAklWorstCase() {
		TwoDPoint []points = new TwoDPoint[5];
		points[0] = new TwoDPoint(-4,  0); 
		points[1] = new TwoDPoint(0,  4);
		points[2] = new TwoDPoint(4, 0);
		points[3] = new TwoDPoint(0, -4);
		points[4] = new TwoDPoint(0, 0);
		
		IPoint[] points2 = AklToussaint.reduce(points);
		assertEquals (4, points2.length);
	
	}
	
	// What happens when maximal DOWN point (.544,.007) is being tested. 
	@Test
	public void testOtherFailedTests() {
		TwoDPoint []points = new TwoDPoint[5];
		points[0] = new TwoDPoint(0.454580464,0.513190837);
		points[1] = new TwoDPoint(0.544987051,0.007670737);
		points[2] = new TwoDPoint(0.757961684,0.792145433);
		points[3] = new TwoDPoint(0.790543079, 0.997729915);
		points[4] = new TwoDPoint(0.971315775, 0.741686663);
		
		IPoint[] hull = new ConvexHullScan().compute(points);

		IPoint[] points2 = AklToussaint.reduce(points);
		IPoint[] hullRegular = new ConvexHullScan().compute(points2);
		
		assertEquals (hull.length, hullRegular.length);
	}

	@Test
	public void testLeftInclusion() {
		TwoDPoint []points = new TwoDPoint[4];
		points[0] = new TwoDPoint(1, 1);
		points[1] = new TwoDPoint(1, 3); 
		points[2] = new TwoDPoint(2, 1);
		points[3] = new TwoDPoint(3, 1);
		assertFalse (SlowHull.internalPoint(points, 0, 1, 2, 3));
		
	}
	
	@Test
	public void testSmallExamples() {
		TwoDPoint []points = new TwoDPoint[4];
		points[0] = new TwoDPoint(1,  10); 
		points[1] = new TwoDPoint(9,  50);
		points[2] = new TwoDPoint(200, 10);
		points[3] = new TwoDPoint(51, 40);
		assertFalse (SlowHull.internalPoint(points, 0, 1, 2, 3));
		
		points[0] = new TwoDPoint (80, 20);  // search for.
		points[1] = new TwoDPoint (79, 42);
		points[2] = new TwoDPoint (6,  28);
		points[3] = new TwoDPoint (87, 10);
		assertTrue (SlowHull.internalPoint(points, 0, 1, 2, 3));
		
		points[0] = new TwoDPoint (80, 20);  // search for.
		points[1] = new TwoDPoint (20, 20);
		points[2] = new TwoDPoint (10, 20);
		points[3] = new TwoDPoint (110, 20);
		assertTrue (SlowHull.internalPoint(points, 0, 1, 2, 3));
	}
	
	@Test
	public void testConstr() {
		// constructor not meaningful, but without this test case, EclEmma shows 99%
		// coder coverage. Sheesh
		new AklToussaint();
	}
}
