package algs.example.gui.problems.tictactoe;


import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Player whose interaction is through mouse events.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MousePlayer extends Player {
	/** Move that user has most recently selected. */
	Move move;
	
	/**
	 * Construct a Mouse player whose input is determined by a graphical controller
	 * and who follows the rules as given by Logic l, and plays the given mark.
	 *
	 * @param mark     Mark to be used for the player.
	 */
	public MousePlayer(char mark) {
		super(mark);
	}
	
	/**
	 * Set the desired move for the user.
	 * 
	 * @param move
	 */
	public void setMove(Move move) {
		this.move = move;
	}
	
	/**
	 * Simply returns the move decided via the Mouse
	 */
	public Move decideMove(IGameState board) {
		return move;
	}

	
}
