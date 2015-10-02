package algs.model.twod;

import java.util.StringTokenizer;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.IPoint;

/**
 * Standard two-dimensional implementation of IPoint.
 * 
 * For compatibility with other n-dimensional implementations, this class also 
 * implements {@link IMultiPoint}
 * 
 * @author George Heineman
 * @see IMultiPoint
 * @see IPoint
 */
public class TwoDPoint implements IPoint, IMultiPoint {
	
	/** X value of two dimensional point. */
	final double x;
	
	/** Y value of two dimensional point. */
	final double y;
	
	/** 
	 * Construct a TwoDPoint from the given (x,y) values.
	 * 
	 * @param x   value of x-coordinate.
	 * @param y   value of y-coordinate.
	 */
	public TwoDPoint (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construct when given an IPoint.
	 * 
	 * @param pt    point whose values are extracted and used to initialize this.
	 */
	public TwoDPoint (IPoint pt) {
		this.x = pt.getX();
		this.y = pt.getY();
	}
	
	/** 
	 * Construct when given a comma-separated string of x,y values as double.
	 * <p>
	 * No serious error handling is considered, so be wary of passing in
	 * invalid values. 
	 * @param s    A comma-separated string of doubles used to initialize this
	 *             two-dimensional point.
	 */
	public TwoDPoint (String s) {
		StringTokenizer st = new StringTokenizer(s, ",");
		x = Double.valueOf(st.nextToken());
		y = Double.valueOf(st.nextToken());
	}
	
	/**
	 * @see IPoint#getX()
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @see IPoint#getY()
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Provides the required equals method.
	 * 
	 * Two points are equal if their (x,y) values are equal to each other
	 * within the floating point tolerance FloatingPoint.epsilon, as defined in 
	 * 
	 * @see FloatingPoint
	 * @param o   the object against which to compare.
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof IPoint) {
			IPoint ip = (IPoint) o;
			
			return ((FloatingPoint.value(x-ip.getX()) == 0) &&
				    (FloatingPoint.value(y-ip.getY()) == 0));
		}
		
		// nope
		return false;
	}
	
	/** Reasonable toString() implementation. */
	public String toString () {
		return x + "," + y;
	}

	/**
	 * @see IMultiPoint#dimensionality()
	 */
	public int dimensionality() {
		return 2;
	}

	/**
	 * @see IMultiPoint#getCoordinate(int)
	 */
	public double getCoordinate(int d) {
		if (d == 1) { return x; }
		
		return y;
	}
	
	/**
	 * Return the Euclidean distance between the given multipoint.
	 * 
	 * Ensures that only valid for two-dimensional comparison.
	 * @param imp   the point against from which the distance is computed.
	 * @exception   IllegalArgumentException if invoked with an argument that
	 *                 does not represent a two-dimensional point.
	 */
	public double distance (IMultiPoint imp) {
		if (imp.dimensionality() != 2) {
			throw new IllegalArgumentException ("distance computation can only be performed between two-dimensional points");
		}
		
		double ox = imp.getCoordinate(1);
		double oy = imp.getCoordinate(2);
		
		return Math.sqrt((ox-x)*(ox-x) + (oy-y)*(oy-y));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	    // use java.awt.2DPoint hashcode function 
		long bits = java.lang.Double.doubleToLongBits(getX());
		bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	/**
	 * Returns the raw representation of this point as an array of two values. 
	 */
	public double[] raw() {
		return new double[] {x, y};
	}

	/**
	 * Provides the default implementation of comparing TwoDPoint to an IPoint
	 * using xy_sorter logic.
	 * 
	 * @param p   The IPoint against which to compare
	 */
	@Override
	public int compareTo(IPoint p) {
		double fp = FloatingPoint.value(getX() - p.getX());
		if (fp < 0) { return -1; }
		if (fp > 0) { return +1; }
		
		fp = FloatingPoint.value(getY() - p.getY());
		if (fp < 0) { return -1; }
		if (fp > 0) { return +1; }
		
		// same exact (x,y) points
		return 0;
	}

}
