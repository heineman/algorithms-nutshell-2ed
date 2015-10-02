package algs.model.kdtree;

/**
 * Defines a standard <b>inorder</b> traversal of the KDTree and enables subclasses
 * to provide specialized method to take action at each node of the tree.
 * <p>
 * By referencing the tree, the traversal can be reused even when the tree
 * is altered. Note, however, that altering the tree structure <em>during</em> a
 * traversal will lead to non-determined behavior.
 * <p>
 * This traversal supports both traversals starting at the root, and those starting
 * at arbitrary points in the hierarchy of the tree (be careful that the starting node
 * is actually a member of the tree over which you traverse!).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class KDTraversal implements IVisitKDNode {
	
	/** Store the tree to be used during traversal. */ 
	KDTree tree;
	
	/** Current location in traversal. */
	DimensionalNode cursor;
	
	/** Default constructor to properly enable subclasses to work. */
	protected KDTraversal() {}
	
	/**
	 * Start traversal at the root.
	 * 
	 * @param tree    need the tree over which traversal executes.
	 */
	public KDTraversal(KDTree tree) {
		this.tree = tree;
		if (tree != null) {
			cursor = tree.getRoot();
		}
	}

	/**
	 * Start traversal at the given node within the tree rooted at tree
	 * 
	 * @param tree    need the tree over which traversal executes.
	 * @param node    node within tree at which to begin the traversal.
	 */
	public KDTraversal(KDTree tree, DimensionalNode node) {
		this.tree = tree;
		cursor = node;
	}
	
	/**
	 * Traverse starting at this given node.
	 * 
	 * node will never be null.
	 * 
	 * @param node    initiate a traversal from this node.
	 */
	private void traverse (DimensionalNode node) {
		DimensionalNode next = node.getBelow();
		if (next != null) { traverse (next); }
			
		visit (node);
		
		next = node.getAbove();
		if (next != null) { traverse (next); }
	}
	
	/**
	 * Specialized behavior will be placed here.
	 * 
	 * @param node    node being visited in the traversal.
	 */
	abstract public void visit(DimensionalNode node);
	
	/**
	 * During a regular traversal, drain is not invoked. To avoid
	 * subclasses mistakenly thinking they must provide this method,
	 * we implement here and mark as final to prevent that mistake.
	 * Since it is never called, this does nothing.
	 * @param   node     node being visited in the traversal.
	 */
	public final void drain(DimensionalNode node) { }
	
	/**
	 * Control the traversal of the entire Tree.
	 *
	 * Will visit each node in the tree starting from the cursor.
	 */
	public void traverse () {
		if (cursor == null) return;
		
		traverse(cursor);
	}
}
