package algs.blog.intersections;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.segments.DoubleGenerator;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.LineSweep;

/**
 * Investigate what happens with the behavior of two line intersecting algorithms as the
 * number of random line segments increases.
 * 
 * @author George Heineman
 */
public class Generator {
	
	public static void main(String[] args) {
		System.out.println("n,lineSweep,bruteForce");
		for (int n = 32; n <= 2048; n *= 2) {
			LineSweep ls = new LineSweep();
			BruteForceAlgorithm bfa = new BruteForceAlgorithm();
			
			// generate segments
			DoubleGenerator dg = new DoubleGenerator (1.0, 0.25);
			ILineSegment ils[] = dg.generate(n);
			
			// run both algorithms
			Hashtable<IPoint, List<ILineSegment>> sweepResults = ls.intersections(ils);
			Hashtable<IPoint, List<ILineSegment>> bruteForceResults = bfa.intersections(ils);
			
			System.out.println(n + "," + sweepResults.size() + "," + bruteForceResults.size());
		}		
		
	}
}
