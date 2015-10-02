package algs.model.searchtree.states;

import java.util.Comparator;
import java.util.Iterator;

import algs.model.searchtree.INode;
import algs.model.searchtree.INodeSet;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * Maintains the set of open states in ordered fashion, using the score() as 
 * the ordering value. 
 * <p>
 * Because multiple nodes may have the same score, the {@link StateTree#remove(INode)}
 * and {@link StateTree#contains(INode)} methods will act strangely to the 
 * outside world. They only care about the score; thus it may be possible there
 * will be two boards with the same score and the request to {@link #remove(INode)} 
 * will actually remove a different node with the same score. It is thus recommended
 * not to use the {@link #remove(INode)} method on {@link StateTree}.
 * <p>
 * {@link INodeSet#insert(INode)} and {@link INodeSet#remove(INode)} are 
 * O(log n) operations where n is the size of the balanced binary tree.
 * {@link INodeSet#remove()} removes the {@link INode} with minimum {@link INode#key()}
 * value, so this is typically not that useful during the evaluation of a search. 
 * After all, the key is reflective of the board state, not the evaluation 
 * of the board state (as would be found in {@link INode#score()} for example).
 * <p>
 * This state variation is not suitable to use as the Closed Set for any of the 
 * searching algorithms, since you won't determine whether a board has been discovered
 * before; rather you will find whether a board with the same score has been seen before.
 * 
 * @author George Heineman
 */
public class StateTree implements INodeSet {

	/** Compare by evaluation score. */
	Comparator<INode> comp = new Comparator<INode>() {
		public int compare(INode o1, INode o2) {
			return o1.score() - o2.score();
		}
	};
	
	/** Tree stores nodes ordered by score: Allow duplicates. */
	BalancedTree<INode,INode> tree;
	
	/** Construct Binary tree to use. Allow duplicates in the tree. */
	public StateTree() {
		tree = new BalancedTree<INode,INode>(comp);
		tree.setAllowDuplicates(true);
	}

	/** 
	 * Insert the board state into the tree.
	 * 
	 * @param n   Board state to be inserted.
	 */
	public void insert(INode n) {
		tree.insert(n, n);
	}

	/** 
	 * Remove and return INode with minimum score value since tree was constructed using
	 * {@link StateTree#comp} as the comparator.
	 */
	public INode remove() {
		return tree.minimum();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return tree.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return tree.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#iterator()
	 */ 
	public Iterator<INode> iterator() {
		return tree.iterator();
	}
	
	/** 
	 * Locate element within the set whose score is the same.
	 * 
	 * @param  n  the node whose score is to be used when searching.
	 */
	public INode contains(INode n) {
		BalancedBinaryNode<INode,INode> entry = tree.getEntry(n);
		if (entry == null) { 
			return null; 
		}
		return entry.value();
	}

	/**
	 * Remove actual value from the set whose score is the same.
	 * <p> 
	 * The score from the INode passed in is used to locate the desired INode
	 * within the set. As such, the behavior of this method may be surprising
	 * since you could have two nodes in the tree with the same score and one
	 * of them will be removed, though you won't know exactly which one.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n   the node representing the value to be removed from the set
	 */
	public boolean remove(INode n) {
		return tree.remove(n) != null;
	}
}
