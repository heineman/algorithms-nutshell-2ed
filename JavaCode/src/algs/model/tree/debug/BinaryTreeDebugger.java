package algs.model.tree.debug;

import algs.debug.DottyDebugger;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BinaryNode;
import algs.model.tree.IBalancedVisitor;
import algs.model.tree.IVisitor;

/**
 * Debugging subclass for binary trees.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BinaryTreeDebugger<K extends Comparable<K>,V> extends DottyDebugger implements IVisitor<K>, IBalancedVisitor<K,V> {

	/** 
	 * Default to having nodes with complex record shapes.
	 * 
	 * Subclasses should override as needed.
	 */
	public String edgeType() {
		return "dir=forward";
	}
	
	/**
	 * Visit (parent, child) pair by visiting both nodes, then add the edge. 
	 */
	public void visit(BinaryNode<K> parent, BinaryNode<K> n) {
		// if not yet seen, visit node. Only have to check parent (for root).
		if (parent != null && !nodes.contains(parent)) {
			visitNode(parent);
		}
		
		if (!nodes.contains(n)) {
			visitNode(n);
		}
		
		if (parent != null) {
			visitEdge(parent, n);		
		}
		
	}

	public void visit(BalancedBinaryNode<K,V> parent, BalancedBinaryNode<K,V> n) {
		// if not yet seen, visit node. Only have to check parent (for root).
		if (parent != null && !nodes.contains(parent)) {
			visitNode(parent);
		}
		
		if (!nodes.contains(n)) {
			visitNode(n);
		}
		
		if (parent != null) {
			visitEdge(parent, n);		
		}
	}

}
