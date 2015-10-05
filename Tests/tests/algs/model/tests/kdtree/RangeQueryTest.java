package algs.model.tests.kdtree;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.IVisitKDNode;
import algs.model.nd.Hypercube;
import algs.model.problems.rangeQuery.BruteForceRangeQuery;

import algs.model.twod.TwoDPoint;

/** These are nothing more than the TwoDTests converted to using KD trees. */
public class RangeQueryTest extends TestCase {
	
	
	/**
	 * Test simple cases.
	 */
	@Test
	public void testSimpleOnes () {
		// empty 
		IMultiPoint []args = new IMultiPoint[0];
		BruteForceRangeQuery bf = new BruteForceRangeQuery(args);
		ArrayList<IMultiPoint> al = bf.search(new Hypercube(0,2,0,2));
		assertEquals (0, al.size());
		
		// just one
		args = new IMultiPoint[] { new TwoDPoint(1,1.5) };
		bf = new BruteForceRangeQuery(args);
		al = bf.search(new Hypercube(0,2,0,2));
		
		IMultiPoint p = al.get(0);
		assertEquals (1.0, p.getCoordinate(1));
		assertEquals (1.5, p.getCoordinate(2));
	}
	

	@Test
	public void testIPointOrIMultiPoint () {
		IPoint[] pointsIP = new IPoint[] {
				new TwoDPoint (200, 153),
				new TwoDPoint (180, 133),
				new TwoDPoint (245, 120),
				
				new TwoDPoint (120,  80),
				new TwoDPoint (80,  144),
				new TwoDPoint (210,  40),
				new TwoDPoint (238, 150),
				
				new TwoDPoint (195,  30),
				new TwoDPoint (190, 140),
				new TwoDPoint (230,  90),
				new TwoDPoint (250, 148),
		};
		
		IMultiPoint[] points = new IMultiPoint[] {
				new TwoDPoint (200, 153),
				new TwoDPoint (180, 133),
				new TwoDPoint (245, 120),
				
				new TwoDPoint (120,  80),
				new TwoDPoint (80,  144),
				new TwoDPoint (210,  40),
				new TwoDPoint (238, 150),
				
				new TwoDPoint (195,  30),
				new TwoDPoint (190, 140),
				new TwoDPoint (230,  90),
				new TwoDPoint (250, 148),
		};
		
		BruteForceRangeQuery bf = new BruteForceRangeQuery(points);
		BruteForceRangeQuery bfIP = new BruteForceRangeQuery(pointsIP);
		

		// try one by itself
		ArrayList<IMultiPoint> results = bf.search(new Hypercube (79,81,143,145));
		ArrayList<IMultiPoint> resultsIP = bfIP.search(new Hypercube (79,81,143,145));
		assertEquals (1, results.size());
		assertEquals (1, resultsIP.size());
		assertEquals (results.get(0), resultsIP.get(0));
	}
	
	@Test
	public void testSample () {
		IMultiPoint[] points = new IMultiPoint[] {
				new TwoDPoint (200, 153),
				new TwoDPoint (180, 133),
				new TwoDPoint (245, 120),
				
				new TwoDPoint (120,  80),
				new TwoDPoint (80,  144),
				new TwoDPoint (210,  40),
				new TwoDPoint (238, 150),
				
				new TwoDPoint (195,  30),
				new TwoDPoint (190, 140),
				new TwoDPoint (230,  90),
				new TwoDPoint (250, 148),
		};
		BruteForceRangeQuery bf = new BruteForceRangeQuery(points);
	
		// do empty query
		ArrayList<IMultiPoint> results = bf.search(new Hypercube (0,2,0,2));
		assertEquals (0, results.size());
		
		// try one by itself
		results = bf.search(new Hypercube (79,81,143,145));
		assertEquals (1, results.size());
		
		// try all by itself
		results = bf.search(new Hypercube (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertEquals (11, results.size());
		
		// try more complex search.
		final StringBuilder record = new StringBuilder("");
		bf.search(new Hypercube (79,81,143,145), new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				record.append ("*");	
			}
			public void drain(DimensionalNode node) {
				record.append ("+");	
			}
		});
		assertEquals ("*", record.toString());
		
		record.setLength(0);
		bf.search(new Hypercube (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), new IVisitKDNode() {
			public void visit(DimensionalNode node) {
				record.append ("*");	
			}
			public void drain(DimensionalNode node) {
				record.append ("+");	
			}
		});
		assertEquals ("***********", record.toString());
	}
	
		
}
