package algs.model.performance.segments;

import java.util.Hashtable;


import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.IntersectionDetection;
import algs.model.tests.common.TrialSuite;
import algs.model.twod.TwoDLineSegment;

public class ComparisonMain {
	
	public static void main (String []args) {
		//LineSweep dba = new LineSweep();
		BruteForceAlgorithm dba = new BruteForceAlgorithm();
		
		testSlidingLadder (dba);
	}
	
	public static void testSlidingLadder(IntersectionDetection id) {
		
		TrialSuite suite = new TrialSuite();
		int NUM_TRIALS = 8;
		
		System.out.println (id.getClass());
		for (int c = 2; c <= 512; c *= 2) {
			System.out.println (c);
			// Start with ladder at (0, n) and slide one right and one down.
			ILineSegment[] segments = new ILineSegment[c];
			int idx=0;
			for (int i = segments.length-1; i >=0; i--) {
				segments[idx++] = new TwoDLineSegment(0,segments.length-i,i,0);
			}
			
			for (int k = 0; k < NUM_TRIALS; k++) {
				System.gc();
				long now = System.currentTimeMillis();
				Hashtable<IPoint, List<ILineSegment>> res = id.intersections(segments);
				long end = System.currentTimeMillis();
				//id.output(res);
				
				int n = segments.length;
				if (res.size() != n*(n-1)/2) {
					// problems!
					System.err.println ("Unexpected size returned:" + res.size() + " for " + c);
					System.exit(0);
				} 
	
				suite.addTrial(c, now, end);
			}
		}
		
		System.out.println (suite.computeTable());
	}
}
