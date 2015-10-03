package algs.example.gui.problems.tictactoe.variations.neighbor;

import java.util.Hashtable;

import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.problems.tictactoe.model.Cell;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.TicTacToeBoard;

/**
 * Store additional state information with each GameState to record
 * the known information about neighbor moves.
 * 
 * Note that we must support any number of undo, so we can't use a Hashtable to
 * store the information. We need to use a linked list
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NeighborState {
	
	/**
	 *  Needs to know about the last moves for each player. 
	 *     key = player.toString(),  value = Cell representing lastMove. 
	 */
    Hashtable<Player,DoubleLinkedList<Cell>> lastMoves = new Hashtable<Player,DoubleLinkedList<Cell>>();

	/**
	 * Return the location where player last played, or null if  invalid player or first move.
	 *  
	 * @param p   Player who made their move last.
	 */
	public Cell getLastMove (Player p) {
		DoubleLinkedList<Cell> list = lastMoves.get(p);
		if (list == null) return null;
		
		DoubleNode<Cell> last = list.last();
		if (last == null) {
			return null;
		}
		return last.value();
	}
	
	/**
	 * Undo the location of the last move.
	 * 
	 * @param p      Player whose move is being removed.
	 */
	public void undoLast(Player p) {
		DoubleLinkedList<Cell> list = lastMoves.get(p);
		if (list == null) {
			System.err.println ("Unexpected NULL list in NeighborState.undoLast");
			return;  // should never happen.
		}
		
		if (list.last() == null) {
			System.err.println ("Unexpected NULL list in NeighborState.undoLast");
		}
		
		list.removeLast();
	}
	
	/**
	 * Append the location of the last move.
	 * 
	 * @param c      Cell that contains (c,r) of last location.
	 */
	public void updateLast(Player p, Cell c) {
		DoubleLinkedList<Cell> list = lastMoves.get(p);
		if (list == null) {
			list = new DoubleLinkedList<Cell>();
			lastMoves.put(p, list);
		}

		list.insert(c);
	}
	
    /**
     * Determines if player is placing their move (col,row) next
     * to their last move, unless all neighboring squares next to
     * their last move are occupied (in which case, false is returned).
     *  
     * @param board     current board state
     * @param player    player making themove.
     * @param col       cell to determine our move against.
     * @param row       row to determine our move against.
     * @return          <code>true</code> if move is adjacent to last move; <code>false</code> otherwise. 
     */
	public boolean validNeighbor (TicTacToeBoard board, Player player, int col, int row) {
    	
    	// Assume all have been taken and that we aren't a true neighbor
    	// of last move.
		boolean allTaken = true;
		boolean isNeighbor = false;
		
		for (int dc = -1; dc <= 1; dc++) {
			for (int dr = -1; dr <= 1; dr++) {
				// don't check our current spot.
				if ((dc == 0) && (dr == 0)) {
					continue;
				}
				
				// no last move? Then can't be adjacent!
				Cell lastOne = getLastMove (player);
				if (lastOne == null) {
				    return false;
				}
				
				int tcol = lastOne.col + dc;
		    	int trow = lastOne.row + dr;
				
		    	// protect by only looking inside valid cells.
                if ((0 <= tcol) && (tcol < board.numColumns()) &&
                        (0 <= trow) && (trow < board.numRows())) {
                    if (board.isClear (tcol, trow)) {
                        allTaken = false;  // must be an empty spot.
                    }
                }		    	
		    	
                if ((col == tcol) && (row == trow)) {
                	isNeighbor = true;     // we are a neighbor 
                }
			}
		}

		// If we have nothing available, return now with TRUE. If we are a neighbor,
		// return now with true, otherwise FALSE.
		return (allTaken || isNeighbor);
    }
	
	/** Useful for debugging. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (Player p : lastMoves.keySet()) {
			DoubleLinkedList<Cell> list = lastMoves.get(p);
			
			sb.append ("  " + p + ": " + list + "\n");
		}
		
		return sb.toString();
	}
	
}
