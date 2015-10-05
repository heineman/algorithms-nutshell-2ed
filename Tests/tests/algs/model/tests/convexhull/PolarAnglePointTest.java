package algs.model.tests.convexhull;


import org.junit.Test;

import algs.model.problems.convexhull.graham.PolarAnglePoint;
import algs.model.twod.TwoDPoint;
import junit.framework.TestCase;

public class PolarAnglePointTest extends TestCase {

	// PolarAnglePoint
	@Test
	public void testPartialHullBoundaries() {
		TwoDPoint pt = new TwoDPoint(10, 20);
		PolarAnglePoint pap = new PolarAnglePoint(pt);
		assertEquals (10.0, pap.x);
		assertEquals (20.0, pap.y);
	}
}
