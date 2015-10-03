package algs.example.gui.problems.segmentIntersection.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.segmentIntersection.model.Model;

/**
 * Mouse Handler to interact with Element Canvas.
 * <p>
 * This abstract class can be extended to support a range of behaviors triggered
 * by the standard mouse actions. The primary behavior is to {@link #construct(int, int, int, int)}
 * an object based upon where the mouse was initially pressed and the location to
 * which the user drags the mouse.
 * 
 * @param <E>   Underlying type of element to be drawn on the canvas.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class MouseHandler<E> extends java.awt.event.MouseAdapter implements MouseMotionListener {
	/** Store the point of contact [AWT]. */
	java.awt.Dimension offset;

	/** interactive Output goes here. */
	protected IOutput output;
	
	/** Store the target canvas. */
	protected ElementCanvas<E> canvas;

	/** Model of entities. */
	Model<E> model;
	
	MouseHandler (ElementCanvas<E> c, IOutput output, Model<E> model) {
		this.canvas = c;
		this.output = output;
		
		this.model = model;
	}
	
	/** Convert the (x,y) -> (x2,y2) drag into an entity to be returned. */
	protected abstract E construct(int x, int y, int x2, int y2);
	
	public void mouseDragged(java.awt.event.MouseEvent me) {
		if (offset == null) {
			offset = new java.awt.Dimension (me.getPoint().x,
					me.getPoint().y);
		}
		
		int x1 = offset.width;
		int x2 = me.getPoint().x;
		int y1 = offset.height;
		int y2 = me.getPoint().y;

		// forces recomputation...
		model.setDynamicEntity(construct (x1,y1,x2,y2));
	}

	public void mousePressed(java.awt.event.MouseEvent me) {
		offset = new java.awt.Dimension (me.getPoint().x,
				me.getPoint().y);
		
		model.setDynamicEntity(null);
	}

	public void mouseReleased(java.awt.event.MouseEvent me) {
		model.setDynamicEntity(null);
	}

	public void mouseMoved(MouseEvent e) {
		// ignored.
	}
}