package algs.blog.intersections;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;

/**
 * Example showing how near-vertical input lines cause problems with LineSweep.
 * 
 * @author George Heineman
 * @date 05/13/2009
 */
public class NearVerticalProblem {
	static ILineSegment[] segments = new ILineSegment[] {
		
		new TwoDLineSegment(0.145721433,0.105568686, 0.14572143,0.8),
		new TwoDLineSegment(0.079389892,0.243921955,.21,0),
		new TwoDLineSegment(0.094178956,0.536624491,.6,.5),

	};

	
	public static void main(String[] args) {
		
		// show slopes of input sets
		for (int i = 0; i < segments.length; i++) {
			IPoint start =  segments[i].getStart();
			IPoint end =  segments[i].getEnd();
			
			double slope = (end.getY() - start.getY())/ (end.getX() - start.getX());
			System.out.println("slope of " + i + "th line is " + slope);
		}
		
		LineSweep dba = new LineSweep();
		BruteForceAlgorithm bfa = new BruteForceAlgorithm();
		
		// causes failure.
		Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(segments);
		
		report (res);

		res = bfa.intersections(segments);
		
		report (res);
	}



	private static void report(Hashtable<IPoint, List<ILineSegment>> res) {
		System.out.println(res.size() + " intersection points.");
		
		for (IPoint ip : res.keySet()) {
			List<ILineSegment> ilss = res.get(ip);
			System.out.println(ip + ":");
			
			for (ILineSegment ils : ilss) {
				System.out.println("  " + ils);
			}
			System.out.println();
		}
	}
}
