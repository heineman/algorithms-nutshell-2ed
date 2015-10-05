package algs.model.tests.interval;

import org.junit.Test;

import algs.model.interval.DiscreteInterval;

import junit.framework.TestCase;

public class IntervalCaseTest extends TestCase {

	/**
	 * Test core methods of DiscreteInterval
	 */
	@Test
	public void testCoreMethods () {
		DiscreteInterval di = new DiscreteInterval(2, 9);
		DiscreteInterval di2 = new DiscreteInterval(2, 9);
		
		assertEquals (di.hashCode(), di2.hashCode());
		
		assertTrue (di.equals(di2));
		assertTrue (di.toTheLeft(1));  // boundary - 1
		assertTrue (di.intersects(2));  // boundary 
		
		assertTrue (di.toTheRight(9)); // boundary
		assertTrue (di.toTheRight(10)); // boundary + 1		
		
		assertEquals ("[2,9)", di.toString());
	}
	
	/**
	 * Test standard object methods.
	 */
	@Test
	public void testStandardMethods () {
		DiscreteInterval di = new DiscreteInterval(2, 9);
		DiscreteInterval di2 = new DiscreteInterval(2, 9);
		
		assertEquals (di, di2);
		assertFalse (di.equals (new String ("test")));
	}
	
	/**
	 * Test the bad things that can go wrong.
	 */
	@Test
	public void testExceptions () {
		try {
			new DiscreteInterval(2, 2);
			fail ("DiscreteInterval fails to throw exception on illegal interval.");
		} catch (IllegalArgumentException iae) {
			
		}

		// validate that it works properly
		new DiscreteInterval(2, 9);
	
		try {
			new DiscreteInterval(5, 2);
			fail ("DiscreteInterval fails to throw exception on illegal interval.");
		} catch (IllegalArgumentException iae) {
		}
	}
	
	/**
	 * Test core methods of DiscreteInterval
	 */
	@Test
	public void testFinal () {
		DiscreteInterval di = new DiscreteInterval(2, 9);
		
		assertFalse (di.equals (null));
		assertFalse (di.equals ("GARBAGE"));
		
		assertTrue (di.toTheLeft(-8));
		assertFalse (di.toTheLeft(7));
		assertTrue (di.toTheRight(23));
		assertFalse (di.toTheRight(7));
		assertTrue (di.intersects(7));
		assertFalse (di.intersects(-8));
		assertFalse (di.intersects(23));
	}
}
