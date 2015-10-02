package algs.model.problems.convexhull.andrew;

import algs.model.IPoint;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.problems.convexhull.IConvexHull;

/**
 * Computes Convex Hull following Andrew's Algorithm with linked lists. This is described
 * in the text.
 *
 * This implementation of ConvexHull does not implement {@link IConvexHull} because the
 * resulting double linked list computation would have to be converted to an array simply
 * to be returned. In doing so, the performance costs would be higher than they should be.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ConvexHullScanLinkedList {
	
	/**
	 * Use Andrew's algorithm to return the computed convex hull for 
	 * the input set of points. Uses linked lists to store information.
	 * 
	 * @param points   a set of (n &ge; 3) two dimensional points.
	 * @return convex hull stored using a linked list from initial points 
	 */
	public DoubleLinkedList<IPoint> compute (IPoint[] points) {
		int n = points.length;

		DoubleLinkedList<IPoint> list = new DoubleLinkedList<IPoint>(IPoint.xy_sorter);
		DoubleNode<IPoint> node = list.first();
		// sort by x-coordinate (and if ==, by y-coordinate) using insertion Sort.
		for (IPoint tdp : points) {
			list.insert(tdp);
		}
		
		// place all into the linked list.
		if (n < 3) {
			DoubleLinkedList<IPoint> dl = new DoubleLinkedList<IPoint>(IPoint.xy_sorter);
			for (IPoint t : points) {
				dl.insert (t);
			}
			return dl; 
		}
	
		node = list.first();
		PartialLinkedListHull upper = new PartialLinkedListHull(node.value(), node.next().value());
		for (DoubleNode<IPoint> p = node.next().next(); p != null; p = p.next()) {
			upper.add (p.value());
			while (upper.hasThree() && upper.areLastThreeNonRight()) {
				upper.removeMiddleOfLastThree();
			}
		}
		
		DoubleNode<IPoint> last = list.last();
		PartialLinkedListHull lower = new PartialLinkedListHull(last.value(), last.prev().value());
		for (DoubleNode<IPoint> p = last.prev().prev(); p != null; p = p.prev()) {
			lower.add (p.value());
			while (lower.hasThree() && lower.areLastThreeNonRight()) {
				lower.removeMiddleOfLastThree();
			}
		}
		
		// Snip off and join.
		DoubleLinkedList<IPoint> upList = upper.points();
		DoubleLinkedList<IPoint> downList = lower.points();
		
		// snip off from front and rear.
		downList.removeFirst();
		downList.removeLast();
		
		// mangles up the lists. Note that if there are no more points
		// in the bottom one, then we are simply done!
		DoubleNode<IPoint> lastOne = upList.last();
		DoubleNode<IPoint> head = downList.first();
		if (head != null) {
			head.prev(last);
			lastOne.next(head);
		}
		
		downList = null;  // destroy this one from use. has no integrity now.
		return upList;
	}
}

