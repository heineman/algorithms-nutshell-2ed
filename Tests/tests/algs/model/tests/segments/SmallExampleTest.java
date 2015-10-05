package algs.model.tests.segments;

import java.util.Hashtable;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.IntersectionDetection;
import algs.model.twod.TwoDLineSegment;

import junit.framework.TestCase;


public class SmallExampleTest extends TestCase {

	@Test
	public void testDuplicates() {
		// make sure we singly report intersections even when input set has duplicates.
		ILineSegment []segments = new ILineSegment[]{
				new TwoDLineSegment(1, 1, 3, 8),
				new TwoDLineSegment(1, 5, 4, 5),
				new TwoDLineSegment(1, 1, 3, 8),   // duplicate
				new TwoDLineSegment(1, 7, 7, 2),
		};
		
		BruteForceAlgorithm dba3 = new BruteForceAlgorithm();
		algs.model.problems.segmentIntersection.LineSweep dba0 = 
			new algs.model.problems.segmentIntersection.LineSweep();
		
		Hashtable<IPoint, List<ILineSegment>> results3 = dba3.intersections(segments);
		Hashtable<IPoint, List<ILineSegment>> results0 = dba0.intersections(segments);

		// duplicate line appears twice (intersecting itself on its endpoints).
		assertEquals (5, results0.size());
		
		// duplicate line appears multiple times in the intersection report, but this doesn't
		// affect the total count
		assertEquals (3, results3.size());
		
		assertTrue (IntersectionDetection.sameWithinEpsilon(results0, results3));
	}
	
	@Test
	public void testError() {
		ILineSegment []segments = new ILineSegment[]{
				new TwoDLineSegment(0.005600016002401231,1.0664042230774395,0.9102903417085113,0.8253209233237351),
				new TwoDLineSegment(1.043548562995483,1.0870501412319074,0.3880844670207466,0.3973021817850543),
				new TwoDLineSegment(0.05235474895357495,0.7505127203934562,0.8481032902766038,0.10089055833592453),
				new TwoDLineSegment(1.1665271375649864,1.4678235217191689,0.6085764558099374,0.9913723115993872)
		};
		
		BruteForceAlgorithm dba3 = new BruteForceAlgorithm();
		algs.model.problems.segmentIntersection.LineSweep dba0 = 
			new algs.model.problems.segmentIntersection.LineSweep();
		
		Hashtable<IPoint, List<ILineSegment>> results3 = dba3.intersections(segments);
		Hashtable<IPoint, List<ILineSegment>> results0 = dba0.intersections(segments);

		// note how these generate two different intersection points.
		// Different by .00000000000000001. Are you kidding me!
		
		// 0.43043123224595614,0.4418638825443114 
		// 0.43043123224595625,0.44186388254431147

		assertTrue (IntersectionDetection.sameWithinEpsilon(results0, results3));
	}
}
		