package algs.model.tests.gametree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.gametree.IComparator;
import algs.model.gametree.debug.AlphaBetaDebugNode;
import algs.model.gametree.debug.MinMaxNode;
import algs.model.gametree.debug.NegMaxNode;
import algs.model.gametree.debug.ScoreNode;

/**
 * Since we changed the way that formatting is done, we get low-coverage counts arbitrarily because
 * code has been left in if we can ever get this working again. This test suite just exercises these
 * methods to make sure they no longer do anything.
 */
public class FormattingTest {

	@Test
	public void testNegMaxNode() {
		NegMaxNode nmn = new NegMaxNode();
		assertEquals (0, nmn.fontSize());
		assertTrue (null == nmn.fontName());
	}

	@Test
	public void testScoreNode() {
		ScoreNode sn = new ScoreNode(0);
		assertEquals (0, sn.fontSize());
		assertTrue (null == sn.fontName());
	}
	
	@Test
	public void testMinMaxNode() {
		MinMaxNode mn = new MinMaxNode(IComparator.MAX);
		assertEquals (0, mn.fontSize());
		assertTrue (null == mn.fontName());
	}
	
	
	@Test
	public void testAlphaBetaDebugNode() {
		AlphaBetaDebugNode abn = new AlphaBetaDebugNode(0, 10);
		assertEquals (0, abn.fontSize());
		assertTrue (null == abn.fontName());
	}
}
