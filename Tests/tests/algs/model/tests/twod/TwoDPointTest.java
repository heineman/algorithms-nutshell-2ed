package algs.model.tests.twod;

import org.junit.Test;

import algs.model.IMultiPoint;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDPoint;
import junit.framework.TestCase;

public class TwoDPointTest extends TestCase {

	@Test
	public void testFailureOfEqualsNonStandard() {
		IMultiPoint imp = new Hyperpoint("2,3,4");
		IMultiPoint obj = new IMultiPoint() {

			Hyperpoint inner = new Hyperpoint ("4,7,5");  /** DIFFERENT **/
			
			public int dimensionality() { return 3; }

			public double distance(IMultiPoint imp) { return inner.distance(imp); }

			public double getCoordinate(int dx) { return inner.getCoordinate(dx); }

			public double[] raw() { return inner.raw(); }
		};
		
		// Not the same, which can be detected even though not same class.
		assertFalse (imp.equals(obj));
	}

	
	@Test
	public void testFailure() {
		TwoDPoint tp = new TwoDPoint("918273.23423,827.1232");
		IMultiPoint imp = new Hyperpoint("2,3,4");
		try {
			tp.distance(imp);
			fail ("failed to check distance on invalid dimensions");
		} catch (IllegalArgumentException iae) {
			// ok.
		}
	}
	
	@Test
	public void testTwoDPointTokenizer() {
		TwoDPoint tp = new TwoDPoint("918273.23423,827.1232");
		assertEquals (tp.getX(), 918273.23423);
		assertEquals (tp.getY(), 827.1232);
	}
	
	@Test
	public void testTwoDPoint() {
		TwoDPoint tp = new TwoDPoint(9,7);
		TwoDPoint tp2 = new TwoDPoint(tp);
		
		assertEquals (9.0, tp2.getX());
		assertEquals (7.0, tp2.getY());
		
		assertEquals ("9.0,7.0", tp.toString());
		assertEquals (2, tp.dimensionality());
		assertEquals (9.0, tp.getCoordinate(1));
		assertEquals (7.0, tp.getCoordinate(2));
		
		// who cares about value, but verify is the same, if ==
		assertEquals (tp.hashCode(), tp2.hashCode());
	}
	
	@Test
	public void testDistance () {
		TwoDPoint tp = new TwoDPoint(0,0);
		TwoDPoint tp2 = new TwoDPoint(3,4);
		
		assertEquals (5.0, tp.distance(tp2));
	}
	
	@Test
	public void testEquals () {
		TwoDPoint tp = new TwoDPoint (9,7);
		TwoDPoint tp2 = new TwoDPoint (9,7);
		
		assertEquals (tp, tp2);
		assertFalse (tp.equals(null));
		assertFalse (tp.equals (new String ("SLKDJ")));
	}

	@Test
	public void testComparison () {
		TwoDPoint tp = new TwoDPoint (9,7);
		TwoDPoint tp2 = new TwoDPoint (9,7);
		TwoDPoint tp3 = new TwoDPoint (9,11);
		TwoDPoint tp4 = new TwoDPoint (3,7);
		
		assertEquals (0, tp.compareTo(tp2));
		assertEquals (-1, tp.compareTo(tp3));
		assertEquals (1, tp3.compareTo(tp));
		assertEquals (1, tp.compareTo(tp4));
		assertEquals (-1, tp4.compareTo(tp));
		
	}
}
