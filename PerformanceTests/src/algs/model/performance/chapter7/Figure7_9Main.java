package algs.model.performance.chapter7;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.debug.BreadthFirstSearch;

public class Figure7_9Main {

	public static void main(String[] args) {
		testFigure();
		testExtendedFigure();
	}

	public static void testFigure() {
		System.out.println ("Real Figure 7_9");
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{2,8,3},{1,6,4},{7,0,5}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		DottyDebugger std = new DottyDebugger();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.debug(std);

		bfs.search(start, goal);
		System.out.println (std.getInputString());
	}

	public static void testExtendedFigure() {
		// too big to be readable.
		System.out.println ("Extended Figure 7_9");
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		DottyDebugger std = new DottyDebugger();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.debug(std);

		bfs.search(start, goal);
		System.out.println (std.getInputString());
	}
}
