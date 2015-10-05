package algs.model.tests.segments;


import java.util.Hashtable;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.segments.UniformGenerator;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/** 
 * Test sample use of line segments.
 * 
 * @author George Heineman
 */
public class SegmentTest extends TestCase {
	
	@Test
	public void testHorizontal() {
		TwoDPoint s = new TwoDPoint (20, 20);
		TwoDPoint e = new TwoDPoint (50, 20);
		TwoDLineSegment tds = new TwoDLineSegment (s, e);
		assertEquals (s, tds.getStart());
		assertEquals (e, tds.getEnd());
		
		// horizontal is ordered left-to-right
		tds = new TwoDLineSegment (e, s);
		assertEquals (s, tds.getStart());
		assertEquals (e, tds.getEnd());
		
		// slope is zero; and y-intercept.
		assertEquals (0.0, tds.slope());
		assertEquals (20.0, tds.yIntercept());
		
	}
	
	@Test
	public void testIntersections() {
		TwoDPoint s = new TwoDPoint (20, 20);
		TwoDPoint e1 = new TwoDPoint (0, 0);
		TwoDPoint e2 = new TwoDPoint (20, 0);
		TwoDPoint e3 = new TwoDPoint (40, 0);
		TwoDPoint e4 = new TwoDPoint (40, 20);
		TwoDPoint e5 = new TwoDPoint (0, 40);
		TwoDPoint miss = new TwoDPoint (99,99);
		
		TwoDLineSegment tds1 = new TwoDLineSegment (s, e1);
		assertEquals (1, tds1.sign());
		assertTrue (tds1.intersection(s));
		assertTrue (tds1.intersection(e1));
		
		assertFalse (tds1.intersection(new TwoDPoint (-10, -10)));
		assertFalse (tds1.intersection(new TwoDPoint (40, 40)));
		assertFalse (tds1.intersection(miss));
		
		TwoDLineSegment tds2 = new TwoDLineSegment (s, e2);
		assertEquals (1, tds2.sign());
		assertTrue (tds2.intersection(s));
		assertTrue (tds2.intersection(e2));

		assertFalse (tds1.intersection(new TwoDPoint (20, 40)));
		assertFalse (tds1.intersection(new TwoDPoint (20, -30)));
		assertFalse (tds2.intersection(miss));

		TwoDLineSegment tds3 = new TwoDLineSegment (s, e3);
		assertEquals (-1, tds3.sign());
		assertTrue (tds3.intersection(s));
		assertTrue (tds3.intersection(e3));

		assertFalse (tds1.intersection(new TwoDPoint (0, 40)));
		assertFalse (tds1.intersection(new TwoDPoint (50, -10)));
		assertFalse (tds3.intersection(miss));

		TwoDLineSegment tds4 = new TwoDLineSegment (s, e4);
		assertEquals (1, tds4.sign());
		assertTrue (tds4.intersection(s));
		assertTrue (tds4.intersection(e4));
		
		assertFalse (tds1.intersection(new TwoDPoint (1, 20)));
		assertFalse (tds1.intersection(new TwoDPoint (70, 20)));
		assertFalse (tds4.intersection(miss));
		
		TwoDLineSegment tds5 = new TwoDLineSegment (s, e5);
		assertEquals (-1, tds5.sign());
		assertFalse (tds5.intersection(new TwoDPoint (-1, 41)));
		assertFalse (tds5.intersection(new TwoDPoint (21, 19)));
		
		assertTrue (tds5.intersection(s));
		assertTrue (tds5.intersection(e5));
		assertFalse (tds5.intersection(miss));
	}
	
	@Test
	public void testSigns() {
		TwoDPoint s = new TwoDPoint (20, 20);
		TwoDPoint e = new TwoDPoint (50, 50);
		TwoDPoint m = new TwoDPoint (100, 20);
		
		TwoDLineSegment tds1 = new TwoDLineSegment (s, e);
		TwoDLineSegment tds2 = new TwoDLineSegment (m, e);
		
		assertEquals (1, tds1.sign());
		assertEquals (-1, tds2.sign());
	}
	
	@Test
	public void testExtras() {
		TwoDPoint s = new TwoDPoint (20, 20);
		TwoDPoint e = new TwoDPoint (50, 20);
		TwoDLineSegment tds = new TwoDLineSegment (s, e);
		assertFalse (tds.isPoint());
		assertTrue (new TwoDLineSegment (s,s).isPoint());
		assertEquals (2, tds.dimensionality());
		
		assertFalse (tds.equals (null));
		assertFalse (tds.equals ("garbage"));
		
		// vertical slope is special; also, ordered from top to bottom.
		tds = new TwoDLineSegment (s, new TwoDPoint (20, 100));
		assertEquals (s, tds.getEnd());
		
		// slope is zero; and y-intercept.
		assertEquals (Double.NaN, tds.slope());
		assertEquals (Double.NaN, tds.yIntercept());
	}
	
	/**
	 * This one had caused problems with multiple found.
	 */
		@Test
		public void testAnother () {
			LineSweep dba = new LineSweep();
			
		
			// Test known intersection
			ILineSegment[] segments = new ILineSegment[]{
				new TwoDLineSegment(new TwoDPoint(81.86460175631862,30.29263943376345),
									new TwoDPoint(65.20602044047269,18.585459953643713)),
				new TwoDLineSegment(new TwoDPoint(85.63750993043222,25.535170832101584),
									new TwoDPoint(66.8981501827304,14.52047110969381)),
				new TwoDLineSegment(new TwoDPoint(78.92359451322304,20.34081835588905),
									new TwoDPoint(59.73377023708525,18.4610494103152)),
			};
			
			Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);
			
			for (IPoint ip : res.keySet()) {
				List<ILineSegment> ilss = res.get(ip);
				System.out.println (ip);
				for (ILineSegment ils : ilss) {
					System.out.println ("  " + ils);
				}
				System.out.println();
			}
			
			assertEquals (2, res.size());
			
		}
	
	/**
	 * Sample trial.
	 */
	@Test
	public void testStructure () {
		LineSweep dba = new LineSweep();
		
		// choose width of .2 (1/5)
		UniformGenerator generator = new UniformGenerator(5);
		
		assertTrue (generator.parameters() != null);
		
		// create 10 lines.
		ILineSegment[] segments = generator.generate(40);
		
		// Test known intersection
		segments = new ILineSegment[]{
			new TwoDLineSegment(new TwoDPoint(10,10), new TwoDPoint(20,20)),
			new TwoDLineSegment(new TwoDPoint(15,2), new TwoDPoint(18,30)),
			new TwoDLineSegment(new TwoDPoint(2,7), new TwoDPoint(15,8)),
			new TwoDLineSegment(new TwoDPoint(4,20), new TwoDPoint(12,-17)),
		};
		
		Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);
		
		for (IPoint ip : res.keySet()) {
			List<ILineSegment> ilss = res.get(ip);
			System.out.println (ip);
			for (ILineSegment ils : ilss) {
				System.out.println ("  " + ils);
			}
			System.out.println();
		}
		
		assertEquals (2, res.size());
		
		// validate construction.
		Generator<ILineSegment> g2 = generator.construct(new String[]{"5"});
		assertTrue (g2 != null);
	}
	
	/**
	 * Investigate better circumstances for updating event queue.
	 */
		@Test
		public void testUpdateQueue () {
			LineSweep dba = new LineSweep();
			
			// Test known intersection
			ILineSegment[] segments = new ILineSegment[]{
				new TwoDLineSegment(new TwoDPoint(-2,14), new TwoDPoint (2, -14)),
				new TwoDLineSegment(new TwoDPoint(-4,13), new TwoDPoint (4, -13)),
				new TwoDLineSegment(new TwoDPoint(-6,12), new TwoDPoint (6, -12)),
				new TwoDLineSegment(new TwoDPoint(-8,11), new TwoDPoint (8, -11)),
				
				// the neighbors to left will include all of the above. The neighbors
				// to the right will include all of the below.
				new TwoDLineSegment(new TwoDPoint (6,0), new TwoDPoint(6, -20)),

				new TwoDLineSegment(new TwoDPoint(11,2), new TwoDPoint (2,-4)),
				new TwoDLineSegment(new TwoDPoint(10,2), new TwoDPoint (1,-7)),
				new TwoDLineSegment(new TwoDPoint(9,2), new TwoDPoint (5,-6)),
			};
			
			Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);
			System.out.println ("new set");
			for (IPoint ip : res.keySet()) {
				List<ILineSegment> ilss = res.get(ip);
				System.out.println (ip);
				for (ILineSegment ils : ilss) {
					System.out.println ("  " + ils);
				}
				System.out.println();
			}
			
			assertEquals (13, res.size());
			
		}
	
}
