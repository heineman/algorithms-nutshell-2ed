package algs.chapter9.table4;

import java.text.NumberFormat;
import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.tests.common.TrialSuite;
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
 * Useful paper on line segment intersection:
 * 
 * Gavrilova, M. and Rokne, J. (2000) "Reliable line segment intersection testing," 
 * Journal of Computer Aided Design, Elsevier, vol. 32, pp. 737-745.
 * http://www.cpsc.ucalgary.ca/~marina/papers/Segment_intersection.ps
 * 
 * @author George Heineman
 */
public class Main {
	
	/**
	 * Count Buffon's problem is:
	 * 
	 *    http://mathworld.wolfram.com/BuffonsNeedleProblem.html
	 * 
	 * You are given an infinite set of toothpicks of length len and a paper with n 
	 * vertical lines drawn on a grid at y=d, 2*d, etc...
	 *
	 * Randomly drop the toothpicks on the paper such that you are guaranteed that the
	 * toothpicks land in the upper right quadrant x range: [0, max] and y range [0,max].
	 * Now, count the number of intersections with the horizontal segments.
	 * 
	 * By framing the question in this way, we have:
	 * 
	 * toothpick length            len
	 * line separation             d
	 * number of toothpicks        n
	 * number of intersections     K
	 * 
	 * Now, K/n = 2*len/PI*d which means that PI = 2*len*n/d*K
	 * 
	 * We must be careful to only consider intersections with the two horizontal lines, rather
	 * than the other toothpicks. To reduce the 'noise' we choose len << d so the only 
	 * intersections are likely to be with lines.  
	 * 
	 * The point is to generate a meaningful benchmark to be able to compare
	 * LineSweep against BruteForce.
	 * 
	 * @param args
	 */
	public static void main (String []args) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(6);

		NumberFormat tf = NumberFormat.getInstance();
		tf.setMaximumFractionDigits(2);
		tf.setGroupingUsed(false);

		NumberFormat cf = NumberFormat.getInstance();
		cf.setGroupingUsed(true);
		
		IntersectionDetection alg1 = new LineSweep();
		IntersectionDetection alg2 = new BruteForceAlgorithm();
		
		int len = 60; 
		int d = 600;
		int max = 60000;  // one hundred lines.
		int numVertical = max/d;
		int NUM_TRIALS = 100;
		int max_N = 8192;
		
		TrialSuite lineSweep_ts = new TrialSuite();
		TrialSuite bf_ts = new TrialSuite();
		
		System.out.println("n\tLS\tBF\tnumInt\tEst.Pi\t\tError");
		for (int n = 16; n <= max_N; n *= 2) {
			int totalInts = 0;
			int totalSegInts = 0;
			
			for (int t = 0; t < NUM_TRIALS; t++) {
				Generator<ILineSegment> generator = new DoubleGenerator(max, len);
				ILineSegment[] ils = generator.generate(n+numVertical);
				
				// put in verticals: Make sure they aren't flush left or right
				// so we don't have lines that can't be intersected with.
				for (int i = 0; i < numVertical; i++) {
					ils[n+i] = new TwoDLineSegment(new TwoDPoint(d/2+d*i, 0), new TwoDPoint(d/2+d*i, max));
				}
				
				Hashtable<IPoint, List<ILineSegment>> res1 = alg1.intersections(ils);
				lineSweep_ts.addTrial(n, 0, alg1.time());
				/* Hashtable<IPoint,ILineSegment[]> res2 = */ alg2.intersections(ils);
				bf_ts.addTrial(n, 0, alg2.time());
				
				totalInts += res1.size();
				// compute which ones are between toothpicks and lines.
				for (IPoint pt: res1.keySet()) {
					// validate this is an intersection with vertical line segment.
					boolean isValid = false;
					List<ILineSegment> lines = res1.get(pt);
					for (ILineSegment line : lines) {
						if (line.getStart().getY() - line.getEnd().getY() == max) {
							isValid = true;
							break;
						}
					}
		
					if (isValid) {
						totalSegInts += (lines.size()-1); // don't count vertical line
					}
				}
			}
			
			double estimate = 2.0*len*n/(totalSegInts*d/NUM_TRIALS);
			double error = (Math.PI*Math.PI/(2*n))*(Math.PI*d/len-2);
			
			String la = tf.format(Double.valueOf(lineSweep_ts.getAverage(n)));
			String ba = tf.format(Double.valueOf(bf_ts.getAverage(n)));
			System.out.println(cf.format(n) + "\t" + la + "\t" + ba + "\t" + totalInts*1.0/NUM_TRIALS + "\t" + 
					nf.format(estimate) + "\t +/- " + nf.format(error));			
		}
		
	}
}
