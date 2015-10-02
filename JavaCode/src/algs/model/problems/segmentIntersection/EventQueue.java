package algs.model.problems.segmentIntersection;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * The EventQueue for a horizontal-sweep line algorithm for line segment intersection.
 * <p>
 * The EventQueue must not be a heap-based implementation, since we will be called upon 
 * to test if an EventPoint is already within the Queue.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class EventQueue {

	/** 
	 * Use balanced binary tree to store points. Values are same
	 * as keys to enable insert to retrieve old keys as values.
	 */
	BalancedTree<EventPoint, EventPoint> events;
	
	/** Default constructor. */
	public EventQueue() {
		events = new BalancedTree<EventPoint,EventPoint>(EventPoint.eventPointSorter);
	}
	
	/** 
	 * Return whether the event queue is empty. 
	 * @return true if {@link EventQueue} is empty; false otherwise 
	 */
	public boolean isEmpty() {
		return events.size() == 0;
	}
	
	/** 
	 * Insert the Event Point into the queue, taking care to properly
	 * maintain the set of segments associated with this EventPoint if
	 * it is indeed has upper Segments.
	 * @param ep    event to insert into queue.
	 */
	public void insert (EventPoint ep) {
		EventPoint oldOne = events.insert(ep, ep);
		
		if (oldOne != null) {
			// make sure upper end points are inserted.
			ep.addUpperLineSegments(oldOne.upperEndpointSegments());
		}
	}
	
	/**
	 * Remove and return left-most child [the smallest one].
	 * @return  minimum event point in the queue. 
	 */
	public EventPoint min() {
		BalancedBinaryNode<EventPoint,EventPoint> bn = events.firstNode();
		EventPoint key = bn.key();
		events.remove(key);
		
		// we can't just return the key, since (in our implementation) the value contains the full
		// set of upper line segments. This is why we return the value(), even though the actual 
		// EventPoint coordinates would otherwise be the same.
		return bn.value();
	}

	/** 
	 * Determine whether event point already exists within the queue.
	 * 
	 * @param ep   event point to probe for
	 * @return     true if contained within the event queue already; false otherwise
	 */
	public boolean contains(EventPoint ep) {
		BalancedBinaryNode<EventPoint,EventPoint> bn = events.getEntry(ep);
		return (bn != null);
	}
	
	/**
	 * Determine whether event point already exists within the queue, and 
	 * return the actual event point within the queue if it does.
	 *  
	 * @param ep    event to search for in the queue to retrieve actual one.
	 * @return actual event in the event queue matching the requested ep target
	 */
	public EventPoint event(EventPoint ep) {
		BalancedBinaryNode<EventPoint,EventPoint> bn = events.getEntry(ep);
		if (bn == null) { return null; } 
		return bn.key();
	}
}
