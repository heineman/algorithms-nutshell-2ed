package algs.example.gui.problems.segmentIntersection.controller;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.segmentIntersection.model.Model;
import algs.model.ICircle;
import algs.model.twod.TwoDCircle;
import algs.model.twod.TwoDPoint;

/**
 * Handler to construct circles in response to mouse events. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CircleMouseHandler extends MouseHandler<ICircle> {

	public CircleMouseHandler(ElementCanvas<ICircle> c, IOutput output,
			Model<ICircle> model) {
		super(c, output, model);
	}

	@Override
	protected ICircle construct(int x1, int y1, int x2, int y2) {
		int r1 = Math.abs(y2 - y1);
		int r2 = Math.abs(x2 - x1);
		if (r2 < r1) {
			r1 = r2;
		}
		
		int ox = (x1+x2)/2; 
		int oy = (y1+y2)/2;
		
		return new TwoDCircle (new TwoDPoint(ox,oy), r1);
	}

}
