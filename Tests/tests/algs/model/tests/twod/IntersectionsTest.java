package algs.model.tests.twod;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;


public class IntersectionsTest extends TestCase {
	
	@Test
	public void testIntersections() {
		// line1+line2 intersect at (0,0)
		// line1+line3 are coincident
		// line2+line3 are non-intersecting
		// line2+line4 are intersecting
		// line3+line4 are parallel
		// line5 intersects NOBODY.
		// line6 intersects NOBODY.
		TwoDLineSegment line1 = new TwoDLineSegment (0,0, 10,10);
		assertEquals (new TwoDPoint(10,10), line1.getStartPoint());   // start is upper 
		assertEquals (new TwoDPoint(0,0), line1.getEndPoint());
		
		TwoDLineSegment line2 = new TwoDLineSegment (0,-10, 0,10);
		TwoDLineSegment line3 = new TwoDLineSegment (5,5, 20,20);
		TwoDLineSegment line4 = new TwoDLineSegment (-3,3, 3,9);
		TwoDLineSegment line5 = new TwoDLineSegment (-30,18, 0,12);
		TwoDLineSegment line6 = new TwoDLineSegment (40,33, 52,12);
		
		assertEquals (ILineSegment.INTERSECTING, line1.intersectionType(line2));
		assertEquals (ILineSegment.INTERSECTING, line2.intersectionType(line1));
		assertEquals (ILineSegment.COINCIDENT, line1.intersectionType(line3));
		assertEquals (ILineSegment.COINCIDENT, line3.intersectionType(line1));
		assertEquals (ILineSegment.NON_INTERSECTING, line2.intersectionType(line3));
		assertEquals (ILineSegment.NON_INTERSECTING, line3.intersectionType(line2));
		assertEquals (ILineSegment.INTERSECTING, line2.intersectionType(line4));
		assertEquals (ILineSegment.INTERSECTING, line4.intersectionType(line2));
		assertEquals (ILineSegment.PARALLEL, line3.intersectionType(line4));
		assertEquals (ILineSegment.PARALLEL, line4.intersectionType(line3));
		
		assertEquals (ILineSegment.NON_INTERSECTING, line5.intersectionType(line1));
		assertEquals (ILineSegment.NON_INTERSECTING, line5.intersectionType(line2));
		assertEquals (ILineSegment.NON_INTERSECTING, line5.intersectionType(line3));
		assertEquals (ILineSegment.NON_INTERSECTING, line5.intersectionType(line4));
		
		assertEquals (ILineSegment.NON_INTERSECTING, line6.intersectionType(line1));
		assertEquals (ILineSegment.NON_INTERSECTING, line6.intersectionType(line2));
		assertEquals (ILineSegment.NON_INTERSECTING, line6.intersectionType(line3));
		assertEquals (ILineSegment.NON_INTERSECTING, line6.intersectionType(line4));
		
		// some intersections.
		assertTrue (line1.intersection(new TwoDPoint(0,0)));
		assertTrue (line1.intersection(new TwoDPoint(10,10)));
		assertTrue (line2.intersection(new TwoDPoint(0,0)));
		
		TwoDLineSegment line7 = new TwoDLineSegment (-3,3, -9,9);
		
		assertTrue (line7.intersection(new TwoDPoint(-4,4)));
		
		
		
	}
}
