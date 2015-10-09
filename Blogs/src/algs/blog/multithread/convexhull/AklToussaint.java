package algs.blog.multithread.convexhull;


import algs.model.FloatingPoint;
import algs.model.IPoint;

/**
 * Heuristic that offers noticeable speed-up when when applied to Convex Hull 
 * problems. 
 * <p>
 * This implementation shows a multi-threaded solution, hard-wired for two 
 * threads.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class AklToussaint {
	
	/**  deny construction of this class. */ 
	private AklToussaint() { }
	
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
	public static int intersect (double x1, double y1, double x2, double y2,
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
	 */
	public static IPoint[] reduce (final IPoint [] points) {
		
		// compute quadrilateral
		double minX = points[0].getX();
		double maxX = minX;
		double minY = points[0].getY();
		double maxY = minY;
		
		// store as static variables.
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
		final double y2 = Math.min(maxY+1, Double.MAX_VALUE);
		
		// need for thread access.
		final double fminX = minX;
		final double fmaxX = minX;
		final double fminY = minY;
		final double fmaxY = minY;
		final int fleft=left, fright=right, ftop=top, fdown=down;
		
		// split into two sets.
		final int split = points.length/2;
		
		// thread one will store points in reduced1 and the number of actual points
		// will be stored in sizes[0]; similarly, thread2 will store points in
		// reduced2 and the number of actual points will be stored in sizes[1].
		// The arrays are large enough since the heuristic will attempt to remove
		// points from the original array.
		final IPoint[] reduced1 = new IPoint[points.length];
		final IPoint[] reduced2 = new IPoint[points.length];
		final int sizes[] = new int[2];

		// two separate threads. one handles the left-half of the array and the other
		// handles the right-half of the array, based upon the split value.
		Thread one = new Thread() {
			public void run() {
				int idx = 0;
				for (int i = 0; i < split; i++) {
					double x = points[i].getX();
					double y = points[i].getY();
					
					// if you intersect LEFT/TOP you can't intersect TOP/RIGHT.
					// if you intersect RIGHT/BOTTOM, you can't intersect BOTTOM/LEFT.
					int ct = 0;
					if (intersect (x, y, x, y2, fminX, points[fleft].getY(), points[ftop].getX(), fmaxY) == 0) {
						ct += intersect (x, y, x, y2, points[ftop].getX(), fmaxY, fmaxX, points[fright].getY());
					} else { ct++; }
					
					if (intersect (x, y, x, y2, fmaxX, points[fright].getY(), points[fdown].getX(), fminY) == 0) {
						ct += intersect (x, y, x, y2, points[fdown].getX(), fminY, fminX, points[fleft].getY());
					} else { ct++; }
					
					// only add if we don't appear within internal of quadrilateral. 
					if (ct == 0 || ct == 2) { 
						reduced1[idx++] = points[i];
					} else {
						// note that extremal points get special treatment. MUST be in
						if (i == fleft || i == fright || i == fdown || i == ftop) {
							reduced1[idx++] = points[i];
						}
					}
				}
				sizes[0] = idx;  // record total size once thread is done.
			}
		};
		
		// points from the right-half of the array.
		Thread two = new Thread() {
			public void run() {
				int idx = 0;
				for (int i = split; i < points.length; i++) {
					double x = points[i].getX();
					double y = points[i].getY();
					
					// if you intersect LEFT/TOP you can't intersect TOP/RIGHT.
					// if you intersect RIGHT/BOTTOM, you can't intersect BOTTOM/LEFT.
					int ct = 0;
					if (intersect (x, y, x, y2, fminX, points[fleft].getY(), points[ftop].getX(), fmaxY) == 0) {
						ct += intersect (x, y, x, y2, points[ftop].getX(), fmaxY, fmaxX, points[fright].getY());
					} else { ct++; }
					
					if (intersect (x, y, x, y2, fmaxX, points[fright].getY(), points[fdown].getX(), fminY) == 0) {
						ct += intersect (x, y, x, y2, points[fdown].getX(), fminY, fminX, points[fleft].getY());
					} else { ct++; }
					
					// only add if we don't appear within internal of quadrilateral. 
					if (ct == 0 || ct == 2) { 
						reduced2[idx++] = points[i];
					} else {
						// note that extremal points get special treatment. MUST be in
						if (i == fleft || i == fright || i == fdown || i == ftop) {
							reduced2[idx++] = points[i];
						}
					}
				}		
				
				sizes[1] = idx; // record total size once done with thread.
			}
		};
		
		// start both threads and wait until they are done.
		one.start();
		two.start();
		try {
			one.join();
			two.join();
		} catch (InterruptedException ie) {
			System.err.println("Multithread AKL Toussaint execution interrupted. Unexpected results may ensue.");
		}
		
		// combine together
		IPoint[] combined = new IPoint[sizes[0] + sizes[1]];
		System.arraycopy(reduced1, 0, combined, 0, sizes[0]);
		System.arraycopy(reduced2, 0, combined, sizes[0], sizes[1]);
		
		return combined;
	}
}
