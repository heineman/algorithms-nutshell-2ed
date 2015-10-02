package algs.model.problems.nearestNeighbor;

import java.util.ArrayList;

import algs.model.IMultiPoint;

/**
 * Brute Force implementation of Nearest Neighbor Query.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BruteForceNearestNeighbor {
	final ArrayList<double[]> points;
	final IMultiPoint results[];

	/**
	 * Store all points to compute nearest neighbor queries.
	 * 
	 * @param points   points forming the input set P. Must contain at least 1 point.
	 */
	public BruteForceNearestNeighbor(IMultiPoint[] points) {
		if (points == null || points.length == 0) {
			throw new IllegalArgumentException ("BruteForce requires at least one point.");
		}
		
		this.points = new ArrayList<double[]>();
		for (int i = 0; i < points.length; i++) {
			this.points.add(points[i].raw());
		}
		this.results = points;
	}
	
	/**
	 * Return the closest point to x within the input set P.
	 * 
	 * @param x   search point
	 * @return    point in set which is closest to x.
	 */
	public IMultiPoint nearest (IMultiPoint x) {
		double dist = Integer.MAX_VALUE;
		int idx = -1;
		
		double[] xraw = x.raw();
		
		for (int i = 0; i < points.size(); i++) {
			double d = 0;
			double []rawpt = points.get(i);
			
			for (int j = 0; j < xraw.length; j++) {
				double delta = xraw[j] - rawpt[j];
				d +=  delta*delta;
			}
			d = Math.sqrt(d);
			
			if (d < dist) {
				dist = d;
				idx = i;
			}
		}
		
		return results[idx];
	}
	
}
