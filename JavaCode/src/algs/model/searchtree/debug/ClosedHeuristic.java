package algs.model.searchtree.debug;

import java.util.Comparator;
import java.util.Iterator;

import algs.debug.IDebugSearch;
import algs.debug.Legend;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.states.StateStorageFactory;
import algs.model.searchtree.*;

/**
 * Given an initial state and a target goal state, expand successors, always choosing
 * to expand the node in the OPEN list whose evaluation is the smallest. This is not
 * A* search because it focuses on closed states.
 * 
 * Ties are broken randomly, except when one of the tied nodes is a goal node.
 * 
 * This was the broken implementation of A* as found in the 1st edition. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ClosedHeuristic implements ISearch {
	
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
	public ClosedHeuristic (IScore sf) {
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
		// Start from the initial state (was ORDERED).
		INodeSet open = StateStorageFactory.create(StateStorageFactory.TREE);
		INode copy = initial.copy();
		scoringFunction.score(copy);
		open.insert(copy); 

		// states we have already visited.
		INodeSet closed = StateStorageFactory.create(closedStorage);
		if (debug != null) { debug.visitNode(copy); debug.markStart(copy); }		
		while (!open.isEmpty()) {
		    // Remove node with smallest evaluation function and mark closed.
			INode n = open.remove();
			closed.insert(n);
			
			// Return if Goal State reached.
			if (n.equals(goal)) {
				if (debug != null) { 
					debug.markGoal (n);
					for (Iterator<INode> it = open.iterator(); it.hasNext(); ) {
						debug.markUnexplored(it.next());
					}
					if (legendOn) {
						debug.visitNode(new Legend("A*Search [" + scoringFunction.getClass().getSimpleName() + "] Processed:" + closed.size() + " , open:" + open.size()));
					}
				}
				numOpen = open.size(); numClosed = closed.size();  /* STATS */
				return new Solution (initial, n, debug);
			}
			
			
			// Compute successor moves and update OPEN/CLOSED lists.
			DepthTransition trans = (DepthTransition) n.storedData();
			int depth = 1;
			if (trans != null) { depth = trans.depth+1; }

			DoubleLinkedList<IMove> moves = n.validMoves();
			for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
				IMove move = it.next();
				
				// Make move and score the new board state.
				INode successor = n.copy();
				move.execute(successor);
				
			    // Record previous move for solution trace and compute evaluation 
			    // function to see if we have improved upon a state we had already
				// closed.
				successor.storedData(new DepthTransition(move, n, depth));
				scoringFunction.score(successor);
				
				// If already visited, see if we are revisiting with lower cost.
				// If not, just continue; otherwise, pull out of closed and process
				INode past = closed.contains(successor);
				if (past != null) {
					if (successor.score() >= past.score()) {
						continue;
					}
					
					if (debug != null) { 
						debug.markUnexplored(successor); 
						debug.visitNode (successor); debug.visitEdge(n, successor);
					}
					
					// we revisit with our lower cost.
					closed.remove(past);
				}
				
				// Record previous move for solution trace and complete evaluation. 
				
				numMoves++; /* STATS */

				if (debug != null) { debug.visitNode (successor); debug.visitEdge(n, successor); } 

				open.insert (successor);
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
