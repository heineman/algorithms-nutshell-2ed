package algs.model.problems.segmentIntersection;

import algs.model.tree.BalancedBinaryNode;

/**
 * The line sweep intersection algorithm stores information with internal
 * nodes, and the leaf nodes contain the actual segments.
 * <p>
 * This example shows how to "extend" a balanced binary tree class structure.
 * <p>
 * Note that the Key,Values are the same type in the Augmented node, so we
 * silently drop the use of values for the parameterizations.
 * 
 * @param <K>  The key to be used for the nodes in the tree. Note that both
 *             the key and the value of the nodes will be of type K for simplicity
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AugmentedNode<K> extends BalancedBinaryNode<K,K> {

	/** Actual line segment is found as the value for this node. */
	
	/** Minimum segment to appear in left sub-tree. */
	K    max;
	
	/** Maximum segment to appear in right sub-tree. */
	K    min;
	
	/**
	 * Construct augmented node as before.
	 * 
	 * @param key        key to be associated with value
	 * @param value      value to be associated with key
	 * @param parent     parent to which we are added
	 */
	public AugmentedNode(K key, K value, AugmentedNode<K> parent) {
		super(key, value, parent);
		
		this.max = value;
		this.min = value;
	}
	
	/** 
	 * Return the min safely. 
	 * @return minimum value associated with augmented node
	 */
	public K min() { return min; }
	
	/** 
	 * Return the max safely. 
	 * @return maximum value associated with augmented node
	 */
	public K max() { return max; }
	
	/**
	 * Helps with type casting.
	 * @return the right child.
	 */
	public AugmentedNode<K> right() {
		return (AugmentedNode<K>) right;
	}

	/**
	 * Helps with type casting.
	 * @return the left child.
	 */
	public AugmentedNode<K> left() {
		return (AugmentedNode<K>) left;
	}
	
	/**
	 * Helps with type casting.
	 * @return the parent for this node.
	 */
	public AugmentedNode<K> parent() {
		return (AugmentedNode<K>) parent;
	}
}
