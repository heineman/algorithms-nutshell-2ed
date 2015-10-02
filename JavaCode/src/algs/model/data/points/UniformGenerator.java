package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample data points within the [0,1] unit square.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UniformGenerator extends Generator<IPoint> {
	
	/** parameters for the constructor. */
	private String[] params = new String [0];
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new UniformGenerator();
	}
		
	/** 
	 * Generate a set of uniform random points in the range:
	 * 
	 *   x = [0.0, 1.0), y = [0.0, 1.0) 
	 *   
	 * @param size   number of points to generate. 
	 * @return       array containing the points.
	 */ 
	public IPoint[] generate (int size) {
		IPoint[] points = new TwoDPoint[size];
		
		// points.
		for (int i = 0; i < size; i++) {
			double x = Math.random();
			double y = Math.random();
			
			points[i] = new TwoDPoint(x, y);
		}
		
		return points;
	}
}
