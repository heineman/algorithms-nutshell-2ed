package algs.model.searchtree.debug;

import java.util.Iterator;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.*;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * Given an initial state, a target goal state, expand in depth-first
 * manner all available moves until the target goal state is reached.
 * <p>
 * This search can be terminated with a provided fixed bound.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DepthFirstSearch implements ISearch {
	
	/** Depth bound. */
	int depthBound;
	
	/** Is this search executing in debug mode. */
	IDebugSearch debug = null;
	
	/** Storage type. Defaults to Hash. */
	int closedStorage = StateStorageFactory.HASH;

	/** To turn off legend, set to false. */
	boolean legendOn = false;

	/**
	 * Construct a depth-first search entity with no fixed depth.
	 */
	public DepthFirstSearch () {
		this (Integer.MAX_VALUE);
	}
	
	/**
	 * Determine structure to use for storing CLOSED set.
	 * @param type    the type of storage for the closed set. 
	 */
	public void storageType (int type) {
		closedStorage = type;
	}
	
	/**
	 * Construct a depth-fixed search entity.
	 * 
	 * @param bound    maximum depth to search
	 */
	public DepthFirstSearch (int bound) {
		this.depthBound = bound;
	}

	/**
	 * Use (or disuse) legend.
	 * @param b   new state for legendOn
	 */
	public void useLegend(boolean b) {
		legendOn = b;
	}	
	
	/**
	 * Set the debugger to use when searching (or null to turn off).
	 * @param debugger   the new debugger to install.
	 * @return the prior debugger used. 
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
	 */
	public Solution search(INode initial, INode goal) {
		// solved already? Leave now!
		if (initial.equals(goal)) {
			if (debug != null) { 
				debug.markGoal (initial);
				
				if (legendOn) {
					debug.visitNode(new Legend("DepthFirst Search with depth-limit:" + depthBound + " Processed:" + 0 + " , open:" +0));
				}
			}
			return new Solution (initial, goal, debug);
		}			
		
		// Start from the initial state
		INodeSet open = StateStorageFactory.create(StateStorageFactory.STACK);
		INode copy = initial.copy();
		open.insert(copy); 
		
		if (debug != null) { debug.visitNode(copy); debug.markStart(copy); }
		// states we have already visited.
		INodeSet closed = StateStorageFactory.create(closedStorage);
		while (!open.isEmpty()) {
			INode n = open.remove();
			closed.insert(n);
			
			// If at (or past) the depth bound, search no more at this state
			DepthTransition trans = (DepthTransition) n.storedData();
			
			// Search no more at this state if no more moves left.
			DoubleLinkedList<IMove> moves = n.validMoves();
			
			// All successor moves translate into appended OPEN states.
			// Verify that we are not immediately undoing the prior move?
			for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
				IMove move = it.next();
				
				INode successor = n.copy();
				move.execute(successor);
				numMoves++; /* STATS */
					
				// If already visited, search this state no more
				if (closed.contains(successor) != null) {
					continue;
				}

				int depth = 1;
				if (trans != null) { depth = trans.depth+1; }
					
				// Record previous move for solution trace. If solution, leave 
				// now, otherwise add to the OPEN set if still within depth bound
				successor.storedData(new DepthTransition(move, n, depth));
				if (debug != null) { debug.visitNode (successor); debug.visitEdge(n, successor); } 
				if (successor.equals(goal)) {
					if (debug != null) { 
						debug.markGoal (successor);
						for (Iterator<INode> it2 = open.iterator(); it2.hasNext(); ) {
							debug.markUnexplored(it2.next());
						}
						if (legendOn) {
							debug.visitNode(new Legend("DepthFirst Search with depth-limit:" + depthBound + " Processed:" + closed.size() + " , open:" + open.size()));
						}
					}
					numOpen = open.size(); numClosed = closed.size();  /* STATS */
					// Defect fix: was n, but eagle-eyed reader through errata detected
					// it should have been successor. 5-8-2015
					return new Solution (initial, successor, debug);
				}
				if (depth < depthBound) { open.insert (successor); }
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
