package algs.blog.visualize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;
import algs.model.tree.BinaryNode;
import algs.model.tree.BinaryTree;
import algs.model.tree.IBalancedVisitor;

/**
 * Produce two DOT files to show the different structures created using 
 * balanced vs. unbalanced trees with random data.
 * 
 * Add in ability to have sequences where insert partially ordered data
 * to see how badly things skew.
 * 
 * @author George Heineman
 */
public class VisualizeComparison {
	// every random number is added with next number of runs. Shows skew
	static int sequentialRuns = 0;

	static BinaryTree<Integer> buildNonBalanced(int n, Random r) {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

		long start = System.currentTimeMillis();
		for (int i = 0; i < n ; i++) {
			int val = Math.abs(r.nextInt());
			bt.insert(val);
		}
		long end = System.currentTimeMillis();

		System.out.println("Total nonBalanced build time:" + (end - start));

		return bt;
	}

	static BalancedTree<Integer,Integer> buildBalanced(int n, Random r) {
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>();

		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) { 
			int val = Math.abs(r.nextInt());
			bt.insert(val,i);   // insert number for visualization later
		}
		long end = System.currentTimeMillis();

		System.out.println("Total balanced build time:" + (end - start));

		return bt;
	}

	public static void main(String[] args) {
		// start with same random seed
		Random r1 = new Random(198273);
		Random r2 = new Random(198273);

		int sz = 5000;
		BinaryTree<Integer> nonb = buildNonBalanced(sz, r1);
		BalancedTree<Integer,Integer> b = buildBalanced(sz, r2);

		output (nonb);
		output (b);		
	}


	private static void output(BalancedTree<Integer, Integer> b) {
		try {
			File out = new File ("BalancedTree.dot");
			final PrintStream ps = new PrintStream (out);
			ps.println("graph g {");
			ps.println("ordering=out; node [ shape=box style=filled label=\"\"];");

			// nodes first
			b.accept(new IBalancedVisitor<Integer, Integer>() {

				@Override
				public void visit(BalancedBinaryNode<Integer, Integer> parent,
						BalancedBinaryNode<Integer, Integer> n) {

					// output node
					String str = "black";
					if (n.color() == BalancedBinaryNode.RED) {
						str = "red";
					}
					ps.println("n" + n.value() + " [color=\"" + str + "\"]");

				}
			});

			// edges next
			b.accept(new IBalancedVisitor<Integer, Integer>() {

				@Override
				public void visit(BalancedBinaryNode<Integer, Integer> parent,
						BalancedBinaryNode<Integer, Integer> n) {

					if (parent != null) {
						ps.println("n" + parent.value() + " -- n" + n.value() + ";");
					}
				}
			});

			ps.println ("}");
			ps.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void output(BinaryTree<Integer> nonb) {
		try {
			File out = new File ("NonBalancedTree.dot");
			final PrintStream ps = new PrintStream (out);
			ps.println("graph g {");
			ps.println("ordering=out; node [ shape=box style=filled label=\"\"];");

			// nodes first
			outnodes(ps, null, nonb.getRoot());
			outedges(ps, null, nonb.getRoot());

			ps.println ("}");
			ps.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void outnodes(PrintStream ps, BinaryNode<Integer> parent, BinaryNode<Integer> node) {
		BinaryNode<Integer> son;

		if ((son = node.getLeftSon()) != null) {
			outnodes(ps, node, son);
		}

		ps.println("n" + node.getValue());

		if ((son = node.getRightSon()) != null) {
			outnodes(ps, node, son);
		}
	}

	private static void outedges(PrintStream ps, BinaryNode<Integer> parent, BinaryNode<Integer> node) {
		BinaryNode<Integer> son;

		if ((son = node.getLeftSon()) != null) {
			outedges(ps, node, son);
		}

		if (parent != null) { 
			ps.println("n" + parent.getValue() + " -- n" + node.getValue());
		}

		if ((son = node.getRightSon()) != null) {
			outedges(ps, node, son);
		}
	}


}
