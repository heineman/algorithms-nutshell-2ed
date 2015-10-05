package algs.model.tests.chapter7;

import java.util.Iterator;

import org.junit.Test;

import junit.framework.TestCase;
import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.data.points.CircleGenerator;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTraversal;
import algs.model.kdtree.KDTree;
import algs.model.kdtree.TwoDFactory;
import algs.model.kdtree.TwoDNode;
import algs.model.kdtree.TwoDTraversal;
import algs.model.kdtree.TwoDTree;
import algs.model.nd.Hypercube;
import algs.model.twod.TwoDPoint;
import algs.model.twod.TwoDRectangle;

public class RecursionOptionsTest extends TestCase {

	@Test
	public void testSmallSample() {
		int[][] pointData = {{5,80}, {15,60}, {25, 30}, {35,50}, {45, 70}, {55, 40}, {65, 20}};
		
		IMultiPoint[] points = new TwoDPoint[pointData.length];
		for (int i = 0; i < pointData.length; i++) {
			points[i] = new TwoDPoint(pointData[i][0], pointData[i][1]);
		}
		KDTree tree = KDFactory.generate(points);
		
		System.out.println(tree);
			    
	}
	
	@Test
	public void testGenerateJustOneAndTwo() {
		CircleGenerator cg = new CircleGenerator(1);
		cg = (CircleGenerator) cg.construct(new String[]{"1"});
			
		IPoint[] points = cg.generate(1);
		KDTree tree = KDFactory.generate(points);
		
		assertEquals (1, tree.count());
		
		TwoDTree tree2D = TwoDFactory.generate(points);
		assertEquals (1, tree2D.count());
		
		points = cg.generate(2);
		tree = KDFactory.generate(points);
		
		assertEquals (2, tree.count());
		
		tree2D = TwoDFactory.generate(points);
		assertEquals (2, tree2D.count());
	}
	
	@Test
	public void testCount() {
		CircleGenerator cg = new CircleGenerator(1);
		cg = (CircleGenerator) cg.construct(new String[]{"1"});
			
		IPoint[] points = cg.generate(3);
		TwoDTree tree2D = TwoDFactory.generate(points);
		assertEquals (3, tree2D.count());
		
		tree2D = new TwoDTree();
		assertEquals (0, tree2D.count());
	}
	
	@Test
	public void testRecursion() {
		for (int n = 2; n < 32768; n*= 2) {
			
			CircleGenerator cg = new CircleGenerator(1);
			assertTrue (cg.parameters() != null);
			cg = (CircleGenerator) cg.construct(new String[]{"1"});
			
			IPoint[] points = cg.generate(n);
			KDTree tree = KDFactory.generate(points);
	
			// now run a series of queries.
			int numTrials = 50;
			int totalDR = 0;
			int totalR  = 0;
			for (int j = 0; j < numTrials; j++) {
				TwoDPoint tp = new TwoDPoint(Math.random(), Math.random());
				tree.nearest(tp);
				totalDR += tree.getNumDoubleRecursion();
				totalR  += tree.getNumRecursion();				
			}
			
			System.out.println (n + "," + totalR + "," + totalDR);
		}
	}
	
	@Test
	public void testCompatibility() {
		int square = 10;
		
		// have more points to fully exercise all paths within the KD/2D-trees
		IPoint[] points = new IPoint[] {
				new TwoDPoint(0,0),
				new TwoDPoint(-10,3),
				new TwoDPoint (-7, -5),
				new TwoDPoint (-5, -9),
				new TwoDPoint (3, -4),
				new TwoDPoint (3, 2),
				new TwoDPoint (-2, 11),
				new TwoDPoint (8, -3),
				new TwoDPoint (-9, 14),
				new TwoDPoint (6, 3),
				new TwoDPoint (9, 12),
		};
		
		KDTree tree = KDFactory.generate(points);
		TwoDTree tree2D = TwoDFactory.generate(points);

		Iterator<IPoint> res2D = tree2D.range(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		Iterator<IMultiPoint> res = tree.range(new Hypercube(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY));
		int count = 0;
		while (res.hasNext()) { 
			count++;
			res.next();
		}
		int count2d = 0;
		while (res2D.hasNext()) { 
			count2d++;
			res2D.next();
		}
		assertEquals (count2d, count);
		
		// minor things
		assertTrue (tree2D.toString() != null);
		assertTrue (tree.toString() != null);
		assertTrue (tree.nearest(null) == null);
		assertTrue (tree2D.nearest(null) == null);
		
		// now run a series of queries.
		int numTrials = 200;
		for (int j = 0; j < numTrials; j++) {
			TwoDPoint tp = new TwoDPoint(Math.random()*square, Math.random()*square);
			IMultiPoint s = tree.nearest(tp);
			IPoint p2 = tree2D.nearest(tp);
			
			assertEquals (s, p2);
		}
		
		// show traversal.
		
		// deal with traversal.
		final StringBuffer sb = new StringBuffer();
		TwoDTraversal tdt = new TwoDTraversal(tree2D) {
			public void visit(TwoDNode node) { sb.append("*"); }
		};
		tdt.traverse();
		assertEquals ("***********", sb.toString());
		
		// empty traversal
		final StringBuffer sb2 = new StringBuffer();
		tdt = new TwoDTraversal(new TwoDTree()) {
			public void visit(TwoDNode node) { sb2.append("*"); }
		};
		tdt.traverse();
		assertEquals ("", sb2.toString());
		
		// null traversal
		final StringBuffer sb3 = new StringBuffer();
		tdt = new TwoDTraversal(null) {
			public void visit(TwoDNode node) { sb3.append("*"); }
		};
		tdt.traverse();
		assertEquals ("", sb3.toString());
		
		// NOTHING to be done and we're ok with that...
		tdt.drain(null);
	}
	
	public void testNull() {
		IPoint []points = new IPoint[0];
		KDTree tree = KDFactory.generate(points);
		assertTrue (tree == null);
		
		IMultiPoint []mps = new IMultiPoint[0];
		tree = KDFactory.generate(mps);
		assertTrue (tree == null);
	}
	
	public void testEmptyTraversal() {
		KDTree tree = null;
		final StringBuffer sb = new StringBuffer();
		KDTraversal kdt = new KDTraversal(tree) {
			@Override
			public void visit(DimensionalNode node) {
				sb.append("*");
			}
		};
		
		kdt.traverse();
		assertEquals ("", sb.toString());
		
		tree = new KDTree(2);
		kdt = new KDTraversal(tree) {
			@Override
			public void visit(DimensionalNode node) {
				sb.append("*");
			}
		};
		kdt.traverse();
		assertEquals ("", sb.toString());
	}	
	
	public void testNull2D() {
		IPoint []points = new IPoint[0];
		TwoDTree tree = TwoDFactory.generate(points);
		assertTrue (tree == null);
	}
	
	public void testMultipleTypes() {
		
		
		IPoint []points = new IPoint[1];
		points[0] = new IPoint() {

			@Override
			public int compareTo(IPoint o) {
				if (getX() < o.getX()) { return -1; }
				if (getX() > o.getX()) { return +1; }
				if (getY() < o.getY()) { return -1; }
				if (getY() > o.getY()) { return +1; }
				
				return 0;
			}

			@Override
			public double getX() {
				return 11;
			}

			@Override
			public double getY() {
				return 12;
			}
			
		};
		
		KDTree tree = KDFactory.generate(points);
		assertEquals(1, tree.count());
	}
	
}