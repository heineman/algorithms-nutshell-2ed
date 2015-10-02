package algs.model.kdtree;

/**
 * Provides interface to enable traversals over KD trees to be defined.
 * <p>
 * In generic traversals (where the entire tree is visited) the {@link #visit(DimensionalNode)}
 * method is likely the only one to be invoked. However for some algorithms a particular
 * subtree rooted at a {@link DimensionalNode} needs to be processed; in this case, the
 * method to invoke is {@link #drain(DimensionalNode)}. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IVisitKDNode {
	/**
	 * Specialized behavior during traversals for each node being visited.
	 * 
	 * @param node   the node being visited.
	 */
	void visit(DimensionalNode node);

	/** 
	 * Specialized behavior during search traversals when an entire sub-tree is visited.
	 * Typical behavior of this implementation is to immediately call visit()
	 * and then perform other computations done only when a node is drained.
	 * @param node   the node being visited when an entire sub-tree is visited.
	 */
	void drain(DimensionalNode node);
}
