package algs.model.searchtree.states;

import java.util.Comparator;
import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;

/**
 * Maintains the set of open states in ordered fashion, so the state with the 
 * lowest evaluation function can be removed.
 * 
 * This straw man implementation using linked lists to store the information,
 * thus the key operations are:
 * <ol>
 * <li> INSERT -- O(n)</li>
 * <li> REMOVE -- O(n)</li>
 * <li> CONTAINS -- O(n)</li>
 * </ol>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StateOrdered implements INodeSet {
	// Use the built in scoring as our comparator.
	Comparator<INode> comparator = new Comparator<INode>() {
		public int compare(INode o1, INode o2) {
			return o1.score() - o2.score();
		}
	};
	
	/** Use double linked list for storage. */
	DoubleLinkedList<INode> list = new DoubleLinkedList<INode>(comparator);
	
	/** Store states using double linked list. */
	public StateOrdered() {}
	
	/**
	 * Insert the board state into the set.
	 * 
	 * @param n  board state to be added.
	 */
	public void insert (INode n) {
		list.insert(n);
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return list.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#iterator()
	 */
	public Iterator<INode> iterator() {
		return list.iterator();
	}
	
	/**
	 * Remove board state with lowest evaluated score.
	 */
	public INode remove () {
		return list.removeFirst();
	}

	/**
	 * Determine if contained within the set.
	 * <p> 
	 * An existing INode in the set that .equals(n) is selected and returned.
	 * 
	 * @param n   the desired node.
	 */
	public INode contains(INode n) {
		DoubleNode<INode> dn = list.first();
		while (dn != null) {
			if (dn.value().equals(n)) {
				return dn.value();
			}
			
			dn = dn.next();
		}
		
		return null;
	}

	/**
	 * Remove from the set.
	 * <p>
	 * An existing INode in the set that .equals(n) is selected for removal.
	 * 
	 * @param n   the INode to be removed from the set.
	 * @return    true if node was removed; false otherwise.
	 */ 
	public boolean remove(INode n) {
		INode state = contains(n);
		if (state == null) { return false; }
		return list.remove(state);
	}	
}
