package algs.model.tests.chapter7;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.*;

/**
 * Show trend of A* for larger and larger N, but only for A*
 * 
 *
 *    Generate T=10 random boards with those moves
 *         Run AStarSearch
 *         

 * @author George Heineman
 */
public class ExtendedTable7_4Test extends TestCase {

	EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	
	/**
	 * Make n random moves and return the resulting board.
	 * 
	 * @param n
	 * @return
	 */
	public INode randomize (int n) {
		
		INode prev = goal;
		ArrayList<INode> visited = new ArrayList<INode>();
		visited.add(goal);
		while (n > 0) {
			ArrayList<INode> nodes = new ArrayList<INode>();
			ArrayList<INode> visitedNodes = new ArrayList<INode>();
			for (Iterator<IMove> it = prev.validMoves().iterator(); it.hasNext(); ) {
				
				INode copy = prev.copy();
				IMove move = it.next();
				move.execute(copy);
				copy.storedData(new Transition (move, prev));
				
				// add only if not yet visited
				if (!visited.contains(copy)) {
					nodes.add(copy);
				} else {
					visitedNodes.add(copy);
				}
			}
			
			// if we get here and NODES is empty, it likely means that all prior
			// board states have been seen before. IN this case we have no choice
			// but to pick one at random.
			if (nodes.size() == 0) {
				int rnd = (int)(Math.random() * visitedNodes.size());
				prev = visitedNodes.get(rnd);
			} else {
				// select an unvisited board state at random.
				int rnd = (int)(Math.random() * nodes.size());
				prev = nodes.get(rnd);
			}
			
			visited.add(prev);
			n--;
		}
		
		return prev;
	}
	
	@Test
	public void testFailureCase() {
		// 8 moves away
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		
		// can't find! Because the horizon effect. Need more detailed analysis to
		// see WHICH was the failed one.
		DepthFirstSearch dfs2 = new DepthFirstSearch(13);
		Solution ds2 = dfs2.search(start.copy(), goal);
		assertFalse (ds2.succeeded());
	}
		
	
	@Test
	public void testHeadtoHead() {
		int T = 1000;
		
		for (int n = 2; n <= 30; n += 1) {			
			int totalsA = 0;
			int statesA = 0;
			int failedA = 0;
			for (int t = 0; t < T; t++) {
				// Run BFS
				// Run DFS-unbounded
				// Run DFS-depth-2*N
				// Run DFS-depth-N
				INode start = randomize(n);
				AStarSearch as = new AStarSearch(new GoodEvaluator());
				Solution asol = as.search(start, goal);
				if (!asol.succeeded()) {
					failedA++;
				}
				totalsA += asol.numMoves();
				statesA += as.numClosed + as.numOpen;
			}

			float avgA = totalsA;
			avgA /= (T - failedA);
			
			System.out.print (n + "," + statesA + ",");
			System.out.print (avgA); if (failedA != 0) { System.out.print ("(" + failedA + ")");	}
			System.out.println();
		}
	}
}
