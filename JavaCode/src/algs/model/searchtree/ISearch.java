package algs.model.searchtree;

/**
 * Common interface for all search algorithms over a search tree.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface ISearch {
	/**
	 * Given the initial state, return a Solution to the final state, or null if
	 * no such path can be found.
	 * 
	 * @param initial   the initial board state
	 * @param goal      the final board state
	 * @return          Solution to search, should one exist.
	 */
	Solution search(INode initial, INode goal);
}
