package algs.model.problems.fifteenpuzzle;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Represents a node in the Fifteen-Puzzle space.
 * <pre>
 *    1 2 3 4
 *    5 6 7 8
 *    9101112
 *   131415 * 
 * </pre>
 * To experiment with some of the searching algorithms, this class implements the
 * Comparable interface by simply comparing the char[][] boards. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class FifteenPuzzleNode implements INode, Comparable<FifteenPuzzleNode> {
	/** State is going to be a two-D array of ints. */
	int [][] board = new int[4][4];
	
	/** Empty Mark. */
	public static final int EmptyMark = 0;
	
	/** Cache scoring value. */
	int score;
	
	/** Stored object for extensions. */
	Object stored;
	
	/** Max constants */
	public static int MaxR = 3;
	public static int MaxC = 3;

	/** 
	 * Constructor for initiating and copying the state.
	 * @param b   initial board state for fifteen puzzle. 
	 */
	public FifteenPuzzleNode (int[][]b) throws IllegalArgumentException {
		validate(b);
		
		this.board = b;
	}
	
	
	/** 
	 * Return a copy of the game state.
	 *
	 * Scoring Function is copied, but note that the storedData is not copied.
	 */
	public INode copy() {
		int[][] newBoard = new int[MaxR+1][MaxC+1];
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				newBoard[r][c] = board[r][c];
			}
		}
		
		FifteenPuzzleNode node = new FifteenPuzzleNode(newBoard);
		return node;
	}

	
	/** 
	 * Validate the board state.
	 * 
	 * An IllegalArgumentException is thrown if the board is an invalid representation
	 * of the puzzle. 
	 */
	private void validate(int[][] b) {
		int found[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		StringBuilder bs = new StringBuilder();
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				found[b[r][c]] = 1;
				bs.append(b[r][c]);
			}
			bs.append('|');
		}
		
		for (int i = 0; i < found.length; i++) {
			if (found[i] == 0) {
				throw new IllegalArgumentException ("Illegal board state:" + bs);
			}
		}
	}

	/**
	 * Return key that satisfies rotational symmetry. 
	 *
	 * Considering the four corners of the board, select the lowest digit
	 * and then read off the remaining eight positions in a fixed order as
	 * an integer. Return that value.
	 * 
	 */
	public Object key() {
		int dr = +1;
		int dc = +1;
		int offRL = +1;
		int offRH = +2;
		int offCL = +1;
		int offCH = +2;
		boolean rFirst = true;
		int d = board[0][0];
		if (board[0][MaxC] < d) {
			dr = +1;
			dc = -1;
			offCL = 2;
			offCH = 1;
			d = board[0][MaxC];
			rFirst = false;
		}
		if (board[MaxR][MaxC] < d) {
			dr = -1;
			dc = -1;
			offCL = 2;
			offCH = 1;
			offRL = 2;
			offRH = 1;
			d = board[MaxR][MaxC];
			rFirst = true;
		}
		if (board[MaxR][0] < d) {
			dr = -1;
			dc = +1;
			offRL = 2;
			offRH = 1;
			d = board[MaxR][0];
			rFirst = false;
		}
		
		StringBuilder sb = new StringBuilder(10);
		if (rFirst) {
			for (int r = -dr+offRL; dr*r <= dr+offRH; r += dr) {
				for (int c = -dc+offCL; dc*c <= dc+offCH; c += dc) {
					sb.append(board[r][c]);
				}
			}
		} else {
			for (int c = -dc+offCL; dc*c <= dc+offCH; c += dc) {
				for (int r = -dr+offRL; dr*r <= dr+offRH; r += dr) {
					sb.append(board[r][c]);
				}
			}
		}
		
		return sb.toString();
	}
	
	/** Determine equivalence of state. */
	public boolean equivalent(INode n) {
		if (n == null) { return false; }
		
		FifteenPuzzleNode state = (FifteenPuzzleNode) n;
		
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				if (board[r][c] != state.board[r][c]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** Determine equals via equivalence of state. */
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o instanceof FifteenPuzzleNode) {
			return equivalent((FifteenPuzzleNode) o);
		}

		return false;
	}

	/**
	 * Define the hashcode to be based on the key()
	 */
	@Override
	public int hashCode() { 
		return key().hashCode();
	}
	
	/**
	 * Compute the score function on the board state.
	 * <p>
	 * If cached value is present, use it instead of evaluating the functino again.
	 * 
	 * @return  score of the board.
	 */
	public int score() {
		return score;
	}
	
	/**
	 * External agent rates the board and stores the score here.
	 */
	public void score(int s) {
		score = s;
	}
	
	public Object storedData(Object o) {
		Object last = stored;
		stored = o;
		return last;
	}

	public Object storedData() {
		return stored;
	}

	/**
	 * Given the game state, return the set of valid moves.
	 */
	public DoubleLinkedList<IMove> validMoves() {
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>();
		
		// where is the blank?
		int br = -1, bc = -1;
		
		outer:
			for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				if (board[r][c] == EmptyMark) {
					br = r;
					bc = c;
					break outer;
				}
			}
		}
		
		// LEFT, UP, RIGHT, DOWN
		int deltas[][] = { { +1, 0}, {0, -1}, {-1, 0}, {0, 1}};
		
		for (int i = 0; i < deltas.length; i++) {
			int dr = deltas[i][0];
			int dc = deltas[i][1];
			
			if (0 <= br + dr && br + dr <= MaxR) {
				if (0 <= bc + dc && bc + dc <= MaxC) {
					list.insert (new SlideMove (board[br+dr][bc+dc], br+dr, bc+dc, br, bc));
				}
			}
		}
		
		return list;
	}

	/**
	 * Return contents of cell[r][c].
	 * 
	 * @param r   desired row
	 * @param c   desired column
	 * @return    contents of cell[r][c]
	 */
	public int cell(int r, int c) {
		return board[r][c];
	}
	
	/**
	 * Ensure that the empty square is in [toR][toC] and that [fromR][fromC]
	 * is adjacent horizontally or vertical.
	 * 
	 * @param fromR	  initial location, row
	 * @param fromC   initial location, column
	 * @param toR     target location, row
	 * @param toC     target location, column
	 * @return true if cells are adjacent and "to" is the empty tile.
	 */
	public boolean isAdjacentAndEmpty(int fromR, int fromC, int toR, int toC) {
		
		if (board[toR][toC] != EmptyMark) {
			return false;
		}
		
		// swap if adjacent via manhattan directions (no diagonals!)
		int dR = Math.abs(fromR-toR);
		int dC = Math.abs(fromC-toC);
		if ((dC == +1 && dR == 0) ||
			(dC == 0  && dR == +1)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Swap contents of neighboring cells. FromC must be the empty spot.
	 * 
	 * @param fromR   initial location, row
	 * @param fromC   initial location, column
	 * @param toR     destination location, row
	 * @param toC     destination location, column
	 * @return        true if swap is allowed; false otherwise.
	 */
	public boolean swap (int fromR, int fromC, int toR, int toC) {
		if (!isAdjacentAndEmpty(fromR, fromC, toR, toC)) {
			return false;
		}
		
		int tmp = board[toR][toC];
		board[toR][toC] = board[fromR][fromC];
		board[fromR][fromC] = tmp;
		return true;
	}
	
	public boolean isEmpty(int r, int c) {
		return (board[r][c] == EmptyMark);
	}
	
	/** Useful debugging method. */
	public String toString () {
		StringBuilder sb =  new StringBuilder();
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				if (board[r][c] == EmptyMark) {
					sb.append ("   ");
				} else {
					if (board[r][c] < 10) { sb.append (" "); }
					sb.append (board[r][c] + " ");
				}
			}
			sb.append ('\n');
		}
		
		return sb.toString();
	}
	
	public String nodeLabel () {
		StringBuilder sb = new StringBuilder();
		for (int c = 0; c <= MaxC; c++) {
			sb.append("{");
			for (int r = 0; r <= MaxR; r++) {
				int val = cell(r, c);
				if (val == EmptyMark) {
					sb.append(" ");
				} else {
					sb.append (val);
				}
				
				if (r <= MaxR-1) { sb.append ("|"); }
			}
			sb.append("}");
			if (c <= MaxC-1) { sb.append ("|"); }
		}
		
		// try to cross-purpose when scores are available:
		if (score != 0) { sb.append("|{").append("score: ").append(score).append("}"); }
		
		return sb.toString();
	}

	/**
	 * Offer rudimentary compareTo method by comparing boards.
	 * <p>
	 * based on String representation since we must be careful to ensure that
	 * a.compareTo(b) is the opposite of b.compareTo(a).
	 */
	public int compareTo(FifteenPuzzleNode n) {
		return toString().compareTo(n.toString());
	}
}
