package algs.model.tests.kdtree;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.nd.Hyperpoint;
import algs.model.nd.Hypercube;

/** Test Hypercube. */
public class HypercubeTest extends TestCase {

	@Test
	public void testIntersection() {
		Hypercube mp = new Hypercube(
				new double[]{1,2},
				new double[]{3,4});
		
		try {
			mp.intersects(new double[]{2,3,5});
			fail ("Should have protected against dimensionality error.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		try {
			Hypercube h3 = new Hypercube(3);
			mp.contains(h3);
			fail ("Should have protected against dimensionality error.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		try {
			Hypercube h3 = new Hypercube(3);
			mp.intersects(h3);
			fail ("Should have protected against dimensionality error.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		assertTrue (mp.intersects(new double[]{2,3}));
		
		assertFalse(mp.intersects(new double[]{0,3}));
		assertFalse(mp.intersects(new double[]{2,5}));
		
	}		

	
	@Test
	public void testMore() {
		Hypercube mp = new Hypercube(
				new double[]{1,2},
				new double[]{3,4});
		
		Hypercube mp2 = new Hypercube(
				new double[]{1,2,3},
				new double[]{3,4,5});
		
		Hypercube mp3 = new Hypercube(
				new double[]{1,2,3},
				new double[]{3,4,6});

		Hypercube mp4 = new Hypercube(
				new double[]{1,2,2},
				new double[]{3,4,5});

		Hypercube mp5 = new Hypercube(
				new double[]{4,2,2},
				new double[]{8,4,5});
		
		assertFalse (mp.equals(mp2));
		assertFalse (mp2.equals(mp3));
		assertFalse (mp2.equals(mp4));
		
		assertTrue (mp2.intersects(mp3));
		assertTrue (mp2.intersects(mp4));
		assertTrue (mp2.intersects(mp5));
		assertFalse (mp2.intersects(new Hypercube(3))); // origin cube
	}
	
	/**
	 * Validate RectangularRegion.
	 */
	@Test
	public void testCube () {
		Hypercube hc = new Hypercube (3);
		assertEquals (3, hc.dimensionality());
		
		double[]a1 = new double[]{1.0};
		double[]a2 = new double[]{1.0, 2.0};
		double[]a3 = new double[]{4.0, 6.0};
		
		try {
			hc = new Hypercube (a1, a2);
			fail ("Hypercube constructor allows different dimension lengths.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		try {
			hc = new Hypercube (a1, a1);
			fail ("Hypercube constructor allows dimensional < 2.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		hc = new Hypercube (a2, a3);
		
		assertFalse (hc.equals (null));
		assertFalse (hc.equals ("skldjlk"));
		
		Hypercube hcx = new Hypercube (a2, a3);
		assertEquals (hc, hcx);
		assertEquals (hc.hashCode(), hcx.hashCode());
		
		assertEquals ("[1.0,4.0 : 2.0,6.0]", hc.toString());
		
		Hyperpoint mp = new Hyperpoint("1.5,1.5");
		assertFalse (hc.intersects(mp));
		
		mp = new Hyperpoint("1.5,2.5");
		assertTrue (hc.intersects(mp));
		
		// check mismatches
		Hyperpoint mp4 = new Hyperpoint("1.5,2.5,8.2,7.3");
		try {
			hc.intersects(mp4);
			fail ("HyperCube fails to detect mismatched dimensionality on intersects.");
		} catch (IllegalArgumentException iae) {
			
		}
	}
}
