package algs.model.problems.rangeQuery;

import java.util.ArrayList;

import algs.model.IHypercube;
import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.kdtree.IVisitKDNode;
import algs.model.twod.TwoDPoint;

/**
 * Brute Force implementation of Range Query.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BruteForceRangeQuery {
	final ArrayList<double[]> points;
	final IMultiPoint results[];
	
	/**
	 * Search points pulled from IPoint array.
	 * @param points    initial points forming input to problem. 
	 */
	public BruteForceRangeQuery(IPoint[] points) {
		this.points = new ArrayList<double[]>();
		this.results = new IMultiPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			this.points.add(new double[]{points[i].getX(), points[i].getY()});
			this.results[i] = new TwoDPoint(points[i]);
		}
	}
	
	/**
	 * Search points pulled from IMultiPoint array.
 	 * @param points    initial points forming input to problem. 
	 */
	public BruteForceRangeQuery(IMultiPoint[] points) {
		this.points = new ArrayList<double[]>();
		for (int i = 0; i < points.length; i++) {
			this.points.add(points[i].raw());
		}
		this.results = points;
	}
	
	/**
	 * Perform brute force search of all points within given region.
	 * 
	 * @param hc    target query range
	 * @return      results of the search
	 */
	public ArrayList<IMultiPoint> search (IHypercube hc) {
		ArrayList<IMultiPoint> retval = new ArrayList<IMultiPoint>();
		
		double[] low_raw = new double[hc.dimensionality()];
		double[] high_raw = new double[hc.dimensionality()];
		
		// extract raw information.
		for (int i = 0; i < low_raw.length; i++) {
			low_raw[i] = hc.getLeft(i+1);
			high_raw[i] = hc.getRight(i+1);
		}
		
		for (int i = 0; i < points.size(); i++) {
			double []rawpt = points.get(i);
			
			boolean inResult = true;
			for (int j = 0; j < low_raw.length; j++) {
				if (rawpt[j] < low_raw[j] || rawpt[j] > high_raw[j]) {
					inResult = false;
					break;
				}
			}
			if (inResult) {
				retval.add(results[i]);
			}
		}
	
		return retval;
	}	
	
	/**
	 * Perform brute force search of all points within given region.
	 * 
	 * @param hc       target query range
	 * @param visitor  visitor which is invoked for each returned value
	 * @return         results of the search
	 */
	public ArrayList<IMultiPoint> search (IHypercube hc, IVisitKDNode visitor) {
		ArrayList<IMultiPoint> retval = new ArrayList<IMultiPoint>();
		
		double[] low_raw = new double[hc.dimensionality()];
		double[] high_raw = new double[hc.dimensionality()];
		
		// extract raw information.
		for (int i = 0; i < low_raw.length; i++) {
			low_raw[i] = hc.getLeft(i+1);
			high_raw[i] = hc.getRight(i+1);
		}
		
		for (int i = 0; i < points.size(); i++) {
			double []rawpt = points.get(i);
			
			boolean inResult = true;
			for (int j = 0; j < low_raw.length; j++) {
				if (rawpt[j] < low_raw[j] || rawpt[j] > high_raw[j]) {
					inResult = false;
					break;
				}
			}
			if (inResult) {
				// HACK since there is no dimensional node, but we are only
				// using this as a counter, so OK.
				visitor.visit(null);
			}
		}
	
		return retval;
	}
}