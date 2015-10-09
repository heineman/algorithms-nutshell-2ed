package algs.blog.improving.freeCell;

import java.util.ArrayList;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Base class automatically follows up with moves that
 * are guaranteed to make forward progress without any
 * negative impact (i.e., these moves can always be made).
 * <p>
 * Only auto-moves considered are to the foundation (i.e., FreeToFoundation
 * or ColumnToFoundation).
 * <p>
 * 
 * A card can be auto-moved to the foundation when all lower cards of opposite
 * Color are already placed in the foundation. For example, assume the 5 of 
 * spades appears in a free cell. It can be moved to the foundation if (a) the
 * 4 spades is present there; and (b) the 4 of diamonds and hearts are in foundation.
 * The reason? it doesn't need to be used to move these cards around.
 * 
 * @author George Heineman
 *
 */
public abstract class AutoMove implements IMove {

	ArrayList<IMove> autoMoves = new ArrayList<IMove>();
	
	/** Determine the number of automoves made. */
	protected int numAutoMoves () {
		return autoMoves.size();
	}
	
	/** 
	 * See if there are any possible automoves.
	 */
	public boolean execute(INode state) {
		autoMoves.clear();
		boolean found = false;
		
		// must iterate over ALL until none more to be found!
		do {
			found = false;
			
			// check all free cells
			FreeCellNode fcn = (FreeCellNode) state;
			for (int i = 0; i < fcn.freeEncoding.length; i++) {
				short card = fcn.freeEncoding[i];
				int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
				int rank = 1 + ((card-1)>>2);  // rank extracted this way.
				boolean isBlack = (suit == FreeCellNode.CLUBS || suit == FreeCellNode.SPADES);
				
				boolean canMove = false;
				
				if (isBlack) {
					canMove = (fcn.foundationEncoding[FreeCellNode.DIAMONDS] >= rank-1) &&
					          (fcn.foundationEncoding[FreeCellNode.HEARTS] >= rank-1);
				} else {
					canMove = (fcn.foundationEncoding[FreeCellNode.CLUBS] >= rank-1) &&
			                  (fcn.foundationEncoding[FreeCellNode.SPADES] >= rank-1);
				}
				
				if (canMove) {
					FreeToFoundationMove fm = new FreeToFoundationMove(card);
					if (fm.isValid(state)) {
						fm.execute(state);
						autoMoves.add(fm);
						found = true;
					}
				}
			}
			
			// now check bottom of all columns.
			// check all free cells
			for (int c = 0; c < fcn.cols.length; c++) {
				if (fcn.cols[c].num == 0) { continue; }
				short bottomCard = fcn.cols[c].cards[fcn.cols[c].num-1];
				if (bottomCard == 0) { continue; }
				
				int suit = ((bottomCard-1)%4);       // subtract 1 since '0' is invalid card.
				int rank = 1 + ((bottomCard-1)>>2);  // rank extracted this way.
				boolean isBlack = (suit == FreeCellNode.CLUBS || suit == FreeCellNode.SPADES);
				
				boolean canMove = false;
				
				if (isBlack) {
					canMove = (rank <= 2) || 
					            ((fcn.foundationEncoding[FreeCellNode.DIAMONDS] >= rank-1) &&
					             (fcn.foundationEncoding[FreeCellNode.HEARTS] >= rank-1));
				} else {
					canMove = (rank <= 2) || 
					            ((fcn.foundationEncoding[FreeCellNode.CLUBS] >= rank-1) &&
			                     (fcn.foundationEncoding[FreeCellNode.SPADES] >= rank-1));
				}
				
				if (canMove) {
					ColumnToFoundationMove cm = new ColumnToFoundationMove(c);
					if (cm.isValid(state)) {
						cm.execute(state);
						autoMoves.add(cm);
						found = true;
					}
				}
			}
		} while (found);
		
		// done
		return true;
	}

	

	/**
	 * Undo moves in reverse order.
	 */
	public boolean undo(INode state) {
		for (int i = autoMoves.size()-1; i >= 0; i--) {
			IMove move = autoMoves.get(i);
			move.undo(state);
		}
		
		autoMoves.clear();
		return true;
	}

}
