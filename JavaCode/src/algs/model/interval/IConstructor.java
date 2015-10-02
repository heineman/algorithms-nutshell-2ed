package algs.model.interval;

import algs.model.IBinaryTreeNode;

/**
 * Interface for constructing nodes in a Segment Tree.
 * <p>
 * Exposed in this way to enable individual SegmentTrees to have nodes that
 * store different pieces of information, yet need to construct nodes as needed.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IConstructor<T extends IBinaryTreeNode<T>> {
	
	/**
	 * Instantiate the actual node.
	 * 
	 * @param left    left boundary of the range
	 * @param right   right boundary of the range
	 * @return        {@link SegmentTreeNode} object created in response to request
	 */
	SegmentTreeNode<T> construct(int left, int right);
}
