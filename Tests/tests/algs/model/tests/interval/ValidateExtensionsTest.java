package algs.model.tests.interval;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.interval.*;

public class ValidateExtensionsTest extends TestCase {
	/** 
	 * Validate the creation of some sample trees.
	 */
	@Test
	public void testSpecial () {
		SegmentTree<SpecialSegmentTreeNode<?>> st = new SegmentTree<SpecialSegmentTreeNode<?>>(1, 3, SpecialSegmentTreeNode.getConstructor());
		
		// small tree
		assertEquals ("(([1,2)<null:null>)[1,3)<null:null>([2,3)<null:null>))", st.toString());
	}
}
