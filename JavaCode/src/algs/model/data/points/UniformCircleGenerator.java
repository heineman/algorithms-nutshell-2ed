package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample data points within the [0,1] unit circle.
 * 
 * @author George Heineman
 * @version 2.0, 9/1/15
 * @since 2.0
 */
public class UniformCircleGenerator extends Generator<IPoint> {
	
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
		return new UniformCircleGenerator();
	}
		
	/** 
	 * Generate a set of uniform random points in the unit circle:
	 * 
	 *   x = [0, 1.0], y = [0.0, 1.0] and exists within circle 
	 *   
	 * @param size   number of points to generate. 
	 * @return       array containing the points.
	 */ 
	public IPoint[] generate (int size) {
		IPoint[] points = new TwoDPoint[size];
		
		// points.
		for (int i = 0; i < size; i++) {
			double radius = 0.5*Math.random();
			double angle = 2*Math.PI*Math.random();
			
			double x = 0.5 + radius*Math.cos(angle);
			double y = 0.5 + radius*Math.sin(angle);
			
			points[i] = new TwoDPoint(x, y);
		}
		
		return points;
	}
}
