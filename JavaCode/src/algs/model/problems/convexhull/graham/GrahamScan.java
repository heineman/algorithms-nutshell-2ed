package algs.model.problems.convexhull.graham;


import java.util.Comparator;

import algs.model.IPoint;
import algs.model.heap.HeapSort;
import algs.model.list.*;
import algs.model.problems.convexhull.IConvexHull;

/**
 * Computes Convex Hull following Graham's Algorithm. This algorithm is described
 * in chapter 3 of the second edition.
 * 
 * Find more details on this algorithm at @see <a href="http://en.wikipedia.org/wiki/Graham_scan">Wikipedia entry</a>.
 *  
 * @author George Heineman
 * @version 1.0, 11/22/14
 * 
 * @since 2.0
 */
public class GrahamScan implements IConvexHull {
	
	/**
	 * Helper class for sorting points by polar coordinate with regards to a base point.
	 */
	class PolarSorter implements Comparator<PolarAnglePoint> {
		/** Stored x coordinate of base point used for comparison. */
		final double baseX;
		
		/** Stored y coordinate of base point used for comparison. */
		final double baseY;
	
		/** PolarSorter needs base point against which all other points are evaluated. */
		public PolarSorter(PolarAnglePoint base) {
			this.baseX = base.x;
			this.baseY = base.y;
		}

		/**
		 * Compares two 2-dimensional points in the Cartesian plane by polar coordinate with 
		 * respect to base point. 
		 * 
		 * Function uses the {@link Math#atan2} function to perform computation
		 * 
		 * @param one first point to be compared
		 * @param two second point to be compared
		 * @return 0 if equal, -1 if one has LARGER polar angle, +1 if one has SMALLER polar angle.
		 */
		public int compare(PolarAnglePoint one, PolarAnglePoint two) {
			if (one == two) { return 0; }
			
			// make sure both have computed angle using atan2 function. Works because one.y is always
			// larger than or equal to base.y
			if (one.angle == Double.MAX_VALUE) { one.angle = Math.atan2(one.y - baseY, one.x - baseX); }
			if (two.angle == Double.MAX_VALUE) { two.angle = Math.atan2(two.y - baseY, two.x - baseX); }
			
			if (one.angle > two.angle) { return -1; }
			else if (one.angle < two.angle) { return +1; }
			
			// if same angle, then order by magnitude where SMALLER is -1 and LARGER is 1
			if (one.y > two.y) { return +1; }
			else if (one.y < two.y) { return -1; }
			
			return 0;
		}
	}
	
	/**
	 * Formula for computing determinant returns twice the signed area of the triangle
	 * formed by these three points.
	 * 
	 * If the computed value is greater than 0, the three sequential points forms left turn.
	 * If smaller than 0, they form right turn. If the computed value is exactly zero, then these points
	 * are collinear (i.e., straight).
	 * 
	 * @param p1  first point in sequence
	 * @param p2  second point in sequence
	 * @param p3  third point in sequence
	 * 
	 * @return true if points are collinear or make right-turn; false if makes left turn
	 */
	public static boolean isLeftTurn(PolarAnglePoint p1, PolarAnglePoint p2, PolarAnglePoint p3) {
	    return (p2.x - p1.x)*(p3.y- p1.y) - (p2.y - p1.y)*(p3.x - p1.x) > 0;
	}
	
	/**
	 * Use Graham's algorithm to return the computed convex hull for 
	 * the input set of points.
	 * <p>
	 * Points must have at least three points to do anything meaningful. If
	 * it does not, then the original array is returned as the "hull".
	 * <p>
	 * This algorithm will still work if duplicate points are found in
	 * the input set of points.
	 * <p>
	 * Note that the order of the original points array passed in as a parameter may change
	 * during this operation. Will throw {@link NullPointerException} if points is null.
	 * 
	 * @param pts     a set of (n &ge; 3) two dimensional points.
	 */
	public IPoint[] compute (IPoint[] pts) {
		int n = pts.length;
		if (n < 3) { return pts; }
		
		// Find point with lowest y-coordinate and populate points[i] with PolarAnglePoints for each pts[i]
		PolarAnglePoint[] points = new PolarAnglePoint[n];
		points[0] = new PolarAnglePoint(pts[0]);
		
		// Find lowest point and swap with last one in points[] array, if it isn't there already
		int lowest = 0;
		double lowestY = points[0].y;
		for (int i = 1; i < n; i++) {
			points[i] = new PolarAnglePoint(pts[i]);
			if (points[i].y < lowestY) {
				lowestY = points[i].y;
				lowest = i;
			}
		}
		
		if (lowest != n-1) {
			PolarAnglePoint temp = points[n-1];
			points[n-1] = points[lowest];
			points[lowest] = temp;
		}
		
		// sort points[0..n-2] by descending polar angle with respect to lowest point points[n-1].
		new HeapSort<PolarAnglePoint>().sort(points, 0, n-2, new PolarSorter(points[n-1]));
				
		// three points KNOWN to be on the hull are (in this order) the point with
		// lowest polar angle (points[n-2]), the lowest point (points[n-1]) and the point
		// with the highest polar angle (points[0]). Start with first two
		DoubleLinkedList<PolarAnglePoint> list = new DoubleLinkedList<PolarAnglePoint>();
		list.insert(points[n-2]);
		DoubleNode<PolarAnglePoint> secondLast = list.first();
		list.insert(points[n-1]);
		DoubleNode<PolarAnglePoint> last = list.last();
		
		// If all points are collinear, handle now to avoid worrying about later
		if (points[0].angle == points[n-2].angle) {
			return new IPoint[] { last.value().original, secondLast.value().original };
		}
		
		// Sequentially visit each in order, removing points upon making mistake. Because
		// we always have at least one "right turn" the inner while loop will always terminate
		for (int i = 0; i < n-1; i++) {
			while (isLeftTurn(secondLast.value(), last.value(), points[i])) {
				if (secondLast.prev() != null) {
					list.removeLast();
					last = secondLast;
					secondLast = secondLast.prev();
				}
			}
			
			// advance and insert next hull point into proper position
			list.insert(points[i]);
			secondLast = last;
			last = last.next();
		}
		
		// the final point is duplicated, so we take n-1 points. Do in clockwise order to 
		// produce same ordering of hull points as other hull algorithms.
		
		IPoint hull[] = new IPoint[list.size()-1];
		DoubleNode<PolarAnglePoint> ptr = list.first();
		int idx = 0;
		while (idx < hull.length) {
			hull[idx++] = ptr.value().original;
			ptr = ptr.next();
		}
		
		return hull;
	}
}