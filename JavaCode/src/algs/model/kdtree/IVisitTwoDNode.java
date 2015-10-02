package algs.model.kdtree;

/**
 * Provides interface to enable traversals over TwoD trees to be defined. 
 * <p>
 * There are two methods provided, to differentiate between the two times that 
 * nodes are visited within the kd-tree. First, during normal searching when a point
 * is found to be contained within the range query. Second when an entire subtree 
 * is found to be contained. The difference leads to great performance benefits, 
 * and the drain method designates that the node is being added "in bulk".
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IVisitTwoDNode {
	
	/**
	 * Specialized behavior during traversals for each node being visited.
	 * 
	 * @param node   node being visited.
	 */
	void visit(TwoDNode node);
	
	/** 
	 * Specialized behavior during search traversals when an entire sub-tree is visited.
	 * Typical behavior of this implementation is to immediately call visit()
	 * and then perform other computations done only when a node is drained.
	 * 
	 * @param    node whose sub-tree rooted at the node is to be visited "in bulk."
	 */
	void drain(TwoDNode node);

}
