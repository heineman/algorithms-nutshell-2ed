package algs.model.tests.convexhull;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.problems.convexhull.slowhull.SlowHull;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;

public class SlowHullTest extends TestCase {

	@Test
	public void testTwoPoints() {
		
		IPoint[] points = new TwoDPoint[]{
				new TwoDPoint(10, 40),
				new TwoDPoint(10, 10),
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (2, hull.length);
		assertEquals (new TwoDPoint(10,40), hull[0]);
		assertEquals (new TwoDPoint(10,10), hull[1]);
	}
	
	@Test
	public void testBox() {
		
		IPoint[] points = new TwoDPoint[]{
				new TwoDPoint(1, 1),
				new TwoDPoint(1, 3),
				new TwoDPoint(2, 1),   // this point threw off the computations before we updated internal check.
				new TwoDPoint(3, 1),
				new TwoDPoint(3, 3),
				
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (4, hull.length);
		assertEquals (new TwoDPoint(1,1), hull[0]);
		assertEquals (new TwoDPoint(1,3), hull[1]);
	}
	
	
	@Test
	public void testVerticalLineFirst() {
		
		IPoint[] points = new TwoDPoint[]{

				new TwoDPoint(10, 10), // min first
				new TwoDPoint(10, 30),
				new TwoDPoint(10, 40),
				new TwoDPoint(10, 20),
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (2, hull.length);
		assertEquals (new TwoDPoint(10,10), hull[0]);
		assertEquals (new TwoDPoint(10,40), hull[1]);
	}
	
	@Test
	public void testVerticalLine() {
		
		IPoint[] points = new TwoDPoint[]{
				new TwoDPoint(10, 30),
				new TwoDPoint(10, 40),
				new TwoDPoint(10, 20),
				new TwoDPoint(10, 10), // min not first
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (2, hull.length);
		assertEquals (new TwoDPoint(10,10), hull[0]);
		assertEquals (new TwoDPoint(10,40), hull[1]);
	}
	
	@Test
	public void testSlowHull() {
		TwoDPoint p1,p2,p3,p4;
		IPoint[] points = new TwoDPoint[]{
				p3=new TwoDPoint(20, 10),
				p2=new TwoDPoint(13, 40),
				p4=new TwoDPoint(7, -2),
				p1=new TwoDPoint(5, 20),
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (4, hull.length);
		assertEquals (p1, hull[0]);
		assertEquals (p2, hull[1]);
		assertEquals (p3, hull[2]);
		assertEquals (p4, hull[3]);
	}
	
	@Test
	public void testHorizontalLine() {
		
		IPoint[] points = new TwoDPoint[]{
				new TwoDPoint(10, 10),
				new TwoDPoint(40, 10),
				new TwoDPoint(30, 10),
				new TwoDPoint(20, 10),
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (2, hull.length);
		assertEquals (new TwoDPoint(10,10), hull[0]);
		assertEquals (new TwoDPoint(40,10), hull[1]);
	}
	
	@Test
	public void testDiagonalLine() {
		
		IPoint[] points = new TwoDPoint[]{
				new TwoDPoint(30, 30),
				new TwoDPoint(10, 10),   // min and max are neither first nor last
				new TwoDPoint(50, 50),
				new TwoDPoint(20, 20),
		};
		
		IPoint hull[]= new SlowHull().compute(points);
		assertEquals (2, hull.length);
		assertEquals (new TwoDPoint(10,10), hull[0]);
		assertEquals (new TwoDPoint(50,50), hull[1]);
	}
}
