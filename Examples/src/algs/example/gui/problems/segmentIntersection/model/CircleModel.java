package algs.example.gui.problems.segmentIntersection.model;

import java.util.ArrayList;
import java.util.Hashtable;
import algs.model.FloatingPoint;

import algs.model.ICircle;
import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.twod.TwoDCircle;
import algs.model.twod.TwoDPoint;

/**
 * Stores a set of circles to be checked for intersections. This is done by 
 * constructing bounding boxes for the circles and using the intersections
 * between these bounding boxes as proxies for the circle intersections.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CircleModel extends Model<ICircle> {

	/** Return the type of entity being compared. */ 
	public String types () { return "Circles"; }
	
	
	/** Off in the middle of nowhere... */
	public static final ICircle nullEntry =
		new TwoDCircle(Integer.MIN_VALUE/4, Integer.MIN_VALUE/4, 1);
	
	/** This circle cannot intersect any other circle (by design). */
	@Override
	public ICircle defaultEntity() {
		return nullEntry;
	}

	// circles will appear in this rough array.
	private ILineSegment[] boxes;
	
	/**
	 * pre-process the circles 
	 * 
	 * @param items
	 */
	public void setItems(ICircle[] items) {
		
		// represent each circle as a box
		boxes = new ILineSegment[4*items.length];
		
		int idx = 0;
		double x,y,r;
		
		// take all four lines and push them into this array
		for (ICircle one : items) {
			x = one.getX();
			y = one.getY();
			r = one.getRadius();
			
			boxes[idx++] = new BoxLineSegment (one, x-r, y-r, x+r, y-r);
			boxes[idx++] = new BoxLineSegment (one, x+r, y-r, x+r, y+r);
			boxes[idx++] = new BoxLineSegment (one, x+r, y+r, x-r, y+r);
			boxes[idx++] = new BoxLineSegment (one, x-r, y+r, x-r, y-r);				
		}
		
		this.items = new ICircle[items.length+1];
		this.items[0] = defaultEntity();
		System.arraycopy(items, 0, this.items, 1, items.length);
	}
	
	
	/**
	 * Set the dynamic entity being managed by the model.
	 * 
	 * @param e    Set new entry, or null to clear/remove old one
	 */
	public void setDynamicEntity (ICircle e) {
		super.setDynamicEntity(e);
		
		if (e == null) {
			e = defaultEntity();  // to work appropriately
		}
		
		// convert and place into boxes[0..3]
		double x = e.getX();
		double y = e.getY();
		double r = e.getRadius();
		
		boxes[0] = new BoxLineSegment (e, x-r, y-r, x+r, y-r);
		boxes[1] = new BoxLineSegment (e, x+r, y-r, x+r, y+r);
		boxes[2] = new BoxLineSegment (e, x+r, y+r, x-r, y+r);
		boxes[3] = new BoxLineSegment (e, x-r, y+r, x-r, y-r);
	}
	
	/**
	 * This example is the simplest, where the entities are themselves line
	 * segments so there is no transformation required.
	 * 
	 */
	@Override
	public int applyIntersections() {
		
		// operate on internal ones...
		Hashtable<IPoint, List<ILineSegment>> lineIntersections = detector.intersections(boxes);
		Hashtable<IPoint,ICircle[]> circleIntersections = new Hashtable<IPoint,ICircle[]>();
		
		// process all of these to uncover the circles that intersect.
		for (IPoint pt : lineIntersections.keySet()) {
			// each point has a set of intersections. Reconvert back to the circles
			// from which lines were created.
			List<ILineSegment> ints = lineIntersections.get(pt);
			ArrayList<ICircle> circs = new ArrayList<ICircle>();
			for (ILineSegment ils : ints) {
				BoxLineSegment bls = (BoxLineSegment) ils;
				ICircle c = bls.circle();
				if (!circs.contains(c)) {
					circs.add(c);
				}
			}
			
			// only add if circs.length > 1
			if (circs.size() > 1) {
				circleIntersections.put(pt, circs.toArray(new ICircle[]{}));
			}
		}
		
		// now we need to determine how many of these intersections are really real.
		this.intersections = filter(circleIntersections);
		return this.intersections.size();
	}
	
	/**
	 * This method implements the algebraic intersection detection method to determine
	 * whether the circles are actually intersecting.
	 * 
	 * Some false positives may occur, and thus must be filtered out.
	 * 
	 * @param circleIntersections
	 */
	private Hashtable<IPoint, List<ICircle>> filter(Hashtable<IPoint, ICircle[]> circleIntersections) {
		Hashtable<IPoint, List<ICircle>> real = new Hashtable<IPoint, List<ICircle>>(); 
		
		for (IPoint pt : circleIntersections.keySet()) {
			// Must try each of the C(n,2) pairs to determine if they really intersect.
			// This breaks the O (n log n) performance of the line intersection algorithm
			// especially since the number of intersecting circles may be as larger as N
			// if they all intersect the same point. Hey, I guess there is no such thing
			// as a free lunch!
			ICircle []circs = circleIntersections.get(pt);
			for (int i = 0; i < circs.length-1; i++) {
				for (int j = i+1; j < circs.length; j++) {
					IPoint []pts = intersects (circs[i], circs[j]);
					if (pts != null) {
						for (IPoint p : pts) {
							report (real, p, circs[i], circs[j]);
						}
					}
				}
			}
		}
		
		// these are in Cartesian coordinates and must be converted
		// into AWT coordinates.
		return real;		
	}

	/**
	 * Add the intersection to our report.
	 * <p>
	 * Make sure that all reported intersections are merged with existing
	 * ones in the report, if this method is invoked multiple times with 
	 * the same intersection point p.
	 * 
	 * @param real      set of existing intersections
	 * @param lists     the point which intersects circle one and two
	 * @param one       circle one involved in the intersection
	 * @param two       circle two involved in the intersection
	 */
	private void report(Hashtable<IPoint, List<ICircle>> real, IPoint p, ICircle one, ICircle two) {
			
		List<ICircle> segs = real.get(p);
		
		if (segs == null) {
			segs = new List<ICircle>();
			real.put(p, segs);
		}

		segs.append(one);
		segs.append(two);
	}
	
	/**
	 * Follow the coded algorithm as described in the exercises. This returns
	 * the point(s) at which the two circles intersect (may be null, 1, or 2 pts.) 
	 * 
	 * (x-a)^2 + (y-b)^2 = r^2
	 * (x-c)^2 + (y-d)^2 = s^2
	 * 
	 * @param circle
	 * @param circle2
	 */
	private IPoint[] intersects(ICircle circle, ICircle circle2) {
		double a = circle.getX();
		double b = circle.getY();
		double r = circle.getRadius();
		double c = circle2.getX();
		double d = circle2.getY();
		double s = circle2.getRadius();
		
		double D = Math.sqrt ((a-c)*(a-c)+(b-d)*(b-d));
		double A = Math.sqrt((D+r+s)*(D+r-s)*(D-r+s)*(-D+r+s))/4;
		double xBase = (a+c)/2 - (a-c)*(r*r-s*s)/(2*D*D);
		double xDelta = 2*(b-d)*A/(D*D);
		double yBase = (b+d)/2 - (b-d)*(r*r-s*s)/(2*D*D);	
		double yDelta = 2*(a-c)*A/(D*D);
	
		if (FloatingPoint.lesserEquals(Math.abs(r-s), D) &&
				FloatingPoint.lesserEquals (D, Math.abs(r+s))) {
			IPoint []vals = new IPoint[] {
					new TwoDPoint (xBase + xDelta, yBase - yDelta),
					new TwoDPoint (xBase - xDelta, yBase + yDelta)
			};
			
			if (vals[0].equals(vals[1])) {
				return new IPoint[]{vals[0]};  // Only one real solution.
			} else {
				return vals;
			}
		} else {
			return null;  // no solution
		}		
	}
}
