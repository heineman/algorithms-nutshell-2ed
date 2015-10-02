package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample lines.
 * <p>
 * Randomly select a point in the [0,1] x [0,1] square, and make the length of the
 * line be 1/ratio in a random direction.
 * <p>
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UniformGenerator extends Generator<ILineSegment> {
	
	/** parameters for the constructor. */
	private String[] params = { "ratio" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The size of each line segment. */
	double length;
	
	/**
	 * Ratio is in the range 1 .. n. The size of the line segment is 1/ratio.
	 * @param ratio   inverse value that determines size of line segment (higher values mean smaller segments)
	 */
	public UniformGenerator (int ratio) {
		this.length = 1.0 / ratio;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new UniformGenerator(Integer.valueOf(args[0]));
	}
		
	/**
	 * Generate a random set of points within a [1,1] box, extending potentially
	 * out to a larger range based upon the length of each line segment.
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[size];
		
		
		// now generate points at each location (cos x, sin x)
		for (int i = 0; i < size; i++) {
			double x = Math.random();
			double y = Math.random();
			double dx = length * Math.cos(Math.random());
			double dy = length * Math.sin(Math.random());
			
			int sgn1, sgn2;
			
			if (Math.random() <= .5) { sgn1 = +1; } else { sgn1 = -1; } 
			if (Math.random() <= .5) { sgn2 = +1; } else { sgn2 = -1; }
			
			TwoDLineSegment line = new TwoDLineSegment(
					new TwoDPoint(x, y),
					new TwoDPoint(x+sgn1*dx, y+sgn2*dy));
			
			lines[i] = line;
		}
		
		return lines;
	}
	
	
}