package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;

/**
 * Generators of grid lines for O(sqrt(n)) number of intersections given
 * n lines.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GridGenerator extends Generator<ILineSegment> {
	
	/** parameters for the constructor. */
	private String[] params = { "Max Height", "Skew" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The maximum size. */
	final double max;
	
	/** Skew of this grid. */
	final double skew;
	
	/**
	 * Maximum height of the ladder.
	 * 
	 * @param height   maximum height of the ladder
	 * @param skew     how far to adjust with each pass down.
	 */
	public GridGenerator (double height, double skew) {
		max = height;
		this.skew = skew; 
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new GridGenerator(Double.valueOf(args[0]), Double.valueOf(args[1]));
	}
		
	/**
	 * Generate grid of horizontal and vertical lines based on max.
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[2*size];
		
		double delta = (max-skew)/size;
		
		// horizontal and vertical lines
		double t = 0;
		int idx = 0;
		while (idx < 2*size) {
			// horizontal
			lines[idx++] = new TwoDLineSegment(0,t,max,t+skew);
			
			//vertical
			lines[idx++] = new TwoDLineSegment(t,0,t+skew,max);
			
			t += delta;
		}
		
		return lines;
	}
}