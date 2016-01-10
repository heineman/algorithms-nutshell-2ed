package algs.model.tests.kdtree;

import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IMultiPoint;
import algs.model.kdtree.*;
import algs.model.nd.Hypercube;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.twod.*;

/** These are nothing more than the TwoDTests converted to using KD trees. */
public class KDTest extends TestCase {

	// use for all max dimensions.
	int maxDimension = 2;


	/**
	 * Validate RectangularRegion.
	 */
	@Test
	public void testRectangularRegion () {
		TwoDRectangle rr = new TwoDRectangle(10, 10, 300, 153);
		assertEquals ("[10.0,10.0 : 300.0,153.0]", rr.toString());
		assertFalse (rr.equals("slkdjlds"));
		assertTrue (rr.equals (rr));
		assertFalse (rr.equals(null));
	}

	/**
	 * Validate the structure of KDNode trees, with alternating levels.
	 */
	@Test
	public void testStructure () {
		DimensionalNode hn = new DimensionalNode (1, new TwoDPoint (100, 100));
		DimensionalNode hn2 = new DimensionalNode(1,new TwoDPoint (100, 253));
		DimensionalNode vn = new DimensionalNode (2,new TwoDPoint (90, 120));
		DimensionalNode vn2 = new DimensionalNode(2,new TwoDPoint (110, 230));

		try {
			hn.setBelow(hn2);
			fail ("Structure of KDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			hn.setAbove(hn2);
			fail ("Structure of KDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			vn.setBelow(vn2);
			fail ("Structure of KDTree can be compromised.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			vn.setAbove(vn2);
			fail ("Structure of KDTree can be compromised.");
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
	public void testKDPoint() {
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
		DimensionalNode n = new DimensionalNode(2, new TwoDPoint (200, 153));
		assertEquals (2, n.dimension);

		assertTrue (n.isBelow(new TwoDPoint(10,10)));
		assertFalse (n.isBelow(new TwoDPoint(10,330)));
	}

	@Test
	public void testRootSetter() {
		// just here for closure.
		KDTree tt = new KDTree(2);
		DimensionalNode n = new DimensionalNode(1, new TwoDPoint (90, 120));
		tt.setRoot(n);
		assertEquals (new TwoDPoint (90,120), tt.getRoot().point);

		// allow for null.
		tt.setRoot(null);
		assertTrue (null == tt.getRoot());
		assertEquals (0, tt.count());
	}

	@Test
	public void testVerticalNode() {
		DimensionalNode n2, n3;
		DimensionalNode n = new DimensionalNode(1, new TwoDPoint (90, 120));
		assertEquals (n.dimension, 1);

		assertTrue (n.isBelow(new TwoDPoint(10,10)));     // means to the left
		assertFalse (n.isBelow(new TwoDPoint(210,330)));  // to the right

		//IHypercube space = new Hypercube (10, 300, 10, 300);

		// split vertical node 'below' (i.e., to the left)
		n = new DimensionalNode(1, new TwoDPoint (90, 120));
		n2 = new DimensionalNode(2, new TwoDPoint (10, 300)); 
		n.setBelow(n2);
		assertEquals (new Hypercube (Double.NEGATIVE_INFINITY, 90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), n2.region());

		// split vertical node 'above' (i.e., to the right)
		n = new DimensionalNode(1, new TwoDPoint (90, 120));
		n2 = new DimensionalNode(2, new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		assertEquals (new Hypercube (90, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), n2.region());

		n = new DimensionalNode(1, new TwoDPoint (90, 120));
		n2 = new DimensionalNode(2, new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		n3 = new DimensionalNode(1, new TwoDPoint (120, 400));
		n2.setAbove(n3);
		assertEquals (new Hypercube (90, Double.POSITIVE_INFINITY, 300.0, Double.POSITIVE_INFINITY), n3.region());

		n = new DimensionalNode(1, new TwoDPoint (90, 120));
		n2 = new DimensionalNode(2, new TwoDPoint (300, 300)); 
		n.setAbove(n2);
		n3 = new DimensionalNode(1, new TwoDPoint (120, 200));
		n2.setBelow(n3);
		assertEquals (new Hypercube (90, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 300.0), n3.region());
	}

	@Test
	public void testExceptions () {

		try {
			new KDTree(-2);
			fail ("KDTree constructor fails to check for proper parameters.");
		} catch (IllegalArgumentException iae) {

		}

		try {
			new KDTree(0);
			fail ("KDTree constructor fails to check for proper parameters.");
		} catch (IllegalArgumentException iae) {

		}

		KDTree tt = new KDTree(2);

		try {
			tt.insert(null);
			fail ("KDTree insert fails to detect null");
		} catch (IllegalArgumentException iae) {

		}

	}

	@Test
	public void testVariety() {
		KDTree tt = new KDTree(2);
		assertEquals (2, tt.maxDimension);
		tt.insert(new TwoDPoint (200, 153));

		DimensionalNode root = tt.getRoot();
		assertEquals (1, root.dimension);
		assertEquals (2, tt.nextDimension(root));
		assertEquals (2, tt.nextDimension(1));
		assertEquals (1, tt.nextDimension(2));

		final StringBuilder empRecord = new StringBuilder();
		Hypercube hc = new Hypercube (Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
		IVisitKDNode visitor = new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				empRecord.append ("*");	
			}

			public void drain(DimensionalNode node) {
				empRecord.append ("+");	
			}
		};
		tt.range(hc, visitor);
		assertEquals ("*", empRecord.toString());
		tt.removeAll();

		// ensure none left.
		empRecord.delete(0, empRecord.length());
		tt.range(hc, visitor);
		assertEquals ("", empRecord.toString());
	}

	@Test
	public void testNearest() {
		KDTree tt = new KDTree(2);

		IMultiPoint p1, p2, p3, p4;
		tt.insert(p1 = new TwoDPoint (100, 100));
		tt.insert(p2 = new TwoDPoint (-100, 100));
		tt.insert(p3 = new TwoDPoint (100, -100));
		tt.insert(p4 = new TwoDPoint (-100, -100));
		BruteForceNearestNeighbor bfnn = new
				BruteForceNearestNeighbor (new IMultiPoint[]{p1,p2,p3,p4});

		// right in the middle! All four are the same distance.
		IMultiPoint p0 = new TwoDPoint (0,0);

		IMultiPoint near = tt.nearest(p0);
		assertTrue (near == p1 || near == p2 || near == p3 || near == p4);
		// no point checking whether result of bfnn is same as tt since
		// any of the above four could be the closest...

		// clearly one.
		assertEquals (p1, tt.nearest (p1));
		assertEquals (p2, tt.nearest (p2));
		assertEquals (p3, tt.nearest (p3));
		assertEquals (p4, tt.nearest (p4));
		assertEquals (p1, bfnn.nearest (p1));
		assertEquals (p2, bfnn.nearest (p2));
		assertEquals (p3, bfnn.nearest (p3));
		assertEquals (p4, bfnn.nearest (p4));

		assertEquals (p1, tt.nearest (new TwoDPoint(1,1)));
		assertEquals (p3, tt.nearest (new TwoDPoint(1,-1)));
		assertEquals (p4, tt.nearest (new TwoDPoint(-1,-1)));
		assertEquals (p2, tt.nearest (new TwoDPoint(-1,1)));
		assertEquals (p1, bfnn.nearest (new TwoDPoint(1,1)));
		assertEquals (p3, bfnn.nearest (new TwoDPoint(1,-1)));
		assertEquals (p4, bfnn.nearest (new TwoDPoint(-1,-1)));
		assertEquals (p2, bfnn.nearest (new TwoDPoint(-1,1)));

	}

	@Test
	public void testNearestExceptions() {
		try {
			new BruteForceNearestNeighbor (null);
			fail ("must disallow brute force on null");
		} catch (IllegalArgumentException npe) {

		}

		try {
			new BruteForceNearestNeighbor (new IMultiPoint[]{});
			fail ("must disallow brute force on empty points");
		} catch (IllegalArgumentException npe) {

		}
	}		

	@Test
	public void testParent() {
		KDTree tt = new KDTree(2);

		try {
			tt.parent(null);
			fail ("KDTree fails to prevent parent of null");
		} catch (IllegalArgumentException e) {

		}

		assertNull (tt.parent(new TwoDPoint (200, 153)));

		IMultiPoint p1 = new TwoDPoint (100, 100);
		IMultiPoint p2 = new TwoDPoint (80, 90);
		IMultiPoint p3 = new TwoDPoint (120, 180);
		IMultiPoint p4 = new TwoDPoint (70, 110);
		IMultiPoint p5 = new TwoDPoint (220, 110);
		IMultiPoint p6 = new TwoDPoint (20, 20);   // not inserted
		IMultiPoint p7 = new TwoDPoint (300, 160); // not inserted

		tt.insert(p1);

		assertEquals (p1, tt.parent(p2).point);
		assertEquals (p1, tt.parent(p3).point);

		tt.insert(p2);
		tt.insert(p3);

		assertEquals (p2, tt.parent (p4).point);
		assertEquals (p3, tt.parent (p5).point);
		assertEquals (p2, tt.parent (p6).point);
		assertEquals (p3, tt.parent (p7).point);

		tt.insert(p4);
		tt.insert(p5);

		assertEquals (p5, tt.parent (p7).point);

		// show working count.
		CounterKDTree cdt = new CounterKDTree();
		assertEquals (0, cdt.getCount());
		Hypercube space = new Hypercube (Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		tt.range(space, cdt);
		assertEquals (5, cdt.getCount());
		cdt.reset();
		assertEquals (0, cdt.getCount());
		tt.range(new Hypercube (60,80, 100,120), cdt);
		assertEquals (1, cdt.getCount());

		// deal with traversal.
		final StringBuffer sb = new StringBuffer();
		KDTraversal kdt = new KDTraversal(tt) {
			public void visit(DimensionalNode node) { sb.append("*"); }
		};
		kdt.traverse();
		assertEquals ("*****", sb.toString());

		// validate drain does NOTHING.
		kdt.drain(null);

		// grab one intermediate node
		DimensionalNode inter = tt.getRoot().getAbove();
		sb.setLength(0);

		kdt = new KDTraversal(tt, inter) {
			public void visit(DimensionalNode node) { sb.append("*"); }
		};
		kdt.traverse();
		assertEquals ("**", sb.toString());

		// bad test
		sb.setLength(0);
		kdt = new KDTraversal(tt, null) {
			public void visit(DimensionalNode node) { sb.append("*"); }
		};
		kdt.traverse();
		assertEquals (0, sb.length());
	}

	@Test
	public void testSample () {
		KDTree tt = new KDTree(2);

		// quick simple boundary cases...
		assertEquals (0, tt.count());
		assertEquals (0, tt.height());
		assertEquals ("", tt.toString());

		// perform null search on empty tree
		Iterator<IMultiPoint> empResults = tt.range(new Hypercube (0,2,0,2));
		int count = 0;
		while (empResults.hasNext()) {
			count++;
			empResults.next();
		}
		assertEquals (0, count);

		final StringBuilder empRecord = new StringBuilder();
		tt.range(new Hypercube (79,81,143,145), new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				empRecord.append ("*");	
			}
			public void drain(DimensionalNode node) {
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
		tt.insert(new TwoDPoint (80,  144));
		tt.insert(new TwoDPoint (210,  40));
		tt.insert(new TwoDPoint (238, 150));

		tt.insert(new TwoDPoint (195,  30));
		tt.insert(new TwoDPoint (190, 140));
		tt.insert(new TwoDPoint (230,  90));
		tt.insert(new TwoDPoint (250, 148));

		assertEquals ("1:<120.0,80.0>2:<195.0,30.0>2:<180.0,133.0>1:<80.0,144.0>2:<190.0,140.0>1:<200.0,153.0>1:<210.0,40.0>2:<230.0,90.0>2:<245.0,120.0>1:<238.0,150.0>2:<250.0,148.0>", 
				tt.toString());

		// check count and heights
		assertEquals (11, tt.count());
		assertEquals (4, tt.height());

		// do empty query
		Iterator<IMultiPoint> results = tt.range(new Hypercube (0,2,0,2));
		assertEquals (false, results.hasNext());

		// try one by itself
		results = tt.range(new Hypercube (79,81,143,145));
		count = 0;
		while (results.hasNext()) {
			count++;
			results.next();
		}
		assertEquals (1, count);

		// try all by itself
		results = tt.range(new Hypercube (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		count = 0;
		while (results.hasNext()) {
			count++;
			results.next();
		}
		assertEquals (11, count);

		// validate catch attempts to modify iterator.
		results = tt.range(new Hypercube (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		KDSearchResults sr = (KDSearchResults) results;
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
				DimensionalNode tdn = new DimensionalNode(1, new TwoDPoint(0,0));
				sr.add(tdn);
				fail ("Shouldn't be able to remove points once iteration starts.");
			} catch (java.util.ConcurrentModificationException cme) {

			}

			sr.next();
		}
		assertEquals (11, count);

		// try more complex search.
		final StringBuilder record = new StringBuilder("");
		tt.range(new Hypercube (79,81,143,145), new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				record.append ("*");	
			}
			public void drain(DimensionalNode node) {
				record.append ("+");	
			}
		});
		assertEquals ("*", record.toString());

		// this will DRAIN all of the nodes...
		record.setLength(0);
		tt.range(new Hypercube (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				record.append ("*");	
			}
			public void drain(DimensionalNode node) {
				record.append ("+");	
			}
		});
		assertEquals ("+++++++++++", record.toString());
	}

	@Test
	public void testInspections () {
		KDTree tt = new KDTree(2);

		// construct tree in top-down fashion
		tt.insert(new TwoDPoint (200, 153));

		tt.insert(new TwoDPoint (180, 133));
		tt.insert(new TwoDPoint (245, 120));

		tt.insert(new TwoDPoint (180, 133));
		tt.insert(new TwoDPoint (245, 120));

		tt.insert(new TwoDPoint (120,  80));
		tt.insert(new TwoDPoint (80,  144));   // THE ONE found.
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

		// place a lot of poinst in this region (120,80) -- (200,133) to ensure drain of subtrees. 
		tt.insert(new TwoDPoint (130, 110));
		tt.insert(new TwoDPoint (137, 113));
		tt.insert(new TwoDPoint (197, 99));
		tt.insert(new TwoDPoint (197, 91));
		tt.insert(new TwoDPoint (167, 120));

		// Infer node properties from region
		tt.range(new Hypercube (79,343,77,165), new IVisitKDNode() {
			public void visit(DimensionalNode node) {

				// find leaves
				if (node.isLeaf()) {
					assertTrue (node.getAbove() == null);
					assertTrue (node.getBelow() == null);
				}
			}

			public void drain(DimensionalNode node) {}
		});

	}

}
