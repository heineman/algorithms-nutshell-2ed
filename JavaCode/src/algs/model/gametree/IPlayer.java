package algs.model.gametree;

import java.util.Collection;

/**
 * A Player of the game.
 * <p>
 * Each player has a scoring method that can be used to evaluate a
 * given game state. For human players, the scoring function is used
 * when the human plays against a computer opponent. Specifically, when 
 * the algorithm tries to identify the best move that the player might make,
 * the evaluation function tells what that best move is to be.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IPlayer {

	/**
	 * Return the player's valid moves given the game state.
	 * <p>
	 * If no moves are available, returns an empty Collection, not null.
	 * 
	 * @param state    game state from which player is to make moves.
	 * @return         {@link Collection} of valid moves from given game state
	 */
	Collection<IGameMove> validMoves(IGameState state);

	/** 
	 * Return the evaluation of this game state from player's perspective.
	 * 
	 * @param  state   The game state  
	 * @return   integer evaluating the game state from player's perspective.
	 */
	int eval (IGameState state);
	
	/** 
	 * Set the scoring method used by player on the game state.
	 * @param score   new scoring method 
	 */
	void score(IGameScore score);
}
