package algs.example.gui.problems.segmentIntersection;

import java.util.ArrayList;

import algs.example.gui.canvas.CircleCanvas;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.canvas.NopDrawer;
import algs.example.gui.generator.GeneratorPanel;
import algs.example.gui.model.IModelUpdated;
import algs.example.gui.problems.segmentIntersection.controller.CircleMouseHandler;
import algs.example.gui.problems.segmentIntersection.controller.MouseHandler;
import algs.example.gui.problems.segmentIntersection.model.CircleModel;
import algs.example.gui.problems.segmentIntersection.view.ActiveEntityDecorator;
import algs.example.gui.problems.segmentIntersection.view.IntersectionDecorator;
import algs.model.ICircle;
import algs.model.data.Generator;
import algs.model.data.circles.UniformGenerator;
import algs.model.twod.TwoDCircle;
import algs.model.twod.TwoDPoint;

/**
 * GUI to present functionality to explore intersecting circles.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IntersectingCirclesGUI extends IntersectingEntitiesGUI<ICircle> 
	implements IModelUpdated<ICircle> {

	/**
	 * Keep Eclipse Happy.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructed canvas. */
	private ElementCanvas<ICircle> canvas;

	@Override
	protected ElementCanvas<ICircle> createCanvas(int width, int height) {
		canvas = new CircleCanvas(width, height);
		canvas.setModel(model);
		
		// we want active points as well as intersections...
		canvas.setDrawer(new ActiveEntityDecorator<ICircle> (
				new IntersectionDecorator<ICircle> (new NopDrawer(), canvas, model),
				canvas, model));
		
		// install handlers
		MouseHandler<ICircle> mh = new CircleMouseHandler(canvas, this, model);
		
		canvas.addMouseListener(mh);
		canvas.addMouseMotionListener(mh);
		
		return canvas;
	}
	
	@Override
	protected void customize(GeneratorPanel<ICircle> gp) {
		Generator<ICircle> gen1 = new UniformGenerator(1);  // dummy argument

		gp.addGenerator("Uniform", gen1);
	}

	@Override
	protected ICircle[] transform(ICircle[] circles,
			int width, int height) {
		ArrayList<ICircle> als = new ArrayList<ICircle>();

		double minX = 0;
		double minY = 0;
		double maxX = 0;
		double maxY = 0;

		for (ICircle c: circles) {
			double x = c.getX();
			double y = c.getY();

			if (x < minX) { minX = x; }
			if (y < minY) { minY = y; }
			if (x > maxX) { maxX = x; }
			if (y > maxY) { maxY = y; }
		}

		double xFactor = 1.0 * width / (maxX - minX);
		double yFactor = 1.0 * height / (maxY - minY);
		
		// Does user want to scale?
		if (!shouldScale()) {
			xFactor = 1.0;
			yFactor = 1.0;
		}
		
		// These are Cartesian coordinates. Include radius in resizing
		for (ICircle c: circles) {
			ICircle newOne = new TwoDCircle (
					new TwoDPoint(c.getX()*xFactor,	c.getY()*yFactor),
							c.getRadius()*xFactor);

			als.add(newOne);
		}

		return als.toArray(new ICircle[0]);
	}

	@Override
	protected void constructModel() {
		model = new CircleModel();
		model.setListener(this);
	}

	@Override
	protected ElementCanvas<ICircle> getCanvas() {
		return canvas;
	}
}