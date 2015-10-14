package algs.blog.graph.main;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;


import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.search.AnalyzeState;
import algs.blog.graph.search.DFS;
import algs.blog.graph.search.Result;
import algs.model.searchtree.IMove;


/**
 * Must expand StackSize to run this one (also Heap space)
 * 
 *   -Xss32768k -Xmx1024m
 *    
 * @author George Heineman
 */
public class DFSExploration {
	
	/**
	 * args[0] contains the name of a file in which one finds the 
	 * list of games (one per line) that were not solved using the
	 * Staged Deepening
	 *   
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// comparator to use.
		Comparator<short[]> comp = FreeCellNode.comparator();

		Scanner sc = new Scanner(new File(args[0]));
		while (sc.hasNext()) {
			int dealNumber = sc.nextInt();
			
			System.out.println("Searching board:" + dealNumber);
			FreeCellNode fcn = Deal.initialize(new File ("artifacts", "32000.txt"), dealNumber);
			System.out.println(fcn.toString());
			
			AnalyzeState st = new AnalyzeState();
			DFS<short[]> dfs = new DFS<short[]>(st);
			Result res = dfs.fullSearch(fcn, Deal.goal(), comp);
			
			Stack<IMove> sol = res.solution();
			System.out.println("Solution has " + sol.size() + " moves.");
			
			System.out.println("   Total number of states: " + dfs.getCounter());
			st.report();
		}
		sc.close();
	}
}
