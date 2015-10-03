package algs.example.convexhull.rings;

import java.util.LinkedList;

import algs.model.IPoint;
import algs.model.array.Selection;
import algs.model.data.points.UniformGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.andrew.ConvexHullScan;

/**
 * Given a set of points, compute the series of "onion" convex hulls
 * that make up the full set of points.
 * <p>
 * Returns a LinkedList of TwoDPoint[] arrays that define the internal
 * convex hulls.
 * <p>
 * It would be interesting to compute the average (??) or minimum or
 * maximum distance between each ring and its nearest outer neighbor.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Rings {
	
	public static void main(String[] args) {
		int size = 50000;
		
		// generate random hull from uniform points in (0,1) and show the rings.
		UniformGenerator ug = new UniformGenerator();
		System.out.println("Generating " + size + " points...");
		IPoint[] pts = ug.generate(size);
		
		System.out.println("computing....");
		LinkedList<IPoint[]> structure = compute(pts);
		
		System.out.println("reporting...");
		info(structure);
		
		// show what happens with AklToussaint
		System.out.println("Applying AklToussaint...");
		pts = AklToussaint.reduce(pts);
		structure = compute(pts);
		
		System.out.println("reporting...");
		info(structure);
	}
	
	/**
	 * Generate tabular report on the rings structure computed by the compute
	 * method.
	 * 
	 * @param rings     linked list of IPoint arrays which represent convex rings
	 */
	public static void info (LinkedList<IPoint[]> rings) {
		System.out.println ("Ring information:");
		System.out.println (rings.size() + " rings");
		StringBuilder sb = new StringBuilder();
		double avgLength = 0;
		for (IPoint[] ip : rings) {
			avgLength += ip.length;
			sb.append (ip.length + ",");
		}
		avgLength = avgLength / rings.size();
		
		sb.deleteCharAt(sb.length()-1);
		System.out.println ("Sequence:");
		System.out.println (sb.toString());
		System.out.println ("Average Length:" + avgLength);
	}

	/**
	 * Return the full Topographic depiction of the set.
	 *
	 * @param points  initial set of points.
	 * @return list of arrays, each of which defines a convex ring structure
	 */
	public static LinkedList<IPoint[]> compute (IPoint[] points) {
		LinkedList<IPoint[]> list = new LinkedList<IPoint[]>();
		
		// sort the points lexicographically and construct linked list of these points.
		Selection.qsort(points, 0, points.length-1, IPoint.xy_sorter);
		LinkedList<IPoint>  pointsList = new LinkedList<IPoint>();
		for (IPoint ip : points) {
			pointsList.add(ip);
		}
		
		// now begin processing.
		IConvexHull alg = new ConvexHullScan();
		while (points.length > 0) {
			IPoint newPoints[] = AklToussaint.reduce(points);
			
			// compute hull and add on.
			IPoint[] hull = alg.compute(newPoints);
			list.add(hull);

			// remove hull points from pointsList.
			for (IPoint hp : hull) {
				pointsList.remove(hp);
			}
			
			// reconstruct points array without old points
			points = pointsList.toArray(new IPoint[]{});
		}
		
		return list;
	}
}
