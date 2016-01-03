package algs.model.problems.segmentIntersection;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;

/**
 * Contains LineSweep algorithm to detect all intersections among an array of
 * different line segments.
 * <p>
 * Note that the LineState uses an augmented binary tree in which the internal nodes
 * store (min, max) values that reflect the leftmost segment in the left sub-tree and 
 * the rightmost segment in the right sub-tree. The keys of the nodes in the tree are
 * the segments themselves.
 * <p>
 * As the algorithm progresses, there may be errors that occur because of problems
 * inherent in the floating point calculations. At times, the algorithm can actually
 * detect when these occur. The algorithm simply outputs the error to stderr but 
 * does not fail or throw an exception. This is an agreeable compromise given the
 * challenges in using floating point computations accurately.
 * <p>
 * The EventQueue is able to insert a new EventPoint in O (log n) as well as locate a
 * previous EventPoint in the event queue with the same performance.
 * <p>
 * This Algorithm assumes that the input contains no duplicate line segments. If 
 * duplicate segments exist, then some of the reported intersections may be a line
 * segment with itself (on both of its end-points).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LineSweep extends IntersectionDetection {

	/** Store state as sweep progresses. */
    LineState lineState;     
    
    /** Event queue of event points. */
    EventQueue eq;
    
    /** Maintain a count of any errors found so far. */
    int errorCount;
    
	public LineSweep  () {
		super();
	}

	public void initialize() {
		errorCount = 0;
		lineState = new LineState();
		eq = new EventQueue();
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
	      EventPoint ep = new EventPoint(ils.getStart());
	      EventPoint existing = eq.event(ep);
	      if (existing == null) { eq.insert(ep); } else { ep = existing; }

	      // add upper line segments to ep (the object in the queue)
	      ep.addUpperLineSegment(ils);

	      ep = new EventPoint(ils.getEnd());
	      existing = eq.event(ep);
	      if (existing == null) { eq.insert(ep); } else { ep = existing; }

	      // add lower line segments to ep (the object in the queue)
	      ep.addLowerLineSegment(ils);
		}

		// Sweep top to bottom, processing each Event Point in queue. 
		while (!eq.isEmpty()) {
			EventPoint p = eq.min();
			try {
				handleEventPoint(p);
			} catch (NullPointerException npe) {
				// Because floating point calculations may lead to inconsistent state
				// in smaller number of cases, especially with lots of random points, 
				// we alert user about situation and recover; only show	first error
				if (++errorCount == 1) {
					System.err.println("  LineSweep produced invalid computation for:" + p);
				}
			}
		}

		computeTime();
		if (errorCount > 1) {
			System.err.println("  LineSweep had problems with:" + errorCount + " point(s).");
		}
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
		AugmentedNode<ILineSegment> left = lineState.leftNeighbor (ep);
		AugmentedNode<ILineSegment> right = lineState.rightNeighbor (ep);

		// determine intersections 'ints' from neighboring line segments and 
		// get upper segments 'ups' and lower segments 'lows' for this event point.
		// An intersection exists if > 1 segment associated with event point
		lineState.determineIntersecting (ep, left, right);
		List<ILineSegment> ints = ep.intersectingSegments();
		List<ILineSegment> ups  = ep.upperEndpointSegments();
		List<ILineSegment> lows = ep.lowerEndpointSegments();
		if (lows.size() + ups.size() + ints.size() > 1) {
			record (ep.point, lows, ups, ints);
		}

		// Delete everything after left until left's successor is right. Then
		// update the sweep point, so insertions will properly be ordered. Only
		// ups and ints are inserted because they are still active.
		lineState.deleteRange (left, right);
		lineState.setSweepPoint (ep.point);
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
			if (left != null) { updateQueue (left, lineState.successor (left));	}
			if (right != null) { updateQueue (lineState.pred (right), right); }
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
        IPoint p = left.key().intersection (right.key());
        if (p == null) { return; }
        if (EventPoint.pointSorter.compare (p, lineState.sweepPt) > 0) {
            EventPoint new_ep = new EventPoint(p);
            if (!eq.contains (new_ep)) { eq.insert(new_ep); }
        }
	}
}
