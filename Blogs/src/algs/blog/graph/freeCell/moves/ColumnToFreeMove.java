package algs.blog.graph.freeCell.moves;

import algs.blog.graph.freeCell.Column;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Move a card from a column to a free cell.
 * 
 * @author George Heineman
 */
public class ColumnToFreeMove implements IMove {

	/** nth column from which card comes. */
	public final int nth;
	
	/** card to be removed. */
	short card;
	
	public ColumnToFreeMove (int nth) {
		this.nth = nth;
	}
	
	/**
	 * Execute the move on the given board state.
	 * 
	 * @param n   state on which to execute the move.
	 */
	public boolean execute(INode n) {
		if (!isValid(n)) {
			return false;
		}
		
		FreeCellNode state = (FreeCellNode) n;

		//state.detach(nth);
		Column col = state.cols[nth];
		
		col.remove();
		if (col.num == 0) { 
			state.sortMap(); 
		}
		state.insertFree(card);
		
		return true;
	}

	/**
	 * Determine if move is valid for the given state.
	 * 
	 * @param n
	 */
	public boolean isValid(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		
		Column col = state.cols[nth];
		if (col.num == 0) return false;
		
		card = (short) col.cards[col.num-1];
		return state.availableFree();
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.

	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		
		state.removeFree(card);
		
		Column col = state.cols[nth];
		col.add(card);
		return true;
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + FreeCellNode.out(card) + " from column " + nth + " to freecell.";
	}
}
