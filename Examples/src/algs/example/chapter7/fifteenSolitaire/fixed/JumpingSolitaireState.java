package algs.example.chapter7.fifteenSolitaire.fixed;

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
 * TestJumping 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class JumpingSolitaireState implements INode, Comparable<JumpingSolitaireState> {

	/** 
	 * Instead of just representing the state as a two-dimensional array, we
	 * encode all possible jumps and store boolean array if specific position 
	 * is empty. Start counting at 0 from the top row moving downwards. Note that
	 * we could have constructed a search to handle any kind of Triangular game, 
	 * but we are staying focused instead on this specific puzzle.
	 */
	boolean filled[] = new boolean[15];
	
	// computed static
	public static int compMoves[][];
	
	/** Each of the triples represents a move (from, over, to). */ 
	public static final int moves[][] = new int[][] {
			{0, 1, 3},    /* from, OVER, to. */
			{0, 2, 5},
			{1, 3, 6},
			{1, 4, 8},
			{2, 4, 7},
			{2, 5, 9},
			{3, 1, 0},
			{3, 4, 5},
			{3, 6, 10},
			{3, 7, 12},
			{4, 7, 11},
			{4, 8, 13},
			{5, 2, 0},
			{5, 4, 3},
			{5, 8, 12},
			{5, 9, 14},
			{6, 3, 1},
			{6, 7, 8},
			{7, 4, 2},
			{7, 8, 9},
			{8, 4, 1},
			{8, 7, 6},
			{9, 5, 2},
			{9, 8, 7},
			{10, 6, 3},
			{10, 11, 12},
			{11, 7, 4},
			{11, 12, 13},
			{12, 7, 3},
			{12, 8, 5},
			{13, 8, 4},
			{13, 12,11},
			{14, 9, 5},
			{14, 13, 12}};
			
	/** Stored data. */
	Object data;
	
	/** Computed score for this node. */
	int score;
	
	/** Initialize to default configuration (start w/ middle open). */
	public JumpingSolitaireState () {
		for (int i = 0; i < filled.length; i++) {
			filled[i] = true;
		}
		
		filled[4] = false;
	}
	
	/** Initialize to pre-existing board state. */
	public JumpingSolitaireState(boolean[] pre) {
		for (int i = 0; i < filled.length; i++) {
			filled[i] = pre[i];
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
		String pre = "    ";
		int idx = 0;
		for (int i = 0; i < 5; i++) {
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
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>();
			
		for (int i = 0; i < moves.length; i++) {
			int from = moves[i][0];
			int over = moves[i][1];
			int to  = moves[i][2];
			
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
