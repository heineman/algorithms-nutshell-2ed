package algs.chapter2.table1;

import java.util.Comparator;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * Validate on large scale the results of Table 2-1.
 * 
 * Use -Xms256m -Xmx256m as the means to start.
 * 
 * @author George Heineman
 * @version 1.0, 7/17/08
 * @since 1.0
 */
public class Main {

	private static BalancedTree<Integer,Integer> tree;

	private static long sum = 0; 
	private static int []trials;
	private static int min, max, minNum, maxNum;
	private static int n = 524288;
	private static boolean debug = false;
	
	// run red/black at all?
	private static boolean runRedBlack = false;
	
	static int countTreeComparisons (int k) {
		BalancedBinaryNode<Integer, Integer> node = tree.root();
		
		int ct = 0;
		while (node != null) {
			ct++;
			if (debug) { System.out.println ("  cmp " + k + " with " + node.value()); }
			if (k < node.value()) {
				node = node.left();
			} else if (k == node.value()) {
				return ct;
			} else {
				node = node.right();
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
		trials = new int[n];
		
		if (runRedBlack) {
			long now = System.currentTimeMillis();
			tree = createRedBlackTree();
			long last = System.currentTimeMillis();
			System.out.println ("RB-tree in:" + (last-now));
			
			// now we check each one, one at a time, to validate the time to find each number
			// state the average time, as well as standard deviation.
			sum = 0;
			min = n;
			minNum = -1;
			maxNum = -1;
			max = 0;
			for (int i = 1; i <= n; i++) {
				trials[i-1] = countComparisons (i);
				
				sum += trials[i-1];
				if (trials[i-1] < min) {
					min = trials[i-1];
					minNum = i;
				} 
				if (trials[i-1] > max) {
					max = trials[i-1];
					maxNum = i;
				}
			}
			
			report();
			
			tree = null;
			sum = 0;
			System.gc();
		}
		
		
		// now create a balanced binary tree with all numbers from 1..1048576
		// This can be done since we know all numbers in the range will be
		// represented.
		long now = System.currentTimeMillis();
		tree = createRedBlackTreeAsComplete();
		long last = System.currentTimeMillis();
		System.out.println ("RB-tree in:" + (last-now));
		
		// now we check each one, one at a time, to validate the time to find each number
		// state the average time, as well as standard deviation.
		sum = 0;
		min = n;
		minNum = -1;
		maxNum = -1;
		max = 0;
		for (int i = 1; i <= n; i++) {
			trials[i-1] = countTreeComparisons (i);
			
			sum += trials[i-1];
			if (trials[i-1] < min) {
				min = trials[i-1];
				minNum = i;
			} 
			if (trials[i-1] > max) {
				max = trials[i-1];
				maxNum = i;
			}
		}

		report();

		// show some sample find.
		debug = true;
		int ct = countTreeComparisons(662563);
		System.out.println (662563 + " found in " + ct + " steps.");
		ct = countTreeComparisons(1);
		System.out.println (1 + " found in " + ct + " steps.");
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

	private static void report() {
		double mean = sum;
		mean = mean / n;
		double calc = 0;
		for (int i = 0; i < trials.length; i++) {
			calc += (trials[i] - mean)*(trials[i] - mean);
		}
		calc /= (n-1);
		calc = Math.sqrt(calc);
		
		System.out.println ("Min (" + minNum + "): " + min);
		System.out.println ("Max (" + maxNum + "): " + max);
		System.out.println ("average:" + mean);
		System.out.println ("stdev:" + calc);

	}
}
