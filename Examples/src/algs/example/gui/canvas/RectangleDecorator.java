package algs.example.gui.canvas;

import java.awt.Color;
import java.awt.Graphics;

import algs.example.gui.canvas.DrawingCanvas;
import algs.example.gui.canvas.DrawingDecorator;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.model.IActiveRectangle;
import algs.model.IRectangle;

/**
 * Decorates a drawn rectangle on the screen.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class RectangleDecorator extends DrawingDecorator {

	/** Inner canvas which contains methods to draw entities. No need to know type parameter. */
	@SuppressWarnings("rawtypes")
	ElementCanvas canvas;

	/** Model from which to retrieve the rectangle. */
	IActiveRectangle model;
	
	/**
	 * Decorates a canvas by processing potential intersections among the
	 * elements already being drawn on the canvas.
	 *  
	 * We need to have the base canvas to be able to access the 'drawElement'
	 * implementation, but we don't need its type parameter information.
	 *  
	 * Because it is a decorator, it knows the inner one. 
	 */
	@SuppressWarnings("rawtypes")
	public RectangleDecorator(DrawingCanvas inner, ElementCanvas canvas, IActiveRectangle m) {
		super(inner);
		
		this.canvas = canvas;
		this.model = m;
	}

	/**
	 * Must properly adjust for height to convert Cartesian coordinates
	 * into AWT.
	 */
	@Override
	public void draw(Graphics sc) {
		// Draw base 
		super.draw(sc);

		// Note that p is in Cartesian coordinates
		IRectangle rect = model.getActiveRectangle();
		if (rect == null) return;
		
		int ht = canvas.getHeight();
		
		sc.setColor(Color.darkGray);
		sc.drawRect((int)rect.getLeft(), ht-(int) rect.getTop(),
				(int) (rect.getRight()-rect.getLeft()),(int)(rect.getTop()-rect.getBottom()));
	}
}
