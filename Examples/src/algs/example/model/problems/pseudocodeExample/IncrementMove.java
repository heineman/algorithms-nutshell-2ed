package algs.example.model.problems.pseudocodeExample;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * In pseudo code example, a player can increment either of two positions.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IncrementMove implements IMove {

	/** Position to increment. */
	int pos;
	
	/** Increment move selects the position to be incremented. */
	public IncrementMove (int pos) {
		if (pos < 0 || pos > 1) {
			throw new IllegalArgumentException ("Only two spaces to be incremented.");
		}
		
		this.pos = pos;
	}

	/** Execute a move. */
	public boolean execute(INode state) {
		TinyPuzzle tp = (TinyPuzzle) state;
		tp.s[pos]++;
		return true;
	}

	/** Moves are always valid. */
	public boolean isValid(INode state) {
		return true;
	}

	/** Undo a move. */
	public boolean undo(INode state) {
		TinyPuzzle tp = (TinyPuzzle) state;
		tp.s[pos]--;
		return true;
	}
}
