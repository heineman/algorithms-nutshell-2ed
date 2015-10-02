package algs.model.problems.convexhull;

import java.util.ArrayList;

import algs.model.FloatingPoint;
import algs.model.IPoint;

/**
 * Heuristic that offers noticeable speed-up when when applied to Convex Hull 
 * problems.
 * 
 * Questions that may not have an impact but are worth asking: What if LEFT/BOTTOM are same 
 * point and TOP/RIGHT are same point? What if LEFT/BOTTOM/TOP/RIGHT are all same point?  
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AklToussaint {
	
	/**
	 * Determine if there is an intersection between two line segments.
	 * 
	 * Segment s1 is (x1,y1) to (x2,y2) while segment s2 is (x3,y3) to (x4,y4).
	 *  
	 * Returns 0/1 instead of false/true to enable code 
	 * in {@link AklToussaint#reduce(IPoint[])} to be more efficiently written
	 * because we want to count the number of intersections.
	 * <p>
	 * Inspired by computation outlined in:
	 * 
	 *     http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/
	 *  
	 * @param x1    segment s1 start
	 * @param y1    segment s1 start
	 * @param x2    segment s1 end
	 * @param y2    segment s1 end
	 * @param x3    segment s2 begin
	 * @param y3    segment s2 begin
	 * @param x4    segment s2 end
	 * @param y4    segment s2 end
	 * @return      1 if there is an intersection, 0 otherwise
	 */
	private static int intersect (double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4) {
		double denom = (y4 - y3)*(x2 - x1)-(x4 - x3)*(y2 - y1);
		
		// parallel (or may be coincident). IN either case, not an intersection.
		// Rely on FloatingPoint arithmetic
		if (FloatingPoint.value(denom) == 0.0) {
			return 0;
		}
		
		double ua_nom = (x4 - x3)*(y1 - y3) - (y4 - y3)*(x1 - x3);
		double ub_nom = (x2 - x1)*(y1 - y3) - (y2 - y1)*(x1 - x3);
		
		double ua = ua_nom/denom;
		double ub = ub_nom/denom;
		
		// line segment intersection.
		if (FloatingPoint.value(ua) >= 0 && 
				FloatingPoint.value(ua-1) <= 0 && 
				FloatingPoint.value(ub) >= 0 && 
				FloatingPoint.value(ub-1) <= 0) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Remove points that are within the quadrilateral formed by the extreme 
	 * points at (x,y) axes.
	 * <p>
	 * These points clearly cannot be on the convex hull. The array returned 
	 * contains those points that are outside the quadrilateral.
     *
	 * @param points  initial convex hull set.
	 * @return points that may still potentially be part of the convex hull
	 */
	public static IPoint[] reduce (IPoint [] points) {
		ArrayList<IPoint> reduced = new ArrayList<IPoint>(); 
		
		// compute quadrilateral
		double minX = points[0].getX();
		double maxX = minX;
		double minY = points[0].getY();
		double maxY = minY;
		int left=0, right=0, top=0, down=0;
		for (int i = 1; i < points.length; i++) {
			double x = points[i].getX();
			double y = points[i].getY();
			
			if (FloatingPoint.lesser(x, minX)) {
				minX = x; 
				left = i;
			} else if (FloatingPoint.greater(x, maxX)) {
				maxX = x;
				right = i;
			}
			
			if (FloatingPoint.lesser(y, minY)) {
				minY = y;
				down = i;
			} else if (FloatingPoint.greater(y, maxY)) {
				maxY = y; 
				top = i;
			}
				
		}
		
		// for each point, trace vertical ray to min (maxY+1,Infinity). This is a point 
		// outside any of the convex hull and serves as Infinity. If intersects the 
		// edges of the quadrilateral an 0 or 2 times, then NOT in the quadrilateral;
		// if intersect 1 time, then IN the quadrilateral. Since an intersection is
		// detected "on the edge" we have to be careful to detect real inside points.
		double y2 = Math.min(maxY+1, Double.MAX_VALUE);
		for (int i = 0; i < points.length; i++) {
			double x = points[i].getX();
			double y = points[i].getY();
			
			// if you intersect LEFT/TOP you can't intersect TOP/RIGHT.
			// if you intersect RIGHT/BOTTOM, you can't intersect BOTTOM/LEFT.
			int ct = 0;
			if (intersect (x, y, x, y2, minX, points[left].getY(), points[top].getX(), maxY) == 0) {
				ct += intersect (x, y, x, y2, points[top].getX(), maxY, maxX, points[right].getY());
			} else { ct++; }
			
			if (intersect (x, y, x, y2, maxX, points[right].getY(), points[down].getX(), minY) == 0) {
				ct += intersect (x, y, x, y2, points[down].getX(), minY, minX, points[left].getY());
			} else { ct++; }
			
			// only add if we don't appear within internal of quadrilateral. 
			if (ct == 0 || ct == 2) { 
				reduced.add(points[i]);
			} else {
				// note that extremal points get special treatment. MUST be in
				if (i == left || i == right || i == down || i == top) {
					reduced.add(points[i]);
				}
			}
		}
		
		return reduced.toArray(new IPoint[]{});
	}
}
