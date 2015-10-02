package algs.model.kdtree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import algs.model.IMultiPoint;

/**
 * Enables quick intersection detection by KD Tree and places burden
 * of extraction onto the client retrieving the points using an iterator.
 *
 * Single points can be added, as well as entire subtrees. The order of the
 * points being returned doesn't matter, so this iterator maintains two
 * separate lists over which to be iterated.
 * 
 * Once {@link #hasNext()} is invoked, no more points can be added.
 * @since 2.0
 * @author George Heineman
 * @version 2.0, 4/25/15
 */
public class KDSearchResults implements Iterator<IMultiPoint> {
	/** Storage of nodes. */
	ArrayList<DimensionalNode> nodes = new ArrayList<DimensionalNode>();
	
	/** Storage of points. */
	ArrayList<IMultiPoint> points = new ArrayList<IMultiPoint>();
	
	/** Once begun, start with points iterator. */
	Iterator<IMultiPoint> pointsIterator = null;
	
	
	/** Once true, iteration has begun and no more points or nodes can be added. */
	boolean inProgress = false;
	
	public KDSearchResults () {
		
	}
	
	/**
	 * Add point to the result set.
	 * 
	 * @exception ConcurrentModificationException    if iterator already begun
	 * @param pt  point to be added to the search results
	 */
	public void add(IMultiPoint pt) {
		if (inProgress) { 
			throw new ConcurrentModificationException ("Can't add point while iterator in progress");
		}
		points.add(pt);
	}
	
	/**
	 * Add subtree rooted at given node to the result set.
	 * 
	 * @exception ConcurrentModificationException    if iterator already begun
	 * @param dn    node representing entire subtree of points to be added to the search.
	 */
	public void add(DimensionalNode dn) {
		if (inProgress) { 
			throw new ConcurrentModificationException ("Can't add point while iterator in progress");
		}
		nodes.add(dn);
	}
	
	@Override
	public boolean hasNext() {
		if (!inProgress) {
			inProgress = true;
			pointsIterator = points.iterator();
		}

		if (pointsIterator.hasNext()) { return true; }
		
		// once points iterator is 'drained' try to replenish from nodes
		if (nodes.isEmpty()) { return false; }
			
		return true;
	}

	@Override
	public IMultiPoint next() {
		if (pointsIterator.hasNext()) {
			return pointsIterator.next();
		}

		// advance by removing DimensionalNode values
		DimensionalNode next = nodes.remove(0);
		pointsIterator = new DimensionalNodeIterator(next);
		
		// should always be at least one point, 
		return pointsIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported");
	}
	

}
