/*
 * TicTacToe complete infrastructure.
 */
package algs.example.gui.problems.tictactoe.variations.neighbor;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;



/**
 * Each player can place an X or an O in an empty square as their first move. However,
 * on each successive move, the player must place a mark in an empty square that is
 * adjacent to one of their earlier marks. If this is not possible, that player can then
 * choose any available open square.
 *
 * We thus extend PlaceMark and further restrict its activity to ensure
 * that we are adjacent to a square with our mark.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NeighborMove extends PlaceMark {
	/** Player making the move. */
	Player player;
	
	/**
	 * Create a Neighbor move, given known location and player.
	 * 
	 * @param col     column of move
	 * @param row     row of move
	 * @param p       NeighborPlayer
	 */
	public NeighborMove(int col, int row, Player p) {
		super(col, row, p);
		
		player = p;
	}
	

	/**
     * Determine the number of neighboring cells that have the same
     * token as the desired character c.
     * 
     * Note: this method takes care of all special cases.
     * 
     * @param board    Board being investigated
     * @return         number of neighboring cells with same mark.
     */
    private int neighbors (TicTacToeBoard board) {
        int count = 0;
        for (int cd = -1; cd <= 1; cd++) {
            for (int rd = -1; rd <= 1; rd++) {
            	if ((cd == 0) && (rd == 0)) {
            		// continue is the opposite of break. This means
            		// go back and continue the for loop above (the
            		// closest one, namely rd).
            		continue;
            	}
            	
                int tcol = col + cd;
                int trow = row + rd;
                
                // protect by only looking inside valid cells.
                if ((0 <= tcol) && (tcol < board.numColumns()) &&
                        (0 <= trow) && (trow < board.numRows())) {
                    // increment count if same character.
                    if (board.get(tcol, trow) == player.getMark()) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }		
	
	/**
	 * A NeighborMove is valid if it is placed into an Empty
	 * cell but also is adjacent (horizontally, vertically, diagonally)
	 * to an existing cell with our mark in it.
	 * 
	 * @param board     current board state.
	 */
	public boolean isValid(TicTacToeBoard board) {
		return (neighbors (board) >=1);	
	}
	
	/**
	 * Return object in readable form.
	 */
	public String toString () {
		return "[NeighborPlaceMark @ (" + col + "," + row + ")]";
	}		

	/**
	 * Determine equality based on structure.
	 * 
	 * Reuses equals() from parent class.
	 * @param o     Object of class NeighborMove to be compared against.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof NeighborMove) {
		    NeighborMove npm = (NeighborMove) o;
			return (npm.player == player) && super.equals(o);
		}
		
		// nope
		return false;
	}	
	
	/**
	 * Makes the move on the given TicTacToe Board.
	 * 
	 *      REQUIRES: board != null
	 *      MODIFIES: board
	 *      EFFECTS:  board state is updated as appropriate for the given move.
	 * 
	 * @return   true if the move was valid given the board configuration.
	 */
	public boolean execute (IGameState gameState) {
		// do the move
		boolean rc = super.execute (gameState);
		if (rc) {
			TicTacToeState state = (TicTacToeState) gameState;
		    NeighborState neighbor = (NeighborState) state.storedData();
		    
			// we were valid, so update NeighborPlayer lastSpot
		    neighbor.updateLast (player, (new Cell (this.col, this.row)));
		}
		
		return rc;
	}
	
	public boolean undo (IGameState gameState) {
		boolean rc = super.undo (gameState);
		TicTacToeState state = (TicTacToeState) gameState;
		NeighborState neighbor = (NeighborState) state.storedData();
	    
		// we were valid, so update NeighborPlayer lastSpot
	    neighbor.undoLast (player);
	    return rc;
	}
}
