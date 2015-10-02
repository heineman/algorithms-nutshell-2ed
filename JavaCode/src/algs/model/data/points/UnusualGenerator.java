package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Divide the size into n/4 and create uniform clusters of [0,1] way apart along
 * the +/- X-axis and Y-axis. This data set tests the way how errors caused by 
 * floating point arithmetic can alter the result of an algorithm
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UnusualGenerator extends Generator<IPoint> {
	
	/** parameters for the constructor. */
	private String[] params = { "max" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** Store the max value. */
	public final double maxValue;

	/** 
	 * Construct the generator given a maximum distance between four clusters.
	 * 
	 * @param maxValue   the distance between clusters.
	 */
	public UnusualGenerator(double maxValue) {
		this.maxValue = maxValue;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new UnusualGenerator(Double.valueOf(args[0]));
	}
	
	/**
	 * Generate a set of |size| points all clustered into four regions,
	 * each located at the corners of a rectangle of width and height of
	 * maxValue.
	 * <p>
	 * 
	 * Each cluster is going to be within a unit square [0,1] that is offset
	 * appropriately with maxValue to place these clusters at each corner.
	 * 
	 * @param size   number of points to generate.
	 */
	public IPoint[] generate (int size) {
		IPoint[] points = new TwoDPoint[size];
		
		Generator<IPoint> unif = new UniformGenerator();

		int idx = 0;
		// +Y axis
		for (IPoint p : unif.generate(size/4)) {
			points[idx++] = new TwoDPoint(p.getX(), p.getY() + maxValue); 
		}

		// +X axis
		for (IPoint p : unif.generate(size/4)) {
			points[idx++] = new TwoDPoint(p.getX() + maxValue, p.getY()); 
		}

		// -Y axis
		for (IPoint p : unif.generate(size/4)) {
			points[idx++] = new TwoDPoint(p.getX(), p.getY() - maxValue); 
		}

		// -X axis
		for (IPoint p : unif.generate(size/4)) {
			points[idx++] = new TwoDPoint(p.getX() - maxValue, p.getY()); 
		}
		
		// now any extra points are padded with (o,i)
		int ct = 1;
		while (idx < size) {
			points[idx++] = new TwoDPoint(0, ct++);
		}
		
		return points;
	}
	
}
