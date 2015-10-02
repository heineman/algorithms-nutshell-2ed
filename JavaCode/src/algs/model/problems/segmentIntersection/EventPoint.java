package algs.model.problems.segmentIntersection;

import java.util.Comparator;

import algs.model.FloatingPoint;
import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.list.Node;

/**
 * The EventPoint is the basic element of the EventQueue.
 * <p>
 * Each EventPoint is fully ordered according to the Horizontal Sweep 
 * Line. Specifically, e1 &lt; e2 if and only if (a) e1.y &gt; e2.y or
 * (b) e1.y == e2.y and e1.x &lt; e2.x 
 * <p>
 * Note that even points store information about the line segments being
 * processed, including: (a) upper segments that begin at the given event
 * point; (b) lower segments that end at the given event point; and (c)
 * segments that intersect at the event point.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class EventPoint implements Comparator<EventPoint> {

	/** The point. */
	public final IPoint point;
	
	/** The segments whose upper (start) point is this event Point. */
	List<ILineSegment> upperSegments = new List<ILineSegment>();
	
	/** The segments whose lower (start) point is this event Point. */
	List<ILineSegment> lowerSegments = new List<ILineSegment>();
	
	/** The segments that intersect with this point. */
	List<ILineSegment> intersectingSegments = new List<ILineSegment>();
	
	/**
	 * Globally useful sorter, sorts points based on a horizontal sweep line
	 * moving vertically down the Cartesian plane.
	 */
	public static Comparator<IPoint> pointSorter = new Comparator<IPoint>() {
		/**
		 * Comparison assumes a horizontal sweep line starting from the top
		 * and sweeping down the plane.
		 */
		public int compare(IPoint o1, IPoint o2) {
			double fp = FloatingPoint.value(o1.getY() - o2.getY());
			if (fp > 0) { return -1; }
			if (fp < 0) { return +1; }
			
			// must be same; revert to second condition.
			fp = FloatingPoint.value(o1.getX() - o2.getX());
			if (fp < 0) { return -1; }
			if (fp > 0) { return +1; }
			
			// same!
			return 0;
		}
	};
	
	/**
	 * Globally useful sorter, sorts points based on a horizontal sweep line
	 * moving vertically down the Cartesian plane.
	 */
	public static Comparator<EventPoint> eventPointSorter = new Comparator<EventPoint>() {
		/**
		 * Comparison assumes a horizontal sweep line starting from the top
		 * and sweeping down the plane.
		 */
		public int compare(EventPoint o1, EventPoint o2) {
			double fp = FloatingPoint.value(o1.point.getY() - o2.point.getY());
			if (fp > 0) { return -1; }
			if (fp < 0) { return +1; }
			
			// must be same; revert to second condition.
			fp = FloatingPoint.value(o1.point.getX() - o2.point.getX());
			if (fp < 0) { return -1; }
			if (fp > 0) { return +1; }
			
			// same!
			return 0;
		}
	};
	
	/** 
	 * Constructor for the Event Point when not an upper (start) endpoint.
	 *
	 * @param  p   point of interest
	 */
	public EventPoint (IPoint p) {
		this.point = p;
	}
	

	/**
	 * Comparison assumes a horizontal sweep line starting from the top
	 * and sweeping down the plane.
	 * 
	 * @param o1   point 1 under comparison
	 * @param o2   point 2 under comparison
	 * @return     0 if same, -1 if smaller, +1 if greater
	 */
	public int compare(EventPoint o1, EventPoint o2) {
		double fp = FloatingPoint.value(o1.point.getY() - o2.point.getY());
		if (fp > 0) { return -1; }
		if (fp < 0) { return +1; }
		
		// must be same; revert to second condition.
		fp = FloatingPoint.value(o1.point.getX() - o2.point.getX());
		if (fp < 0) { return -1; }
		if (fp > 0) { return +1; }
		
		return 0;
	}
	
	/** 
	 * Add this line segment but only if its Upper (i.e., Start) is
	 * reflective of this eventPoint. 
	 * 
	 * @param upper  validated segment whose upper matches event point.
	 */
	public void addUpperLineSegment (ILineSegment upper) {
		if (upper.getStart().equals(point)) {
			upperSegments.append(upper);
		} else {
			throw new IllegalArgumentException ("Improper attempt to add line segment whose upper point does not match EventPoint.");
		}
	}
	
	/** 
	 * Add this line segment but only if its Lower (i.e., End) is
	 * reflective of this eventPoint. 
	 * 
	 * @param lower  validated segment whose lower matches event point.
	 */
	public void addLowerLineSegment (ILineSegment lower) {
		if (lower.getEnd().equals(point)) {
			lowerSegments.append(lower);
		} else {
			throw new IllegalArgumentException ("Improper attempt to add line segment whose lower point does not match EventPoint.");
		}
	}
	
	/** 
	 * Add this line segment as an intersecting one. 
	 * 
	 * @param ints   intersecting line segment with this event point.
	 */
	public void addIntersectingLineSegment (ILineSegment ints) {
		intersectingSegments.append(ints);
	}
	
	/**
	 * Return the set of Line segments whose upper (i.e., Start) endpoint is 
	 * this EventPoint.
	 * 
	 * In the algorithm, this return value is denoted U(p)
	 * @return {@link List} of upper segments of lines associated with this event point
	 */
	public List<ILineSegment> upperEndpointSegments() {
		return upperSegments;
	}
	
	/**
	 * Return the set of Line segments whose lower (i.e., End) endpoint is 
	 * this EventPoint.
	 * 
	 * In the algorithm, this return value is denoted L(p)
	 * @return {@link List} of lower segments of lines associated with this event point
	 */
	public List<ILineSegment> lowerEndpointSegments() {
		return lowerSegments;
	}
	
	/**
	 * Return the set of Line segments that intersect this eventPoint.
	 * 
	 * In the algorithm, this return value is denoted C(p)
	 * @return {@link List} of intersecting segments associated with this event point
	 */
	public List<ILineSegment> intersectingSegments() {
		return intersectingSegments;
	}
	
	/** 
	 * Batch process a set of upper insertions here.
	 * 
	 * @param list    list of line segments
	 */
	public void addUpperLineSegments(List<ILineSegment> list) {
		upperSegments.concat(list);
	}
	
	/** 
	 * Helper for toString in concatenating lists.
	 * @param list    list to be concatenated together.
	 * @return   human readable string
	 */
	private String concat (List<ILineSegment> list) {
		StringBuilder res = new StringBuilder();
		Node<ILineSegment> n = list.head();
		while (n != null) {
			res.append (n.value());
			n = n.next();
			if (n != null) {
				res.append (",");
			}
		}
		
		return res.toString();
	}
	
	/** Need equals method if this class is ever to be used within the collection classes. */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof EventPoint) {
			EventPoint other = (EventPoint) o;
			return point.equals(other.point);
		}
		
		// nope
		return false;
	}
	
	/** Useful representation. */
	public String toString () {
		
		return "<" + point.toString()+ ": up:" + concat(upperSegments) +
			", low:" + concat(lowerSegments) + 
			", inter:" + concat(intersectingSegments) + ">";
	}
	
}
