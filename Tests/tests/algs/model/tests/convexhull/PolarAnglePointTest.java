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
		
		// compareTo and toString
		double max = Double.MAX_VALUE;
		assertEquals ("[10.0,20.0 @ " + max + "]", pap.toString());
		
		// note angles are externally set, which makes it hard to test but eas(ier) to write graham scan
		TwoDPoint pt2 = new TwoDPoint(15, 5);
		PolarAnglePoint pap2 = new PolarAnglePoint(pt);
		
		// until you change angles, they are the same.
		assertEquals (0, pap.compareTo(pap2));
			
		pap.angle = Math.PI/2;
		pap2.angle = Math.PI;
		
		assertEquals (1, pap.compareTo(pap2));
		
		pap.angle = Math.PI;
		pap2.angle = Math.PI/2;
		
		assertEquals (-1, pap.compareTo(pap2));
		
	}
}
