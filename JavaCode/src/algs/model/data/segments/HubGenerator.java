package algs.model.data.segments;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;

/**
 * Generators of sample lines that form a hub-and-spoke.
 * <p>
 * Should only be a single intersection, though when converted into integers
 * the rounding error may increase this number.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class HubGenerator extends Generator<ILineSegment> {
	
	/** parameters for the constructor. */
	private String[] params = { "Length", "x-origin", "y-origin" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** The maximum size. */
	final double max;
	
	/** X origin of hub. */
	final double x;
	
	/** Y origin of hub. */
	final double y;
	
	/**
	 * Hub needs its length, x- and y-coordinate
	 * 
	 * @param length of the spokes radiating out from (x,y)
	 * @param x   x-coordinate of the origin of hub
	 * @param y   y-coordinate of the origin of hub 
	 */
	public HubGenerator (double length, double x, double y) {
		max = length;
		this.x = x;
		this.y = y;
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<ILineSegment> construct(String[] args) {
		return new HubGenerator(Double.valueOf(args[0]),
				Double.valueOf(args[1]),Double.valueOf(args[2]));
	}
		
	/**
	 * Given an (x,y) coordinate, create a number of lines all intersecting
	 * at that location but not each other.
	 */
	public ILineSegment[] generate (int size) {
		ILineSegment[] lines = new ILineSegment[size];
		
		// Degrees between lines:
		double deg = 2*Math.PI/size;
		
		// Generate radially around from (x,y) to (x+max*cos[a], y+max*sin[a])
		int idx=0;
		double angle = 0;
		for (int i = 0; i < lines.length; i++) {
			lines[idx++] = new TwoDLineSegment (
					x, 
					y,
					x+max*Math.cos(Math.PI+angle),
					y+max*Math.sin(Math.PI+angle));
			
			angle += deg;
		}
		
		return lines;
	}
	
	
}