package algs.example.gui.problems.rangeQuery;

import java.awt.Color;
import java.awt.Graphics;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.IMultiPoint;


/**
 * Defines how elements (in this case SelcetableMultiPoint objects) are drawn in 
 * the graphics context.
 * 
 * Properly handles selectable points (and marked ones).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SelectablePointCanvas extends ElementCanvas<IMultiPoint> {
	
	/**
	 * Keep Eclipse happy.
	 */
	private static final long serialVersionUID = -1442454425465617120L;

	/** Properly converts Cartesian coordinates in p into AWT. */
	@Override
	public void drawElement(Graphics sc, IMultiPoint o) {
		IMultiPoint p = (IMultiPoint) o;
		double x = p.getCoordinate(1);  // x
		double y = getHeight() - p.getCoordinate(2);  // y
		
		if (p instanceof SelectableMultiPoint) {
			SelectableMultiPoint smp = (SelectableMultiPoint)p;
			if (smp.isSelected()) {
				if (smp.getMark() > 0) {
					sc.setColor(Color.green);   // drained nodes
				} else {
					sc.setColor(Color.red);     // normal selection
				}
			} else {
				sc.setColor(Color.white);
			}
		} else {
			sc.setColor(Color.white);
		}
		sc.fillOval((int)x-4,(int)y-4,8,8);
		
		// edge shown in black.
		sc.setColor(Color.black);
		sc.drawOval((int)x-4,(int)y-4,8,8);
	}	
}
