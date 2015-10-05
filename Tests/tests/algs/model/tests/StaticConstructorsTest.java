package algs.model.tests;

import org.junit.Test;

import algs.debug.Formatter;
import algs.model.array.Selection;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.TwoDFactory;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.tests.common.TrialSuiteHelper;

import junit.framework.TestCase;

/**
 * Only purpose of this test case is (annoyingly) to execute the null-constructors for classes that only have static
 * methods. Sheesh. EclEmma has issues (but overall it is an exceptionally useful tool to have).
 * 
 * @author George Heineman
 */
public class StaticConstructorsTest extends TestCase {

	@Test
	public void testStatics() {
		new KDFactory();
		new TwoDFactory();
		new Formatter();
		new Selection();
		new PlayerFactory();
		new TrialSuiteHelper();
	}
	
}
