package algs.model.problems.convexhull;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.FloatingPoint;
import algs.model.IPoint;

/**
 * Represents either the top or the bottom of a Convex Hull.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PartialHull {
	/** Points that make up the hull. */
	ArrayList<IPoint> points = new ArrayList<IPoint>();
	
	/**
	 * Construct the initial partial hull.
	 * 
	 * @param first     Left-most point (for upper) and right-most (for lower) 
	 * @param second    Next one in sorted order, as the next assumed point in the hull.
	 */
	public PartialHull (IPoint first, IPoint second) {
		points.add(first);
		points.add(second);
	}
	
	/** 
	 * Add point to the Partial Hull.
	 * @param p   point to add to the partial hull. 
	 */
	public void add(IPoint p) {
		points.add(p);
	}
	
	/** 
	 * Returns middle of last three. Returns true on success; false otherwise. 
	 * @return true if successful in removing the middle of the last three points
	 */
	public boolean removeMiddleOfLastThree() {
		if (!hasThree()) return false;   // something to do
		
		int pos = points.size();
		points.remove(pos-2);
		return true;
	}
	
	/** 
	 * Determine if there are more than 2 points currently in the partial hull.
	 * @return true if there are at least three points in the partial hull. 
	 */
	public boolean hasThree() {
		return points.size() > 2;
	}
	
	/** 
	 * Helper function to report number of points in the hull.
	 * @return number of points in partial hull. 
	 */
	public int size() { 
		return points.size();
	}
	
	/**
	 * Return the points in this Partial Hull.
	 * @return array of points (in order) that form the partial hull. 
	 */
	public IPoint[] getPoints() {
		return points.toArray(new IPoint[]{});
	}
	
	/**
	 * Return the points in this Partial Hull as an Iterator. 
	 * @return iterator of points (in order) that form the partial hull.
	 */
	public Iterator<IPoint> points() {
		return points.iterator();
	}
	
	/** 
	 * Determines if last three points reflect a right turn.
	 * 
	 * If hasThree() is false, then this returns false.
	 * @return true if the last three points on the partial hull do not form a right turn
	 */
	public boolean areLastThreeNonRight() {
		if (!hasThree()) return false;  // something to do
		
		double x1,y1,x2,y2,x3,y3;
		
		int pos = points.size()-3;
		
		x1 = points.get(pos).getX();
		y1 = points.get(pos).getY();
		
		x2 = points.get(pos+1).getX();
		y2 = points.get(pos+1).getY();
		
		x3 = points.get(pos+2).getX();
		y3 = points.get(pos+2).getY();
		
		double val1 = (x2 - x1)*(y3 - y1);
		double val2 = (y2 - y1)*(x3 - x1);
		double diff = FloatingPoint.value(val1 - val2);
		if (diff >= 0) return true;
		
		return false;
	}	
}

