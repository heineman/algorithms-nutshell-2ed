package algs.model.gametree;

import java.util.*;

/**
 * Represents an Intelligent agent that uses the NegMax algorithm to select a move. 
 * <p>
 * NegMax is a variation on MiniMax developed by Knuth/Moore (1975) that aims
 * to simplify the evaluation of moves. Instead of alternating between MIN and 
 * MAX levels, each evaluation is a MAX that conforms to the following logic:
 * <pre>
 *      A        
 *     / \       
 *    B   C      
 *   / \  |\     
 *  D  E  F G    
 * </pre>
 * <p>
 * At Level A, player aims to maximize the best scores of his children (B
 * and C).  A will ultimately select "the Maximum of the negative scores 
 * of its children". To make this work, non-terminal positions (i.e., A,B,C) 
 * return the maximum of the negative scores of its children as evaluated 
 * accordingly by the player. Thus PLAYER evaluates A, while OPPONENT evaluates
 * the boards at B,C. Consider the OPPONENT'S two moves from state B that lead 
 * to D and E. These boards are evaluated according to PLAYER. If E is the stronger
 * position (say 12) while D is weaker (say -3) then B "returns the max of the 
 * negation of its children's values". Thus B returns "-12". Assume in the subtree
 * of C that position F evaluates to 4 and G evaluates to 7, thus C returns "-7".
 * Now, A is choosing between B and C values, and it does so by selecting the max
 * of the negated values. Thus A evaluates to MAX (-(-12), -(-7)) which results in
 * a score of 12. Thus in position A, PLAYER can ensure a score of 12.
 * <p>
 * Truth be told, the more convoluted logic only seems to increase the complexity
 * of the algorithm, since the return value is the same as MiniMax. The only true
 * benefit lies in the future extension of NegMax with Alpha/Beta pruning.
 * <p>
 * The toString method is provided to make it a bit easier to debug.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NegMaxEvaluation implements IEvaluation {
	
	/** Game state to be modified during search. */
	IGameState state;
	
	/** Ply depth. How far to continue search. */
	int ply;
	
	/**
	 * Create an evaluator with the given state. It is important that
	 * the same player evaluate the board regardless of MIN and MAX. The
	 * player will be known when <code>bestMove</code> is invoked.
	 * 
	 * @param ply       Depth to search.
	 */
	public NegMaxEvaluation (int ply) {
		this.ply = ply;
	}
	
	/**
	 * Initiates the NegMax computations by using its ply to determine the number
	 * of moves in advance to look.
	 * <p>
	 * If no moves are available to player, then null is returned.
	 * @param s                  Initial Game State
	 * @param player             The player making the next move
	 * @param opponent           The player's opponent
	 */
	public IGameMove bestMove (IGameState s, IPlayer player, IPlayer opponent) {
		state = s.copy();
		MoveEvaluation me = negmax (ply, player, opponent);
		return me.move;
	}
	
	/**
	 * Given the board state, Intelligent player is trying to determine where
	 * to make his move. The decision will be made based upon trying all possible
	 * moves for Intelligent player and determining which one produces maximum
	 * getScore() evaluation.
	 * <p>
	 * Since we swap player and opponent each time, we always evaluate the board
	 * state to select move that Maximizes the negative score of our opponent.
	 * 
	 * @param ply        the fixed depth to look ahead.
	 * @param player     the current player.
	 * @param opponent   the opponent.
	 * @return           best {@link MoveEvaluation} as determined by algorithm
	 */
	public MoveEvaluation negmax (int ply, IPlayer player, IPlayer opponent) {
		
		// If no allowed moves or a leaf node, return board state score.
		Iterator<IGameMove> it = player.validMoves (state).iterator();
		if (ply == 0 || !it.hasNext()) {
			return new MoveEvaluation (player.eval (state)); 
		}
		
		// Try to improve on this lower-bound move.
		MoveEvaluation best = new MoveEvaluation (MoveEvaluation.minimum());

		// get moves for this player and generate the boards that result from
		// making these moves. Select maximum of the negative scores of children.
		while (it.hasNext()) {
			IGameMove move = it.next();
			move.execute (state);

			// Recursively evaluate position using consistent negmax. 
			MoveEvaluation me = negmax (ply-1, opponent, player);
			move.undo (state);
			
			if (-me.score > best.score) {
				best = new MoveEvaluation (move, -me.score);
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
}
