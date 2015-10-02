package algs.model.interval;

import algs.model.IBinaryTreeNode;
import algs.model.IInterval;

/**
 * Nodes of the SegmentTree are constructed from this class.
 * <p>
 * Extended classes can store additional information as well as provide their own
 * update method, as required.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SegmentTreeNode<T extends IBinaryTreeNode<T>> implements IBinaryTreeNode<SegmentTreeNode<T>>, IInterval {
	
	/** Left value for this node. */
	int left;
	
	/** Right value for this node. */
	int right;
	
	/** 
	 * Count, which is common to all potential uses of the Segment Tree and reflects the
	 * number of intervals allocated to the given node.
	 */
	int count;
	
	/** The left child (if one exists). */
	SegmentTreeNode<T> lson;
	
	/** The right child (if one exists). */
	SegmentTreeNode<T> rson;
	
	/**
	 * Construct node for this range.
	 * 
	 * @param left  left value of the range
	 * @param right right value of the range
	 * 
	 * @exception   IllegalArgumentException if interval is invalid.
	 */
	protected SegmentTreeNode (int left, int right) {
		checkInterval (left, right);
		
		this.left = left;
		this.right = right;
		this.count = 0;
	}
	
	/**
	 * Return the left value for this node.
	 */
	public int getLeft() {
		return left;
	}
	
	/**
	 * Return the right value for this node.
	 */
	public int getRight () {
		return right;
	}

	/*
     * (non-Javadoc)
     * @see algs.model.interval.IInterval#intersects(int)
     */
	public boolean intersects(int q) {
		return (left <= q) && (q < right);
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.interval.IInterval#toTheLeft(int)
	 */
	public boolean toTheLeft(int q) {
		return q < left;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.interval.IInterval#toTheRight(int)
	 */
	public boolean toTheRight(int q) {
		return q >= right;
	}
	
	/**
	 * Return the count associated with this node.
	 * 
	 * @return    integer count associated with node.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Return the left child.
	 * 
	 * @return    the left child of the node.
	 */
	public SegmentTreeNode<T> getLeftSon() {
		return lson;
	}
	
	/**
	 * Return the right child.
	 * 
	 * @return    the right child of the node.
	 */
	public SegmentTreeNode<T> getRightSon() {
		return rson;
	}
	
	/** 
	 * Determine the matching test.
	 * 
	 * Defaults to equality of the node based upon the interval. Subclasses can override, for 
	 * example, to determine if the interval is contained within the node's information, but
	 * only if information is only compared using IInterval information.
	 * 
	 * @param obj     the interval with whom we wish to match Test.
	 * @return        true if equals
	 */
	public boolean equals (Object obj) {
		if (obj == null) { return false; }
		
		if (obj instanceof IInterval) {
			IInterval interval = (IInterval) obj;
			return left == interval.getLeft() && right == interval.getRight();
		}
		
		// nope.
		return false;
	}
	
	/**
	 * Used to validate IInterval before being incorporated into this data structure.
	 * 
	 * @param interval   proposed IInterval object to be validated.
	 * 
	 * @exception  IllegalArgumentException  if (interval.getLeft() &ge; interval.getRight())
	 */
	public void checkInterval (IInterval interval) {
		int begin = interval.getLeft();
		int end = interval.getRight();
		
		if (begin >= end) {
			throw new IllegalArgumentException ("Invalid SegmentTreeNode insert: begin (" +
					begin +	") must be strictly less than end (" + end + ")");
		}
	}
	
	/**
	 * Used to validate [left, right) interval before being incorporated into this data structure.
	 * 
	 * @param begin    open left border of interval
	 * @param end      closed right border of interval
	 * 
	 * @exception  IllegalArgumentException  if (begin &ge; end)
	 */
	public void checkInterval (int begin, int end) {
		if (begin >= end) {
			throw new IllegalArgumentException ("Invalid SegmentTreeNode insert: begin (" +
					begin +	") must be strictly less than end (" + end + ")");
		}
	}
	
	/**
	 * Return smallest granularity node for the given [target,target+1)
	 * 
	 * @param target    desired value to locate
	 * @return          smallest granularity node given the target.
	 */
	public SegmentTreeNode<T> getNode (int target) {
		// have we narrowed down?
		if (left == target) {
			if (right == left+1) {
				return this;
			}
		}
		
		// press onwards
		int mid = (left+right)/2;

		if (target < mid) { return lson.getNode (target); }
		return rson.getNode (target);
	}
	
	/**
	 * Insert the given segment into the SegmentTree.
	 * 
	 * @param interval  interval segment being inserted.
	 * 
	 * @return        true if segment was updated
	 *
	 * @exception   IllegalArgumentException if interval is ill-formed.
	 */
	public boolean insert (IInterval interval) {
		checkInterval (interval);
		
		int begin = interval.getLeft();
		int end = interval.getRight();
		
		boolean modified = false;
		
		// Matching both? 
		if (begin <= left && right <= end) {
			count++;
			
			// now allow for update (overridden by subclasses)
			update(interval);
			
			modified = true;
		} else {
			int mid = (left+right)/2;

			if (begin < mid) { modified |= lson.insert (interval); }
			if (mid < end) { modified |= rson.insert (interval); }
		}
		
		return modified;
	}
	
	/**
	 * Remove the given segment from the SegmentTree.
	 * 
	 * Only removal of previously inserted intervals ensures correctness!
	 * 
	 * @param interval  interval segment being inserted.
	 * 
	 * @return          true if segment was updated
	 * @exception       IllegalArgumentException if interval is invalid.
	 */
	public boolean remove (IInterval interval) {
		checkInterval (interval);

		int begin = interval.getLeft();
		int end = interval.getRight();
		
		boolean modified = false;
		if (begin <= left && right <= end) {
			count--;

			// now allow for update (overridden by subclasses)
			dispose(interval);
			
			modified = true;
		} else {
			int mid = (left+right)/2;
			if (begin < mid) { modified |= lson.remove (interval); }
			if (mid < end) { modified |= rson.remove (interval); }
		}
		
		return modified;
	}
	
	/**
	 * Algorithms over SegmentTrees often store additional information with 
	 * each node, and may perform complex computations on insert. 
	 * 
	 * This method is overridden by subclasses as required.
	 * 
 	 * @param interval  interval segment being updated.
	 */
	protected void update(IInterval interval) {}
	
	/**
	 * Algorithms over SegmentTrees often store additional information with 
	 * each node, and may wish to clear information and/or perform computations
	 * when a segment is deleted. 
	 * 
	 * This method is overridden by subclasses as required.
	 * 
 	 * @param interval  interval segment being disposed of.
	 */
	protected void dispose(IInterval interval) {}	

	/**
	 * A shallow representation of this node. 
	 */
	public String toString() {
		return "[" + left + "," + right + ")";
	}

	/**
	 * SegmentTreeNodes have no associated value with each node.
	 */
	@Override
	public SegmentTreeNode<T> getValue() {
		throw new UnsupportedOperationException("SegmentTreeNode::getValue not supported. Use getLeft() and getRight() instead");
	}
}