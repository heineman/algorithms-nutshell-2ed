package algs.blog.graph.gui.view;

import javax.swing.JPanel;
import java.awt.Graphics;

import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.gui.Solver;

/**
 * Draw the board in the given JPanel.
 * 
 * @author George Heineman
 */
public class FreeCellDrawing extends JPanel {

	/**
	 * Keep Eclipse Happy
	 */
	private static final long serialVersionUID = 1L;

	/** Contains information to be drawn. */
	short[] key;
	
	/** Vertical and horizontal offsets. */
	int voffset = 10;
	int hoffset = 10;
	
	@Override
	public void paintComponent(Graphics g) {
		
		// if still null, then not fully loaded.
		CardImages ci = Solver.cardImages;
		if (ci == null) {
			g.drawString("Waiting for card images to load...", 100, 100);
			return;
		}
		
		// if no key, then not yet selected
		if (key == null) {
			return;
		}
		
		// clear everything...
		g.clearRect(0, 0, getWidth(), getHeight());
		
		int ht = ci.getHeight();
		int wth = ci.getWidth();
		int over = ci.getOverlap();
		
		// draw free cells
		for (int i = 0; i < 4; i++) {
			short card = key[i];
			if (card != 0) {
				int suit = 1 + ((card-1)%4);   // subtract 1 since '0' is invalid card.
				int rank = 1 + ((card-1)>>2);  // rank extracted this way.
				
				g.drawImage(ci.getCardImage(rank, suit),
						-5+hoffset*(i+1) + wth*i, voffset, -5+hoffset*(i+1)+wth*(i+1), voffset+ht,
						0, 0, wth, ht, this);
			}
		}
		
		// draw foundation cells
		for (int i = 4; i < 8; i++) {
			short card = key[i];
			if (card != 0) {
				int suit = i-3;
				int rank = card;
				
				g.drawImage(ci.getCardImage(rank, suit),
						5+hoffset*(i+1) + wth*i, voffset, 5+hoffset*(i+1)+wth*(i+1), voffset+ht,
						0, 0, wth, ht, this);
			}
		}
		// draw board
		int idx = 8;
		for (int i = 0; i < 8; i++) {
			for (int sz = 0; sz < 19; sz++) {
				short card = key[idx++];
				if (card != 0) {
					int suit = 1 + ((card-1)%4);   // subtract 1 since '0' is invalid card.
					int rank = 1 + ((card-1)>>2);  // rank extracted this way.

					g.drawImage(ci.getCardImage(rank, suit),
							hoffset*(i+1) + wth*i, ht+voffset + 10+over*sz, hoffset*(i+1)+wth*(i+1), ht+voffset + ht+10+over*sz,
							0, 0, wth, ht, this);
				}
			}
		}
		
	}

	/** Set the node to be drawn. */
	public void setNode(FreeCellNode node) {
		key = node.rawkey();
		
		// force repaint.
		repaint();
		
	}
	
}
