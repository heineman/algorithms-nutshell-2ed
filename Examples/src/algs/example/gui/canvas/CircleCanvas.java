package algs.example.gui.canvas;

import java.awt.Graphics;

import algs.model.ICircle;

/**
 * Displays to the screen a set of ILineSegment objects.
 * 
 * <p> 
 * Note that this object deals with Cartesian points and properly displays them
 * using the java.awt.Canvas coordinates that uses the upper left corner as (0,0).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CircleCanvas extends ElementCanvas<ICircle> {
	
	/**
	 * Generated Serializable ID. Keep Eclipse happy.
	 */
	private static final long serialVersionUID = 7452400024011623672L;
	
	public CircleCanvas(int width, int height) {
		super();
		
		this.setSize(width, height);
	}

	/**
	 * Provide concrete realization for drawing each element line segment.
	 */
	public void drawElement(Graphics sc, ICircle ic) {
		// Convert to Cartesian coordinates
		double y = ic.getY();
			
		sc.drawOval((int)(ic.getX()-ic.getRadius()),(int)(y-ic.getRadius()),(int)(ic.getRadius()*2),(int)(ic.getRadius()*2));
	}
}
