package algs.model.searchtree.debug;

import java.util.Iterator;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.*;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * Given an initial state and a target goal state, expand in breadth-first
 * manner all available moves until the target goal state is reached.
 * 
 * This search approach is guaranteed to find the shortest possible path to
 * the goal state.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BreadthFirstSearch implements ISearch {
	
	/** Is this search executing in debug mode. */
	IDebugSearch debug = null;
	
	/** Storage type. Defaults to HASH for performance benefits. */
	int closedStorage = StateStorageFactory.HASH;
	
	/** 
	 * Determine structure to use for storing CLOSED set. 
	 * @param type   the type of storage to use for the closed set. 
	 */
	public void storageType (int type) {
		closedStorage = type;
	}
	
	/**
	 * Set the debugger to use when searching (or null to turn off).
	 * @param debugger   debugger to use during search (or null if no debugging)
	 * @return {@link IDebugSearch} object to use for debugging and animations. 
	 */
	public IDebugSearch debug(IDebugSearch debugger) {
		IDebugSearch old = debug;
		debug = debugger;
		return old;
	}
	
	/** 
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 * 
	 * Do we avoid generating moves that undo previous move?
	 * @param initial    initial {@link INode} state
	 * @param goal       destination {@link INode} state
	 * @return           {@link Solution} computed by algorithm.
	 */
	public Solution search(INode initial, INode goal) {
		// solved already? Leave now!
		if (initial.equals(goal)) {
			if (debug != null) { 
				debug.markGoal (initial);
				debug.visitNode(new Legend("Breadth-First Search Processed:" + 0 + " , open:" +0));
			}
			return new Solution (initial, goal, debug);
		}		
		
		// Start from the initial state
		INodeSet open = StateStorageFactory.create(StateStorageFactory.QUEUE);
		INode copy = initial.copy();
		open.insert(copy); 
		
		// states we have already visited.
		INodeSet closed = StateStorageFactory.create(closedStorage);
		if (debug != null) { debug.visitNode(copy); debug.markStart(copy); }
		while (!open.isEmpty()) {
			INode n = open.remove();
			closed.insert(n);
			
			// All successor moves translate into appended OPEN states.
			// Verify that we are not immediately undoing the prior move?
			DoubleLinkedList<IMove> moves = n.validMoves();
			for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
				IMove move = it.next();
				
				// make move on a copy
				INode successor = n.copy();
				move.execute(successor);
				numMoves++; /* STATS */
					
				// If already visited, search this state no more
				if (closed.contains(successor) != null) {
					continue;
				}
			
				// Record previous move for solution trace. If solution, leave 
				// now, otherwise add to the OPEN set.
				successor.storedData(new Transition(move, n));
				if (debug != null) { debug.visitNode (successor); debug.visitEdge(n, successor); } 
				if (successor.equals(goal)) {
					if (debug != null) { 
						debug.markGoal (successor);
						for (Iterator<INode> it2 = open.iterator(); it2.hasNext(); ) {
							debug.markUnexplored(it2.next());
						}
					}
					numOpen = open.size(); numClosed = closed.size();  /* STATS */
					return new Solution (initial, successor, debug);
				}
				open.insert(successor);
			}
		}
		
		// No solution.
		numOpen = open.size(); numClosed = closed.size();  /* STATS */
		return new Solution (initial, goal, debug, false);
	}
	
	// statistical information to evaluate algorithms effectiveness.
	public int numMoves = 0;
	public int numOpen = 0;
	public int numClosed = 0;
}
