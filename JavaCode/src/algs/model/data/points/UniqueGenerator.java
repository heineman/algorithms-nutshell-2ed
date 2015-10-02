package algs.model.data.points;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generate sample points whose x and y values are all unique integers &ge; 0.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class UniqueGenerator extends Generator<IPoint> {
	
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
		return new UniqueGenerator();
	}
	
	
	/** 
	 * Generate a set of |size| points in the plane.
	 * <p>
	 * x-dimension is 1..size/2<br>
	 * y-dimension is 1..size/2<br>
	 * <p>
	 * All coordinates (x and y) are unique, since they are drawn
	 * randomly from the given set.
	 * 
	 * @param  size   number of points to generate.
	 */ 
	public IPoint[] generate (int size) {
		IPoint[] points = new TwoDPoint[size];
		int ct = 0;
		
		// populate set.
		int[] nums = new int[2*size];
		for (int i =0; i < 2*size; i++) { nums[i] = i; }
		
		// randomly select one, and drain until all done.
		int idx = 2*size;
		while (idx > 0) {
			int r = (int)(Math.random()*idx);
			int rv = nums[r];
			
			// swap
			nums[r] = nums[idx-1];
			nums[idx-1] = -1;
			idx--;
			
			int c = (int)(Math.random()*idx);
			int cv = nums[c];
			
			// swap
			nums[c] = nums[idx-1];
			nums[idx-1] = -1;
			idx--;
			
			// add to our set.
			points[ct++] = new TwoDPoint (rv,cv);
		}
		
		return points;
	}
}