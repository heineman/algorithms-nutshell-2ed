package algs.model.tests.intersections;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.problems.segmentIntersection.EventPoint;
import algs.model.problems.segmentIntersection.EventQueue;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

public class EventQueueTest extends TestCase {

	// make sure multiple inserted endpoints join their upper line segments so none are lost.
	@Test
	public void testInsert() {
		EventQueue q = new EventQueue();
		
		// start high at (100,100)
		IPoint p = new TwoDPoint(100, 100);
		
		// terminating line at (20,20)
		ILineSegment ils = new TwoDLineSegment(p, new TwoDPoint(20, 20)); 
		EventPoint ep = new EventPoint(p);
		ep.addUpperLineSegment(ils);
		q.insert(ep);
		
		// next one terminating line at (30,30)
		EventPoint ep2 = new EventPoint(p);
		ILineSegment ils2 = new TwoDLineSegment(p, new TwoDPoint(30, 30));
		ep2.addUpperLineSegment(ils2);
		q.insert(ep2);
		
		// both added to the same event point
		EventPoint ep3 = q.min();
		
		assertEquals (2, ep3.upperEndpointSegments().size());
		
		assertTrue (q.isEmpty());
		
	}

}
