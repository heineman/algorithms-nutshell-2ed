package algs.example.gui.problems.segmentIntersection.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Hashtable;

import algs.example.gui.canvas.DrawingCanvas;
import algs.example.gui.canvas.DrawingDecorator;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.problems.segmentIntersection.model.Model;
import algs.model.IPoint;
import algs.model.list.List;

/**
 * Decorates the set of intersections on top of an existing layout. This abstract
 * class must be customized by the appropriate Entity so the intersection 
 * algorithm can be applied (based on whether it operates on Lines, circles,
 * rectangles, etc...)
 * 
 * @param <E>    Type of entity being drawn on the canvas.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IntersectionDecorator<E> extends DrawingDecorator {

	/** Inner canvas which contains methods to draw entities. */
	ElementCanvas<E> canvas;
	
	/** Contains model information. */
	Model<E> model;
	
	/**
	 * Decorates a canvas by processing potential intersections among the
	 * elements already being drawn on the canvas.
	 *  
	 * We need to have the base canvas to be able to access the 'drawElement'
	 * implementation. 
	 *  
	 * Because it is a decorator, it knows the inner one. 
	 */
	public IntersectionDecorator(DrawingCanvas inner, ElementCanvas<E> canvas, Model<E> m) {
		super(inner);
		
		this.canvas = canvas;
		this.model = m;
	}

	@Override
	public void draw(Graphics sc) {
		// Draw base 
		super.draw(sc);

		// Now we augment with intersections...		
		Hashtable<IPoint, List<E>> hash = model.intersections();
		if (hash != null && hash.size() > 0) {
			for (IPoint ip : hash.keySet()) {
				List<E> els = hash.get(ip);
				sc.setColor(Color.blue);

				for (E e : els) {
					canvas.drawElement(sc, e);
				}

				sc.setColor(Color.red);
				canvas.drawPoint(sc, ip);
			}
		}
	}

}
