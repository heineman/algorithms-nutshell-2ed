package algs.model.tests.interval;

import java.util.Iterator;
import junit.framework.TestCase;
import org.junit.Test;

import algs.model.IInterval;
import algs.model.interval.DiscreteInterval;
import algs.model.interval.SegmentTree;
import algs.model.interval.SegmentTreeNode;

public class ValidateSegmentTreeTest extends TestCase {
	
	/** 
	 * Validate the creation of some sample trees.
	 */
	@Test
	public void testSampleTree() {
		SegmentTree<?> st;
		
		// small tree
		st = new SegmentTree<SegmentTreeNode<?>> (1,3);
		assertEquals ("(([1,2))[1,3)([2,3)))", st.toString());

		// a bit bigger [cref example from Preparata/Shamos monograph]
		st = new SegmentTree<SegmentTreeNode<?>> (4, 15);
		assertEquals ("(((([4,5))[4,6)([5,6)))[4,9)(([6,7))[6,9)(([7,8))[7,9)([8,9)))))[4,15)((([9,10))[9,12)(([10,11))[10,12)([11,12))))[9,15)(([12,13))[12,15)(([13,14))[13,15)([14,15))))))",
						st.toString());
	}
	
	/**
	 * Validate invalid attempts are prevented.
	 */
	@Test
	public void testInvalidAttempts() {
		try {
			new SegmentTree<SegmentTreeNode<?>> (2, 1);
			fail("Failed to detect invalid constructor request.");
			
		} catch (IllegalArgumentException iae) {
			
		}
		
		//	small tree
		SegmentTree<?> st = new SegmentTree<SegmentTreeNode<?>> (1,3);

		try {
			// add FAKE IInterval.
			st.add (new IInterval() {

				public int getLeft() {
					return 10;
				}

				public int getRight() {
					return 8;
				}

				public boolean intersects(int q) {
					return false;
				}

				public boolean equals(Object o) {
					if (o == null) { return false; }
					if (o instanceof IInterval) {
						IInterval i = (IInterval) o;
						return getLeft() == i.getLeft() && getRight() == i.getRight();
					}
					return false; 
				}

				public boolean toTheLeft(int q) {
					return false;
				}

				public boolean toTheRight(int q) {
					return false;
				}
				
			});
			fail ("Segment Tree unprotected against rogue IInterval.");
		} catch (IllegalArgumentException iae) {
			
		}
		
	}
	
	/**
	 * Test the size() method.
	 */
	@Test
	public void testSize() {
		SegmentTree<?> st;
		
		// a bit bigger [cref example from Preparata/Shamos monograph]
		st = new SegmentTree<SegmentTreeNode<?>> (4, 15);
		assertEquals (21, st.size());

	}
	
	/**
	 * Test iterator for the structure (should be inorder).
	 */
	@Test
	public void testIterator () {
		SegmentTree<SegmentTreeNode<?>> st;
		
		// a bit bigger [cref example from Preparata/Shamos monograph]
		st = new SegmentTree<SegmentTreeNode<?>> (4, 15);
		
		for (Iterator<IInterval> it = st.iterator(); it.hasNext(); ) {
			it.next();
		}
		
	}
	
	/** 
	 * Test empty conditions
	 */
	@Test
	public void testEmptyTree() {
		SegmentTree<?> st;
		
		// small tree
		st = new SegmentTree<SegmentTreeNode<?>> (1,4);
		
		// segment tree is:
		//   [1,4)
		//     left:[1,2)
		//     right:[2,4)
		//        left:[2,3)
		//        right:[3,4)
		
		assertEquals (5, st.size());
		Iterator<IInterval> it = st.iterator();
		IInterval intv = it.next();
		
		assertTrue (st.contains(intv));
		
		// note that even though [1,3) is a subset of the range, you can't use the SegmentTree
		// data structure to detect this fact, since at the base level, it only checkes whether
		// segments [i,i+1) are in the tree.
		DiscreteInterval di = new DiscreteInterval(1,3);
		assertFalse (st.contains(di));
		
		di = new DiscreteInterval(2,3);
		assertTrue (st.contains(di));
		
	}
}
