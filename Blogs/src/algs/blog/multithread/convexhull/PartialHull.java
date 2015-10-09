package algs.blog.multithread.convexhull;

import java.util.ArrayList;

import algs.model.FloatingPoint;
import algs.model.IPoint;

/**
 * Represents either the top or the bottom of a Convex Hull.
 * <p>
 * Optimized for multi-threaded access by avoiding inefficient code to generate
 * an array from the ArrayList of points. In fact, this optimization could be
 * made to the original {@link algs.model.problems.convexhull.PartialHull} class.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
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
	
	/** Add point to the Partial Hull. */
	public void add(IPoint p) {
		points.add(p);
	}
	
	/** Returns middle of last three. Returns true on success; false otherwise. */
	public boolean removeMiddleOfLastThree() {
		if (!hasThree()) return false;   // something to do
		
		int pos = points.size();
		points.remove(pos-2);
		return true;
	}
	
	/** Determine if there are more than 2 points currently in the partial hull. */
	public boolean hasThree() {
		return points.size() > 2;
	}
	
	/** Helper function to report number of points in the hull. */
	public int size() { 
		return points.size();
	}
	
	/** 
	 * Determines if last three points reflect a right turn.
	 * 
	 * If hasThree() is false, then this returns false.
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

	/**
	 * Fill array with points starting at given offset.
	 * 
	 * @param hull      array into which hull is being transcribed
	 * @param offset    offset location into which to write
	 * 
	 * @return index one greater than last one inserted.
	 */
	public int transcribe(IPoint[] hull, int offset) {
		int idx = offset;
		int sz = points.size();
		for (int i = 0; i < sz; i++) {
			hull[idx++] = points.get(i);
		}
		
		return idx;
	}

	/**
	 * Fill array with num points starting at given offset.
	 * 
	 * @param hull      array into which hull is being transcribed
	 * @param offset    offset location into which to write
	 * @param num       number of points to transcribe
	 */
	public int transcribe(IPoint[] hull, int offset, int num) {
		int idx = offset;
		for (int i = 0; i < num; i++) {
			hull[idx++] = points.get(i);
		}
		
		return idx;
	}	
}

