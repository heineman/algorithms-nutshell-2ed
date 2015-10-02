package algs.model.searchtree;

import java.util.Iterator;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * Given an initial state and a target goal state, expand successors, always choosing
 * to expand the node in the OPEN list whose evaluation is the smallest. This is not
 * A* search because it focuses on closed states.
 * 
 * Ties are broken randomly, except when one of the tied nodes is a goal node.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ClosedHeuristic implements ISearch {
	
	/** Scoring function to use. */
	IScore scoringFunction;
	
	/** Storage type. Defaults to HASH. */
	int closedStorage = StateStorageFactory.HASH;
	
	/** 
	 * Determine structure to use for storing CLOSED set. 
	 * @param type   type of storage to use for closed set.
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
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 */
	public Solution search(INode initial, INode goal) {
		// Start from the initial state
		INodeSet open = StateStorageFactory.create(StateStorageFactory.TREE);
		INode copy = initial.copy();
		scoringFunction.score(copy);
		open.insert(copy); 
		
		// states we have already visited are stored in a queue unless configured.
		INodeSet closed = StateStorageFactory.create(closedStorage);
		while (!open.isEmpty()) {
		    // Remove node with smallest evaluation function and mark closed.
			INode n = open.remove();
			closed.insert(n);
			
			// Return if goal state reached.
			if (n.equals(goal)) {
				numOpen = open.size(); numClosed = closed.size();  /* STATS */
				return new Solution (initial, n);
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
				
			    // Record previous move for solution trace and compute  
			    // evaluation function to see if we have improved upon 
				// a state already closed
				successor.storedData(new DepthTransition(move, n, depth));
				scoringFunction.score(successor);

				numMoves++; /* STATS */

				// If already visited, see if we are revisiting with lower cost.
				// If not, just continue; otherwise, pull out of closed and process
				INode past = closed.contains(successor);
				if (past != null) {
					if (successor.score() >= past.score()) {
						continue;
					}
					
					// we revisit with our lower cost.
					closed.remove(past);
				}
				
					
				// place into open. 
				open.insert (successor);
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
