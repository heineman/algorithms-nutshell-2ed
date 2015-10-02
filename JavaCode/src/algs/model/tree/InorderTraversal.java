package algs.model.tree;

import algs.model.IBinaryTreeNode;

/**
 * Perform an inorder traversal of the tree.
 * 
 * Left - Self - Right
 * 
 * @param <T>     type of the elements stored by the BinaryTree over which this traversal executes.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class InorderTraversal<T extends IBinaryTreeNode<T>> extends AbstractBinaryTraversal<T> {

	/**
	 * Start at the given node.
	 * @param node    start in order traversal at given node 
	 */
	public InorderTraversal(IBinaryTreeNode<T> node) {
		super(node);
	}

	/**
	 * Initial phase for inorder traversal is LEFT.
	 *
	 * @see AbstractBinaryTraversal#initialPhase()
	 * @return initial phase for in-order traversal, which is left
	 */
	@Override
	public Phase initialPhase() {
		return left;
	}
	
	/**
	 * Final phase for inorder traversal is RIGHT.
	 * 
	 * @see AbstractBinaryTraversal#finalPhase()
	 * @return final phase for in order traversal, which is right
	 */
	@Override
	public Phase finalPhase() {
		return right;
	}

	/**
	 * Advance phase to follow inorder traversal.
	 * 
	 * @see AbstractBinaryTraversal#advancePhase(algs.model.tree.AbstractBinaryTraversal.Phase)
	 * @param phase  current phase in traversal
	 * @return next phase for in order traversal
	 */
	@Override
	public Phase advancePhase(Phase phase) {
		if (phase == left) { return self; }
		if (phase == right) { return done; }
		
		// must be self, so go right.
		return right;
	}

}
