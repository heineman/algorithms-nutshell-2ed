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
public class ColumnToColumnMove implements IMove {

	/** nth column from which card comes. */
	public final int fromCol;
	
	/** nth column to which card goes. */
	public final int toCol;

	/** Bottom card of sequence to be removed. */
	int card;
	
	/** number in sequence. */
	int num = 1;
	
	/** temp stack. */
	short []cards = new short[13]; // max number that can be moved.
	
	public ColumnToColumnMove (int from, int to) {
		this.fromCol = from;
		this.toCol = to;
		this.num = 1;
	}
	
	/** Person calling this had better ensure descending/alternating color of num. */
	public ColumnToColumnMove (int from, int to, int num) {
		this.fromCol = from;
		this.toCol = to;
		
		this.num = num;
	}
	
	/**
	 * Execute the move on the given board state.
	 * 
	 * @param n   state on which to execute the move.
	 */
	public boolean execute(INode n) {
		if (!isValid(n)) {
			System.err.println("Requested invalid move!");
			return false;
		}

		FreeCellNode state = (FreeCellNode) n;
		
		int ct = num;
		int idx = 0;
		while (ct-- > 0) {
			cards[idx++] = state.cols[fromCol].remove(); 
		}
		
		while (idx-- > 0) {
			state.cols[toCol].add(cards[idx]);
		}

		if (state.cols[fromCol].num == 0 || state.cols[toCol].num == 1) { 
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
		
		Column from = state.cols[fromCol];
		if (from.num == 0) return false;
		
		// this will be bottom card that gets moved 
		card = from.cards[from.num-num];
		
		int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
		int rank = 1 + ((card-1)>>2);  // rank extracted this way.
		
		boolean isBlackCard = (suit == FreeCellNode.CLUBS || suit == FreeCellNode.SPADES);
		
		Column to = state.cols[toCol];
		if (num > 1) {
			// just not enough space!
			if (num > state.numberVacant()+1) {
				return false;
			}
			
			// the 'vacant' column is one we are moving into! Must deny.
			if ((num == state.numberVacant() + 1) && to.num == 0) {
				return false;
			}
		}
		
		if (to.num == 0) return true;  // always move to a free column
		int tr = to.rank();
		// only move if alternating colors and lower rank.
		return (isBlackCard != to.isBlack()) && (rank == (tr-1));
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.

	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(INode n) {
		FreeCellNode state = (FreeCellNode) n;
		
		int ct = num;
		int idx = 0;
		
		while (ct-- > 0) {
			cards[idx++] = state.cols[toCol].remove(); 
		}
		
		while (idx-- > 0) {
			state.cols[fromCol].add(cards[idx]);
		}
		
		return true;
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + num + " cards based at " + FreeCellNode.out(card) + " from column " + fromCol + " to column " + toCol;
	}
}
