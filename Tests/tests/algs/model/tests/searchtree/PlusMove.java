package algs.model.tests.searchtree;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

class PlusMove implements IMove {

	/** Increment move selects the position to be incremented. */
	public PlusMove () {

	}

	/** Execute a move. */
	public boolean execute(INode state) {
		OnePuzzle op = (OnePuzzle) state;
		op.s++;
		return true;
	}

	/** Moves are valid as long as state < MAX_VALUE */
	public boolean isValid(INode state) {
		OnePuzzle op = (OnePuzzle) state;
		return op.s <= OnePuzzle.MAX_VALUE;
	}

	/** Undo a move. */
	public boolean undo(INode state) {
		OnePuzzle op = (OnePuzzle) state;
		op.s--;
		return true;
	}
}