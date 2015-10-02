package algs.model.searchtree;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;


/**
 * Maintains the set of closed states in ordered fashion, so the state with the 
 * lowest evaluation function can be removed.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ClosedStates implements INodeSet, Iterable<INode> {
	
	/** Use double linked list for storage. */
	DoubleLinkedList<INode> closed;
	
	public ClosedStates() {
		closed = new DoubleLinkedList<INode>();
	}
	
	/**
	 * Remove state from list if its score is less than the score of the
	 * state as it exists within list.
	 * 
	 * Return -1 if the state wasn't on the list to begin with.
	 * Return  0 if the state is on the list and our score is higher or equal.
	 * Return +1 if the state was removed because its score was lowered.
	 * 
	 * @param state   comparable board state used to make decisions.
	 * @return -1 if state doesn't exist in ClosedStates, 0 if our score is higher, or 1 if removed.
	 */
	public int removeIfLowerScore(INode state) {
		if (state == null) {
			throw new NullPointerException ("removeIfLowerScore received null state");
		}
		
		INode prev = closed.contains(state);
		if (prev == null) return -1;
		
		if (state.score() < prev.score()) {
			// We have improved. Remove from CLOSED and alert caller this happened. This
			// must be 'prev' to ensure proper removal.
			closed.remove(prev);
			return +1;
		}
		
		return 0;
	}
	
	/**
	 * Determine if the given state is contained.
	 * <p>
	 * Returns the actual node as stored in the list (which may store additional
	 * storedData). Uses .equals as the comparator method.
	 * 
	 * @param n query board state.
	 */
	public INode contains (INode n) {
		return closed.contains(n);
	}
	
	/**
	 * Insert the board state into the openStates.
	 * 
	 * @param n   board state to insert.
	 */
	public void insert (INode n) {
		closed.insert(n);
	}
	
	/** 
	 * Determine if closed states is empty. 
	 * 
	 * @return true if closed states is an empty set.
	 */
	public boolean isEmpty() {
		return closed.isEmpty();
	}
	
	/**
	 * Determine number of states in the closed set. 
	 * 
	 * @return size of the closed set.
	 */
	public int size() {
		return closed.size();
	}

	/** 
	 * Expose iterator to internal board states. 
	 *
	 * @return iterator to process all board states in the closed set.
	 */ 
	public Iterator<INode> iterator() {
		return closed.iterator();
	}
	
	/** Not expected to be called since this is the closed list. */
	public INode remove() {
		throw new IllegalStateException ("No semantic meaning associated with default remove operation on ClosedStates.");
	}

	/**
	 * Remove node from closed states that is equivalent to given state.
	 * 
	 * @param n   target board state to remove.
	 * @return    true board state was removed from the closed set.
	 */
	public boolean remove(INode n) {
		INode state = contains(n);
		if (state == null) { return false; }
		return closed.remove(state);
	}
}
