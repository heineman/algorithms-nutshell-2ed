package algs.model.tests.data;

import java.util.Hashtable;

import org.junit.Test;

import algs.model.FloatingPoint;
import algs.model.ICircle;
import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.circles.UniformGenerator;
import algs.model.data.points.UniqueGenerator;
import algs.model.data.points.UnusualGenerator;
import algs.model.data.segments.DoubleGenerator;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;

public class MoreGeneratorsTest extends TestCase {

	@Test
	public void testUnusualGenerator() {
		UnusualGenerator ug = new UnusualGenerator(20);
		assertEquals (20.0, ug.maxValue);
		
		IPoint[] pts = ug.generate(103);
		
		// not much we can test about this unusual generator, really.
		assertEquals (103, pts.length);
		
		// Consider flexible constructors
		assertTrue (ug.parameters() != null);
		UnusualGenerator uug;
		assertTrue ((uug = (UnusualGenerator) ug.construct(new String[]{"20"})) != null);
		assertEquals (20.0, uug.maxValue);
		
		// non null
		assertTrue (ug.toString() != null);
	}
	
	@Test
	public void testCircleUniformGenerator() {
		UniformGenerator ug = new UniformGenerator(20);
		assertEquals (20.0, ug.radius);
		ICircle[] circles = ug.generate(10);
		assertEquals (10, circles.length);
		
		for (ICircle c : circles) {
			assertEquals (20.0, c.getRadius());
		}

		// Consider flexible constructors
		String[]parms = ug.parameters(); 
		assertTrue (parms != null);
		assertEquals (1, parms.length);
		
		UniformGenerator uug;
		assertTrue ((uug = (UniformGenerator) ug.construct(new String[]{"20"})) != null);
		assertEquals (20.0, uug.radius);

		// non null
		assertTrue (ug.toString() != null);
	}
	
	@Test
	public void testUniqueGenerator() {
		UniqueGenerator ug = new UniqueGenerator();
		
		IPoint[] points = ug.generate(1000);
		assertEquals (1000, points.length);
		
		// quick and dirty check for uniqueness AND integer-ness :)
		Hashtable<Double,Boolean> ht = new Hashtable<Double,Boolean>();
		for (IPoint p : points) {
			assertFalse (ht.containsKey(p.getX()));
			ht.put(p.getX(), true);
			assertFalse (ht.containsKey(p.getY()));
			ht.put(p.getY(), true);
		
			// make sure x is integral
			int px = (int) p.getX();
			Double d = p.getX();
			assertEquals (0, d.compareTo((double) px));
	
			int py = (int) p.getY();
			d = p.getY();
			assertEquals (0, d.compareTo((double) py));
		}

		// Consider flexible constructors
		assertTrue (ug.parameters() != null);
		assertTrue (ug.construct(new String[]{}) != null);
		
		// non null
		assertTrue (ug.toString() != null);
	}
	
	@Test
	public void testBulkDoubleGenerator() {
		double sq = 0.7;
		DoubleGenerator ug = null;
		try {
			ug = new DoubleGenerator(sq, 1.2);
			fail ("should detect invalid use of DoubleGenerator");
		} catch (IllegalArgumentException iae) {
			
		}
		
		ug = new DoubleGenerator(sq, sq);
		ILineSegment[] segments = ug.generate(1000);
		assertEquals (1000, segments.length);
		
		int idx = 0;
		for (ILineSegment ils : segments) {
			idx++;
			TwoDPoint p1 = (TwoDPoint) ils.getStart();
			TwoDPoint p2 = (TwoDPoint) ils.getEnd();
			
			assertTrue (p1.getX() >= 0.0);
			assertTrue (p1.getX() <= sq);
			assertTrue (p1.getY() >= 0.0);
			assertTrue (p1.getY() <= sq);
			
			assertTrue (p2.getX() >= 0.0);
			assertTrue (p2.getX() <= sq);
			assertTrue (p2.getY() >= 0.0);
			assertTrue (p2.getY() <= sq);
			
			// any validation on the lengths?
			double dist = p1.distance(p2); 
			assertTrue ("point " + idx + " missed distance :" + dist, FloatingPoint.lesserEquals(dist, sq));
		}
	}
	
	@Test
	public void testDoubleGenerator() {
		DoubleGenerator ug = new DoubleGenerator(100,10);
		assertEquals (100.0, ug.max);
		assertEquals (10.0, ug.length);
		
		ILineSegment[] segments = ug.generate(10);
		assertEquals (10, segments.length);
		
		for (ILineSegment ils : segments) {
			IPoint p1 = ils.getStart();
			IPoint p2 = ils.getEnd();
			
			assertTrue (p1.getX() >= 0);
			assertTrue (p1.getY() >= 0);
			
			assertTrue (p2.getX() >= 0);
			assertTrue (p2.getY() >= 0);
			
			assertTrue (p1.getX() <= 100);
			assertTrue (p1.getY() <= 100);
			
			assertTrue (p2.getX() <= 100);
			assertTrue (p2.getY() <= 100);
		}
		
		// Consider flexible constructors
		assertTrue (ug.parameters() != null);
		DoubleGenerator dg;
		assertTrue ((dg = (DoubleGenerator) ug.construct(new String[]{"20","10"})) != null);
		assertEquals (20.0, dg.max);
		assertEquals (10.0, dg.length);
		
		// non null
		assertTrue (ug.toString() != null);
	}
}
