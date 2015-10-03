package algs.example.gui.problems.segmentIntersection;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.canvas.NopDrawer;
import algs.example.gui.canvas.SegmentCanvas;
import algs.example.gui.generator.GeneratorPanel;
import algs.example.gui.model.IModelUpdated;
import algs.example.gui.problems.segmentIntersection.controller.MouseHandler;
import algs.example.gui.problems.segmentIntersection.controller.SegmentMouseHandler;
import algs.example.gui.problems.segmentIntersection.model.LineSegmentModel;
import algs.example.gui.problems.segmentIntersection.view.ActiveEntityDecorator;
import algs.example.gui.problems.segmentIntersection.view.IntersectionDecorator;
import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.data.segments.GridGenerator;
import algs.model.data.segments.LoadFromFileGenerator;
import algs.model.data.segments.DoubleGenerator;
import algs.model.data.segments.HubGenerator;
import algs.model.data.segments.IntegerGenerator;
import algs.model.data.segments.SlidingLadderGenerator;
import algs.model.data.segments.UniformGenerator;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

import java.util.ArrayList;

/**
 * GUI to present functionality to explore intersecting segments.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IntersectingSegmentsGUI extends IntersectingEntitiesGUI<ILineSegment> 
     implements IModelUpdated<ILineSegment> {

	/** Constructed canvas. */
	private ElementCanvas<ILineSegment> canvas;
	
	/**
	 * Keep Eclipse happy.
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void constructModel() {
		model = new LineSegmentModel();
		model.setListener(this);
	}
	
	@Override
	protected ElementCanvas<ILineSegment> createCanvas(int width, int height) {
		canvas = new SegmentCanvas(width, height);
		canvas.setModel(model);	
		
		// we want active points as well as intersections...
		canvas.setDrawer(new ActiveEntityDecorator<ILineSegment> (
				new IntersectionDecorator<ILineSegment> (new NopDrawer(), canvas, model),
				canvas, model));
		
		// install handlers
		MouseHandler<ILineSegment> mh = new SegmentMouseHandler(canvas, this, model);
		
		canvas.addMouseListener(mh);
		canvas.addMouseMotionListener(mh);
		
		return canvas;
	}
	
	public ElementCanvas<ILineSegment> getCanvas() {
		return canvas;
	}
	
	/** Our set of generators. */
	@Override
	protected void customize(GeneratorPanel<ILineSegment> gp) {
		// the following objects are never used to generate points; they are
		// used solely as prototypes when constructing the real things.
		Generator<ILineSegment> gen1 = new UniformGenerator(1);  // dummy argument
		Generator<ILineSegment> gen2 = new DoubleGenerator(1.0,1.0);  // dummy argument
		Generator<ILineSegment> gen3 = new IntegerGenerator(100,100);  // dummy arguments
		Generator<ILineSegment> gen4 = new SlidingLadderGenerator(100);  // dummy arguments
		Generator<ILineSegment> gen4a = new GridGenerator(100,5);  // dummy arguments
		Generator<ILineSegment> gen5 = new HubGenerator(40, 100, 100);  // dummy arguments
		Generator<ILineSegment> gen6 = new LoadFromFileGenerator("");   // dummy arguments
		
		gp.addGenerator("Uniform", gen1);
		gp.addGenerator("Double", gen2);
		gp.addGenerator("Integer", gen3);
		gp.addGenerator("Sliding Ladder", gen4);
		gp.addGenerator("Grid", gen4a);
		gp.addGenerator("Wheel Hub", gen5);
		gp.addGenerator("File...", gen6);
	}

	/** How to transform line segments. */
	@Override
	protected ILineSegment[] transform(ILineSegment[] segments,
			int width, int height) {
		ArrayList<ILineSegment> als = new ArrayList<ILineSegment>();

		double minX = 0;
		double minY = 0;
		double maxX = 0;
		double maxY = 0;

		for (ILineSegment ils: segments) {
			double x1 = ils.getStart().getX();
			double y1 = ils.getStart().getY();
			double x2 = ils.getEnd().getX();
			double y2 = ils.getEnd().getY();

			if (x1 < minX) { minX = x1; }
			if (x2 < minX) { minX = x2; }

			if (y1 < minY) { minY = y1; }
			if (y2 < minY) { minY = y2; }

			if (x1 > maxX) { maxX = x1; }
			if (x2 > maxX) { maxX = x2; }

			if (y1 > maxY) { maxY = y1; }
			if (y2 > maxY) { maxY = y2; }
		}

		double xFactor = 1.0 * width / (maxX - minX);
		double yFactor = 1.0 * height / (maxY - minY);

		// Does user want to scale?
		if (!shouldScale()) {
			ILineSegment[] copy = new ILineSegment [segments.length];
			for (int i = 0; i < copy.length; i++) {
				copy[i] = segments[i];
			}
			return copy;
		}

		// Scale appropriately
		for (ILineSegment ils: segments) {
			ILineSegment newOne = new TwoDLineSegment (
					new TwoDPoint(ils.getStart().getX()*xFactor,
							ils.getStart().getY()*yFactor),
							new TwoDPoint(ils.getEnd().getX()*xFactor,
									ils.getEnd().getY()*yFactor)
			);
			als.add(newOne);
		}

		// convert as array
		return als.toArray(new ILineSegment[0]);
	}
}