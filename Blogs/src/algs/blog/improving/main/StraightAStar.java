package algs.blog.improving.main;

import java.io.File;
import java.io.IOException;


import algs.blog.improving.freeCell.Deal;
import algs.blog.improving.freeCell.FreeCellEvaluator;
import algs.blog.improving.freeCell.FreeCellNode;
import algs.model.searchtree.AStarSearch;
import algs.model.searchtree.Solution;

/**
 * Rely on default AStar to search.
 * <p>
 * Note that the published algorithm doesn't do enough to limit the memory usage, since we
 * only need to store keys in our closed states should those keys be sufficient to be able
 * to identify which boards we no longer need to search.
 * <p>
 * Even changing the heap space to 512MB doesn't solve this.
 * 
 * @author George Heineman
 */
public class StraightAStar {

	public static void main (String[] args) throws IOException {
		int dealNumber = Integer.valueOf(args[0]);
		
		FreeCellNode fcn = Deal.initialize(new File ("32000.txt"), dealNumber);
		System.out.println(fcn.toString());
		
		// stop any free cell moves. Can still solve some games.
		AStarSearch as = new AStarSearch(new FreeCellEvaluator());
		try {
			Solution sol = as.search(fcn, Deal.goal());
			System.out.println(sol.toString());
		} catch (Error e) {
			System.out.println("NumMoves:" + as.numMoves);
			e.printStackTrace();
		}
	}
}
