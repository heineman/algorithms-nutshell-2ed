package algs.example.chapter7.fifteenSolitaire.fixed;

import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Evaluate state by considering just the number of available moves. 
 *
 * Try to maximize number of available moves at all times.
 * 
 * TRIAL and ERROR GIVES (21 open, 112 closed): Note that this eval function
 * returns a negative number, thus it is inadmissible; yet it still allows a
 * search to find the solution.
 * 
 * 		DoubleLinkedList<IMove> mv = node.validMoves();
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
 * always > 0
 
 	    // compute h^(n) 		
		// isolated pegs are a *GOOD* thing. Less work to do.
		int nf = 0;
		for (int i = 0; i < node.filled.length; i++) {
			if (node.filled[i]) nf++;
		}
		int ni = numIsolated(node);
		int hn = nf - ni;
 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class JumpingEvaluator implements IScore {
	
	int [][]neighbors;
	int k;  // triangle problem size.
	
	// pass in k which describes size of triangle. 
	public JumpingEvaluator(int k) {
		int [][]moves = JumpingSolitaireState.moves;
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
			
			for (int j = 0; j < neighbors[0].length; j++) {
				if (neighbors[from][j] == -1) {
					neighbors[from][j] = over;
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

	// count number of pegs that are isolated (have no neighbors). These
	// require an additional move before it can move and that might be 
	// expensive.
	private int numIsolated(JumpingSolitaireState node) {
		int num = 0;
		
		for (int i = 0; i < neighbors.length; i++) {
			// are all neighbors empty?
			int last = neighbors[i].length;
			for (int j = 0; j < last; j++) {
				if (neighbors[i][j] == -1) {
					// must be empty
					num++;
					break;
				}
				
				// not isolated if a neighbor is filled in
				if (node.filled[neighbors[i][j]]) {
					break;
				}
			}
		}
		
		return num;
	}
}
