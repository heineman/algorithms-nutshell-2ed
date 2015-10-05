package algs.model.tests.segments;

import java.util.Hashtable;

import org.junit.Test;

import junit.framework.TestCase;

import algs.model.FloatingPoint;
import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;


public class RegularNGonTest extends TestCase {

	int intersections[] = new int[] {
			0,  /** 0 points. */
			0,  /** 1 point. */
			0,  /** 2 points. */
			0,  /** 3 points. */
			1,  /** 4 points. */
			5,
			13,
			35,
			49,
			126,
			161,
			330,
			301,
			715,
			757,
			1365,
			1377,
			2380,
			1837,
			3876,
			3841,
			5985,
			5941,
			8855,
			7297,
			12650,
			12481,
			17550,
			17249,
			23751,
			16801
	};
	
	/**
	 * Delta function from http://math.berkeley.edu/~poonen/papers/ngon.pdf
	 * @param m
	 * @param n
	 */
	public int delta (int m, double dn) {
		int n = (int) dn;
		if (n % m == 0) { return 1; }
		return 0;
	}
	
	public long count (double n) {
		double ct = n*(n-1)*(n-2)*(n-3)/24;
		
		ct = ct + (-5*n*n*n + 45*n*n - 70*n + 24)/24*delta(2,n);
		ct = ct - (3*n/2)*delta(4,n) + (-45*n*n + 262*n)/6*delta(6,n);
		ct = ct + 42*n*delta(12,n) + 60*n*delta(18,n) + 35*n*delta(24,n);
		ct = ct - 38*n*delta(30,n) - 82*n*delta(42,n) - 330*n*delta(60, n);
		ct = ct - 144*n*delta(84,n) - 96*n*delta(90,n) - 144*n*delta(120,n);
		ct = ct - 96*n*delta(210, n);
		return (long) ct;
	}
	
	/** Validate the equation, as provided. */
	@Test
	public void testCount() {
		for (int i = 3; i < intersections.length; i++) {
			assertEquals (intersections[i], count(i));
		}
	}
	
	public void testRegularKGon() {
		LineSweep dba = new LineSweep();
		
		int MAX = 30;
		
		// generate k-gon [http://math.berkeley.edu/~poonen/papers/ngon.pdf]
		for (int k = 4; k <= MAX; k++) {
			System.out.println (k);
			IPoint[] points = new IPoint[k];
			
			for(int i=0; i<k; i++) {
				points[i] = new TwoDPoint(FloatingPoint.value(Math.cos(2*i*Math.PI/k)),
										  FloatingPoint.value(Math.sin(2*i*Math.PI/k)));
		     }
			
			// now generate every possible pair.
			ILineSegment[] segments = new ILineSegment[k*(k-1)/2];
			int idx = 0;
			for (int i = 0; i < k-1; i++) {
				for (int j = i+1; j < k; j++) {
					segments[idx++] = new TwoDLineSegment(points[i], points[j]);
				}
			}
			
			Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);

			// Note that the intersections on the n-Gon in the 'count' equation above do not include the points 
			// on the edge, so we simply decrement ct by k to achieve the expected value.
			int ct = res.size() - k;
			
			System.out.println (k + "-gon has " + ct + " intersections from " + segments.length + " segments.");
			assertEquals (intersections[k], ct);
		}
	}
}
