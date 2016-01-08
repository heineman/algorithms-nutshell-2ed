package algs.blog.improving.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

import algs.blog.graph.freeCell.BoardScorer;
import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.search.GoalDirectedStagedDeepening;
import algs.blog.graph.search.Result;
import algs.model.searchtree.IMove;
import algs.model.searchtree.IScore;

/**
 * Apply FreeCellExploration to a specific set of board numbers, as stored in a file
 * passed in as an argument.
 * 
 * @author George Heineman
 */
public class AttackUnsolvedBoards {

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
		
		// all output to this file.
		File f = new File ("Attack.report.txt");
		FileWriter fw = new FileWriter (f);
		pw = new PrintWriter (fw);
		
		FreeCellNode goal = Deal.goal();
		
		Scanner sc = new Scanner(new File(args[0]));
		while (sc.hasNext()) {
			int dealNumber = sc.nextInt();
			
			System.out.println("Searching board:" + dealNumber);
			FreeCellNode fc = Deal.initialize(new File ("artifacts", "32000.txt"), dealNumber);
			System.out.println(fc.toString());
			
			// prepare the initial board. Skip those boards we don't want.
			// Take care that board has already been advanced by iterator.
	
			pw.println("working on: " + dealNumber);
			pw.println(fc);
			pw.flush();
			System.out.println("working on: " + dealNumber);

			IScore eval = new BoardScorer();
			GoalDirectedStagedDeepening<short[]> gdsd =
				new GoalDirectedStagedDeepening<short[]>(goal, eval);
			
			// lookahead 7	
			gdsd.setLookAhead(7);
			
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
		sc.close();
	}
}
