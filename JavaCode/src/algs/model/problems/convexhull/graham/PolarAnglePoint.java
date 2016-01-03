package algs.model.problems.convexhull.graham;

import algs.model.IPoint;
import algs.model.problems.convexhull.graham.GrahamScan.PolarSorter;

/** 
 * A {@link PolarAnglePoint} object stores the original point and the polar angle when comparing the point against
 * a fixed point which has the lowest Y coordinate in a collection.
 * 
 * This extra storage simplifies {@link GrahamScan}. Note that the polar angle is initialized to {@link Double#MAX_VALUE}
 * so that computation only happens once, and thereafter is cached.
 * 
 * Attribute angle is computed and set externally, thus its field is not final; it remains package private to 
 * ensure integrity. It is set in lazy fashion from within {@link PolarSorter}.
 * 
 * The compareTo method is not invoked directly because GrahamScan algorithm uses {@link PolarSorter} as the 
 * comparator, which overrides the default {@link #compareTo(PolarAnglePoint)} method; however, it needs to be
 * here so the code properly compiles. Thus it never executes in the Graham Scan context.
 */
public class PolarAnglePoint implements Comparable<PolarAnglePoint>{
	public final double x;
	public final double y;
	public final IPoint original;
	
	/** Uncomputed angles are set to maximum value. Making public to allow external comparisons with compareTo. */
	public double angle = Double.MAX_VALUE;
	
	/**
	 * Construct PolarAnglePoint by extracting (x,y) coordinates from {@link IPoint} object.
	 * 
	 * @param pt   source for x and y coordinates.
	 */
	public PolarAnglePoint (IPoint pt) {
		this.x = pt.getX();
		this.y = pt.getY();
		this.original = pt;
	}

	/** Default sort order is by polar angle. */
	@Override
	public int compareTo(PolarAnglePoint p) {
		if (p.angle > angle) { return +1; }
		else if (p.angle < angle) { return -1; }
		
		return 0;
	}
	
	/** Method for debugging. */
	public String toString() {
		return "[" + x + "," + y + " @ " + angle + "]";
	}
}
