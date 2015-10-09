package algs.blog.searching.tree;

import algs.blog.searching.search.ICollectionSearch;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * Use a balanced binary tree to store all items.  
 * <p>
 * In the worst case, the items are inserted into the tree in alphabetic order, which 
 * is what we do in this case.
 * 
 * @author George Heineman
 */
public class BalancedTreeSearch implements ICollectionSearch<String> {

	/** Use balanced binary tree. */
	final BalancedTree<String,Boolean> tree;
	
	public BalancedTreeSearch() {
		tree = new BalancedTree<String,Boolean>();
	}
	
	/**
	 * Insert the string into our collection.
	 * <p>
	 * Note that since there are no duplicates, attempts to insert the same string
	 * multiple times are silently ignored.
	 * 
	 * @param s   string to insert into the tree.
	 */
	public void insert(String s) {
		tree.insert(s, true);
	}
	
	@Override
	public boolean exists(String target) {
		return tree.getEntry(target) != null;
	}
	
	private int totalProbes(BalancedBinaryNode<String,Boolean> node, int p) {
		if (node == null) {
			return 0;
		}
		
		return p + totalProbes(node.left(),p+1) + totalProbes(node.right(),p+1);
	}
	
	private int totalProbes() {
		BalancedBinaryNode<String,Boolean> p = tree.root();
		if (p == null) {
			return 0;
		}
		
		return 1 + totalProbes(p.left(),2) + totalProbes(p.right(),2);
	}
	
	private int depth(BalancedBinaryNode<String,Boolean> node) {
		if (node == null) {
			return 0;
		}
		
		return Math.max(1 + depth(node.left()), 1 + depth(node.right()));
	}
	
	private int depth() {
		BalancedBinaryNode<String,Boolean> p = tree.root();
		if (p == null) {
			return 0;
		}
		
		return Math.max(1 + depth(p.left()), 1 + depth(p.right()));
	}
	
	@Override
	public String toString() {
		return "Balanced Tree: Maximum depth:" + depth() + ", Average depth:" + (1.0f*totalProbes())/tree.size();
	}

}
