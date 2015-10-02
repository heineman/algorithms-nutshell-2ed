package algs.model.tree;

/**
 * Visitor of nodes within the balanced binary tree.
 * 
 * @param <K>   keys in the tree
 * @param <V>   values in the tree (often same as K).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IBalancedVisitor<K,V> {
	
	/** 
	 * Visit a node, and keep in mind its parent.
	 * 
	 * @param parent   parent (or null if root) of node being visited.
	 * @param n        node being visited
	 */
	void visit (BalancedBinaryNode<K,V> parent, BalancedBinaryNode<K,V> n);
}
