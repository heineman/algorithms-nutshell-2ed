package algs.blog.graph.freeCell.solver;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.freeCell.moves.ColumnToColumnMove;
import algs.blog.graph.freeCell.moves.ColumnToFoundationMove;
import algs.blog.graph.freeCell.moves.ColumnToFreeMove;
import algs.blog.graph.freeCell.moves.FreeToColumnMove;
import algs.blog.graph.freeCell.moves.FreeToFoundationMove;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;

public class StandardSolver implements ISolver {

	/**
	 * Given the game state, return the set of valid moves.
	 * <p>
	 * 
	 * Some boards can be solved WITHOUT any free cell. Useful as test cases.
	 */
	public DoubleLinkedList<IMove> validMoves(FreeCellNode node) {
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>();
		IMove move = null;
		
		// These moves should be ordered. 
		//    1. FreeCell to foundation
		for (int i = 3; i >= 0; i--) {
			if (node.freeEncoding[i] > 0) {
				move = new FreeToFoundationMove(node.freeEncoding[i]);
				if (move.isValid(node)) { 
					list.insert(move); 
				}
			}
		}
		
		// 2. cols to foundation
		for (int i = 0; i < 8; i++) {
			if (node.cols[i].num == 0) continue;
			
			move = new ColumnToFoundationMove(i);
			if (move.isValid(node)) { 
				list.insert(move); 
			}
		}
		
		// 1a. free to columns.
		for (short c = 0; c < 8; c++) {
			for (short i = 0; i < 4; i++) {
				move = new FreeToColumnMove(c, node.freeEncoding[i]);
				if (move.isValid(node)) { 
					list.insert(move);
				}
			}
		}
		
		// 3. cols to cols. 
		// Order by the cards that they reveal  (Blanks best, then by lowest card)
		for (short c = 0; c < 8; c++) {
			if (node.cols[c].num == 0) continue;
			
			// start at bottom and work way up.
			int card = node.cols[c].cards[node.cols[c].num-1];
			
			int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
			int rank = 1 + ((card-1)>>2);  // rank extracted this way.
			
			boolean isBlackCard = (suit == FreeCellNode.CLUBS || suit == FreeCellNode.SPADES);
			
			for (int nc = 1; nc <= node.cols[c].num; nc++) {
				if (nc > 1) {
					int nextCard = node.cols[c].cards[node.cols[c].num-nc];
					int nextSuit = ((nextCard-1)%4);       // subtract 1 since '0' is invalid card.
					int nextRank = 1 + ((nextCard-1)>>2);  // rank extracted this way.
					boolean isNextBlackCard = (nextSuit == FreeCellNode.CLUBS || nextSuit == FreeCellNode.SPADES);
					
					// not alternating.
					if (isNextBlackCard == isBlackCard) { break; }
					if (nextRank != rank + 1) { break; }
					isBlackCard = isNextBlackCard;  // flip to this one
					rank = nextRank;
				}
				
				boolean alreadyMovedToBlank = false;
				for (int t = 0; t < 8; t++) {
					if (c == t) continue; // no self moves.
					
					// skip moves which are merely moving an entire
					// column to another empty column.
					if (node.cols[c].num == nc && node.cols[t].num == 0) {
						continue;
					}
					
					move = new ColumnToColumnMove(c, t, nc);
					if (move.isValid(node)) { 
						if (node.cols[t].num == 0 && alreadyMovedToBlank) {
							// skip multiple moves-to-blank if two are free...
						} else {
							list.insert(move);
						}
						if (node.cols[t].num == 0) { alreadyMovedToBlank = true; }
					}
				}
			}
		}
		
		// 4. cols to free
		for (int i = 0; i < 8; i++) {
			if (node.cols[i].num == 0) continue;
			
			move = new ColumnToFreeMove(i);
			if (move.isValid(node)) { 
				list.insert(move); 
			}
		}
		
		return list;
	}
}
