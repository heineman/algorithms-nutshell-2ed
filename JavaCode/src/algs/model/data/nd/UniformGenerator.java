package algs.model.data.nd;

import algs.model.IMultiPoint;
import algs.model.data.Generator;
import algs.model.nd.Hyperpoint;

/**
 * Generators of sample data points within the [0,scale) unit square.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UniformGenerator extends Generator<IMultiPoint> {
	
	/** Points are assigned this number of dimensions. */
	public final int dimensions;
	
	/** Points are drawn with indices in range [0,scale]. */ 
	public final double scale;
	
	/** parameters for the constructor. */
	private String[] params = {"Dimensions", "Max" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IMultiPoint> construct(String[] args) {
		return new UniformGenerator(Integer.valueOf(args[0]), Double.valueOf(args[1]));
	}

	/**
	 * Construct generator given desired number of dimensions and scale.
	 * 
	 * @param dimensions   dimensional range
	 * @param scale        scale of the points.
	 */
	public UniformGenerator (int dimensions, double scale) {
		this.dimensions = dimensions;
		this.scale = scale;
	}
	
	/** 
	 * Generate a set of uniform random points in the range:
	 * 
	 *   x = [0.0, scale), y = [0.0, scale) 
	 *   
	 * @param size   number of points to generate. 
	 * @return       array containing these points.
	 */ 
	public IMultiPoint[] generate (int size) {
		IMultiPoint[] points = new Hyperpoint[size];
		
		// storage
		double vals[] = new double[dimensions];
		
		// points.
		for (int i = 0; i < size; i++) {
			for (int d = 0; d < dimensions; d++) {
				vals[d] = scale*Math.random();
			}
			
			points[i] = new Hyperpoint(vals);
		}
		
		return points;
	}
}
