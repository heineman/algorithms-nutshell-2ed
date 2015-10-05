package algs.model.tests.segments;

import java.util.Hashtable;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.segments.IntegerGenerator;
import algs.model.data.segments.UniformGenerator;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.IntersectionDetection;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;

/**
 * Validate different implementations regardless of speed. There are known
 * problems with comparing the LineSweep variations with the BruteForceAlgorithm
 * because of floating point arithmetic.
 * 
 * See {@link SmallExampleTest} to see one such case.
 */
public class ComparisonTest extends TestCase {
	
	@Test
	public void testThis() {
		// original
		algs.model.problems.segmentIntersection.LineSweep dba0 = 
			new algs.model.problems.segmentIntersection.LineSweep();
		
		// slower line storage
		algs.model.problems.segmentIntersection.linkedlist.LineSweep dba1 = 
			new algs.model.problems.segmentIntersection.linkedlist.LineSweep();
		
		// slower event queue processing.
		algs.model.problems.segmentIntersection.priorityqueue.SlowLineSweep dba2 =
			new algs.model.problems.segmentIntersection.priorityqueue.SlowLineSweep();
		
		UniformGenerator ug = new UniformGenerator(1);
		
		ILineSegment[] segments = ug.generate(128);
		
		Hashtable<IPoint,List<ILineSegment>> res0 = dba0.intersections(segments);
		Hashtable<IPoint,List<ILineSegment>> res1 = dba1.intersections(segments);
		Hashtable<IPoint,List<ILineSegment>> res2 = dba2.intersections(segments);
		
		assertEquals (res0.size(), res1.size());
		assertEquals (res0.size(), res2.size());
		
		// compare everything against dba0. Use helper method provided by 
		// the IntersectionDetection class.
		assertTrue (IntersectionDetection.sameWithinEpsilon(res0, res1));
		assertTrue (IntersectionDetection.sameWithinEpsilon(res0, res2));
		
		IntegerGenerator ig = new IntegerGenerator(100, 30);
		assertTrue (ig.parameters() != null);
		
		int num = 128;
		segments = ig.generate(num);

		// because these are integer coordinates, let's check to see if 
		// coordinates are same and remove duplicates.
		ILineSegment[] nonDups = new ILineSegment[num];
		int idx = 0;
		for (int i = 0; i < num; i++) {
			boolean found = false;
			for (int j = 0; j < idx; j++) {
				if (segments[j].equals(segments[i])) {
					found = true;
					break;  // same! Leave out...
				}
			}
			
			if (!found) {
				nonDups[idx++] = segments[i];
			}
		}
		
		// now rebuild with fewer segments...
		segments = new ILineSegment[num];
		for (int i = 0; i < num; i++) {
			segments[i] = nonDups[i];
		}
		System.out.println(num + " segments...");
		
		res0 = dba0.intersections(segments);
		res1 = dba1.intersections(segments);
		res2 = dba2.intersections(segments);
		
		assertEquals (res0.size(), res1.size());
		assertEquals (res0.size(), res2.size());
		
		
		// compare everything against dba0. Use helper method provided by 
		// the IntersectionDetection class.
		assertTrue (IntersectionDetection.sameWithinEpsilon(res0, res1));
		assertTrue (IntersectionDetection.sameWithinEpsilon(res0, res2));
			
		// validate construction.
		Generator<ILineSegment> g2 = ig.construct(new String[]{"100", "30"});
		assertTrue (g2 != null);
	}
	
	@Test
	public void testEquality() {
		// original
		algs.model.problems.segmentIntersection.LineSweep dba0 = 
			new algs.model.problems.segmentIntersection.LineSweep();
		
		// slower line storage
		algs.model.problems.segmentIntersection.linkedlist.LineSweep dba1 = 
			new algs.model.problems.segmentIntersection.linkedlist.LineSweep();
		
		// slower event queue processing.
		algs.model.problems.segmentIntersection.priorityqueue.SlowLineSweep dba2 =
			new algs.model.problems.segmentIntersection.priorityqueue.SlowLineSweep();
		
		// two equal segments.
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment(
						new TwoDPoint(1, 2),
						new TwoDPoint(8, 9)),
				new TwoDLineSegment(
						new TwoDPoint(1, 2),
						new TwoDPoint(8, 9)),
		};
		
		Hashtable<IPoint, List<ILineSegment>> res0 = dba0.intersections(segments);
		Hashtable<IPoint, List<ILineSegment>> res1 = dba1.intersections(segments);
		Hashtable<IPoint, List<ILineSegment>> res2 = dba2.intersections(segments);
		
		assertEquals (res0.size(), res1.size());
		assertEquals (res0.size(), res2.size());
	}
}

