package algs.model.kdtree;

import java.util.Comparator;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;

/**
 * Able to compare IMultiPoint objects using a fixed dimensional index to select
 * the value against which to compare.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DimensionalComparator implements Comparator<IMultiPoint> {

	/** The dimension against which the points are being compared. */
	public final int d;
	
	/** 
	 * Construct with the given dimensional index (d &ge; 1).
	 * 
	 * @param d    specific dimension (d &ge; 1).
	 */
	public DimensionalComparator (int d) {
		if (d < 1) { 
			throw new IllegalArgumentException ("Dimensional Comparator must have d>=1");
		}
		
		this.d = d;
	}
	
	/** 
	 * Compare the two points against a given dimension.
	 * <p>
	 * Note that for performance reasons, there is no check to ensure that the
	 * two points have the same dimensionality. Indeed, if this method is invoked
	 * with an IMultiPoint whose dimensionality is less than d, then the result
	 * is undetermined (it may throw a runtime exception it may not). 
	 *  
	 * @param o1    first IMultiPoint to be compared against
	 * @param o2    second IMultiPoint to be compared against. 
	 */
	public int compare(IMultiPoint o1, IMultiPoint o2) {
		return FloatingPoint.compare (o1.getCoordinate(d), o2.getCoordinate(d));
	}
}
