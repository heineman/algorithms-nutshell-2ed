package algs.example.model.problems.pseudocodeExample;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Minimal problem used to test out the search algorithms from chapter 7.
 * <p>
 * A move can increment one of two values.
 * 
 * To work with the search algorithms that aim to store puzzle nodes in a tree
 * with keys, one must be able to properly compare two TinyPuzzle states 
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TinyPuzzle implements INode, Comparable<TinyPuzzle> {
	
	/** state. */
	int s[];
	
	/** Stored data. */
	Object data;
	
	/** Computed score for this node. */
	int score;
	
	/** Initialize to empty. */
	public TinyPuzzle () {
		s = new int[2];
	}
	
	/** Initialize to pre-existing board state. */
	public TinyPuzzle (int[] pre) {
		s = new int[2];
		s[0] = pre[0];
		s[1] = pre[1];
	}
	
	/** return copy of node. */
	public INode copy() {
		return new TinyPuzzle (s);
	}

	/** Ensure equivalent method based on equals. */
	public boolean equivalent(INode state) {
		return equals (state);
	}

	/** Must implement equals */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof TinyPuzzle) {
			TinyPuzzle tp = (TinyPuzzle) o;
			return tp.s[0] == s[0] && tp.s[1] == s[1];
		}
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INode#hash()
	 */
	public int hashCode() {
		long bits = java.lang.Double.doubleToLongBits(s[0]);
		bits ^= java.lang.Double.doubleToLongBits(s[1]) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}
	
	/** suitable unique key for this node. */
	public Object key() {
		return s[0] + ":" + s[1];
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
		return s[0] + "|" + s[1];
	}

	public Object storedData(Object o) {
		Object x = data;
		data = o;
		return x;
	}

	public Object storedData() {
		return data;
	}

	/** Either of two spots can be incremented. */
	public DoubleLinkedList<IMove> validMoves() {
		DoubleLinkedList<IMove> list = new DoubleLinkedList<IMove>();
			
		list.insert (new IncrementMove(0));
		list.insert (new IncrementMove(1));
		return list;
	}

	public String nodeLabel () {
		StringBuffer sb = new StringBuffer (s[0] + "|" + s[1]);
		
		// try to cross-purpose when scores are available:
		if (score != 0) { sb.append("|{").append("score: ").append(score).append("}"); }
		
		return sb.toString();
	}

	/** Needed for nodes to be placed within binary tree. */
	public int compareTo(TinyPuzzle tp) {
		if (s[0] < tp.s[0]) return -1;
		if (s[0] > tp.s[0]) return +1;
		
		if (s[1] < tp.s[1]) return -1;
		if (s[1] > tp.s[1]) return +1;
		
		return 0; // must be same!
	}
}
