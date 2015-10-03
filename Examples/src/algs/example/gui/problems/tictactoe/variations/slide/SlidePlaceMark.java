package algs.example.gui.problems.tictactoe.variations.slide;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Extends PlaceMark simply by updating the turn number.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlidePlaceMark extends PlaceMark {

    /**
     * Default constructor given (col,row) and Player.
     * 
     * @param col
     * @param row
     * @param player
     */
    public SlidePlaceMark(int col, int row, Player player) {
        super(col, row, player);
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
	public boolean execute (IGameState gameState) {
	    boolean rc = super.execute(gameState);
	    if (!rc) return false;
	    
	    TicTacToeState state = (TicTacToeState) gameState;
	    SlideLogic logic = (SlideLogic) state.logic();
	    logic.doneTurn();	   
	    return true;
	}
	
	/**
	 * Undoes the given move and returns true, or returns false if unable to undo.
	 * 
	 * @see Move#undo(IGameState)
	 */
	public boolean undo(IGameState gameState) {
		boolean rc = super.undo(gameState);
		if (!rc) return false;
		    
		TicTacToeState state = (TicTacToeState) gameState;
		SlideLogic logic = (SlideLogic) state.logic();
		logic.undoTurn();	   
		return true;
	}
	
	/**
	 * Return object in readable form.
	 */
	public String toString () {
		return "[SlidePlaceMark @ (" + col + "," + row + ")]";
	}	

}
