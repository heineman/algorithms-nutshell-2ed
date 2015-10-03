package algs.chapter9.oldtable1;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.andrew.ConvexHullScan;

/**
 * Given random point selections, what is the average number of points
 * in the convex hull?
 * 
 * @author George Heineman
 *
 * Here are two trials
 *
 * 10         3      7         1      6
 * 100        52     11        13     17
 * 1000       520    19        528    18
 * 10000      2722   22        4971   28
 * 100000     51478  36        41082  28
 * 1000000    398822 39        704352 45
 * 
 */
public class Main { 

	public static void main (String []args) {
		int idx = 10;
		
		int NUM = 7;
		
		Generator<IPoint> g = new UniformGenerator();
		System.out.println ("Size of random convex hull.");
		
		System.out.println ("size,numEliminated,finalSize");
		for (int i = 1; i < NUM; i++) {
			IPoint[] points = g.generate(idx);
			
			int num = points.length;
			points = AklToussaint.reduce(points);
			num = num - points.length;
			
			IPoint[] hull = new ConvexHullScan().compute(points);
			
			System.out.println (idx + "," + num + "," + hull.length);
			
			idx *= 10;
		}
	}
}
