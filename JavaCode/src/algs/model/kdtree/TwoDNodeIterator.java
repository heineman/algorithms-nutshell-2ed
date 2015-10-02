package algs.model.kdtree;

import java.util.Iterator;
import java.util.Stack;

import algs.model.IPoint;

/**
 * Provides in-order traversal of 2-dimensional KD-Tree.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TwoDNodeIterator implements Iterator<IPoint> {
	
	/** Binary traversals have three phases. */
	public enum Phase { LEFT, SELF, RIGHT, DONE };
	
	public static final Phase left = Phase.LEFT;
	public static final Phase self = Phase.SELF;
	public static final Phase right = Phase.RIGHT;
	public static final Phase done = Phase.DONE;
	
	/** A moment in the computation of the traversal. */
	class Moment {
		TwoDNode node = null;
		
		Phase phase = done;
		
		Moment (TwoDNode node, Phase phase) {
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
	 * @param node    beginning node to use for traversal.
	 */
	public TwoDNodeIterator(TwoDNode node) {
		if (node == null) {
			throw new NullPointerException ("TwoDNodeIterator received null starting point");
		}
		
		stack.add(new Moment (node, left));
		
		// advance as far as we need to until we have a value to be returned.
		advance();
	}
	
	/**
	 * Advance phase to follow inorder traversal.
	 * @param  phase     current phase in the traversal (left, right, self).
	 * @return           next phase
	 */
	public Phase advancePhase(Phase phase) {
		if (phase == left) { return self; }
		if (phase == right) { return done; }
		
		// must be self, so go right.
		return right;
	}
	
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
				if (m.node.above != null) {
					m = new Moment (m.node.above, left);
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
				// must be BELOW
				if (m.node.below != null) {
					m = new Moment (m.node.below, left);
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
	 * Returns the next point in the traversal. 
	 */
	public IPoint next() {
		
		// there must be something on the stack.
		if (stack.empty()) {
			throw new java.util.NoSuchElementException("TwoDNode Traversal Iterator has no more elements");			
		}
		
		Moment m = stack.peek();
		
		// now advance.
		advance();

		// return the most recent one.
		return m.node.point;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported");
	}

}
