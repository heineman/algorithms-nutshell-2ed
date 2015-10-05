package algs.model.tests.eightpuzzle;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.list.DoubleLinkedList;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.IMove;
import algs.model.searchtree.IScore;


public class NodeTest extends TestCase {
	
	@Test
	public void testSimple() {
		int [][] test = new int[][] {{1,2,3}, {4,5,6}, {7,0,8}};
		
		EightPuzzleNode node = new EightPuzzleNode(test);
		EightPuzzleNode copy = (EightPuzzleNode) node.copy();
		assertEquals (node, copy);
		assertEquals (node, node);
		assertTrue (node.equivalent(copy));
		assertTrue (node.equivalent(node));

		try {
			new EightPuzzleNode(new int[][] {{1,2,3}, {4,8,6}, {7,0,8}});
			fail("Fails to detect illegal board.");
		} catch (IllegalArgumentException iae) {
			
		}
		
		IScore s = new GoodEvaluator();
		assertEquals (42, s.eval(node));
		
		assertEquals (null, node.storedData());
		node.storedData(new String ("test"));
		assertEquals (new String ("test"), node.storedData());
		
		System.out.println (node.toString());
		assertEquals ("123\n456\n7 8\n", node.toString());
		
		// moves
		DoubleLinkedList<IMove> moves = node.validMoves();
		assertEquals (3, moves.size());
		IMove m1 = moves.first().value();
		m1.execute(copy);
		copy = (EightPuzzleNode) node.copy();
		IMove m2 = moves.first().next().value();
		m2.execute(copy);
		copy = (EightPuzzleNode) node.copy();
		IMove m3 = moves.first().next().next().value();
		m3.execute(copy);
		
		// move to solution
		assertTrue (node.isAdjacentAndEmpty(2, 2, 2, 1));
		assertTrue (node.isAdjacentAndEmpty(2, 0, 2, 1));
		assertFalse (node.isAdjacentAndEmpty(2, 1, 2, 2));
		assertTrue (node.swap(2, 2, 2, 1));
	}
}
