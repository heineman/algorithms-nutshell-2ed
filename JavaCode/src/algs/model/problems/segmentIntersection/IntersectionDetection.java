package algs.model.problems.segmentIntersection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import algs.model.FloatingPoint;
import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.list.Node;

/**
 * This superclass has been designed to enable the side-by-side comparison
 * of a number of line segment variations, where different data structures
 * are used to support the core algorithm.
 * <p>
 * Note that one implication of using this structure is that the line state 
 * may not be emptied in between executions.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class IntersectionDetection {

	/** 
	 * Reported intersections. This hash table is returned by all intersections methods.
	 *
	 * key: IPoint with the intersection
	 * value: List of the segments that intersect at that location.
	 */
	protected Hashtable<IPoint,List<ILineSegment>> report;

	/** computed time. */
	private long startingTime;
	private long computedTime;

	/**
	 * The subclass determines all intersections.
	 * 
	 * @param segments   Array of Segments that form the input set S.
	 * @return   hashtable containing the intersections.
	 */
	public abstract Hashtable<IPoint,List<ILineSegment>> intersections (ILineSegment[] segments);

	/**
	 * Compute the intersection of all segments when given a Collection of segments.
	 * 
	 * @param segments    Collection of Segments that form the input set S.
	 * @return   hashtable containing the intersections.
	 */
	public Hashtable<IPoint,List<ILineSegment>> intersections (Collection<ILineSegment> segments) {
		return intersections(segments.toArray(new ILineSegment[]{}));
	}

	/** Initialize algorithm. */
	protected void initialize () {
		report = new Hashtable<IPoint,List<ILineSegment>>();
	}

	/**
	 * Add the intersection to our report of these two segments. 
	 * 
	 * Take care that the same line segment is not added twice.
	 * <p>
	 * It is clear that the performance of this method can be improved upon.
	 * Note how it uses the inefficient pattern to only increase the size
	 * of the array by two with each discovered point. However, since this
	 * implementation affects equally the brute force implementation as well
	 * as the LineSweep implementation, it was left as is.
	 * <p>
	 * @param p   an intersection point
	 * @param il1 a member of a line pair that intersects at that intersection point
	 * @param il2 the other member of a line pair that also intersects at that intersection point 
	 */
	protected void record(IPoint p, ILineSegment il1, ILineSegment il2) {
		List<ILineSegment> segs = report.get(p);

		if (segs != null) {
			// assume we can add both of them...
			int add1 = 1, add2 = 1;

			// ensure that we aren't double-adding
			for (ILineSegment exists: segs) {
				if (add1 == 1 && exists == il1) { add1 = 0; }
				if (add2 == 1 && exists == il2) { add2 = 0; }
			}
			
			// both already in there! No need to add.
			if (add1+add2 == 0) { return; }
			
			// simply add to this list
			if (add1 == 1) { segs.append(il1); }
			if (add2 == 1) { segs.append(il2); }
		}  else {
			segs = new List<ILineSegment>();
			segs.append(il1);
			segs.append(il2);
		}
		
		// add to report.
		report.put(p, segs);
	}

	/**
	 * Add the intersection to our report.
	 * <p>
	 * Make sure that all reported intersections are merged with existing
	 * ones in the report, if this method is invoked multiple times with 
	 * the same intersection point p.
	 * <p>
	 * This method is a helper method that makes it easy to add line segments that are intersecting
	 * in one of three spots (upper, lower, intersecting).
	 *  
	 * @param p
	 * @param lists
	 */
	protected void record(IPoint p, List<ILineSegment> lows, List<ILineSegment> ups, List<ILineSegment> ints) { 

		// get or create the list of line segments associated with this point.
		List<ILineSegment> segs = report.get(p);
		if (segs == null) {
			segs = new List<ILineSegment>();
			report.put(p, segs);
		}
		
		// We have to ensure that the low/ups/ints lists are untouched, otherwise havoc will ensue
		Node<ILineSegment> node = lows.head();
		while (node != null) {
			segs.append(node.value());
			node = node.next();
		}
		
		node = ups.head();
		while (node != null) {
			segs.append(node.value());
			node = node.next();
		}
		
		node = ints.head();
		while (node != null) {
			segs.append(node.value());
			node = node.next();
		}
	}

	/**
	 * Compute the intersection of all segments when given an Iterator of segments.
	 * 
	 * @param it   Iterator of line segments from which intersections are to be computed.
	 * @return     Hashtable of computed intersections.
	 */
	public Hashtable<IPoint,List<ILineSegment>> intersections (Iterator<ILineSegment> it) {
		Collection<ILineSegment> c = new ArrayList<ILineSegment>();
		while (it.hasNext()) {
			c.add(it.next());
		}

		return intersections(c.toArray(new ILineSegment[]{}));
	}

	/** 
	 * Return last time to complete algorithm (in Milliseconds).
	 * @return time in milliseconds for algorithm to complete 
	 */
	public long time() {
		return computedTime;
	}

	// timing

	protected void computeTime() {
		computedTime = System.currentTimeMillis()-startingTime;
	}

	protected void startTime() {
		startingTime = System.currentTimeMillis();
	}

	/** 
	 * Helper function (for debugging) to ensure that the two resulting computations
	 * are the same within an epsilon (for the intersection points).
	 * 
	 * Automatically only considers non-self-intersecting line segments.
	 * 
	 * Generates a meaningful report if all found intersections are close enough.
	 * 
	 * @param result1   report from a previous line intersection algorithm
	 * @param result2   report from a previous line intersection algorithm
	 * @return          true if the results are essentially equivalent within epsilon.
	 */
	public static boolean sameWithinEpsilon (
			Hashtable<IPoint,List<ILineSegment>> result1,
			Hashtable<IPoint,List<ILineSegment>> result2) {

		// show results.
		for (IPoint ip1 : result1.keySet()) {
			for (IPoint ip2 : result2.keySet()) {
				if (FloatingPoint.value(ip1.getX() - ip2.getX()) != 0.0) { continue; }
				if (FloatingPoint.value(ip1.getY() - ip2.getY()) != 0.0) { continue; }

				// the same. compare intersections, which may be in any order.
				ArrayList<LineSegmentPair> results = new ArrayList<LineSegmentPair>();
				
				List<ILineSegment> segs1 = result1.get(ip1);
				
				// make cross-product of segs1
				Node<ILineSegment> node = segs1.head();
				while (node != null) {
					Node<ILineSegment> n2 = node.next();
					while (n2 != null) {
						results.add(new LineSegmentPair(node.value(), n2.value()));
						
						n2 = n2.next();
					}
					
					node = node.next();
				}
				
				// now remove each one as it is found
				List<ILineSegment> segs2 = result2.get(ip2);
				node = segs2.head();
				while (node != null) {
					Node<ILineSegment> n2 = node.next();
					while (n2 != null) {
						LineSegmentPair lsp = new LineSegmentPair(node.value(), n2.value());
						if (!results.remove(lsp)) {
							// Only happens with exceptional circumstances where floating
							// point error accumulates and causes an incorrect result.
							System.out.println("Unexpected intersection pair:" + lsp);
							List<ILineSegment> s1 = result1.get(ip1);
							List<ILineSegment> s2 = result2.get(ip2);
							for (ILineSegment ils : s1) {
								System.out.println("  " + ils);
							}
							System.out.println("------------------------------");
							for (ILineSegment ils : s2) {
								System.out.println("  " + ils);
							}
							return false;
						}
						
						n2 = n2.next();
					}
					
					node = node.next();
				}
			
				if (results.size() != 0) {
					System.out.println("Unmatched intersection pair.");
					return false;
				}
			}
		}

		return true;  // must be the same!
	}

	/**
	 * Convenient helper class to format the output from intersection algorithms.
	 * @param  result     result of computing intersections
	 */
	public void output (Hashtable<IPoint,List<ILineSegment>> result) {
		for (IPoint ip : result.keySet()) {
			List<ILineSegment> ilss = result.get(ip);
			System.out.println (ip);
			for (ILineSegment ils : ilss) {
				System.out.println ("  " + ils);
			}
			System.out.println();
		}
	}
}
