package algs.model.problems.convexhull.slowhull;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.problems.convexhull.IConvexHull;

/**
 * Computes Convex Hull using a brute force approach that computes
 * all n^3 triangles and removes points that are within a triangle.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlowHull implements IConvexHull {

	/**
	 * Use SlowHull algorithm to return the computed convex hull for the input set of 
	 * points. Uses BruteForce approach.
	 * 
	 * @param points   a set of (n &ge; 3) two dimensional points.
	 */
	public IPoint[] compute (IPoint[] points) {
		int n = points.length;

		if (n < 3) { return points; }
	
		// all initial values set to false.
		boolean internal[] = new boolean [n];
		
		// try all possible triangles. At the same time, compute the left-most 
		// point as we iterate over i. If two points have the same (x) coordinate, then
		// the leftmost one is going to be the one with the smallest Y coordinate.
		int leftMostX = n-2;
		double nx = points[n-1].getX();
		double ox = points[leftMostX].getX();
		if (nx < ox) {
			leftMostX = n-1; 
		} else if (nx == ox) {
			if (FloatingPoint.lesser(points[n-1].getY(), points[leftMostX].getY())) {
				leftMostX = n-1;
			}
		}
		
		for (int i = 0; i < n-2; i++) {
			nx = points[i].getX();
			ox = points[leftMostX].getX();
			if (nx < ox) {
				leftMostX = i; 
			} else if (nx == ox) {
				if (FloatingPoint.lesser(points[i].getY(), points[leftMostX].getY())) {
					leftMostX = i;
				}
			}
			
			for (int j = i+1; j < n-1; j++) {
				for (int k = j+1; k < n; k++) {
				
					// note that pi != pj != pk
					// now for each point check for inclusion within pi, pj, pk.
					for (int m = 0; m < n; m++) {
						if (m == i || m == j || m == k) continue;
						
						// skip computation if we are already known to be an internal point.
						if (!internal[m]) {
							if (internalPoint (points, m, i, j, k)) {
								internal[m] = true;
							}
						}
					}
				}
			}
		}
		
		// when we get here, leftMostX is set to the index of the leftmost point in points 
		// and internal[] flags points that are known not to be on the convex hull
		double angle[] = new double[n];
		
		int numOnHull = 0;
		for (int i = 0; i < n; i++) {
			if (internal[i]) continue;
			
			numOnHull++;
			if (i != leftMostX) {
				angle[i] = compute (points, leftMostX, i);
			}
			
		}
		
		// if we get here and numOnHull is zero, then all points were on a straight line. Simply pick
		// the two extreme points as the points of the convex hull.
		if (numOnHull == 0) {
			// see if we are a vertical line. If so, then must choose minY and maxY, otherwise we
			// choose minX and maxX.
			if (points[0].getX() == points[1].getX()) {
				// vertical line.
				IPoint minY = points[0];
				IPoint maxY = points[0];
				for (int i = 1; i < points.length; i++) {
					double y = points[i].getY();
					if (y < minY.getY()) {
						minY = points[i];
					}
					if (y > maxY.getY()) {
						maxY = points[i];
					}
				}
				
				// hull has two points
				return new IPoint[]{minY, maxY};
			} else {
				// get minX and maxX points.
				IPoint minX = points[0];
				IPoint maxX = points[0];
				for (int i = 1; i < points.length; i++) {
					double x = points[i].getX();
					if (x < minX.getX()) {
						minX = points[i];
					}
					if (x > maxX.getX()) {
						maxX = points[i];
					}
				}
				
				return new IPoint[]{minX, maxX};
			}
		}
		
		// Now for all remaining points (i.e., toDelete[i] == false) sort by 
		// angle with vertical line on the smallest point. Use insertion sort.
		IPoint []hull = new IPoint[numOnHull];
		hull[0] = points[leftMostX];
		angle[leftMostX] = 2*Math.PI;  // default case, to make last part of algorithm work.
		
		for (int i = 1; i < numOnHull; i++) {
			double min = 2*Math.PI;  // greater than anything.
			int minP = -1;
			for (int j = 0; j < n; j++) { 
				if (internal[j]) continue;  // ignore internal points still
				
				if (angle[j] < min) {
					min = angle[j];
					minP = j;
				}
			}
			
			hull[i] = points[minP];
			angle[minP] = 2*Math.PI;  // make sure not selected again.
		}
		
		// DONE!
		return hull;
	}

	/**
	 * Determine if points[m] is inside triangle formed by &lt;i,j,k&gt;
	 * 
	 * Given line (i,j), determine which side points[m] is on. Then verify that
	 * this is the case for line (j,k) and line (k,i) in that order and direction.
	 * <p>
	 * Note that points that are collinear with any of the edges are determined to be "inside"
	 * <p>
	 * @param points  original IPoint array into which (i,j,k) index
	 * @param m   index of target point to be investigated
	 * @param i   index of point 1
	 * @param j   index of point 2
	 * @param k   index of point 3
	 * @return true if point[m] is inside triangle formed by points i,j,k
	 */
	public static boolean internalPoint(IPoint[] points, int m, int i, int j, int k) {
		double []x = new double[] { points[m].getX(), points[i].getX(), points[j].getX(), points[k].getX()};
		double []y = new double[] { points[m].getY(), points[i].getY(), points[j].getY(), points[k].getY()};
		
		double ab = ((y[0]-y[1])*(x[2]-x[1]) - (x[0]-x[1])*(y[2]-y[1]));
		double bc = ((y[0]-y[2])*(x[3]-x[2]) - (x[0]-x[2])*(y[3]-y[2]));
		double ca = ((y[0]-y[3])*(x[1]-x[3]) - (x[0]-x[3])*(y[1]-y[3]));

		return FloatingPoint.greaterEquals(ab*bc, 0) &&
			   FloatingPoint.greaterEquals(bc*ca, 0) &&
			   FloatingPoint.greaterEquals(ab*ca, 0);
		//return ab*bc>=0 && bc*ca >=0;
	}

	/**
	 * Compute the angle between the vertical line based at leftMostX and 
	 * the line between points[leftMostX] and points[i].
	 * 
	 * Note that theta is the angle we are trying to compute.  Since we are 
	 * in control of the vertical line, we assume points[leftMostX] is
	 * the origin, thus the vertical line can be set to the vector (0,1), in 
	 * otherwords, v1 = 0*i + 1*j. The other vector is then 
	 * <pre>
	 *    v2 = (points[i].getX() - points[leftMostX].getX())*i + 
	 *           (points[i].getY() - points[leftMostX].getY())*j
	 * 
	 *    cos(theta) = ((Vector 1) . (Vector 2)) / (||Vector 1|| X ||Vector 2||)
	 * </pre>    
	 * where the . (dot) is the dot product between the vectors and
	 * ||Vector|| represents the magnitude of the vector.
	 * <p>
	 * If v = a*i+b*j and w = c*i+d*j, then 
	 * <pre>
	 *    dot product of v.w = ac + bd
	 *    ||v|| is sqrt (a^2 + b^2).  
	 * </pre>
	 * Once we compute cos(theta), we invoke cos[-1] to compute theta, which will
	 * be a value between 0 and pi. 
	 * 
	 * @param points      points to inspect
	 * @param leftMostX   index of the left-most point
	 * @param i           index of target point to inspect
	 * @return computed angle between the vertical line based at leftMostX and 
	 * the line between points[leftMostX] and points[i].
	 */
	public static double compute(IPoint[] points, int leftMostX, int i) {
		double v2x = points[i].getX()-points[leftMostX].getX();
		double v2y = points[i].getY()-points[leftMostX].getY();
		
		// optimize
		double dotProduct = v2y;
		double magv2 = Math.sqrt(v2x*v2x + v2y*v2y);
		
		double cosTheta = dotProduct/magv2;
		return Math.acos(cosTheta);
	}
	
	
}
