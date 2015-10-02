package algs.model.problems.eightpuzzle;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Represents a node in the Eight-Puzzle space.
 * <pre>
 *    1 2 3
 *    4 5 6
 *    7 8 x
 * </pre>
 * To experiment with some of the searching algorithms, this class implements the
 * Comparable interface by simply comparing the char[][] boards. 
 * <p>
 * Note there is a static global variable {@link #debug} which controls the
 * way that {@link #nodeLabel()} computes its string value.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class EightPuzzleNode implements INode, Comparable<EightPuzzleNode> {
	/** State is going to be a two-D array of ints. */
	int [][] board = new int[3][3];
	
	/** Empty Mark. */
	public static final int EmptyMark = 0;
	
	/** Cache scoring value. */
	int score;
	
	/** Stored object for extensions. */
	Object stored;
	
	/** Max constant for number of rows. */
	public static int MaxR = 2;
	
	/** Max constant for number of columns. */
	public static int MaxC = 2;

	/** 
	 * Constructor for initiating and copying the state. 
	 *
	 * @param   b   initial board state to use.
	 */
	public EightPuzzleNode (int[][]b) throws IllegalArgumentException {
		validate(b);
		
		this.board = b;
	}
	
	/**
	 * Since this node is responsible for its formatted output in debugging,
	 * we place that localized control here. If true, then the generated 
	 * DOTTY nodes show the score with each node.
	 * 
	 * Make true for A*Search, false for DFS/BFS, when generating the images
	 * in chapter 7.
	 */
	public static boolean debug = false;
	
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
		
		EightPuzzleNode node = new EightPuzzleNode(newBoard);
		return node;
	}

	
	/** Validate the given board state. */
	private void validate(int[][] b) {
		int found[] = {0,0,0,0,0,0,0,0,0};
		
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
	 * <p>
	 * Considering the four corners of the board, select the lowest digit
	 * and then read off the remaining eight positions in a fixed order as
	 * an integer. Return that value.
	 */
	public Object key() {
		int dr = +1;
		int dc = +1;
		boolean rFirst = true;
		int d = board[0][0];
		if (board[0][2] < d) {
			dr = +1;
			dc = -1;
			d = board[0][2];
			rFirst = false;
		}
		if (board[2][2] < d) {
			dr = -1;
			dc = -1;
			d = board[2][2];
			rFirst = true;
		}
		if (board[2][0] < d) {
			dr = -1;
			dc = +1;
			d = board[2][0];
			rFirst = false;
		}
		
		StringBuilder sb = new StringBuilder(10);
		if (rFirst) {
			for (int r = -dr+1; dr*r <= dr+1; r += dr) {
				for (int c = -dc+1; dc*c <= dc+1; c += dc) {
					sb.append(board[r][c]);
				}
			}
		} else {
			for (int c = -dc+1; dc*c <= dc+1; c += dc) {
				for (int r = -dr+1; dr*r <= dr+1; r += dr) {
					sb.append(board[r][c]);
				}
			}
		}
		
		return sb.toString();
	}
	
	/** 
	 * Determine equivalence of state.
	 * 
	 * @param n    the state being compared against.
	 */
	public boolean equivalent(INode n) {
		if (n == null) { return false; }
		
		EightPuzzleNode state = (EightPuzzleNode) n;
		
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				if (board[r][c] != state.board[r][c]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** 
	 * Determine equals via equivalence of state.
	 * 
	 * @param o   object being compared against.
	 */
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof EightPuzzleNode) {
			return equivalent((EightPuzzleNode) o);
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
	 * If cached value is present, use it instead of evaluating the function again.
	 * 
	 * @return  score of the board.
	 */
	public int score() {
		return score;
	}
	
	/**
	 * External agent rates the board and stores the score here.
	 * 
	 * @param s    designated score for this node.
	 */
	public void score(int s) {
		score = s;
	}
	
	/** 
	 * Store the given piece of information with the node and return the last
	 * piece of information which had been stored with the node.
	 * <p>
	 * If null is returned, then no prior information was stored.
	 * 
	 * @param o    object to be stored with the node.
	 */
	public Object storedData(Object o) {
		Object last = stored;
		stored = o;
		return last;
	}

	/** Return the data stored with the node. */ 
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
	 * @param r   source location row
	 * @param c   source location column
	 * @return    integer contents of cell[r][c].
	 */
	public int cell(int r, int c) {
		return board[r][c];
	}
	
	/**
	 * Ensure that the empty square is in [toR][toC] and that [fromR][fromC]
	 * is adjacent horizontally or vertical.
	 * 
	 * @param fromR   source location row    
	 * @param fromC   source location column
	 * @param toR     destination location row
	 * @param toC     destination location column
	 * @return        true if empty square is in [toR][toC] and [fromR][fromC] is adjacent.
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
	 * @param fromR   source location row    
	 * @param fromC   source location column
	 * @param toR     destination location row
	 * @param toC     destination location column
	 * @return        true if able to swap locations; false otherwise.
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
	
	/** 
	 * Determine is the given (r,c) in the board is empty.
	 * 
	 * @param r    row to be checked.
	 * @param c    column to be checked.
	 * @return     true if location is empty; false otherwise.
	 */ 
	public boolean isEmpty(int r, int c) {
		return (board[r][c] == EmptyMark);
	}
	
	/** 
	 * Compute string representation.
	 * 
	 * @return meaningful string representation.
	 */
	public String toString () {
		StringBuilder sb =  new StringBuilder();
		for (int r = 0; r <= MaxR; r++) {
			for (int c = 0; c <= MaxC; c++) {
				if (board[r][c] == EmptyMark) {
					sb.append (' ');
				} else {
					sb.append (board[r][c]);
				}
			}
			sb.append ('\n');
		}
		
		return sb.toString();
	}
	
	/**
	 * Return label to appear within the debugger output. 
	 *
	 * @return 		human-readable string to use in debugging output. 
	 */
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
		if (debug) {
			sb.append("|{").append("score: ").append(score).append("}"); 
		}
		
		return sb.toString();
	}

	/**
	 * Offer rudimentary compareTo method by comparing boards.
	 * <p>
	 * based on String representation since we must be careful to ensure that
	 * a.compareTo(b) is the opposite of b.compareTo(a).
	 * <p>
	 * Needed if, for example, node is ever to appear in a data structure that
	 * stores information by comparisons.
	 * 
	 * @param n   node with which to compare.
	 */
	public int compareTo(EightPuzzleNode n) {
		return toString().compareTo(n.toString());
	}
}
