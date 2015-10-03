package algs.example.gui.problems.nearestNeighbor;

import java.awt.Color;
import java.awt.Graphics;

import algs.example.gui.canvas.DrawingCanvas;
import algs.example.gui.canvas.DrawingDecorator;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.problems.nearestNeighbor.model.Model;
import algs.model.IMultiPoint;

/**
 * Decorates the nearest point by drawing both it and the current point with 
 * a red line connecting the two.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */

public class NearestPointDecorator extends DrawingDecorator {

	/** Inner canvas which contains methods to draw entities. */
	ElementCanvas<IMultiPoint> canvas;
	
	/** Contains model information. */
	Model model;
	
	/**
	 * Decorates a canvas by processing potential intersections among the
	 * elements already being drawn on the canvas.
	 *  
	 * We need to have the base canvas to be able to access the 'drawElement'
	 * implementation. 
	 *  
	 * Because it is a decorator, it knows the inner one. 
	 */
	public NearestPointDecorator(DrawingCanvas inner, ElementCanvas<IMultiPoint> canvas, Model m) {
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
		IMultiPoint p = model.getNearest();
		if (p == null) return;
		
		int ht = canvas.getHeight();
		
		double x = p.getCoordinate(1);
		double y = ht - p.getCoordinate(2);
		
		// Note that tgt is in Cartesian coordinates
		IMultiPoint tgt = model.getTarget();
		double tx = tgt.getCoordinate(1);
		double ty = ht - tgt.getCoordinate(2);
		
		canvas.drawElement(sc, tgt);
		canvas.drawElement(sc, p);
		
		sc.setColor(Color.red);
		sc.drawLine((int)x, (int) y, (int) tx, (int) ty);
		sc.fillOval((int)x - 4, (int)y - 4, 8, 8);
	}
	
}
