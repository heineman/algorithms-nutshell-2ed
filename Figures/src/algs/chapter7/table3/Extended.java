package algs.chapter7.table3;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.*;

/**
 * Show trend of A* for larger and larger N, but only for A*
 * 
 * @author George Heineman
 */
public class Extended  {

	/** Goal node. */
	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	/** Randomly choose a number of boards and make sure they are all unique */
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
			int numFailures = 5;
			while (numFailures > 0) {
				try {
					int rnd = (int)(Math.random() * nodes.size());
					prev = nodes.get(rnd);
					break;
				} catch (IndexOutOfBoundsException ioobe) {
					numFailures--;
				}
			}
			
			visited.add(prev);
			n--;
		}
		
		return prev;
	}
	
	/**
	 * Generate the extended table for A* searches.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int T = 1000;
		
		for (int n = 2; n <= 30; n += 1) {			
			int totalsA = 0;
			int statesA = 0;
			int failedA = 0;
			
			for (int t = 0; t < T; t++) {
				// Run BFS
				// Run DFS-unbounded
				// Run DFS-depth-2*N
				// Run DFS-depth-N
				INode start = randomize(n);
				AStarSearch as = new AStarSearch(new GoodEvaluator());
				Solution asol = as.search(start, goal);
				if (!asol.succeeded()) {
					failedA++;
				}
				totalsA += asol.numMoves();
				statesA += as.numClosed + as.numOpen;
			}

			float avgA = totalsA;
			avgA /= (T - failedA);
			
			System.out.print (n + "," + statesA + ",");
			System.out.print (avgA); if (failedA != 0) { System.out.print ("(" + failedA + ")");	}
			System.out.println();
		}
	}
}
