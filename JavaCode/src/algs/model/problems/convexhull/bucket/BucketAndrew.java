package algs.model.problems.convexhull.bucket;

import java.util.Iterator;
import java.util.LinkedList;

import algs.model.IPoint;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.PartialHull;

/**
 * Computes Convex Hull following Andrew's Algorithm.
 * 
 * This algorithm is described in the text. We use BucketSort to sort the points.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BucketAndrew implements IConvexHull {
	
	/**  
	 * Take advantage of linear-time sorting over the x-coordinates of the points
	 * using BucketSort.
	 * 
	 * Ties are broken using the y-coordinate. All sorting is done in place.
	 * 
	 * @param points   array of points to be sorted.
	 */
	@SuppressWarnings("unchecked")
	void bucketSort (IPoint[] points) {
		// find min and maximum.
		double min = Integer.MAX_VALUE;
		double max = Integer.MIN_VALUE;
		for (IPoint p : points) {
			double x = p.getX();
			if (x < min) { min = x; }
			if (x > max) { max = x; }
		}
		
		// create n+1 bins within the range [min, max]; n+1 to deal with the right-most bin
		// which will contain the max points.
		int n = points.length;
		double delta = (max - min)/n;
		LinkedList<IPoint>[] buckets = new LinkedList[n+1];
		
		// Use hash=(x - min)/delta to insert all points into the 
		// n buckets.
		for (IPoint p : points) {
			double d = (p.getX() - min)/delta;
			int h = (int) d;
			if (buckets[h] == null) {
				buckets[h] = new LinkedList<IPoint>();
			}
			buckets[h].add(p);
		}
		
		// use insertion sort for all buckets, pulling the values from the
		// array lists and inserting right back into the points[] array.
		int idx = 0;
		for (LinkedList<IPoint> entries : buckets) {
			
			// empty bucket? continue
			if (entries == null) continue;
			int sz = entries.size(); 
			if (sz == 0) { continue; }
			
			// just a single entry?
			if (sz == 1) {
				points[idx++] = entries.get(0);
				continue;
			}
			
			// use insertion sort on remaining elements to place into
			// points[idx, idx+sz).
			Iterator<IPoint> it = entries.iterator();
			int low = idx;
			points[idx++] = it.next();
			while (it.hasNext()) {
				int i = idx-1;
				IPoint entry = it.next();
				while (i >= low && IPoint.xy_sorter.compare(points[i], entry) > 0) {
					points[i+1] = points[i];
					i--;
				}
				points[i+1] = entry;
				idx++;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.problems.convexhull.IConvexHull#compute(algs.model.IPoint[])
	 */
	public IPoint[] compute (IPoint[] points) {
		int n = points.length;
		if (n < 3) { return points; }

		// linear-time sort of points.
		bucketSort(points);

		// form hulls.
		PartialHull upper = new PartialHull(points[0], points[1]);
		for (int i = 2; i < n; i++) {
			upper.add(points[i]);
			while (upper.hasThree() && upper.areLastThreeNonRight()) {
				upper.removeMiddleOfLastThree();
			}
		}
		
		PartialHull lower = new PartialHull(points[n-1], points[n-2]);
		for (int i = n-3; i >=0; i--) {
			lower.add(points[i]);
			while (lower.hasThree() && lower.areLastThreeNonRight()) {
				lower.removeMiddleOfLastThree();
			}
		}
		
		// remove duplicate end points when combining.
		IPoint[]hull = new IPoint[upper.size()+lower.size()-2];
		System.arraycopy(upper.getPoints(), 0, hull, 0, upper.size());
		System.arraycopy(lower.getPoints(), 1, hull, upper.size(), lower.size()-2);
		
		return hull;		
	}
}
