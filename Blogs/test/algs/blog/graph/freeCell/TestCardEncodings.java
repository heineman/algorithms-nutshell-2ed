package algs.blog.graph.freeCell;

import algs.blog.graph.freeCell.FreeCellNode;
import junit.framework.TestCase;


public class TestCardEncodings extends TestCase {

	public void testEncode() {
		
		assertEquals ("AC", FreeCellNode.out2(FreeCellNode.CLUBS, 1).toString());
		assertEquals ("KS", FreeCellNode.out2(FreeCellNode.SPADES, 13).toString());
		
		assertEquals ("TD", FreeCellNode.out2(FreeCellNode.DIAMONDS, 10).toString());
		assertEquals (5, FreeCellNode.fromCard("2C"));
		
		assertEquals (1, FreeCellNode.fromCard("AC"));
		assertEquals (52, FreeCellNode.fromCard("KS"));
		
	}
}
