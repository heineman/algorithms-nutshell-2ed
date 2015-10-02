package algs.model.interval;

import algs.model.IInterval;

/**
 * Represents a discrete interval [left,right) that implements IInterval.
 * <p>
 * The interval [left,right) is closed on left, but open on right.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DiscreteInterval implements IInterval {

	/** Left value of interval (open). */
	int left;
	
	/** Right value of interval (closed). */
	int right;
	
	/**
	 * Create a discrete interval whose left is strictly less than its right.
	 * 
	 * @param left   leftmost value in interval (open)
	 * @param right  rightmost value in interval (closed)
	 */
	public DiscreteInterval (int left, int right) {
		if (left >= right) {
			throw new IllegalArgumentException ("Discrete Interval: left (" + left + ") must be stricly less than right (" + right + ").");
		}
		
		this.left = left;
		this.right = right;
	}
	
	/** Return the left value of the interval. */
	public int getLeft() {
		return left;
	}

	/** Return the right value of the interval. */
	public int getRight() {
		return right;
	}
	
	/**
	 * Compare DiscreteInterval objects.
	 * 
	 * Note, as per IInterval contract, only compares against IInterval
	 * information.
	 * @param o     object against which to compare.
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof IInterval) {
			IInterval di = (IInterval) o;
			return (di.getLeft() == left) && (di.getRight() == right);
		}
		
		return false;		
	}
	
	/**
     * Returns the hashCode for this DiscreteInterval.
     * 
     * Idea taken from java.awt.Point2D
     * 
     * @return      a hash code for this <code>DiscreteInterval</code>.
     */
    public int hashCode() {
		long bits = left;
		bits ^= right * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}
    
    /** Return reasonable String representation. */
    public String toString () {
    	return "[" + left + "," + right + ")";
    }

    /*
     * @see algs.model.interval.IInterval#intersects(int)
     */
	public boolean intersects(int q) {
		return (left <= q) && (q < right);
	}

	/*
	 * @see algs.model.interval.IInterval#toTheLeft(int)
	 */
	public boolean toTheLeft(int q) {
		return q < left;
	}

	/*
	 * @see algs.model.interval.IInterval#toTheRight(int)
	 */
	public boolean toTheRight(int q) {
		return q >= right;
	}

}
