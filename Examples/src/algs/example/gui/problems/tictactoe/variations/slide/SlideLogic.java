package algs.example.gui.problems.tictactoe.variations.slide;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * SlideTicTacToe -- On turns 1-8, Players X and O can only place their
 * markers in an empty cell. If by the eighth turn, no-one has won, the 
 * game shifts into the Sliding phase. In this phase, X starts by moving 
 * one of his 'X' cells into the empty cell, but only if it is adjacent 
 * to the empty cell either vertically or horizontally. If X can't move 
 * then O wins. If X moves to create three-in-a-row, then X wins; 
 * otherwise, it is O's turn to slide one of his markers into the empty 
 * cell, if one is neighbor to the empty cell either horizontally or
 * vertically. Sliding proceeds until one player can't move or three in
 * a row occurs.
 * <p>
 * NOTE: This is the game I want to code. However, if you make it
 * so a player DOES NOT HAVE A VALID MOVE, then we need a way to 
 * declare winner. Since I can't think immediately about how to do
 * this, I am just going to change the rules to state that the
 * lack of a move means you can just PLACE a marker in the empty
 * spot.
 * <p>
 * 
 * If no winner is determined after 16 additional slide moves, then 
 * the game is drawn.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlideLogic extends StraightLogic {

	/** Two phases: PLACE and SLIDE. */
	public static final int PLACE_PHASE = 1;
	public static final int SLIDE_PHASE = 2;
	
	/** State shared among all SlideLogic objects that are copied. */
	SlideState state;
    
    /**
     * Constructor sets initial turn to 1. 
     */
    public SlideLogic () {
        state = new SlideState();
    }

    public String rules() {
    	 return "Rules:\n======\nOn turns 1-8, Players X and O can only place their " +
    	 "markers in an empty cell. If by the eighth turn, no-one has won, the " +
    	 "game shifts into the Sliding phase. In this phase, X starts by moving " + 
    	 "one of his 'X' cells into the empty cell, but only if it is adjacent " + 
    	 "to the empty cell either vertically or horizontally. If X can't move " + 
    	 "then O wins. If X moves to create three-in-a-row, then X wins; " + 
    	 "otherwise, it is O's turn to slide one of his markers into the empty " + 
    	 "cell, if one is neighbor to the empty cell either horizontally or " +
    	 "vertically. Sliding proceeds until one player can't move or three in " +
    	 "a row occurs. ";
    }
    
    public int getPhase() {
    	return state.getPhase();
    }
    
    /**
	 * Each logic must implement 'clone' to properly be used when 
	 * evaluating future moves.
	 * 
	 * @return   a clone of the SlideLogic object.
	 */
	public SlideLogic copy () {
	    SlideLogic sl = new SlideLogic();
	    sl.state = state;
	    
	    // now return.
	    return sl; 
	}   
    
    /**
     * Increments turn, and updated phase accordingly.
     */
    public void doneTurn () {
    	state.advanceTurn();
    }
    
    /**
     * Increments turn, and updated phase accordingly.
     */
    public void undoTurn () {
       state.reverseTurn();
    }
    
    /**
	 * Method to determine the type of move that the user has 
	 * selected.
	 * 
	 * Subclasses should override this method so they will be
	 * able to properly construct the appropriate move given
	 * the context.
	 * 
	 * @param gameState     current board state
	 * @param col           selected column
 	 * @param row           selected row
	 * @param player        Player making the move
	 * @return              Appropriate Move to make, or null if specific (col,row) is an invalid move.
	 */
	public Move interpretMove(IGameState gameState, int col, int row, Player player) {
		TicTacToeState tstate = (TicTacToeState) gameState;
	    TicTacToeBoard board = tstate.board();
	  
		if (col < 0) return null;
		if (col >= board.numColumns()) return null;
		if (row < 0) return null;
		if (row >= board.numRows()) return null;
		
		// during PLACE phase, we are like straight TicTacToe
		if (state.getPhase() == PLACE_PHASE) {
			if (board.isClear(col, row)) {
				return new SlidePlaceMark (col, row, player);
			}
			
			// invalid move.
			return null;
		}
		
		// in SLIDE_PHASE, we identify a cell, and if it is 
		// adjacent to the empty square (ONLY HORIZONTALLY or 
		// VERTICALLY) then the move is allowed, otherwise we
		// must return null.
		Cell empty = getEmptyCell (board);
		if (empty == null) return null;  // no move possible
		
		SlideMark sm = new SlideMark (col, row, empty.col, empty.row, player);
		if (sm.isValid(gameState)) {
			return sm;
		}
		
		// if we get here, then we must ensure that we have no valid move. If a valid move
		// is discovered, then we return null.
		for (int c = 0; c < board.numColumns(); c++) {
			for (int r = 0; r < board.numRows(); r++) {
				sm = new SlideMark (c, r, empty.col, empty.row, player);
				if (sm.isValid(gameState)) {
					return null;
				}
			}
		}
		
		// if we get here, then we have validated our only alternative.
		if (board.isClear(col,row)) {
			// Since we have none that are adjacent, we revert to 
			// Standard PlaceMark
			return new PlaceMark (empty.col, empty.row, player);
		}
		
		// SHOULD never get here. Deal by responding with no valid move.
		return null;
	}
	
	/**
	 * Most TicTacToe variations are guaranteed to have a fixed number of
	 * moves before the game is either won, lost, or drawn. However,
	 * Slide TicTacToe can go on for some time.
	 * 
	 * We set a reasonable default.
	 * 
	 * @return the maximum number of allowed moves at which time a draw must
	 *         be determined if the game has not been won.
	 */
	public int maxNumberMoves() {
		return 16;		
	}	

	/**
	 * Return the location of the empty cell in the board.
	 * 
	 * If more than one cell is empty, then null is returned.
	 * 
	 * @param board    current board state.
	 * @return         <code>Cell</code> object pointing to only EMPTY cell, or null if multiple empty cells exist.
	 */
	private Cell getEmptyCell(TicTacToeBoard board) {
		Cell empty = null;
		for (int c = 0; c < board.numColumns(); c++) {
			for (int r = 0; r < board.numRows(); r++) {
				// if we find empty cell...
				if (board.isClear(c, r)) {
					// and we already have a candidate, return in error.
					if (empty != null) {
						return null;
					} else {
						// simply set.
						empty = new Cell (c,r);
					}
				}
			}
		}
		
		return empty;
	}    
	
	
	/**
	 * Return name for logic.
	 */
	public String toString () {
	    return "SlideLogic";
	}
}
