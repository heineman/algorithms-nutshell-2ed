package algs.blog.improving.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Stack;

import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.DealIterator;
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
public class BoardDFSExploration {
	
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
		PrintWriter pw = new PrintWriter ("DFS-exploration.txt");
		
		// iterate over all deals in this file.
		for (DealIterator di = Deal.iterator(new File ("artifacts", "32000.txt")); di.hasNext(); ) {

			// prepare the initial board. Skip those boards we don't want.
			// Take care that board has already been advanced by iterator.
			FreeCellNode fc = di.next();
			pw.println(fc);
			pw.flush();
			System.out.println("working on: " + (di.getNextDealNumber()-1));
			
			AnalyzeState st = new AnalyzeState();
			DFS<short[]> dfs = new DFS<short[]>(st);
			Result res = dfs.fullSearch(fc, Deal.goal(), comp);
			
			Stack<IMove> sol = res.solution();
			System.out.println("Solution has " + sol.size() + " moves.");
			
			System.out.println("   Total number of states: " + dfs.getCounter());
			st.report();
		}
		pw.close();
	}
}
