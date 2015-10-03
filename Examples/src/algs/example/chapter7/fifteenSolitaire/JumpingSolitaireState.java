package algs.example.chapter7.fifteenSolitaire;

import java.util.Comparator;
import java.util.Hashtable;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * A popular solitaire game (found at Cracker Barrel restaurants around the
 * United states!) is formed by a triangular piece of wood with fifteen holes
 * in it forming a pattern of five rows: 
 * 
 * <pre>
 *       o
 *      o o
 *     o o o
 *    o o o o
 *   o o o o o
 * </pre>
 * 
 * All but one of the holes is filled with a golf tee. You can move a tee by
 * jumping it over an existing tee to land in an empty hole. The goal is to
 * remove all Tees.
 * <p>
 * This looks like it could be solved by depth-first search since the number
 * of moves at any time is bounded and changes decrementally as the game 
 * progresses.  Even if there are thirteen moves to make with 3 moves allowed
 * at each turn, this only means 3^14 or 4,782,969 possible moves. Certainly
 * doable on modern machines.  We only need to determine the initial state and
 * goal state. Now here it gets tricky since these are both up to the player.
 * That is, the only goal is to produce just a single Tee standing. We start
 * by trying for the "Bingo!" on the first pass, that is, start with the initial
 * state with the middle hole open (the third one on the third row) and try to
 * get the last Tee to end up there.
 * 
 * <b>Steps to an implementation:</b>
 * <ul>
 * <li> Develop the class implementing INode. For inspiration, check out the 
 * existing ones in the repository (EightPuzzle, TinyPuzzle). This class
 * (JumpingSolitaireState) handles it.
 * <ol>
 * <li>Write constructor (both default and with pre-filled values). This is
 * dependent upon storing the state simply as a boolean[] array of filled values
 * for each peg location. Note how we reify the moves ONCE and use this for 
 * the validMoves() method. Also note that we could generalize this to any
 * triangular puzzle (or any other shape for that matter!) but this is for
 * a future extension by the interested reader!
 * <li>Write copy() method to invoke the obvious constructor.
 * <li>Write equals() and equivalent() methods using the state.
 * <li>Provide default implementations for score(int),score()
 * <li>Create key() from the filled array.
 * <li>Provide default storedData(),storedData(Object) implementations.
 * <li>Provide reasonable toString() that tries to show triangle (but mainly fails)
 * <li>Provide validMoves method that traverses all available moves to see which
 *     ones are legal 
 * <li>nodeLabel() is used during the DOTTY generation of graph images. For now
 *     this is not an issue, so provide some meaningless behavior.
 * <li>Important: If this class is ever to be stored within a binary tree, then
 * the key() method must be meaningful and the compareTo(JumpingSolitaireState)
 * method must also be provided.
 * <li>Important: If this class is ever to be stored within a hash table, then
 * the hashCode() method must meaningful and correct.
 * </ol>
 * </ul>
 * 
 * That's it. This implementation took about 30 minutes. Now let's try it out.
 * This is all accomplished in the {@link TestJumping} class which executes an
 * unbounded DFS on the search tree. 
 * <p>
 * Ok, so fail on first guess (0 open, 1651 closed). Try to see if we end up
 * with a peg in the corner (0, 10, 14). These fail as well. Ok, so now let's
 * try to end at any old location (1,2,3,5,6-9,11-13). Place in loop and see
 * what happens (this is all done in TestJumping class). Aha! Found it. You
 * end up with a single peg in the 12th spot (middle of lowest row). QED.
 * Can we ever end up with a peg in the middle? Look at second test case in
 * TestJumping that shows this can be done if start at
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class JumpingSolitaireState implements INode, Comparable<JumpingSolitaireState> {

	// same order (NW=0) as shown in arrays below
	public static final int NW = 0;
	public static final int NE = 1;
	public static final int  E = 2;
	public static final int SE = 3;
	public static final int SW = 4;
	public static final int  W = 5;
	
	public final int k;   // number of pegs
	public final int r;   // width of one side.
	
	/** 
	 * Instead of just representing the state as a two-dimensional array, we
	 * encode all possible jumps and store boolean array if specific position 
	 * is empty. Start counting at 0 from the top row moving downwards. Note that
	 * we could have constructed a search to handle any kind of Triangular game, 
	 * but we are staying focused instead on this specific puzzle.
	 */
	boolean filled[];
	
	// computed static
	public static int compMoves[][] = null;
			
	/** Stored data. */
	Object data;
	
	/** Computed score for this node. */
	int score;
	
	/** If choose to order the moves, set that comparator. */
	static Comparator<IMove> order = null;
	
	/** Initialize to default configuration (start w/ middle of third open). */
	public JumpingSolitaireState (int n) {
		if (n < 10) {
			throw new IllegalArgumentException ("invalid triangle problem size. Must be >= 10");
		}

		k = n;
		filled = new boolean[k];
		for (int i = 0; i < k; i++) {
			filled[i] = true;
		}
		
		filled[4] = false;
		r=1+(int)(Math.ceil(Math.sqrt(k)));
		
		computeMoves();
	}
	
	/** Initialize to pre-existing board state. */
	public JumpingSolitaireState(boolean[] pre) {
		if (pre.length < 10) {
			throw new IllegalArgumentException ("invalid triangle problem size. Must be >= 10");
		}

		k = pre.length;
		
		// if k is a perfect square (does happen!) we must add one more
		int adjust = 0;
		if ((int)(Math.sqrt(k))*(int)(Math.sqrt(k)) == k) {
			adjust = 1;
		}
		
		r=adjust + 1+(int)(Math.ceil(Math.sqrt(k)));
		

		filled = new boolean [k];
		for (int i = 0; i < filled.length; i++) {
			filled[i] = pre[i];
		}
		computeMoves();
	}
	
	/**
	 * Compute allowed moves for all positions.
	 * 
	 * There are seven distinct regions of the board with specific
	 * move types. Likely we could take advantage of symmetry to break
	 * this down to three, but I just want to code this up. 
	 *   
	 *         0                   A            A: SE, SW only
	 *        1 2                 A A           B: NE, E, SE, SE
	 *       3 4 5               B A C          C: NW, W, SW, SE
	 *      6 7 8 9             B B C C         D: NW, NE, E, SE, SW, W
	 *     0 1 2 3 4           B B D C C        E: NE, E
	 *    5 6 7 8 9 0         E E F F G G       F: W, NW, NE, E
	 *   1 2 3 4 5 6 7       E E F F F G G      G: W, NW
	 *      
	 */    
	private void computeMoves() {
		// (r+1)*r/2 = filled.length; we thus determine the width of triangle
		if (compMoves != null) { return; } // done!
		
		// compute up to six possible moves for each board. Strip away the
		// meaningless ones after we have computed all.
		int idx = 0;
		Hashtable<Integer,int[]> entries = new Hashtable<Integer,int[]>();
		for (int i = 0; i < r; i++) {
			for (int j = 0; j <= i; j++) {
				int ids[] = new int[] {
					idx, idx - (i+1), idx - 2*i - 1,    // North-West
					idx, idx - i, idx - 2*i + 1,        // North-East
					idx, idx + 1, idx + 2,              // East
					idx, idx + (i+2), idx + 2*i + 5,    // South-East
					idx, idx + (i+1), idx + 2*i + 3,    // South-West
					idx, idx -1, idx -2,                // West
				};
				
				entries.put(idx, ids);
				idx++;
			}
		}
		
		// each region removes invalid moves. We know k >= 10
		int numValidMoves = 6 * k; 
		boolean[] adjusted = new boolean[k];  // all false
			
		// A: 0 1 2 4 (SE/SW)
		for (int i : new int []{0,1,2,4}) {
			int[] a = entries.get(i);
			a[3*NW]=-1; a[3*NE] = -1; a[3*E] = -1; a[3*W] = -1;
			numValidMoves -= 4;
			adjusted[i] = true;
		}
		
		// B: 3, 6, 10, ... k-3*r+3   (NE, E, SE, SW)
		// B: 7, 11, ..., k-3*r+4
		int numAdj = 0;
		for (int d=3, i=3; i <= k-3*r+3; i+=d, d++) {
			if (adjusted[i]) continue;
			
			int[] b = entries.get(i);
			b[3*NW] = -1; b[3*W] = -1;
			numValidMoves -= 2;
			adjusted[i] = true;
			numAdj++;
		}
		if (numAdj > 1) {  // might not be big enough.... 
			for (int d=4, i=7; i <= k-3*r+4; i+=d, d++) {
				if (adjusted[i]) continue;
	
				int[] b = entries.get(i);
				b[3*NW] = -1; b[3*W] = -1;
				numValidMoves -= 2;
				adjusted[i] = true;
			}
		}		
		// C: 5, 9, 14, ... k-2*r   (NW, SE, SW, W)
		// C: 8, 13, ..., k-2*r-1
		for (int d=4, i=5; i <= k-2*r; i+=d, d++) {
			if (adjusted[i]) continue;

			int[] c = entries.get(i);
			c[3*NE] = -1; c[3*E] = -1;
			numValidMoves -= 2;
			adjusted[i] = true;
		}
		if (numAdj > 1) {
			for (int d=5, i=8; i <= k-2*r-1; i+=d, d++) {
				if (adjusted[i]) continue;
	
				int[] c = entries.get(i);
				c[3*NE] = -1; c[3*E] = -1;
				numValidMoves -= 2;
				adjusted[i] = true;
			}
		}
		
		// E: NE, E     (NE, E)
		for (int i : new int []{k-r, k-r+1, k-2*r+1, k-2*r+2}) {
			if (adjusted[i]) continue;

			int[] e = entries.get(i);
			e[3*NW]=-1; e[3*SE] = -1; e[3*SW] = -1; e[3*W] = -1;
			numValidMoves -= 4;
			adjusted[i] = true;
		}
		
		// F: k-2r+3 .. k-r-3      (NW, NE, E, W)
		// F: k-r+2  .. k-3
		for (int i=k-r+2; i <= k-3; i++) {
			if (adjusted[i]) continue;

			int[] f = entries.get(i);
			f[3*SE] = -1; f[3*SW] = -1;
			numValidMoves -= 2;
			adjusted[i] = true;
		}
		if (numAdj > 1) {
			for (int i=k-2*r+3; i <= k-r-3; i++) {
				if (adjusted[i]) continue;
	
				int[] f = entries.get(i);
				f[3*SE] = -1; f[3*SW] = -1;
				numValidMoves -= 2;
				adjusted[i] = true;
			}
		}
				
		// G: NW, W
		for (int i : new int []{k-r-2, k-r-1, k-2, k-1}) {
			if (adjusted[i]) continue;

			int[] g = entries.get(i);
			g[3*NE]=-1; g[3*E] = -1; g[3*SE] = -1; g[3*SW] = -1;
			numValidMoves -= 4;
			adjusted[i] = true;
		}
		
		// FINALLY we go through each one and compute valid moves, to be 
		// store in compMoves as {from, over, to}.
		idx = 0;
		compMoves = new int[numValidMoves][3];
		int vm = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j <= i; j++) {
				int[] e = entries.get(idx);
				
				// see if any are valid. If So, CONSTRUCT
				for (int m = 0; m < 6; m++) {
					if (e[3*m] == -1) continue;   // invalid!
					
					compMoves[vm][0] = e[3*m];    // from
					compMoves[vm][1] = e[3*m+1];  // over
					compMoves[vm][2] = e[3*m+2];  // to
					vm++;
				}
				idx++;
			}
		}
	}
	
	/** return copy of node. */
	public INode copy() {
		return new JumpingSolitaireState (filled);
	}

	/** Ensure equivalent method based on equals. */
	public boolean equivalent(INode state) {
		return equals (state);
	}

	/** Must implement equals */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof JumpingSolitaireState) {
			JumpingSolitaireState jss = (JumpingSolitaireState) o;
			for (int i = 0; i < filled.length; i++) {
				if (filled[i] != jss.filled[i]) {
					return false;
				}
			}
			return true;  // ALL MATCH!
		}
		
		return false;  // NOPE.
	}
	
	/** suitable unique key for this node. */
	public Object key() {
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < filled.length; i++) {
			if (filled[i]) { key.append ('T'); } else { key.append('F'); }
		}
		return key.toString();
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		int pow = 1;
		for (int i = 0; i < filled.length; i++) {
			if (filled[i]) {
				sum += pow;
			}
			pow *= 2;
		}
		
		return sum;
	}
	
	/** Set score for this node. */
	public void score(int s) {
		score = s;
	}

	/** Return computed score for this node. */
	public int score() {
		return score;
	}
	
	/** Helper debugging method. */
	public String toString () {
		StringBuilder sb = new StringBuilder();
		String pre = " ";
		for (int i = 0; i < r; i++) {
			pre += " ";
		}
		
		int idx = 0;
		int i = 0;
		while (idx < filled.length) {
			sb.append(pre);
			for (int j = 0; j <= i; j++) {
				if (filled[idx++]) {
					sb.append('*');
				} else {
					sb.append('.');
				}
				sb.append(' ');
			}
			sb.append('\n');
			
 		    // advance row
			i++;
			if (pre.length() != 0) {
				pre = pre.substring(1); // trim by one
			}
		}
		
		return sb.toString();
	}

	public Object storedData(Object o) {
		Object x = data;
		data = o;
		return x;
	}

	public Object storedData() {
		return data;
	}

	/** Run through all moves and add triple if available. */
	public DoubleLinkedList<IMove> validMoves() {
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>(order);
			
		for (int i = 0; i < compMoves.length; i++) {
			int from = compMoves[i][0];
			int over = compMoves[i][1];
			int to  = compMoves[i][2];
			
			if (filled[from] && filled[over] && !filled[to]) {
				list.insert (new JumpMove (from, over, to));
			}
		}
		
		return list;
	}

	// GRAPHICAL representation hard since this is triangle. Avoiding for now
	public String nodeLabel () {
		return (String) key() + "|{" + score + "}";
	}

	/** Support some meaningful comparison. */
	public int compareTo(JumpingSolitaireState jss) {
		for (int i = 0; i < filled.length; i++) {
			if (!filled[i] && jss.filled[i]) { return -1; }
			if (filled[i] && !jss.filled[i]) { return +1; }
		}
		
		return 0; // must be same!
	}
}
