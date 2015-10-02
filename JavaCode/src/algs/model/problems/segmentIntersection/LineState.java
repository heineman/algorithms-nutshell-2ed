package algs.model.problems.segmentIntersection;

import java.util.Comparator;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.list.Node;

/**
 * Manages the state of segments in a balanced binary tree whose 
 * leaf nodes are used to store segments while the interior nodes
 * are used to guide searches and insertions to the appropriate leaf nodes.
 * <p>
 * Defines a comparator seg_order which compares line segments that
 * are known to have x-values on the sweepPt y-value horizontal line.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LineState  {
	
	/** Location of current sweep line. Needed to properly compare segments. */
	IPoint sweepPt;
	
	/** Useful debugging information can be generated. */
	boolean debug = false;
	
	/**
	 * The key point about this comparator is that it is only called when both 
	 * line segments intersect the horizontal line defined by the y-value of the sweep pt.
	 * If the above is not true, then the two line segments are actually incomparable.
	 * <p>
	 * One last point. This method is only invoked from within the insert method
	 * where o1 already has been placed in the tree. Thus we know that o2 contains
	 * the sweep point. Perhaps this optimization is unnecessary...
	 * <p>
	 * Once this point is clear, then it is simple how to order the segments.
	 * <ul>
	 * <li> if they do not intersect, compare by their x-values as defined at the 
	 *     sweep pt. Note if either line is horizontal, this still works because
	 *     we checked first whether the lines actually intersected.
	 * <li> if they do intersect, then we need to know where we are in the sweep 
	 *     algorithm, since at the intersection point, lines change their ordering
	 *     to properly make the algorithm work.
	 * </ul> 
	 */
	public Comparator<ILineSegment> seg_order = new Comparator<ILineSegment>() {

		public int compare(ILineSegment o1, ILineSegment o2) {
			IPoint p = o1.intersection(o2);
			if (p == null) {
				// we know that the sweepPt is on o2, so we simply determine
				// the side that o1 falls upon. We know this since we are only
				// invoked by the insert method where o1 already exists in the tree
				// and o2 is the newly added segment.
				if (o1.pointOnRight(sweepPt)) { return -1; }
				if (o1.pointOnLeft(sweepPt)) { return +1; }
				return 0;
			}
			
			// Does intersection occur above sweep point? If so, then reverse standard
			// left-to-right ordering; if intersection is below the sweep line, then 
			// use standard left-to-right ordering
			if (EventPoint.pointSorter.compare(p, sweepPt) > 0) {
				if (o1.pointOnRight(o2.getStart())) {
					return -1;
				} else {
					return +1;
				}
			} else {
				if (o1.pointOnRight(o2.getEnd())) {
					return -1;
				} else {
					return +1;
				}
			}
		}
	};

	/** Line state stored here. Use the dynamic comparator based on sweepPt. */
	AugmentedBalancedTree<ILineSegment> state = 
		new AugmentedBalancedTree<ILineSegment>(seg_order);
 
	/** 
	 * Helper debugging method to return root of the state. 
	 * @return root node 
	 */
	public AugmentedNode<ILineSegment> root() {
		return state.root();
	}
	
	/**
	 * Return minimum node in state tree (or null if state tree is empty).
	 * @return minimum node in the line state
	 */
	private AugmentedNode<ILineSegment> getMinimumInTree() {
		
		AugmentedNode<ILineSegment> n =  state.root();
		if (n == null) { return null; }
		
		while (n.left() != null) {
			n = n.left();
		}
		
		return n; 
	}
	
	/**
	 * Only intersections are allowed with neighboring segments in the line state. Thus we
	 * check from the successor of left, right through (but not including) right.
	 * <p>
	 * These left and right are the first segments that match. 
	 * 
	 * @param p      {@link EventPoint} under examination
	 * @param left	 node which was determined to be left of this point
	 * @param right  node which was determined to be right of this point
	 */
	public void determineIntersecting(EventPoint p, 
			AugmentedNode<ILineSegment> left, AugmentedNode<ILineSegment> right) {
		// empty? Nothing to do.
		if (sweepPt == null) { return; }
		
		if (left == null) {
			// must get minimum one in tree
			left = getMinimumInTree();
		} else {
			left = successor (left);
		}
		
		while (left != right) {
			ILineSegment ils = left.key();
			
			if (ils != null) {
				// Can ignore start and end because those intersection types are already handled.
				if (!ils.getStart().equals(p.point) && !ils.getEnd().equals(p.point)) {
					p.addIntersectingLineSegment(ils);
				}
			}
			
			left = successor(left);
		}
	}

	/** 
	 * Set where the sweep line is to appear.
	 * 
	 * @param pt   the sweep point to use
	 */
	public void setSweepPoint (IPoint pt) {
		this.sweepPt = pt;
	}

	/**
	 * Insert the segments into the line state.
	 * <p>
	 * It is worth noting that each of these segments being inserted
	 * is guaranteed to be on the sweep point.
	 * 
	 * @param list    list of ILineSegments to insert.
	 */
	public void insertSegments(List<ILineSegment> list) {
		Node<ILineSegment> node = list.head();
		while (node != null) {
			state.insert(node.value());
			
			node = node.next();
		}
	}
	
	/** 
	 * Find node within the state that is the closest neighbor (on the left)
	 * to the given event point. Make a line from the given point to the x-intersection on the sweep line.
	 * <p>
	 * If we find multiple with same point, we have to keep on going to the left.
	 * Specifically, if compare returns 0, we keep going to the left.
	 * 
	 * @param ep   event point for which you want the left neighbor in the line state
	 * @return     node in line state which is left neighbor of the given ep
	 */
	public AugmentedNode<ILineSegment> leftNeighbor(EventPoint ep) {
		AugmentedNode<ILineSegment> n = state.root();
		if (n == null) { return null; }
		
		while (n.key() == null) {
			AugmentedNode<ILineSegment> rt = n.right();
			
			if (rt.min.pointOnRight (ep.point)) {
				n = n.right();
			} else {
				n = n.left();
			}
			
		}
		
		if (n.key().pointOnRight(ep.point)) {
			return n;
		}
		
		return null;	
	}

	
	/** 
	 * Find segment within the state that is the closest neighbor (on the right)
	 * to the given event point.
	 * <p>
	 * If we find multiple with same point, we have to keep on going to the right.
	 * Specifically, if compare returns 0, we keep going to the right.
	 *
	 * @param ep   event point for which you want the right neighbor in the line state
	 * @return     node in line state which is right neighbor of the given ep
	 */
	public AugmentedNode<ILineSegment> rightNeighbor(EventPoint ep) {
		AugmentedNode<ILineSegment> n = state.root();
		if (n == null) { return null; }
		
		while (n.key() == null) {
			AugmentedNode<ILineSegment> lf = n.left();

			if (lf.max.pointOnLeft (ep.point)) {
				n = n.left();
			} else {
				n = n.right();
			}
		}
		
		if (n.key().pointOnLeft(ep.point)) {
			return n;
		}
		
		return null;	
	}

	/**
	 * Return successor leaf in the tree.
	 * <p>
	 * We can be guaranteed to be called with a LEAF node, since interior
	 * nodes are only guiding the process.
	 * 
	 * @param n  node being queried
	 * 
	 * @return   leaf in the tree that is the next segment to the right (or null
	 *           if no such node exists).
	 */
	public AugmentedNode<ILineSegment> successor(AugmentedNode<ILineSegment> n) {
		// If we are the right-child of a node, must go back.
		while ((n != state.root() && n == n.parent().right())) {
			n = n.parent();
		}

		// Now we have reached a node by whose parent we are not the right.
		// If we are indeed at the root, then no successor...
		if (n == state.root()) { return null; }

		// Otherwise go to the right, and find the left-most child. This node is the successor 
		n = n.parent().right();

		while (n.left() != null) {
			n = n.left();
		}

		return n;
	}

	/**
	 * Return predecessor leaf in the tree.
	 * <p>
	 * We can be guaranteed to be called with a LEAF node, since interior
	 * nodes are only guiding the process.
	 * 
	 * @param n  node being queried
	 * 
	 * @return   leaf in the tree that is the next segment to the right (or null
	 *           if no such node exists).
	 */
	public AugmentedNode<ILineSegment> pred(AugmentedNode<ILineSegment> n) {
		// If we are the left-child of a node, must go back.
		while ((n != state.root() && n == n.parent().left())) {
			n = n.parent();
		}

		// Now we have reached a node by whose parent we are not the right.
		// If we are indeed at the root, then no successor...
		if (n == state.root()) { return null; }

		// Otherwise go to the left, and find the right-most child. This node is the pred 
		n = n.parent().left();

		while (n.right() != null) {
			n = n.right();
		}

		return n;
	}

	/**
	 * Delete everything from the successor of left until the successor of left is right.
	 * <p>
	 * All of these line segments potentially need to be reorganized once
	 * the sweep point advances.
	 * 
	 * @param left    leftmost range of segments to remove
	 * @param right   rightmost range of segments to remove
	 */
	public void deleteRange(AugmentedNode<ILineSegment> left, AugmentedNode<ILineSegment> right) {
		AugmentedNode<ILineSegment> t;
		if (left == null) { t = getMinimumInTree(); } else { t = successor (left); }
		
		while (t != right) {
			state.deleteEntry(t);
		
			// note: We always go back to the original spot, since we want to drain everything
			// in between. Note that 'left' never leaves the tree. Convenient!
			if (left == null) { t = getMinimumInTree(); } else { t = successor (left); }
		}
	}

	/**
	 * Retrieve sweep point.
	 * @return point representing actual sweep point. 
	 */
	public IPoint getSweepPoint() {
		return sweepPt;
	}

}
