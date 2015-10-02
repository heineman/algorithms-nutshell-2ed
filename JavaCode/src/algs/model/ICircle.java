package algs.model;

/**
 * A circle point has an IPoint origin and a radius &ge; 0.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface ICircle {
	
	/** 
	 * Return the x-coordinate value of the circle origin. 
	 * @return x-coordinate of circle origin. 
	 */
	double getX();
	
	/** 
	 * Return the y-coordinate value of the circle origin.
	 * @return y-coordinate of circle origin. 
	 */
	double getY();
	
	/**
	 * Return origin as an IPoint. 
	 * @return origin of circle as a point. 
	 */
	public IPoint getOrigin();
	
	/** 
	 * Return the radius of the circle. 
	 * @return radius of circle 
	 */
	double getRadius();
	
	/** 
	 * Return bounding rectangle for this circle. 
	 * @return enclosing bounding rectangle of circle. 
	 */
	IRectangle boundingRectangle();
	
	/** 
	 * Must properly compute equals(Object) to compare based 
	 * origin and radius
	 * 
	 * @see Object#equals(Object)
	 */
	boolean equals (Object o);
}
