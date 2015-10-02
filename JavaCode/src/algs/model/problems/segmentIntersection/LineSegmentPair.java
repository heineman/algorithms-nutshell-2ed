package algs.model.problems.segmentIntersection;

import algs.model.ILineSegment;

/**
 * Helper class for IntersectionDetection for detecting when two independently
 * computed intersection result sets are the same.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LineSegmentPair {

	/** One segment of the pair of intersecting line segments. */
	final ILineSegment one;
	
	/** The other segment of the pair of intersecting line segments. */
	final ILineSegment two;
	
	/**
	 * Construct the pair.
	 * <p>
	 * No check is made to ensure the line segments actually intersect.
	 * 
	 * @param il1   one member.
	 * @param il2   second member.
	 */
	public LineSegmentPair (ILineSegment il1, ILineSegment il2) {
		one = il1;
		two = il2;
	}
	
	/**
	 * Equality based on whether (one,two) == (one,two) or (two,one).
	 * 
	 * @param o   object against which to be compared. Could compare along
	 *            either direction of the pair.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o instanceof LineSegmentPair) {
			LineSegmentPair lsp = (LineSegmentPair) o;
			return (lsp.one == one && lsp.two == two) ||
				   (lsp.two == one && lsp.one == two);
		}
		
		return false;
	}
	
	/** Reasonable toString. */
	public String toString() { 
		return one + "-" + two;
	}
}
