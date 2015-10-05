package algs.model.tests.data;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.twod.TwoDCircle;
import algs.model.twod.TwoDPoint;
import algs.model.twod.TwoDRectangle;
import junit.framework.TestCase;

public class CatchAllTest extends TestCase {
	
	@Test
	public void testCircles() {
		TwoDCircle c = new TwoDCircle(3, 4, 9.5);
		assertEquals (9.5, c.getRadius());
		assertEquals (4.0, c.getY());
		assertEquals (3.0, c.getX());
		assertEquals (new TwoDPoint(3,4), c.getOrigin());
		TwoDCircle d = new TwoDCircle (3, 4, 9.5);
		TwoDCircle e = new TwoDCircle (3, 4, 10);
		assertEquals (c,d);
		assertEquals (c.hashCode(), d.hashCode());
		assertFalse (c.equals (e));
		assertTrue (c.toString() != null);
		assertFalse (c.equals(null));
		assertFalse (c.equals ("sds"));
		
		assertEquals (new TwoDRectangle (-7, -6, 13, 14), e.boundingRectangle());
	}
	
	@Test
	public void testXYSorter() {
		// same
		assertEquals (0, 
				IPoint.xy_sorter.compare(new TwoDPoint(3,4),
										 new TwoDPoint(3,4)));
		
		// sort by X, then by Y
		assertTrue (IPoint.xy_sorter.compare(new TwoDPoint(3,4),
										 new TwoDPoint(6,2)) 
					< 0);

		// sort by X, then by Y
		assertTrue (IPoint.xy_sorter.compare(new TwoDPoint(3,4),
										 new TwoDPoint(3,2)) 
					> 0);

		
		// deal with null values.
		assertTrue (IPoint.xy_sorter.compare(new TwoDPoint(3,4), null) 	> 0);
		assertTrue (IPoint.xy_sorter.compare(null, new TwoDPoint(8,8))  < 0);
		assertTrue (IPoint.xy_sorter.compare(null, null)  == 0);

	}
}
