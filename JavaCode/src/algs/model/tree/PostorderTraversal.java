package algs.model.tree;

import algs.model.IBinaryTreeNode;

/**
 * Perform a post traversal of the tree.
 * 
 * Left - Right - Self
 * 
 * @param <T>   Type of value stored by the {@link IBinaryTreeNode} extensions.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PostorderTraversal<T extends IBinaryTreeNode<T>> extends AbstractBinaryTraversal<T> {

	/** 
	 * Start at the given node.
	 * @param node    starting node for the traversal.
	 */
	public PostorderTraversal(T node) {
		super(node);
	}

	/**
	 * Initial phase for postorder traversal is LEFT.
	 *
	 * @see AbstractBinaryTraversal#initialPhase()
	 */
	@Override
	public Phase initialPhase() {
		return left;
	}
	
	/**
	 * Final phase for postorder traversal is SELF.
	 * 
	 * @see AbstractBinaryTraversal#finalPhase()
	 */
	@Override
	public Phase finalPhase() {
		return self;
	}

	/**
	 * Advance phase to follow postorder traversal.
	 * 
	 * @see AbstractBinaryTraversal#advancePhase(algs.model.tree.AbstractBinaryTraversal.Phase)
	 */	
	@Override
	public Phase advancePhase(Phase phase) {
		if (phase == left) { return right; }
		if (phase == right) { return self; }
		
		// must be SELF, so we are done.
		return done;
	}

}
