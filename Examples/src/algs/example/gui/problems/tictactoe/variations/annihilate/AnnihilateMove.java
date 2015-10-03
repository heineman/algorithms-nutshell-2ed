package algs.example.gui.problems.tictactoe.variations.annihilate;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Represents a move for the AnnihilateTicTacToe variation that
 * removes an opponent's marker if two neighboring cells (in any direction)
 * contain the player's marker.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AnnihilateMove extends PlaceMark {
	/** marker that was in cell before move was engaged [used during undo]. */
	protected char oldMarker = '\0';
	 
	/**
	 * Construct an AnnihilateMove move with given (col,row) and mark to be placed.
	 * 	    REQUIRES: player.mark != ' ' AND 0 <= col < # of columns in board AND
	 *                0 <= row < # of rows in board. 
	 */
	public AnnihilateMove (int col, int row, Player player) {
		super (col, row, player);
	}
	
	/**
     * Determine the number of neighboring cells that have the same
     * token as the desired character c.
     * 
     * Note: this method takes care of all special cases.
     * 
     * @param board    Board being investigated
     * @param col      column of desired cell to be annihilated
     * @param row      row of desired cell to be annihilated
     * @param c        player's marker 
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
	 * Determines if AnnihilateMove is valid.
	 * 
	 * An annihilate move MUST remove a cell occupied by opponent.
	 */
	public boolean isValid(TicTacToeBoard board) {
		if (board.isClear(col, row)) {
			return false;
		}
		
		// can't play on our own spots.
		if (board.get(col, row) == player.getMark()) {
			return false;
		}

		// can be annihilated if >=2 neighbors.
		return (neighbors (board) >= 2);
	}

	/**
	 * Make the annihilate move happen.
	 */
	public boolean execute (IGameState gameState) {
	   TicTacToeState state = (TicTacToeState) gameState;
	   TicTacToeBoard board = state.board();
		if (isValid (board)) {
			oldMarker = board.get (col, row);
			
			// we made decision to prevent set on occupied squares; 
			// we must clear first.
			board.clear(col, row);  
			board.set (col, row, player.getMark());
			return true;
		}
		
		// move is invalid and cannot happen.
		return false;
	}

	/**
	 * Undoes the move.
	 */
	public boolean undo(TicTacToeBoard board) {
		// move wasn't made.
		if (oldMarker == '\0') {
			return false;
		}
		
		board.set (col, row, oldMarker);
		return true;
	}
	
	/**
	 * Determine equality based on structure.
	 * 
	 * Reuses equals() from parent class.
	 */
	public boolean equals (Object o) {
		if (o instanceof AnnihilateMove) {
			AnnihilateMove po = (AnnihilateMove) o;
			return (po.oldMarker == oldMarker) && super.equals(o);
		}
		
		// nope
		return false;
	}
	
	/**
	 * Return object in readable form.
	 */
	public String toString () {
		return "[Annihilate " + oldMarker + " @ (" + col + "," + row + ") with " + player.getMark() + "]";
	}	

}
