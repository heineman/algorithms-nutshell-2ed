package algs.model.gametree;

/** 
 * Each game state position requires some scoring function. 
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IGameScore {
	
	/** 
	 * Method to evaluate a game state from a player's perspective.
	 * <p>
	 * Intended to enable scoring functions from being designed separately 
	 * from the specific representation of a game. Higher scores are 
	 * more favorable for the given {@link IPlayer}. A maximum score 
     * value ({@link Integer#MAX_VALUE}) implies that the {@link IPlayer} has won
     * the game. A minimum score value ({@link Integer#MIN_VALUE}) implies
     * that the opponent has won the game. In general when comparing two
     * score values returned by this interface, the one with the higher 
     * score reflects a better state position for the given {@link IPlayer}.
	 * 
	 * @param   state   The current game state position
	 * @param   player  The player from whose perspective the game state is evaluated
	 * @return          integer value representing the evaluation of a game state.
	 */
	int score (IGameState state, IPlayer player);
}
