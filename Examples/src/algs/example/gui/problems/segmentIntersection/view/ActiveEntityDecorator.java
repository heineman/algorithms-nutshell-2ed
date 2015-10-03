package algs.example.gui.problems.segmentIntersection.view;

import java.awt.Graphics;

import algs.example.gui.canvas.DrawingCanvas;
import algs.example.gui.canvas.DrawingDecorator;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.problems.segmentIntersection.model.Model;

/**
 * Responsible for drawing a "floating line segment" which appears above 
 * any prior drawn state
 * 
 * @param <E>   type of entity
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ActiveEntityDecorator<E> extends DrawingDecorator {

	/** Need for key drawing capabilities. */
	ElementCanvas<E> canvas;
	
	/** Store model to access information (the dynamic line!). */
	Model<E> model;
	
	/**
	 * Need inner as part of decoration. Need canvas for core capabilities. Need
	 * model for information.
	 * 
	 * @param inner
	 * @param canvas
	 */
	public ActiveEntityDecorator(DrawingCanvas inner, ElementCanvas<E> canvas, Model<E> m) {
		super(inner);
		
		this.canvas = canvas;
		this.model = m;
	}


	@Override
	public void draw (Graphics g) {
		// make sure we draw what is to be superimposed upon.
		super.draw(g);
		
		// now draw the line being actively dragged around.
		// draw active line on top.... Convert to AWT
		E entityBeingAdded = model.getDynamicEntity();
		if (entityBeingAdded != null) {
        	java.awt.Color old = g.getColor();
			g.setColor(java.awt.Color.black);
			canvas.drawElement (g, entityBeingAdded);
        	g.setColor(old);
        }
	}
}
