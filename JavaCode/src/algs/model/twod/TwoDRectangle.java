package algs.model.twod;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.IRectangle;

/**
 * Represents a rectangular region in the Cartesian plane.
 * <p>
 * Note that the rectangle could be infinite in none, one, or two of these dimensions
 * by having any of its coordinates set to Double.NEGATIVE_INFINITY or 
 * Double.POSITIVE_INFINITY. A rectangle could be one-dimensional (if either x1==x2 or
 * y1==y2) or zero-dimensional (if both x1==x2 and y1==y2).
 * <p>
 * RectangularRegion is (slightly) incompatible with IHypercube because rectangles are
 * commonly referred to as (left, bottom, right, top). As you can see, the dimensions
 * are intermixed. IHypercube expects a number of dimensions, and to keep things straight
 * they are reported in order: here this means it would be (left, right) (bottom, top).
 * In order to avoid confusion, we make no attempt to have this class conform to both
 * IRectangle and IHypercube.
 * <p>
 * Note that for convenience, there is a method in Hypercube that will create a 
 * two-dimensional Hypercube counterpart given a RectangularRegion. 
 * 
 * @see algs.model.IHypercube
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDRectangle implements IRectangle {

	/** Left of the region. */
	double left;
	
	/** Bottom of the region. */
	double bottom;
	
	/** Right of the region. */
	double right;
	
	/** Top of the region. */
	double top;
	
	/**
	 * Construct a rectangle from the given Cartesian coordinates.
	 * 
	 * @param left     left-x value of rectangle
	 * @param bottom   bottom-y value of rectangle
	 * @param right    right-x value of rectangle
	 * @param top      top-y value of rectangle
	 */
	public TwoDRectangle (double left, double bottom, double right, double top) {
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.top = top;
	}
	
	/** 
	 * Copy constructor. 
	 * 
	 * @param r   Rectangle to be copied.
	 */
	public TwoDRectangle (IRectangle r) {
		this.left = r.getLeft();
		this.right = r.getRight();
		this.bottom = r.getBottom();
		this.top = r.getTop();
	}
	
	/**
	 * Return bottom of rectangle. 
	 *
	 * @return bottom-y coordinate of rectangle.
	 */
	public double getBottom() {
		return bottom;
	}

	/**
	 * Return left of rectangle. 
	 *
	 * @return left-x coordinate of rectangle.
	 */
	public double getLeft() {
		return left;
	}

	/** 
	 * Return right of rectangle.
	 *  
	 * @return right-x coordiante of rectangle.
	 */
	public double getRight() {
		return right;
	}

	/**
	 * Return top of rectangle. 
	 * 
	 * @return top-y coordinate of rectangle.
	 */
	public double getTop() {
		return top;
	}

	/** 
	 * Update top of rectangle. 
	 * @param d    new value for top-y coordinate
	 */
	public void setTop (double d) {
		top = d;
	}
	
	/** 
	 * Update bottom of rectangle. 
	 * @param d    new value for bottom-y coordinate
	 */
	public void setBottom (double d) {
		bottom = d;
	}
	
	/** 
	 * Update left of rectangle. 
	 * @param d    new value for left-x coordinate
	 */
	public void setLeft (double d) {
		left = d;
	}
	
	/**
	 * Update right of rectangle.
 	 * @param d    new value for right-x coordinate
	 */
	public void setRight (double d) {
		right = d;
	}
	
	/**
	 * Determines intersection of the given point within the closed Rectangular region.
	 * @param p   IPoint to be inspected.
	 * @return    true if point intersects current rectangle
	 */
	public boolean intersects(IPoint p) {
		double x = p.getX();
		double y = p.getY();
		
		return (FloatingPoint.greaterEquals(x, left) &&
			FloatingPoint.lesserEquals(x, right) &&
			FloatingPoint.greaterEquals(y, bottom) &&
			FloatingPoint.lesserEquals(y, top));
	}
	
	/**
	 * Determines containment of the given rectangle within the closed Rectangular region.
	 * <p>
	 * Handle -INF and +INF equitably.
	 * @param r   Rectangle to be checked for containment within self.
	 * @return    true if given rectangle is contained within self.
	 */
	public boolean contains (IRectangle r) {
		double rl = r.getLeft();
		double rr = r.getRight();
		if (FloatingPoint.lesserEquals(left, rl) &&
				FloatingPoint.lesserEquals(rl, rr) &&
				FloatingPoint.lesserEquals(rr, right)) {
			double rb = r.getBottom();
			double rt = r.getTop();
			if (FloatingPoint.lesserEquals(bottom, rb) && 
					FloatingPoint.lesserEquals(rb, rt) &&
					FloatingPoint.lesserEquals(rt, top)) {
				return true;
			}
		}
		
		// nope!
		return false;
	}
	
	/** 
	 * Reasonable representation of this rectangular region. 
	 * @return readable string for object. 
	 */
	public String toString () {
		return "[" + left + "," + bottom + " : " + right + "," + top + "]";
	}
	
	/**
	 * Extend to cover .equals() to any object that implements IRectangle.
	 * 
	 * Perform comparison on (left,bottom,right,top) values.
	 * 
	 * @see Object#equals(Object)
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		if (o instanceof IRectangle) {
			IRectangle other = (IRectangle) o;
			
			return left == other.getLeft() &&
				   bottom == other.getBottom() &&
				   right == other.getRight() &&
				   top == other.getTop();
		}
		
		// nope
		return false;
	}
}
