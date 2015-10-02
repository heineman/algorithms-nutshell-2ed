package algs.model.searchtree.states;

import java.util.ArrayList;
import java.util.Iterator;

import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * Maintains the set of open states using a Hash for quick contains() but also
 * stores binary tree of evaluations for quick getMin().
 * <p>
 * 
 * @author George Heineman
 * @version 2.0, 8/30/15
 * @since 2.0
 */
public class StatePriorityRetrieval implements INodeSet {

	/** Store all nodes for quick contains check. */
	StateHash hash;
	
	/** Each node stores a collection of INodes that evaluate to same score. */
	BalancedTree<Integer,ArrayList<INode>> tree;
	
	/** Construct hash to store INode objects. */
	public StatePriorityRetrieval () {
		 hash = new StateHash();
		 tree = new BalancedTree<Integer,ArrayList<INode>>();
	}
	
	/**
	 * Insert a node.
	 * 
	 * @param n  node to be inserted.
	 */
	public void insert(INode n) {
		hash.insert(n);
		
		int score = n.score();
		BalancedBinaryNode<Integer, ArrayList<INode>> entry = tree.getEntry(score);
		ArrayList<INode> list;
		if (entry != null){ 
			list = entry.value(); 
		} else {
			list = new ArrayList<INode>();
			tree.insert(score, list);
		}
		list.add(n);
	}

	/** 
	 * Remove and return INode with minimum score value
	 */
	public INode remove() {
		ArrayList<INode> min = tree.minimum();
		
		// grab first one (must be at least one)
		INode best = min.remove(0);
		hash.remove(best);
		
		// still have more at this score? Reinsert for next time.
		if (min.size() > 0) {
			tree.insert(best.score(), min);
		}
		
		return best;
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return hash.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return hash.size();
	}
	
	/**
	 * Retrieve all elements in set by hash, not by order in the binary tree.
	 */
	public Iterator<INode> iterator() {
		return hash.iterator();
	}
	
	/**
	 * Return node by querying hashtable.
	 *  
	 * @param n     target node to be searched for
	 */
	public INode contains(INode n) {
		return hash.contains(n);
	}

	/**
	 * Remove actual entry from the set.
	 * <p> 
	 * The INode passed in must be an actual value returned by {@link INodeSet#contains(INode)}.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n   the node representing the entry to be removed.
	 */
	public boolean remove(INode n) {
		boolean rc = hash.remove(n);
		if (!rc) { return false; }
		
		// remove from BT
		int score = n.score();
		BalancedBinaryNode<Integer, ArrayList<INode>> entry = tree.getEntry(score);
		if (entry == null) {
			System.err.println("Warning: StatePriorityRetrieval not synchronized with hash.");
			return false;
		}
		ArrayList<INode> list = entry.value();
		list.remove(n);
		if (list.size() == 0) {
			tree.remove(score);
		}
		
		return true;
	}
}
