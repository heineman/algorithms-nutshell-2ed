package algs.model.problems.segmentIntersection.priorityqueue;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.AugmentedNode;
import algs.model.problems.segmentIntersection.EventPoint;
import algs.model.problems.segmentIntersection.IntersectionDetection;
import algs.model.problems.segmentIntersection.LineState;

/**
 * Uses slow event queue to show degradation of performance when
 * using just a standard priority queue implementation.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlowLineSweep extends IntersectionDetection {

    LineState lineState;     // Store state as sweep progresses.
    SlowEventQueue eq;       // Event queue of event Points.

	public SlowLineSweep  () {
		super();
	}

	/** Initialize algorithm. */
	public void initialize() {
		lineState = new LineState();
		eq = new SlowEventQueue();
		super.initialize();
	}
	
	/**
	 * Compute the intersection of all segments when given an array of segments.
	 * <p>
	 * Returns the report which is maintained by the superclass
	 * 
	 * @param segs          Line segments to be checked for intersections.
	 */
	public Hashtable<IPoint,List<ILineSegment>> intersections (ILineSegment[] segs) {
		startTime();
		initialize();
		
		// construct Event Queue from initial segments. Ensure that only unique points
		// appear in the Event Queue by combining all information as it is discovered 
		for (ILineSegment ils : segs) {
			EventPoint old, ep = new EventPoint(ils.getStart());
			if ((old = eq.event(ep)) == null) {
				eq.insert(ep);
			} else {
				ep = old;
			}
			ep.addUpperLineSegment(ils);

			ep = new EventPoint(ils.getEnd());
			if ((old = eq.event(ep)) == null) {
				eq.insert(ep);
			} else {
				ep = old;
			}
			ep.addLowerLineSegment(ils);
		}

		// Sweep top to bottom, processing each Event Point in the queue
		while (!eq.isEmpty()) {
			EventPoint p = eq.min();
			handleEventPoint(p);
		}

		computeTime();
		return report;
	}

	/** 
	 * Handle the event point.
	 * 
	 * Suppress warnings because of Java's inability to create a generic 
	 * array of list arguments (in report method call). 
	 * 
	 * @param ep
	 */
	private void handleEventPoint (EventPoint ep) {
		// Find segments, if they exist, to left (and right) of ep in line state.
		// Intersections can only happen between neighboring segments. We start
		// with nearest ones because as line sweeps down we will eventually find
		// any other intersections that (for now) we put off.
		AugmentedNode<ILineSegment> left = lineState.leftNeighbor(ep);
		AugmentedNode<ILineSegment> right = lineState.rightNeighbor(ep);

		// determine intersections 'ints' from neighboring line segments and 
		// get upper segments 'ups' and lower segments 'lows' for this event point.
		// An intersection exists if > 1 segment associated with event point
		lineState.determineIntersecting(ep, left, right);
		List<ILineSegment> ints = ep.intersectingSegments();
		List<ILineSegment> ups = ep.upperEndpointSegments();
		List<ILineSegment> lows = ep.lowerEndpointSegments();
		if (lows.size() + ups.size() + ints.size() > 1) {
			record (ep.point, lows, ups, ints);
		}

		// Delete everything after left until left's successor is right. Then
		// update the sweep point, so insertions will properly be ordered. Only
		// ups and ints are inserted because they are still active.
		lineState.deleteRange(left, right);
		lineState.setSweepPoint(ep.point);
		boolean update = false;
		if (!ups.isEmpty()) {
			lineState.insertSegments (ups);
			update = true;
		}
		if (!ints.isEmpty()) { 
			lineState.insertSegments (ints);
			update = true;
		}

		// If state shows no intersections at this event point, see if left & right 
		// segments intersect below sweep line, and update event queue properly.
		// Otherwise, if there was an intersection, the order of segments between 
		// left & right have switched so we check two specific ranges, namely, left 
		// and its (new) successor, and right and its (new) predecessor.
		if (!update) {
			if (left != null && right != null) { updateQueue (left, right); }
		} else {
			if (left != null) { updateQueue (left, lineState.successor(left));	}
			if (right != null) { updateQueue (lineState.pred(right), right); }
		}
	}

	/**
	 * If left and right intersect below the sweep line (or on it) and to the
	 * right of the current event point p, and the intersection is not yet present
	 * as an event in Q, then insert it
	 * 
	 * @param left               segment in state
	 * @param right              another segment in state, to the right of left
	 */
	private void updateQueue(AugmentedNode<ILineSegment> left, 
			                 AugmentedNode<ILineSegment> right) {
        // Determine if the two neighboring line segments intersect. Make sure that 
        // new intersection point is *below* the sweep line and not added twice.
        IPoint p = left.key().intersection(right.key());
        if (p != null && EventPoint.pointSorter.compare(p,lineState.getSweepPoint()) > 0) {
            EventPoint new_ep = new EventPoint(p);
            if (!eq.contains(new_ep)) { eq.insert(new_ep); }
        }
	}
}
