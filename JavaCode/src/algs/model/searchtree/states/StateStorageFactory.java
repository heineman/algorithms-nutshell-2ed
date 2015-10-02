package algs.model.searchtree.states;

import algs.model.searchtree.INodeSet;

/**
 * Return an appropriate INodeSet entity.
 * <p>
 * Used by search algorithms to construct both OPEN and CLOSED sets. The 
 * efficiency of the underlying implementations determines the performance
 * of the algorithms. Each of these INodeSet entities supports the
 * {@link INodeSet#remove()} as it sees fit. In some cases, {@link INodeSet#remove()}
 * will remove the member INode with the least score; other times, it simply
 * removes the most recent one added. Each algorithm that uses {@link INodeSet}
 * will properly select the one to construct from this factory.  
 * 
 * <ol>
 * <li>ORDERED -- A straw man implementation that should not be used since 
 *                it stores its elements using a linked list. Inserts and
 *                removals are performed, therefore, in O(n). There are 
 *                better implementations available.
 * <li>STACK -- Implementation stores elements in the set such that 
 *                {@link INodeSet#remove()} removes the last element added 
 *                to the set. Insert and removals are constant O(1) operations.
 *                However, {@link INodeSet#contains(algs.model.searchtree.INode)} is O(n).          
 * <li>HASH -- Implementation stores elements in the set but doesn't support 
 *                the {@link INodeSet#remove(algs.model.searchtree.INode)} method.
 *                Inserts and queries are constant O(1) operations.
 * <li>TREE -- Implementation stores elements in a balanced binary tree. The 
 *                trouble is that the {@link algs.model.searchtree.INode#score()} is
 *                used as the discriminating key, thus it turns out to not be
 *                that useful at all.
 * </ol>
 *                 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StateStorageFactory {
	
	/** Use Double-Linked lists to maintain an ordered set by score(). */
	public static final int ORDERED = 1;
	
	/** Use stack to maintain set. No ordering, but last-in, first-out. */
	public static final int STACK = 2;
	
	/** Use queue to maintain set. No ordering, but first-in, first-out. */
	public static final int QUEUE = 3;
	
	/** Use balanced binary tree to maintain set. Ordering based on score(). */
	public static final int TREE = 4;
	
	/** Use hash table to maintain set. Ordering irrelevant. */
	public static final int HASH = 5;
	
	/** Combines HASH for contains() queries while using TREE for ordering. */
	public static final int PRIORITY_RETRIEVAL = 6;
	
	public static INodeSet create (int type) {
		switch (type) {
			case ORDERED:
				return new StateOrdered();
			case STACK:
				return new StateStack();
			case QUEUE:
				return new StateQueue();
			case TREE:
				return new StateTree();
			case HASH:
				return new StateHash();
			case PRIORITY_RETRIEVAL:
				return new StatePriorityRetrieval();
		}
		
		throw new IllegalArgumentException ("Must specify a valid type for StateStorageFactory.");
	}
}
