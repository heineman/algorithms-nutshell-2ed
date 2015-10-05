package algs.model.tests.kdtree;

import org.junit.Test;

import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;

public class KDExtendedTest extends TestCase {

	@Test
	public void testTwoDPoint () {
		TwoDPoint pt = new TwoDPoint (20, 40);
		assertEquals (2, pt.dimensionality());
		assertEquals (20.0, pt.getCoordinate(1));
		
		assertEquals (40.0, pt.getCoordinate(2));
	}
}
