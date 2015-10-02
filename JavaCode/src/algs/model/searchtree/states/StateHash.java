package algs.model.searchtree.states;

import java.util.Hashtable;
import java.util.Iterator;

import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;

/**
 * Maintains the set of open states using a Hash.
 * <p>
 * {@link INodeSet#insert(INode)} and {@link INodeSet#remove(INode)} can be 
 * essentially linear if the {@link Object#hashCode()} function for INode properly distributes the
 * potential set of INode objects uniformly.
 * 
 * Note that remove is unsupported in this context because there is no
 * clear interpretation that makes external sense. The Hashtable constructed
 * to store the states could return the INode objects in an implementation-specific
 * way, but that would not be testable under any circumstance.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StateHash implements INodeSet {

	/** Initial capacity. Settable for debugging purposes. */
	public static int initialCapacity = 1009;
	
	/** Start with about 1,000 initial capacity. */
	Hashtable<INode,INode> table;

	/** Construct hash to store INode objects. */
	public StateHash () {
		 table = new Hashtable<INode, INode>(initialCapacity);
	}
	
	/**
	 * Insert a node into the hash.
	 * 
	 * @param n  node to be inserted into hash.
	 */
	public void insert(INode n) {
		table.put(n, n);
	}

	/** Not supported since it makes no sense in this context. */
	public INode remove() {
		throw new UnsupportedOperationException("remove() operation not supported by StateHash");
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return table.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return table.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#iterator()
	 */
	public Iterator<INode> iterator() {
		return table.keySet().iterator();
	}
	
	/**
	 * Locate element stored in set that equals n.
	 * 
	 * Using the hashCode method for the INode to locate the proper hash 
	 * bucket and then the equals check is performed. Thus this method only
	 * works if the INode implementation properly codes {@link Object#equals(Object)}
	 * and {@link Object#hashCode()}.
	 *  
	 * @param n     target node to be searched for
	 */
	public INode contains(INode n) {
		return table.get(n);
	}

	/**
	 * Remove actual entry from the set.
	 * <p> 
	 * The INode passed in must be an actual value returned by {@link INodeSet#contains(INode)}.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n   the node representing the entry to be removed.
	 */
	public boolean remove(INode n) {
		return table.remove(n) != null;
	}
}
