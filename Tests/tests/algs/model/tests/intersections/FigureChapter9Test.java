package algs.model.tests.intersections;

import java.util.Hashtable;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.LineSegmentPair;
import algs.model.problems.segmentIntersection.linkedlist.LineSweep;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;


public class FigureChapter9Test extends TestCase {

	@Test
	public void testMultipleRecords() {
		BruteForceAlgorithm dba2 = new BruteForceAlgorithm();
		
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment(-5, -5, 5, 5),
				new TwoDLineSegment(0, -2, 0, 2),
				new TwoDLineSegment(-3, 3, 3, -3)
		};
		
		// all at (0,0)
		Hashtable<IPoint,List<ILineSegment>> res = dba2.intersections(segments);
		assertEquals (1, res.size());
		assertEquals (new TwoDPoint(0,0), res.keySet().iterator().next());
	}
	
	@Test
	public void testThis() {
		LineSweep dba1 = new LineSweep();
		BruteForceAlgorithm dba2 = new BruteForceAlgorithm();
		
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment(0,3,0,13),
				new TwoDLineSegment(0,13,12,1),
				new TwoDLineSegment(0,3,8,7),
				new TwoDLineSegment(8,7,13,2),
				new TwoDLineSegment(9,0,12,9),
				new TwoDLineSegment(0,3,12,3)
		};

		Hashtable<IPoint,List<ILineSegment>> res = dba1.intersections(segments);
		for (IPoint ip : res.keySet()) {
			System.out.println (ip + ":");
			List<ILineSegment> ls = res.get(ip);
			for (ILineSegment ils: ls) {
				System.out.println ("  " + ils);
			}
		}
		long time1 = dba1.time();
			
		/* Hashtable<IPoint, ILineSegment[]> res2 = */ dba2.intersections(segments);
		long time2 = dba2.time();
			
		System.out.println (res.size() + "," + time1 + "," + time2);
			
	}
	
	@Test
	public void testLineSegmentPair() {
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment(0,3,0,13),
				new TwoDLineSegment(0,13,12,1),
				new TwoDLineSegment(0,11,12,1),
		};
		
		LineSegmentPair lsp1 = new LineSegmentPair(segments[0], segments[1]);
		LineSegmentPair lsp2 = new LineSegmentPair(segments[1], segments[0]);
		LineSegmentPair lsp3 = new LineSegmentPair(segments[2], segments[0]);
		
		assertTrue (lsp1.equals(lsp1));
		assertTrue (lsp1.equals(lsp2));
		assertTrue (lsp2.equals(lsp1));
		assertFalse (lsp3.equals (lsp1));
		assertFalse (lsp1.equals (lsp3));
		assertFalse (lsp1.equals(null));
		assertFalse (lsp1.equals ("badValue"));
	
		assertEquals (segments[0].toString() + "-" + segments[1].toString(), lsp1.toString());
		
	}
}
