package algs.example.gui.problems.nearestNeighbor;

import java.awt.Color;
import java.awt.Graphics;

import algs.example.gui.canvas.ElementCanvas;
import algs.model.IMultiPoint;

/**
 * Defines how elements (in this case IMultiPoint objects) are drawn in the 
 * graphics context. 
 * <p>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MultiPointCanvas extends ElementCanvas<IMultiPoint> {
	
	/**
	 * Keep Eclipse happy.
	 */
	private static final long serialVersionUID = -1442454425465617120L;

	/** Properly converts Cartesian coordinates in p into AWT. */
	@Override
	public void drawElement(Graphics sc, IMultiPoint p) {
		double x = p.getCoordinate(1);  // x
		double y = getHeight() - p.getCoordinate(2);  // y
		
		sc.setColor(Color.white);
		sc.fillOval((int)x-4,(int)y-4,8,8);
		sc.setColor(Color.black);
		sc.drawOval((int)x-4,(int)y-4,8,8);
	}
	
}
