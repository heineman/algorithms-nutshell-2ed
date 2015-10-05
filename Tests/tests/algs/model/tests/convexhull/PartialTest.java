package algs.model.tests.convexhull;

import java.util.Iterator;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.list.DoubleLinkedList;
import algs.model.problems.convexhull.PartialHull;
import algs.model.problems.convexhull.andrew.PartialLinkedListHull;
import algs.model.twod.TwoDPoint;

import junit.framework.TestCase;

public class PartialTest extends TestCase {

	// PartialHull
	@Test
	public void testPartialHullBoundaries() {
		PartialHull ph = new PartialHull(new TwoDPoint (79, 20), new TwoDPoint (80, 21));
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		// error cases
		assertFalse (ph.removeMiddleOfLastThree());
		assertFalse (ph.areLastThreeNonRight());
		
		ph.add(new TwoDPoint(81, 22));
		assertEquals (3, ph.size());
		assertTrue (ph.hasThree());
		assertTrue (ph.areLastThreeNonRight());  // these are in a line!
		ph.removeMiddleOfLastThree();
	}
	
	// PartialLinkedListHull
	@Test
	public void testPartialLinkedListHullBoundaries() {
		PartialLinkedListHull ph = new PartialLinkedListHull(new TwoDPoint (79, 20), new TwoDPoint (80, 21));
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		// error cases
		assertFalse (ph.removeMiddleOfLastThree());
		assertFalse (ph.areLastThreeNonRight());
		
		ph.add(new TwoDPoint(81, 22));
		assertEquals (3, ph.size());
		assertTrue (ph.hasThree());
		assertTrue (ph.areLastThreeNonRight());  // these are in a line!
		ph.removeMiddleOfLastThree();
	}
	
	// PartialLinkedListHull
	@Test
	public void testPartialLinkedListHull() {
		PartialLinkedListHull ph = new PartialLinkedListHull(new TwoDPoint (79, 20), new TwoDPoint (80, 42));
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		ph.add(new TwoDPoint(81, 200));
		assertEquals (3, ph.size());
		assertTrue (ph.hasThree());
		assertTrue (ph.areLastThreeNonRight());
		ph.removeMiddleOfLastThree();
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		DoubleLinkedList<IPoint> dll = ph.points();
		assertEquals (new TwoDPoint(79, 20), dll.first().value());
		assertEquals (new TwoDPoint(81, 200), dll.first().next().value());
		assertEquals (2, ph.size());
		assertEquals (2, dll.size());
	}
	
	@Test
	public void testPartialHull() {
		PartialHull ph = new PartialHull(new TwoDPoint (79, 20), new TwoDPoint (80, 42));
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		ph.add(new TwoDPoint(81, 200));
		assertEquals (3, ph.size());
		assertTrue (ph.hasThree());
		assertTrue (ph.areLastThreeNonRight());
		ph.removeMiddleOfLastThree();
		assertEquals (2, ph.size());
		assertFalse (ph.hasThree());
		Iterator<IPoint> it = ph.points();
		assertEquals (new TwoDPoint(79, 20), it.next());
		assertEquals (new TwoDPoint(81, 200), it.next());
		assertFalse (it.hasNext());
	}
}
