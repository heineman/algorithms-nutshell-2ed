package algs.model.searchtree;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * Given an initial state, a target goal state, expand in breadth-first
 * manner all available moves until the target goal state is reached.
 * <p>
 * This search approach uses a Stack to store the OPEN set, to expand the 
 * most recently added board states, until the depth limit is reached.
 * <p>
 * Note that different performance can be obtained by setting the closedStorage
 * appropriately. If set to {@link StateStorageFactory}.HASH then performance
 * benefits will be seen. If set to {@link StateStorageFactory}.TREEWITHKEY then
 * the boardstate Key will be used to differentiate boards. With board states that
 * exhibit rotational symmetry, this method will achieve faster search times.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DepthFirstSearch implements ISearch {
	
	/** Depth bound. */
	int depthBound;
	
	/** Storage type. Defaults to HASH for performance benefits. */
	int closedStorage = StateStorageFactory.HASH;
	
	/**
	 * Depth-First with no bound.
	 */
	public DepthFirstSearch () {
		this (Integer.MAX_VALUE);
	}
	
	/**
	 * Initiate the Depth First Search with the given fixed-depth bound to search.
	 * @param bound    fixed depth to which to search.
	 */
	public DepthFirstSearch (int bound) {
		this.depthBound = bound;
	}
	
	/** 
	 * Determine structure to use for storing CLOSED set.
	 * 
	 * Possible types are drawn from {@link StateStorageFactory}
	 * @param type  type of storage to use for closed sets
	 */
	public void storageType (int type) {
		closedStorage = type;
	}

	/** 
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 */
	public Solution search(INode initial, INode goal) {
		// If initial is the goal, return now.
		if (initial.equals(goal)) { return new Solution (initial, goal); }
		
		INodeSet open = StateStorageFactory.create(StateStorageFactory.STACK);
		open.insert (initial.copy()); 
		
		// states we have already visited.
		INodeSet closed = StateStorageFactory.create (closedStorage);
		while (!open.isEmpty()) {
			INode n = open.remove();
			closed.insert (n);
						
			DepthTransition trans = (DepthTransition) n.storedData();
			
			// All successor moves translate into appended OPEN states.
			DoubleLinkedList<IMove> moves = n.validMoves();
			for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
				IMove move = it.next();
				
				// Execute move on a copy since we maintain sets of board states
				INode successor = n.copy();
				move.execute (successor);
				numMoves++; /* STATS */
					
				// If already visited, try another state
				if (closed.contains (successor) != null) { continue; }

				int depth = 1;
				if (trans != null) { depth = trans.depth+1; }
					
				// Record previous move for solution trace. If solution, leave 
				// now, otherwise add to the OPEN set if still within depth bound.
				successor.storedData (new DepthTransition (move, n, depth));
				if (successor.equals (goal)) {
					numOpen = open.size(); numClosed = closed.size();  /* STATS */
					return new Solution (initial, successor);
				}
				if (depth < depthBound) { open.insert (successor); }
			}
		}
		
		// No solution.
		numOpen = open.size(); numClosed = closed.size();  /* STATS */
		return new Solution (initial, goal, false);
	}

	// statistical information to evaluate algorithms effectiveness.
	public int numMoves = 0;
	public int numOpen = 0;
	public int numClosed = 0;
}
