package algs.blog.graph.search;

import algs.model.searchtree.INode;

/**
 * As Search Tree is explored, these visit methods are invoked.
 * 
 * @author George Heineman
 */
public interface IVisitor {
	
	/**
	 * New node uncovered in search space.
	 * 
	 * @param n    node
	 * @param id   assigned unique id
	 */
	void visitNode (INode n, int id);
	
	/**
	 * New edge uncovered in search space, from parent to child.
	 * <p>
	 * The identifiers refer to nodes that were previously visited by the
	 * {@link #visitNode(INode, int)} method.
	 * 
	 * @param parent
	 * @param child
	 */
	void visitEdge (int parent, int child);

}
