package algs.chapter9.figure5;

import algs.model.FloatingPoint;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generate non-uniform points in a really thin slice formation, though
 * not purely a vertical line in the unit square.
 * 
 * includes points (0,0) and (1,0) to avoid one to form clean buckets. 
 * 
 * @author George Heineman
 */
public class SliceGenerator extends Generator<IPoint> {

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
		return new SliceGenerator();
	}
	
	
	/**
	 * Generate 1 point at (0,0), then two points at (d,0), then three points
	 * at (2*d,0) and so on, until size points are produced.
	 */
	@Override
	public IPoint[] generate(int size) {
		IPoint[] points = new TwoDPoint[size];
		
		// spread out.
		points[0] = new TwoDPoint(0,0);
		points[1] = new TwoDPoint(1.0,0);
		
		// cluster remaining size-2 points within narrow sliver 
		// that should all fit into a single bucket.
		double x = .502;
		
		double y = 0.0;
		int i=1;         // number to place in this column
		int idx = 2;     // point to create
		while (idx < size) {
			for (int j = 0; j < i; j++) {
				points[idx++] = new TwoDPoint(x,y);
				if (idx >= size) { break; }  // can happen at any time...
				
				y += 1;  
			}
			
			// move on to next column.
			x -= 5*FloatingPoint.epsilon;
			i++; 
			y = 0.0;
		}
		
		return points;
	}
}