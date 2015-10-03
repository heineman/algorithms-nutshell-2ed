package algs.example.gui.problems.tictactoe.variations.annihilate;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Contains AnnihilateLogic.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AnnihilateLogic extends Logic {
    /** Singleton instance of clones. Since all AnnihilateLogic objects are identical. */
    private AnnihilateLogic _copy =null;
    
	/**
	 * Each logic must implement 'clone' to properly be used when 
	 * evaluating future moves.
	 * 
	 * Note that we can avoid clone if the Logic is readonly; simply
	 * return singleton instance .
	 * 
	 * @return   a Clone of the AnnihilateLogic object.
	 */
	public AnnihilateLogic copy () {
	    if (_copy == null ){
	    	_copy = new AnnihilateLogic();
	    }
	    
	    // now return.
	    return _copy; 
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
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();

		if (board.isClear(col, row)) {
			return new PlaceMark (col, row, player);
		}
		
		// Only other alternative is AnnihilateMove. Try it
		AnnihilateMove move = new AnnihilateMove (col, row, player);
		if (move.isValid(board)) {
			return move;
		}
		
		// invalid move given board state.
		return null;
	}
	
	/**
	 * Most TicTacToe variations are guaranteed to have a fixed number of
	 * moves before the game is either won, lost, or drawn. However,
	 * Annihilate TicTacToe can go on for some time.
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
	 * Return name for logic.
	 */
	public String toString () {
	    return "AnnihilateLogic";
	}

	@Override
	public String rules() {
		return "Rules:\n======\nPlay as ordinary Tic Tac Toe except you can " +
		 "Remove an opponent's marker if two neighboring cells (in any direction) " + 
		 "contain your marker.";
	}

}
