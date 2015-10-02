package algs.model.problems.tictactoe.model;

/**
 * Represents a column, row location on the TicTacToe board.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Cell {
	
	/** The column for the board location. */
	public final int col;
	
	/** The row for the board location. */
	public final int row;
	
	/**
	 * Constructs a Cell object given a column and row.
	 * 
	 * @param c   designated column for cell
	 * @param r   designated row for cell
	 */
	public Cell (int c, int r) {
		this.col = c;
		this.row = r;
	}
	
	/** 
	 * Copy constructor.
	 * 
	 * @param cell   cell to be copied
	 */
	public Cell (Cell cell) {
		this (cell.col, cell.row);
	}
	
	/**
	 * Override equals() method from java.lang.Object.
	 */
	public boolean equals (Object o) {
		if (o == null) return false;
		
		if (o instanceof Cell) {
			Cell cell = (Cell) o;
			
			return (cell.col == col) && (cell.row == row);
		}
		
		// incomparable.
		return false;
	}
	
	/**
	 * Hashcode must be implemented if this cell is to be used in a Hashtable.
	 * @return relevant hashcode to use.
	 */
	public int hashCode() {
		return col + row;
	}
	
	/**
	 * Determines if this is adjacent to the given cell
	 * either horizontally or vertically.
	 * 
	 * Doesn't check for invalid c because we don't know the 
	 * full size of the board within Cell.
	 * 
	 * @param c   desired cell against which to compare
	 * @return    <code>true</code> if this is adjacent to c either 
	 *            horizontally/vertically; <code>false</code> otherwise.
	 */
	public boolean isAdjacent (Cell c) {
		if (equals(c)) return false;
		
		// see if we are vertically adjacent (same column)
		if (col == c.col) {
			return (Math.abs (row-c.row) == 1);
		} 

		// see if we are horizontally adjacent (same row)
		if (row == c.row) {
			return (Math.abs (col-c.col) == 1);
		}
		
		// nope
		return false;
	}
	
	/**
	 * Return representation of cell.
	 */	
	public String toString () {
		return "(" + col + "," + row + ")";
	}
}
