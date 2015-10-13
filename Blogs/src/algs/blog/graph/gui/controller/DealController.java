package algs.blog.graph.gui.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import algs.blog.graph.freeCell.BoardScorer;
import algs.blog.graph.freeCell.Deal;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.gui.view.FreeCellDrawing;
import algs.blog.graph.search.GoalDirectedStagedDeepening;
import algs.blog.graph.search.Result;
import algs.model.searchtree.IMove;
import algs.model.searchtree.IScore;

/**
 * Controller to process new deal.
 * 
 * @author George Heineman
 */
public class DealController implements ActionListener {

	/** Frame in which app runs. */
	final JFrame frame;
	
	/** Viewer to represent game on screen. */
	final FreeCellDrawing drawer;
	
	/** List containing moves. */
	final JList<IMove> list;
	
	/** Last listener (so we can delete with each redeal). */
	StateModifier listener;
	
	public DealController (JFrame frame, FreeCellDrawing drawer, JList<IMove> list) {
		this.frame = frame;
		this.drawer = drawer;
		this.list = list;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int dealNumber;
	
		try {
			dealNumber = Integer.valueOf(e.getActionCommand());
		
			if (dealNumber < 1 || dealNumber > 32000) {
				frame.setTitle("Invalid deal Number (1-32000): " + e.getActionCommand());
				return;
			}
		} catch (NumberFormatException nfe) {
			frame.setTitle("Invalid deal Number (1-32000): " + e.getActionCommand());
			return;
		}
		frame.setTitle("Solution for " + dealNumber);
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		// simple solution: 28352
		// no solution: 25382
		// infinite loop: 1200
		//int dealNumber = 28352;  
		
		// start from initial state (and make copy for later). 
		FreeCellNode fcn;
		try {
			fcn = Deal.initialize(new File ("artifacts", "32000.txt"), dealNumber);
		} catch (IOException e1) {
			frame.setTitle ("Unable to find file \"32000.txt\"");
			return;
		}
		
		// Compute the solution.
		IScore eval = new BoardScorer();
		GoalDirectedStagedDeepening<short[]> sd = new GoalDirectedStagedDeepening<short[]>(Deal.goal(), eval);
		Result res = sd.fullSearch (fcn, eval, FreeCellNode.comparator());
		Stack<IMove> st = res.solution();
		if (st == null) {
			System.err.println("No Computed Solution for " + dealNumber);
			System.exit(1);
		}
		
		
		DefaultListModel<IMove> dlm = new DefaultListModel<IMove>();
		while (!st.isEmpty()) {
			dlm.add(0, st.pop());
		}
		
		list.setModel(dlm);
		list.setSelectedIndex(0);
		if (listener != null) {
			list.removeListSelectionListener(listener);
		}
		
		listener = new StateModifier(list, fcn, drawer);
		list.addListSelectionListener(listener);
		frame.setCursor(Cursor.getDefaultCursor());
		
		drawer.setNode (fcn);
		drawer.repaint();
	}
	

}
