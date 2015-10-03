package algs.example.chapter7.fifteenSolitaire;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Evaluate state by considering just the number of available moves. 
 *
 * Try to maximize number of available moves at all times.
 * 
 * TRIAL and ERROR GIVES on 21-solitaire (74 open, 5057 closed): Note that this 
 * eval function returns a negative number at times, thus it is inadmissible; yet 
 * it still allows a search to find the solution. Find code as eval0()
 * 
  		DoubleLinkedList<IMove> mv = node.validMoves();
		int hn = ( mv.size()) * 2;
		
		// isolated pegs count as penalty points.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		if (ni == 1) {
			hn += (int)Math.sqrt(nf);
		} else {
			hn += nf-ni*ni;
		}
 *
 * But we can do better. Earlier intuition leads me astray. We actually want
 * to have lots of isolated pegs, since these will be easy to move. Too many
 * unisolated pegs turns out to be bad. This heuristic is good since it is 
 * always > 0. Find code as eval1(). This leads to (68 open, 4228 closed)
 
 	    // compute h^(n) 		
		// isolated pegs are a *GOOD* thing. Less work to do.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		int hn = nf - ni;
 
 * How to improve on this one? Perhaps seriously pegs that have no 
 * peg within 2 units in any of the six cardinal directions (assuming such
 * a move is valid. In short, when are a peg's neighbor's neighbors empty?
 * This heuristic eval2() leads to (65 open, 1555 closed). 
    
   	    // compute h^(n) 		
        // isolated pegs are a *GOOD* thing. Less work to do.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		int hn = nf - ni;
		
		// but tack on penalty for doubly-isolated ones.
		hn += numDoubleIsolated(node);
 
 * Unfortunately, when run on a larger board, the eval2() heuristic fails to 
 * converge on a solution (while the program didn't run out of memory, it 
 * ran long enough to suggest that no solution was forthcoming.
 *
 * Another possibility is to ascribe weights for each hole, hoping to keep
 * as many pegs in play where the ultimate final peg is to be placed. Now
 * to keep the heuristic simple, sum the weights of the three holes and use
 * this number to order the moves being made (see {@link OrderedMain} for 
 * the implementation which pursues this option).
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class JumpingEvaluator implements IScore {
	
	int [][]neighbors;
	int k;  // triangle problem size.
	
	// pass in k which describes size of triangle. 
	public JumpingEvaluator(int k) {
		int [][]moves = JumpingSolitaireState.compMoves;
		// one more than we need (only need 6) so we can have easier code below
		neighbors = new int[k][7];  
		
		for (int i = 0; i < neighbors.length; i++) {
			for (int j = 0; j < neighbors[0].length; j++) {
				neighbors[i][j] = -1;
			}
		}
		
		// build up this neighbor list
		for (int i = 0; i < moves.length; i++) {
			int from = moves[i][0];
			int over = moves[i][1];
			int dest = moves[i][2];
			
			// one direction
			for (int j = 0; j < neighbors[0].length; j++) {
				if (neighbors[from][j] == -1) {
					neighbors[from][j] = over;
					break;
				}
			}
			
			// other direction
			for (int j = 0; j < neighbors[0].length; j++) {
				if (neighbors[dest][j] == -1) {
					neighbors[dest][j] = over;
					break;
				}
			}
			
		}
	}
	
	/**
	 * @see algs.model.searchtree.IScore#score(INode)
	 */
	public void score(INode state) {
		state.score(eval(state));
	}

	/**
	 * h^(n) takes into account number of isolated pegs.
	 * <p>
	 * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form the start node
	 * n to the state.
	 * <p>
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state
	 */
	public int eval(INode state) {
		return eval2(state);
	}
	
	/**
	 * h^(n) takes into account number of isolated pegs.
	 * <p>
	 * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form the start node
	 * n to the state.
	 * <p>
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state
	 */
	public int eval2(INode state) {
		JumpingSolitaireState node = (JumpingSolitaireState) state;
		
		// compute h^(n) 		
		// isolated pegs are a *GOOD* thing. Less work to do.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		int hn = nf - ni;
		
		// but tack on penalty for doubly-isolated ones.
		hn += numDoubleIsolated(node);
		
		// compute g^(n)
		int gn = 0;
		DepthTransition t = (DepthTransition) state.storedData();
		if (t != null) { 
			gn = t.depth; 
		}
		
		return gn + hn;
	}

	
	/**
	 * h^(n) takes into account number of isolated pegs.
	 * <p>
	 * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form the start node
	 * n to the state.
	 * <p>
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state
	 */
	public int eval1(INode state) {
		JumpingSolitaireState node = (JumpingSolitaireState) state;
		
		// compute h^(n) 		
		// isolated pegs are a *GOOD* thing. Less work to do.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		int hn = nf - ni;
		
		// compute g^(n)
		int gn = 0;
		DepthTransition t = (DepthTransition) state.storedData();
		if (t != null) { 
			gn = t.depth; 
		}
		
		return gn + hn;
	}

	/**
	 * h^(n) takes into account number of isolated pegs.
	 * <p>
	 * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form the start node
	 * n to the state.
	 * <p>
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state
	 */
	public int eval0(INode state) {
		JumpingSolitaireState node = (JumpingSolitaireState) state;
		
  		DoubleLinkedList<IMove> mv = node.validMoves();
		int hn = ( mv.size()) * 2;
		
		// isolated pegs count as penalty points.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		if (ni == 1) {
			hn += (int)Math.sqrt(nf);
		} else {
			hn += nf-ni*ni;
		}
		
		// compute g^(n)
		int gn = 0;
		DepthTransition t = (DepthTransition) state.storedData();
		if (t != null) { 
			gn = t.depth; 
		}
		
		return gn + hn;
	}


	// count number of pegs whose neighbors' neighbors are empty.
	private int numDoubleIsolated(JumpingSolitaireState node) {
		int num = 0;
		
		for (int i = 0; i < neighbors.length; i++) {
			if (isIsolated(node, i)) {
				int last = neighbors[i].length;

				// if all of these neighbors are isolated, we can increment.
				for (int j = 0; j < last; j++) {
					if (neighbors[i][j] == -1) {
						num++;
						break;
					}
					
					if (node.filled[neighbors[i][j]]) {
						break;
					}
				}
			}			
			// now must check all neighbors' neighbors
		}
		
		return num;
	}

	
	private boolean isIsolated (JumpingSolitaireState node, int peg) {
		// are all neighbors empty?
		int last = neighbors[peg].length;
		for (int j = 0; j < last; j++) {
			if (neighbors[peg][j] == -1) {
				return true;
			}
			
			// not isolated if a neighbor is filled in
			if (node.filled[neighbors[peg][j]]) {
				return false;
			}
		}
		
		return true;  // must be
	}
	
	// count number of pegs that are isolated (have no neighbors). These
	// require an additional move before it can move and that might be 
	// expensive.
	private int numIsolated(JumpingSolitaireState node) {
		int num = 0;
		
		for (int i = 0; i < neighbors.length; i++) {
			if (isIsolated(node, i)) {
				num++;
			}
		}
		
		return num;
	}
}
