package algs.model.problems.tictactoe.model;

import algs.model.gametree.IGameState;

/**
 * Logic of the normal TicTacToe
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StraightLogic extends Logic {

    /** Singleton instance of copies. Since all StraightLogic objects are identical. */
    private StraightLogic _copy =null;
    
    /** rules of Straight Tic Tac Toe Apply. */
    public String rules() {
    	return "Rules:\n======\nThe regular rules of Tic Tac Toe apply.";
    }
    
    /**
	 * Each logic must implement 'copy' to properly be used when 
	 * evaluating future moves.
	 * 
	 * Note that we can avoid clone if the Logic is readonly; simply
	 * return singleton instance.
	 * 
	 * @return   a copy of the StraightLogic object.
	 */
	public StraightLogic copy () {
	    if (_copy == null ){
	    	_copy = new StraightLogic();
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
	 * Note: First iteration of this method had just 'char mark' as 
	 * argument; but then we faced the Neighbor game, and it became
	 * clear that we need to know the player making the move.
	 * 
	 * @param gameState     current board state
	 * @param col           selected column
 	 * @param row           selected row
	 * @param player        player making the move.
	 * @return              null if move is invalid; otherwise a valid Move object.
	 */
	public Move interpretMove(IGameState gameState, int col, int row, Player player) {
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();
		
		if (col < 0) return null;
		if (col >= board.numColumns()) return null;
		if (row < 0) return null;
		if (row >= board.numRows()) return null;
		
		if (board.isClear(col, row)) {
			return new PlaceMark (col, row, player);
		}
		
		// nothing to say
		return null;
	}	
	
	/**
	 * Return name for logic.
	 */
	public String toString () {
	    return "StraightLogic Rules";
	}

}
