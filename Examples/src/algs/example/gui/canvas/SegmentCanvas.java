package algs.example.gui.canvas;

import java.awt.Dimension;
import java.awt.Graphics;

import algs.model.ILineSegment;

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
public class SegmentCanvas extends ElementCanvas<ILineSegment> {
	
	/**
	 * Generated Serializable ID. Keep Eclipse happy.
	 */
	private static final long serialVersionUID = 7452400024011623672L;
	
	public SegmentCanvas(int width, int height) {
		super();
		
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Provide concrete realization for drawing each element line segment.
	 */
	public void drawElement(Graphics sc, ILineSegment ils) {
		// Convert to Cartesian coordinates
		double x1 = ils.getStart().getX();
		double y1 = ils.getStart().getY();
		
		double x2 = ils.getEnd().getX();
		double y2 =  ils.getEnd().getY();
		
		sc.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
	}

}
