package algs.example.gui.problems.segmentIntersection.model;

import algs.model.ILineSegment;
import algs.model.twod.TwoDLineSegment;

/**
 * Stores a set of line segments.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LineSegmentModel extends Model<ILineSegment> {

	/** Return the type of entity being compared. */ 
	public String types () { return "Line Segments"; }
	
	/** Off in the middle of nowhere... */
	public static final ILineSegment nullEntry =
		new TwoDLineSegment(Integer.MIN_VALUE/4, Integer.MIN_VALUE/4,
				Integer.MIN_VALUE/4 + 5, Integer.MIN_VALUE/4 + 5);
	
	/** This line segment cannot intersect any other line segment (by design). */
	@Override
	public ILineSegment defaultEntity() {
		return nullEntry;
	}

	/**
	 * This example is the simplest, where the entities are themselves line
	 * segments so there is no transformation required.
	 * 
	 */
	@Override
	public int applyIntersections() {
		 intersections = detector.intersections(items);
		 return intersections.size();
	}

	@Override
	public void setItems(ILineSegment[] items) {
		this.items = new ILineSegment[items.length+1];
		this.items[0] = defaultEntity();
		System.arraycopy(items, 0, this.items, 1, items.length);
	}
}
