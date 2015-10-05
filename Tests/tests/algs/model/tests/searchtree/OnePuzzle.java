package algs.model.tests.searchtree;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Minimal problem for testing
 * <p>
 * A move can increment one until the MAX_VALUE state has been reached.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
class OnePuzzle implements INode, Comparable<OnePuzzle> {

	/** To cause BFS to fail, we must have maximum value within memory. */
	public static final int MAX_VALUE = 10;

	/** state. */
	int s;

	/** Stored data. */
	Object data;

	/** Computed score for this node. */
	int score;

	/** Initialize to empty. */
	public OnePuzzle () {
		s = 0;
	}

	/** Initialize to pre-existing board state. */
	public OnePuzzle (int x) {
		s = x;
	}

	/** return copy of node. */
	public INode copy() {
		return new OnePuzzle (s);
	}

	/** Ensure equivalent method based on equals. */
	public boolean equivalent(INode state) {
		return equals (state);
	}

	/** Must implement equals */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof OnePuzzle) {
			OnePuzzle op = (OnePuzzle) o;
			return op.s == s;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INode#hash()
	 */
	public int hashCode() {
		long bits = java.lang.Double.doubleToLongBits(s);
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	/** suitable unique key for this node. */
	public Object key() {
		return "" + s;
	}

	/** Set score for this node. */
	public void score(int score) {
		this.score = score;
	}

	/** Return computed score for this node. */
	public int score() {
		return score;
	}

	/** Helper debugging method. */
	public String toString () {
		return "" + s;
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

		// only create moves that are valid
		if (this.s < MAX_VALUE) {
			list.insert (new PlusMove());
		}
		return list;
	}

	public String nodeLabel () {
		StringBuffer sb = new StringBuffer (s);

		// try to cross-purpose when scores are available:
		if (score != 0) { sb.append("|{").append("score: ").append(score).append("}"); }

		return sb.toString();
	}

	/** Needed for nodes to be placed within binary tree. */
	public int compareTo(OnePuzzle op) {
		return (s - op.s);
	}

}