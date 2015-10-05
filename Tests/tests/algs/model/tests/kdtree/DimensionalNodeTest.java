package algs.model.tests.kdtree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.kdtree.DimensionalNode;
import algs.model.nd.Hyperpoint;

public class DimensionalNodeTest {

	@Test
	public void testDimensionalNode() {
		Hyperpoint hp = new Hyperpoint(new double [] { 1, 2, 3});
		DimensionalNode dn = new DimensionalNode(3, hp);
	
		dn.setAbove(null);
		assertTrue (null == dn.getAbove());
		
		dn.setBelow(null);
		assertTrue (null == dn.getBelow());		
	}

}
