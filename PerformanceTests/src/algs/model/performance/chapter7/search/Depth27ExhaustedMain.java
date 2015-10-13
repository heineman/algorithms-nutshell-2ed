package algs.model.performance.chapter7.search;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.Solution;


public class Depth27ExhaustedMain {

	public static void main(String[] args) {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		algs.model.searchtree.DepthFirstSearch dfs =
			new algs.model.searchtree.DepthFirstSearch(27);
		
		System.gc();
		long now1 = System.currentTimeMillis();
		Solution sol = dfs.search(start, goal);
		long now2 = System.currentTimeMillis();
		System.out.println((now2-now1) + " milliseconds");
		System.out.println (sol.toString());
		
		System.out.println (sol.moves().size() + " moves");
	}
}
