package algs.blog.graph.freeCell.moves;

import algs.blog.graph.freeCell.Column;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Move a card from a free cell to a column.
 * 
 * @author George Heineman
 */
public class FreeToColumnMove implements IMove {

	/** nth column to which card goes. */
	public final short nth;
	
	/** card to be removed. */
	short card;
	
	public FreeToColumnMove (short nth, short card) {
		this.nth = nth;
		this.card = card;
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

		state.removeFree(card);
		
		//state.detach(nth);
		Column col = state.cols[nth];
		col.add(card);
		if (col.num == 1) {
			state.sortMap();
		}
		
		return true;
	}

	/**
	 * Determine if move is valid for the given state.
	 * 
	 * @param n
	 */
	public boolean isValid(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		if (card == 0) { return false; }
		
		Column col = state.cols[nth];
		if (col.num == 0) return true;  // can always place on empty column
		
		int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
		int rank = 1 + ((card-1)>>2);  // rank extracted this way.
		
		boolean isBlackCard = (suit == FreeCellNode.CLUBS || suit == FreeCellNode.SPADES);
		
		int destCard = col.cards[col.num-1];
		int destRank = 1 + ((destCard-1)>>2);  // rank extracted this way.
		
		
		return (col.isBlack() != isBlackCard) && (destRank - 1 == rank);
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.

	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		
		Column col = state.cols[nth];
		col.remove();
		
		state.insertFree(card);
		return true;
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + FreeCellNode.out(card) + " from freecell to " + nth + " column.";
	}
}
