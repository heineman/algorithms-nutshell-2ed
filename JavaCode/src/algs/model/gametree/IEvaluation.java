package algs.model.gametree;

/**
 * Common interface for game Tree algorithms seeking the best move given
 * a particular game state and player making its move.
 * <p>
 * Since this is a two-person game which may need to look into the future,
 * the current opponent is passed along for good measure.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IEvaluation {
	
	/** 
	 * For game state, player and opponent, return the best move. 
	 * <p>
	 * If no move is even available, then null is returned.
	 * 
	 * @param state     Current game position
	 * @param player    Current player making move
	 * @param opponent  Opponent of player who will make counter moves 
	 * @return          selected move considered to be best in this game state.
	 */
	IGameMove bestMove (IGameState state, IPlayer player, IPlayer opponent);
}
