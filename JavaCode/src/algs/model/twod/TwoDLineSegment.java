package algs.model.twod;

import algs.model.FloatingPoint;
import algs.model.ILineSegment;
import algs.model.IMultiLineSegment;
import algs.model.IMultiPoint;
import algs.model.IPoint;

/**
 * Standard two-dimensional implementation of ILineSegment.
 * 
 * By etiquette, all line segments are directionless, so we impose our own order
 * on things. Specifically, the start of the line has its y value &ge; the y-value 
 * of the end.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDLineSegment implements ILineSegment, IMultiLineSegment {
	
	/** Store Line segment start. */
	public final TwoDPoint start;
	
	/** Store Line segment end. */
	public final TwoDPoint end;
	
	/** Is just a point? */
	public final boolean isPoint;
	
	/** Slope. */
	public final double m;
	
	/** Sign of Slope. Needed to deal with the 3 situations when determining pointOnRight. Assume positive */
	public final int sign;
	
	/** Compute y-Intercept. */
	double b;
	
	/**
	 * Helper method for constructing a Line segment from four points.
	 * 
	 * @param x1    x-value of p1
	 * @param y1    y-value of p1
	 * @param x2    x-value of p2
	 * @param y2    y-value of p2
	 */
	public TwoDLineSegment (double x1, double y1, double x2, double y2) {
		this (new TwoDPoint (x1, y1), new TwoDPoint (x2, y2));
	}
	
	/** 
	 * 
	 * @param s   Start of this line segment in two-dimensional space
	 * @param e   End of this line segment in two-dimensional space
	 * 
	 * TODO: Should we prevent attempts to store with Infinity?
	 */
	public TwoDLineSegment (IPoint s, IPoint e) {
		TwoDPoint st = new TwoDPoint (s);
		TwoDPoint ed = new TwoDPoint (e);
		
		// swap so the start is the upper of the two points.
		if (FloatingPoint.lesser(st.getY(), ed.getY())) {
			TwoDPoint tmp = st;
			st = ed;
			ed = tmp;
		} else if (FloatingPoint.value(st.getY() - ed.getY()) == 0) {
			// if a horizontal line, make sure it goes left to right.
			if (FloatingPoint.greater(st.getX(), ed.getX())) {
				TwoDPoint tmp = st;
				st = ed;
				ed = tmp;
			}
		}
		
		// pre-compute slope
		double x1 = st.getX();
		double y1 = st.getY();
		double x2 = ed.getX();
		double y2 = ed.getY();
		
		if (FloatingPoint.same(x1,x2)) { 
			m = Double.NaN;
			sign = 1;
		} else {
			m = (y2 - y1)/(x2-x1);
			if (FloatingPoint.lesser(m, 0)) {
				sign = -1; 
			} else {
				sign = 1;   // even for 0/horizontal case. 
			}
		}
		
		// pre-compute y-Intercept
		
		// note that y = m*x + b. Once m is known, then plug in sample x and y to 
		// retrieve b
		if (FloatingPoint.same(x1,x2)) { 
			b =  Double.NaN; 
		} else {
			b = y1 - m*x1;
		}

		// set the final values.
		this.start = st;
		this.end = ed;
		
		// if same points, then is a point.
		isPoint = (st.equals(ed));
	}
	
	/**
	 * @see IMultiLineSegment#getStartPoint()
	 */
	public IMultiPoint getStartPoint() {
		return start;
	}
	
	/**
	 * @see IMultiLineSegment#getEndPoint()
	 */
	public IMultiPoint getEndPoint() {
		return end;
	}
	
	/**
	 * @see ILineSegment#getStart()
	 */
	public IPoint getStart() {
		return start;
	}
	
	/**
	 * @see ILineSegment#getEnd()
	 */
	public IPoint getEnd() {
		return end;
	}
	
	/**
	 * Provides the standard equals method.
	 * 
	 * A line segment (p1, p2) is not the same as line segment (p2, p1).
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof ILineSegment) {
			ILineSegment ils = (ILineSegment) o;
			return start.equals(ils.getStart()) && end.equals (ils.getEnd());
		}
		
		// nope
		return false;
	}
	
	/** Reasonable toString() implementation. */
	public String toString () {
		return "<" + start + "," + end + ">";
	}

	/**
	 * @see IMultiLineSegment#dimensionality()
	 */
	public int dimensionality() {
		return 2;
	}
	
	/** Compute slope of line segment, or return Double.NaN for vertical lines. */
	public double slope() {
		return m;
	}
	
	/** Return sign of slope. */
	public int sign() {
		return sign;
	}
	
	/** Determine if horizontal. */
	public boolean isHorizontal() {
		return FloatingPoint.same(start.getY(), end.getY());
	}
	
	/** Determine if vertical. */
	public boolean isVertical() {
		return FloatingPoint.same(start.getX(), end.getX());
	}
	
	/** Compute yIntercept of line segment if extended to be a line. */
	public double yIntercept() {
		return b;
	}

	/** 
	 * Compute the intersection type with the given line segment.
	 * 
	 * Method not actually used by any algorithms (yet). Returns
	 * one of:
	 * <ul>
	 * <li>INTERSECTING -- the two line segments intersect.
	 * <li>NON_INTERSECTING -- the two line segments do not intersect.
	 * <li>PARALLEL -- the two line segments are parallel.
	 * <li>COINCIDENT -- the two line segments intersect each other
	 *                   multiple times because they are parallel.
	 * </ul>
	 * @param other   other line segment with which to compare.
	 * @return integer value, one of INTERSECTING, NON_INTERSECTING, PARALLEL, COINCIDENT
	 */
	public int intersectionType (ILineSegment other) {
		double x1 = start.getX();
		double y1 = start.getY();
		double x2 = end.getX();
		double y2 = end.getY();
		
		double x3 = other.getStart().getX();
		double y3 = other.getStart().getY();
		double x4 = other.getEnd().getX();
		double y4 = other.getEnd().getY();
		
		// common denominator
		double da = (y4 - y3)*(x2 - x1);
		double db = (x4 - x3)*(y2 - y1);
		double denom = da-db;
		
		// numerators
		double ux = (x4-x3)*(y1-y3) - (y4-y3)*(x1-x3);
		double uy = (x2-x1)*(y1-y3) - (y2-y1)*(x1-x3);
		
		if (FloatingPoint.value(denom) == 0.0) {
			if (FloatingPoint.value(ux) == 0 && FloatingPoint.value(uy) == 0) {
				return COINCIDENT;
			}
			return PARALLEL;
		}
		
		ux = ux / denom;
		uy = uy / denom;
		
		// line segment intersections are between 0 and 1. Both must be true
		if (ux < 0) return NON_INTERSECTING;
		if (ux > 1) return NON_INTERSECTING;
		if (uy < 0) return NON_INTERSECTING;
		if (uy > 1) return NON_INTERSECTING;
		
		return INTERSECTING;  // HAH!
	}
	
	/** 
	 * Computer the intersection point with the given line segment.
	 * 
	 * If no such intersection, return null. If null is returned, you may have to 
	 * invoke intersectionType to discover the reason.
	 */
	public IPoint intersection (ILineSegment other) {
		double x1 = start.getX();
		double y1 = start.getY();
		double x2 = end.getX();
		double y2 = end.getY();
		
		double x3 = other.getStart().getX();
		double y3 = other.getStart().getY();
		double x4 = other.getEnd().getX();
		double y4 = other.getEnd().getY();
		
		// common denominator
		double da = (y4 - y3)*(x2 - x1);
		double db = (x4 - x3)*(y2 - y1);
		double denom = da-db;
		
		if (FloatingPoint.value(denom) == 0) {
			return null;   // PARALLEL or COINCIDENT
		}
		
		// numerators
		double ux = (x4-x3)*(y1-y3) - (y4-y3)*(x1-x3);
		double uy = (x2-x1)*(y1-y3) - (y2-y1)*(x1-x3);
		
		ux = ux / denom;
		uy = uy / denom;
		
		// line segment intersections are between 0 and 1. Both must be true; special
		// care must be paid to both boundaries w/ floating point issues.
		if (FloatingPoint.value(ux) >= 0 && 
			FloatingPoint.value(ux-1) <= 0 && 
			FloatingPoint.value(uy) >= 0 && 
			FloatingPoint.value(uy-1) <= 0) {
			double ix = x1 + ux*(x2-x1);
			double iy = y1 + ux*(y2-y1);
			TwoDPoint intersect = new TwoDPoint(ix, iy);
			
			return intersect;
		}
	
		return null;  // not intersecting
	}

	/** 
	 * Determine if line segments intersects this point.
	 * <p>
	 * Intersection is true if point equals either of the end points.
	 * 
	 * Removed since it detects intersections along the infinite line which is
	 * the extension of this line segment. Only for vertical lines does this
	 * method restrict the intersection to between (start, end).
	 * 
	 * @param p   point to be inspected.
	 */
	public boolean intersection(IPoint p) {
		// sample endpoints.
		if (start.equals(p)) { return true; }
		if (end.equals(p)) { return true; }
		
		// vertical line? Handle.
		if (FloatingPoint.same(start.x, end.x)) {
			return (FloatingPoint.same(p.getX(), start.x) &&
					FloatingPoint.lesserEquals(p.getY(), start.y) &&
					FloatingPoint.greaterEquals(p.getY(), end.y));
		}

		/**
		 *  Note that we could pre-compute with each line segment since this is
		 *  likely to be a commonly needed piece of data for the algorithms.
		 */
		double yIntercept = start.y - m*start.x;
		
		// validate.
		boolean onInfiniteLine = (FloatingPoint.value(p.getY() - yIntercept - m*p.getX()) == 0.0);
		if (!onInfiniteLine) {
			return false;
		}
		
		// make sure between (s,e) points. Note vertical line handled earlier.
		// Make use of 'sign'
		if (sign == 1) {
			if (p.getX() > start.getX()) return false;
			if (p.getX() < end.getX()) return false;
		} else {
			if (p.getX() < start.getX()) return false;
			if (p.getX() > end.getX()) return false;
		}
		
		return true;
	}

	/**
	 * @see ILineSegment#isPoint()
	 */
	public boolean isPoint() {
		return isPoint;
	}
	
	/**
	 * Given the line segment in its reverse-directed orientation (from lower to upper
	 * point), determine if point p is to the right of the line, as you head from lower 
	 * to upper.
	 * 
	 * If it is exactly on the line, then we return false.
	 * 
	 * Note that we use the following inverted equation for lines to make the determination.
	 * The equation can easily be confirmed, but care must be taken based upon the sign 
	 * of the slope (since we are dividing by m).
	 *     
	 * <pre>              
	 *                 (y2 - y1)
     *    0 = y - y1 - --------- (x - x1)
     *                 (x2 - x1)
	 * </pre>
	 * 
	 * Since this operation is SO COMMON, we use a standard trick to remove the IF statements.
	 * Specifically, if m is positive, then we check if res &lt; 0 but if m is negative, then we 
	 * check if -res &lt; 0. Thus we also need to store the SIGN of the slope with each segment.
	 * 
	 * @param p      Point in question
	 */
	public boolean pointOnRight(IPoint p) {
		if (isVertical()) {
			return FloatingPoint.lesser(start.getX(), p.getX());
		} else if (isHorizontal()) {
			return (FloatingPoint.greater(p.getY(), start.getY()));
		}
		
		double res = p.getY() - end.getY() - m*(p.getX() - end.getX());
		
		return FloatingPoint.lesser(res*sign, 0);
	}
	
	/**
	 * Given the line segment in its reverse-directed orientation (from lower to upper point), determine
	 * if point p is to the left of the line, as you head from lower to upper
	 * 
	 * If it is exactly on the line, then we return false.
	 * 
	 * See {@link #pointOnRight(IPoint)} for the mathematical explanation 
	 * for these equations, as well as importance of <code>sign</code>.
	 * 
	 * @param p query point 
	 * @return true if p is to the left of the line; false otherwise.
	 */
	public boolean pointOnLeft(IPoint p) {
		if (isVertical()) {
			return FloatingPoint.greater(start.getX(), p.getX());
		} else if (isHorizontal()) {
			return FloatingPoint.lesser(p.getY(), start.getY());
		}
		
		double res = p.getY() - end.getY() - m*(p.getX() - end.getX());

		return FloatingPoint.greater(res*sign, 0);
	}

	
}
