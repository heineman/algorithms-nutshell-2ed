package algs.model.kdtree;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.IHypercube;
import algs.model.IMultiPoint;
import algs.model.nd.Hypercube;

/**
 * Standard unbalanced k-dimensional binary tree.
 * <p>
 * Stores a set of points against which one can execute 2-dimensional range queries
 * against a rectangle D whose domain is defined by a rectangle D=[x1,x2] x [y1,y2].
 * Note that the rectangle could be infinite in none, one, or two of these dimensions
 * by having any of its coordinates set to Double.NEGATIVE_INFINITY or 
 * Double.POSITIVE_INFINITY. A rectangle could be one-dimensional (if either x1==x2 or
 * y1==y2) or zero-dimensional (if both x1==x2 and y1==y2).
 * <p>
 * Note that the example above is for Double values; the node values stored could be
 * of any type that implements Comparable. 
 * <p>
 * Original source derived from Bentley [1975].
 * http://portal.acm.org/citation.cfm?id=361007
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class KDTree {

	/** Root of the tree. Initial dimensionality is going to be 1. */
	private DimensionalNode root;
	
	/** Maximum dimension for this tree. */
	public final int maxDimension;

	/** debugging output. */
	boolean debug = false;
	
	// debugging stats
	private int numRecursion=0;
	private int numDoubleRecursion=0;
	
	/** 
	 * Helper method to always determine the next dimensionality given a node.
	 *
	 * Used in test cases.
	 * 
	 * @param   node     returns dimension to use for children of this node.
	 * @return  integer value representing the next dimension in order.
	 */ 
	public int nextDimension (DimensionalNode node) {
		return nextDimension (node.dimension);
	}
	
	/** 
	 * Helper method to always determine the next dimensionality given an integer
	 * representing the specified dimension in the KDTree.
	 * 
	 * Used in test cases.
	 * 
	 * @param   d     returns dimension of children nodes for next dimension.
	 * @return        integer representing next dimension (minimum is 1).
	 */ 
	public int nextDimension (int d) {
		if (d == maxDimension) {
			return 1;
		}
		
		return d+1;
	}
	
	/**
	 * Default KDTree constructor.
	 * 
	 * @param md    Maximum dimension of nodes within this tree.
	 */
	public KDTree(int md) {
		if (md < 1) {
			throw new IllegalArgumentException ("KD tree must have at least one dimension.");
		}
		
		root = null;
		maxDimension = md;
	}
	
	/**
	 * Empty the KDTree of all of its internal points.
	 *
	 * Leave the maximum dimensionality the same.
	 */
	public void removeAll() {
		root = null;
	}
		
	/** 
	 * Return the parent of the point IF the point were to be inserted into the kd-tree.
	 * 
	 * Returns null only if the tree is empty to begin with (i.e., there are no parents).
	 * 
	 * Note that you will have to inspect the dimension for the DimensionalNode to determine
	 * if this is a Below or an Above parent.
	 * @param value    proposed value which could be inserted (but is not).
	 * @return    node which would be parent if given value were to be inserted
	 */
	public DimensionalNode parent (IMultiPoint value) {
		if (value == null) {
			throw new IllegalArgumentException ("unable to insert null value into KDTree");
		}
		
		if (root == null) { return null; }
		
		// we walk down the tree iteratively, varying from vertical to horizontal
		DimensionalNode node = root;
		DimensionalNode next;
		while (node != null) {
			/** if this point is below node, search that location. */
			if (node.isBelow(value)) {
				next = node.getBelow();
				if (next == null) {
					// break and return this node
					break;
				} else {
					node = next;
				}
			} else {
				next = node.getAbove();
				if (next == null) {
					// break and return this node
					break;
				} else {
					node = next;
				}
			}
		}
		
		return node;
	}
	
	/**
	 * Insert the value into its proper location.
	 * <p>
	 * No balancing is performed.
	 * <p>
	 * isBelow checks for strict less than. This means that (by default) equal
     * coordinates are going to appear in above Subtree
	 * 
	 * @param value  non-null value to be added into the tree.
	 * @exception    IllegalArgumentException if value is null
 	 */
	public void insert (IMultiPoint value) {
		if (value == null) {
			throw new IllegalArgumentException ("unable to insert null value into KDTree");
		}
		
		if (root == null) {
			root = new DimensionalNode(1, value);
			return;
		}
		
		// we walk down the tree iteratively, varying from vertical to horizontal
		DimensionalNode node = root;
		DimensionalNode next;
		while (node != null) {
			/** if this point is below node, search that location. */
			if (node.isBelow(value)) {
				next = node.getBelow();
				if (next == null) {
					/** insert here! Break and return. */
					node.setBelow(node.construct(value));
					break;
				} else {
					node = next;
				}
			} else {
				next = node.getAbove();
				if (next == null) {
					/** insert here! Break and return. */
					node.setAbove(node.construct(value));
					return;
				} else {
					node = next;
				}
			}
		}
	}
	 
	/**
	 * Return the root of the KDTree.
	 * @return   root of the KD tree.
	 */
	public DimensionalNode getRoot() {
		return root;
	}
	
	/**
	 * Set the root of the KDTree.
	 * 
	 * Use with Caution! One issue that may cause problems is when the new root does
	 * not have 'boundless' space associated with it, so the initial infinite region
	 * is propagated throughout the tree whenever the root is set.
	 * 
	 * @param newRoot    The desired node to be the root of the tree.
	 */
	public void setRoot(DimensionalNode newRoot) {
		root = newRoot;
		if (root == null) { 
			return;
		}
		
		// construct initial region which will be partitioned by nodes as we recurse through tree.
		double [] lows = new double[maxDimension];
		double [] highs = new double[maxDimension];
		for (int i = 0; i < maxDimension; i++) {
			lows[i] = Double.NEGATIVE_INFINITY;
			highs[i] = Double.POSITIVE_INFINITY;
		}
		                          
		Hypercube region = new Hypercube(lows, highs);
		root.propagate(region);
	}
	
	/**
	 * Find the nearest point in the KDtree to the given point.
	 * <p>
	 * Only returns null if the tree was initially empty. Otherwise, must
	 * return some point that belongs to the tree.
	 * <p>
	 * If tree is empty or if the target is <code>null</code> then
	 * <code>null</code> is returned.
	 * 
	 * @param   target    the target of the search.
	 * @return  closest {@link IMultiPoint} object in the tree to the target {@link IMultiPoint} 
	 */
	public IMultiPoint nearest (IMultiPoint target) {
		if (root == null || target == null) return null;
	
		// find parent node to which target would have been inserted. 
	    // Best shot at finding closest point.
		DimensionalNode parent = parent(target);
		IMultiPoint result = parent.point;
		double smallest = target.distance(result);
		
		// Start back at the root to try to find closer one.
		numRecursion=0;        /* STATS */
		numDoubleRecursion=0;  /* STATS */
		double best[] = new double[] { smallest };  // computed best distance.
		
		double raw[] = target.raw();
		IMultiPoint betterOne = root.nearest (raw, best);
		if (betterOne != null) { return betterOne; }
		return result;
	}

	/**
	 * Locate all points within the KDTree that fall within the given IHypercube.
	 * 
	 * @since 2.0       changed to allow efficient retrieval via iterators
	 * 
	 * @param space     non-null space in which to search
	 * @exception       NullPointerException if space is null
	 * @return          Iterator of MultiPoints that fall within the given space.
 	 */
	public Iterator<IMultiPoint> range (IHypercube space) {
		if (root == null) {
			return new ArrayList<IMultiPoint>().iterator();
		}
		
		// search, placing results into 'results'.
		KDSearchResults results = new KDSearchResults();
		root.range(space, results);
		
		return results;
	}
	
	/**
	 * Locate all points within the TwoDTree that fall within the given 
	 * IHypercube and visit those nodes via the given visitor.
	 * 
	 * @param space     non-null space within which to search.
	 * @param visitor   non-null IVisitTwoDNode visitor that contains behavior to process at
	 *                  each discovered point within the space.
	 * @exception       NullPointerException if space or visitor is null
 	 */
	public void range (IHypercube space, IVisitKDNode visitor) {
		if (root == null) {
			return;
		}
		
		// search, visiting those nodes that are identified, rather than storing.
		root.range(space, visitor);
	}
	
	
	/** 
	 * Helper method for toString().
	 * 
	 * @param sb
	 * @param node
	 */
	private void buildString (StringBuilder sb, DimensionalNode node) {
		if (node == null) { return; }
		
		DimensionalNode left = node.getBelow();
		DimensionalNode right = node.getAbove();
		
		if (left != null) { buildString(sb, left); }
		sb.append (node.toString());
		if (right != null) { buildString (sb, right); }
	}
	
	/** 
	 * Reasonable toString to aid debugging.
	 * @return human readable string.
	 */
	public String toString () {
		if (root == null) { return ""; }
		
		StringBuilder sb = new StringBuilder();
		buildString (sb, root);
		return sb.toString();
	}
	
	/** 
	 * Helper method for testing.
	 * @return number of nodes in the KD tree. 
	 */
	public int count () { 
		if (root == null) { return 0; }
		return root.count();
	}

	/** 
	 * Helper method for testing.
	 * @return height of the KD tree. 
	 */
	public int height () { 
		if (root == null) { return 0; }
		return root.height();
	}
	
	// debugging output
	public int getNumRecursion() { return numRecursion; }
	public int getNumDoubleRecursion() { return numDoubleRecursion; }

}

