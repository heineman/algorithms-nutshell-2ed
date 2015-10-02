package algs.model.problems.tictactoe.model;

import algs.model.gametree.IGameState;
import algs.model.gametree.IPlayer;
import algs.model.gametree.IGameScore;
import algs.model.gametree.MoveEvaluation;

/**
 * Evaluation of the board state.
 * 
 * This evaluation is not that useful, but it can be used as a kind of
 * strawMan when compared with a real evaluation, such as {@link BoardEvaluation}.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DefaultEvaluation implements IGameScore {

	/**
	 * Provides a default evaluation function for the given board state.
	 * The evaluation is taken from the point of view of a given player
	 * AFTER he has made his move.
	 * 
	 * If the board state is:
	 * <ul> 
	 *    <li>a win for the player, then MoveEvaluation.maximum() is returned. 
	 *    <li>a draw, then zero is returned.
	 *    <li>a win for the opponent, then MoveEvaluation.minimum()
	 * </ul>
	 * 
	 * Otherwise, the score returned is the sum of the following:
	 * <ul><li>+1,000 points if exists two out of three in a row 
	 *         (Horiz/Vert/Diag) with third being empty
	 *     <li>+10,000 if there are &gt; 1 of the 2/3 situation
	 * </ul>
	 * 
	 * Possible scores are:
	 *     
	 * Note: If a board is passed in with both X and O winning, then
	 * 1,000,000 is returned.
	 * 
	 * @param state    The state of the game
	 * @param ip       The player who has just made their move
	 * @return         integer representing board score.
	 */
	public int score (IGameState state, IPlayer ip) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		Player p = (Player) ip;
		
		int pscore = 0;
		
		// we have lost!
		int n = numInRow (board, 3, p.getOpponentMark());
		if (n > 0) {
			return MoveEvaluation.minimum();
		} 

		// deal with drawn situations with equanimity
		if (board.isDraw()) {
			return 0;
		}
			
		// we are the champion!
		n = numInRow (board, 3, p.getMark());
		if (n > 0) {
			return MoveEvaluation.maximum(); 
		}

		// rate our selves by the number of ways we have two-in-a-row
		n = numInRow (board, 2, p.getMark());
		if (n > 1) {
			pscore = 10000;
		} else if (n == 1) {
			pscore = 1000;
		}
		
		return pscore;
	}
	
	/**
	 * Count the number of 2 or 3 for given mark (where if 2, then extra must be space)
	 * 
	 * @param board    Current board state
	 * @param num      number in a row to be looking for
	 * @param mark     mark we are looking for.
	 * @return         total number of 2 with third being space, or 3.
	 */
	private int numInRow(TicTacToeBoard board, int num, char mark) {
    	int c, r;
	    	
        // Number of two/three where third cell is empty.
    	int ct = 0;
    	
    	// vertical columns
    	for (c = 0; c < board.numColumns(); c++) {
    		if (countMark(board, c, 0, 0, 1, board.numRows(), mark) == num) {
    			ct++;
    		}
    	}

    	// horizontal rows
    	for (r = 0; r < board.numRows(); r++) {
    		if (countMark(board, 0, r, 1, 0, board.numColumns(), mark) == num) {
    			ct++;
    		}
    	}

    	// UpperLeft to LowerRight diagonal
    	if (countMark(board, 0, 0, 1, 1, board.numColumns(), mark) == num) {
			ct++;
		}

    	// LowerLeft to UpperRight diagonal
    	if (countMark(board, 0, 2, 1, -1, board.numColumns(), mark) == num) {
			ct++;
		}

    	return ct;
    }

	/**
	 * Starting from (c,r) count three in direction (dc,dr) and
	 * return the number of cells that contain mark 'm' without
	 * containing opponent marks (empty cells are skipped).
	 * 
	 * If, however, a non-empty cell is discovered
	 * whose mark is not 'm', then -1 is returned -1.
	 * 
	 * @param board   Board state being investigated
	 * @param c       initial cell column
	 * @param r       initial cell row
	 * @param dc      amount to increment c by on each pass
	 * @param dr      amount to increment r by on each pass
	 * @param sz      number of passes to make.
	 * @param m       char we are looking for
	 * 
	 * @return  count or -1 if opponent blocks location.
	 */
	private int countMark (TicTacToeBoard board, int c, int r, int dc, int dr, int sz, char m) {
		// keep track of number seen so far.
		int ct = 0;
		
		// note ADVANCE condition below. increment i and update c,r
		for (int i = 0; i < sz; i++, c+= dc, r+= dr) {
			// skip empty spaces.
			if (board.isClear (c,r)) {
				continue;
			}
			
			// found opponent. Leave Now!
			if (board.get (c,r) != m) {
				return -1;
			}
			
			// we have one
			ct++;
		}
		
		// return our count.
		return ct;
	}
}
