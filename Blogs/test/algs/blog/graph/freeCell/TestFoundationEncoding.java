package algs.blog.graph.freeCell;

import algs.blog.graph.freeCell.FreeCellNode;

import junit.framework.TestCase;

public class TestFoundationEncoding extends TestCase {

	public void testEmpty() {
		FreeCellNode fcn = new FreeCellNode();

		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);

	}

	public void testAddOne() {
		FreeCellNode fcn = new FreeCellNode();

		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);

		fcn.insertFoundation((short)1);   // ACE of CLUBS

		assertEquals (1, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);
	}

	public void testAddFourAces() {
		FreeCellNode fcn = new FreeCellNode();

		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);

		fcn.insertFoundation((short)2);   // ACE of DIAMONDS
		fcn.insertFoundation((short)1);    // ACE of CLUBS
		fcn.insertFoundation((short)3);   // ACE of HEARTS
		fcn.insertFoundation((short)4);   // ACE of SPADES

		assertEquals (1, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.SPADES]);
	}


	public void testAddFourAscending() {
		FreeCellNode fcn = new FreeCellNode();

		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);

		fcn.insertFoundation((short)2);   // ACE of DIAMONDS
		fcn.insertFoundation((short)1);   // ACE of CLUBS
		fcn.insertFoundation((short)3);   // ACE of HEARTS
		fcn.insertFoundation((short)4);   // ACE of SPADES

		fcn.insertFoundation((short)7);   // TWO of HEARTS
		fcn.insertFoundation((short)11);   // THREE of HEARTS
		fcn.insertFoundation((short)8);   // TWO of SPADES
		fcn.insertFoundation((short)5);    // TWO of CLUBS
		fcn.insertFoundation((short)6);    // TWO of DIAMONDS

		assertEquals (2, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);
	}

	public void testRemoveSome() {
		FreeCellNode fcn = new FreeCellNode();

		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (FreeCellNode.EMPTY, fcn.foundationEncoding[FreeCellNode.SPADES]);

		fcn.insertFoundation((short)2);   // ACE of DIAMONDS
		fcn.insertFoundation((short)1);   // ACE of CLUBS
		fcn.insertFoundation((short)3);   // ACE of HEARTS
		fcn.insertFoundation((short)4);   // ACE of SPADES

		fcn.insertFoundation((short)7);   // TWO of HEARTS
		fcn.insertFoundation((short)11);   // THREE of HEARTS
		fcn.insertFoundation((short)8);   // TWO of SPADES
		fcn.insertFoundation((short)5);    // TWO of CLUBS
		fcn.insertFoundation((short)6);    // TWO of DIAMONDS

		assertEquals (2, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (2, fcn.removeFoundation(FreeCellNode.CLUBS));
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (1, fcn.removeFoundation(FreeCellNode.CLUBS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (0, fcn.removeFoundation(FreeCellNode.CLUBS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (2, fcn.removeFoundation(FreeCellNode.DIAMONDS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (1, fcn.removeFoundation(FreeCellNode.DIAMONDS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (3, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (3, fcn.removeFoundation(FreeCellNode.HEARTS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (2, fcn.removeFoundation(FreeCellNode.HEARTS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (1, fcn.removeFoundation(FreeCellNode.HEARTS));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (2, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (2, fcn.removeFoundation(FreeCellNode.SPADES));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (1, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (1, fcn.removeFoundation(FreeCellNode.SPADES));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.SPADES]);

		assertEquals (0, fcn.removeFoundation(FreeCellNode.SPADES));
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.CLUBS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.DIAMONDS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.HEARTS]);
		assertEquals (0, fcn.foundationEncoding[FreeCellNode.SPADES]);
	}

}
