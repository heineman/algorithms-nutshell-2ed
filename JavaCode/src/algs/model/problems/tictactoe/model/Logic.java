package algs.model.problems.tictactoe.model;

import java.util.ArrayList;
import java.util.Collection;

import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;

/**
 * Contains logic for a particular TicTacToe Variation about how
 * to interpret the attempt to select a cell (Col, Row) as the 
 * desired move.
 * 
 * Note: This will not capture all cases, but will get most.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class Logic {
	
	/**
	 * Method to determine the type of move that the user has 
	 * selected.
	 * 
	 * Subclasses should override this method so they will be
	 * able to properly construct the appropriate move given
	 * the context.
	 * 
	 * @param board         current board state
	 * @param col           selected column
 	 * @param row           selected row
	 * @param player        player making the move
	 * 
	 * @return              null if move is invalid; otherwise a valid Move object.
	 */
	public abstract Move interpretMove(IGameState board, int col, int row, Player player);
	
	/**
	 * Return the valid moves at this board state for the given player.
	 * 
	 * Note that this will return an empty collection if the game is over (win, lose or draw).
	 * 
	 * @param ip    player whose perspective is in question
	 * @param state the game state 
	 * @return      {@link Collection} of valid moves from the given game state.
	 */
	public Collection<IGameMove> validMoves(IPlayer ip, IGameState state) {
		Player player = (Player) ip;
		ArrayList<IGameMove> valids = new ArrayList<IGameMove>();
		
		// if game is over or a draw? No more moves
		if (state.isWin()) { return valids; }
		if (state.isDraw()) { return valids; }
		
		TicTacToeState gameState = (TicTacToeState) state;
		TicTacToeBoard board = gameState.board();
		
		// methodically try all possible moves. 
		for (int c = 0; c < board.numColumns(); c++) {
			for (int r = 0; r < board.numRows(); r++) {
				// When invalid, null is returned.
			    Move move = interpretMove(state, c, r, player);
				if (move != null) {
					valids.add(move);
				}
			}
		}
		
		return valids;
	}

	/**
	 * Each logic must implement 'copy' to properly be used when 
	 * evaluating future moves.
	 * 
	 * @return   a Copy of the Logic object.
	 */
	public abstract Logic copy ();
	
	/**
	 * Most TicTacToe variations are guaranteed to have a fixed number of
	 * moves before the game is either won, lost, or drawn. However, to 
	 * plan for some variations that are more extensive, this method
	 * determines (for the variation) the cut-off point at which a game
	 * must be considered a draw. 
	 * 
	 * We set a reasonable default, and allow variations to extend as 
	 * needed.
	 * 
	 * @return the maximum number of allowed moves at which time a draw must
	 *         be determined if the game has not been won.
	 */
	public int maxNumberMoves() {
		return 9;		
	}

	/** 
	 * Enable variations to instantiate the stored data with each game state.
	 * 
	 * Override by subclasses, as needed. Provide default null implementation 
	 * here when not needed to simplify subclasses.
	 * @param state     the initial state to use.
	 */
	public void initializeState(TicTacToeState state) {}
	
	/**
	 * Return a description of the game and how it is to be played.
	 * @return human readable string
	 */
	public abstract String rules();
}
	
