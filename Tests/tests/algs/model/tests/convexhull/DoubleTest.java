package algs.model.tests.convexhull;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.list.DoubleLinkedList;
import algs.model.twod.TwoDPoint;

public class DoubleTest extends TestCase {

	@Test
	public void testDoubles() {
		DoubleLinkedList<IPoint> dl = new DoubleLinkedList<IPoint>(IPoint.xy_sorter);
		
		dl.insert(new TwoDPoint(2, 4));
		dl.insert(new TwoDPoint(2, 8));
		dl.insert(new TwoDPoint(2, 6));
		dl.insert(new TwoDPoint(2, 1));		
	}
}
