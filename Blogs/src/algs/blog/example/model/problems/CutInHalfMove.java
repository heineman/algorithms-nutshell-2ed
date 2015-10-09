package algs.blog.example.model.problems;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * In pseudo code example, a player can cut both in half providing they
 * are both even.
 *  
 * @author George Heineman
 * @version 1.1, 3/4/11
 * @since 1.1
 */
public class CutInHalfMove implements IMove {

	/** Increment move selects the position to be incremented. */
	public CutInHalfMove () {
	
	}

	/** Execute a move. */
	public boolean execute(INode state) {
		SmallPuzzle tp = (SmallPuzzle) state;
		tp.s[0] /= 2;
		tp.s[1] /= 2;
		return true;
	}

	/** Moves are always valid. */
	public boolean isValid(INode state) {
		SmallPuzzle tp = (SmallPuzzle) state;
		return (tp.s[0] % 2 == 0) && (tp.s[1] % 2 == 0);
	}

	/** Undo a move. */
	public boolean undo(INode state) {
		SmallPuzzle tp = (SmallPuzzle) state;
		tp.s[0] *= 2;
		tp.s[1] *= 2;
		return true;
	}

}
