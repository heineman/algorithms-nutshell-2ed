package algs.model.searchtree;

import algs.debug.IDebugSearch;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;

/**
 * Records the solution for a search from an initial state to a solved
 * goal state.
 * 
 * In some search algorithms, the goal state is known in advance; in others,
 * it is discovered as the search progresses.
 * 
 * Upon a completed search, the set of moves can be recovered.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Solution {

	/** Initial node. */
	public final INode initial;
	
	/** Goal node. */
	public final INode goal;

	/** Moves that produce the sequence from initial to goal. */
	DoubleLinkedList<IMove> moves;
	
	/** Is this search executing in debug mode. */
	IDebugSearch debug = null;
	
	/** Was this a successful search? */
	boolean success;

	/**
	 * Build the solution and work backwards with a debugger.
	 * 
	 * @param initial  initial state
	 * @param goal     final state
	 * @param debug    entity to help generate debug output
	 */
	public Solution (INode initial, INode goal, IDebugSearch debug) {
		this.initial = initial;
		this.goal = goal;
		this.debug = debug;
		
		solve();
		this.success = true;
	}
	
	/**
	 * Build the solution and work backwards without a debugger.
	 * 
	 * @param initial   initial state
	 * @param goal      final state
	 */
	public Solution (INode initial, INode goal) {
		this (initial, goal, null);
	}
	
	/**
	 * Build the solution and work backwards without a debugger.
	 * 
	 * @param initial   initial state
	 * @param goal      final state
	 * @param success   was this a successful search?
	 */
	public Solution (INode initial, INode goal, boolean success) {
		this (initial, goal, null);
		this.success = success;
	}
	
	/** 
	 * Build solution with success or not.
	 * 
	 * @param initial   initial state
	 * @param goal      final state
	 * @param debug     entity to help generate debug output
	 * @param success   was this a successful search?
	 */
	public Solution (INode initial, INode goal, IDebugSearch debug, boolean success) {
		this (initial, goal, debug);
		this.success = success;
	}
	
	/** 
	 * Sequence of moves that achieve the goalState.
	 * @return sequence of moves as DoubleLinkedList 
	 */
	public DoubleLinkedList<IMove> moves() {
		return moves;
	}

	/** 
	 * Was this a successful solution?
	 * @return true if a successful solution; false otherwise. 
	 */
	public boolean succeeded() {
		return success;
	}
	
	/** 
	 * Return number of moves in the solution. 
	 * @return number of moves in the solution. 
	 */
	public int numMoves() {
		return moves.size();
	}
	
	/** 
	 * Return solution as a string. 
	 * @return human readable string. 
	 */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		
		DoubleNode<IMove> node = moves.first();
		boolean endsWithComma = false;
		while (node != null) {
			sb.append (node.value() + ",");
			node = node.next();
			endsWithComma = true;
		}
		
		if (endsWithComma) {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	/**
	 * Generate the solution for the search by working backwards to initial
	 * goal and then regenerating in forward order.
	 */
	private void solve () {
		INode n = goal;
		
		// Regenerate the trail of nodes into a DoubleLinkedList. 
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>();
		
		// work our way backwards until we terminate at the initial state.
		while (n != null) {
			Transition trans = (Transition) n.storedData();
			
			// gone to the end!
			if (trans == null) {
				break;
			}
			
			if (debug != null) { debug.markEdge(trans.prev, n); }
			
			list.insert(trans.move);
			n = trans.prev;
		}
		
		// List is now the reverse of the solution path. So we reverse it here
		moves = new DoubleLinkedList<IMove>();
		while (!list.isEmpty()) {
			moves.insert(list.removeLast());
		}
	}

}
