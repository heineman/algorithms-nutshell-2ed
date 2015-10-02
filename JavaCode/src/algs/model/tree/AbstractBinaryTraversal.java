package algs.model.tree;

import java.util.Stack;
import java.util.Iterator;

import algs.model.IBinaryTreeNode;

/**
 * The default traversal class for IBinaryTree trees.
 * 
 * Makes it possible to expose a pre-, in-, or post-order traversal as an Iterator.
 *<p>
 * @param <T>  Any class that extends {@link IBinaryTreeNode} can be used as the 
 * structure of a Binary tree, and thus can be used as the parameter for this traversal
 * class.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class AbstractBinaryTraversal<T extends IBinaryTreeNode<T>> implements Iterator<IBinaryTreeNode<T>> {

	/** Binary traversals have three phases. */
	public enum Phase { LEFT, SELF, RIGHT, DONE };
	
	public static final Phase left = Phase.LEFT;
	public static final Phase self = Phase.SELF;
	public static final Phase right = Phase.RIGHT;
	public static final Phase done = Phase.DONE;
	
	/** A moment in the computation of the traversal. */
	class Moment {
		IBinaryTreeNode<T> node = null;
		
		Phase phase = done;
		
		Moment (IBinaryTreeNode<T> node, Phase phase) {
			this.node = node;
			this.phase = phase;
		}
	}
	
	/**
	 * Current point in the computation. 
	 *
	 * The current node is always the top one on the stack (if stack is empty we are done).
	 * 
	 * Make private so subclasses can't mess it up. 
	 */
	private Stack<Moment> stack = new Stack<Moment>();
	
	/**
	 * Start the traversal at the given node.
	 * 
	 * @param node   the starting node for the traversal.
	 */
	public AbstractBinaryTraversal(IBinaryTreeNode<T> node) {
		if (node == null) {
			throw new NullPointerException ("AbstractBinaryTraversal received null starting point");
		}
		
		stack.add(new Moment (node, initialPhase()));
		
		// advance as far as we need to until we have a value to be returned.
		advance();
	}


	/** 
	 * Return the initial phase of the traversal.
	 * 
	 * Essential for the generic traversal routine in advance()
	 * @return initial phase to use in the traversal
	 */
	public abstract Phase initialPhase();
	
	/** 
	 * Return the final phase of the traversal.
	 * 
	 * Essential for the generic traversal routine in advance()
	 * @return final phase in the traversal
	 */
	public abstract Phase finalPhase();
	
	/**
	 * Determine the next phase in the traversal.
	 * 
	 * Essential for the generic traversal routine in advance()
	 * @param p     current phase in traversal
	 * @return next phase to use in the traversal
	 */
	public abstract Phase advancePhase (Phase p);
	
	/**
	 * Advance the traversal, returning the SELF node once found or <code>null</code> when all is done.
	 * 
	 * Will never be called with empty stack.
	 */
	void advance() {
		Moment m = stack.peek();
		
		// build up stack and keep track of the phase of the computation at each of the
		// inner nodes.
		while (m != null) {
			if (m.phase == done) {
				stack.pop();
				if (stack.isEmpty()) {
					// DONE
					break;
				}   
				
				// must continue to move forward, so we update phase.
				m = stack.peek();
				m.phase = advancePhase(m.phase);
			} else if (m.phase == left) {
				if (m.node.getLeftSon() != null) {
					m = new Moment (m.node.getLeftSon(), initialPhase());
					stack.push(m);
				} else {
					// advance before returning node.
					m.phase = advancePhase (m.phase);
				}
			} else if (m.phase == self) {
				// advance before completing the phase.
				m.phase = advancePhase (m.phase);
				break;
			} else {
				// must be RIGHT
				if (m.node.getRightSon() != null) {
					m = new Moment (m.node.getRightSon(), initialPhase());
					stack.push(m);
				} else {
					// advance before returning node.
					m.phase = advancePhase (m.phase);
				}
			}
		}
	}

	/**
	 * Determines if there are more steps in the traversal.
	 */
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	/**
	 * Returns the next node in the traversal. 
	 */
	public IBinaryTreeNode<T> next() {
		
		// there must be something on the stack.
		if (stack.empty()) {
			throw new java.util.NoSuchElementException("Binary Traversal Iterator has no more elements");			
		}
		
		Moment m = stack.peek();
		
		// now advance.
		advance();

		// return the most recent one.
		return m.node;
	}

	/** Not supported. */
	public void remove() {
		throw new java.lang.UnsupportedOperationException("Binary Traversal Iterator does not support removals.");
	}
		
}
