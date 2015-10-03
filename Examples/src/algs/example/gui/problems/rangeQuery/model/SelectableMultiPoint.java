package algs.example.gui.problems.rangeQuery.model;

import java.awt.*;

import algs.example.gui.problems.rangeQuery.ISelectable;
import algs.model.IMultiPoint;
import algs.model.IPoint;

/**
 * A bridge class that extends the standard java.awt.Point but
 * also implements IMultiPoint.
 * 
 * In other words, it behaves as needed for the Java AWT, yet it 
 * also can be dropped into the algs.model data structures.
 * 
 * The primary extension for the rectQuery problem is that a
 * point can be selected (needed to highlight on screen those
 * points that are indeed selected).
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SelectableMultiPoint extends Point implements IMultiPoint, ISelectable {

	/**
	 * Generated Serializable ID
	 */
	private static final long serialVersionUID = 533888078133699992L;
	
	/** Selectable. */
	boolean isSelected;
	
	/** selectable mark. */
	int mark;
	
	/** Build a point from the details. */
	public SelectableMultiPoint (int x, int y) {
		super (x,y);
	}
	
	/** Convenience constructor to draw information from IPoint. */
	public SelectableMultiPoint (IPoint pt) {
		super ((int)pt.getX(), (int)pt.getY());
	}

	/** Convenience constructor to draw information from IMultiPoint of two dimensions. */
	public SelectableMultiPoint (IMultiPoint pt) throws IllegalArgumentException {
		super ((int)pt.getCoordinate(1), (int)pt.getCoordinate(2));
		
		if (pt.dimensionality() > 2) {
			throw new IllegalArgumentException ("Only able to construct from IMultiPoint with 2 dimensions");
		}
	}
	
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public String toString () {
		String sel = "";
		if (isSelected) sel = "*";
		return "(" + x + "," + y + ")" + sel;
	}

	/** Determine selectable status. */
	public void select(boolean flag) {
		isSelected = flag;
		mark = 0;   // always reset on select. 
	}

	/**
	 * @see IMultiPoint#dimensionality()
	 */
	public int dimensionality() {
		return 2;
	}

	/**
	 * @see IMultiPoint#getCoordinate(int)
	 */
	public double getCoordinate(int d) {
		if (d == 1) {
			return x;
		}

		// must be the second dimension, then.
		return y;
	}
	
	/**
	 * Return the euclidean distance between the given multipoint.
	 * 
	 * Ensures that only valid for two-dimensional comparison.
	 */
	public double distance (IMultiPoint imp) {
		if (imp.dimensionality() != 2) {
			throw new IllegalArgumentException ("distance computation can only be performed between two-dimensional points");
		}
		
		double ox = imp.getCoordinate(1);
		double oy = imp.getCoordinate(2);
		
		return Math.sqrt((ox-x)*(ox-x) + (oy-y)*(oy-y));
	}

	public double[] raw() {
		return new double[] { x, y };
	}

	public void select(int mark) throws IllegalArgumentException {
		isSelected = true;
		this.mark = mark;
	}	
	
	public int getMark() {
		return mark;
	}
}
