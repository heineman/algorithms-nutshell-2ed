package algs.model.tests.debug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algs.debug.Formatter;
import algs.debug.IGraphEntity;
import algs.debug.drawers.DefaultNodeDrawer;
import algs.debug.drawers.DiscardedNodeDrawer;
import algs.debug.drawers.UnexploredNodeDrawer;
import algs.model.gametree.MoveEvaluation;
import algs.model.gametree.debug.AlphaBetaDebugNode;

/**
 * The computed DOTTY output is based on hand-crafted examples. This test suite validates that 
 * the appropriate dot output appears as expected.
 * 
 * @author George Heineman
 *
 */
public class DebugTest {

	static final String label = "NODELABEL";
	
	/** created entity. */
	IGraphEntity n;

	/**
	 * Set up the default object to use.
	 */
	@Before
	public void setUp() {
		n = new IGraphEntity() {

			@Override
			public String nodeLabel() {
				return label;
			}
			
		};
	}
	
	// at one point we had sophisticated font-formatting. This has been discarded
	@Test
	public void testIGraph() {
		assertFalse (Formatter.isSymbol(0));
		
		// was one of the special ones in the past.
		assertFalse (Formatter.isSymbol(MoveEvaluation.minimum()));
		assertFalse (Formatter.isSymbol(MoveEvaluation.maximum()));
	}
	
	// discarded nodes are non-frutiful nodes from a search perspective.
	@Test
	public void testDiscardedNodeDrawer() {
		DiscardedNodeDrawer dnd = new DiscardedNodeDrawer();
		
		assertEquals ("[fillcolor=\".8 .8 .8\" label=\"" + label + "\"]", dnd.draw(n));
	}
	
	// unexplored nodes would have been explored
	@Test
	public void testUnexploredNodeDrawer() {
		UnexploredNodeDrawer und = new UnexploredNodeDrawer();
		
		assertEquals ("[style=filled,color=\"gray60\" fontcolor=\"gray20\" fillcolor=\"gray80\" label=\"" + label + "\"]",
				und.draw(n));
	}
	
	@Test
	public void testDefaultNodeDrawer() {
		AlphaBetaDebugNode abdn = new AlphaBetaDebugNode(Integer.MIN_VALUE, 55);
		
		DefaultNodeDrawer dnd = new DefaultNodeDrawer();
		assertEquals ("[label=\"{a: -2147483648|b: 55}\"]", dnd.draw(abdn));
	}

}
