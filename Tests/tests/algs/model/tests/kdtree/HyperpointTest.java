package algs.model.tests.kdtree;


import junit.framework.TestCase;

import org.junit.Test;

import algs.model.FloatingPoint;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDPoint;

/** Test Hyperpoint. */
public class HyperpointTest extends TestCase {
	
	@Test
	public void testOther() {
		// mixed dimensions can't compare.
		Hyperpoint hp = new Hyperpoint ("3,4,5,6");
		Hyperpoint hp2 = new Hyperpoint (new double[]{1,4});
		assertFalse (hp.equals(hp2));

		// non Hyperpoint but IMultiPoint
		TwoDPoint tdp = new TwoDPoint(2,8);
		hp = new Hyperpoint("2,8");
		assertEquals (hp, tdp);
		
		// validate raw
		assertEquals(2.0,hp.raw()[0]);
		assertEquals(8.0,hp.raw()[1]);
		
		assertEquals (hp2.distance(hp), hp2.distance(tdp));
	}
	
	/**
	 * Validate distance.
	 */
	@Test
	public void testDistance () {
		Hyperpoint hp = new Hyperpoint ("3,4,5,6");
		Hyperpoint hp2 = new Hyperpoint ("3,4,5,6");

		assertEquals (hp, hp2);
		assertEquals (hp.hashCode(), hp2.hashCode());
		
		assertEquals (0.0, FloatingPoint.value(hp.distance(hp2)));
		
		// known value. This is based on 8^2 + 15^2 = 17^2
		hp = new Hyperpoint ("10,10,4,3,4,4,4,4");
		hp2 = new Hyperpoint ("0,0,0,0,0,0,0,0");
		assertEquals (17.0, FloatingPoint.value(hp.distance(hp2)));
		
		try {
			hp.distance(new Hyperpoint ("1,2,3"));
			fail ("Fails to detect invalid distance comparison");
		} catch (IllegalArgumentException iae) {
			
		}
	}
	
	
	
	/**
	 * Validate Hyperpoint core methods.
	 */
	@Test
	public void testHyperpoint () {
		Hyperpoint hp = new Hyperpoint ("3,4,5,6");
		Hyperpoint hp2 = new Hyperpoint ("3,4,5,6");
		Hyperpoint hp3 = new Hyperpoint ("3,4,4,6");
		assertEquals (4, hp.dimensionality());

		assertEquals ("3.0,4.0,5.0,6.0", hp.toString());
		assertFalse (hp.equals(null));
		assertFalse (hp.equals ("ksjdhjk"));
		assertTrue (hp.equals (hp2));
		assertFalse (hp2.equals (hp3));
		
		Hyperpoint hp4 = new Hyperpoint (hp2);
		assertEquals (hp2, hp4);
	}
	
	
}
