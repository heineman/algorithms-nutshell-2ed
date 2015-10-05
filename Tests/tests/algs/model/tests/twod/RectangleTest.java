package algs.model.tests.twod;

import org.junit.Test;

import algs.model.twod.TwoDRectangle;

import junit.framework.TestCase;


public class RectangleTest extends TestCase {

	@Test
	public void testBasics() {
		TwoDRectangle r1 = new TwoDRectangle(2,3, 10,11);
		assertTrue (r1.equals(r1));
		
		TwoDRectangle r2 = new TwoDRectangle(2,-11, 10,11);
		TwoDRectangle r3 = new TwoDRectangle(2,3, -11,11);
		TwoDRectangle r4 = new TwoDRectangle(1,3, 10,-11);
		assertFalse (r1.equals(r2));
		assertFalse (r1.equals(r3));
		assertFalse (r1.equals(r4));
	}
	
	@Test
	public void testBasicContainment() {
		TwoDRectangle r1 = new TwoDRectangle(2,3, 10,11);
		
		TwoDRectangle r2 = new TwoDRectangle(5,5, 8,9);
		
		assertTrue (r1.contains(r2));
		assertFalse (r2.contains(r1));
		
	}
}
