package algs.chapter7.table3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.*;

/**
 * Attempt head to head comparison of BFS/DFS.
 * 
 * For N=2,4,8,16 path lengths
 *    Generate T=10 random boards with those moves
 *         Run BFS
 *         Run DFS-depth-2*N
 *         Run DFS-depth-N
 *         Run AStarSearch
 *         
 * Compare by (a) Number of nodes searched; (b) solution length found.         
 * 
 *
 * @author George Heineman
 */
public class Main  {

	/** Properly format table numbers .*/
	static NumberFormat nf;
	
	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	
	public static INode randomize (int n) {
		
		INode prev = goal;
		ArrayList<INode> visited = new ArrayList<INode>();
		visited.add(goal);
		while (n > 0) {
			ArrayList<INode> nodes = new ArrayList<INode>();
			for (Iterator<IMove> it = prev.validMoves().iterator(); it.hasNext(); ) {
				
				INode copy = prev.copy();
				IMove move = it.next();
				move.execute(copy);
				copy.storedData(new Transition (move, prev));
				
				// add only if not yet visited
				if (!visited.contains(copy)) {
					nodes.add(copy);
				}
			}
			
			// select one at random.
			int rnd = (int)(Math.random() * nodes.size());
			prev = nodes.get(rnd);
			visited.add(prev);
			n--;
		}
		
		return prev;
	}
	
	public static void main(String[] args) {
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		nf.setGroupingUsed(false);
		
		int T = 1000;
		
		System.out.println ("n\t#A*\t#BFS\t#DFSn\t#DFS2n\tsA*\tsBFS\tsDFSn\tsDFS2n");
		for (int n = 2; n <= 14; n += 1) {			
			float totalsB=0, totalsD=0, totalsD2N = 0, totalsA = 0;
			float statesB=0, statesD=0, statesD2N = 0, statesA = 0;
			int failedB=0, failedD=0, failedD2N = 0, failedA = 0;
			
			for (int t = 0; t < T; t++) {
				// Run BFS
				// Run DFS-unbounded
				// Run DFS-depth-N
				// Run DFS-depth-2*N
				INode start = randomize(n);
				BreadthFirstSearch bfs = new BreadthFirstSearch();
				Solution bs = bfs.search(start.copy(), goal);
				if (!bs.succeeded()) {
					failedB++;     // should never happen.
				}
				totalsB += bs.numMoves();
				statesB += bfs.numClosed + bfs.numOpen;
					
				DepthFirstSearch dfs = new DepthFirstSearch(n);
				Solution ds = dfs.search(start.copy(), goal);
				if (!ds.succeeded()) {
					failedD++;
				}
				totalsD += ds.numMoves();
				statesD += dfs.numClosed + dfs.numOpen;
				
				DepthFirstSearch dfs2n = new DepthFirstSearch(2*n);
				Solution ds2n = dfs2n.search(start.copy(), goal);
				if (!ds2n.succeeded()) {
					failedD2N++;
				}
				totalsD2N += ds2n.numMoves();
				statesD2N += dfs2n.numClosed + dfs2n.numOpen;
				
				AStarSearch as = new AStarSearch(new GoodEvaluator());
				Solution asol = as.search(start, goal);
				if (!asol.succeeded()) {
					failedA++;
				}
				totalsA += asol.numMoves();
				statesA += as.numClosed + as.numOpen;

				if (bs.numMoves() != asol.numMoves()) {
					System.out.println(bs);
					System.out.println(asol);
					
				}
			}
			
			totalsB /= (T - failedB);
			totalsA /= (T - failedA);
			totalsD /= (T - failedD);
			totalsD2N /= (T - failedD2N);
			
			// these are still derived from T even though failure to find move
			statesB /= T;
			statesA /= T;
			statesD /= T;
			statesD2N /= T;
			
			System.out.print (n + "\t" + out(statesA) + "\t" + out(statesB) + "\t" + out(statesD) + "\t" + out(statesD2N) + "\t");
			System.out.print (out(totalsA)); if (failedA != 0) { System.out.print ("(" + failedA + ")");}
			System.out.print ("\t");
			System.out.print (out(totalsB)); if (failedB != 0) { System.out.print ("(" + failedB + ")");}
			System.out.print ("\t");
			System.out.print (out(totalsD)); if (failedD != 0) { System.out.print ("(" + failedD + ")");	}
			System.out.print ("\t");
			System.out.print (out(totalsD2N)); if (failedD2N != 0) { System.out.print ("(" + failedD2N + ")");	}
			System.out.println();
		}
	}

	/** Helper method for formatting table. */ 
	private static String out(float f) {
		return nf.format(f);
	}
}
