package algs.blog.graph.search;

import java.util.Comparator;
import java.util.Hashtable;


import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;
import algs.model.tree.BalancedTree;

/**
 * Pursue a full depth first search, until stack overflow.
 * 
 * @param  <K>    key type for each node.
 * @author George Heineman
 */
public class DFS<K> {

	/** Ultimate destination. */
	INode goal;

	/** Current state. */
	INode node;

	/** Past record of visited locations. */
	BalancedTree<K,Integer> prev;

	/** record stats for dead-ends. */
	Hashtable<Integer,long[]> deadEnd ;

	/** record stats for how many 'repeated states' found, and at what level. */
	Hashtable<Integer,long[]> duplicated;

	/** moves that achieve final position. */
	java.util.Stack<IMove> moveStack;

	/** Counter used for unique identifiers for vertices. */
	int ctr = 0;
	
	/** Depth limit beyond which DFS does not attempt a search. */
	int depthLimit = 0;

	/** Visitor for nodes and edges. Provides empty default. Can be changed. */
	IVisitor visitor = new IVisitor() {
		public void visitEdge(int parent, int child) {}
		public void visitNode(INode n, int id) {}
	};

	/** Construct DFS agent with default visitor. */
	public DFS () {

	}

	/**
	 * Construct DFS agent with given behavior for visiting nodes and edges.
	 * 
	 * @param visitor
	 */
	public DFS (IVisitor visitor) {
		if (visitor != null) {
			this.visitor = visitor;
		}
	}

	/** Set the depth limit beyond which no search can continue. */
	public void setDepthLimit(int limit) {
		depthLimit = limit;
	}
	
	/** For debugging, return the total count of nodes searched. */
	public int getCounter() {
		return ctr;
	}
	
	public Result fullSearch (INode start, INode goal, Comparator<K> comp) {
		node = start;
		this.goal = goal;

		ctr = 0;
		deadEnd = new Hashtable<Integer,long[]>();
		duplicated = new Hashtable<Integer,long[]>();
		moveStack = new java.util.Stack<IMove>();
		prev = new BalancedTree<K, Integer>(comp);

		if (visitor != null) {
			visitor.visitNode(node, ctr);
		}

		Result res = new Result();
		if (search(0, ctr)) {
			res = new Result(moveStack);
		}

		res.setDeadEndStates(deadEnd);
		res.setDuplicatedStates(duplicated);
		return res;
	}

	/**
	 * Determines if the search is successful.
	 * <p>
	 * Common implementations of this method are to either (a) compare directly
	 * against a board state; or (b) evaluate the board for quality. You may 
	 * override this method as you wish.
	 *  
	 * @param n
	 * @return <code>true</code> if the search can terminate.
	 */
	public boolean searchComplete(INode n) {
		return n.equals(goal);
	}

	/**
	 * Generate full graph given DFS full search of the problem space.
	 * 
	 * @param depth    depth of search.
	 * @return <code>true</code> if search completes successfuly; <code>false</code>
	 *         otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean search(int depth, int nodeId) {
		if (searchComplete(node)) {
			return true;
		}

		// sorry this is all.
		if (depthLimit > 0 && depth > depthLimit) {
			return false;
		}
		
		ctr++;
		DoubleLinkedList<IMove> s = node.validMoves();
		DoubleNode<IMove> st = s.first();
		while (st != null) {
			IMove move = st.value();
			move.execute(node);    				// make move
			moveStack.push(move); 				// record move

			K key = (K) node.key();
			Integer exist = prev.search(key);
			if (exist == null) {
				visitor.visitNode(node, ctr);
				visitor.visitEdge(nodeId, ctr);

				prev.insert(key, ctr);     	    // record that we've been here
				if (search (depth+1, ctr)) {    // keep going. Might solve!
					return true;
				}
			} else {
				visitor.visitEdge(nodeId, ctr);

				long[] nd = duplicated.get(depth);
				if (nd == null) {
					duplicated.put(depth, new long[1]);
				} else {
					nd[0]++;
				}
			}

			move.undo(node);
			moveStack.pop();        		// try another move
			st = st.next();                 // advance
		}

		long[] nd = deadEnd.get(depth);
		if (nd == null) {
			deadEnd.put(depth, new long[1]);
		} else {
			nd[0]++;
		}
		return false;                       // dead-end.
	}

}
