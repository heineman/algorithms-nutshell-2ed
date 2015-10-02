package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample data points along the edge of a circle whose origin
 * is (0,0) and has the given radius (defaults to 1).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CircleGenerator extends Generator<IPoint> {

	/** parameters for the constructor. */
	private String[] params = { "radius" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** Default to unit circle. */
	public final int radius;

	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new CircleGenerator(Integer.valueOf(args[0]));
	}
	
	/** 
	 * Construct default generator with radius of 1. 
	 */
	public CircleGenerator () {
		this (1);
	}
	
	/**
	 * Construct generator with given radius.
	 *  
	 * @param r  desired radius
	 */
	public CircleGenerator (int r) {
		this.radius = r;
	}
	
	/**
	 * Generate a set of |size| points along the edge of a circle, thus ensuring
	 * that all points belong to the convex hull.
	 * 
	 * @param size   number of points to be generated.
	 */
	public IPoint[] generate (int size) {
		IPoint[] points = new TwoDPoint[size];
		
		double delta = Math.PI*2;
		delta = delta / (size+1);   // don't want to go all the way around
		
	    // now generate points at each location (cos x, sin x)
		for (int i = 0; i < size; i++) {
			points[i] = new TwoDPoint (radius*Math.cos(i*delta), radius*Math.sin(i*delta));
		}
		
		return points;
	}
	
}
