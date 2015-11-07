package algs.chapter7.figure17;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;
import algs.model.searchtree.Solution;
import algs.model.searchtree.Transition;
import algs.model.searchtree.states.StateStorageFactory;

/** 
 * Generate table of information Table 7-1.
 * 
 * Also provide information useful for the first observation following
 * Table 7-1.
 */
public class Main {

	static INodeSet open;
	static INodeSet closed;
	static GoodEvaluator ge = new GoodEvaluator();
	
	private static int maxDepth;
	private static ArrayList<String> nearMisses = new ArrayList<String>();
	
	/** See if within one move of solution. */
	private static boolean withinOne(INode n, INode goal) {
		DoubleLinkedList<IMove> moves = n.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove move = it.next();
			
			// Execute move on a copy since we maintain sets of board states
			INode successor = n.copy();
			move.execute(successor);
			if (ge.eval(successor) == 0) {
				return true;
			}
		}
		
		return false;   // not one move away.
	}
	
	/** See if within two moves of solution. */
	private static boolean withinTwo(INode n, INode goal) {
		// All successor moves translate into appended OPEN states.
		// Verify that we are not returning to previously visited state
		DoubleLinkedList<IMove> moves = n.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove move = it.next();
			
			// Execute move on a copy since we maintain sets of board states
			INode successor = n.copy();
			move.execute(successor);
			
			if (withinOne(successor, goal)) {
				return true;
			}
		}
		
		return false;   // not within two
	}
	
	/** See if within three moves of solution. */
	private static boolean withinThree(INode n, INode goal) {
		// All successor moves translate into appended OPEN states.
		// Verify that we are not returning to previously visited state
		DoubleLinkedList<IMove> moves = n.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove move = it.next();
			
			// Execute move on a copy since we maintain sets of board states
			INode successor = n.copy();
			move.execute(successor);
			
			if (withinTwo(successor, goal)) {
				return true;
			}
		}
		
		return false;   // not within three
	}
	
	private static void addAllToNearMisses(INode n) {
		// work our way backwards until we terminate at the initial state.
		while (n != null) {
			Transition trans = (Transition) n.storedData();
			
			// gone to the end!
			if (trans == null) {
				return;
			}
			
			n = trans.prev;
			nearMisses.add(n.key().toString());
			return; // just one.
		}
	}
	
	/** 
	 * A bit of a hack, here. We copy the code for search just so we
	 * can make the alteration to see if we are ever just one move 
	 * away from the solution.
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
					
				// If already visited, search this state no more. ALTERATION: See if this state is
				// one that (a) leads to a solution in less than 3 moves; and (b) has already been 
				// visited.
				if (closed.contains(successor) != null) {
					String key = successor.key().toString();
					if (nearMisses.contains(key)) {
						System.out.println("on Depth:" + trans.depth + " we encounter " + key + " which");
						System.out.println("leads to a solution but is in our closed set!");
						System.out.println ("search tree closed:" + (closed.size() + " open:" + open.size()));
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
					if (withinOne(successor, goal)) {
						System.out.println("Within one of solution and had to stop:\n" + successor);
						System.out.println ("search tree closed:" + (closed.size() + " open:" + open.size()));
						nearMisses.add(successor.key().toString());
						addAllToNearMisses(n);
					} else if (withinTwo(successor, goal)) {
						System.out.println("Within two of solution and had to stop:\n" + successor);
						System.out.println ("search tree closed:" + (closed.size() + " open:" + open.size()));
						nearMisses.add(successor.key().toString());
						addAllToNearMisses(n);
					} else if (withinThree(successor, goal)) {
						System.out.println("Within three of solution and had to stop:\n" + successor);
						System.out.println ("search tree closed:" + (closed.size() + " open:" + open.size()));
						nearMisses.add(successor.key().toString());
						addAllToNearMisses(n);
					}
				}
			}
		}
		
		// No solution.
		return new Solution (initial, goal, false);
	}
	
	public static void inspectHowClose() {
		EightPuzzleNode s2 = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		maxDepth = 25;
		Solution sol = search(s2, goal);
		long total = open.size() + closed.size();
		System.out.println(s2 + "," + total + "," + sol.numMoves());
	}

	public static void inspectHowCloseAnother() {
		EightPuzzleNode s2 = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		System.out.println("-------------------------N2 with depth 13 ---------------------");
		maxDepth = 13;
		Solution sol = search(s2, goal);
		long total = open.size() + closed.size();
		System.out.println(s2 + "," + total + "," + sol.numMoves());
	}

	
	public static void main(String[] args) {
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
		
		System.out.println("Now how close did we come on N2 w/ 25-ply depth.");
		inspectHowClose();
		inspectHowCloseAnother();
	}
}
