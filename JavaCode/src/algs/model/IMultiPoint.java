package algs.model;

/**
 * A multi-dimensional point has a set of coordinates in d-dimensional space.
 * <p>
 * This interface is the d-dimensional counterpart to {@link IPoint}. Indeed
 * the classes which implement {@link IPoint} should also implement {@link IMultiPoint}
 * to enable two-dimensional data to be processed in the same way that d-dimensional
 * data can be.
 * <p>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IMultiPoint {
	
	/** 
	 * Return the dimensionality of this point.
	 * @return  integer dimensionality of this point. 
	 */
	int dimensionality ();
	
	/** 
	 * Return the coordinate value in the given dimension for the given point. 
	 *
	 * @param dx   the dimension 1 &le; dx &le; dimensionality() for the desired coordinate.
	 * @return     coordinate value
	 */
	double getCoordinate (int dx);
	
	/**
	 * Return the Euclidean distance between the given IMultiPoint object.
	 * 
	 * @param imp   other IMultiPoint to which we want to compute the Euclidean distance.
	 * @return      distance between two points.
	 */
	double distance (IMultiPoint imp);

	/** 
	 * For optimizing computations, return double[] coordinates. 
	 * @return array[] of coordinates for point. 
	 */ 
	double[] raw();	
}
