package algs.model.performance.chapter7;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;
import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;

/** 
 * Generate table of information Table 7-1
 */
public class Table7_1Main  {

	static INodeSet open;
	static INodeSet closed;
	static GoodEvaluator ge = new GoodEvaluator();
	private static int maxDepth = 25;
	private static int bestProbe = 999;
	private static int closedMax = 999;
	
	/** See if within one move of solution. */
	private static boolean withinOne(INode n, INode goal) {
		// All successor moves translate into appended OPEN states.
		// Verify that we are not returning to previously visited state
		DoubleLinkedList<IMove> moves = n.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove move = it.next();
			
			// Execute move on a copy since we maintain sets of board states
			INode successor = n.copy();
			move.execute(successor);
				
			if (ge.eval(successor) < bestProbe) {
				bestProbe = ge.eval(successor);
				if (bestProbe == 17) {
					System.out.println ("search tree closed:" + (closed.size() + " open:" + open.size()));
				}
				System.out.println ("close (" + bestProbe + "):\n" + successor);
				return true;
			}
		}
		
		return false;
	}
	
	/** 
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 */
	private static Solution search(INode initial, INode goal) {
		// If goal is initial, return now.
		if (initial.equals(goal)) { return new Solution (initial, goal); }
		
		open = StateStorageFactory.create(StateStorageFactory.STACK);
		open.insert(initial.copy()); 
		
		// states we have already visited.
		closed = StateStorageFactory.create(StateStorageFactory.HASH);
		while (!open.isEmpty()) {
			INode n = open.remove();
			closed.insert(n);
			
			// Prepare for computations
			DepthTransition trans = (DepthTransition) n.storedData();
			
			// All successor moves translate into appended OPEN states.
			// Verify that we are not returning to previously visited state
			DoubleLinkedList<IMove> moves = n.validMoves();
			for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
				IMove move = it.next();
				
				// Execute move on a copy since we maintain sets of board states
				INode successor = n.copy();
				move.execute(successor);
					
				// If already visited, search this state no more
				if (closed.contains(successor) != null) {
					if (ge.eval(successor) < closedMax) {
						closedMax = ge.eval(successor);
						System.out.println("closed and not inspecting (" + closedMax + "):\n" + successor);
					}
					continue;
				}
				
				int depth = 1;
				if (trans != null) { depth = trans.depth+1; }
					
				// Record previous move for solution trace. If solution, leave 
				// now, otherwise add to the OPEN set if we are within depth bound.
				successor.storedData(new DepthTransition(move, n, depth));
				if (successor.equals(goal)) {
					return new Solution (initial, successor);
				}
				if (depth < maxDepth) { 
					open.insert (successor);
				} else {
					
					// see if 'n' is one away from a valid solution.
					withinOne(successor, goal);
				}
			}
		}
		
		// No solution.
		return new Solution (initial, goal, false);
	}
	
	/** Come close to solving but never there. */
	public static void testFindMissing() {
		EightPuzzleNode s2 = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		Solution sol = search(s2, goal);
		long total = open.size() + closed.size();
		System.out.println(s2 + "," + total + "," + sol.numMoves());
		
		// inspect these closed states, and find one that has minimum 
		// evaluation, based on GoodEvaluation.
		GoodEvaluator ge = new GoodEvaluator();
		int max = Integer.MAX_VALUE;
		INode best =null;
		for (Iterator<INode> it = closed.iterator(); it.hasNext(); ) {
			INode n = it.next();
			if (ge.eval(n) < max) {
				max = ge.eval(n);
				best = n;
			}
		}
		
		System.out.println ("Closest:\n" + best + "(score:" + max + ")");
	}
	
	public static void testDFS() {
		EightPuzzleNode s1 = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		EightPuzzleNode s2 = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		EightPuzzleNode s3 = new EightPuzzleNode(new int[][]{
				{1,4,0},{7,3,2},{6,8,5}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		EightPuzzleNode nodes [] = new EightPuzzleNode[] {s1, s2, s3};
		for (int k = 0; k < nodes.length; k++) {
			System.out.println (nodes[k]);
			for (int i = 1; i < 31; i++) {
				algs.model.searchtree.DepthFirstSearch dfs;
				if (i == 30) {
					// unbounded
					dfs = new algs.model.searchtree.DepthFirstSearch();
				} else {
					dfs = new algs.model.searchtree.DepthFirstSearch(i);
				}
				
				dfs.storageType(StateStorageFactory.HASH);
				Solution sol = dfs.search(nodes[k], goal);
				long total = dfs.numOpen + dfs.numClosed;
				System.out.println(i + "," + total + "," + sol.numMoves());
			}
		}
	}
	
	public static void main(String[] args) {
		testDFS();
		testFindMissing();
	}
}
