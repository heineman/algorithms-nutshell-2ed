package algs.model.data.circles;

import algs.model.ICircle;
import algs.model.data.Generator;
import algs.model.twod.TwoDCircle;
import algs.model.twod.TwoDPoint;

/**
 * Generator of sample circles.
 * <p>
 * It only makes sense for the radius to be less than 1 since the origin
 * of the circles are all created uniformly within the unit square.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UniformGenerator extends Generator<ICircle> {
	
	/** parameters for the constructor. */
	private String[] params = { "radius" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The size of each radius. */
	public final double radius;
	
	/**
	 * Radius of the circle to be generated.
	 * 
	 * @param radius    radius of circle to be generated.
	 */
	public UniformGenerator (double radius) {
		this.radius = radius;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ICircle> construct(String[] args) {
		return new UniformGenerator(Double.valueOf(args[0]));
	}
	
	/** 
	 * Generate a set of uniform circles points in the range:
	 * 
	 *   x = [0.0, 1.0), y = [0.0, 1.0) 
	 *   
	 * with given radius.
	 * @param size    radius to use when generating circles
	 * @return        array of generated circles
	 */ 
	public TwoDCircle[] generate (int size) {
		TwoDCircle[] circles = new TwoDCircle[size];
		
		// circles.
		for (int i = 0; i < size; i++) {
			double x = Math.random();
			double y = Math.random();
			
			circles[i] = new TwoDCircle(new TwoDPoint(x, y), radius);
		}
		
		return circles;
	}

}
