package algs.model.tests.searchtree;

import org.junit.Test;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.ClosedStates;
import algs.model.searchtree.INode;

import junit.framework.TestCase;

/**
 * Test out closed states.
 * 
 * @author George Heineman
 *
 */
public class ClosedStatesTest extends TestCase {

	@Test
	public void testSimple() {
		ClosedStates cs = new ClosedStates();
		assertEquals (0, cs.size());
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		assertTrue (cs.isEmpty());
		cs.insert(start);
		assertEquals (1, cs.size());
		
		assertNull (cs.contains(goal));
		assertNotNull (cs.contains (start));
		
		// validate that remove() never to be called on closed states
		try {
			cs.remove();
			fail ("Never allow remove() on closed states");
		} catch (IllegalStateException iae) {
			// OK.
		}
		
		assertFalse (cs.isEmpty());
	
		// can remove individual nodes
		assertTrue (cs.remove(start));
		assertTrue (cs.isEmpty());
		assertNull (cs.contains(start));
		assertFalse (cs.remove(start));
		
		// test iterator and Iterable
		cs.insert(start);
		cs.insert(goal);
		INode[]vals = new INode[2];
		int idx = 0;
		for (INode n : cs) {
			vals[idx++] = n;
		}
		
		assertEquals (vals[0], start);
		assertEquals (vals[1], goal);
	}
	
	@Test
	public void testMore () {
		ClosedStates cs = new ClosedStates();
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});

		start.score(99);
		
		EightPuzzleNode start2 = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		start2.score(95);
		
		// this should remove start
		cs.insert(start);
		cs.removeIfLowerScore(start2);
		assertTrue (cs.isEmpty());

		assertEquals (-1, cs.removeIfLowerScore(start));
		
		cs.insert(start2);
		assertEquals (1, cs.size());
		cs.removeIfLowerScore(start);
		assertEquals (1, cs.size());
		
		try {
			cs.removeIfLowerScore(null);
			fail ("Failed to stop null pointer passed into removeIfLowerScore");
		} catch (NullPointerException npe) {
			// OK.
		}

	}
}
