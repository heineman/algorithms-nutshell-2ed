package algs.model.problems.convexhull.andrew;

import algs.model.IPoint;
import algs.model.heap.HeapSort;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.PartialHull;

/**
 * Computes Convex Hull following Andrew's Algorithm. This algorithm is described
 * in the text.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ConvexHullScan implements IConvexHull {
	
	/**
	 * Use Andrew's algorithm to return the computed convex hull for 
	 * the input set of points.
	 * <p>
	 * Points must have at least three points to do anything meaningful. If
	 * it does not, then the sorted array is returned as the "hull".
	 * <p>
	 * This algorithm will still work if duplicate points are found in
	 * the input set of points.
	 *
	 * @param points     a set of (n &ge; 3) two dimensional points.
	 */
	public IPoint[] compute (IPoint[] points) {
		// sort by x coordinate (and if ==, by y coordinate). 
		int n = points.length;
		new HeapSort<IPoint>().sort(points, 0, n-1, IPoint.xy_sorter);
		if (n < 3) { return points; }
	
		// Compute upper hull by starting with leftmost two points
		PartialHull upper = new PartialHull (points[0], points[1]);
		for (int i = 2; i < n; i++) {
			upper.add (points[i]);
			while (upper.hasThree() && upper.areLastThreeNonRight()) {
				upper.removeMiddleOfLastThree();
			}
		}
		
		// Compute lower hull by starting with rightmost two points
		PartialHull lower = new PartialHull (points[n-1], points[n-2]);
		for (int i = n-3; i >=0; i--) {
			lower.add (points[i]);
			while (lower.hasThree() && lower.areLastThreeNonRight()) {
				lower.removeMiddleOfLastThree();
			}
		}
		
		// remove duplicate end points when combining.
		IPoint[] hull = new IPoint[upper.size()+lower.size()-2];
		System.arraycopy (upper.getPoints(), 0, hull, 0, upper.size());
		System.arraycopy (lower.getPoints(), 1, hull, upper.size(), lower.size()-2);
		
		return hull;
	}
}