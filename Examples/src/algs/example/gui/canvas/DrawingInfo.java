package algs.example.gui.canvas;

import algs.model.kdtree.DimensionalNode;

/**
 * Maintains AWT information about the regions associated with each point
 * in the KD tree.
 * 
 * Ensures that drawing lines will be within their respective boundaries
 * as the KD tree is drawn.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DrawingInfo {
	int minHorizontal;
	int maxHorizontal;
	
	int minVertical;
	int maxVertical;
	
	public DrawingInfo (int minh, int maxh, int minv, int maxv) {
		minHorizontal = minh;
		maxHorizontal = maxh;
		
		minVertical = minv;
		maxVertical = maxv;
	}
	
	public String toString () { return "H:" + minHorizontal + "," + maxHorizontal + ", V:" + minVertical + "," + maxVertical; } 
	
	public DrawingInfo above(DimensionalNode node) {
		int d = node.dimension; 
		if (d == 1) {
			return new DrawingInfo ((int) node.point.getCoordinate(d), maxHorizontal,
						minVertical, maxVertical);
		} else {
			return new DrawingInfo (
					minHorizontal, maxHorizontal,
					(int) node.point.getCoordinate(d), maxVertical);
		}
	}
	
	public DrawingInfo below(DimensionalNode node) {
		int d = node.dimension; 
		if (d == 1) {
			return new DrawingInfo (minHorizontal, (int) node.point.getCoordinate(d),
						minVertical, maxVertical);
		} else {
			return new DrawingInfo (
					minHorizontal, maxHorizontal,
					minVertical, (int) node.point.getCoordinate(d));
		}
	}
}
