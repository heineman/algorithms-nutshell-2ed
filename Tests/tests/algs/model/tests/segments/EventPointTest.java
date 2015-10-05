package algs.model.tests.segments;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.problems.segmentIntersection.EventPoint;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

public class EventPointTest extends TestCase {

	@Test
	public void testCompare() {
		// ensure sweep is proper.
		EventPoint ep1 = new EventPoint(new TwoDPoint(10,20));
		EventPoint ep2 = new EventPoint(new TwoDPoint(10,20));
		EventPoint ep3 = new EventPoint(new TwoDPoint(10,6));
		EventPoint ep4 = new EventPoint(new TwoDPoint(15,6));
		
		// per contract of comparator.
		assertEquals (ep1.compare(ep1,ep2) == 0,ep1.equals(ep2));
		assertEquals (0, ep1.compare(ep2,ep1));
		
		// sweeping downward, ep1 comes before ep3
		assertTrue (ep1.compare(ep1, ep3) < 0);
		assertTrue (ep1.compare(ep3, ep1) > 0);
		
		// sweeping downward, since ep3 and ep4 are on same horizontal line
		// we check x coordinates
		assertTrue (ep3.compare(ep3, ep4) < 0);
		assertTrue (ep4.compare(ep4, ep3) > 0);
	}

	@Test
	public void testErrorDetection() {
		EventPoint ep1 = new EventPoint(new TwoDPoint(10,20));
		
		try {
			ep1.addUpperLineSegment(new TwoDLineSegment(9, 9, 11, 11));
			fail ("Must detect improper additions to upper line segment.");
		} catch (IllegalArgumentException iae) {
			// success
		}
		
		try {
			ep1.addLowerLineSegment(new TwoDLineSegment(9, 9, 11, 11));
			fail ("Must detect improper additions to lower line segment.");
		} catch (IllegalArgumentException iae) {
			// success
		}

	}
	
	@Test
	public void testEqualsObject() {
		EventPoint ep1 = new EventPoint(new TwoDPoint(10,20));
		EventPoint ep2 = new EventPoint(new TwoDPoint(10,20));
		EventPoint ep3 = new EventPoint(new TwoDPoint(10,6));
		
		assertEquals (ep1, ep2);
		assertEquals (ep2, ep1);
		assertFalse (ep1.equals(ep3));
		assertFalse (ep3.equals(ep1));
		
		assertFalse (ep1.equals(null));
		assertFalse (ep1.equals("slkdjl"));
	}

}
