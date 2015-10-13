package algs.blog.graph.freeCell;


import java.util.Comparator;

import algs.blog.graph.freeCell.solver.ISolver;
import algs.blog.graph.freeCell.solver.StandardSolver;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Represents a node in the FreeCell game.
 * <p>
 * The state of the free cell game is totally determined by:
 * 1. Contents of four free cells (order not relevant)
 * 2. The top card of the four foundation cells, ordered LEFT to RIGHT by SUIT.
 * 3. The state of eight columns of cards. While the order is not relevant, within
 *    a column, the order is extremely relevant.
 * <p>
 * Each card has 52 values. We add "zero" to mean no card, so we need to represent 53
 * distinct values. We only need 6 bits for each value.
 * <p>
 * If you order the free cell state, then 24 bits is all that you need. This fits within
 * a single integer. The same can be said of the foundation cells. 
 * <p>
 * We make the following observation: The initial longest column has eight cards. If the
 * bottom card is a king, then the MAXIMUM column size is going to be 20 cards. Most are
 * must less. Since there are 52 possible cards, and we need at least 6 bits for each
 * card, we need 52*6 = 312 bits for the maximum state representation of a single column.
 * However, while the columns are in play, the only thing you really need is the color
 * of the card and its rank. Thus we really only need 5 bits for each card. Is this 
 * relevant? Well the state now only needs 260 bits.
 * <p>
 * HERE is the mapping from card to (suit,rank).
 * 
 * 		int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
 *      int rank = 1 + ((card-1)>>2);  // rank extracted this way.
 * 
 * @author George Heineman
 */
public class FreeCellNode implements INode, Comparable<FreeCellNode> {
	/** State of board. [0-5]=card1, [6-11]=card2, [12-17]=card3, [18-23]=card4. */
	public short freeEncoding[];
	
	/** State of foundation. [0-5]=CLUB, [6-11]=DIAMOND, [12-17]=HEART, [18-23]=SPADE. */ 
	public short foundationEncoding[];
	
	/** Encodings of the columns: 8 columns, no more than 20 in each column. */
	public Column cols[];
	
	/**
	 * Instead of swapping columns for sorting purposes, this mapping contains the order. 
	 * Order is based on bottom card in each column. That's it!
	 * The order is ascending from left to right. The values in order are a permutation
	 * of the column indices {0,1,2,3,4,5,6,7}.
	 */
	int order[];
	
	/** Empty Mark. */
	public static final int EMPTY = 0;
	
	int MAX = 16777215;  // 2^24 - 1
	
	int BITS = 63;  // 6 bits 
	public static final int SIZE = 6;   // # of bits
	public static final int TWO_SIZE = 12;   // # of bits
	public static final int THREE_SIZE = 18;   // # of bits
	
	public static final int LEFT = 16515072;
	public static final int LEFT_MIDDLE = 258048;
	public static final int RIGHT_MIDDLE = 4032;
	public static final int RIGHT = 31;
	
	public static final int CLUBS = 0;
	public static final int DIAMONDS = 1;
	public static final int HEARTS = 2;
	public static final int SPADES = 3;
	
	static ISolver customSolver = new StandardSolver();
	
	public FreeCellNode (short free[], short foundation[], Column[] cols) {
		this.cols = cols;
		this.freeEncoding = free;
		this.foundationEncoding = foundation;
		
		// seed with values to start.
		order = new int[]{0,1,2,3,4,5,6,7};

		sortMap();
	}
	
	public FreeCellNode () {
		this.freeEncoding = new short[4];
		this.foundationEncoding = new short[4];
		this.cols = new Column[8];
		for (int i = 0; i < 8; i++) {
			this.cols[i] = new Column();
		}
		
		// seed with values to start. Already sorted since all empty.
		order = new int[]{0,1,2,3,4,5,6,7};
	}
	
	// ensure that mapping is accurate.
	public void sortMap() {
		for (int j = 1; j <= 7; j++) {
			int oj = order[j];
			int value = this.cols[oj].cards[0];
			
			int i = j-1;
			while (i >= 0 && this.cols[order[i]].cards[0] > value) {
				order[i+1] = order[i];
				i--;
			}
			order[i+1]=oj;
		}		
	}

	/**
	 * Return a Comparator to be used when comparing the key() values of 
	 * this node.
	 */
	public static Comparator<short[]> comparator () {
		return new Comparator<short[]>() {

			// keys are the same length
			public int compare(short[] o1, short[] o2) {
				int sz = o1.length;
				for (int i = 0; i < sz; i++) {
					if (o1[i] < o2[i]) { return -1; }
					if (o1[i] > o2[i]) { return +1; }
				}
				
				return 0;
			}
		};
	}
	
	/** 
	 * Remove the card, should it exist within the free location.
	 * NO CHECK IS MADE IF CARD iS 0 
	 */
	public int removeFree(int card) {
		if (card == freeEncoding[3]) {
			freeEncoding[3]=freeEncoding[2];
			freeEncoding[2]=freeEncoding[1];
			freeEncoding[1]=freeEncoding[0];
			freeEncoding[0]=0;
			return card;
		}
		
		if (card == freeEncoding[2]) {
			freeEncoding[2]=freeEncoding[1];
			freeEncoding[1]=freeEncoding[0];
			freeEncoding[0]=0;
			return card;
		}
		
		if (card == freeEncoding[1]) {
			freeEncoding[1]=freeEncoding[0];
			freeEncoding[0]=0;
			return card;
		}

		freeEncoding[0]=0;
		return card;
	}
	
	public boolean hasWon() {
		// all free
		for (int i = 0; i < freeEncoding.length; i++) {
			if (freeEncoding[i] != 0) { return false; }
		}
		// all kings
		for (int i = 0; i < foundationEncoding.length; i++) {
			if (foundationEncoding[i] != 13) { return false; }
		}
		
		// all empty
		for (int i = 0; i < 8; i++) {
			if (cols[i].num != 0) { return false; }
		}
		
		return true;
	}
	
	public int numberVacant() {
		int numFree = 0;
		if (freeEncoding[0] == 0) { numFree++; }
		if (freeEncoding[1] == 0) { numFree++; }
		if (freeEncoding[2] == 0) { numFree++; }
		if (freeEncoding[3] == 0) { numFree++; }
		
		// must check each one since we may no longer be sorted...
		for (int i = 0; i < 8; i++) {
			if (cols[i].num == 0) { numFree++; } 
		}
		
		return numFree;
	}
	
	/**
	 * 
	 * 
	 * ONLY CALL SHOULD YOU KNOW THAT THERE IS INDEED A FREE SPOT.
	 * @param card
	 */
	public void insertFree(short card) {
		if (card > freeEncoding[3]) {
			freeEncoding[0] = freeEncoding[1];
			freeEncoding[1] = freeEncoding[2];
			freeEncoding[2] = freeEncoding[3];
			freeEncoding[3] = card;
			return;
		}

		if (card > freeEncoding[2]) {
			freeEncoding[0] = freeEncoding[1];
			freeEncoding[1] = freeEncoding[2];
			freeEncoding[2] = card;
			return;
		}
		
		if (card > freeEncoding[1]) {
			freeEncoding[0] = freeEncoding[1];
			freeEncoding[1] = card;
			return;
		}
		
		// last place it can go (ZERO). Just insert
		freeEncoding[0] = card;
	}
	
	
	/**
	 * Since we order the free cells, left to right, then we need 
	 * only check the first one to see if there are any free. Once this
	 * first one is taken, we know all are.
	 */
	public boolean availableFree() {
		return (freeEncoding[0] == 0);
		
	}
	
	
	
	/**
	 * No check is made to ensure this is even legal. Returns the rank of the 
	 * card removed from the specific suit pile. 
	 * 
	 * BE CAREFUL
	 * @param card
	 */
	public int removeFoundation (int suit) {
		int rank = foundationEncoding[suit];
		if (rank == 0) { return 0; }
		return foundationEncoding[suit]--;
	}
	
	/**
	 * No check is made to ensure this is even legal. BE CAREFUL
	 * @param card
	 */
	public void insertFoundation (short card) {
		short suit = (short)(((card-1)%4));       // subtract 1 since '0' is invalid card.
		short rank = (short)(1 + ((card-1)>>2));  // rank extracted this way.
		
		foundationEncoding[suit]=rank;
	}
	
	/** Cache scoring value. */
	int score;
	
	/** Stored object for extensions. */
	Object stored;
	
	/** Max constant for number of rows. */
	public static int MaxR = 2;
	
	/** Max constant for number of columns. */
	public static int MaxC = 2;

	
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
		
		// On copy, just reuse pointers. When making a move, we detach and copy
		// only then.
		Column[] copy = new Column[8];
		for (int i = 0; i < 8; i++) {
			copy[i] = cols[i].copy();
		}
		short []freeCopy = new short[4];
		short []foundationCopy = new short[4];
		for (int i = 0; i < 4; i++) {
			freeCopy [i] = freeEncoding[i];
			foundationCopy[i] = foundationEncoding[i];
		}
		FreeCellNode node = new FreeCellNode(freeCopy, foundationCopy, copy);
		return node;
	}

	/**
	 * Return raw key that simply reports full state as array of short.
	 * <p>
	 * Useful when analyzing and comparing board states with each other.
	 */
	public short[] rawkey() {
		// must allow for up to 19 for each column (8*19=152). Plus 8 for
		// Free and Foundation cells.
		short key[] = new short[160];
		
		int idx = 0;
		for (int i = 0; i < 4; i++) { 
			key[idx++] = freeEncoding[i];
		}
		for (int i = 0; i < 4; i++) {
			key[idx++] = foundationEncoding[i];
		}
		
		// place in order.
		for (int i = 0; i < cols.length; i++) {
			Column col = cols[i];  // UNORDERED
			int sz = col.num;
			for (int j = 0; j < sz; j++) {
				key[idx++] = col.cards[j];
			}
			while (sz < 19) {
				key[idx++] = 0;
				sz++;
			}
		}
		
		return key;
	}

	/**
	 * Return key that satisfies symmetry since columns are ordered. 
	 * <p>
	 * Considering the four corners of the board, select the lowest digit
	 * and then read off the remaining eight positions in a fixed order as
	 * an integer. Return that value.
	 * <p>
	 * We MUST insert delimiter "-1" characters between column representations,
	 * otherwise we might mistakenly feel we have visited the same state.
	 * This actually can happen!
	 */
	public Object key() {
		// must allow for up to 8 from foundation/free. Wastes some space.
		// add column delimiters
		short key[] = new short[69];
		
		int idx = 0;
		for (int i = 0; i < 4; i++) { 
			key[idx++] = freeEncoding[i];
		}
		for (int i = 0; i < 4; i++) {
			key[idx++] = foundationEncoding[i];
		}
		
		// place in order.
		for (int i = 0; i < cols.length; i++) {
			Column col = cols[order[i]];
			int sz = col.num;
			for (int j = 0; j < sz; j++) {
				key[idx++] = col.cards[j];
			}
			key[idx++] = -1;
		}
		
		return key;
	}
	
	/** 
	 * Determine equivalence of state.
	 * 
	 * @param n    the state being compared against.
	 */
	public boolean equivalent(INode n) {
		if (n == null) { return false; }
		
		FreeCellNode state = (FreeCellNode) n;
		for (int i = 0; i < 4; i++) {
			if (state.freeEncoding[i] != freeEncoding[i]) return false;
			if (state.foundationEncoding[i] != foundationEncoding[i]) return false;
		}
		
		
		// intermediate. If any of the bottom cards don't match, leave
		for (int i = 0; i < 8; i++) {
			if (state.cols[order[i]].cards[0] != cols[order[i]].cards[0]) {
				return false;
			}
		}
		
		// Oh well, now we try all cards.
		short[]key1 = (short[])state.key();
		short[]key2 = (short[])this.key();
		
		for (int i = 0; i < key1.length; i++) {
			if (key1[i] != key2[i]) { return false; }
		}
		
		// must be the same!
		return true;
	}
	
	/** 
	 * Determine equals via equivalence of state.
	 * 
	 * @param o   object being compared against.
	 */
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof FreeCellNode) {
			return equivalent((FreeCellNode) o);
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
	 * Return computed score.
	 * <p>
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

	
	
	/** Useful debugging method. */
	public String toString () {
		StringBuilder sb =  new StringBuilder();
		for (int i = 3; i>=0; i--) {
			sb.append(out(freeEncoding[i]));
		}
		sb.append("       ");
		for (int i = 0; i<4; i++) {
			sb.append(out2(i, foundationEncoding[i]));
		}
		sb.append ("\n");
		boolean hasSome = true;
		int r = 0;
		while (hasSome) {
			hasSome = false;
			for (int c = 0; c < 8; c++) {
				//if (cols[order[c]].num > r) {
				if (cols[c].num > r) {
					//int card = cols[order[c]].cards[r];
					int card = cols[c].cards[r];
					sb.append (out(card) + " ");
					hasSome = true;
				} else {
					sb.append ("   ");
				}
			}
			sb.append("\n");
			r++;
		}
		
		return sb.toString();
	}

	static StringBuilder scratchOut = new StringBuilder("  ");
	static StringBuilder blank = new StringBuilder("  ");
	static final String ranks = ".A23456789TJQK";
	static final String suits = "CDHS";
	public static final StringBuilder out(int card) {
		if (card == 0) {
			return blank;
		}
		
		int rs = 1 + ((card-1)>>2);
		int ss = ((card-1)%4);
		
		try {
			scratchOut.setCharAt(0, ranks.charAt(rs));
			scratchOut.setCharAt(1, suits.charAt(ss));
		} catch (Exception e) {
			System.out.println("BAD");
			e.printStackTrace();
		}
		return scratchOut;		
	}

	public static short fromCard(String c) {
		int r = ranks.indexOf(c.charAt(0));
		int s = suits.indexOf(c.charAt(1));
		return (short)(((r-1)*4) + 1 + s);
	}
	
	// same but this time with limited info.
	public static StringBuilder out2(int suit, int rank) {
		if (rank == 0) { return blank; }
		scratchOut.setCharAt(0, ranks.charAt(rank));
		scratchOut.setCharAt(1, suits.charAt(suit));
		return scratchOut;	
	}
	
	/** Return label to appear within the debugger output. */
	public String nodeLabel () {
		return "node-label";
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
	public int compareTo(FreeCellNode n) {
		return toString().compareTo(n.toString());
	}
	
	/**
	 * Choose a different solving method to replace {@link StandardSolver}.
	 * 
	 * @param solver     new solving method to use.
	 */
	public static void setSolvingMethod (ISolver solver) {
		customSolver = solver;
	}
	

	@Override
	public DoubleLinkedList<IMove> validMoves() {
		return customSolver.validMoves(this);
	}
}
