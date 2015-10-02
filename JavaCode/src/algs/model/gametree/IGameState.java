package algs.model.gametree;

import algs.debug.IGraphEntity;

/**
 * A valid representation of the state of a particular game with two players.
 * <p>
 * A game state is either in progress, has been won by a player, or is a draw.
 * <p>
 * For ease of debugging, each state has a reference counter that is incremented when
 * a new state is computed via a move and never decremented. It can thus be used as a
 * unique id when referencing states in the game tree later.
 * <p>
 * To support graphical drawing of state searches, this interface extends IGraphEntity.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IGameState extends IGraphEntity {

	/**
	 * Determine if this game state is a draw.
	 * @return true if this game is a draw 
	 */
	boolean isDraw();
	
	/** 
	 * Determine if this game state has a winner.
	 * @return true if game has been won 
	 */
	boolean isWin();
	
	/** 
	 * Enable one to grab a copy of this game state.
	 * @return copy of the game state 
	 */
	IGameState copy();
	
	/** 
	 * Determine if this game state is equivalent to the given state.
	 * <p>
	 * The notion of equivalence is based upon the actual game. For games
	 * that exhibit symmetries in game state (such as board games), you can
	 * get great savings simply by reducing symmetrical positions.
	 * <p>
	 * Useful when attempting to reduce the search space. This method is 
	 * defined separately from {@link Object#equals(Object)} to make sure
	 * there is no confusion with using equals by the JDK collection classes.
	 * 
	 * @param state The game state being compared against.     
	 * @return true if the states can be considered equivalent.
	 */
	boolean equivalent (IGameState state);
	
	// debugging interface.
	
	/** Debugging interface for incrementing count of games. */
	void incrementCounter();
	
	/**
	 * Debugging interface for retrieving counter.
	 * @return counter for debugging purposes. 
	 */
	int counter();
}
