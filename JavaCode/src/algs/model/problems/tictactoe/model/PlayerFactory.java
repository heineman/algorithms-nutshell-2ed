package algs.model.problems.tictactoe.model;

import algs.model.gametree.*;

/**
 * Factory to properly construct Player objects representing the type of 
 * agents playing TicTacToe.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PlayerFactory {
	
	/** Known types. */
	public static final String Random = "Random";
	
	/** Algorithms. */
	public static final String MiniMax = "MiniMax";
	public static final String NegMax = "NegMax";
	public static final String AlphaBeta = "AlphaBeta";
	
	/**
	 * Create a player just by the type. Return null if not random since that type might require parameters
	 * of which this method is unaware.
	 * 
	 * @param type     Type of player
	 * @param mark     X or O mark for player
	 * @return         {@link Player} object representing given mark and type
	 */
	public static Player createPlayer(String type, char mark) {
		Player player = null;
		if (type.equals (Random)) {
			player = new RandomPlayer (mark);
		} else {
			return null;
		}	
		
		// Default to include a quality evaluation function
		player.score(new BoardEvaluation());
		return player;
	}
	
	/**
	 * Create player with a fixed ply lookahead.
	 * 
	 * Use the default Evaluation scoring method. Note that whoever invokes this method
	 * must assign the opponent to be used before that player is asked to determine
	 * its moves.
	 * 
	 * @param type    Type of player
	 * @param mark    Mark to use for player
	 * @param ply     Depth of ply to search.
	 * @return        {@link Player} object representing given type, mark and ply.
	 */
	public static Player createPlayerWithPly(String type, char mark, int ply) {
		Player player = null;
		if (type.equals (AlphaBeta)) {
			player = new IntelligentAgent (mark, new AlphaBetaEvaluation(ply));
		} else if (type.equals (MiniMax)) {
			player =  new IntelligentAgent (mark, new MinimaxEvaluation(ply)); 
		} else if (type.equals (NegMax)) {
			player =  new IntelligentAgent (mark, new NegMaxEvaluation(ply)); 
		} else {
			throw new IllegalArgumentException("PlayerFactory.createPlayerWithPly received unknown type:" + type);
		}
		
		// Default to include a quality evaluation function
		player.score(new BoardEvaluation());
		return player;
	}
}
