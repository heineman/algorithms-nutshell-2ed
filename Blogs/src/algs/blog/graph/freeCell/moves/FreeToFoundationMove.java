package algs.blog.graph.freeCell.moves;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Move a card from a free cell to a foundation.
 * 
 * @author George Heineman
 */
public class FreeToFoundationMove implements IMove {

	/** card to be removed. */
	short card;
	
	/** suit of this card. */
	short suit;
	
	/** rank of this card. */
	short rank;
	
	public FreeToFoundationMove (short card) {
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
		state.insertFoundation(card);
		
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
		
		suit = (short)((card-1)%4);     // subtract 1 since '0' is invalid card.
		rank = (short)(1 + ((card-1)>>2));  // rank extracted this way.
		
		return (state.foundationEncoding[suit] + 1 == rank);
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.

	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		state.removeFoundation(suit);
		state.insertFree(card);
		return true;
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + FreeCellNode.out(card) + " from free cell to foundation.";
	}
}
