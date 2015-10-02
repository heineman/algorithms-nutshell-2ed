package algs.model.nd;

import algs.model.FloatingPoint;
import algs.model.IHypercube;
import algs.model.IMultiPoint;

/**
 * Represents a Hypercube in the n-dimensional Cartesian plane.
 * 
 * Note that it is an invariant that getLeft(d) &le; getRight(d) for all dimensions d 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Hypercube implements IHypercube {

	/** Number of dimensions in this hypercube. */
	final int dimension;
	
	/** low values in each dimension. */
	double lows[];
	
	/** high values in each dimension. */
	double highs[];
	
	/**
	 * Construct an n-dimensional hypercube with origin coordinates.
	 * 
	 * @param dimension   the number of dimensions of the hypercube.
	 */
	public Hypercube (int dimension) {
		this.dimension = dimension;
		
		this.lows = new double [dimension];
		this.highs = new double [dimension];
	}
	
	/**
	 * Fill in values for this hypercube drawn from the IHypercube parameter.
	 * 
	 * @param   cube     The existing IHypercube object that we are to mimic
	 */
	public Hypercube (IHypercube cube) {
		this.dimension = cube.dimensionality();
		
		this.lows = new double [dimension];
		this.highs = new double [dimension];
		
		for (int i = 1; i <= dimension; i++) {
			this.lows[i-1] = cube.getLeft(i);
			this.highs[i-1] = cube.getRight(i);
		}
	}
	
	/**
	 * Construct an n-dimensional hypercube.
	 * <p>
	 * The dimensionality is assumed to be the length of either array.
	 * 
	 * @param lows    vector of low values
	 * @param highs   vector of high values
	 * @exception  IllegalArgumentException    if n is &lt; 2 or if the arrays differ in length.
	 */
	public Hypercube (double lows[], double highs[]) {
		dimension = lows.length;
		if (lows.length != highs.length) {
			throw new IllegalArgumentException ("lows and highs arrays do not contain the same number of dimensions.");
		}
		
		if (dimension < 2) {
			throw new IllegalArgumentException ("Hypercube can only be created with dimensions of 2 and higher.");
		}
		
		this.lows = new double[dimension];
		this.highs = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			this.lows[i] = lows[i];
			this.highs[i] = highs[i];
		}
	}
	
	/**
	 * Convenience method to construct a 2-dimensional Hypercube from two points.
	 * <p>
	 * Note that the order of these parameters is at odds with what would be found
	 * in RectangularRegion.
	 * <p>
	 * @param xlow    x-low coordinate of a 2d hypercube.
	 * @param xhigh   x-high coordinate of a 2d hypercube.
	 * @param ylow    y-low coordinate of a 2d hypercube.
	 * @param yhigh   y-high coordinate of a 2d hypercube.
	 */
	public Hypercube(double xlow, double xhigh, double ylow, double yhigh) {
		lows = new double[] { xlow, ylow };
		highs = new double[] { xhigh, yhigh };
		
		dimension = 2;
	}

	/** 
	 * Return the dimensionality of this hypercube. 
	 */
	public int dimensionality() {
		return dimension;
	}

	/**
	 * Return the left-coordinate in the given dimension.
	 * 
	 * @param  d    Desired dimension 
	 */
	public double getLeft(int d) {
		return lows[d-1];
	}
	
	/**
	 * Set the left-coordinate in the given dimension.
	 * 
	 * @param  d       Desired dimension 
	 * @param  value   new value.
	 */
	public void setLeft(int d, double value) {
		lows[d-1] = value;
	}
	
	/**
	 * Return the right-coordinate in the given dimension.
	 * 
	 * @param  d       Desired dimension.
	 */
	public double getRight(int d) {
		return highs[d-1];
	}
	
	/**
	 * Set the right-coordinate in the given dimension.
	 * 
	 * @param  d       Desired dimension 
	 * @param  value   new value.
	 */
	public void setRight(int d, double value) {
		highs[d-1] = value;
	}

	/**
	 * Meaningful hashcode function.
	 */
	public int hashCode() {
		long bits = java.lang.Double.doubleToLongBits(lows[0]);
		for (int i = 1; i < dimension; i++) {
			bits ^= java.lang.Double.doubleToLongBits(lows[i-1]) * 31;
		}
		for (int i = 1; i < dimension; i++) {
			bits ^= java.lang.Double.doubleToLongBits(highs[i-1]) * 31;
		}
		
	    // use java.awt.2DPoint hashcode function 
		return (((int) bits) ^ ((int) (bits >> 32)));
	}
	
	/**
	 * Determine equality by comparing coordinates on each dimension
	 * @see Object#equals(Object)
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof IHypercube) {
			IHypercube ihc = (IHypercube) o;
			
			if (ihc.dimensionality() != dimension) { return false; }
			
			// Check each dimension, and leave right away on failure.
			for (int i = 1; i <= dimension; i++) {
				
				double x = ihc.getLeft(i);
				if (!FloatingPoint.same(x,lows[i-1])) return false;
				
				x = ihc.getRight(i);
				if (!FloatingPoint.same(x,highs[i-1])) return false;
			}
			
			// everything passes!
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determine intersection among all point coordinates (in raw, optimized form).
	 * 
	 * @param rawPoint   array of the coordinates of the target {@link IMultiPoint} 
	 */
	public boolean intersects (double[]rawPoint) {
		if (rawPoint.length != dimension) {
			throw new IllegalArgumentException ("Unable to determine intersection between Hypercube (dimension " +
					dimension + ") and point (dimension " + rawPoint.length + ")");
		}
		
		// Check each dimension, and leave right away on failure.
		for (int i = 0; i < dimension; i++) {
			if (rawPoint[i] < lows[i] || rawPoint[i] > highs[i]) {
				return false;
			}
		}
		
		// passed all checks.
		return true;
	}
	
	/**
	 * Determine intersection among all point coordinates.
	 *
	 * @param p     check for intersection among all coordinate dimensions for this {@link IMultiPoint}.
	 */
	public boolean intersects(IMultiPoint p) {
		if (p.dimensionality() != dimension) {
			throw new IllegalArgumentException ("Unable to determine intersection between Hypercube (dimension " +
					dimension + ") and point (dimension " + p.dimensionality() + ")");
		}
		
		// Check each dimension, and leave right away on failure.
		for (int i = 1; i <= dimension; i++) {
			double x = p.getCoordinate(i);
			if (x < lows[i-1] || x > highs[i-1]) {
				return false;
			}
		}
		
		// passed all checks.
		return true;
	}
	
	/** 
	 * Determine if the hypercube wholly contains the given hypercube h.
	 * <p>
	 * The hypercube presents closed intervals on all dimensions. Note that if
	 * -INF or +INF is present, then we can take care to ensure proper containment
	 * even in face of boundless dimensions.
	 * 
	 * @param       h    query hypercube
	 * @exception   IllegalArgumentException If dimensions are not the same
	 */
	public boolean contains(IHypercube h) throws IllegalArgumentException {
		if (dimension != h.dimensionality()) {
			throw new IllegalArgumentException ("Unable to check containment for hypercubes of different dimensions.");
		}
		
		// Check each dimension, and leave right away on failure.
		for (int i = 1; i <= dimension; i++) {
			// find target bounds
			double innerLeft = h.getLeft(i);
			double innerRight = h.getRight(i);
			
			// is [innerLeft,innerRight] wholly contained within our [left,right]?
			if (getLeft(i) <= innerLeft && 
				innerLeft <= innerRight && 
				innerRight <= getRight(i)) {
				// try next dimension
			} else {
				return false;   // fails immediately
			}
		}
		
		// passed all checks.
		return true;
	}
	
	/** 
	 * Determine if the hypercube intersects the given hypercube h.
	 * <p>
	 * The hypercube presents closed intervals on all dimensions. Note that if
	 * -INF or +INF is present, then we can take care to ensure proper containment
	 * even in face of boundless dimensions.
	 * 
	 * @param       h    query hypercube
	 * @exception   IllegalArgumentException  If dimensions are not the same
	 */
	public boolean intersects (IHypercube h) throws IllegalArgumentException {
		if (dimension != h.dimensionality()) {
			throw new IllegalArgumentException ("Unable to check containment for hypercubes of different dimensions.");
		}
		
		// Check each dimension, and leave right away on success.
		for (int i = 1; i <= dimension; i++) {
			// find target bounds
			double innerLeft = h.getLeft(i);
			double innerRight = h.getRight(i);
			
			// no intersection potential? try next dimension
			if (innerRight < getLeft(i)) continue;
			if (innerLeft > getRight(i)) continue;
			
			return true;
		}
		
		// failed to have a single intersection.
		return false;
	}
	
	/** Reasonable toString method. */
	public String toString () {
		StringBuilder sb = new StringBuilder ("["); 
		for (int d = 1; d <= dimension; d++) {
			sb.append ("" + getLeft(d) + "," + getRight(d));
			if (d != dimension) { sb.append (" : "); }
		}
		
		sb.append("]");
		return sb.toString();
	}
}
