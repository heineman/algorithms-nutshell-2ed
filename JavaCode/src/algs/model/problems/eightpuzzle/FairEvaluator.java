package algs.model.problems.eightpuzzle;

import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Fair evaluation, subset of GoodEvaluator from Nilsson, p. 66.
 * 
 * @author George Heineman
 * @version 2.0, 8/30/15
 * @since 1.0
 */
public class FairEvaluator implements IScore {
	/** Known goal state. */
	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});
	
	/** index values [r][c] for proper locations, based on goal above. */
	static int[][] diffs = new int[][] {
		{1, 1},      // who cares about the blank? But here for consistency
		{0, 0},      // location where 1 belongs [0][0]
		{0, 1},
		{0, 2},
		{1, 2}, 
		{2, 2},
		{2, 1}, 
		{2, 0},
		{1, 0}
	};

	/**
	 * Description of this measure.
	 */
	public String toString() {
		return "P(n) where P(n) is the sum of the Manhattan distances"    + "\n" +
			   "that each tile is from \"home.\"";
	}
	
	/**
	 * @see algs.model.searchtree.IScore#score(INode)
	 */
	public void score(INode state) {
		state.score(eval(state));
	}
	
	/**
	 * h(n) = P(n), where P(n) is the sum of the distances (via Manhattan
	 * metric) that each tile is from "home".
	 * <p>
	 * Compute f(n) = g(n) + h(n) where g(n) is the length of the path from the 
	 * start node n to the state.
	 * 
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state   game state to be evaluated
	 * @return        integer evaluation.
	 */
	public int eval(INode state) {
		EightPuzzleNode node = (EightPuzzleNode) state;
		
		// Each tile is between 0 and 4 moves away from its proper position.
		int Pn = 0;
		for (int r = 0; r <= EightPuzzleNode.MaxR; r++) {
			for (int c = 0; c <= EightPuzzleNode.MaxC; c++) {
				if (node.isEmpty(r,c)) { continue; }
				
				int digit = node.cell(r, c);
				Pn += Math.abs(diffs[digit][0] - r);
				Pn += Math.abs(diffs[digit][1] - c);
			}
		}

		// compute g(n)
		int gn = 0;
		DepthTransition t = (DepthTransition) state.storedData();
		if (t != null) { 
			gn = t.depth; 
		}
		
		return gn + Pn;
	}
	
	/**
	 * Return string representing the evaluation g(n) + P(n) where P(n) is 
	 * the sum of the distances (via Manhattan metric) that each tile is from "home". 
	 * 
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state   game state to be evaluated.
	 * @return        human readable string representing evaluation of state.
	 */
	public String evalString(INode state) {
		EightPuzzleNode node = (EightPuzzleNode) state;
		
		// Each tile is between 0 and 4 moves away from its proper position.
		int Pn = 0;
		for (int r = 0; r <= EightPuzzleNode.MaxR; r++) {
			for (int c = 0; c <= EightPuzzleNode.MaxC; c++) {
				if (node.isEmpty(r,c)) { continue; }
				
				int digit = node.cell(r, c);
				Pn += Math.abs(diffs[digit][0] - r);
				Pn += Math.abs(diffs[digit][1] - c);
			}
		}
		
		// compute g(n)
		int gn = 0;
		String eval = "";
		DepthTransition t = (DepthTransition) state.storedData();
		
		if (t != null) { 
			if (t.depth != 0) {  // protect against zero depth
				gn = t.depth;
				eval = eval + gn + "+" + Pn + "=" + (gn+Pn);
			}
		} else {
			eval = eval + Pn;
		}
		
		return eval;
	}
}
