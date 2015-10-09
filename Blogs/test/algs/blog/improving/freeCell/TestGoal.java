package algs.blog.improving.freeCell;

import algs.blog.improving.freeCell.FreeCellNode;
import junit.framework.TestCase;

public class TestGoal extends TestCase {

	public void testInitial() {

		FreeCellNode fcn = new FreeCellNode();
		fcn.insertFoundation((short)49);  // King Clubs;
		fcn.insertFoundation((short)50);  // King Diamonds;
		fcn.insertFoundation((short)51);  // King Hearts;
		fcn.insertFoundation((short)52);  // King Spades;
		
		
		System.out.println(fcn.toString());
		
		System.out.println("key:" + fcn.key());
	}
	
}
