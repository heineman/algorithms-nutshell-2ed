package algs.model.performance.tree;

import java.util.Iterator;

import algs.model.tree.BinaryNode;
import algs.model.tree.BinaryTree;

/**
 * Performance tests.
 * 
 * @author George Heineman
 */
public class EvaluateBinaryTreeMain  {
	

	/**
	 * Build a left-linear tree with n nodes.
	 * 
	 * This is a totally unbalanced tree starting with root and having only left children
	 * all the way to the only leaf.
	 * 
	 * @param n
	 * @return
	 */
	static BinaryTree<Integer> buildLeftLinear (int n) {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		
		for (int i = n; i > 0; i--) {
			bt.insert(i);
		}

		return bt;
	}
	
	
	/**
	 * Build a right-linear tree with n nodes.
	 * 
	 * @param n
	 * @return
	 */
	static BinaryTree<Integer> buildRightLinear (int n) {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		
		for (int i = 0; i < n; i++) {
			bt.insert(i);
		}

		return bt;
	}	
	
	/**
	 * Build a complete tree with 2^n - 1 nodes.
	 * 
	 * @param n
	 * @return
	 */
	static BinaryTree<Integer> buildComplete(int n) {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		
		// construct complete tree
		int b = (int) Math.pow(2, n-1);
		for (int i = 0; i < n; i++) {
			bt.insert(b);
			
			for (int j = 1; j <= Math.pow(2, i) - 1; j++) {
				bt.insert (b + 2*b*j);
			}
			
			b = b / 2;
		}
		
		return bt;
	}
	
	public static void main (String args[]) {
		//int n = Integer.valueOf(args[0]);
		int n = 20;
		long sum = 0;
		
		long now = System.currentTimeMillis();
		//BinaryTree<Integer> bt = buildComplete(n);
		BinaryTree<Integer> bt = buildComplete(n);
		long ending = System.currentTimeMillis();
		
		System.out.println ("create time: " + (ending-now));
		
		// traverse everything with some meaningless computation. 
		now = System.currentTimeMillis();
		for (Iterator<Integer> it = bt.inorder(); it.hasNext(); ) {
			sum += (it.next());
			sum = sum & 0xffff;
		}
		ending = System.currentTimeMillis();
		System.out.println ("inorder time: " + (ending-now));
		System.out.println ("checksum: " + sum);
		
		// recursive in-order traversal
		sum = 0;
		now = System.currentTimeMillis();
		sum = inorder(0, bt.getRoot());
		ending = System.currentTimeMillis();
		
		System.out.println ("inorder time: " + (ending-now));
		System.out.println ("checksum: " + sum);
	}


	private static long inorder(long runningSum, BinaryNode<Integer> n) {
		BinaryNode<Integer> tmp;
		if ((tmp = n.getLeftSon()) != null) {
			runningSum = inorder (runningSum, tmp); 	
		}
		
		runningSum += (Integer) n.getValue();
		runningSum = runningSum & 0xffff;
		
		if ((tmp = n.getRightSon()) != null) {
			runningSum = inorder (runningSum, tmp); 	
		}
		
		return runningSum;
	}
}
	
