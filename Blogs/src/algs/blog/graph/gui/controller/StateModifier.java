package algs.blog.graph.gui.controller;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.gui.view.FreeCellDrawing;
import algs.model.searchtree.IMove;


/**
 * Processes GUI events over the JList to ensure the current board represents
 * the state BEFORE the final selected item.
 * <p>
 * Maintains invariant that drawer shows the state of board PRIOR to invoking the
 * selected move.
 * 
 * @author George Heineman
 */
public class StateModifier implements ListSelectionListener {

	/** Controlled JList. */
	JList<IMove> list;
	
	/** Current board state. */
	FreeCellNode initial;
	
	/** FreeCellDrawing entity. */
	FreeCellDrawing drawer;
	
	public StateModifier (JList<IMove> list, FreeCellNode node, FreeCellDrawing drawer) {
		this.list = list;
		
		// initial state
		this.initial = (FreeCellNode) node.copy();
		this.drawer = drawer;
	}

	public void setNode(FreeCellNode fcn) {
		initial = fcn;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// must find the one that is selected
		int idx = list.getSelectedIndex();
		DefaultListModel<IMove> dlm = (DefaultListModel<IMove>) list.getModel();
		FreeCellNode node = (FreeCellNode) initial.copy();
		for (int i = 0; i < idx; i++) {
			IMove move = (IMove) dlm.get(i);
			if (move.isValid(node)) {
				move.execute(node);
			} else {
				System.out.println("INVALID MOVE!");
			}
		}
		
		drawer.setNode(node);
		drawer.repaint();
	}
}
