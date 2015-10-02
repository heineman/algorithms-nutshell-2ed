package algs.model;

import java.util.Comparator;


/**
 * A point has an x- and y-coordinate over the cartesian plane.
 * <p>
 * Classes that implement this interface must provide accurate {@link Object#equals(Object)}
 * and {@link Object#hashCode()} methods. To be useful during debugging, one should
 * also have a meaningful {@link Object#toString()} method.
 * <p>
 * Points are (by default) sortable using xy_sorter
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IPoint extends Comparable<IPoint> {
	
	/**
	 * Globally useful sorter, that first sorts by x, and then by y coordinate.
	 * 
	 * Useful for determining full ordering of IPoint objects over a Cartesian Plane.
	 */
	public static Comparator<IPoint> xy_sorter =
		new Comparator<IPoint>() {

			/**
			 * Compares two 2-dimensional points in the Cartesian plane.
			 * 
			 * Handles null values as follows. 
			 * <ol>
			 * <ul>If one is null && two is null, then 0 is returned (an exact null match).
			 * <ul>Else if one is null, then return -1
			 * <ul>Else if two is null, then return +1
			 * </ol> 
			 * 
			 * @param one
			 * @param two
			 */
			public int compare(IPoint one, IPoint two) {
				if (one == null) {
					if (two == null) { 
						return 0; 
					}
					return -1;
				} else if (two == null) { 
					return +1; 
				}
				
				double fp = FloatingPoint.value(one.getX() - two.getX());
				if (fp < 0) { return -1; }
				if (fp > 0) { return +1; }
				
				fp = FloatingPoint.value(one.getY() - two.getY());
				if (fp < 0) { return -1; }
				if (fp > 0) { return +1; }
				
				// same exact (x,y) points
				return 0;
			}

	};	
	
	/** 
	 * Return the x-coordinate value for the given point.
	 * @return x-coordinate for point. 
	 */
	double getX();
	
	/**
	 * Return the y-coordinate value for the given point. 
 	 * @return y-coordinate for point. 
	 */
	double getY();
}
