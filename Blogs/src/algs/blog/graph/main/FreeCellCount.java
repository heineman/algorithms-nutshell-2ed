package algs.blog.graph.main;

import java.io.File;
import java.io.IOException;

import algs.blog.graph.count.Count;
import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;


/**
 * Compute Knuth's walk for free cell estimation from a board state selected
 * by number from a bank of 32,000 classic FreeCell deals.
 *
 * @author George Heineman
 */
public class FreeCellCount {

	/** 
	 * Receive as arguments:
	 *    1. Integer [0-32000] deciding which deal to select from the standard
	 *       collection of "classic" FreeCell games.
	 */
	public static void main (String []args) throws IOException {

		FreeCellNode fcn = Deal.initialize(new File("artifacts", "32000.txt"), Integer.valueOf(args[0]));
		System.out.println("Game Number:" + args[0]);
		System.out.println(fcn);
		for (short s : (short[])fcn.key()) {
			System.out.print(s + ",");
		}
		System.out.println();

		// look 16 moves into the future.
		Count.computeCount (fcn, 16);
	}
}
