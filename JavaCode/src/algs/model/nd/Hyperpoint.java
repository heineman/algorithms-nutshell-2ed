package algs.model.nd;

import java.util.ArrayList;
import java.util.StringTokenizer;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;

/**
 * Standard d-dimensional implementation of IMultiPoint.
 * 
 * @see IMultiPoint
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Hyperpoint implements IMultiPoint {
	/** Store index values. */
	double []values;
	
	/** Dimensionality of the point. */
	final int dimensionality;
		
	/** 
	 * Construct when given an IMultiPoint.
	 * 
	 * @param pt   multi-dimensional point from which values are taken.
	 */
	public Hyperpoint (IMultiPoint pt) {
		int d = this.dimensionality = pt.dimensionality();
		values = new double[d];
		
		for (int i = 1; i <= d; i++) {
			values[i-1] = pt.getCoordinate(i);
		}
	}
	
	/** 
	 * Construct when given a raw array of double values
	 * 
	 * @param vals    array of size d containing double coordinates. 
	 */
	public Hyperpoint (double []vals) {
		int d = this.dimensionality = vals.length;
		values = new double[d];
		
		for (int i = 1; i <= d; i++) {
			values[i-1] = vals[i-1];
		}
	}
	
	/** 
	 * Construct Hyperpoint when given a comma-separated string of doubles.
	 * <p>
	 * The number of dimensions in the resulting Hyperpoint is based on the
	 * number of values in the string.
	 * 
	 * @param s   String formed from a comma-separated set of doubles.
	 */
	public Hyperpoint (String s) {
		ArrayList<String> al = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(s, ",");
		while (st.hasMoreTokens()) {
			al.add(st.nextToken());
		}
		
		dimensionality = al.size();
		values = new double[dimensionality];
		for (int i = 1; i <= dimensionality; i++) {
			values[i-1] = Double.valueOf(al.get(i-1));
		}
	}
	
	
	/**
	 * Supports the equals checking of IMultiPoint objects.
	 * 
	 * Optimized for two Hyperpoint objects.
	 * 
	 * @param o    object to be compared against. 
	 */
	public boolean equals (Object o) {
		if (o == null) return false;

		if (o instanceof IMultiPoint) {
			IMultiPoint imp = (IMultiPoint) o;
			if (imp.dimensionality() != dimensionality) return false;
			
			if (imp instanceof Hyperpoint) {
				Hyperpoint other = (Hyperpoint) imp;
				for (int i = 1; i <= dimensionality; i++) {
					if (!FloatingPoint.same(other.values[i-1], values[i-1])) {
						return false;
					}
				}
			} else {
				for (int i = 1; i <= dimensionality; i++) {
					if (!FloatingPoint.same(imp.getCoordinate(i), values[i-1])) {
						return false;
					}
				}
			}

			// must be the same! 
			return true;
		}
		
		// nope
		return false;
	}
	
	/** Reasonable toString() implementation. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dimensionality; i++) {
			sb.append (values[i] + ",");
		}
		return sb.substring(0,sb.length()-1);
	}

	/**
	 * @see IMultiPoint#dimensionality()
	 */
	public int dimensionality() {
		return dimensionality;
	}

	/**
	 * All coordinates are 1-based.
	 * 
	 * @see IMultiPoint#getCoordinate(int)
	 */
	public double getCoordinate(int d) {
		return values[d-1];
	}
	
	/**
	 * Return the Euclidean distance between the given IMultiPoint.
	 * <p>
	 * Optimized for two Hyperpoint objects, but works with any IMultiPoint
	 * 
	 * @param  imp   compute n-dimensional distance to this point.
	 */
	public double distance (IMultiPoint imp) {
		if (imp.dimensionality() != dimensionality) {
			throw new IllegalArgumentException ("distance computation can only be performed between points with the same dimension.");
		}
		
		double sum = 0;
		
		// optimize for two Hyperpoints.
		if (imp instanceof Hyperpoint) {
			Hyperpoint other = (Hyperpoint) imp;
			for (int i = 1; i <= dimensionality; i++) {
				double d = other.values[i-1]-values[i-1];
				sum += d*d;
			}
		} else {
			for (int i = 1; i <= dimensionality; i++) {
				double v = imp.getCoordinate(i);
				sum += (v-values[i-1])*(v-values[i-1]);
			}
		}
		
		return Math.sqrt(sum);
	}
	
	/** Reasonable hash code. */
	public int hashCode() {
		long bits = java.lang.Double.doubleToLongBits(values[0]);
		for (int i = 2; i < dimensionality; i++) {
			bits ^= java.lang.Double.doubleToLongBits(values[i-1]) * 31;
		}
		
	    // use java.awt.2DPoint hashcode function 
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	/** Return copy of points for safe implementation. */
	public double[] raw() {
		double copy[] = new double[dimensionality];
		System.arraycopy(values, 0, copy, 0, dimensionality);
		return copy;
	}
}
