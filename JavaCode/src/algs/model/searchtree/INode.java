package algs.model.searchtree;

import algs.debug.IGraphEntity;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.states.StateStorageFactory;

/**
 * A valid representation of the node within a search tree.
 * <p>
 * To enable extensibility, different Search Tree variations can store an 
 * additional piece of information with a search node. It is stored as an 
 * Object and can be retrieved or set.
 * <p>
 * To support the efficient operation of the contains method within INodeStorage, 
 * classes that implement this interface must provide a suitable {@link Object#hashCode()}
 * implementation.
 * <p>
 * The equals operator must properly compare states solely on the information
 * contained in the state (and not in the storedData). To play nicely with
 * {@link INodeSet} classes that uses {@link StateStorageFactory#HASH} as the
 * storage method, make sure that {@link Object#hashCode()} is implemented
 * otherwise you may encounter strange behavior. Note that there is no way
 * to enforce that the INode implementing class actually provides this
 * method; you are warned.
 * <p> 
 * To support debugging, this interface extends IGraphEntity. This interface is
 * not generic because of the predominant use of INode as a placeholder.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface INode extends IGraphEntity {

	/** 
	 * Return an ordered list of moves that can be made on this board state.
	 *  
	 * @return  ordered list of moves.
	 */
	DoubleLinkedList<IMove> validMoves();
	
	/** 
	 * Set the score for this node.
	 * <p>
	 * Values are interpreted by the search algorithm being used. One common convention
	 * is that values closer to zero reflect board states that are closer to the
	 * goal state.
	 * <p>
	 * A score of zero might represent a solved solution, but that is up to the 
	 * designer of the {@link IScore} evaluation function.
	 * @param s   new score for this node.
	 */
	void score (int s);
	
	/** 
	 * Evaluate the board state according to the scoring function and return an 
	 * integer score.
	 * <p>
	 * Values are interpreted by the search algorithm being used. One common convention
	 * is that values closer to zero reflect board states that are closer to the
	 * goal state.
	 * <p>
	 * A score of zero might represent a solved solution, but that is up to the 
	 * designer of the {@link IScore} evaluation function.
	 * @return evaluated score for this node.
	 */
	int score();
	
	/**
	 * Enable one to grab a copy of this board state.
	 * <p>
	 * Note that the storedData with the state is not copied.
	 * @return  copy of the object (though {@link #storedData()} information is not copied.
	 */
	INode copy();
	
	/** 
	 * Determine if this board state is equivalent to the given state.
	 * 
	 * The notion of equivalence is based upon the actual game. For games
	 * that exhibit symmetries in board state (such as board games), you can
	 * get great savings simply by reducing symmetrical positions.
	 * 
	 * At the same time, the large number of positions might make it 
	 * prohibitively expensive to store all unique positions.
	 *  
	 * Useful when attempting to reduce the search space.
	 * 
	 * @param state    State in question
	 * @return   true if equivalent; false otherwise.
	 */
	boolean equivalent (INode state);
	
	/**
	 * Computes to a key value such that if two board states have the exact same
	 * key, then the board states are equivalent.
	 * <p>
	 * If a board state chooses not to implement a sophisticated function, then the
	 * implementing class should return 'this' as the implementation. 
	 * @return object to use as key in hashtable.
	 */
	Object key ();
	
	/**
	 * Store additional information with this search tree, returning the old
	 * information that had been stored (if at all).
	 * <p>
	 * Returns the prior object that had been stored with the node.
	 *
	 * @param o   object to store with the INode object.
	 * @return    old information that had been stored with object (may be null).
	 */
	Object storedData(Object o);
	
	/** 
	 * Retrieve additional specific information stored with this search tree. 
	 * 
	 * @return   information stored with {@link INode} object.
	 */
	Object storedData();
}
