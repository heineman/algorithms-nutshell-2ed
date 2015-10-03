package algs.example.gui.problems.segmentIntersection.controller;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.segmentIntersection.model.Model;
import algs.model.ILineSegment;
import algs.model.twod.TwoDLineSegment;

/**
 * Handler to construct lines when responding to mouse events. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SegmentMouseHandler extends MouseHandler<ILineSegment> {

	public SegmentMouseHandler(ElementCanvas<ILineSegment> c, IOutput output,
			Model<ILineSegment> model) {
		super(c, output, model);
	}

	@Override
	protected ILineSegment construct(int x, int y, int x2, int y2) {
		return new TwoDLineSegment (x,y, x2, y2);
	}

}
