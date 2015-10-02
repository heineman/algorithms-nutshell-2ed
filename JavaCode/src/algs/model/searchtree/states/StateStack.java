package algs.model.searchtree.states;

import java.util.Iterator;
import java.util.Stack;

import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;

/**
 * Provide storage that behaves like a stack.
 * 
 * {@link INodeSet#insert(INode)} and {@link INodeSet#remove()} are 
 * constant time operations. However, {@link INodeSet#contains(INode)}
 * becomes O(n) since the entire stack must be inspected. Finally,
 * {@link INodeSet#remove(INode)} is O(n) since whole stack must be searched.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StateStack implements INodeSet {

	/** Stack to store state. */
	Stack<INode> stack = new Stack<INode>();

	/** 
	 * Insert pushes the element onto the stack.
	 * 
	 * @param n   the INode to store in the set.
	 */
	public void insert(INode n) {
		stack.push(n);
	}

	/** Remove takes the topmost element from the stack. */
	public INode remove() {
		return stack.pop();
	}
	
	/** Is stack empty? */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/** Determine number of states in the set. */
	public int size() {
		return stack.size();
	}
	
	/** Expose iterator to internal board states. */ 
	public Iterator<INode> iterator() {
		return stack.iterator();
	}
	
	/**
	 * A costly operation in a stack; typically not required.
	 * 
	 * @param n   the target node to be looked for.
	 */
	public INode contains(INode n) {
		for (int i = 0; i < stack.size(); i++) {
			if (stack.elementAt(i).equals(n)) {
				return stack.elementAt(i);
			}
		}
		
		return null;
	}


	/**
	 * Remove actual value from the list.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n   the node representing the value to be removed from the set.
	 */
	public boolean remove(INode n) {
		for (int i = 0; i < stack.size(); i++) {
			if (stack.elementAt(i).equals(n)) {
				stack.remove(i);
				return true;
			}
		}
		
		return false;
	}
}
