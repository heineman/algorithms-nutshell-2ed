package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;

/**
 * Generators of sample lines to produce maximum number of intersections
 * <p>
 * Sliding ladder from (0,x) to (x,0) downwards.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlidingLadderGenerator extends Generator<ILineSegment> {
	
	/** parameters for the constructor. */
	private String[] params = { "Max Height" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The maximum size. */
	double max;
	
	/**
	 * Maximum height of the ladder.
	 * 
	 * @param height  maximum height of the ladder 
	 */
	public SlidingLadderGenerator (double height) {
		max = height;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new SlidingLadderGenerator(Double.valueOf(args[0]));
	}
		
	/**
	 * Generate a random set of points within a [1,1] box, extending potentially
	 * out to a larger range based upon the length of each line segment.
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[size];
		
		// Start with ladder at (0, n) and slide one right and one down.
		int idx=0;
		double delta = max/(size-1);
		double val = max;
		for (int i = lines.length-1; i >=0; i--) {
			lines[idx++] = new TwoDLineSegment(
					0,
					val,
					max-val,
					0);
			
			val -= delta;
		}
		
		return lines;
	}
}