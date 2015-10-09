package algs.blog.example.model.problems;

import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Puzzle evaluator for the TinyPuzzle example used in pseudocode fact sheets. 
 *  
 * @author George Heineman
 * @version 1.0, 3/4/11
 * @since 1.0
 */
public class PuzzleEvaluator1 implements IScore {

	/** Know target. */
	int target[];
	
	/** Evaluator must know the destination target to be able to determine its score. */
	public PuzzleEvaluator1 (int target[]) {
		this.target = target;
	}
	
	/** Evaluate how far from the solution state is. */
	public int eval(INode state) {
		SmallPuzzle tp = (SmallPuzzle) state;
		
		if (tp.s[0] > target[0]) return Integer.MAX_VALUE;
		if (tp.s[1] > target[1]) return Integer.MAX_VALUE;
		
		return Math.abs(target[0] - tp.s[0]) + Math.abs(target[1] - tp.s[1]);
	}

	/** Set the score. */
	public void score(INode state) {
		state.score(eval(state));
	}

}
