package algs.model.problems.tictactoe.model;

import algs.model.gametree.IGameState;

/**
 * Place a mark on the TicTacToe Board.
 * <ul><li>A typical PlaceMark move takes a character mark and inserts
 *         it into a given (c,r) location within a TicTacToe board.
 *     <li>AF (pm) = { col and row are represented by ints } +
 *                   { mark is a char }
 * </ul>
 * 
 * <ul><li>mark != TicTacToeBoard.EMPTY </li>
 *     <li>0 &le; col &lt; number of columns in board (expected to be 3)</li>
 *     <li>0 &le; row &lt; number of rows in board (expected to be 3)</li>
 * </ul>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PlaceMark extends Move {
	/** The column to contain the mark. Available to subclasses. */ 
	protected int col;

	/** The row to contain the mark. Available to subclasses. */ 
	protected int row;

	/** The player making the move. Available to subclasses. */ 
	protected Player player;

	/**
	 * Construct a placeMark move with given (col,row) and mark to be placed.
	 * <p>
	 * REQUIRES: player.mark != ' ' AND 0 &le; col &lt; # of columns in board AND
	 *           0 &le; row &lt; # of rows in board. 
	 * @param col   desired location, column
	 * @param row   desired location, row
	 * @param player   player making the move
	 */
	public PlaceMark (int col, int row, Player player) {
		this.col = col;
		this.row = row;
		this.player = player;
	}
	
	/**
	 * Determines if move is valid.
	 * 
	 * PlaceMark is valid if board is empty at desired location.
	 */
	public boolean isValid(IGameState gameState) {
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();
		
		if (col < 0) return false;
		if (col >= board.numColumns()) return false;
		if (row < 0) return false;
		if (row >= board.numRows()) return false;
		
		return board.isClear(col, row);
	}	
	
	/**
	 * Place a mark on the TicTacToeBoard.
	 * 
	 * If move is invalid, then false is returned, otherwise the board state is updated
	 * and true is returned.
	 */
	public boolean execute(IGameState gameState) {
		// invalid PlaceMark move! 
		if (!isValid(gameState)) {
			return false;
		}
		
		// go ahead and make the move
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();
		
		// move this into the empty board
		board.set(col, row, player.getMark());
		return true;
	}

	/**
	 * Undoes the given move and returns true, or returns false if unable to undo.
	 * 
	 * @see Move#undo(IGameState)
	 */
	public boolean undo(IGameState gameState) {
		TicTacToeState state = (TicTacToeState) gameState;
		TicTacToeBoard board = state.board();
		
		// we don't have a marker here any more !? Return in error.
		if (board.get(col, row) != player.getMark()) {
			return false;
		}
		
		board.clear(col, row);
		return true;
	}
	
	/**
	 * Determine equality based on structure.
	 * 
	 * @param o     Object of class PlaceMark against which equality is being evaluated.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof PlaceMark) {
			PlaceMark po = (PlaceMark) o;
			return (po.col == col) && (po.player == player) && (po.row == row);
		}
		
		// nope
		return false;
	}
	
	/** 
	 * Return the column for this move.
	 * @return column associated with move. 
	 */
	public int getColumn () { return col; }
	
	/** 
	 * Return the row for this move.
	 * @return row associated with move. 
	 */
	public int getRow () { return row; }

	/** 
	 * Return the player for this move.
	 * @return player associated with this move. 
	 */
	public Player getPlayer() { return player; } 
	
	/**
	 * Return object in readable form.
	 */
	public String toString () {
		return "[Place " + player.getMark() + " @ (" + col + "," + row + ")]";
	}

}
