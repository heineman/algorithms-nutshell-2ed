package algs.example.gui.problems.tictactoe.variations.neighbor;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Each player can place an X or an O in an empty square as their
 * first move. However, on each successive move, the player must 
 * place a mark in an empty square that is adjacent to THEIR 
 * PREVIOUS MARK. If this is not possible, that player can then
 * choose any available open square.
 *
 * For example:
 * 
 * <pre>
 *    X|  |	    X|  |      X|  |	  X|  |     X|  |      X|  |O	
 *   --+--+--  --+--+--   --+--+--   --+--+--  --+--+--   --+--+--
 *     |  |  ->  |O |   -> X|O |  ->  X|O |     X|O |   -> X|O | 
 *   --+--+--  --+--+--   --+--+--   --+--+--  --+--+--   --+--+--
 *     |  |      |  |       |  |      O|  |     O|X |      O|X | 
 * 
 *                                             O is Blocked
 *                                             can win next move!
 * <pre> 
 * 
 * <ul><li>A typical NeighborLogic maintains the state of the two last moves, one
 *         for player X and one for player O.
 *     <li>AF (nl) = { Hashtable 'lastMoves' whose keys are toString from the player objects,
 *                     and values are Cell objects }
 * </ul>
 * 
 * <ul><li>If lastMoves.get(player) is null, then player has not yet made
 *         any moves on the board.
 *     <li>contents of the Hashtable are Cell objects. Keys are String versions of player marks.
 * </ul>
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NeighborLogic extends Logic {
	
	/** Parameterize the Game state with our specialized NeighborState info. */
	public void initializeState(TicTacToeState state) {
		state.storedData(new NeighborState());
	}
	
    /**
	 * Each logic must implement 'copy' to properly be used when 
	 * evaluating future moves.
	 * 
	 * @return   a Copy of the NeighborLogic object.
	 */
	public NeighborLogic copy () {
	    NeighborLogic nb = new NeighborLogic();
	    
	    // return new neighborLogic object.
	    return nb;
	}

	/** 
	 * Set ply depth.
	 * 
	 * 4 is insufficient to guarantee win for Neighboring TicTacToe. It will choose center square, whereas
	 * choosing the CORNER will guarantee the win.
	 * 
	 * Choose to override by specialized logic if more depth is required.
	 */
	public int plyDepth() {
		return 5;
	}
	
	/**
	 * Determines if last move has an available neighbor.
	 * 
	 * If there has been no previous move, then true is returned.
	 * 
	 * @param board            current board state
	 * @param previousMove     location of prior move
	 * @return                 <code>true</code> if no more allowed moves; <code>false</code> otherwise.
	 */
	private boolean availableNeighbor(TicTacToeBoard board, Cell previousMove) {
		// nothing special to do
		if (previousMove == null) {
			return true;
		}
		
		int count = 0;
        for (int cd = -1; cd <= 1; cd++) {
            for (int rd = -1; rd <= 1; rd++) {
            	if ((cd == 0) && (rd == 0)) {
            		// continue is the opposite of break. This means
            		// go back and continue the for loop above (the
            		// closest one, namely rd).
            		continue;
            	}
            	
                int tcol = previousMove.col + cd;
                int trow = previousMove.row + rd;
                
                // protect by only looking inside valid cells.
                if ((0 <= tcol) && (tcol < board.numColumns()) &&
                        (0 <= trow) && (trow < board.numRows())) {
                    // increment count if same character.
                    if (board.isClear (tcol, trow)) {
                        count++;
                    }
                }
            }
        }
        
        return (count == 0);
	}

	/**
	 * Method to determine the type of move that the user has selected.
	 * 
	 * 
	 * @param gameState     current game state
	 * @param col           selected column
 	 * @param row           selected row
	 * @param player        player making the move
	 * @return              null if move is invalid; otherwise a valid Move object.
	 */
	public Move interpretMove(IGameState gameState, int col, int row, Player player) {
		TicTacToeState state = (TicTacToeState) gameState;
		NeighborState neighbor = (NeighborState) state.storedData();
		TicTacToeBoard board = state.board();
		
		// board position is already taken? Don't even bother...
		if (!board.isClear(col,row)) {
			return null;
		}


		// Now that we have determined we have marks, let's verify
		// that we are adjacent to our last move. If all of the 
		// spots that are adjacent to our last move are FULL, then
		// we can just place the mark
		if (availableNeighbor (board, neighbor.getLastMove(player))) {
			return new NeighborPlaceMark (col, row, player);
		}
			
		// Only other alternative is NeighborMove. Try it
		if (neighbor.validNeighbor (board, player, col, row)) {
		    NeighborMove move = new NeighborMove (col, row, player);
			if (move.isValid(board)) {
				return move;
			}
		}
		
		// nothing to do!
		return null;
	}
	
	
	/**
	 * Return name for logic.
	 */
	public String toString () {
	    return "NeighborLogic";
	}

	@Override
	public String rules() {
		return "Rules:\n======\n Each player can place an X or an O in an empty " +
		"square as their first move. However, on each successive move, the player " +
		"must place a mark in an empty square that is adjacent to THEIR PREVIOUS " + 
		"MARKER. If this is not possible, that player can then choose any " +
		"available open square.";
	}
}
