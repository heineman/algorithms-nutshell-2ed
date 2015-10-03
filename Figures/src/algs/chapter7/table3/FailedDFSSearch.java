package algs.chapter7.table3;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.Solution;

/**
 * Shows an interesting case where DFS fails to locate a solution that
 * should exist.
 * 
 * @author George Heineman
 *
 */
public class FailedDFSSearch {

	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	public static void main(String[] args) {
		// 8 moves away
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		
		// can't find! Because the horizon effect. Need more detailed analysis to
		// see WHICH was the failed one.
		DepthFirstSearch dfs2 = new DepthFirstSearch(13);
		Solution ds2 = dfs2.search(start.copy(), goal);
		System.out.println("Solution status:" + ds2.succeeded());
	}
		
}
