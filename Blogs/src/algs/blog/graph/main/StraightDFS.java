package algs.blog.graph.main;


import java.io.File;

import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.Solution;

/**
 * Rely on default depth first search algorithm.
 * <p>
 * Note that the published algorithm doesn't do enough to limit the memory usage,
 * since we only need to store keys in our closed states, should those keys be 
 * sufficient to be able to identify which boards we no longer need to search.
 * <p>
 * Even changing the heap space to 512MB doesn't solve this problem.
 * 
 * @author George Heineman
 */
public class StraightDFS {

	public static void main(String[] args) throws Exception {
		int dealNumber = Integer.valueOf(args[0]);
		int depthBound = Integer.MAX_VALUE;
		if (args.length> 1) {
			depthBound = Integer.valueOf(args[1]);
		}
		
		File inputFile = new File ("artifacts", "32000.txt");
		FreeCellNode fcn = Deal.initialize(inputFile, dealNumber);
		
		System.out.println(fcn.toString());
		
		// unbounded search
		DepthFirstSearch dfs = new DepthFirstSearch(depthBound);
		
		try {
			Solution sol = dfs.search(fcn, Deal.goal());
			System.out.println(sol.toString());
			System.out.println("NumMoves:" + dfs.numMoves);
		} catch (Error e) {
			System.out.println("NumMoves:" + dfs.numMoves);
			e.printStackTrace();
		}
	}
}
