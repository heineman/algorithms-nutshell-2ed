package algs.model.gametree;

import algs.model.gametree.MoveEvaluation;

/**
 * Combines an {@link IGameState} position with a {@link MoveEvaluation}
 * that produced the game state.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Pair {
	
	/** The game state in consideration. */
	public final IGameState state;
	
	/** The move evaluation that generated the state. */
	public final MoveEvaluation move;
	
	/**
	 * Pair together the given game state and move evaluation.
	 * @param s    game state
	 * @param m    {@link MoveEvaluation} to assicate with game state 
	 */
	public Pair (IGameState s, MoveEvaluation m) {
		this.state = s;
		this.move = m;
	}
}