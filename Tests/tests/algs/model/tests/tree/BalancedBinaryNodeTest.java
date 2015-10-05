package algs.model.tests.tree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

public class BalancedBinaryNodeTest {

	@Test
	public void testTree() {
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>();
		bt.insert(13, 17);
		BalancedBinaryNode<Integer, Integer> bn = bt.getEntry(13);
		
		assertEquals (BalancedBinaryNode.BLACK, bn.color()); 
		
		// WARNING! Never do this within a real tree
		bn.color(false);
		assertEquals (BalancedBinaryNode.RED, bn.color()); 
		bn.color(true);
		assertEquals (BalancedBinaryNode.BLACK, bn.color()); 
			
		BalancedBinaryNode<Integer, Integer> parent = bn.parent();
		assertTrue (parent == null);
		
		assertTrue (17 == bn.setValue(22));
		assertTrue (22 == bn.value());
		
		assertEquals ("13=22", bn.toString());
	
		// try number of equals
		assertFalse (bn.equals (null));
		assertFalse (bn.equals ("garbage"));
		
		// awkward ones
		BalancedBinaryNode<Integer,Integer> bbn1 = new BalancedBinaryNode<Integer,Integer>(null,null,null);
		BalancedBinaryNode<Integer,Integer> bbn2 = new BalancedBinaryNode<Integer,Integer>(null,2,null);
		BalancedBinaryNode<Integer,Integer> bbn3 = new BalancedBinaryNode<Integer,Integer>(3,null,null);
		
		assertTrue (bbn1.equals(bbn1));
		
		assertFalse (bbn1.equals(bn));
		assertFalse (bbn2.equals(bn));
		assertFalse (bbn3.equals(bn));
		
		assertFalse (bn.equals (bbn1));
		assertFalse (bn.equals (bbn2));
		assertFalse (bn.equals (bbn3));
	}

	
}
