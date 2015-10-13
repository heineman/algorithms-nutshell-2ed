package algs.model.performance.segments;

import algs.model.FloatingPoint;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;
public class PerfComparisonMain {
	
	public static void main (String[] args) {
		/**
		 * Compare whether the formula
		 * 
		 *   FloatingPoint.value((y-b)/m - x) < 0
		 *   
		 * is faster/slower than
		 * 
		 *   FloatingPoint.value(y-b-mxf) < 0
		 *   
		 * where f is the 'sign' of m. Could improve the speed of some mathematical
		 * comparisons by 7%. Is it worth it?
		 */
		TwoDLineSegment seg1 = new TwoDLineSegment(
				new TwoDPoint(12,6),
				new TwoDPoint(2, 1));
		
		// point to determine which side of seg1 it is on.
		TwoDPoint pt = new TwoDPoint (9, 3);
		
		boolean res = false;
		int trials = 10000000;
		long now = System.currentTimeMillis();
		for (int i = 0; i < trials; i++) {
			res = FloatingPoint.value((pt.getY()-seg1.yIntercept())/seg1.slope() - pt.getX()) < 0;
		}
		long now2 = System.currentTimeMillis();

		long time1 = now2-now;
		int c = 1;
		now = System.currentTimeMillis();
		for (int i = 0; i < trials; i++) {
			res = FloatingPoint.value(pt.getY()-seg1.yIntercept() - seg1.slope()*pt.getX()*c) < 0;
		}
		now2 = System.currentTimeMillis();
		
		long time2 = now2-now;
		
		System.out.println (time2 + " should be less than " + time1);
		assert (time2 < time1);
		double perc = ((double)(time1 - time2))/time1;
		
		System.out.println ("savings of " + ((int)(perc*100)) + " percent.");
		if (res) {}; // only here to stop complaining about not reading res
	}
}
