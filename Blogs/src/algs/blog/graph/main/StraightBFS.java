package algs.blog.graph.main;

import java.io.File;
import java.io.IOException;


import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
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
		
		File inputFile = new File ("artifacts", "32000.txt");
		FreeCellNode fcn = Deal.initialize(inputFile, dealNumber);
		
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
