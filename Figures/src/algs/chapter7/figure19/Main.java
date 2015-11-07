package algs.chapter7.figure19;

import algs.debug.DottyDebugger;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.debug.BreadthFirstSearch;

public class Main {
	public static void main(String[] args) {
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
}
