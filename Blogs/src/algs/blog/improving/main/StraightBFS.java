package algs.blog.improving.main;

import java.io.IOException;


import algs.blog.improving.freeCell.Deal;
import algs.blog.improving.freeCell.FreeCellNode;
import algs.model.searchtree.BreadthFirstSearch;
import algs.model.searchtree.Solution;

/**
 * Rely on default breadth first search algorithm.
 * 
 * @author George Heineman
 */
public class StraightBFS  {

	
	public static void main(String[] args) throws IOException {
		int dealNumber = Integer.valueOf(args[0]);
		
		FreeCellNode fcn = Deal.initialize(new java.io.File("32000.txt"), dealNumber);
		
		System.out.println(fcn.toString());
		
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		try {
			Solution sol = bfs.search(fcn, Deal.goal());
			System.out.println(sol.toString());
		} catch (Error e) {
			System.out.println("NumMoves:" + bfs.numMoves);
			e.printStackTrace();
		}
	}
}
