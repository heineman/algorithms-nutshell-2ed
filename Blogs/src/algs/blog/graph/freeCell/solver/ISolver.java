package algs.blog.graph.freeCell.solver;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;

/**
 * Develop interface for solver, which enables the original solver in addition to
 * an improved solver that uses auto-moves.
 * 
 * Leave open possibility for a more advanced solver that might reorder moves 
 * intelligently to further improve the search time.
 * 
 * @author George Heineman
 */
public interface ISolver {
	/**
	 * Return linked list of potential moves from this node.
	 * 
	 * @param node
	 * @return
	 */
	DoubleLinkedList<IMove> validMoves(FreeCellNode node);		
}
