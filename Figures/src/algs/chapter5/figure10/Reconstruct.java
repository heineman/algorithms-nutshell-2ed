package algs.chapter5.figure10;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;
import algs.model.tree.IBalancedVisitor;

/**
 * Given a specific Red-Black tree and the initial set of elements, 
 * reconstruct the order in which the elements were inserted to produce
 * the Red-black tree.
 * 
 * This class exists because I used the following applet
 * 
 *    http://gauss.ececs.uc.edu/RedBlack/redblack.html
 * 
 * to generate the Red-black trees used for this figure, but forgot the 
 * order in which they were inserted!
 * 
 * Permutation code inspired by:
 * 
 *  http://cs.fit.edu/~ryan/java/programs/combinations/Permute-java.html
 * 
 * This is a small, neat example of putting algorithms and data structures
 * to use to solve a problem!
 * 
 * @author George Heineman
 */
public class Reconstruct {
	
	/** real numbers. */
	static final int [] base = {13, 15, 16, 17, 25, 26, 43 };
	
	/** final coloring (using IN-order traversal numbers). TRUE=black. */
	/** This matches the image of Figure 5-10. */
	static final boolean[] coloring = {false, true, false, false, true, true, true};
	
	static int root = 26;   // actual root.
	
	/** Permutation. */
	static Integer [] permutation;
	
	public static void main(String[] args) {
		
		int trials = 0;
		
		Permute p = new Permute (base);
		while (p.hasNext()) {
			trials++;
			permutation = p.next();
			
			BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>();
			for (int i = 0; i < permutation.length; i++) {
				bt.insert(permutation[i], permutation[i]);
			}
			
			if (validateTree(bt)) {
				System.out.println("At trial " + trials + ", one insertion order is: ");
				for (int e : permutation) {
					System.out.print(e + " ");
				}
				System.out.println();
				
				System.out.println(bt);
				return;
			}
		}
		
		System.out.println("Unable to recover insertion order after " + trials + " tries");
	}

	/** 
	 * Using an inorder traversal, compare the colors of the nodes against
	 * the one we want.
	 * 
	 * @param bt
	 * @return
	 */
	private static boolean validateTree(BalancedTree<Integer, Integer> bt) {
		final boolean[]color = new boolean[base.length];
		
		bt.accept(new IBalancedVisitor<Integer, Integer>() {

			int idx = 0;
			
			public void visit(BalancedBinaryNode<Integer, Integer> parent,
					BalancedBinaryNode<Integer, Integer> n) {
				
				color[idx++] = n.color();
			}
			
		});
		
		// leave early on failure.
		for (int i = 0; i < base.length; i++) {
			if (color[i] != coloring[i]) {
				return false;
			}
		}
		
		// check root
		if (bt.root().value() != root) {
			return false;
		}
		
		// We have a match!
		return true;
	}

}
