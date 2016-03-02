package algs.model.gametree;

import java.util.*;

/**
 * Initiate AlphaBeta Evaluation over the given game state and ply.
 * <p>
 * Base this implementation on the Negmax algorithm, since the pruning code is
 * simpler to understand.
 * <p>
 * To properly enable debugging, we must invoke the counter methods of the
 * IGameState being processed (incrementing when we expand and decrementing
 * when we retract). The number of states explored can be found in the numStates
 * class variable. 
 * <p>
 * The toString method is provided to make it a bit easier to debug.
 * <p>
 * All lines commented with STAT can be eliminated and are only here to 
 * make it possible to generate statistics about the execution of the 
 * algorithm. Their overhead is minimal.
 * <p>
 * Note that a lookahead of ZERO will only return the evaluation of the board
 * state and the returned move will be null.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AlphaBetaEvaluation implements IEvaluation {
	/**State to be modified during search. */
	IGameState state;

	/** Ply depth. How far to continue search. */
	int ply;
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the board regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search; must be greater than zero.
	 */
	public AlphaBetaEvaluation (int ply) { this.ply = ply; }

	/**
	 * Initiates the AlphaBeta computations by determining the maximum number of
	 * moves in advance to look.
	 * <p>
	 * The original game state is copied prior to being processed so no external 
	 * effect occurs. This implementation is derived from NegMax and selects moves
	 * accordingly.
	 * <p>
	 * If no moves are available to player, then null is returned.
	 * 
	 * @param s                  Game state being evaluated. 
	 * @param player             The player making the next move.
	 * @param opponent           The player's opponent.
	 */
	public IGameMove bestMove (IGameState s, IPlayer player, IPlayer opponent) {
		state = s.copy();
		numStates++; /* STATS */
		MoveEvaluation me = alphabeta(ply, player, opponent, 
				MoveEvaluation.minimum(), MoveEvaluation.maximum());
		
		return me.move;
	}
	
	/**
	 * Given the board state, use alphaBeta algorithm to locate best move 
	 * for original player. 
	 * <p>
	 * The MoveEvaluation object records the move with each score. The move is truly
	 * only useful in the first invocation, since that is returned to bestMove. It is 
	 * done this way to avoid having to duplicate logic in different methods.
	 * <p>
	 * Since we build the alpha/beta logic on top of NegMax, there is only one type of
	 * pruning that occurs, namely when alpha >= beta. The [alpha, beta] range reflects
	 * the global state of the search and defines a "window" within which the search will
	 * continue to look for moves. If the window "vanishes" because alpha >= beta, then
	 * no further searching is necessary, and the search tree is "pruned".
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param player     the current player
	 * @param opponent   the opponent
	 * @param alpha      maximum lower bound of an allowed move 
	 * @param beta       minimum upper bound of an allowed move 
	 */
	private MoveEvaluation alphabeta (int ply, IPlayer player, IPlayer opponent, int alpha, int beta) {
		// If no moves at all, return evaluation of board from player's perspective.
		Iterator<IGameMove> it = player.validMoves (state).iterator();
		if (ply == 0 || !it.hasNext()) {
			return new MoveEvaluation (player.eval (state));
		}
		
		// Select "maximum of negative value of children" that improves alpha
		MoveEvaluation best = new MoveEvaluation (alpha);
		while (it.hasNext()) {
			IGameMove move = it.next();
			
			move.execute (state);
			numStates++; /* STATS */
			MoveEvaluation me = alphabeta (ply-1, opponent, player, -beta, -alpha);
			move.undo (state);
			
		    // If improved upon alpha, keep track of this move.
			if (-me.score > alpha) { 
				alpha = -me.score; 
				best = new MoveEvaluation (move, alpha); 
			}
			
			// search no longer productive.
			if (alpha >= beta) { 
				return best; 
			} 
		} 
		return best;
	}
	
	/** 
	 * Expose board state as string (useful for debugging purposes).
	 */
	public String toString () {
		return state.toString();
	}
	
	/** statistical information to evaluate algorithms effectiveness. */
	public int numStates = 0;
}
