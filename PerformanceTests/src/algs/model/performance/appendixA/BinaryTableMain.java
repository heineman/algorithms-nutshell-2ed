package algs.model.performance.appendixA;

import java.util.Comparator;

import algs.model.tests.common.TrialSuite;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;
import algs.model.tree.BinaryNode;
import algs.model.tree.BinaryTree;

/**
 * Use -Xms256m -Xmx256m as the means to start.
 * 
 * @author George
 *
 */
public class BinaryTableMain {

	private static BalancedTree<Integer,Integer> tree;
	private static BinaryTree<Integer> btree;
	private static int n;
	private static boolean debug = false;
	
	static int countTreeComparisons (int k) {
		BinaryNode<Integer> node = btree.getRoot();
		
		int ct = 0;
		while (node != null) {
			ct++;
			if (debug) { System.out.println ("  cmp " + k + " with " + node.getValue()); }
			if (k < node.getValue()) {
				node = node.getLeftSon();
			} else if (k == node.getValue()) {
				return ct;
			} else {
				node = node.getRightSon();
			}
		}
		
		return -1;
	}
	
	static int countComparisons (int k) {
		BalancedBinaryNode<Integer,Integer> node = tree.root();
		
		int ct = 0;
		while (node != null) {
			ct++;
			if (k < node.key()) {
				node = node.left();
			} else if (k == node.key()) {
				return ct;
			} else {
				node = node.right();
			}
		}
		
		return ct;
	}
	
	public static void main (String []args) {
		TrialSuite suiteB = new TrialSuite();
		TrialSuite suiteRBC = new TrialSuite();
		TrialSuite suiteRB1toN = new TrialSuite();
		
		int numTrials = 10;
		
		
		for (int d = 16; d <= 262144; d*= 2) {
			d = 262144*4;
			n = d-1;
			System.out.println ("n:" + n);
			for (int t = 0; t < numTrials; t++) {
				System.gc();
				long now = System.currentTimeMillis();
				btree = createBalancedTree();
				long last = System.currentTimeMillis();
				btree = null;
				suiteB.addTrial(n, now, last);
				
				System.gc();
				now = System.currentTimeMillis();
				tree = createRedBlackTreeAsComplete();
				last = System.currentTimeMillis();
				tree = null;
				suiteRBC.addTrial(n, now, last);
				
				System.gc();
				now = System.currentTimeMillis();
				tree = createRedBlackTree();
				last = System.currentTimeMillis();
				tree = null;
				System.gc();
				suiteRB1toN.addTrial(n, now, last);
			}
		}
		
		System.out.println ("Complete binary tree.");
		System.out.println (suiteB.computeTable());
		
		System.out.println ("Complete balanced binary tree.");
		System.out.println (suiteRBC.computeTable());
		
		System.out.println ("Balanced binary tree 1..n");
		System.out.println (suiteRB1toN.computeTable());		
	}
	
	private static BalancedTree<Integer,Integer> createRedBlackTreeAsComplete() {
		Comparator<Integer> comp = new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
			
		};
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>(comp);
		
		// for perfect balanced tree, we must create FULL tree which is greater 
		// than n (this is 2^20).
		int k = (int) (Math.log(n)/Math.log(2));
		int b = (int) Math.pow(2, k);
		int i = 0;
		while (b > 0) {
			bt.insert(b,b);
				
			for (int j = 1; j <= Math.pow(2, i) - 1; j++) {
				bt.insert (b + 2*b*j,b + 2*b*j);
			}
			
			b = b / 2;
			i++;
		}
		
		return bt;
	}
	
	private static BinaryTree<Integer> createBalancedTree() {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		
		// for perfect balanced tree, we must create FULL tree which is greater 
		// than n (this is 2^20).
		int k = (int) (Math.log(n)/Math.log(2));
		int b = (int) Math.pow(2, k);
		int i = 0;
		while (b > 0) {
			bt.insert(b);
				
			for (int j = 1; j <= Math.pow(2, i) - 1; j++) {
				bt.insert (b + 2*b*j);
			}
			
			b = b / 2;
			i++;
		}
		
		return bt;
	}

	private static BalancedTree<Integer,Integer> createRedBlackTree() {
		Comparator<Integer> comp = new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
			
		};
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>(comp);
		
		for (int i = 1; i <= n; i++) { 
			bt.insert(i,i);
		}
		
		return bt;
	}
}
