package algs.model.tree;

/**
 * Visitor of nodes.
 * 
 * @param <T>       Type of value associated with each {@link BinaryNode}
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IVisitor<T extends Comparable<T>> {
	
	/** 
	 * Visit a node, and keep in mind its parent.
	 * 
	 * @param parent   parent (or null if root) of node being visited.
	 * @param n        node being visited
	 */
	void visit (BinaryNode<T> parent, BinaryNode<T> n);
}
