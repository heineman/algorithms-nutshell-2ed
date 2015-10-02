package algs.model;

/**
 * Represents a hypercube in the n-dimensional Cartesian plane.
 * 
 * Note that it is an invariant that getLeft(d) &le; getRight(d) for all dimensions d 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IHypercube {
	
	/** 
	 * Return the dimensionality of this hypercube. 
	 *
	 * @return   integer dimensionality of hypercube. 
	 */
	int dimensionality();
	
	/** 
	 * return the coordinate value for the left-side of the given dimension.
	 * 
	 * @param d    dimension value in the range 1 &le; d &le; dimensionality()
	 * @return     coordinate value for the left-side of dimension.
	 */
	double getLeft(int d);
	
	/** 
	 * return the coordinate value for the right-side of the given dimension.
	 * 
	 * @param d    dimension value in the range 1 &le; d &le; dimensionality()
	 * @return     coordinate value for the right-side of dimension.
	 */
	double getRight(int d);
	
	/** 
	 * Determine if the given point intersects the hypercube.
	 * 
	 * The hypercube presents closed intervals on all dimensions.
	 * 
	 * @param p     query point.
	 * @exception   IllegalArgumentException If dimensions are not the same
	 * @return      true if given point intersects this hypercube.
	 */
	boolean intersects (IMultiPoint p) throws IllegalArgumentException;

	/** 
	 * Optimized version of {@link IHypercube#intersects(IMultiPoint)}. 
	 *
	 * @param coords   query point represented as coordinate array.
	 * @return true if given point as coordinate array intersects this hypercube.
	 */
	boolean intersects(double[] coords);
	
	/**
	 * Determine if the hypercube intersects the given hypercube h.
	 * @param h     The target hypercube.
	 * @exception   IllegalArgumentException If dimensions are not the same
	 * @return      true if h is this object intersects given hypercube h; false otherwise
	 */
	boolean intersects (IHypercube h) throws IllegalArgumentException;
	
	/** 
	 * Determine if the hypercube wholly contains the given hypercube h.
	 * 
	 * The hypercube presents closed intervals on all dimensions.
	 * @param h     The target hypercube.
	 * @exception   IllegalArgumentException If dimensions are not the same
	 * @return      true if h is this object wholly contains given hypercube h; false otherwise
	 */
	boolean contains (IHypercube h) throws IllegalArgumentException;
}
