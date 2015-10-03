package algs.example.gui.problems.tictactoe.variations.slide;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Move that is placed based upon player turn number.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlideMark extends PlaceMark {
	/** The empty cell. */
	Cell emptyCell;
	
    /**
     * Default constructor given (col,row) and Player.
     * @param col          col of cell containing mark to move.
     * @param row          row of cell containing mark to move.
     * @param emptyCol     col of empty cell.
     * @param emptyRow     row of empty cell.
     * @param player       Player making the move.
     */
    public SlideMark(int col, int row, int emptyCol, int emptyRow, Player player) {
        super(col, row, player);
        
        emptyCell = new Cell (emptyCol, emptyRow);
    }
    
    /**
	 * Determines if move is valid.
	 * 
	 * SlideMark is valid if board has marker for player and it 
	 * is adjacent to empty square, and we are in SLIDE PHASE.
	 * @param    gameState    Current board state.
	 * @return   <code>true</code> if move is allowed; <code>false</code> otherwise.
	 */
	public boolean isValid(IGameState gameState) {
		TicTacToeState state = (TicTacToeState) gameState;
	    TicTacToeBoard board = state.board();
	    SlideLogic logic = (SlideLogic) state.logic();
	    
	    // must be in Slide phase, otherwise fails.
	    if (logic.getPhase() != SlideLogic.SLIDE_PHASE) {
	    	return false;
	    }
	    	
		if (col < 0) return false;
		if (col >= board.numColumns()) return false;
		if (row < 0) return false;
		if (row >= board.numRows()) return false;
		
		// not our marker! leave in error.
		if (board.get(col, row) != player.getMark()) {
			return false;
		}
		
		// Move is invalid if we aren't adjacent to empty cell.
		Cell here = new Cell (col, row);
		if (!here.isAdjacent (emptyCell)) {
			return false;
		}
		
		// Looks good!
		return true;
	}
	
	/**
	 * Makes the move on the given TicTacToe Board.
	 * 
	 *      REQUIRES: board != null
	 *      MODIFIES: board
	 *      EFFECTS:  board state is updated as appropriate for the given move.
	 * 
	 * @return   true if the move was valid given the board configuration.
	 */
	public boolean execute(IGameState gameState) {
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();
	    board.set (emptyCell.col, emptyCell.row, board.get (col, row));
	    board.clear (col, row);
	    return true;
	}
	
	/**
	 * Return object in readable form.
	 */
	public String toString () {
		return "[RestrictedMove @ (" + col + "," + row + ")]";
	}	

}
