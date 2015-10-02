package algs.model.problems.tictactoe.model;

import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;

/**
 * Represents a Move on the TicTacToe Board.
 * <p>
 * At this level of abstraction, we cannot define an Abstraction Function
 * because there is no known representation.
 * <p>
 * However, we must state unequivocally that each {@link Move} object must define
 * a proper toString() because otherwise the hashCode method defined here
 * will not work.
 * <p>
 * Also, the equals(Object o) method must be implemented by all subclasses.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class Move implements IGameMove {

	/**
	 * Determines if move can be made on the given TicTacToe Board.
	 * 
	 *      REQUIRES: board != null
	 * 
	 * @return   true if the move can be made; false, otherwise.
	 */
	public abstract boolean isValid (IGameState board);
	
	/**
	 * Makes the move on the given TicTacToe Board.
	 * 
	 *      REQUIRES: board != null
	 *      MODIFIES: board
	 *      EFFECTS:  board state is updated as appropriate for the given move.
	 * 
	 * @return   true if the move was valid given the board configuration.
	 */
	public abstract boolean execute (IGameState board);
	
	/**
	 * Removes the effect of the move from the TicTacToe Board.
	 * 	    REQUIRES: (board != null) and (this was most recent move on board)
	 *      MODIFIES: board
	 *      EFFECTS:  board state is updated as if this move had not
	 *                happened.
	 */
	public abstract boolean undo (IGameState board);
	
	/**
	 * Ensure that we can use Move objects within hashtables by 
	 * simplying calculating hashCode values based upon toString()
	 * for the Move subclasses.
	 */
	public int hashCode () {
		return toString().hashCode();
	}
}
