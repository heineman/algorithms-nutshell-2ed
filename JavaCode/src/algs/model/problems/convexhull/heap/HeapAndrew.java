package algs.model.problems.convexhull.heap;

import java.util.Iterator;
import java.util.LinkedList;

import algs.model.IPoint;
import algs.model.heap.ExternalBinaryHeap;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.PartialHull;

/**
 * Computes Convex Hull following Andrew's Algorithm. This is described
 * in the text.
 * 
 * Uses a BinaryHeap as the sorting entity for the points.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class HeapAndrew implements IConvexHull {
	
	/**
	 * Use Andrew's algorithm to return the computed convex hull for 
	 * the input set of points. Implementation uses a Binary Heap rather
	 * than sorting initial set.
	 * 
	 * @param points   a set of (n &ge; 3) two dimensional points.
	 */
	public IPoint[] compute (IPoint[] points) {
		int n = points.length;
		if (n < 3) { return points; }
		
		ExternalBinaryHeap<IPoint> heap = new ExternalBinaryHeap<IPoint> (n, IPoint.xy_sorter);

		// add points into sorted structure, using xy_sorter
		for (IPoint p : points) { heap.insert(p); }
		
		// While computing upper hull, build list of points in reverse order.
		LinkedList<IPoint> list = new LinkedList<IPoint>();
		IPoint p1 = heap.smallest();
		list.addFirst(p1);
		IPoint p2 = heap.smallest();
		list.addFirst(p2);
		PartialHull upper = new PartialHull(p1, p2);
		while (!heap.isEmpty()) {
			p1 = heap.smallest();
			list.addFirst(p1);
			
			upper.add(p1);
			while (upper.hasThree() && upper.areLastThreeNonRight()) {
				upper.removeMiddleOfLastThree();
			}
		}
		
		// process over the points and compute lower hull
		Iterator<IPoint> it = list.listIterator();
		p1 = it.next();
		p2 = it.next();
		PartialHull lower = new PartialHull(p1, p2);
		while (it.hasNext()) {
			p1 = it.next();
			
			lower.add(p1);
			while (lower.hasThree() && lower.areLastThreeNonRight()) {
				lower.removeMiddleOfLastThree();
			}
		}
		
		// remove duplicate end points when combining.
		IPoint[]hull = new IPoint[upper.size()+lower.size()-2];
		int idx = 0;
		Iterator<IPoint> pit = upper.points();
		while (pit.hasNext()) { hull[idx++] = pit.next();	}
		pit = lower.points();
		pit.next(); // skip point that already has been added
		while (idx < hull.length) { hull[idx++] = pit.next(); }
		return hull;
	}
	
}
