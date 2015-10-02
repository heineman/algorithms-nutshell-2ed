package algs.model.searchtree.states;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;

/**
 * Provide storage that behaves like a queue.
 *
 * {@link INodeSet#insert(INode)} and {@link INodeSet#remove()} are 
 * constant time operations. However, {@link INodeSet#contains(INode)}
 * becomes O(n) since the entire queue must be inspected. Finally,
 * {@link INodeSet#remove(INode)} is O(n) since whole queue must be searched.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StateQueue implements INodeSet {

	/** List whose insert is at the end. We use removeFirst to take from front. */
	DoubleLinkedList<INode> queue = new DoubleLinkedList<INode>();
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return queue.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#iterator()
	 */
	public Iterator<INode> iterator() {
		return queue.iterator();
	}
	
	/** 
	 * Insert node places at end of queue.
	 * 
	 * @param n   INode to be inserted into the set.
	 */
	public void insert(INode n) {
		queue.insert(n);
	}

	/** 
	 * Remove a node by taking the first one from the queue.
	 */
	public INode remove() {
		return queue.removeFirst();
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#contains(algs.model.searchtree.INode)
	 */
	public INode contains(INode n) {
		DoubleNode<INode> dn = queue.first();
		while (dn != null) {
			if (dn.value().equals(n)) {
				return dn.value();
			}
			
			dn = dn.next();
		}
		
		return null;
	}

	/**
	 * Remove actual value from the set.
	 * <p> 
	 * An existing INode in the set that .equals(n) is selected for removal.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n    the node representing the value to be removed from the list.
	 */
	public boolean remove(INode n) {
		// must get actual INode via contains (which operates on .equals) since remove
		// only operates on ==.
		INode state = contains(n);
		if (state == null) { return false; }
		return queue.remove(state);
	}
}
