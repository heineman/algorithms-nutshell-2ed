package algs.model.searchtree.debug;

import java.util.Comparator;
import java.util.Iterator;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.searchtree.states.StateStorageFactory;
import algs.model.searchtree.*;

/**
 * Given an initial state and a target goal state, expand successors, always choosing
 * to expand the node in the OPEN list whose evaluation is the smallest. Proper 
 * implementation of AStar using data structure for open that supports efficient
 * contains() as well as getMin() by using extra storage.
 * 
 * Ties are broken randomly, except when one of the tied nodes is a goal node.
 * 
 * @author George Heineman
 * @version 2.0, 8/30/15
 * @since 1.0
 */
public class AStarSearch implements ISearch {
	
	/** Scoring function to use. */
	IScore scoringFunction;
	
	/** Is this search executing in debug mode. */
	IDebugSearch debug = null;
	
	/** Evaluation function. */
	Comparator<INode> comparator;
	
	/** To turn off legend, set to false. */
	boolean legendOn = true;
	
	/** Storage type. Defaults to HASH. */
	int closedStorage = StateStorageFactory.HASH;
	
	/** 
	 * Determine structure to use for storing CLOSED set.
	 * @param type    storage type for the closed set.
	 */
	public void storageType (int type) {
		closedStorage = type;
	}
	
	/**
	 * Prepare an A* search using the given scoring function.
	 * 
	 * @param sf   static evaluation function 
	 */ 
	public AStarSearch (IScore sf) {
		this.scoringFunction = sf;
	}
	
	/** 
	 * Set the debugger to use when searching (or null to turn off).
	 * @param debugger    the debugger to use. 
	 * @return   the former debugger used.
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
	 */
	public Solution search(INode initial, INode goal) {
		// Start from the initial state
		INodeSet open = StateStorageFactory.create(StateStorageFactory.PRIORITY_RETRIEVAL);
		INode copy = initial.copy();
		scoringFunction.score(copy);
		open.insert(copy); 

		// states we have already visited.
		INodeSet closed = StateStorageFactory.create(closedStorage);
		if (debug != null) { debug.visitNode(copy); debug.markStart(copy); }		
		while (!open.isEmpty()) {
		    // Remove node with smallest evaluation function and mark closed.
			INode best = open.remove();
			
			// Return if goal state reached.
			if (best.equals(goal)) {
				if (debug != null) { 
					debug.markGoal (best);
					for (Iterator<INode> it = open.iterator(); it.hasNext(); ) {
						debug.markUnexplored(it.next());
					}
					if (legendOn) {
						debug.visitNode(new Legend("A*Search [" + scoringFunction.getClass().getSimpleName() + "] Processed:" + closed.size() + " , open:" + open.size()));
					}
				}
				numOpen = open.size(); numClosed = closed.size();  /* STATS */
				return new Solution (initial, best, debug);
			}
			closed.insert(best);
			
			// Compute successor moves and update OPEN/CLOSED lists.
			DepthTransition trans = (DepthTransition) best.storedData();
			int depth = 1;
			if (trans != null) { depth = trans.depth+1; }

			for (IMove move : best.validMoves()) {
				numMoves++; /* STATS */
				
				// Make move and score the new board state.
				INode successor = best.copy();
				move.execute(successor);
				
				if (closed.contains(successor) != null) {
					continue;
				}
				
			    // Record previous move for solution trace and compute
			    // evaluation function to see if we have improved
				successor.storedData(new DepthTransition(move, best, depth));
				scoringFunction.score(successor);

				// If not yet visited, or it has better score
				INode exist = open.contains(successor);
				if (exist == null || successor.score() < exist.score()) {
					
					// remove old one, if one had existed, and insert better one
					if (exist != null) {
						open.remove(exist);
					}
					if (debug != null) { debug.visitNode (successor); debug.visitEdge(best, successor); } 
					open.insert(successor);
				}
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
