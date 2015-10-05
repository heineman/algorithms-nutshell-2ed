package algs.model.tests.tree;

import org.junit.Test;

import algs.model.tree.BalancedTree;
import algs.model.tree.RightThreadedBinaryTree;
import algs.model.tree.debug.BinaryTreeDebugger;
import algs.model.tree.debug.RightThreadTreeDebugger;

import junit.framework.TestCase;

public class VisualizerTest extends TestCase {
	
	@Test 
	public void testVisualization() {
		// 5-levels of complete trees.
		RightThreadedBinaryTree<Integer> bt = RightThreadedTreeTest.buildComplete(4);
		BinaryTreeDebugger<Integer,Integer> btd = new BinaryTreeDebugger<Integer,Integer>(); 
		
		bt.accept(btd);
		
		// process the debugger
		System.out.println (btd.getInputString());
	}
	
	@Test 
	public void testVisualizationAnother() {
		// 5-levels of complete trees.
		BalancedTree<Integer,Integer> bt = new BalancedTree<Integer,Integer>();
		BinaryTreeDebugger<Integer,Integer> btd = new BinaryTreeDebugger<Integer,Integer>(); 
		
		bt.insert(25, 25);
		bt.insert(43, 43);
		bt.insert(26, 26);
		bt.insert(16, 16);
		bt.insert(17, 17);
		bt.insert(15, 15);
		bt.insert(13, 13);
		
		bt.accept(btd);
		
		// process the debugger
		System.out.println (btd.getInputString());
		
		bt.insert(14, 14);
		
		btd = new BinaryTreeDebugger<Integer,Integer>();
		bt.accept(btd);
		
		// process the debugger
		System.out.println (btd.getInputString());
	}
	
	@Test 
	public void testRightThreadDebug() {
		// 5-levels of complete trees.
		RightThreadedBinaryTree<Integer> bt = RightThreadedTreeTest.buildComplete(4);
		RightThreadTreeDebugger<Integer> rttd = new RightThreadTreeDebugger<Integer>(); 
		
		bt.accept(rttd);
		
		// process the debugger
		System.out.println (rttd.getInputString());
	}
	
}