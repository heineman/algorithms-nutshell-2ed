package algs.model.tree;

/**
 * A RightThreadedBinaryNode adds a 'thread' link to the successor node in the
 * Binary Tree for the given node.
 * 
 * @param <T>    Type of the elements stored in this Binary Tree.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class RightThreadedBinaryNode<T extends Comparable<T>> extends BinaryNode<T> {

	/** Successor node to this node in the tree. */
	RightThreadedBinaryNode<T>   thread;
	
	/**
	 * Construct a node to belong in a right-threaded binary tree.
	 * 
	 * @param value    Value to be inserted.
	 */
	@SuppressWarnings("unchecked")
	public RightThreadedBinaryNode(Comparable<T> value) {
		super((T)value);
	}

	/**
	 * Determines if this node is indeed maintaining a forward-pointer to the next in tree.
	 * 
	 * @return  <code>true</code> if this node is a right-thread node
	 */
	public boolean isThread () {
		return (thread != null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.IBinaryTree#getLeftSon()
	 */
	public RightThreadedBinaryNode<T> getLeftSon() {
		return (RightThreadedBinaryNode<T>) left;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.IBinaryTree#getRightSon()
	 */
	public RightThreadedBinaryNode<T> getRightSon() {
		return (RightThreadedBinaryNode<T>) right;
	}
	
	/**
	 * Returns the next node in the tree via the thread (or null).
	 * @return next node in the tree
	 */
	public RightThreadedBinaryNode<T> getNext() {
		return thread;
	}
	
	/**
	 * Expose threaded value in the node label.
	 * @return return DOT-enabled label. 
	 */
	public String nodeLabel() {
		RightThreadedBinaryNode<T> thread = getNext();

		// {{left|2}|{right|3}}|{sentinel}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
			sb.append("{value|" + getValue() + "}");

		sb.append("}");
		if (thread != null) {
			sb.append ("|{" + thread.getValue() + "}"); 
		}
		
		return sb.toString();
	}

}
