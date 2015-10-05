package algs.model.tests.tictactoe;

import org.junit.Test;

import algs.model.problems.tictactoe.model.Cell;

import junit.framework.TestCase;


public class CellTest extends TestCase {

	@Test
	public void testCell() {
		Cell c = new Cell (2, 1);
		Cell c2 = new Cell (c);
		Cell c3 = new Cell (1, 2);
		
		assertEquals (c.hashCode(), c2.hashCode());
		assertEquals (c, c2);
		assertFalse (c.equals("lsidjl"));
		assertFalse (c.equals (null));
		
		assertEquals ("(2,1)", c.toString());
		
		assertTrue (c.isAdjacent(new Cell (2, 0)));
		assertTrue (c.isAdjacent(new Cell (2, 2)));
		assertTrue (c.isAdjacent(new Cell (1, 1)));
		assertTrue (c.isAdjacent(new Cell (3, 1)));  // unknown size, so this is ok.
		assertFalse (c.isAdjacent(c));
		assertFalse (c.isAdjacent(new Cell (1, 2)));
		assertFalse (c.isAdjacent(new Cell (2, 1)));
		assertFalse (c.isAdjacent(new Cell (0, 1)));
		assertFalse (c3.isAdjacent(new Cell (1, 0)));
	}
}
