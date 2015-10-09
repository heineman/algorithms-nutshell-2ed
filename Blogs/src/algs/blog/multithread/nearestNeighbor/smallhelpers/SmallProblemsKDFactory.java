package algs.blog.multithread.nearestNeighbor.smallhelpers;

import java.util.Comparator;

import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.array.Selection;
import algs.model.kdtree.DimensionalComparator;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.twod.TwoDPoint;

/**
 * Produces a KD-tree from a given input set using recursive median approach.
 * <p>
 * Note that we take care to construct the associated {@link Hypercube} regions with each
 * node to ensure the integrity of the regions; without these regions being properly
 * set, there is no way to "drain" the children of a subtree when a query wholly 
 * contains a subtree's region.  
 * <p>
 * Nodes of this tree contain a multi-threaded implementation of the 
 * {@link DimensionalNode#nearest(double[], double[])} method, as an alternate
 * for comparison.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class SmallProblemsKDFactory {

	/** 
	 * Known comparators for partitioning points along dimensional axes.
	 * <p>
	 * Uses 1-based access for ease of programming, so always is one larger
	 * than necessary.
	 */
	private static Comparator<IMultiPoint> comparators[];
	
	/**
	 * Generate a KDTree from the given array of points.
	 * <p>
	 * If points is null, then return null since the dimensionality
	 * is unknown.
	 * <p>
	 * All points must have the same dimensionality, otherwise strange
	 * behavior may occur. Also, this method is not re-entrant, since the 
	 * comparators array is regenerated upon each invocation, thus we mark
	 * the method as 'synchronized'.
	 * 
	 * @param points    points which are to be used as source to generate KDTree.
	 */
	@SuppressWarnings("unchecked")
	public static synchronized SmallProblemsKDTree generate (IMultiPoint []points) {
		if (points.length == 0) { return null; }
		
		// median will be the root.
		int maxD = points[0].dimensionality();
		SmallProblemsKDTree tree = new SmallProblemsKDTree(maxD);
		
        // Make dimensional comparators that compare points by ith dimension
		comparators = new Comparator[maxD+1];
		for (int i = 1; i <= maxD; i++) {
			comparators[i] = new DimensionalComparator(i);
		}
		
		tree.setRoot(generate (1, maxD, points, 0, points.length-1));
		return tree;
	}
		
	/**
	 * Generate a KDTree from the given array of IPoints.
     *
     * If underlying objects in points[] already implement IMultiPoint, then good. Otherwise
     * a new array of TwoDPoint objects is constructed to be passed into the generate method
     * on an IMultiPoint[] array (where the values for these TwoDPoints are extracted
     * from the X and Y coordinate values of the IPoint objects).
     *
	 * @param points
	 */
	public static synchronized KDTree generate (IPoint []points) {
		if (points.length == 0) { return null; }
		
		// convert all IPoint into IMultiPoint. If not an instance, do the hard way.
		IMultiPoint others[] = new IMultiPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			if (points[i] instanceof IMultiPoint) {
				others[i] = (IMultiPoint) points[i];
			} else {
				others[i] = new TwoDPoint(points[i].getX(), points[i].getY());
			}
		}
		
		return generate (others);
	}
	
	
	/** Helper method for IMultiPoint. */
	private static DimensionalNode generate (int d, int maxD, IMultiPoint points[], int left, int right) {
		
		// Handle the easy cases first
		if (right < left) { return null; }
		if (right == left) { return new SmallProblemsKDNode (d, points[left]); }
		
        // Order the array[left,right] so the mth element will be the median
        // and the elements prior to it will all be <=, though they won't 
		// necessarily be sorted; similarly, elements after will all be >= 
		int m = 1+(right-left)/2;
		Selection.select(points, m, left, right, comparators[d]);
		
        // Median point on this dimension becomes the parent
		SmallProblemsKDNode dm = new SmallProblemsKDNode (d, points[left+m-1]);
		
		// update to the next dimension, or reset back to 1
		if (++d > maxD) { d = 1; }
		
		// recursively compute left and right sub-trees, which translate into
		// 'below' and 'above' for n-dimensions.
		dm.setBelow(generate (d, maxD, points, left, left+m-2));
		dm.setAbove(generate (d, maxD, points, left+m, right));
		return dm;
	}	
}
