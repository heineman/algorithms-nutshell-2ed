package algs.model;

/**
 * A Line Segment between two-dimensional points.
 * <p>
 * The common etiquette is that the start of a line segment has the higher y-value
 * than the end of the line segment; note for horizontal line segments the start
 * will be the one whose x-value is smaller (i.e., left-to-right).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface ILineSegment {

	/** Types of intersecting line segments. */
	public static final int PARALLEL = 0;
	public static final int COINCIDENT = 1;
	public static final int INTERSECTING = 2;
	public static final int NON_INTERSECTING = -1;
	
	/** 
	 * Return the coordinate value of the Start of the line segment as a two-dimensional IPoint.
	 * 
	 * The start point will have a higher y-value than the end line, except for
	 * horizontal lines.
	 * 
	 * @return point representing the start of the line segment.
	 */
	IPoint getStart ();
	
	/** 
	 * Return the coordinate value of the End of the line segment as a two-dimensional IPoint.
	 * 
	 * The end point will have a lower y-value than the end line, except for
	 * horizontal lines.
	 * 
	 * @return point representing the end of the line segment.
	 */
	IPoint getEnd ();
	
	/** 
	 * Determine if this line segment is simply a point (same start &amp; end). 
	 * 
	 * @return true if this line segment represents a one-dimensional point. 
	 */
	boolean isPoint();
	
	/**
	 * Return the slope of the line segment. For vertical lines, Double.NaN
	 * @return  slope of the line segment
	 */
	double slope();
	
	/**
	 * Return the sign of the slope of the line segment. 
	 * 
	 * Vertical lines return 1. Horizontal lines return 0. Lines with positive
	 * slope return +1 while lines with negative slope return -1.
	 * @return sign of the slope
	 */
	int sign();
	
	/** 
	 * Determine if horizontal line. 
	 * @return true if this line is horizontal; false otherwise 
	 */
	boolean isHorizontal();
	
	/** 
	 * Determine if vertical line. 
	 * @return true if this line is vertical; false otherwise 
	 */
	boolean isVertical();
	
	/**
	 * Return the intersection point with the given Line Segment, or null if no such intersection.
	 * 
	 * Note that for overlapping line segments, it is unclear WHAT should be returned. 
	 * @param other   query segment
	 * @return        point on this line segment that intersects other; null if no intersection exists.
	 */
	IPoint intersection (ILineSegment other);
	
	/**
	 * Determine if Line Segment intersects the given point.
	 * @param p   query point
	 * @return    true if line segment intersects query point.
	 */
	boolean intersection(IPoint p);	
	
	/**
	 * Determine if the given point is to the right of the line segment,
	 * if we view the line segment from the lower (end) point to the upper
	 * (start) point.
	 * @param p   query point
	 * @return    true if point is on right of the line segment.
	 */
	public boolean pointOnRight(IPoint p);
	
	/**
	 * Determine if the given point is to the left of the line segment,
	 * if we view the line segment from the lower (end) point to the upper
	 * (start) point.
	 * @param p   query point
	 * @return    true if point is on left of the line segment.
	 */
	public boolean pointOnLeft(IPoint p);
	
	/**
	 * Return the y-intercept of the line segment if it were extended to be a full line.
	 * For vertical lines, Double.NaN
	 * @return    the y-intercept of this line segment if it were a full line; Double.NaN if line segment is vertical.
	 */
	double yIntercept();
}
