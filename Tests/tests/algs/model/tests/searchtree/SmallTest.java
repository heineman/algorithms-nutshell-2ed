package algs.model.tests.searchtree;

import junit.framework.TestCase;

import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.BreadthFirstSearch;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;

// experiment with different values as well as different approaches.

public class SmallTest extends TestCase {

	@Test
	public void testAStarFailsImpossible() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{2,0,3},{1,5,4},{7,8,6}
		});
		EightPuzzleNode impossibleGoal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,5,6}
		});

		algs.model.searchtree.AStarSearch as = new algs.model.searchtree.AStarSearch(new GoodEvaluator());
		as.storageType(StateStorageFactory.HASH);  // make sure this one works, too.
		Solution sol = as.search(initial, impossibleGoal);
		assertFalse (sol.succeeded());

		// full set.
		assertEquals (181440, as.numClosed);
	}

	@Test
	public void testDebugAStarFailsImpossible() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{2,0,3},{1,5,4},{7,8,6}
		});
		EightPuzzleNode impossibleGoal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,5,6}
		});

		algs.model.searchtree.debug.AStarSearch as = new algs.model.searchtree.debug.AStarSearch(new GoodEvaluator());
		as.storageType(StateStorageFactory.HASH);  // make sure this one works, too.
		Solution sol = as.search(initial, impossibleGoal);
		assertFalse (sol.succeeded());

		// full set.
		assertEquals (181440, as.numClosed);
	}

	// Test AStar finds cheaper solution using previously visited nodes with higher solutions.
	// set breakpoint within AStarSearch to see it in action.
	@Test
	public void testAStarFindsCheaper() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{2,0,3},{1,5,4},{7,8,6}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		algs.model.searchtree.AStarSearch as = new algs.model.searchtree.AStarSearch(new GoodEvaluator());
		Solution sol = as.search(initial, goal);
		assertTrue (sol.succeeded());

		assertEquals (11, sol.numMoves());
		assertEquals (11, sol.moves().size());

	}

	// Test AStar fixed for 2nd edition. Won't always be higher number of moves
	@Test
	public void testAStarFixed() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{1,5,2},
				{0,7,3},
				{6,4,8}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},
				{8,0,4},
				{7,6,5}
		});

		algs.model.searchtree.AStarSearch as = new algs.model.searchtree.AStarSearch(new GoodEvaluator());
		Solution sol = as.search(initial.copy(), goal.copy());
		assertTrue (sol.succeeded());

		assertEquals (13, sol.numMoves());
		assertEquals (13, sol.moves().size());

		algs.model.searchtree.ClosedHeuristic ch = new algs.model.searchtree.ClosedHeuristic(new GoodEvaluator());
		sol = ch.search(initial.copy(), goal.copy());
		assertTrue (sol.succeeded());

		assertEquals (13, sol.numMoves());
		assertEquals (13, sol.moves().size());

	}

	// Test AStar finds cheaper solution using previously visited nodes with higher solutions.
	// set breakpoint within AStarSearch to see it in action.
	@Test
	public void testAStarDebugAlsoFindsCheaper() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{2,0,3},{1,5,4},{7,8,6}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		algs.model.searchtree.debug.AStarSearch as = new algs.model.searchtree.debug.AStarSearch(new GoodEvaluator());
		DottyDebugger dd = new DottyDebugger();
		as.debug(dd);
		Solution sol = as.search(initial, goal);
		assertTrue (sol.succeeded());

		assertEquals (11, sol.numMoves());
		assertEquals (11, sol.moves().size());

	}


	// failure test. These can never make a solution.
	@Test
	public void testNeverSucceeed() {
		EightPuzzleNode initial = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		EightPuzzleNode impossibleGoal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,5,6}
		});

		DepthFirstSearch dfs = new DepthFirstSearch(4);
		Solution sol = dfs.search(initial, impossibleGoal);
		assertFalse (sol.succeeded());

		assertEquals (0, sol.moves().size());
		assertEquals (0, sol.numMoves());
	}

	// test initial as goal.
	@Test
	public void testAStarExampleInitial() {
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		//BreadthFirstSearch<EightPuzzleNode> bfs = new BreadthFirstSearch<EightPuzzleNode>(start, goal);
		//DepthFirstSearch<EightPuzzleNode> bfs = new DepthFirstSearch<EightPuzzleNode>(start, goal, 16);
		algs.model.searchtree.AStarSearch as = new algs.model.searchtree.AStarSearch(new GoodEvaluator());
		//OrderedSearch<EightPuzzleNode> bfs = new OrderedSearch<EightPuzzleNode>(start, goal);
		Solution sol = as.search(goal, goal);
		assertTrue (sol.succeeded());

		assertEquals (0, sol.moves().size());
	}

	@Test
	public void testDFSExampleInitial() {
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		DepthFirstSearch dfs = new DepthFirstSearch(8);
		Solution sol = dfs.search(goal, goal);
		assertTrue (sol.succeeded());

		assertEquals (0, sol.moves().size());
	}

	@Test
	public void testBFSExampleInitial() {
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		BreadthFirstSearch bfs = new BreadthFirstSearch();
		Solution sol = bfs.search(goal, goal);
		assertTrue (sol.succeeded());

		assertEquals (0, sol.moves().size());

	}


	@Test
	public void testAStarExample() {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		//BreadthFirstSearch<EightPuzzleNode> bfs = new BreadthFirstSearch<EightPuzzleNode>(start, goal);
		//DepthFirstSearch<EightPuzzleNode> bfs = new DepthFirstSearch<EightPuzzleNode>(start, goal, 16);
		algs.model.searchtree.AStarSearch as = new algs.model.searchtree.AStarSearch(new GoodEvaluator());
		//OrderedSearch<EightPuzzleNode> bfs = new OrderedSearch<EightPuzzleNode>(start, goal);
		Solution sol = as.search(start, goal);
		assertEquals ("move 7,move 6,move 5,move 4,move 2,move 8,move 1,move 2",
				sol.toString());
	}

	@Test
	public void testDFSExample() {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		DepthFirstSearch dfs = new DepthFirstSearch(8);
		Solution sol = dfs.search(start, goal);
		assertEquals ("move 7,move 6,move 5,move 4,move 2,move 8,move 1,move 2",
				sol.toString());
	}

	@Test
	public void testBFSExample() {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		BreadthFirstSearch bfs = new BreadthFirstSearch();
		Solution sol = bfs.search(start, goal);
		assertEquals ("move 7,move 6,move 5,move 4,move 2,move 8,move 1,move 2",
				sol.toString());
	}
}
