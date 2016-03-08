package algs.chapter11.table4;

import java.util.ArrayList;

/**
 * For an n-by-n board, store up to n non-threatening queens and support the 
 * search along the lines of Knuth's random walk.
 * 
 * Since it is assumed the queens are being added row by row starting from 0, we
 * only search backwards for threatening queens.
 * 
 * @author George Heineman
 */
public class Board {

	/** The board. */
	boolean [][] board;
	
	/** board size. */
	final int n;
	
	/** Temporary structure to store last set of valid positions. */
	ArrayList<Integer> nextValidRowPositions = new ArrayList<Integer>();
	
	/** Construct the n by n board. */
	public Board (int n) {
		board = new boolean[n][n];
		this.n = n;
	}
	
	/** Start with row and work upwards to see if still valid. */
	private boolean valid (int row, int col) {
		// another queen in same column, left diagonal, or right diagonal?
		int d = 0;
		while (++d <= row) { 
			if (board[row-d][col]) { return false; }
			if (col >= d && board[row-d][col-d]) { return false; }
			if (col+d < n && board[row-d][col+d]) { return false; }
		}
		return true; // OK
	}
	
	/**
	 * Find out how many valid children states are found by trying to add a queen
	 * to the given row.
	 * 
	 * Note: No error checking to ensure you aren't adding a queen to a row which
	 * already contains one. Caveat Emptor!
	 * 
	 * Returns a number from 0 to n.
	 */
	public int numChildren (int row) {
		int count = 0;
		nextValidRowPositions.clear();
		for (int i = 0; i < n; i++) {
			board[row][i] = true;
			if (valid (row, i)) {
				count++;
				nextValidRowPositions.add (i);
			}
			board[row][i] = false;
		}
		
		return count;
	}
	
	/** If no board is available then return false. */
	public boolean randomNextBoard (int r) {
		int sz = nextValidRowPositions.size();
		if (sz == 0) { return false; }
		
		// select one randomly
		int c = (int) (Math.random()*sz);
		board[r][nextValidRowPositions.get (c)] = true;
		return true;
	}
	
	/** Useful debugging function. */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (board[r][c]) { sb.append ("Q"); } else { sb.append ("."); }
			}
			sb.append ("\n");
		}
		
		return sb.toString();
	}
	
}
