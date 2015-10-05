package algs.model.tests.segments;

import java.util.Hashtable;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;

import junit.framework.TestCase;

public class FinalChallengeTest extends TestCase {

	/**
	 * This detects unexpected problem with floating point computations.
	 */
	@Test
	public void testNonZeroError() {
		LineSweep dba = new LineSweep();
		
		// Note that L1 and L2 intersect at point p.
		// 
		// However, Linesweep showed a problem deep in the sweep when it checks to see
		// if the line L1 is to the right of the point 'p'. The resulting floating-point
		// computation is a value that is on the order of 10^-15 which places it outside
		// of the range for consideration with the FloatingPoint values.
		// 
		// So while there should be two detected intersections, LineSweep fails to find either one.
		// What is the lesson to be drawn? Be wary of floating point value computations
		ILineSegment[] segments = new ILineSegment[] {
		/* L1 */	new TwoDLineSegment(424.2213883396885,83.64382123041435,430.21898827125784,83.4741301556963),
		/* L2 */	new TwoDLineSegment(424.27530553919314,85.91543442004598,424.2754719964361,79.91543442235498),
		/* L3 */	new TwoDLineSegment(429.8421554463252,85.39816284280639,424.56541286695244,82.54229384177802) 
		};
		 
		Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);
		assertEquals (0, res.size());

	}
}
