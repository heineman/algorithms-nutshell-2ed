package algs.example.gui.problems.tictactoe.variations.neighbor;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Records the last move with logic.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NeighborPlaceMark extends PlaceMark {
	
	/**
	 * @param col
	 * @param row
	 * @param p     Player making the move.
	 */
	public NeighborPlaceMark(int col, int row, Player p) {
		super(col, row, p);
	}
	
	/**
	 * Make the PlaceMark move, and update saved position information
	 * 
	 * @param gameState   current board state.
	 */
	public boolean execute (IGameState gameState) {
	    boolean rc = super.execute(gameState);
	    
	    // now update last position
	    TicTacToeState state = (TicTacToeState) gameState;
	    NeighborState neighbor = (NeighborState) state.storedData();
	    neighbor.updateLast(player, new Cell (col, row));

	    return rc;
	}
	
	/**
	 * Undo the PlaceMark move, and update saved position information
	 */
	public boolean undo(IGameState gameState) {
		boolean rc = super.undo(gameState);
		
		// now update last position
		TicTacToeState state = (TicTacToeState) gameState;
		NeighborState neighbor = (NeighborState) state.storedData();
		neighbor.undoLast(player);

		return rc;
	}
}
