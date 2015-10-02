package algs.model.problems.tictactoe.model;

import java.util.Collection;
import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;

/**
 * Randomly makes moves given the logic of the TicTacToe variation.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class RandomPlayer extends Player {

	/**
	 * Construct a Random player who determines a move randomly from available open cells.
	 * 
	 * @param mark     Mark to be used for the player.
	 */
	public RandomPlayer(char mark) {
		super(mark);
	}	
	
	/**
	 * Randomly make a move based upon the available logic of the game.
	 * 
	 * Make sure you check that the game is not already won, lost or drawn before calling 
	 * this method, because you  
	 * 
	 * @param   state   Current board state
	 * @return          random valid Move to make, or null if none is available. 
	 */
	public IGameMove decideMove(IGameState state) {
		// if board is a draw, do nothing.
		if (state.isDraw()) return null;
 
		// board already has been won.
		if (state.isWin()) return null;
		
		// This works properly, since this works on the scheduled player in board.
		// 
		Collection<IGameMove> moves = logic.validMoves(this, state);
		if (moves.size() == 0) {
			// Should never happen, given earlier draw/win checks, but validMoves may inadvertently
			// return NO moves, so we have to protect against it.
			return null;
		} else {
			IGameMove []mvs = moves.toArray(new IGameMove[]{});
			int idx = (int)(Math.random() * moves.size());
			return mvs[idx];
		}
	}

	/**
	 * Return the valid moves for this player given the game state. 
	 * 
	 * @param state   Current game state position
	 */
	public Collection<IGameMove> validMoves(IGameState state) {
		return logic.validMoves(this, state);
	}
}
