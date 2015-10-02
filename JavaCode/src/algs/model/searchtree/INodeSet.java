package algs.model.searchtree;

import java.util.Iterator;

/**
 * Defines an interface by which sets of INode objects are accessed.
 * <p>
 * Define this instead of, say, using the {@link java.util.Collection} approach 
 * because it will be simpler, and because there are some domain specific 
 * operations appropriate for sets of {@link INode} that are not immediately 
 * supported by the JDK package. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface INodeSet {

	/** 
	 * Is collection empty. 
	 * @return true if set is empty. 
	 */
	boolean isEmpty();

	/**
	 * Return the number of states in the set. 
	 * @return  size of the set. 
	 */
	int size();
	
	/**
	 * Return the actual node in storage that is equal to the given node.
	 * <p>
	 * The signature of this method may look odd. Recall that 
	 * If the node doesn't exist then return null.
	 *
	 * @param n    Board state to be inspected
	 * @return     actual node that matches the given node.
	 */
	INode contains (INode n);

	/**
	 * Return iterator to the existing board states. 
	 * @return iterator of nodes in the set. 
	 */
	Iterator<INode> iterator ();
	
	/** 
	 * Remove minimum node based on inherent behavior. Changes based on the
	 * subtype. If a queue, then removes first; If a stack, then removes topmost
	 * element on the stack.
	 * 
	 * @return minimum node from set which is removed from set.
	 */ 
	INode remove();

	/** 
	 * Remove node from node set.
	 * <p>
	 * The parameter is either an actual node in the node set or a copy of one of its nodes.
	 * 
	 * @param n    Board state to be removed
	 * @return     true if given node was removed; false otherwise.
	 */
	boolean remove(INode n);

	/**
	 * Inserts node based on inherent behavior. Changes based on the subtype. If 
	 * a queue, then added at the end; if a stack then inserted to be the topmost
	 * element on the stack.
	 * <p>
	 * It is the responsibility of the caller to ensure that the node does not already 
	 * exist in the list, using the {@link INodeSet#contains(INode)} method.
	 * 
	 * @param n    Board state to be inserted
	 */
	void insert(INode n);
}
