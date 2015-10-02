package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/**
 * Generators of sample lines using doubles coordinated within the [max, max] 
 * box whose segment distance is length.
 * 
 * Make sure we don't generate single points as line segments.
 * 
 * To ensure points don't fall outside of the [max,max] range, we 
 * generate the internal random points using the smaller box [max-length].
 * 
 * TODO: Alert users when max == length, because then the generated line segments will all likely
 * have (0,0) as one of its end points. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DoubleGenerator extends Generator<ILineSegment> {

	/** parameters for the constructor. */
	private String[] params = { "max", "length" };
	
	@Override
	public String[] parameters() {
		return params;
	}

	/** The size of each line segment. */
	public final double length;
	
	/** Size of the domain for both x- and y- coordinates [0, max]. */
	public final double max;
	
	/**
	 * Prepare generator with boundaries of the square together with the desired
	 * length of line segments to create.
	 * 
	 * @param max     maximum square boundaries within which values are generated
	 * @param length  length of the line.
	 */
	public DoubleGenerator (double max, double length) {
		this.length = length;
		this.max = max;
		
		if (length > max) {
			throw new IllegalArgumentException ("Unable to consistently generate line segments of length " + length + " within square box of dimension " + max);
		}
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new DoubleGenerator(Double.valueOf(args[0]), Double.valueOf(args[1]));
	}
		
	/**
	 * Generate a random set of segments within a [max,max] box, 
	 * extending potentially out to a larger range based upon the length 
	 * of each line segment.
	 * <p>
	 * 
	 * @param size   The number of ILineSegments to create   
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[size];
		
		// now generate points at each location (cos x, sin x)
		for (int i = 0; i < size; i++) {
			double x = Math.random()*(max-length);
			double y = Math.random()*(max-length);
			
			double dx = length*Math.sin(2*Math.PI*Math.random()-Math.PI);   
			double dy = Math.sqrt(length*length-dx*dx);
			
			double x2 = x+dx;
			if (x2 < 0) { x2 = 0; } // never negative
			if (x2 > max) { x2 = max; } // keep within bound
		    int sgn;
		    if (Math.random()<0.5) { sgn = -1; } else { sgn = 1; }
		    
		    double y2 = y+sgn*dy;   
			if (y2 < 0) { y2 = 0; } // never negative
			if (y2 > max) { y2 = max; } // keep within bound
			
			TwoDLineSegment line = new TwoDLineSegment(
					new TwoDPoint(x, y),
					new TwoDPoint(x2, y2));
			
			// in extremely unlikely circumstance that we have generated a point instead of a line
			// segment, arbitrarily offset (x2,y2) by .001
			if (line.end.equals(line.start)) {
				line = new TwoDLineSegment(new TwoDPoint(x, y),
						new TwoDPoint(x+.001, y+.001));
			}
			
			lines[i] = line;
		}
		
		return lines;
	}
	
}
