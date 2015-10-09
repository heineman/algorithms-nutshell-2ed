package algs.blog.graph.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;


import algs.blog.graph.freeCell.BoardScorer;
import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;


/**
 * must run with EXTENDED stacks.
 * 
 * Generate ncol formatted file for the given graph.
 * <p>
 * The NCol format simply stores lines of the form "id1 id2" in a file, where
 * each id is an integer id that refers to a node in the graph. Use the converters
 * in the <code>converters</code> package to convert into various alternative formats.
 * 
 * @author George Heineman
 */
public class StagedDeepeningGraph implements IVisitor {

	/** Where output is to be stored. */
	PrintWriter out;
	
	/** used for hashtable. */
	static final Object dummy = new Object();
	
	/** Past edges. */
	Hashtable<String, Object> pastEdges = new Hashtable<String, Object>();
	
	/** Show board states or not. */
	public boolean showBoardStates = false;
	
	/** Show edges or not. */
	public boolean showEdges = true;

	/**
	 * Output sent to this file. 
	 * 
	 * @param file
	 * @throws FileNotFoundException 
	 */
	public StagedDeepeningGraph(File file) throws FileNotFoundException {
		out = new PrintWriter(file);
	}
	
	public static void main(String[] args) throws IOException {
		FreeCellNode fcn;
		int dealNumber = 16; // 11982; // 16;
		
		File inputFile = new File ("artifacts" + File.separator + "graph" + File.separator + "32000.txt");
		fcn = Deal.initialize(inputFile, dealNumber);
		System.out.println(fcn.toString());
			
		StagedDeepeningGraph sdg = new StagedDeepeningGraph(new File ("outputStaged_" + dealNumber + ".ncol"));
		
		//DFS<short[]> dfs = new DFS<short[]>(dg);
		//Result res = dfs.fullSearch(fcn, Deal.goal(), FreeCellNode.comparator());
		
		
		IScore eval = new BoardScorer();
		GoalDirectedStagedDeepening<short[]> gdsd =
			new GoalDirectedStagedDeepening<short[]>(Deal.goal(), eval, sdg);
		
		Result res = gdsd.fullSearch(fcn, eval, FreeCellNode.comparator());
		sdg.close();
		
		
		System.out.println("Solution found:" + res.solution().size() + " moves.");
	}

	protected void close() {
		out.flush();
		out.close();
	}

	@Override
	public void visitEdge(int nodeId, int node2) {
		if (!showEdges) { return; }
		
		String key = new StringBuilder(' ').append(nodeId).append(' ').append(node2).toString();
		if (pastEdges.containsKey(key)) {
			return;
		}
		String keyR = new StringBuilder(' ').append(node2).append(' ').append(nodeId).toString();
		if (pastEdges.containsKey(keyR)) {
			return;
		}
		
		if (nodeId == 0) {
			nodeId = 0;
		}
		
		out.println(key);
		pastEdges.put(key, dummy);
	}


	@Override
	public void visitNode(INode n, int nodeid) {
		if (!showBoardStates) { return; }
	
		short[] key = (short[])n.key();
		byte[]bytes = new byte[key.length]; for (int i = 0; i < key.length; i++) { bytes[i] = (byte)(34+key[i]); }
		String stringz = new String(bytes);
		
		out.println("# " + stringz + " " + nodeid);
	}

}
