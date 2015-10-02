package algs.model.tree;

import algs.model.IBinaryTreeNode;

/**
 * Perform a pre traversal of the tree.
 * 
 * Self - Left - Right
 * 
 * @param <T>    Type of value associated with each {@link IBinaryTreeNode}
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PreorderTraversal<T extends IBinaryTreeNode<T>> extends AbstractBinaryTraversal<T> {

	/** 
	 * Start at the given node.
	 * @param node   starting node for the traversal. 
	 */
	public PreorderTraversal(IBinaryTreeNode<T> node) {
		super(node);
	}

	/**
	 * Initial phase for preorder traversal is SELF.
	 *
	 * @see AbstractBinaryTraversal#initialPhase()
	 * @return initial phase for preorder traversal
	 */
	@Override
	public Phase initialPhase() {
		return self;
	}
	
	/**
	 * Final phase for preorder traversal is RIGHT.
	 * 
	 * @see AbstractBinaryTraversal#finalPhase()
	 * @return final phase for preorder traversal
	 */
	@Override
	public Phase finalPhase() {
		return right;
	}

	/**
	 * Advance phase to follow preorder traversal.
	 * 
	 * @see AbstractBinaryTraversal#advancePhase(algs.model.tree.AbstractBinaryTraversal.Phase)
	 * @param phase   current phase
	 * @return next phase in traversal following current phase.
	 */
	@Override
	public Phase advancePhase(Phase phase) {
		if (phase == left) { return right; }
		if (phase == right) { return done; }
		
		// must be SELF, so we go left.
		return left;
	}

}
