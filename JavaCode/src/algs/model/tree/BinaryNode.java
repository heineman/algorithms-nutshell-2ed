package algs.model.tree;

import algs.debug.IGraphEntity;
import algs.model.IBinaryTreeNode;

/**
 * Standard node for an unbalanced binary tree.
 * <p>
 * Supports ability to be part of graphical output.
 *
 * @param <T>     the base type of the values stored by the BinaryNode. Must be
 *                Comparable.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BinaryNode<T extends Comparable<T>> implements IBinaryTreeNode<T>, IGraphEntity {

	/** Left son. */
	BinaryNode<T> left;
	
	/** Right son. */
	BinaryNode<T> right;
	
	/** Value. */
	T value;
	
	/**
	 * Default BinaryTree constructor.
	 * @param value    value stored by this node. 
	 */
	public BinaryNode(T value) {
		if (value == null) {
			throw new IllegalArgumentException ("BinaryTree cannot store 'null' values.");
		}
		
		left = null;
		right = null;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.IBinaryTree#getLeftSon()
	 */
	public BinaryNode<T> getLeftSon() {
		return left;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.IBinaryTree#getRightSon()
	 */
	public BinaryNode<T> getRightSon() {
		return right;
	}
	
	/**
	 * Return the value for this node.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Return representation of this node.
	 */
	public String toString () {
		return "(" + value.toString() + ")";
	}

	/** 
	 * Node Label for binary node. 
	 *
	 * Note: Dispensed with idea of showing left/right and only focus on 
	 * the value.
	 */
	public String nodeLabel() {
		// {{left|2}|{right|3}}|{sentinel}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("{value|" + value + "}");
		sb.append("}");
		
		return sb.toString();
	}
}
