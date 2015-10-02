package algs.model.kdtree;

import java.util.Comparator;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.array.Selection;

/**
 * Produces a TwoD-tree from a given input set using recursive median approach.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDFactory {

	/** The 1st element compares along dimension 1 (x) and the second y. */
	private static final Comparator<?> comparators [] = {
		null,
		new Comparator<IPoint>() {
			public int compare(IPoint o1, IPoint o2) {
				double v = FloatingPoint.value(o1.getX() - o2.getX());
				return (int) v;
			}
		},
		new Comparator<IPoint>() {
			public int compare(IPoint o1, IPoint o2) {
				double v = FloatingPoint.value(o1.getY() - o2.getY());
				return (int) v;
			}
		}
	};
	
	/**
	 * Generate a TwoDTree from the given array of points.
	 * <p>
	 * If points is null, then return null since the dimensionality
	 * is unknown.
	 * <p>
	 * All points must have the same dimensionality, otherwise strange
	 * behavior may occur. Also, this method is not re-entrant, since the 
	 * comparators array is regenerated upon each invocation, thus we mark
	 * the method as 'synchronized'.
	 * 
	 * @param points    initial set of points to insert into TwoDTree
	 * @return          balanced TwoDTree representing these points
	 */
	public static synchronized TwoDTree generate (IPoint []points) {
		if (points.length == 0) { return null; }
		
		// median will be the root.
		TwoDTree tree = new TwoDTree();
		
		// Tree will be constructed, but since it was done bottom up, all of the
		// rectangles are invalid. These must be re-propagated.
		VerticalNode root = (VerticalNode) generate (1, points, 0, points.length-1);
		tree.setRoot (root);
		tree.updateRectangles();  // should always be called after 'setRoot'.
		return tree;
	}
		
	/** Helper method to properly construct appropriate node class. */
	private static TwoDNode construct (int d, IPoint p) {
		if (d == 1) {
			return new VerticalNode (p); 
		} else {
			return new HorizontalNode (p);
		}
	}
	
	/** Helper method to generate TwoDNode for tree. */
	private static TwoDNode generate (int d, IPoint points[], int left, int right) {
		
		// Handle the easy cases first
		if (right < left) { return null; }
		if (right == left) {
			return construct (d, points[left]);
		}
		
        // Order the array[left,right] so the mth element will be the median
        // and the elements prior to it will all be smaller, though they won't
        // necessarily be sorted; similarly, the elements after will all be larger, 
        // though they won't necessarily be sorted.
		int m = 1+(right-left)/2;
		Selection.select(points, m, left, right, comparators[d]);
		
		// this will be the parent
		TwoDNode dm = construct (d, points[left+m-1]);
		
		// update to the next dimension, or reset back to 1
		if (++d > 2) { d = 1; }
		
		// compute left and right children, which translate into 'below'
		// and 'above' for n-dimensions.
		dm.setBelow(generate (d, points, left, left+m-2));
		dm.setAbove(generate (d, points, left+m, right));
		return dm;
	}	
}
