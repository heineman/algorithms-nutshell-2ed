package algs.example.gui.canvas;

import java.awt.Color;
import java.awt.Graphics;

import algs.example.gui.model.IRetrieveKDTree;
import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;

/**
 * Decorates the set of points by drawing the KD-tree which was constructed.
 * 
 * Note that all points in the KDTree are still in Cartesian coordinates so
 * this decorator properly inverts y coordinate when drawing based upon the 
 * height of the canvas. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class KDTreeDecorator extends DrawingDecorator {

	/** Inner canvas which contains methods to draw entities. */
	ElementCanvas<IMultiPoint> canvas;
	
	/** Contains model information. */
	IRetrieveKDTree model;
	
	/** Is active? */
	boolean visible = false;
	
	/**
	 * Decorates a canvas by processing potential intersections among the
	 * elements already being drawn on the canvas.
	 *  
	 * We need to have the base canvas to be able to access the 'drawElement'
	 * implementation. 
	 *  
	 * Because it is a decorator, it knows the inner one. 
	 */
	public KDTreeDecorator(DrawingCanvas inner, ElementCanvas<IMultiPoint> canvas, IRetrieveKDTree m) {
		super(inner);
		
		this.canvas = canvas;
		this.model = m;
	}

	@Override
	public void draw(Graphics sc) {
		// Draw base 
		super.draw(sc);

		// not shown if not visible!
		if (!visible) { return; }
		
		// augment with kd tree
		// draw each of the points in the KD tree.
		KDTree tree = model.getTree();
		DimensionalNode dn = tree.getRoot();
		if (dn == null) return;
		
		draw(sc, dn, new DrawingInfo(0,canvas.getWidth(), 0,canvas.getHeight()));
	}
	
	public void setVisible (boolean b) {
		visible = b;
	}
	
	/**
	 * Draw the kd tree.
	 * 
	 * Note that the DimensionalNode is in Cartesian coordinates while the
	 * DrawingInfo is in AWT coordinates.
	 * 
	 * @param sc
	 * @param n
	 * @param info
	 */
	void draw (Graphics sc, DimensionalNode n, DrawingInfo info) {
		if (n == null) return;
		
		int ht = canvas.getHeight();
		
		IMultiPoint tdp = (IMultiPoint) n.point;
		double x = tdp.getCoordinate(1);
		double y = ht - tdp.getCoordinate(2);
		sc.setColor(Color.black);
		
		// draw line before point.
		if (n.dimension == 1) {
			// x coordinate: vertical line
			sc.drawLine((int)x,ht-info.minVertical, 
					(int)x, ht-info.maxVertical);
		} else {
			// y coordinate: horizontal line
			sc.drawLine(info.minHorizontal, (int)y,
					    info.maxHorizontal, (int)y);
		}
		canvas.drawElement(sc, tdp);
		

		// draw "-" for horizontal and "|" for vertical
		if (n.dimension == 1) {
			sc.drawLine((int)x-2, (int)y, (int)x+1,(int)y);
		} else {
			sc.drawLine ((int)x, (int)y-2, (int)x, (int)y+1);
		}
		
		// recurse
		draw (sc, n.getAbove(), info.above(n));
		draw (sc, n.getBelow(), info.below(n));
	}
	
}
