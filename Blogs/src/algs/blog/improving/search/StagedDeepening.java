package algs.blog.improving.search;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import algs.blog.graph.search.Chain;
import algs.blog.graph.search.IVisitor;
import algs.blog.graph.search.Result;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;
import algs.model.tree.BalancedTree;

/**
 * S = {initial}
 * while (S not empty) 
 *   foreach board in S
 *     search K moves ahead, and place all boards at the Kth level into T
 *   order T by some evaluation function.
 *   S = T
 * end while
 * 
 * @param  <K>    key type for each node.
 * @author George Heineman
 */
public abstract class StagedDeepening<K> {

	/** Current state of search. */
	protected INode node;

	/** Previous states that have been visited. Key=node key. */
	protected BalancedTree<K,Integer> prev;
	
	/**
	 * Future boards to be searched are inserted into balanced tree
	 * according to their evaluation.
	 */
	BalancedTree<Integer,INode> T;
	
	/** How much state can accumulate before it is thrown away. */
	int MAX_SIZE = 200000;

	/** Provides a bridge to the earlier search pass. */
	Chain last = null;
	
	/** Stack of moves from initial board to current state. */
	java.util.Stack<IMove> moveStack;

	/** Full depth look ahead this number of moves. */
	int K = 6;
	
	/** Heuristic to rate boards. */
	protected IScore eval;
	
	/** Visited unique counter. */
	int visited = 0;
	
	/** Visitor for nodes and edges. Provides empty default. Can be changed. */
	IVisitor visitor = new IVisitor() {
		public void visitEdge(int parent, int child) {}
		public void visitNode(INode n, int id) {}
	};
	
	/**
	 * Construct Staged Deepening agent with given behavior for visiting nodes and edges.
	 * 
	 * @param visitor
	 */
	public StagedDeepening (IVisitor visitor) {
		if (visitor != null) {
			this.visitor = visitor;
		}
	}
	
	/** Choose an alternate number of moves to look ahead. */
	public void setLookAhead (int k) {
		if (k < 1) {
			throw new IllegalArgumentException ("LookAhead value must be > 1");
		}
		
		K = k;
	}
	
	/** Choose an alternate maximum number of states to store. */
	public void setMaxSize(int ms) {
		if (ms < 0) {
			throw new IllegalArgumentException ("MaxSize value must be >0");
		}
		
		MAX_SIZE = ms;
	}
	
	/**
	 * Useful debugging routine.
	 * 
	 * @param n
	 */
	static void debug(INode n) {
		System.out.println(n);
		Chain ch = (Chain) n.storedData();
		if (ch == null) {
			System.out.println("  INITIAL BOARD");
			return;
		}
		
		// must reverse the chain
		Stack<Chain> chainStack = new Stack<Chain>();
		while (ch != null) {
			chainStack.push(ch);
			ch = ch.previous;
		}
		
		while (!chainStack.isEmpty()) {
			ch = chainStack.pop();
			for (Iterator<IMove> it = ch.moves.iterator(); it.hasNext(); ) {
				System.out.println(it.next());
			}
		}
	}

	/**
	 * Return stack of moves from initial state that leads to this final state
	 *  
	 * @param n  Final State
	 */
	public static Stack<IMove> computeSolution(INode n) {
		Stack<IMove> res = new Stack<IMove>();
		System.out.println(n);
		Chain ch = (Chain) n.storedData();
		if (ch == null) {
			return res;
		}
		
		// must reverse the chain
		Stack<Chain> chainStack = new Stack<Chain>();
		while (ch != null) {
			chainStack.push(ch);
			ch = ch.previous;
		}
		
		while (!chainStack.isEmpty()) {
			ch = chainStack.pop();
			for (Iterator<IMove> it = ch.moves.iterator(); it.hasNext(); ) {
				res.push(it.next());
			}
		}
		
		return res;
	}
	
	/**
	 * Determines if the search is successful.
	 * <p>
	 * Common implementations of this method are to either (a) compare directly
	 * against a board state; or (b) evaluate the board for quality.
	 *  
	 * @param n
	 * @return <code>true</code> if the staged deepening search can terminate.
	 */
	public abstract boolean searchComplete(INode n);

	/**
	 * Primary worker routine to launch the staged deepening algorithm.
	 * <p>
	 * This algorithm terminates when its search tree is exhausted, or a
	 * satisfactory end state has been reached, as determined by the subclass.
	 * 
	 * @param start
	 * @param goal
	 * @param comp   comparator for nodes.
	 * @return
	 */
	public Result fullSearch (INode start, IScore eval, Comparator<K> comp) {
		
		node = start;
		this.eval = eval;
		
		prev = new BalancedTree<K,Integer>(comp);

		// conduct search
		moveStack = new java.util.Stack<IMove>();
		BalancedTree<Integer,INode> S = new BalancedTree<Integer,INode>();
		T = new BalancedTree<Integer,INode>();
		S.insert(eval.eval(node), node.copy());
		
		int lastID;
		while (S.size() > 0) {
			
			node = S.minimum();
			
			// last must be set PRIOR to invoking search, since it is used for
			// linking solutions. moveStack must be instantiated anew also, so
			// we only create a stack of moves from S.min to new K-distant nodes.
			last = (Chain) node.storedData();
			moveStack = new Stack<IMove>();
			
			Chain chain = new Chain (stackCopy (moveStack), last);
			node.storedData(chain);

			if (last == null) {
				lastID = 0;
			} else {
				lastID = last.lastID;
			}
			
			if (search(lastID, 0)) {
				// update stored move information.
				chain = new Chain (stackCopy (moveStack), chain);
				node.storedData(chain);
				moveStack = computeSolution(node);
				Result res = new Result(moveStack);
				return res;
			}

			S = T;
		}	
		
		Result res = new Result();
		return res;
	}
	
	/**
	 * Recursive routine to continue the search at the given depth.
	 * 
	 * @param depth
	 */
	@SuppressWarnings("unchecked")
	private boolean search(final int nodeId, int depth) {
		if (searchComplete(node)) {
			return true;
		}

		if (depth > K ) {
			if (prev.size() > MAX_SIZE) {
				System.err.println("Emptying prev set:" + prev.size() + " entries...");
				prev.clear();  // empty out 
				System.gc();
			}
			
			// record that we've been here
			prev.insert((K) node.key(), visited);      
			int score = eval.eval(node);
			
			// store this node in T and maintain back-link so we can 
			// properly reconstruct the move sequence. Last is externally
			// set to reflect sequence of moves that got to the node from
			// which the initial search(0) invocation was executed. We chain
			// moves so we don't have to store all previous board states.
			INode aCopy = node.copy();
			aCopy.storedData(new Chain(stackCopy(moveStack), last));
			T.insert(score, aCopy);
			return false;
		}

		DoubleLinkedList<IMove> s = node.validMoves();
		DoubleNode<IMove> st = s.first();
		while (st != null) {
			IMove move = st.value();
			move.execute(node);  // make move
			
			K key = (K) node.key();
			Integer exist = prev.search(key);
			if (exist == null) {
				visited++;
				visitor.visitNode(node, visited);
				visitor.visitEdge(nodeId, visited);
				
				if (prev.size() > MAX_SIZE) {
					System.err.println("Emptying prev set:" + prev.size() + " entries...");
					prev.clear();  // empty out
					System.gc();
				}

				prev.insert(key, visited);           // record that we've been here
				moveStack.push(move);                // add to move stack 
				if (search (visited,depth+1)) {      // keep going. Might solve!
					return true;
				}
				moveStack.pop();                     // remove from move stack
			}
			
			move.undo(node);  		                 // Undo move
			st = st.next();                          // advance to next move
		}

		return false;                                // dead-end.
	}

	/**
	 * Helper routine to copy stacks which need to be maintained for linking
	 * in solutions.
	 * 
	 * @param existing    stack to be copied.
	 */
	private static Stack<IMove> stackCopy(Stack<IMove> existing) {
		Stack<IMove> aCopy = new Stack<IMove>();
		for (Iterator<IMove> it = existing.iterator(); it.hasNext(); ) {
			aCopy.push(it.next());
		}

		return aCopy;
	}

}
