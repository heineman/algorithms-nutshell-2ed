package algs.model.gametree;

import java.util.*;

/**
 * Perform a MiniMax evaluation over a game state to the fixed ply depth.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MinimaxEvaluation implements IEvaluation {
	
	/** Game state to be modified during search. */
	IGameState state;
	
	/** Ply depth. How far to continue search. */
	int ply;
	
	/** Evaluate all states from this perspective. */
	IPlayer original;
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the state regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search.
	 */
	public MinimaxEvaluation (int ply) {
		this.ply = ply;
	}
	
	/**
	 * Initiates the MiniMax computations by using its ply to determine the maximum
	 * number of moves in advance to look.
	 * <p>
	 * If no moves are available to player, then null is returned.
	 * 
	 * @param s                  Initial game state
	 * @param player             The player making the next move
	 * @param opponent           The player's opponent
	 */
	public IGameMove bestMove (IGameState s, IPlayer player, IPlayer opponent) {
		this.original = player;
		this.state = s.copy();
		numStates++; /* STATS */

		MoveEvaluation move = minimax (ply, IComparator.MAX, player, opponent);
		return move.move;
	}
	
	/**
	 * Given the game state, use minimax algorithm to locate best move 
	 * for original player. 
	 * <p>
	 * Note that the initial invocation of this method will be on MIN states
	 * <p>
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param comp       the type (MIN or MAX) of this level, to evaluate moves. MAX selects
	 *                   the best move while MIN selects the worst moves.
	 * @param player     the current player.
	 * @param opponent   the opponent.
	 */
	MoveEvaluation minimax (int ply, IComparator comp, 
			IPlayer player, IPlayer opponent) {

		// If no allowed moves or a leaf node, return game state score.
		Iterator<IGameMove> it = player.validMoves (state).iterator();
		if (ply == 0 || !it.hasNext()) {
			return new MoveEvaluation (original.eval (state)); 
		}

		// Try to improve on this lower-bound (based on selector). 
		MoveEvaluation best = new MoveEvaluation (comp.initialValue());

		// Generate game states resulting from valid moves for this player 
		while (it.hasNext()) {
			IGameMove move = it.next();
			move.execute (state);
			numStates++; /* STATS */

			// Recursively evaluate position. Compute MiniMax and swap
			// player and opponent, synchronously with MIN and MAX.
			MoveEvaluation me = minimax (ply-1, comp.opposite(), opponent, player);
			
			move.undo (state);
			
			// Select maximum (minimum) of children if we are MAX (MIN)
			if (comp.compare (best.score, me.score) < 0) {
				best = new MoveEvaluation (move, me.score); 
			}
		}
		
		return best;
	}
	
	/** 
	 * Expose game state as string (useful for debugging purposes).
	 */
	public String toString () {
		return state.toString();
	}
	
	// statistical information to evaluate algorithms effectiveness.
	public int numStates = 0;
}
