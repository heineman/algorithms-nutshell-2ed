package algs.model.tests.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IBinaryTreeNode;
import algs.model.tree.AbstractBinaryTraversal;
import algs.model.tree.BinaryNode;
import algs.model.tree.BinaryTree;
import algs.model.tree.InorderTraversal;
import algs.model.tree.PostorderTraversal;
import algs.model.tree.PreorderTraversal;

/**
 * Test cases for the Binary Tree
 * 
 * @author George Heineman
 */
public class BinaryTreeTest extends TestCase {

	public void testDebug() {
		BinaryNode<Integer> bn = new BinaryNode<Integer>(7);
		assertEquals ("{{value|7}}", bn.nodeLabel());
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
	BinaryTree<Integer> buildLeftLinear (int n) {
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
	BinaryTree<Integer> buildRightLinear (int n) {
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
	BinaryTree<Integer> buildComplete(int n) {
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

	/**
	 * Ensure empty trees are properly validated.
	 *
	 */
	@Test
	public void testEmpty() {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

		assertNull(bt.getRoot());
		assertEquals ("()", bt.toString());

		assertFalse (bt.member(9));
	}


	/**
	 * Validate removal of invalid entries.
	 *
	 */
	@Test
	public void testInvalidRemovals () {
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

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
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

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
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

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

		BinaryTree<Integer> bt = buildComplete(n);

		assertNotNull (bt.getRoot());

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testTraversals () {
		// number of levels
		int n = 3;

		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		assertEquals ("", pack(bt.inorder()));
		assertEquals ("", pack(bt.preorder()));
		assertEquals ("", pack(bt.postorder()));
		assertEquals ("", pack(bt.iterator()));

		// validate detects null starting point
		InorderTraversal itt;
		try {
			IBinaryTreeNode<Integer> root = bt.getRoot();
			itt = new InorderTraversal(root);
			fail ("should fail on null root");
		} catch (Exception e) {

		}

		// validate empty iterator behaves properly
		assertFalse (bt.iterator().hasNext());
		try {
			bt.iterator().remove();
			fail ("Shouldn't be able to modify binary tree through iterator.");
		} catch (Exception e) {

		}

		bt = buildComplete(n);

		// validate simple phase valueOf
		AbstractBinaryTraversal.Phase p = AbstractBinaryTraversal.Phase.valueOf("LEFT");
		assertEquals (AbstractBinaryTraversal.Phase.LEFT, p);
		
		// verify the final phase for traversals.
		assertEquals (AbstractBinaryTraversal.Phase.RIGHT, new InorderTraversal(bt.getRoot()).finalPhase());
		assertEquals (AbstractBinaryTraversal.Phase.RIGHT, new PreorderTraversal(bt.getRoot()).finalPhase());
		assertEquals (AbstractBinaryTraversal.Phase.SELF, new PostorderTraversal(bt.getRoot()).finalPhase());

		// demonstrate non-mutability
		try {
			new InorderTraversal(bt.getRoot()).remove();
			fail ("shouldn't be able to mutate traversals");
		} catch (Exception e) {

		}

		Iterator<Integer> it = bt.inorder();
		try {
			it.remove();
			fail ("InOrder traversal claims to offer remove.");
		} catch (UnsupportedOperationException e) {

		}
		assertEquals ("1,2,3,4,5,6,7", pack(it));
		assertFalse (it.hasNext());
		try {
			it.next();
			fail ("InOrder traversal incorrectly exceeds its extension.");
		} catch (NoSuchElementException nsee) {

		}

		it = bt.preorder();
		try {
			it.remove();
			fail ("PreOrder traversal claims to offer remove.");
		} catch (UnsupportedOperationException e) {

		}
		assertEquals ("4,2,1,3,6,5,7", pack(it));
		assertFalse (it.hasNext());
		try {
			it.next();
			fail ("PreOrder traversal incorrectly exceeds its extension.");
		} catch (NoSuchElementException nsee) {

		}

		it = bt.postorder();
		try {
			it.remove();
			fail ("PostOrder traversal claims to offer remove.");
		} catch (UnsupportedOperationException e) {

		}
		assertEquals ("1,3,2,5,7,6,4", pack(it));	
		assertFalse (it.hasNext());
		try {
			it.next();
			fail ("PostOrder traversal incorrectly exceeds its extension.");
		} catch (NoSuchElementException nsee) {

		}

		bt = buildComplete(1);
		assertEquals ("1",pack(bt.inorder()));

		// try again with non-empty starting point.
		itt = new InorderTraversal(bt.getRoot());
		assertTrue(itt.hasNext());
		itt.next();
		assertFalse(itt.hasNext());

		bt = buildComplete(0);
		assertEquals ("",pack(bt.inorder()));
	}


	/**
	 * Validate that exceptions are thrown as needed.
	 *
	 */
	@Test
	public void testExceptions () {
		// Note that we can't properly type bt as BinaryTree<T> since we want to
		// validate that the class itself properly manages improper typed arguments.
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

		bt.insert(11);

		// can't call next too many times on iterator
		Iterator<Integer> it = bt.inorder();

		// can't call remove
		try {
			it.remove();
			fail ("Iterator believes it supports remove.");
		} catch (UnsupportedOperationException uoe) {

		}		

		it.next();  // safe
		try {
			it.next();  // not safe
			fail ("Iterator fails to cover end case");
		} catch (NoSuchElementException nsee) {

		}

		bt.insert(9);

		try {
			bt.member(null);
			fail ("BinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {

		}

		try {
			bt.insert(null);
			fail ("BinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {

		}

		try {
			bt.remove(null);
			fail ("BinaryTree Fails to throw IllegalArgumentException on member");
		} catch (IllegalArgumentException cce) {

		}

		try {
			new BinaryNode<Integer>(null);
			fail ("BinaryNode fails to throw IllegalArgumentException on construct-null");
		} catch (IllegalArgumentException iae) {

		}

		// ValueExtractor can't remove
		try {
			bt.inorder().remove();
			fail ("Should be unable to modify traversals.");
		} catch (UnsupportedOperationException uoe) {

		}
	}
	
	/**
	 * Validate that exceptions are thrown as needed.
	 *
	 */
	@Test
	public void testEmptyIterator() {
		// Note that we can't properly type bt as BinaryTree<T> since we want to
		// validate that the class itself properly manages improper typed arguments.
		BinaryTree<Integer> bt = new BinaryTree<Integer>();

		// can't call next too many times on iterator
		Iterator<Integer> it = bt.inorder();
		assertFalse (it.hasNext());
		try {
			it.next();
			fail ("BinaryTree iterator must throw exception on next() for empty trees.");
		} catch (java.util.NoSuchElementException nsee) {
			
		}
	}
}

