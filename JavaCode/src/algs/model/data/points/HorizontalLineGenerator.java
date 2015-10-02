package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generate a set of points that all exist on a horizontal line.
 * <p>
 * The y-coordinate of each generated point is determined by the 
 * generator. The x-cooordinate of each point is an integer multiple
 * of 10.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class HorizontalLineGenerator extends Generator<IPoint> {
	
	/** parameters for the constructor. */
	private String[] params = new String [] { "y-value" };
	
	/** location of horizontal line. */
	public final double yValue;
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	public HorizontalLineGenerator (double yValue) {
		this.yValue = yValue;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new HorizontalLineGenerator(Double.valueOf(args[0]));
	}
	
	/**
	 * Generate a set of |size| points all along a horizontal line (x,99).
	 * 
	 * Each point has an x-coordinate that is a multiple of 10.
	 * 
	 * @param size   number of points to generate
	 */
	public TwoDPoint[] generate (int size) {
		TwoDPoint[] points = new TwoDPoint[size];
		
		// now generate points at each location along horizontal line.
		for (int i = 0; i < size; i++) {
			points[i] = new TwoDPoint (10*i, yValue);
		}
		
		return points;
	}
	

}
