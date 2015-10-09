package algs.blog.graph.freeCell;

import algs.blog.graph.freeCell.Column;

import junit.framework.TestCase;


public class TestColumn extends TestCase {

	public void testEmptyColumn() {
		Column c = new Column();
		assertTrue (c.num == 0);
		
	}
	
	public void testAddAndRemoveOne() {
		Column c = new Column();
		c.add((short)1);  // ACE of CLUBS
		assertTrue (c.num == 1);
		
		assertTrue (c.isBlack());
		assertEquals (1, c.rank());
		assertEquals (1, c.remove());
		assertTrue (c.num == 0);
	}
	
	public void testAddABunch() {
		Column c = new Column();
		for (short i = 1; i < 52; i += 4) {
			c.add(i);  // ACE of CLUBS through KING of clubs
		}
		
		assertTrue (c.num == 13);
		
		assertTrue (c.isBlack());
		assertEquals (13, c.rank());
		for (int i = 49, ct = 13; i >= 1; i -= 4) {
			assertEquals (i, c.remove());
			assertEquals (--ct, c.num);
		}
	}
}
