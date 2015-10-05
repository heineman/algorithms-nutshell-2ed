package algs.model.tests.searchtree;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import algs.example.model.problems.pseudocodeExample.PuzzleEvaluator;
import algs.example.model.problems.pseudocodeExample.TinyPuzzle;
import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * Test a variety of state set storage implementations.
 * 
 * @author George Heineman
 */
public class GenericTests extends TestCase {

	ArrayList<INode> states = new ArrayList<INode>();
		
	PuzzleEvaluator eval;
	
	/** Create the various INode entities to be inserted for testing purposes. */
	@Override
	public void setUp() {
		// Create a set of states
		states.add (new TinyPuzzle(new int[]{2,2}));
		states.add (new TinyPuzzle(new int[]{0,0}));
		states.add (new TinyPuzzle(new int[]{2,1}));
		states.add (new TinyPuzzle(new int[]{2,0}));
		states.add (new TinyPuzzle(new int[]{2,3}));
		states.add (new TinyPuzzle(new int[]{1,0}));
		
		int[] goal = new int[]{2,3};
		eval = new PuzzleEvaluator(goal);
		
		// rate each one...
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			INode n = it.next();
			n.score(eval.eval(n));
		}
	}

	@Test
	public void testbad() {
		// error
		try {
			// This makes coverage = 100% for StateStorageFactory.
			new StateStorageFactory();  
			StateStorageFactory.create(-2);
			fail ("Failed to detect invalid input. ");
		} catch (IllegalArgumentException iae) {
			// ok
		}
	}
	
	@Test
	public void testBinary() {
		// order by distance to goal
		INodeSet set = StateStorageFactory.create(StateStorageFactory.TREE);
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			set.insert(it.next());
		}

		assertMembership(set);
		
		// remove the (2,0) state (which is 3 away from target (2,3). The only
		// way this works is if the node 'k' has its properly evaluated score
		// using the same evaluation function used to create the tree.
		INode k = new TinyPuzzle(new int[]{2,0});
		k.score(eval.eval(k));
		assertTrue (set.remove(k));
		
		// this only works because there was only one state in setup() which
		// was the same distance to (2,3) as (2,0).
		assertTrue (set.contains(k) == null);
		
		// remove in order of score.
		assertEquals (new TinyPuzzle(new int[]{2,3}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,2}), set.remove());
		
		TinyPuzzle tp = new TinyPuzzle(new int[]{1,3});
		tp.score(eval.eval(tp));
		set.insert(tp);
		
		assertEquals (new TinyPuzzle(new int[]{1,3}), set.remove());
		
		assertEquals (new TinyPuzzle(new int[]{2,1}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{1,0}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{0,0}), set.remove());
		failToDelete(set);
	}

	@Test
	public void testHash() {
		// order by distance to goal
		INodeSet set = StateStorageFactory.create(StateStorageFactory.HASH);
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			set.insert(it.next());
		}

		assertMembership(set);
		
		// remove the (2,0) state...
		INode stateToRemove = new TinyPuzzle(new int[]{2,0});
		assertTrue (set.remove(stateToRemove));
		assertTrue (set.contains(new TinyPuzzle(new int[]{2,0})) == null);
		
		try {
			set.remove();
			fail ("Remove must be unsupported by StateHash.");
		} catch (UnsupportedOperationException uoe) {
			
		}

		// all you can say is that they come from the original set.
		for (Iterator <INode> it = set.iterator(); it.hasNext(); ) {
			INode k = it.next();
			assertTrue (states.contains(k));
		}
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			INode k = set.contains(it.next());
			if (k != null) {
				assertTrue (set.remove(k));
			}
		}
		
		failToDelete(set);
	}
		
	@Test
	public void testOrdered() {
		// order by distance to goal
		INodeSet set = StateStorageFactory.create(StateStorageFactory.ORDERED);
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			set.insert(it.next());
		}

		assertMembership(set);
		
		// remove the (2,0) state...
		assertTrue (set.remove(new TinyPuzzle(new int[]{2,0})));
		assertTrue (set.contains(new TinyPuzzle(new int[]{2,0})) == null);

		// remove in order.
		assertEquals (new TinyPuzzle(new int[]{2,3}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,2}), set.remove());
		
		TinyPuzzle tp = new TinyPuzzle(new int[]{1,3});
		tp.score(eval.eval(tp));
		set.insert(tp);
		
		assertEquals (new TinyPuzzle(new int[]{1,3}), set.remove());
		
		assertEquals (new TinyPuzzle(new int[]{2,1}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{1,0}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{0,0}), set.remove());
		failToDelete(set);
	}

	@Test
	public void testQueue() {
		// order by distance to goal
		INodeSet set = StateStorageFactory.create(StateStorageFactory.QUEUE);
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			set.insert(it.next());
		}

		assertMembership(set);
		
		assertEquals (states.size(), set.size());

		// remove the (2,0) state...
		INode marked = new TinyPuzzle(new int[]{2,0});
		assertTrue (set.remove(marked));
		assertTrue (set.contains(new TinyPuzzle(new int[]{2,0})) == null);

		assertEquals (states.size()-1, set.size());

		// remove in order.
		assertEquals (new TinyPuzzle(new int[]{2,2}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{0,0}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,1}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,3}), set.remove());
		
		TinyPuzzle tp = new TinyPuzzle(new int[]{1,3});
		tp.score(eval.eval(tp));
		set.insert(tp);
		
		assertEquals (new TinyPuzzle(new int[]{1,0}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{1,3}), set.remove()); // at end
		failToDelete(set);
	}

	@Test
	public void testStack() {
		// order by distance to goal
		INodeSet set = StateStorageFactory.create(StateStorageFactory.STACK);
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			set.insert(it.next());
		}

		assertMembership(set);
		
		// remove the (2,0) state...
		assertTrue (set.remove(new TinyPuzzle(new int[]{2,0})));
		assertTrue (set.contains(new TinyPuzzle(new int[]{2,0})) == null);
		
		// remove in order.
		assertEquals (new TinyPuzzle(new int[]{1,0}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,3}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{2,1}), set.remove());
		assertEquals (new TinyPuzzle(new int[]{0,0}), set.remove());

		TinyPuzzle tp = new TinyPuzzle(new int[]{1,3});
		tp.score(eval.eval(tp));
		set.insert(tp);
		
		assertEquals (new TinyPuzzle(new int[]{1,3}), set.remove()); // right now
		assertEquals (new TinyPuzzle(new int[]{2,2}), set.remove());
		failToDelete(set);
	}

	// helper method
	private void failToDelete (INodeSet set) {
		assertTrue (set.isEmpty());
		
		// test failure on remove
		assertFalse (set.remove(new TinyPuzzle(new int[]{2,0})));
	}
	
	// helper method.
	private void assertMembership(INodeSet set) {
		assertFalse (set.isEmpty());
		assertEquals (states.size(), set.size());
		
		// ensure contains works
		for (Iterator<INode> it = states.iterator(); it.hasNext(); ) {
			INode k = it.next();
			assertTrue (set.contains(k) == k);
		}
		
		// ensure iterator works
		for (Iterator<INode> it = set.iterator(); it.hasNext(); ) {
			INode k = it.next();
			assertTrue (set.contains(k) == k);
		}
		
	}

	
}
