package algs.example.gui.problems.rangeQuery.controller;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.rangeQuery.MainFrame;
import algs.example.gui.problems.rangeQuery.model.Model;
import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.twod.TwoDRectangle;

/**
 * Processes mouse results and updates model accordingly.
 * 
 * The active rectangle selected by this handler is converted into Cartesian
 * coordinates before being passed along to the Model.
 * 
 * @param <E>  base element type to be drawn by the underlying canvas.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MouseHandler<E> extends java.awt.event.MouseAdapter {
	/** Store the point of contact. */
	java.awt.Point anchor;
	
	/** Store the target canvas. */
	ElementCanvas<E> canvas;
	
	/** Store model of the rectQuery. */
	Model<SelectableMultiPoint> model;
	
	/** Keep the main frame just to be able to signal when released. A bit of a hack. */
	MainFrame frame;
	
	/** Generic output interface. */
	IOutput output;
	
	public MouseHandler (MainFrame frame, ElementCanvas<E> c, IOutput output, Model<SelectableMultiPoint> model) {
		this.frame = frame;
		this.canvas = c;
		this.output = output;
		
		this.model = model;
	}
	
	public void mouseDragged(java.awt.event.MouseEvent me) {
		int x1 = anchor.x;
		int x2 = me.getPoint().x;
		int y1 = anchor.y;
		int y2 = me.getPoint().y;
		
		// order
		if (x2 < x1) {
			int t = x2;
			x2 = x1;
			x1 = t;
		}
		if (y2 < y1) {
			int t = y2;
			y2 = y1;
			y1 = t;
		}
		
		// locate within. Note that we must convert AWT coordinates
		// into Cartesian ones. NOTE inversion of y1,y2
		int ht = canvas.getHeight();
		TwoDRectangle targetRect = new TwoDRectangle(x1, ht-y2, x2, ht-y1);
		
		model.setActiveRectangle(targetRect);
		
		canvas.redrawState();
		canvas.repaint();
	}
	
	public void mousePressed(java.awt.event.MouseEvent me) {
		anchor = me.getPoint();
		
		// must be Cartesian coordinates.
		int ht = canvas.getHeight();
		model.setActiveRectangle(new TwoDRectangle (anchor.x, ht-anchor.y, anchor.x, ht-anchor.y));
		
		canvas.redrawState();
		canvas.repaint();
	}
	
	public void mouseReleased(java.awt.event.MouseEvent me) {
		int x1 = anchor.x;
		int x2 = me.getPoint().x;
		int y1 = anchor.y;
		int y2 = me.getPoint().y;
		
		// order
		if (x2 < x1) {
			int t = x2;
			x2 = x1;
			x1 = t;
		}
		if (y2 < y1) {
			int t = y2;
			y2 = y1;
			y1 = t;
		}
		
		// locate within. Note that we must convert AWT coordinates
		// into Cartesian ones. NOTE inversion of y1,y2
		int ht = canvas.getHeight();
		TwoDRectangle targetRect = new TwoDRectangle(x1, ht-y2, x2, ht-y1);
		
		model.setActiveRectangle(targetRect);
		
		// be sure to signal a change, just in case interactive is turned off.
		// a bit of a hack.
		frame.modelUpdated(model);
		
		canvas.redrawState();
		canvas.repaint();
	}
}
