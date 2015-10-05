package algs.model.tests.eightpuzzle;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.problems.eightpuzzle.EightPuzzleNode;

/**
 * Test ability to generate unique key for a board, given rotational symmetry.
 * 
 * @author George Heineman
 */
public class KeyTest extends TestCase {
	
	@Test
	public void testKeys() {
		int [][] test = new int[][] {{1,2,3}, {4,5,6}, {7,0,8}};
		
		EightPuzzleNode node = new EightPuzzleNode(test);
		EightPuzzleNode copy = (EightPuzzleNode) node.copy();
		assertEquals (node, copy);
		assertEquals (node.key(), copy.key());
		
		int [][] test2 = new int[][] {{3,6,8}, {2,5,0}, {1,4,7}};
		EightPuzzleNode node2 = new EightPuzzleNode(test2);
		assertFalse (node.equals(node2));
		assertEquals (node.key(), node2.key());
		
		int [][] test3 = new int[][] {{8,0,7}, {6,5,4}, {3,2,1}};
		EightPuzzleNode node3 = new EightPuzzleNode(test3);
		assertFalse (node.equals(node3));
		Object s1 = node.key();
		Object s2 = node3.key();
		assertEquals (s1,s2);

		int [][] test4 = new int[][] {{7,4,1}, {0,5,2}, {8,6,3}};
		EightPuzzleNode node4 = new EightPuzzleNode(test4);
		assertFalse (node.equals(node4));
		assertEquals (node.key(), node4.key());
}
}
