package algs.model.problems.segmentIntersection.linkedlist;

import java.util.Comparator;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.list.List;
import algs.model.list.Node;
import algs.model.problems.segmentIntersection.EventPoint;
import algs.model.problems.segmentIntersection.LineState;

/**
 * Class that shows the performance degradation when the line state is stored 
 * using a linked list instead of an augmented, balanced binary tree.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LinkedListLineState  {

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
	
	/** use comparator for insertions. */
	DoubleLinkedList<ILineSegment> dlstate = 
		new DoubleLinkedList<ILineSegment>(seg_order);
	
	/**
	 * Inspect range of lines in [left, right] to see if they should be added 
	 * to the intersecting line set of p.
	 * 
	 * @param p             The sweep point.
	 * @param left          Left most line in the state under consideration 
	 * @param right         Right most line in the state under consideration
	 */
	void determineIntersecting(EventPoint p, 
			DoubleNode<ILineSegment> left, DoubleNode<ILineSegment> right) {
		// empty? Nothing to do.
		if (sweepPt == null) { return; }
		
		if (left == null) {
			// must get minimum one in state
			left = dlstate.first();
		} else {
			left = successor (left);
		}
		
		while (left != right) {
			ILineSegment ils = left.value();
			
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
	 * @see LineState#setSweepPoint(IPoint)
	 * 
	 * @param sweep   the new Sweep Point.
	 */
	public void setSweepPoint (IPoint sweep) {
		this.sweepPt = sweep;
	}
	
	/**
	 * @see LineState#leftNeighbor(EventPoint)
	 * 
	 * @param ep   point for which we want the left neighbor in the line state.
	 * @return     node which is left neighbor in the line state for query point
	 */
	public DoubleNode<ILineSegment> leftNeighbor(EventPoint ep) {
		DoubleNode<ILineSegment> n = dlstate.first();
		if (n == null) { return null; }
		DoubleNode<ILineSegment> prev = null;
		
		while (n.value().pointOnRight(ep.point)) {
			prev = n;
			n = n.next();
			if (n == null) { break; }
		}
		
		return prev;	
	}
	
	/**
	 * @see LineState#rightNeighbor(EventPoint)
	 * 
	 * @param ep   point for which we want the right neighbor in the line state.
	 * @return     node which is right neighbor in the line state for query point
	 */
	public DoubleNode<ILineSegment> rightNeighbor(EventPoint ep) {
		DoubleNode<ILineSegment> n = dlstate.last();
		if (n == null) { return null; }
		DoubleNode<ILineSegment> prev = null;
	
		while (n.value().pointOnLeft(ep.point)) {
			prev = n;
			n = n.prev();
			if (n == null) break;
		}
		
		return prev;	
	}
	
	/** 
	 * Return successor line segment in the state to given one.
	 *  
	 * @param n   given line segment
	 * @return    successor to right of given line segment in state.
	 */
	public DoubleNode<ILineSegment> successor(DoubleNode<ILineSegment> n) {
		return n.next();
	}
	
	/** 
	 * Return predecessor line segment in the state to given one.
	 *  
	 * @param n   given line segment
	 * @return    predecessor to left of given line segment in state.
	 */
	public DoubleNode<ILineSegment> pred(DoubleNode<ILineSegment> n) {
		return n.prev();
	}
	
	/**
	 * insert the set of line segments into the state at their proper location
	 * based upon the sort order.
	 * 
	 * @param list   set of line segments to insert into the state.
	 */
	public void insertSegments(List<ILineSegment> list) {
		Node<ILineSegment> node = list.head();
		while (node != null) {
			dlstate.insert(node.value());
			
			node = node.next();
		}
	}
	
	/**
	 * Delete all line segments from the state in the range (left, right).
	 * <p>
	 * Note that left and right are not removed from the state.
	 * 
	 * @param left   leftmost boundary of the segments to remove
	 * @param right  rightmost boundary of the segments to remove
	 */
	public void deleteRange(DoubleNode<ILineSegment> left, DoubleNode<ILineSegment> right) {
		DoubleNode<ILineSegment> t;
		if (left == null) { t = dlstate.first(); } else { t = successor (left); }
		
		while (t != right) {
			dlstate.remove(t);
			
			// note: We always go back to the original spot, since we want to drain everything
			// in between. Note that 'left' never leaves the tree. Convenient!
			if (left == null) { t = dlstate.first(); } else { t = successor (left); }
		}
	}
}
