package algs.chapter9.table5;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;
import algs.model.twod.TwoDLineSegment;
import algs.model.data.segments.DoubleGenerator;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.problems.segmentIntersection.IntersectionDetection;

/**
 * Another way to determine BruteForce beats LineSweep.
 * 
 * Really explore the area of [32,25] to see when this cross over seems to occur.
 * Remove all computations for PI and focus solely on the AGGREGATE time for the
 * number of trials (to try to provide a meaningful length of time to measure the
 * performance.
 * 
 * @author George Heineman
 */
public class DrillDown {
	
	/**
	 * See Main for the use of these data sets.
	 *
	 * @param args
	 */
	public static void main (String []args) {
		IntersectionDetection alg1 = new LineSweep();
		IntersectionDetection alg2 = new BruteForceAlgorithm();
		
		int len = 6; 
		int d = 60;
		int max = 600;  // ten lines.
		int numVertical = max/d;
		int NUM_TRIALS = 100;
		int max_N = 256;
		
		System.out.println("n,numIntersections,LineSweep,BruteForce");
		for (int n = 32; n <= max_N; n++) {
			
			// prepare all trials.
			Object[] trials = new Object[NUM_TRIALS];
			for (int t = 0; t < NUM_TRIALS; t++) {
				Generator<ILineSegment> generator = new DoubleGenerator(max, len);
				ILineSegment[] ils = generator.generate(n+numVertical);
				
				// put in verticals: Make sure they aren't flush left or right
				// so we don't have lines that can't be intersected with.
				for (int i = 0; i < numVertical; i++) {
					ils[n+i] = new TwoDLineSegment(new TwoDPoint(d/2+d*i, 0), new TwoDPoint(d/2+d*i, max));
				}
				
				trials[t] = ils;
			}
			
			System.gc();
			long ints1 = 0;
			long now = System.currentTimeMillis();
			for (int t = 0; t < NUM_TRIALS; t++) {
				ILineSegment[] ils = (ILineSegment[]) trials[t];
				Hashtable<IPoint, List<ILineSegment>> res1 = alg1.intersections(ils);
				ints1 += res1.size();
			}
			long after = System.currentTimeMillis();
			long t1 = after - now; 
				
			System.gc();
			long ints2 = 0;
			now = System.currentTimeMillis();
			for (int t = 0; t < NUM_TRIALS; t++) {
				ILineSegment[] ils = (ILineSegment[]) trials[t];
				Hashtable<IPoint, List<ILineSegment>> res1 = alg2.intersections(ils);
				ints2 += res1.size();
			}
			after = System.currentTimeMillis();
			long t2 = after - now;
			
			if (ints1 != ints2) {
				System.out.println("different number " + ints1 + "," + ints2);
			}
			
			System.out.println(n + "," + ints1 + "," + t1 + "," + t2);
		}
	}
}
