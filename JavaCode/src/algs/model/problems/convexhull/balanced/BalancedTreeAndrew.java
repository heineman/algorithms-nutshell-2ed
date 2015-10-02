package algs.model.problems.convexhull.balanced;

import java.util.Iterator;

import algs.model.IPoint;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.PartialHull;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * Computes Convex Hull following Andrew's Algorithm. This algorithm is described
 * in the text.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BalancedTreeAndrew implements IConvexHull {
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.problems.convexhull.IConvexHull#compute(algs.model.IPoint[])
	 */
	public IPoint[] compute (IPoint[] points) {
		int n = points.length;
		if (n < 3) { return points; }
		
		// Use Balanced Binary tree to store points in left-to-right order.
		BalancedTree<IPoint,IPoint> tree = new BalancedTree<IPoint,IPoint>(IPoint.xy_sorter);

		// add points into sorted structure.
		for (IPoint p : points) { tree.insert(p, p); }
		
		BalancedBinaryNode<IPoint,IPoint> node = tree.firstNode();
		BalancedBinaryNode<IPoint,IPoint> next = tree.successor(node);
		
		PartialHull upper = new PartialHull(node.key(), next.key());
		while (true) {
			node = next;
			next = tree.successor(next);
			if (next == null) break;
			upper.add(next.key());
			while (upper.hasThree() && upper.areLastThreeNonRight()) {
				upper.removeMiddleOfLastThree();
			}
		}
		
		node = tree.lastNode();
		next = tree.pred(node);
		
		PartialHull lower = new PartialHull(node.key(), next.key());
		while (true) {
			node = next;
			next = tree.pred(next);
			if (next == null) break;
			
			lower.add(next.key());
			while (lower.hasThree() && lower.areLastThreeNonRight()) {
				lower.removeMiddleOfLastThree();
			}
		}
		
		// remove duplicate end points when combining.
		IPoint[]hull = new IPoint[upper.size()+lower.size()-2];
		int idx = 0;
		Iterator<IPoint> it = upper.points();
		while (it.hasNext()) { hull[idx++] = it.next();	}
		it = lower.points();
		it.next(); // skip point that already has been added
		while (idx < hull.length) { hull[idx++] = it.next(); }
		return hull;
	}
}
