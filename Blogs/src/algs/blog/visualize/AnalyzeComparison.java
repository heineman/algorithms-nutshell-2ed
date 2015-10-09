package algs.blog.visualize;

import java.util.Random;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;
import algs.model.tree.BinaryNode;
import algs.model.tree.BinaryTree;

/**
 * produce two DOT files to show the different structures created using balanced vs. unbalanced
 * trees.
 * 
 * Compute Efficiency of a binary tree as follows:
 * 
 *   #Leaf - (#innerNull / n)
 *   ------------------------
 *            #Leaf
 * 
 * @author George Heineman
 */
public class AnalyzeComparison {

	// every random number is added with next number of runs. Shows skew
	static int sequentialRuns;
	
	static BinaryTree<Integer> buildNonBalanced(int n, Random r) {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

		long start = System.currentTimeMillis();
		while (n > 0) {
			int val = Math.abs(r.nextInt());
			for (int j = 0; j <= sequentialRuns; j++) {
				bt.insert(val+j);
				n--;
			}
		}
		long end = System.currentTimeMillis();

		System.out.println("Total nonBalanced build time:" + (end - start));

		return bt;
	}

	static BalancedTree<Integer,Integer> buildBalanced(int n, Random r) {
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>();

		long start = System.currentTimeMillis();
		while (n > 0) {
			int val = Math.abs(r.nextInt());
			for (int j = 0; j <= sequentialRuns; j++) {
				bt.insert(val+j,val+j);
				n--;
			}
		}
		long end = System.currentTimeMillis();

		System.out.println("Total balanced build time:" + (end - start));

		return bt;
	}

	public static void main(String[] args) {
		// start with same random seed
		int seed = 198273;
		for (sequentialRuns = 0; sequentialRuns < 5; sequentialRuns++) {
			System.out.println(sequentialRuns);
			System.out.println();
			for (int sz = 1; sz <= 16777216; sz *= 2) {
			
				Random r1 = new Random(seed);
				Random r2 = new Random(seed);

				BinaryTree<Integer> nonb = buildNonBalanced(sz, r1);
				BalancedTree<Integer,Integer> b = buildBalanced(sz, r2);
	
				float nonbA = analyzeDepth(seed, sz, nonb);
				float bA = analyzeDepth(seed, sz, b);
				float effnonbA = efficiency(seed, sz, nonb);
				float effbA = efficiency(seed, sz, b);
				System.out.println(sz + "\t" + bA + "\t" + nonbA + "\t" + effbA + "\t" + effnonbA);
			}
			System.out.println();
		}
	}

	private static float analyzeDepth(int seed, int total, BalancedTree<Integer, Integer> b) {
		// search Times
		Random r3 = new Random(seed);
		int totalCount = 0;
		int n = total;
		while (n > 0) {
			int val = Math.abs(r3.nextInt());
			for (int j = 0; j <= sequentialRuns; j++) {
				
				int ct = 0;
				BalancedBinaryNode<Integer, Integer> node = b.root();
				while (node != null && !node.key().equals(val+j)) {
					if (val < node.key()) { node = node.left(); }
					else { node = node.right(); }
					
					ct++;
				}
				
				totalCount += ct;
				n--;
			}
		}
		
		float avg = totalCount;
		avg /= total;
		
		return avg;		
	}

	private static float analyzeDepth(int seed, int total, BinaryTree<Integer> nonb) {
		// search Times
		Random r3 = new Random(seed);
		int totalCount = 0;
		int n = total;
		while (n > 0) {
			int rnd = r3.nextInt();
			int val = Math.abs(rnd);
			for (int j = 0; j <= sequentialRuns; j++) {
				int ct = 0;
				
				BinaryNode<Integer> node = nonb.getRoot();
				while (node != null && !node.getValue().equals(val+j)) {
					if (val < node.getValue()) { node = node.getLeftSon(); }
					else { node = node.getRightSon(); }
					
					ct++;
				}
				
				totalCount += ct;
				n--;
			}
		}
		
		float avg = totalCount;
		avg /= total;
		
		return avg;		
	}

    // compute efficiency
	private static float efficiency(int seed, int total, BalancedTree<Integer, Integer> b) {
		// search Times
		long start = System.currentTimeMillis();
		Random r3 = new Random(seed);
		int numLeaf = 0;
		int numInnerNull = 0;
		
		int found = 0;
		int n = total;
		while (n > 0) {
			int rnd = r3.nextInt();
			int val = Math.abs(rnd);
			for (int j = 0; j <= sequentialRuns; j++) {
				
				BalancedBinaryNode<Integer, Integer> entry = b.getEntry(val+j);
				if (entry != null) {
					found++;
				} else {
					System.out.println("MISSING:" + (val+j));
				}
				n--;
				
				if (entry != null) {
					if (entry.left() == null && entry.right() == null) {
						numLeaf++;
					} else if (entry.left() == null) {
						numInnerNull++;
					} else if (entry.right() == null) {
						numInnerNull++;
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Total search Balanced time:" + (end - start) + " [found:" + found + "]");
		
		float eff = -numInnerNull;
		eff /= total;
		
		eff += numLeaf;
		eff /= numLeaf;
		return eff;
		
	}

	private static float efficiency(int seed, int total, BinaryTree<Integer> nonb) {
		// search Times
		long start = System.currentTimeMillis();
		Random r3 = new Random(seed);
		int numLeaf = 0;
		int numInnerNull = 0;
		
		int found = 0;
		int n = total;
		while (n > 0) {
			int rnd = r3.nextInt();
			int val = Math.abs(rnd);
			for (int j = 0; j <= sequentialRuns; j++) {
			
				// compare and locate in proper location
				BinaryNode<Integer> node = nonb.getRoot();
				while (node != null) {
					int c = node.getValue().compareTo(val+j);
				
					if (c == 0) { break; }
					
					// Search to the left: note order of compare changed above
					if (c > 0) {
						node = node.getLeftSon();
					} else {
						node = node.getRightSon();
					}
				}
				
				if (node != null) {
					found++;
				} else {
					System.out.println("MISSING:" + (val+j));
				}
				
				n--;
				if (node != null) {
					if (node.getLeftSon() == null && node.getRightSon() == null) {
						numLeaf++;
					} else if (node.getLeftSon() == null) {
						numInnerNull++;
					} else if (node.getRightSon() == null) {
						numInnerNull++;
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Total search non-Balanced time:" + (end - start) + " [found:" + found + "]");

		
		float eff = -numInnerNull;
		eff /= total;
		
		eff += numLeaf;
		eff /= numLeaf;
		return eff;
	}
}
