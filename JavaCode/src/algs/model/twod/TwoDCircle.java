package algs.model.twod;

import algs.model.FloatingPoint;
import algs.model.ICircle;
import algs.model.IPoint;
import algs.model.IRectangle;

/**
 * A circle is defined by a center point and a radius.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDCircle implements ICircle {

	/** Center point of the circle. */
	TwoDPoint    origin;
	
	/** Radius of the circle. */
	double radius;
	
	/** 
	 * Construct a Circle.
	 * 
	 * @param origin     origin point of circle
	 * @param radius     the radius of the circle
	 */
	public TwoDCircle (TwoDPoint origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}
	
	/** 
	 * Construct a Circle.
	 * 
	 * @param x         x-coordinate of origin
	 * @param y         y-coordinate of origin
	 * @param radius    radius of circle
	 */
	public TwoDCircle (double x, double y, double radius) {
		this (new TwoDPoint (x,y), radius);
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof ICircle) {
			ICircle ip = (ICircle) o;
			
			return ip.getOrigin().equals(getOrigin()) &&
			FloatingPoint.same(ip.getRadius(), radius);
		}
		
		// nope
		return false;
	}
	
	/** Hashcode. */
	public int hashCode() {
		return (int) (origin.hashCode() + radius);
		
	}

	public IPoint getOrigin() {
		return origin;
	}

	public double getRadius() {
		return radius;
	}

	public double getX() {
		return origin.getX();
	}

	public double getY() {
		return origin.getY();
	}

	public IRectangle boundingRectangle() {
		return new TwoDRectangle (
				origin.getX() - radius,
				origin.getY() - radius, 
				origin.getX() + radius,
				origin.getY() + radius);
	}
	
	public String toString () {
		return "<" + origin +" @ " + radius + ">";
	}
	
}
