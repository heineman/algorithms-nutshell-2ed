package algs.model.tests.intersections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;

public class IntersectionDetectionTest {

	@Test
	public void testEntryPoints() {
		LineSweep dba = new LineSweep();


		// showed a problem
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment (0, 0, 5, 5),
				new TwoDLineSegment (2, 2, 2, 2)
		};

		ArrayList<ILineSegment> as = new ArrayList<ILineSegment>();
		as.add(segments[0]);
		as.add(segments[1]);
		
		Hashtable<IPoint,List<ILineSegment>> res = dba.intersections(segments);
		Hashtable<IPoint,List<ILineSegment>> res2 = dba.intersections(as);
		Hashtable<IPoint,List<ILineSegment>> res3 = dba.intersections(as.iterator());

		assertEquals (1, res.size());
		assertEquals (1, res2.size());
		assertEquals (1, res3.size());
	}
}
