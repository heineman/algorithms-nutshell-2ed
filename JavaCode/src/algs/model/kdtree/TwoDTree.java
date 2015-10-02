package algs.model.kdtree;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.IPoint;
import algs.model.IRectangle;
import algs.model.twod.TwoDRectangle;

/**
 * Standard unbalanced 2-dimensional binary tree.
 * <p>
 * Stores a set of points against which one can execute 2-dimensional range queries
 * against a rectangle D whose domain is defined by a rectangle D=[x1,x2] x [y1,y2].
 * Note that the rectangle could be infinite in none, one, or two of these dimensions
 * by having any of its coordinates set to Double.NEGATIVE_INFINITY or 
 * Double.POSITIVE_INFINITY. A rectangle could be one-dimensional (if either x1==x2 or
 * y1==y2) or zero-dimensional (if both x1==x2 and y1==y2).
 * <p>
 * This data structure offers no remove functionality.
 * <p>
 * Note that the example above is for Double values; the node values stored could be
 * of any type that implements Comparable. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDTree {

	/** Root is vertical. */
	VerticalNode root = null;
	
	/**
	 * Insert the value into its proper location.
	 * 
	 * No balancing is performed.
	 * 
	 * @param value  non-null value to be added into the tree.
	 * @exception    IllegalArgumentException if value is null
 	 */
	public void insert (IPoint value) {
		if (value == null) {
			throw new IllegalArgumentException ("unable to insert null value into TwoDTree");
		}
		
		if (root == null) {
			root = new VerticalNode(value);
			return;
		}
		
		// we walk down the tree iteratively, varying from vertical to horizontal
		TwoDNode node = root;
		TwoDNode next;
		do {
			/** if this point is below node, search that location. */
			if (node.isBelow(value)) {
				next = node.getBelow();
				if (next == null) {
					/** insert here! */
					node.setBelow(node.construct(value));
					break;
				} else {
					node = next;
				}
			} else {
				next = node.getAbove();
				if (next == null) {
					/** insert here! */
					node.setAbove(node.construct(value));
					break;
				} else {
					node = next;
				}
			}
		} while (node != null);
	}
	
	/**
	 * Return the root of the TwoDTree, which is guaranteed to be a Vertical Node.
	 * @return VerticalNode root node of this two-dimensional kd-tree
	 */
	public VerticalNode getRoot() {
		return root;
	}
	
	/** 
	 * Helper method for testing.
	 * @return count of nodes in this tree. 
	 */
	public int count () { 
		if (root == null) { return 0; }
		return root.count();
	}
	
	/** 
	 * Return the parent of the point IF the point were to be inserted into the 2d-tree.
	 * 
	 * Returns null only if the tree is empty to begin with (i.e., there are no parents).
	 * 
	 * Note that you will have to inspect the dimension for the TwoNode to determine
	 * if this is a Below or an Above parent.
	 * @param value    query point for whom we want to determine parent if it were added.
	 * @return         parent node for this point if it were added.
	 */
	public TwoDNode parent (IPoint value) {
		if (value == null) {
			throw new IllegalArgumentException ("unable to insert null value into TwoDTree");
		}
		
		if (root == null) return null;
		
		// we walk down the tree iteratively, varying from vertical to horizontal
		TwoDNode node = root;
		TwoDNode next;
		while (node != null) {
			/** if this point is below node, search that location. */
			if (node.isBelow(value)) {
				next = node.getBelow();
				if (next == null) {
					// break and return node
					break;
				} else {
					node = next;
				}
			} else {
				next = node.getAbove();
				if (next == null) {
					// break and return node
					break;
				} else {
					node = next;
				}
			}
		}
		
		return node;
	}
	
	
	/**
	 * Set the root of the TwoDTree.
	 * <p>
	 * Use with Caution! One issue that may cause problems is when the new root does
	 * not have 'boundless' space associated with it.
	 * <p>
	 * Note that @see {@link #updateRectangles()} should always be called after 
	 * invoking this function, to ensure rectangles are all properly computed. 
	 * @param  node    new parent node for this tree
	 */
	public void setRoot(VerticalNode node) {
		root = node;
	}
	
	
	/**
	 * Return the Euclidean distance between the given two points.
	 * 
	 * @param p1   first point
	 * @param p2   second point
	 * @return     Euclidean distance between given points.
	 */
	static double distance (IPoint p1, IPoint p2) {
		return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX()) +
				(p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
	}
	
	/**
	 * Find the nearest point in the TwoDtree to the given point.
	 * <p>
	 * Only returns null if the tree was initially empty or if the
	 * target is null. Otherwise, must return some point that belongs 
	 * to the tree.
	 * 
	 * @param target   point which serves as focus of the search.
	 * @return         point which is nearest to target point.
	 */
	public IPoint nearest (IPoint target) {
		if (root == null || target == null) return null;
	
		// find parent node to which target would have been inserted. This is our
	    // best shot at locating closest point; compute best distance guess so far
		TwoDNode parent = parent(target);
		IPoint result = parent.point;
		double smallest = distance(target, result);
		
		// now start back at the root, and check all rectangles that potentially
		// overlap this smallest distance. If better one is found, return it.
		double best[] = new double[] {smallest };  // computed best distance.
		
		IPoint betterOne = root.nearest (target, best);
		if (betterOne != null) { return betterOne; }
		return result;
	}
	
	/**
	 * Locate all points within the TwoDTree that fall within the given rectangle.
	 * 
	 * @param space     non-null rectangular region within which to search.
	 * @exception       NullPointerException if space or visitor is null.
 	 * @return          Iterator of IPoints that fall within the given space.
 	 */
	public Iterator<IPoint> range (IRectangle space) {
		if (root == null) {
			return new ArrayList<IPoint>().iterator();
		}
		
		// search, placing results into 'results'.
		TwoDSearchResults results = new TwoDSearchResults();
		root.range(space, results);
		
		return results;
	}
	
	/**
	 * Locate all points within the TwoDTree that fall within the given rectangle and
	 * visit those nodes via the given visitor.
	 * 
	 * @param space     non-null space within which search occurs.
	 * @param visitor   non-null {@link IVisitTwoDNode} visitor.
	 * @exception       NullPointerException if space or visitor is null.
 	 */
	public void range (IRectangle space, IVisitTwoDNode visitor) {
		if (root == null) {
			return;
		}
		
		// search, visiting those nodes that are identified, rather than storing.
		root.range(space, visitor);
	}
	
	/** Helper method for toString(). */
	private void buildString (StringBuilder sb, TwoDNode node) {
		if (node == null) { return; }
		
		TwoDNode left = node.getBelow();
		TwoDNode right = node.getAbove();
		
		if (left != null) { buildString(sb, left); }
		sb.append (node.toString());
		if (right != null) { buildString (sb, right); }
	}
	
	/**
	 * Reasonable toString to aid debugging.
	 * @return reasonable string representation. 
	 */
	public String toString () {
		if (root == null) { return ""; }
		
		StringBuilder sb = new StringBuilder();
		buildString (sb, root);
		return sb.toString();
	}

	/** Propagate all rectangles down to leaves. */
	public void updateRectangles() {
		VerticalNode vn = getRoot();
		vn.region = new TwoDRectangle (Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
			    Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		vn.specialUpdateRectangle();
	}
}
