package algs.chapter9.table5;

import java.text.NumberFormat;
import java.util.Hashtable;


import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.IntersectionDetection;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.tests.common.TrialSuite;
import algs.model.twod.TwoDLineSegment;

/**
 * Compare on worst-case when the number of intersections is n*(n-1)/2
 * 
 * @author George Heineman
 */
public class Main {
	
	public static void main (String []args) {
		LineSweep dba1 = new LineSweep();
		BruteForceAlgorithm dba2 = new BruteForceAlgorithm();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		TrialSuite db1_suite = testSlidingLadder (dba1);
		TrialSuite db2_suite = testSlidingLadder (dba2);
		
		System.out.println("n\tAvgLS\tAvgBF");
		for (int c = 2; c <= 512; c *= 2) {
			System.out.println(c + "\t" + nf.format(Double.valueOf(db1_suite.getAverage(c))) + "\t" 
					+ nf.format(Double.valueOf(db2_suite.getAverage(c))));
		}
	}
	
	/**
	 * Run a trial and return the computed TrialSuite
	 * 
	 * @param id
	 * @return
	 */
	public static TrialSuite testSlidingLadder(IntersectionDetection id) {
		
		TrialSuite suite = new TrialSuite();
		int NUM_TRIALS = 100;
		
		for (int c = 2; c <= 512; c *= 2) {
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
				
				int n = segments.length;
				if (res.size() != n*(n-1)/2) {
					System.err.println ("Unexpected size returned:" + res.size() + " for " + c);
					System.exit(2);
				} 
	
				suite.addTrial(c, now, end);
			}
		}
		
		return suite;
	}
}
