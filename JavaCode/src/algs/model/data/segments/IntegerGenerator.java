package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample lines using integer coordinated within the [max, max] 
 * box whose segment distance is length.
 * <p>
 * Make sure we don't generate single points as line segments. If the max value is too
 * small, it may be possible that a number of duplicate line segments will be generated, but
 * at least they won't be single points.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class IntegerGenerator extends Generator<ILineSegment> {
	
	/** parameters for the constructor. */
	private String[] params = { "max", "length" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The size of each line segment. */
	double length;
	
	/** Size of the square within which segments are created at integer boundaries. */
	double max;
	
	/**
	 * Prepare generator with boundaries of the square together with the desired
	 * length of line segments to create.
	 * 
	 * @param max     maximum square boundaries within which integers are generated
	 * @param length  length of the line.
	 */
	public IntegerGenerator (int max, int length) {
		this.length = length;
		this.max = max;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new IntegerGenerator(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
	}
	
	/**
	 * Generate a random set of segments within a [max,max] box, 
	 * extending potentially out to a larger range based upon the length 
	 * of each line segment.
	 * <p>
	 * Each coordinate value of the segment is an integer.
	 * 
	 * @param size   The number of ILineSegments to create   
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[size];
		
		// now generate points at each location (cos x, sin x)
		for (int i = 0; i < size; i++) {
			double x = Math.random(); x *= max;
			double y = Math.random(); y *= max;
			double dx = length * Math.cos(Math.random());
			double dy = length * Math.sin(Math.random());
			
			int sgn1, sgn2;
			
			if (Math.random() <= .5) { sgn1 = +1; } else { sgn1 = -1; } 
			if (Math.random() <= .5) { sgn2 = +1; } else { sgn2 = -1; }
			
			TwoDLineSegment line = new TwoDLineSegment(
					new TwoDPoint((int)x, (int)y),
					new TwoDPoint((int)(x+sgn1*dx), (int)(y+sgn2*dy)));
			
			// in extremely unlikely circumstance that we have generated a point instead of a line
			// segment, arbitrarily offset (x1,y1) by -1 and (x2,y2) by +1. 
			if (line.end.equals(line.start)) {
				line = new TwoDLineSegment(
						new TwoDPoint((int)x-1, (int)y-1),
						new TwoDPoint((int)(x+sgn1*dx)+1, (int)(y+sgn2*dy)+1));
			}
			
			lines[i] = line;
		}
		
		return lines;
	}
	
}