package algs.model.performance.pq_random;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

public class BalancedTreePQ implements IPQueue {

	BalancedTree<Double,Integer> tree;
	
	public BalancedTreePQ() {
		tree = new BalancedTree<Double,Integer>();
	}
	
	public void insert(double priority, int item) {
		tree.insert(priority, item);
	}

	public int minimum() {
		BalancedBinaryNode<Double,Integer> node = tree.root();
		BalancedBinaryNode<Double,Integer> left = node.left();
		while (left != null) {
			node = left;
			left = left.left();
		}
		
		int item = node.value();
		tree.remove(node.key());
		return item;
	}

}
