package algs.blog.graph.gui;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.freeCell.solver.AutoMovesSolver;

/**
 * Show a full solution to a board graphically.
 * 
 * @author George Heineman
 */
public class ImprovedSolver {
	
	public static void main(String[] args) throws Exception {
		FreeCellNode.setSolvingMethod(new AutoMovesSolver());
		
		Solver.main(args);
	}
}
