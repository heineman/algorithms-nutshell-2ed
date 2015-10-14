package algs.blog.graph.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;



import algs.blog.graph.freeCell.BoardScorer;
import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.DealIterator;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.search.GoalDirectedStagedDeepening;
import algs.blog.graph.search.Result;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Explore all 32,000 boards from the classic FreeCell implementation and 
 * see which ones can be solved using the StagedDeepening algorithm.
 * <p>
 * This execution may take several days! You can easily restart the computation
 * from a specific spot using command line arguments.
 * 
 * @author George Heineman
 */
public class FreeCellExploration {

	/** Output generated to this PrintWriter. */
	static PrintWriter pw;
	
	/**
	 * Load up series of deals from the file "32000.txt" and process
	 * those between argv[0] and argv[1]. These are both strings.
	 * If no arguments, then process all; If just one, then process
	 * starting from that point onwards. If both, then process the
	 * range inclusively.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main (String []args) throws IOException {
		int low = 0;
		int high = 32000;
		if (args == null || args.length == 0) {
			// leave as original
		} else if (args.length == 1) {
			low = Integer.valueOf(args[0]);
		} else if (args.length >= 2) {
			low = Integer.valueOf(args[0]);
			high = Integer.valueOf(args[1]);
			if (args.length > 2) {
				System.err.println("Ignoring all command arguments but " + 
								   args[0] + " and " + args[1]); 
			}
		}

		
		// all output to this file.
		File f = new File ("Output.report.txt");
		FileWriter fw = new FileWriter (f);
		pw = new PrintWriter (fw);
		
		FreeCellNode goal = Deal.goal();
		
		// iterate over all deals in this file.
		System.out.println("Processing boards [" + low + "," + high + "] to " + f);
		File inputFile = new File ("artifacts", "32000.txt");
		for (DealIterator di = Deal.iterator(inputFile); di.hasNext(); ) {

			// prepare the initial board. Skip those boards we don't want.
			// Take care that board has already been advanced by iterator.
			INode fc = di.next();
			if (di.getNextDealNumber()-1 < low || di.getNextDealNumber()-1 > high) {
				continue;
			}

			pw.println("Search for:" + (di.getNextDealNumber()-1));
			pw.println(fc);
			pw.flush();
			System.out.println("working on: " + (di.getNextDealNumber()-1));

			IScore eval = new BoardScorer();
			GoalDirectedStagedDeepening<short[]> gdsd =
				new GoalDirectedStagedDeepening<short[]>(goal, eval);
			
			Result res = gdsd.fullSearch(fc, eval, FreeCellNode.comparator());
			Stack<IMove> st = res.solution();
			if (res.success) {
				System.out.println("  Solution found!");
				pw.println(st.size() + " moves");
			} else {
				System.out.println(" No solution uncovered.");
				pw.println("  NO SOLUTION FOUND!");
			}

			// store all results found...
			pw.flush();
		}
	}
}
