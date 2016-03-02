package algs.model.searchtree;

import algs.model.searchtree.states.StateStorageFactory;

/**
 * Given an initial state and a target goal state, expand successors, always choosing
 * to expand the node in the OPEN list whose evaluation is the smallest. Should a
 * state be revisited while it exists in the open state, the one with smaller score
 * is kept.
 * 
 * Ties are broken randomly, except when one of the tied nodes is a goal node.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AStarSearch implements ISearch {
	
	/** Scoring function to use. */
	IScore scoringFunction;
	
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
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 */
	public Solution search(INode initial, INode goal) {
		// Start from the initial state
		INodeSet open = StateStorageFactory.create (StateStorageFactory.PRIORITY_RETRIEVAL);
		INode copy = initial.copy();
		scoringFunction.score (copy);
		open.insert (copy); 

		// Use Hashtable to store states we have already visited.
		INodeSet closed = StateStorageFactory.create (closedStorage);
		while (!open.isEmpty()) {
		    // Remove node with smallest evaluation function and mark closed.
			INode best = open.remove();
			
			// Return if goal state reached.
			if (best.equals(goal)) {
				numOpen = open.size(); numClosed = closed.size();  /* STATS */
				return new Solution (initial, best);
			}
			closed.insert(best);
			
			// Compute successor moves and update OPEN/CLOSED lists.
			DepthTransition trans = (DepthTransition) best.storedData();
			int depth = 1;
			if (trans != null) { depth = trans.depth+1; }

			for (IMove move : best.validMoves()) {
				// Make move and score the new board state.
				INode successor = best.copy();
				move.execute (successor);
				numMoves++; /* STATS */

				if (closed.contains(successor) != null) {
					continue;
				}
				
			    // Record previous move for solution trace and compute
			    // evaluation function to see if we have improved
				successor.storedData (new DepthTransition (move, best, depth));
				scoringFunction.score (successor);

				// If not yet visited, or it has better score
				INode exist = open.contains(successor);
				if (exist == null || successor.score() < exist.score()) {
					
					// remove old one, if one had existed, and insert better one
					if (exist != null) {
						open.remove (exist);
					}
					open.insert (successor);
				}
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
