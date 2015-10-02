package algs.model.problems.segmentIntersection;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;

/**
 * Brute-force implementation of Line Segment intersection.
 * <p>
 * Can be used as a benchmark for improvements when using LineSweep; alternatively,
 * can show the degenerate cases where a brute force approach is faster. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BruteForceAlgorithm extends IntersectionDetection {

	/** Default constructor. */
	public BruteForceAlgorithm () {
		super();
	}
	
	/**  
	 * Check each possible pair of line segments and record its intersection.
	 * 
	 * @param segments    segments to be evaluated.
	 */
	@Override
	public Hashtable<IPoint, List<ILineSegment>> intersections(
			ILineSegment[] segments) {
		startTime();
		initialize();
		for (int i = 0; i < segments.length-1; i++) {
			for (int j = i+1; j < segments.length; j++) {
				IPoint p = segments[i].intersection(segments[j]);
				if (p != null) {
					record (p, segments[i], segments[j]);
				}
			}
		}
		computeTime();
		return report;
	}

}
