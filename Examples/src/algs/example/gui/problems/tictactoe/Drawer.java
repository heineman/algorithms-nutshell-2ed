package algs.example.gui.problems.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import algs.model.problems.tictactoe.model.*;


/**
 * Object responsible for drawing the Tic Tac Toe board.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Drawer {
	/** Width/Height of squares. */
	public static final int CELLSIZE = 60;
	
	/** Offset (x,y) location for Tic Tac Toe Board. */
	public static final int OFFSET_X = 10;
	public static final int OFFSET_Y = 10;
	
	/** Inset into each Cell on screen. */
	public static final int INSET = 10;

	/**
	 * Draw marker for player in specific location.
	 * 
	 * @param g    Graphics object that controls the drawing
	 * @param c    column of cell to draw
	 * @param r    row of cell to draw
	 * @param m    char to draw (or ' ' for empty space)
	 */
	private void drawSpot(Graphics g, int c, int r, char m) {
		// erase if SPACE
		if (m == TicTacToeBoard.EMPTY) {
			g.setColor(Color.lightGray);
			g.fillRect(30 + CELLSIZE*c, 30+CELLSIZE*r, 40, 40);
			return;
		}
		
		// draw extra large character. 
		// NOTE THAT DRAW STRING IS THE ONLY METHOD THAT DEMANDS
		// THE ANCHOR FOR THE COORDINATES BE LOWER LEFT CORNER
		g.setColor (Color.black);
		g.drawString ("" + m, 40+CELLSIZE*c, 60+CELLSIZE*r);
	}
    
	/**
	 * Interpret XY given the way the board is drawn, and 
	 * return a Cell representing (col, row) for that mouse
	 * point.
	 * 
	 * @param x     x coordinate
	 * @param y     y coordinate
	 */
	public Cell interpretXY (int x, int y)  {
		int col = (x - OFFSET_X - INSET)/CELLSIZE;
		int row = (y - OFFSET_Y - INSET)/CELLSIZE;
		
		return new Cell (col, row);
	}
	
	
	/**
	 * Responsible for drawing the board.
	 * @param g
	 */
	public void drawBoard (Graphics g, TicTacToeBoard board) {
		if (board == null) return;  // nothing to draw?
		
	    // make sure font is at 36 point for large size. Note that
		// this should only create a new Font object once.
		Font f = g.getFont();
		if (f.getSize() != 36) {
			f = new Font (f.getFamily(), Font.PLAIN, 36);
			g.setFont (f);
		}
		
		int width = CELLSIZE*board.numColumns() + INSET * (board.numColumns()-1);
		int height = CELLSIZE*board.numRows() + INSET * (board.numRows()-1);
		g.setColor(Color.lightGray);
		g.fillRect (OFFSET_X, OFFSET_Y, 
					width, height);							
				
		// Draw Axes
		g.setColor (Color.black);
		for (int i = 1; i < 3; i++) {
			g.drawLine(OFFSET_X + INSET + CELLSIZE*i, OFFSET_Y + INSET, OFFSET_X + INSET + CELLSIZE*i, height);  // vertical lines
			g.drawLine(OFFSET_X + INSET, OFFSET_Y + 10+CELLSIZE*i, width, OFFSET_Y + INSET+CELLSIZE*i);      // horizontal lines
		}
		
		if (board != null) {
			for (int c = 0; c < board.numColumns(); c++) {
				for (int r = 0; r < board.numRows(); r++) {
					drawSpot (g, c, r, board.get(c, r));
				}
			}
		}
	}
	
}
