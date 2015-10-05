package algs.model.tests.fp;

import org.junit.Test;

import algs.model.FloatingPoint;

import junit.framework.TestCase;


public class ExampleFloatingPointTest extends TestCase {

	// sample function to compute x^n
	private double power(double x, long n) { 
		 while (n > 0 && n % 2 == 0) { 
		     x = x*x;
		     n = n/2;
		 } 

		 double u = x;

		 while (true) { 
		     n = n/2;
		     if (n==0) return u;
		     x = x*x;
		     if (n%2 == 1) u = u*x;
		 }
	}

	@Test
	public void testCompleteCoverage() {
		assertTrue (FloatingPoint.same(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse (FloatingPoint.same(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertTrue (FloatingPoint.same(Double.NaN, Double.NaN));
		assertFalse (FloatingPoint.same(Double.NaN, 5.0));
		assertFalse (FloatingPoint.same(2.2, Double.NaN));
		assertFalse (FloatingPoint.same(Double.NEGATIVE_INFINITY, 3.0));
		assertFalse (FloatingPoint.same(5.0, Double.NEGATIVE_INFINITY));
		
		assertTrue (FloatingPoint.greaterEquals(5.0, -2.0));
		assertTrue (FloatingPoint.greaterEquals(5.0, 5.0));
		assertFalse (FloatingPoint.greaterEquals(5.0, 72.0));
		
		assertTrue (FloatingPoint.greater(5.0, -2.0));
		assertFalse (FloatingPoint.greater(5.0, 5.0));
		assertFalse (FloatingPoint.greater(5.0, 72.0));
		
		assertFalse (FloatingPoint.lesser(5.0, -2.0));
		assertFalse (FloatingPoint.lesser(5.0, 5.0));
		assertTrue (FloatingPoint.lesser(5.0, 72.0));		
		
		assertFalse (FloatingPoint.lesserEquals(5.0, -2.0));
		assertTrue (FloatingPoint.lesserEquals(5.0, 5.0));
		assertTrue (FloatingPoint.lesserEquals(5.0, 72.0));	
	}
	
	@Test
	public void testPowerDifferences() {
		double x = 981276349876.293874;
		for (int i = 0; i < 20; i++) {
			double v1 = 1/power (x,i);
			double v2 = power ((1/x), i);
			System.out.println (v1 + "," + v2);
		}
	}
	
	@Test
	public void testEquality() {
		assertTrue (FloatingPoint.same (2.3, 2.3));
		
		// same if less than epsilon, in either direction.
		assertTrue (FloatingPoint.same (100.0, 100.0 - (FloatingPoint.epsilon/10)));
		assertTrue (FloatingPoint.same (100.0, 100.0 + (FloatingPoint.epsilon/10)));
	}
	
	@Test
	public void testSmallFPs() {
		double x, y;
		long bits;
		
		x = 2.0e-267 * 1.0e-260;
		assertEquals ("0.0", Double.toString(x));
		bits = Double.doubleToLongBits(x);
		assertEquals ("0", Long.toHexString(bits));
		
		y = -2.0e-267 * 1.0e-260;
		assertEquals ("-0.0", Double.toString(y));
		bits = Double.doubleToLongBits(y);
		assertEquals ("8000000000000000", Long.toHexString(bits));
		
		x = 0.0 / 0.0;
		assertEquals ("NaN", Double.toString(x));
		bits = Double.doubleToLongBits(x);
		assertEquals ("7ff8000000000000", Long.toHexString(bits));
		
		x = 2.0/3.0;
		assertEquals ("0.6666666666666666", Double.toString(x));
		bits = Double.doubleToLongBits(x);
		assertEquals ("3fe5555555555555", Long.toHexString(bits));
		
		x = 256 * 3.76;
		assertEquals ("962.56", Double.toString(x));
		bits = Double.doubleToLongBits(x);
		assertEquals ("408e147ae147ae14", Long.toHexString(bits));
		
	}
	
	@Test
	public void testConstr() {
		// constructor not meaningful, but without this test case, EclEmma shows 97%
		// coder coverage. Sheesh
		new FloatingPoint();
	}
}
