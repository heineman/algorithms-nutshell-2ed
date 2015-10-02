package algs.model;

/**
 * Represents a rectangle in the Cartesian plane.
 * <p>
 * Classes that implement this interface must provide accurate {@link Object#equals(Object)}
 * and {@link Object#hashCode()} methods. To be useful during debugging, one should
 * also have a meaningful {@link Object#toString()} method.
 * <p>
 * Note that it is an invariant that getLeft() &le; getRight() while getBottom() &le; getTop() 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IRectangle {
	
	/**
	 * Return the x-coordinate value for the left-side. 
	 * @return left-x coordinate of rectangle. 
	 */
	double getLeft();
	
	/** 
	 * Return the y-coordinate value for the bottom. 
 	 * @return bottom-y coordinate of rectangle. 
	 */
	double getBottom();
	
	/**
	 * Return the x-coordinate value for the right-side.
 	 * @return right-x coordinate of rectangle. 
	 */
	double getRight();
	
	/** 
	 * Return the y-coordinate value for the top. 
	 * @return top-y coordinate of rectangle. 
	 */
	double getTop();
	
	/** 
	 * Determine if the given point intersects the rectangle.
	 * 
	 * The rectangle presents closed intervals on both the X and Y dimension.
	 * @param p   point to be inspected
	 * @return    true if point intersects rectangle; false otherwise.
	 */
	boolean intersects (IPoint p);
	
	/** 
	 * Determine if rectangle contains the given rectangle r.
	 * 
	 * The rectangle presents closed intervals on both the X and Y dimension.
	 * @param    r  rectangle to be inspected
	 * @return   true if self contains the given rectangle.
	 */
	boolean contains (IRectangle r);
	
	/** 
	 * Must properly compute equals(Object) to compare based on getXXX() values.
	 * 
	 * @see Object#equals(Object)
	 */
	boolean equals (Object o);
}
