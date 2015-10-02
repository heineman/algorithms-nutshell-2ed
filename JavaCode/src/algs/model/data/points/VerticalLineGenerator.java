package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generate a set of points that all exist on a vertical line (99,y) where
 * y is an integer greater than 0.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class VerticalLineGenerator extends Generator<IPoint> {
	
	/** parameters for the constructor. */
	private String[] params = new String [] {"x-value"};
	
	/** x Coordinate of generated points. */
	public final double xValue;
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/**
	 * Generator for points along a vertical line.
	 *  
	 * @param xValue   the x-coordinate of the vertical line
	 */
	public VerticalLineGenerator(double xValue) {
		this.xValue = xValue;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 * @param args     parameters to the constructor
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new VerticalLineGenerator(Double.valueOf(args[0]));
	}
		
	/**
	 * Generate a set of |size| points all along a vertical line
	 * <p>
	 * All y-coordinates are integer multiples of 10.
	 * 
	 * @param  size    Number of points to generate.
	 */
	public TwoDPoint[] generate (int size) {
		TwoDPoint[] points = new TwoDPoint[size];
		
		// now generate points at each location along vertical line.
		for (int i = 0; i < size; i++) {
			points[i] = new TwoDPoint (xValue, 10*i);
		}
		
		return points;
	}
}
