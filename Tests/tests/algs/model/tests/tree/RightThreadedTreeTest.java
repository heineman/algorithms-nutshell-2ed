package algs.model.tests.tree;


import java.util.Iterator;
import junit.framework.TestCase;
import org.junit.Test;

import algs.model.tree.RightThreadedBinaryNode;
import algs.model.tree.RightThreadedBinaryTree;

/**
 * Test cases for the Right-Threaded Binary Tree
 * 
 * @author George Heineman
 */
public class RightThreadedTreeTest extends TestCase {
	
	@Test 
	public void testArtificial() {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
			
		// validate that it can handle accept on a tree with an empty root.
		bt.accept(null);
		
		bt.insert(8);
		RightThreadedBinaryNode<Integer> node = bt.getRoot();
		assertTrue (8 == node.getValue());
		
		RightThreadedBinaryNode<Integer> next = node.getNext();
		assertEquals (RightThreadedBinaryTree.sentinel, next.toString());
		
	}

	/**
	 * Build a left-linear tree with n nodes.
	 * 
	 * This is a totally unbalanced tree starting with root and having only left children
	 * all the way to the only leaf.
	 * 
	 * @param n
	 * @return
	 */
	public static RightThreadedBinaryTree<Integer> buildLeftLinear (int n) {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
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
	public static RightThreadedBinaryTree<Integer> buildRightLinear (int n) {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
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
	public static RightThreadedBinaryTree<Integer> buildComplete(int n) {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
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
	
	/**
	 * Ensure empty trees are properly validated.
	 *
	 */
	@Test
	public void testEmpty() {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
		assertFalse (bt.inorder().hasNext());
		assertFalse (bt.preorder().hasNext());
		assertFalse (bt.postorder().hasNext());
		assertFalse (bt.iterator().hasNext());
		
		// doesn't matter where this is...
		assertEquals (-1, bt.validateArtificialRoot());
		
		try {
			bt.inorder().next();
			fail ("Shouldn't be possible to extract next element from empty iterator.");
		} catch (java.util.NoSuchElementException nsee) {
			
		}
		
		try {
			bt.inorder().remove();
			fail ("Shouldn't be possible to remove from empty iterator.");
		} catch (UnsupportedOperationException uoe) {
			
		}
		
		assertEquals ("()", bt.toString());
		assertNull(bt.getRoot());
		assertFalse (bt.member(9));
	}
	
	
	/**
	 * Validate removal of invalid entries.
	 *
	 */
	@Test
	public void testInvalidRemovals () {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
		assertFalse (bt.remove(99));
		
		bt = buildComplete(4);
		
		
		assertFalse (bt.remove(99));  // not present
		assertFalse (bt.remove(-2));  // not present
	}
	
	/**
	 * Test that duplicates are allowed.
	 *
	 */
	@Test
	public void testDuplicates () {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
		bt.insert(9);
		bt.insert(11);
		bt.insert(9);
		bt.insert(9);
		
		assertEquals ("((9)(((9)((9)))(11)))", bt.toString());
		
	}
	
	/**
	 * Test special delete cases
	 *
	 */
	@Test
	public void testSpecialRemovals () {
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
		bt.insert(11);
		bt.insert(9);
		bt.insert(13);
		bt.insert(7);
		bt.insert(15);
		
		assertTrue (bt.remove(9));     // remove child with only a left son
		assertTrue (bt.remove(13));    // remove child with only a right son
		
		assertFalse (bt.member(9));    // validate no longer in tree
		assertFalse (bt.member(13));   // validate no longer in tree
		assertTrue  (bt.member(7));    // this one is still there.
		
		assertFalse (bt.remove(9));    // duplicate deletes not possible
		assertFalse (bt.remove(13));   // duplicate deletes not possible
		
		assertFalse (bt.remove(14));  // not present
		assertFalse (bt.remove(1));   // not present
		assertFalse (bt.remove(16));  // not present
		assertFalse (bt.remove(12));  // not present
	}
	
	/**
	 * Validate the construction of a complete tree.
	 *
	 */
	@Test
	public void testComplete() {
		// number of levels
		int n = 4;
		
		RightThreadedBinaryTree<Integer> bt = buildComplete(n);
		
		assertEquals ("(((((1))(2)((3)))(4)(((5))(6)((7))))(8)((((9))(10)((11)))(12)(((13))(14)((15)))))", bt.toString());
		
		// remove all nodes in increasing order.
		for (int i = 1; i < Math.pow(2, n); i++) {
			bt.remove(i);
		}
		
		assertEquals ("()", bt.toString());
		
		// now do in decreasing order
		bt = buildComplete(n);
		
		// remove all nodes in increasing order.
		for (int i = (int) (Math.pow(2, n) - 1); i > 0 ; i--) {
			bt.remove(i);
		}
		
		assertEquals ("()", bt.toString());
		
		// remove root(s) in successive order, testing inner workings of delete cases.
		bt = buildComplete(n);
		int []toRemove = {8, 9, 10, 11, 12, 13, 14, 15, 4, 5, 6, 7, 2, 3, 1}; 
		for (int i : toRemove) {
			bt.remove(i);
		}
		assertEquals ("()", bt.toString());
	}
	
	/**
	 * Extract all values from the iterator and pack into a comma-separated string.
	 * 
	 * Note that the string does not terminate in a ,
	 * 
	 * @param it  Iterator of values to be extracted
	 * @return    comma-separated string.
	 */
	public String pack(Iterator<Integer> it) {
		StringBuilder sb = new StringBuilder("");
		while (it.hasNext()) {
			sb.append (it.next() + ",");
		}
		
		// remove trailing comma, if possible
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}
	
	/**
	 * Validate traversals.
	 *
	 */
	@Test
	public void testTraversals () {
		// number of levels
		int n = 3;
		
		RightThreadedBinaryTree<Integer> bt = buildComplete(n);
		assertEquals ("1,2,3,4,5,6,7", pack(bt.inorder()));
		assertEquals ("4,2,1,3,6,5,7", pack(bt.preorder()));
		assertEquals ("1,3,2,5,7,6,4", pack(bt.postorder()));	
		
		bt = buildComplete(1);
		Iterator<Integer> it = bt.inorder();
		assertEquals ("1",pack(it));
		assertFalse(it.hasNext());
		try {
			it.remove();
			fail ("shouldn't be able to remove from iterator.");
		} catch (UnsupportedOperationException uoe) {
			
		}
		
		try {
			it.next();
			fail ("shouldn't be able to retrieve next from drained iterator.");
		} catch (java.util.NoSuchElementException  nsee) {
			
		}
		
		bt = buildComplete(0);
		assertEquals ("",pack(bt.inorder()));
	}
	
	
	/**
	 * Validate that exceptions are thrown as needed.
	 *
	 */
	@Test
	public void testExceptions () {
		// Note that we can't properly type bt as RightThreadedBinaryTree<T> since we want to
		// validate that the class itself properly manages improper typed arguments.
		RightThreadedBinaryTree<Integer> bt = new RightThreadedBinaryTree<Integer>();
		
		bt.insert(11);
		bt.insert(9);
		
		try {
			bt.member(null);
			fail ("RightThreadedBinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {
			
		}
		
		try {
			bt.insert(null);
			fail ("RightThreadedBinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {
			
		}
		
		try {
			bt.remove(null);
			fail ("RightThreadedBinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {
			
		}
		
	}
}

