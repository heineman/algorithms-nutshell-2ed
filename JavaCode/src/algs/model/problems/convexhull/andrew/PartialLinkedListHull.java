package algs.model.problems.convexhull.andrew;


import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;

/**
 * Represents either the top or the bottom of a Convex Hull.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PartialLinkedListHull {

	/** By not selecting a comparator, the insert is guaranteed to be an append. */
	DoubleLinkedList<IPoint> points = new DoubleLinkedList<IPoint>();
	
	/**
	 * Construct the initial partial hull.
	 * 
	 * @param first     Left-most point (for upper) and right-most (for lower) 
	 * @param second    Next one in sorted order, as the next assumed point in the hull.
	 */
	public PartialLinkedListHull (IPoint first, IPoint second) {
		points.insert(first);
		points.insert(second);
	}
	
	/** 
	 * Add point to the Partial Hull.
	 * @param p   point to add to the partial hull. 
	 */
	public void add(IPoint p) {
		points.insert(p);
	}
	
	/** 
	 * Removes middle of last three.
	 * @return  true on success; false otherwise.  
	 */
	public boolean removeMiddleOfLastThree() {
		if (!hasThree()) return false;   // something to do
		
		IPoint last = points.removeLast();
		points.removeLast();  // remove them middle one
		points.insert(last);

		return true;
	}
	
	/** 
	 * Determine if there are more than 2 points currently in the partial hull.
	 * @return   true if partial hull has at least three points.  
	 */
	public boolean hasThree() {
		DoubleNode<IPoint> p = points.first();
		int ct = 0;
		while (p != null) {
			ct++;
			p = p.next();
			if (ct > 2) return true;
		}
		
		return false;
	}
	
	/** 
	 * Helper function to report number of points in the hull.
	 * @return size of partial hull 
	 */
	public int size() { 
		DoubleNode<IPoint> p = points.first();
		int ct = 0;
		while (p != null) {
			ct++;
			p = p.next();
		}
		return ct;
	}
	
	/** 
	 * Return the points in this Partial Hull.
	 * @return points in this partial hull 
	 */
	public DoubleLinkedList<IPoint> points() {
		return points;
	}
	
	/** 
	 * Determines if last three points reflect a right turn.
	 * 
	 * If hasThree() is false, then this returns false.
	 * @return true if last three form non-right turn.
	 */
	public boolean areLastThreeNonRight() {
		if (!hasThree()) return false;  // something to do
		
		double x1,y1,x2,y2,x3,y3;
		
		DoubleNode<IPoint> last = points.last();
		DoubleNode<IPoint> second = last.prev();
		DoubleNode<IPoint> third = second.prev();
		
		x1 = third.value().getX();
		y1 = third.value().getY();
		
		x2 = second.value().getX();
		y2 = second.value().getY();
		
		x3 = last.value().getX();
		y3 = last.value().getY();
		
		double val1 = (x2 - x1)*(y3 - y1);
		double val2 = (y2 - y1)*(x3 - x1);
		double diff = FloatingPoint.value(val1 - val2);
		if (diff == 0) return true;
		if (diff > 0) return true;
		
		return false;
	}
}
