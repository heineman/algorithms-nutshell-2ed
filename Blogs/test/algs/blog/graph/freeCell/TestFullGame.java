package algs.blog.graph.freeCell;

import java.io.File;
import java.io.IOException;
import java.util.Stack;


import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.search.DFS;
import algs.blog.graph.search.Result;
import algs.model.searchtree.IMove;
import junit.framework.TestCase;

public class TestFullGame extends TestCase {
	public void testOneGame() {
		try {
			// these can all be solved without increasing default Stack size
			FreeCellNode fcn = Deal.initialize(new File ("artifacts/32000.txt"), 2);
			FreeCellNode copy = (FreeCellNode) fcn.copy();
			
			DFS<short[]> dfs = new DFS<short[]>();
			Result res = dfs.fullSearch(fcn, Deal.goal(), FreeCellNode.comparator());
				
			Stack<IMove> sol = res.solution();
			for (int i = 0; i < sol.size(); i++) {
				sol.get(i).execute(copy);
			}

			// get to end board state
			assertEquals (copy, fcn);
			assertTrue (copy.hasWon());
			assertTrue (fcn.hasWon());
			
		} catch (IOException ioe) {
			fail("unable to locate 32000.txt file.");
		}
	}
}
