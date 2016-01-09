package algs.model.tests.convexhull;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.balanced.BalancedTreeAndrew;
import algs.model.problems.convexhull.bucket.BucketAndrew;
import algs.model.problems.convexhull.graham.GrahamScan;
import algs.model.problems.convexhull.graham.NativeGrahamScan;
import algs.model.problems.convexhull.heap.HeapAndrew;
import algs.model.twod.TwoDPoint;

/**
 * note that SlowHull still may return different hull points because of the
 * errors inherent in floating point computations. For this reason, SlowHull
 * is not considered in this test case.
 *  
 */
public class HullComparisonsTest extends TestCase {

	
	@Test
	public void testSizeThreeCollinear() {
		IPoint[] points = new IPoint[] {
				new TwoDPoint(1.0, 3.0),
				new TwoDPoint(1.0, 2.0),
				new TwoDPoint(1.0, 4.0)
		};
		IPoint[] copy = new IPoint[points.length];
		
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hull = new ConvexHullScan().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullGraham = new GrahamScan().compute(copy);
		NativeGrahamScan.leftShift(hullGraham);
		
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullNativeGraham = new NativeGrahamScan().compute(copy);
		NativeGrahamScan.leftShift(hullNativeGraham);
		
		// Compute Andrew scan with heuristic
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullRegular = new ConvexHullScan().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullBalanced= new BalancedTreeAndrew().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullHeap= new HeapAndrew().compute(copy);
		
		// Fast enough that it could compare favorably w/ AKL
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullBucket= new BucketAndrew().compute(copy);
		
		assertEquals(hull.length, hullRegular.length);
		assertEquals(hull.length, hullBucket.length);
		assertEquals(hull.length, hullBalanced.length);
		assertEquals(hull.length, hullHeap.length);
		assertEquals(hull.length, hullGraham.length);
		assertEquals(hull.length, hullNativeGraham.length);
		
		// point for point. 
		for (int i = 0; i < hull.length; i++) {
			assertEquals (hull[i], hullRegular[i]);
			assertEquals (hull[i], hullBucket[i]);
			assertEquals (hull[i], hullBalanced[i]);
			assertEquals (hull[i], hullHeap[i]);
			assertEquals (hull[i], hullGraham[i]);
			assertEquals (hull[i], hullNativeGraham[i]);			
		}
		
		// test bucket andrew on less than 3 points.
		copy = new IPoint[] { points[0], points[1] };
		hullBucket= new BucketAndrew().compute(copy);
		assertEquals (points[0], hullBucket[0]);
		assertEquals (points[1], hullBucket[1]);
	
	}
	
	public void testMultiplePointsWithSomeCollinearOnHull() {
		IPoint[] points = new IPoint[] {
				new TwoDPoint(1.0, 2.0),
				new TwoDPoint(-2.0, 40),
				new TwoDPoint(1.0, 3.0),
				new TwoDPoint(1.0, 14.0),
				new TwoDPoint(-2.0, -10),
				new TwoDPoint(1.0, 4.0),
				
		};
		IPoint[] copy = new IPoint[points.length];
		
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullNativeGraham = new NativeGrahamScan().compute(copy);
		NativeGrahamScan.leftShift(hullNativeGraham);
		
		System.out.println("-------------------");
		for (int j = 0; j < hullNativeGraham.length; j++) {
			System.out.println(hullNativeGraham[j]);
		}
	}
	
	public void testSizeFourCollinear() {
		IPoint[] points = new IPoint[] {
				new TwoDPoint(1.0, 3.0),
				new TwoDPoint(1.0, 2.0),
				new TwoDPoint(1.0, 4.0),
				new TwoDPoint(1.0, 14.0),
		};
		IPoint[] copy = new IPoint[points.length];
		
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hull = new ConvexHullScan().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullGraham = new GrahamScan().compute(copy);
		NativeGrahamScan.leftShift(hullGraham);
		
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullNativeGraham = new NativeGrahamScan().compute(copy);
		NativeGrahamScan.leftShift(hullNativeGraham);
		
		// Compute Andrew scan with heuristic
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullRegular = new ConvexHullScan().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullBalanced= new BalancedTreeAndrew().compute(copy);

		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullHeap= new HeapAndrew().compute(copy);
		
		// Fast enough that it could compare favorably w/ AKL
		for (int i = 0; i < points.length; i++) { copy[i] = points[i]; }
		IPoint[] hullBucket= new BucketAndrew().compute(copy);
		
		assertEquals(hull.length, hullRegular.length);
		assertEquals(hull.length, hullBucket.length);
		assertEquals(hull.length, hullBalanced.length);
		assertEquals(hull.length, hullHeap.length);
		assertEquals(hull.length, hullGraham.length);
		assertEquals(hull.length, hullNativeGraham.length);
		

		for (int j = 0; j < hull.length; j++) {
			System.out.println(hull[j]);
		}
		System.out.println("-------------------");
		for (int j = 0; j < hullNativeGraham.length; j++) {
			System.out.println(hullNativeGraham[j]);
		}
		
		// point for point. 
		for (int i = 0; i < hull.length; i++) {
			assertEquals (hull[i], hullRegular[i]);
			assertEquals (hull[i], hullBucket[i]);
			assertEquals (hull[i], hullBalanced[i]);
			assertEquals (hull[i], hullHeap[i]);
			assertEquals (hull[i], hullGraham[i]);
			assertEquals (hull[i], hullNativeGraham[i]);
		}
		
		// test bucket andrew on less than 3 points.
		copy = new IPoint[] { points[0], points[1] };
		hullBucket= new BucketAndrew().compute(copy);
		assertEquals (points[0], hullBucket[0]);
		assertEquals (points[1], hullBucket[1]);
	
	}
	
	@Test
	public void testSizeOfRandomHull() {
		Generator<IPoint> g = new UniformGenerator();

		int n = 50;
		for (int t = 0; t < 5; t++) {
			IPoint[] max = g.generate(n);

			IPoint[] hull = new ConvexHullScan().compute(max);

			// Compute Andrew scan with heuristic
			IPoint[] points = AklToussaint.reduce(max);
			IPoint[] hullRegular = new ConvexHullScan().compute(points);

			points = AklToussaint.reduce(max);
			IPoint[] hullBalanced= new BalancedTreeAndrew().compute(points);

			points = AklToussaint.reduce(max);
			IPoint[] hullGraham = new GrahamScan().compute(points);
			NativeGrahamScan.leftShift(hullGraham);
			
			points = AklToussaint.reduce(max);
			IPoint[] hullNativeGraham = new NativeGrahamScan().compute(points);
			NativeGrahamScan.leftShift(hullNativeGraham);
			
			points = AklToussaint.reduce(max);
			IPoint[] hullHeap= new HeapAndrew().compute(points);
			
			// Fast enough that it could compare favorably w/ AKL
			points = AklToussaint.reduce(max);
			IPoint[] hullBucket= new BucketAndrew().compute(points);

			assertEquals(hull.length, hullRegular.length);
			assertEquals(hull.length, hullBucket.length);
			assertEquals(hull.length, hullBalanced.length);
			assertEquals(hull.length, hullHeap.length);
			assertEquals(hull.length, hullGraham.length);
			assertEquals(hull.length, hullNativeGraham.length);

			
			// point for point. 
			for (int i = 0; i < hull.length; i++) {
				assertEquals (hull[i], hullRegular[i]);
				assertEquals (hull[i], hullBucket[i]);
				assertEquals (hull[i], hullBalanced[i]);
				assertEquals (hull[i], hullHeap[i]);
				assertEquals (hull[i], hullGraham[i]);        // owing to random inputs, this has failed in past. Checking...
				assertEquals (hull[i], hullNativeGraham[i]);
			}
		}
	}
	
	@Test
	public void testGrahamWithThree() {
		Generator<IPoint> g = new UniformGenerator();

		int n = 3;
		for (int t = 0; t < 100; t++) {
			IPoint[] max = g.generate(n);
			IPoint[] copy = new IPoint[max.length];
			
			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hull = new ConvexHullScan().compute(copy);

			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hullGraham = new GrahamScan().compute(copy);
			
			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hullNativeGraham = new NativeGrahamScan().compute(copy);
			
			assertEquals(hull.length, hullGraham.length);
			assertEquals(hull.length, hullNativeGraham.length);

			// find point in common and then iteratively check in sequence
			int gi = 0;
			while (gi < hull.length) {
				if (hullGraham[gi].equals(hull[0])) {
					break;
				}
				
				gi++;
			}
			
			assert (gi < hull.length);
			
			for (int i = 0; i < hull.length; i++) {
				assertEquals (hull[i], hullGraham[gi]);
				gi = (gi + 1) % hullGraham.length; 
			}
		}
	}
	
	@Test
	public void testGrahamWithFour() {
		Generator<IPoint> g = new UniformGenerator();

		int n = 4;
		for (int t = 0; t < 1000; t++) {
			IPoint[] max = g.generate(n);
			IPoint[] copy = new IPoint[max.length];
			
			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hull = new ConvexHullScan().compute(copy);

			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hullGraham = new GrahamScan().compute(copy);
			
			assertEquals(hull.length, hullGraham.length);

			// find point in common and then iteratively check in sequence
			int gi = 0;
			while (gi < hull.length) {
				if (hullGraham[gi].equals(hull[0])) {
					break;
				}
				
				gi++;
			}
			
			assert (gi < hull.length);
			
			for (int i = 0; i < hull.length; i++) {
				assertEquals (hull[i], hullGraham[gi]);
				gi = (gi + 1) % hullGraham.length; 
			}
		}
	}
	
	@Test
	public void testRandomGrahamScan() {
		Generator<IPoint> g = new UniformGenerator();

		int n = 175;
		for (int t = 0; t < 25; t++) {
			IPoint[] max = g.generate(n);
			IPoint[] copy = new IPoint[max.length];
			
			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hull = new ConvexHullScan().compute(copy);

			for (int i = 0; i < max.length; i++) { copy[i] = max[i]; }
			IPoint[] hullGraham = new GrahamScan().compute(copy);
			
			assertEquals(hull.length, hullGraham.length);

			// find point in common and then iteratively check in sequence
			int gi = 0;
			while (gi < hull.length) {
				if (hullGraham[gi].equals(hull[0])) {
					break;
				}
				
				gi++;
			}
			
			assert (gi < hull.length);
			
			for (int i = 0; i < hull.length; i++) {
				assertEquals (hull[i], hullGraham[gi]);
				gi = (gi + 1) % hullGraham.length; 
			}
		}
	}
	
	@Test
	public void testGrahamScan() {
		
		// dangerous for graham scan since this requires backtracking
		IPoint[] max = new IPoint[] {
					new TwoDPoint(0,0),
					new TwoDPoint(3,0),
					new TwoDPoint(6,6),
					new TwoDPoint(2,5),
					new TwoDPoint(-1,4),
					new TwoDPoint(-3,3),
					new TwoDPoint(-9,5)
		};
		IPoint[] copy = new IPoint[max.length];
		
		for (int i = 0; i < copy.length; i++) { copy[i] = max[i]; }
		IPoint[] hull = new ConvexHullScan().compute(max);

		for (int i = 0; i < copy.length; i++) { copy[i] = max[i]; }
		IPoint[] hullGraham = new GrahamScan().compute(max);
		
		assertEquals(hull.length, hullGraham.length);

		// find point in common and then iteratively check in sequence
		int gi = 0;
		while (gi < hull.length) {
			if (hullGraham[gi].equals(hull[0])) {
				break;
			}
			
			gi++;
		}
		
		assert (gi < hull.length);
		
		for (int i = 0; i < hull.length; i++) {
			assertEquals (hull[i], hullGraham[gi]);
			gi = (gi + 1) % hullGraham.length; 
		}
	}

}
