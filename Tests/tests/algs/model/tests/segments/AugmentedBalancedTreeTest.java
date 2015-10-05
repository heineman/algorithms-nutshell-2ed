package algs.model.tests.segments;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.problems.segmentIntersection.AugmentedBalancedTree;
import algs.model.problems.segmentIntersection.AugmentedNode;

public class AugmentedBalancedTreeTest {

	@Test
	public void testRoot() {
		AugmentedBalancedTree<Integer> abt = new AugmentedBalancedTree<Integer>();
		
		assertTrue (null == abt.root());
		assertTrue (null == abt.getEntry(99));
		assertTrue (null == abt.remove(88));
		
		abt.insert(17);
		
		assertTrue (null == abt.remove(99));
		
		AugmentedNode<Integer> n = abt.getEntry(17);
		assertTrue (n == abt.root());
		
		abt.insert(13);
		AugmentedNode<Integer> n2 = abt.getEntry(13);
		
		// still not found...
		assertTrue (null == abt.getEntry(99));
		
		// we now have THREE nodes. because the root is special.
		assertEquals (3, abt.size());
		
		AugmentedNode<Integer> n3 = abt.root();
		assertTrue (n3.left() == n2);  // left one is 13
		assertTrue (n3.right() == n);  // right one is 17
		
		abt.remove(13);
		assertEquals (1, abt.size());
		
		assertTrue (null == abt.remove(9));
		assertEquals (1, abt.size());
	}
	
	@Test
	public void testBalance() {
		AugmentedBalancedTree<Integer> abt = new AugmentedBalancedTree<Integer>();
		
		for (int i = 0; i < 7; i++) {
			abt.insert(i);
		}
		
		// 2*7 - 1 is expected number.
		assertEquals (13, abt.size());
		
		for (int i = 6; i>0; i--) {
			assertTrue (i == abt.getEntry(i).key());
			abt.remove(i);
			assertEquals (2*i-1, abt.size());
		}
		abt.remove(0);
		assertEquals (0, abt.size());
		
		
	}

}
