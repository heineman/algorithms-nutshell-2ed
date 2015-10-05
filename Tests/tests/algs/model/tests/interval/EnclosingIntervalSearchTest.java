package algs.model.tests.interval;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IInterval;
import algs.model.interval.DiscreteInterval;
import algs.model.interval.SegmentTree;
import algs.model.interval.StoredIntervalsNode;
import algs.model.problems.EnclosingIntervalSearch;

public class EnclosingIntervalSearchTest extends TestCase {
	
	/**
	 * Test DiscreteInterval hashCode
	 */
	@Test
	public void testHash() {
		DiscreteInterval di = new DiscreteInterval (7, 12);
		DiscreteInterval di2 = new DiscreteInterval (7, 12);
		assertEquals (di.hashCode(), di2.hashCode());
		assertEquals (di, di2);
	}
		
	
	/**
	 * Test removal of key nodes
	 */
	@Test
	public void testRemovals() {
		SegmentTree<StoredIntervalsNode<?>> st = new SegmentTree<StoredIntervalsNode<?>>(4,15,StoredIntervalsNode.getConstructor());
		
		// add a bunch.
		ArrayList<DiscreteInterval> list = new ArrayList<DiscreteInterval>();
		
		list.add(new DiscreteInterval (7, 12));
		list.add(new DiscreteInterval (5,  7));
		list.add(new DiscreteInterval (4, 10));
		list.add(new DiscreteInterval (8, 13));
		
		// add into the segment tree.
		for (DiscreteInterval di : list) {
			assertTrue (st.add(di));
			boolean val = st.contains(di);
			assertTrue (val);
		}
		
		// now add these, one at a time. Then remove in the same order.
		while (list.size() > 0) {
			// remove first one.
			DiscreteInterval done = list.get(0);
			st.remove(done);
			list.remove(done);
			
			// validate that segment tree still contains the other ones, but not the
			// one that was deleted.
			for (DiscreteInterval di : list) {
				assertTrue (st.contains(di));
			}
			assertFalse (st.contains(done));
		}
	}
	
	/** 
	 * Validate the creation of some sample trees.
	 */
	@Test
	public void testGathering () {
		SegmentTree<StoredIntervalsNode<?>> st = new SegmentTree<StoredIntervalsNode<?>>(1,15,StoredIntervalsNode.getConstructor());
		
		StoredIntervalsNode<?> sin = st.getRoot();
		DiscreteInterval di = new DiscreteInterval(2,5);
		Collection<IInterval> col = sin.gather(di);
		assertEquals (0, col.size());
		
		// add a bunch of things.
		IInterval i1 = new DiscreteInterval (1,4);
		IInterval i2 = new DiscreteInterval (2,7);
		IInterval i3 = new DiscreteInterval (2,3);
		IInterval i4 = new DiscreteInterval (1,6);
		
		assertTrue(st.add(i1));
		assertTrue(st.add(i2));
		assertTrue(st.add(i3));
		assertTrue(st.add(i4));
		
		col = sin.gather(di);
		assertEquals (3, col.size());
		st.remove (i1);  // remove one of these...
		st.remove (i2);  // remove one of these...
		col = sin.gather(di);
		assertEquals (1, col.size());		
		st.remove (i3);  // remove one of these...
		st.remove (i4);  // remove one of these...
		col = sin.gather(di);
		assertEquals (0, col.size());		
	}
	
	/** 
	 * Validate the creation of some sample trees.
	 */
	@Test
	public void testSpecial () {
		SegmentTree<StoredIntervalsNode<?>> st2 = new SegmentTree<StoredIntervalsNode<?>>(4,15,StoredIntervalsNode.getConstructor());
		
		assertEquals ("(((([4,5)<>)[4,6)<>([5,6)<>))[4,9)<>(([6,7)<>)[6,9)<>(([7,8)<>)[7,9)<>([8,9)<>))))[4,15)<>((([9,10)<>)[9,12)<>(([10,11)<>)[10,12)<>([11,12)<>)))[9,15)<>(([12,13)<>)[12,15)<>(([13,14)<>)[13,15)<>([14,15)<>)))))", 
				st2.toString());
		
		assertTrue (st2.add(new DiscreteInterval(7,12)));
		assertEquals (1, EnclosingIntervalSearch.compute(st2, 7).size());
		assertEquals (0, EnclosingIntervalSearch.compute(st2, 12).size());  // outside range
		assertEquals (1, EnclosingIntervalSearch.compute(st2, 11).size());
		assertEquals (1, EnclosingIntervalSearch.compute(st2, 9).size());
		assertEquals (0, EnclosingIntervalSearch.compute(st2, 4).size());
		assertEquals (0, EnclosingIntervalSearch.compute(st2, 15).size());
		
		// edge appears now multiple times in the output. 
		assertEquals ("(((([4,5)<>)[4,6)<>([5,6)<>))[4,9)<>(([6,7)<>)[6,9)<>(([7,8)<>)[7,9)<[7,12),>([8,9)<>))))[4,15)<>((([9,10)<>)[9,12)<[7,12),>(([10,11)<>)[10,12)<>([11,12)<>)))[9,15)<>(([12,13)<>)[12,15)<>(([13,14)<>)[13,15)<>([14,15)<>)))))", 
				st2.toString());
		
		SegmentTree<StoredIntervalsNode<?>> st = new SegmentTree<StoredIntervalsNode<?>>(1, 73, StoredIntervalsNode.getConstructor());
		
		IInterval i1 = new DiscreteInterval (1,4);
		IInterval i2 = new DiscreteInterval (5,7);
		IInterval i3 = new DiscreteInterval (2,7);
		IInterval i4 = new DiscreteInterval (2,6);
		
		assertTrue(st.add(i1));
		assertTrue(st.add(i2));
		assertTrue(st.add(i3));
		assertTrue(st.add(i4));
		
		// now compute intersection at various points. Pay special attention because
		// these intervals are semi-closed [left, right) where the left point is included
		// in the positive search queries, while the right value is not.
		assertEquals (0, EnclosingIntervalSearch.compute(st, 0).size());
		assertEquals (1, EnclosingIntervalSearch.compute(st, 1).size());
		assertEquals (3, EnclosingIntervalSearch.compute(st, 2).size());
		assertEquals (3, EnclosingIntervalSearch.compute(st, 3).size());
		assertEquals (2, EnclosingIntervalSearch.compute(st, 4).size());
		assertEquals (3, EnclosingIntervalSearch.compute(st, 5).size());
		assertEquals (2, EnclosingIntervalSearch.compute(st, 6).size());   // misses [2,6)
		assertEquals (0, EnclosingIntervalSearch.compute(st, 7).size());   // outside all
	}
	
	@Test
	public void testConstr() {
		// constructor not meaningful, but without this test case, EclEmma shows 95%
		// coder coverage. Sheesh
		new EnclosingIntervalSearch();
	}
}
