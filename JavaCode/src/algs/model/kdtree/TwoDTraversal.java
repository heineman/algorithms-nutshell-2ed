package algs.model.kdtree;

/**
 * Defines a standard inorder traversal of the TwoDTree and enables subclasses
 * to provide specialized method to take action at each node of the tree.
 * <p>
 * By referencing the tree, the traversal can be reused even when the tree
 * is altered. Note, however, that altering the tree structure *during* a
 * traversal will lead to non-determined behavior.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class TwoDTraversal implements IVisitTwoDNode {
	
	/** Store the tree to be used during traversal. */
	TwoDTree tree;
	
	/** Default constructor to properly enable subclasses to work. */
	protected TwoDTraversal() {	}
	
	/**
	 * Start traversal at the root.
	 * 
	 * @param tree   Need to know the tree to process the traversal.
	 */
	public TwoDTraversal(TwoDTree tree) {
		this.tree = tree;
	}

	/**
	 * Traverse starting at this given node.
	 * <p>
	 * node will never be null.
	 * 
	 * @param node  node in the tree that starts the traversal
	 */
	protected void traverse (TwoDNode node) {
		TwoDNode next = node.getBelow();
		if (next != null) { traverse (next); }
			
		visit (node);
		
		next = node.getAbove();
		if (next != null) { traverse (next); }
	}
	
	/**
	 * Specialized behavior will be placed here (when visiting node).
	 * @param node    node in tree being visited during traversal
	 */
	abstract public void visit(TwoDNode node);
	
	/**
	 * This drain is never invoked during the node-by-node traversal and
	 * it is implemented here to avoid subclasses from mistakenly thinking
	 * that they need to implement it. We mark this method as final to 
	 * prevent such attempts.
	 * 
	 * @param node   Node to be drained, though this is never invoked during
	 *               the traversal.
	 */
	public final void drain(TwoDNode node) {} 
	
	/**
	 * Control the traversal of the entire Tree.
	 *
	 * Will visit each node in the tree
	 */
	public void traverse () {
		if (tree == null) return;
		
		TwoDNode node = tree.getRoot();
		if (node == null) return;
		
		traverse(node);
	}
	
}
