package algs.model.performance.chapter7.search;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.searchtree.Solution;


public class SmallSuccessMain {

	public static void main(String[] args) {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		algs.model.searchtree.debug.DepthFirstSearch dfs =
			new algs.model.searchtree.debug.DepthFirstSearch(27);
		TicTacToeDebugger std = new TicTacToeDebugger();
		std.ordering(TicTacToeDebugger.DepthFirstOrdering);  // needed to ensure proper ordering for depth-first search.
		dfs.debug(std);
		Solution sol = dfs.search(start, goal);
		System.out.println (sol.toString());
		
		System.out.println (std.getInputString());
		String s1 = sol.toString();
		System.out.println (s1);
		System.out.println (sol.moves().size() + " moves");
		
	}
}
