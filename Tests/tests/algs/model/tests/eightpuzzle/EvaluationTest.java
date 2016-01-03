package algs.model.tests.eightpuzzle;

import org.junit.Test;

import algs.model.problems.eightpuzzle.BadEvaluator;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.FairEvaluator;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.problems.eightpuzzle.WeakEvaluator;
import algs.model.searchtree.DepthTransition;
import junit.framework.TestCase;

public class EvaluationTest extends TestCase {

	@Test
	public void testGoodEvaluator() {
		EightPuzzleNode state = new EightPuzzleNode(new int[][]{
				{8,1,3},{0,2,4},{7,6,5}
		});
		
		EightPuzzleNode two = new EightPuzzleNode(new int[][]{
				{0,1,3},{8,2,4},{7,6,5}
		});
		GoodEvaluator eval = new GoodEvaluator();
		BadEvaluator bad = new BadEvaluator();
		WeakEvaluator weak = new WeakEvaluator();
		FairEvaluator fair = new FairEvaluator();
		
		int rc;
		
		// go through score method
		eval.score(state);
		assertEquals (18, state.score());
		bad.score(state);
		assertEquals (14, state.score());
		weak.score(state);
		assertEquals (3, state.score());
		fair.score(state);
		assertEquals (3, state.score());
		
		// go through eval method
		rc = eval.eval(state);
		assertEquals (18, rc);
		assertEquals ("3+3*5=18", eval.evalString(state));
		
		rc = bad.eval(state);
		assertEquals (14, rc);
		assertEquals ("(0-4)+(7-3)+(6-1)+(5-8)=2=|16-2|=14", bad.evalString(state));
		
		rc = weak.eval(state);
		assertEquals (3, rc);
		assertEquals ("3", weak.evalString(state));
		
		rc = fair.eval(state);
		assertEquals (3, rc);
		assertEquals ("3", fair.evalString(state));
		
		
		// try second one
		rc = eval.eval(two);
		assertEquals (17, rc);
		rc = bad.eval(two);
		assertEquals (2, rc);
		rc = weak.eval(two);
		assertEquals (2, rc);
		rc = fair.eval(two);
		assertEquals (2, rc);
		
		
		// deal with depth first evaluation
		int depth = 17;
		state.storedData(new DepthTransition(null, null, depth));
		weak.score(state);
		assertEquals (3 + depth, state.score());
		assertEquals ("17+3=20", weak.evalString(state));
		
		eval.score(state);
		assertEquals ("17+3+3*5=35", eval.evalString(state));
		
		bad.score(state);
		assertEquals ("(0-4)+(7-3)+(6-1)+(5-8)=2=|16-2|=14", bad.evalString(state));
		
		fair.score(state);
		assertEquals ("17+3=20", fair.evalString(state));
		
		// deal with zero first evaluation
		depth = 0;
		state.storedData(new DepthTransition(null, null, depth));
		weak.score(state);
		assertEquals (3 + depth, state.score());
		assertEquals ("3", weak.evalString(state));
		
		eval.score(state);
		assertEquals ("3+3*5=18", eval.evalString(state));
		
		bad.score(state);
		assertEquals ("(0-4)+(7-3)+(6-1)+(5-8)=2=|16-2|=14", bad.evalString(state));
		
		// all have non-zero toStrings...
		assertFalse (null == eval.toString());
		assertFalse (null == weak.toString());
		assertFalse (null == bad.toString());
	}
}
