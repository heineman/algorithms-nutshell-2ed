package algs.model.tests.kdtree;

import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.kdtree.HorizontalNode;
import algs.model.kdtree.IVisitTwoDNode;
import algs.model.kdtree.TwoDNode;
import algs.model.kdtree.TwoDSearchResults;
import algs.model.kdtree.TwoDTree;
import algs.model.kdtree.VerticalNode;
import algs.model.twod.TwoDRectangle;
import algs.model.twod.TwoDPoint;

public class TwoDTest extends TestCase {


	/**
	 * Validate RectangularRegion.
	 */
	@Test
	public void testRectangularRegion () {
		TwoDRectangle rr = new TwoDRectangle(10, 10, 300, 153);
		TwoDRectangle rr2 = new TwoDRectangle(10, 10, 300, 153);
		assertEquals ("[10.0,10.0 : 300.0,153.0]", rr.toString());
		assertFalse (rr.equals("slkdjlds"));
		assertTrue (rr.equals (rr));
		assertTrue (rr.equals (rr2));
		assertTrue (rr2.equals (rr));
		assertFalse (rr.equals(null));
	}

	/**
	 * Validate the structure of TwoDNode trees, with alternating levels.
	 */
	@Test
	public void testStructure () {
		HorizontalNode hn = new HorizontalNode(new TwoDPoint (200, 153));
		HorizontalNode hn2 = new HorizontalNode(new TwoDPoint (100, 253));
		VerticalNode vn = new VerticalNode(new TwoDPoint (90, 120));
		VerticalNode vn2 = new VerticalNode(new TwoDPoint (110, 230));

		try {
			hn.setBelow(hn2);
			fail ("Structure of TwoDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			hn.setAbove(hn2);
			fail ("Structure of TwoDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			vn.setBelow(vn2);
			fail ("Structure of TwoDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			vn.setAbove(vn2);
			fail ("Structure of TwoDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		// allowed [These only check the proper types are allowed. This actually 
		// produces a cyclic tree...]
		hn.setBelow (vn);
		vn.setBelow (hn2);
		hn.setAbove (vn2);
		vn2.setAbove (hn);

	}

	@Test
	public void testTwoDPoint() {
		TwoDPoint pt = new TwoDPoint(10,30);
		assertEquals (10.0, pt.getX());
		assertEquals (30.0, pt.getY());

		// probe equals
		TwoDPoint pt2 = new TwoDPoint(10,30);
		assertEquals (pt, pt2);
		assertEquals (pt2, pt);
		assertFalse (pt.equals("SDS"));
		assertFalse (pt.equals(null));

	}

	@Test
	public void testHorizontalNode() {
		HorizontalNode hn = new HorizontalNode(new TwoDPoint (200, 153));
		assertFalse (hn.isVertical());

		assertTrue (hn.isBelow(new TwoDPoint(10,10)));   // below
		assertFalse (hn.isBelow(new TwoDPoint(10,330))); // above
	}

	@Test
	public void testVerticalNode() {
		TwoDNode n2, n3;
		TwoDNode n = new VerticalNode(new TwoDPoint (90, 120));

		assertTrue (n.isBelow(new TwoDPoint(10,10)));     // means to the left
		assertFalse (n.isBelow(new TwoDPoint(210,330)));  // to the right

		// obvious stuff
		assertTrue (null == n.getBelow());
		assertTrue (null == n.getAbove());
		n.setBelow(null);
		n.setAbove(null);
		assertTrue (null == n.getBelow());
		assertTrue (null == n.getAbove());

		//IHypercube space = new Hypercube (10, 300, 10, 300);

		// split vertical node 'below' (i.e., to the left)
		n = new VerticalNode(new TwoDPoint (90, 120));
		n2 = new HorizontalNode(new TwoDPoint (10, 300)); 
		n.setBelow(n2);
		// double left, double bottom, double right, double top
		assertEquals (new TwoDRectangle (Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 90.0, Double.POSITIVE_INFINITY), n2.getRegion());

		// split vertical node 'above' (i.e., to the right)
		n = new VerticalNode(new TwoDPoint (90, 120));
		n2 = new HorizontalNode(new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		assertEquals (new TwoDRectangle (90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), n2.getRegion());

		n = new VerticalNode(new TwoDPoint (90, 120));
		n2 = new HorizontalNode(new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		n3 = new VerticalNode(new TwoDPoint (120, 400));
		n2.setAbove(n3);
		// double left, double bottom, double right, double top
		assertEquals (new TwoDRectangle (90, 300, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), n3.getRegion());

		n = new VerticalNode(new TwoDPoint (90, 120));
		n2 = new HorizontalNode(new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		n3 = new VerticalNode(new TwoDPoint (120, 200));
		n2.setBelow(n3);
		assertEquals (new TwoDRectangle (90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 300.0), n3.getRegion());
	}

	@Test
	public void testSample () {
		TwoDTree tt = new TwoDTree();

		// basic checks
		assertEquals ("", tt.toString());

		// any check for parentage on an empty tree returns null.
		assertTrue (tt.parent(new TwoDPoint (200, 153)) == null);

		// perform null search on empty tree
		Iterator<IPoint> empResults = tt.range(new TwoDRectangle(0,0,2,2));
		int count = 0;
		while (empResults.hasNext()) {
			count++;
			empResults.next();
		}
		assertEquals (0, count);

		final StringBuilder empRecord = new StringBuilder();
		tt.range(new TwoDRectangle(79,143,81,145), new IVisitTwoDNode() {
			public void visit(TwoDNode node) {
				empRecord.append ("*");	
			}
			public void drain(TwoDNode node) {
				empRecord.append ("+");	
			}
		});
		assertEquals ("", empRecord.toString());

		// construct tree in top-down fashion
		tt.insert(new TwoDPoint (200, 153));

		assertNotNull(tt.getRoot());
		assertEquals (new TwoDPoint (200, 153), tt.getRoot().point);

		tt.insert(new TwoDPoint (180, 133));
		tt.insert(new TwoDPoint (245, 120));

		tt.insert(new TwoDPoint (120,  80));
		tt.insert(new TwoDPoint (80,  144));   // THE ONE fond.
		tt.insert(new TwoDPoint (210,  40));
		tt.insert(new TwoDPoint (238, 150));

		tt.insert(new TwoDPoint (195,  30));
		tt.insert(new TwoDPoint (190, 140));
		tt.insert(new TwoDPoint (230,  90));
		tt.insert(new TwoDPoint (250, 148));

		// add more to get some internal nodes that are fully defined without Infinity
		tt.insert(new TwoDPoint (60, 80));
		tt.insert(new TwoDPoint (65, 75));
		tt.insert(new TwoDPoint (200, 40));
		tt.insert(new TwoDPoint (200, 200));
		tt.insert(new TwoDPoint (140, 80));

		// place a lot of poins in this region (120,80) -- (200,133) to ensure drain of subtrees. 
		tt.insert(new TwoDPoint (130, 110));
		tt.insert(new TwoDPoint (137, 113));
		tt.insert(new TwoDPoint (197, 99));
		tt.insert(new TwoDPoint (197, 91));
		tt.insert(new TwoDPoint (167, 120));


		// do empty query
		Iterator<IPoint> results = tt.range(new TwoDRectangle(0,0,2,2));
		count = 0;
		while (results.hasNext()) {
			count++;
			results.next();
		}
		assertEquals (0, count);

		// try one by itself
		results = tt.range(new TwoDRectangle(79,143,81,145));
		count = 0;
		while (results.hasNext()) {
			count++;
			results.next();
		}
		assertEquals (1, count);

		// try all by itself
		results = tt.range(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		count = 0;
		while (results.hasNext()) {
			count++;
			results.next();
		}
		assertEquals (21, count);

		// try more complex search.
		final StringBuilder record = new StringBuilder("");
		tt.range(new TwoDRectangle(79,143,81,145), new IVisitTwoDNode() {
			public void visit(TwoDNode node) {
				record.append ("*");	
			}
			public void drain(TwoDNode node) {
				record.append ("+");	
			}
		});
		assertEquals ("*", record.toString());

		// validate catch attempts to modify iterator.
		results = tt.range(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		TwoDSearchResults sr = (TwoDSearchResults) results;
		count = 0;
		while (sr.hasNext()) {
			count++;
			
			// must fail
			try {
				sr.add(new TwoDPoint(0,0));
				fail ("Shouldn't be able to add points once iteration starts.");
			} catch (java.util.ConcurrentModificationException cme) {
				
			}
			
			// must fail
			try {
				sr.remove();
				fail ("Shouldn't be able to remove points once iteration starts.");
			} catch (UnsupportedOperationException uoe) {
				
			}
			
			// must fail
			try {
				TwoDNode tdn = new HorizontalNode(new TwoDPoint(0,0));
				sr.add(tdn);
				fail ("Shouldn't be able to remove points once iteration starts.");
			} catch (java.util.ConcurrentModificationException cme) {
				
			}
			
			sr.next();
		}
		assertEquals (21, count);

		record.setLength(0);
		tt.range(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), new IVisitTwoDNode() {
			public void visit(TwoDNode node) {
				record.append ("*");	
			}
			public void drain(TwoDNode node) {
				record.append ("+");	
			}
		});

		// one drain!
		assertEquals ("******++++++*********", record.toString());
	}

	@Test
	public void testErrors () {
		TwoDTree tt = new TwoDTree();

		// basic checks
		try {
			tt.parent(null);
			fail ("Must detect null on TwoDTree parent request.");
		} catch (Exception e) {

		}

		// basic checks
		try {
			tt.insert(null);
			fail ("Must detect null on TwoDTree insert request.");
		} catch (Exception e) {

		}


	}

}
