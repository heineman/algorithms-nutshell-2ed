package algs.blog.graph.freeCell;

import algs.blog.graph.freeCell.FreeCellNode;

import junit.framework.TestCase;

public class TestFreeEncoding extends TestCase {

	public void testEmpty() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
	}
	
	public void testAddOne() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		
		assertTrue (fcn.availableFree());
		
		// just make sure it can be taken out again.
		assertEquals (23, fcn.removeFree(23));
	}
	
	public void testAddNoOrder() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		assertEquals (23, fcn.freeEncoding[3]);
		fcn.insertFree((short)32);
		assertEquals (32, fcn.freeEncoding[3]);
		assertEquals (23, fcn.freeEncoding[2]);
		fcn.insertFree((short)27);
		assertEquals (32, fcn.freeEncoding[3]);
		assertEquals (27, fcn.freeEncoding[2]);
		assertEquals (23, fcn.freeEncoding[1]);
		fcn.insertFree((short)25);
		assertEquals (32, fcn.freeEncoding[3]);
		assertEquals (27, fcn.freeEncoding[2]);
		assertEquals (25, fcn.freeEncoding[1]);
		assertEquals (23, fcn.freeEncoding[0]);
		
		assertFalse (fcn.availableFree());
		
	}
	
	public void testAddFourDescending() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		assertEquals (23, fcn.freeEncoding[3]);
		fcn.insertFree((short)21);
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		fcn.insertFree((short)18);
		fcn.insertFree((short)5);
		
		assertFalse (fcn.availableFree());
		
		// place at the left-most location (3).
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		assertEquals (18, fcn.freeEncoding[1]);
		assertEquals (5, fcn.freeEncoding[0]);
	}
	
	public void testAddFourAscending() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		assertEquals (23, fcn.freeEncoding[3]);
		fcn.insertFree((short)28);
		assertEquals (28, fcn.freeEncoding[3]);
		assertEquals (23, fcn.freeEncoding[2]);
		fcn.insertFree((short)35);
		assertEquals (35, fcn.freeEncoding[3]);
		assertEquals (28, fcn.freeEncoding[2]);
		assertEquals (23, fcn.freeEncoding[1]);
		fcn.insertFree((short)44);
		
		assertFalse (fcn.availableFree());
		
		// place at the left-most location (3).
		assertEquals (44, fcn.freeEncoding[3]);
		assertEquals (35, fcn.freeEncoding[2]);
		assertEquals (28, fcn.freeEncoding[1]);
		assertEquals (23, fcn.freeEncoding[0]);
	}
	
	public void testDeleteAscending() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		assertEquals (23, fcn.freeEncoding[3]);
		fcn.insertFree((short)21);
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		fcn.insertFree((short)18);
		fcn.insertFree((short)5);
		
		assertFalse (fcn.availableFree());
		
		// place at the left-most location (3).
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		assertEquals (18, fcn.freeEncoding[1]);
		assertEquals (5, fcn.freeEncoding[0]);
		
		fcn.removeFree(5);   // lose the 5
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		assertEquals (18, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(18);   // lose the 18
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(21);   // lose the 21
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (0, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(23);   // lose the 23
		assertEquals (0, fcn.freeEncoding[3]);
		assertEquals (0, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
	}
	
	public void testDeleteDescending() {
		FreeCellNode fcn = new FreeCellNode();
		
		assertTrue (fcn.availableFree());
		fcn.insertFree((short)23);
		assertEquals (23, fcn.freeEncoding[3]);
		fcn.insertFree((short)21);
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		fcn.insertFree((short)18);
		fcn.insertFree((short)5);
		
		assertFalse (fcn.availableFree());
		
		// place at the left-most location (3).
		assertEquals (23, fcn.freeEncoding[3]);
		assertEquals (21, fcn.freeEncoding[2]);
		assertEquals (18, fcn.freeEncoding[1]);
		assertEquals (5, fcn.freeEncoding[0]);
		
		fcn.removeFree(23);   // lose the 23
		assertEquals (21, fcn.freeEncoding[3]);
		assertEquals (18, fcn.freeEncoding[2]);
		assertEquals (5, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(21);   // lose the 21
		assertEquals (18, fcn.freeEncoding[3]);
		assertEquals (5, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(18);   // lose the 18
		assertEquals (5, fcn.freeEncoding[3]);
		assertEquals (0, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
		
		fcn.removeFree(5);   // lose the 5
		assertEquals (0, fcn.freeEncoding[3]);
		assertEquals (0, fcn.freeEncoding[2]);
		assertEquals (0, fcn.freeEncoding[1]);
		assertEquals (0, fcn.freeEncoding[0]);
	}
	
}
