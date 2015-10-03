package algs.example.gui.problems.segmentIntersection.model;

import algs.model.ICircle;
import algs.model.twod.TwoDLineSegment;

/** 
 * represent circle as bounding-box line segment. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BoxLineSegment extends TwoDLineSegment {

	// reference back to the ICircle
	ICircle circle;
	
	public BoxLineSegment(ICircle circle, double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);

		this.circle = circle;
	}
	
	public ICircle circle() {
		return circle;
	}
}
