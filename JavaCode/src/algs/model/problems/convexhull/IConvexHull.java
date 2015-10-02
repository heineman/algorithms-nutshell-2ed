package algs.model.problems.convexhull;

import algs.model.IPoint;

/**
 * Defined interface for algorithms that compute the convex hull for a set
 * of IPoint objects. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IConvexHull {
	
	/**
	 * Return the computed convex hull for the input set of IPoint objects.
	 * <p>
	 * Points must have at least three points to do anything meaningful. If
	 * it does not, then the sorted array is returned as the "hull".
	 * <p>
	 * Some implementations may be able to work if duplicate points are found,
	 * but the set should contain distinct {@link algs.model.IPoint} objects.
	 *
	 * @param points     an array of (n &ge; 3) two dimensional points.
	 * @return           convex hull of given points as array.
	 */
	IPoint[] compute (IPoint[] points);
}
