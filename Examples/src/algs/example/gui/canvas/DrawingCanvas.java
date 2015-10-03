package algs.example.gui.canvas;

import java.awt.Graphics;

/**
 * This abstract base class represents both the initial renderer and the
 * subsequent decorators being used as part of the rending process.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class DrawingCanvas {
	
	/**
	 * Any decorator can request for entire state to be redrawn
	 */
	public abstract void update();
	
	/**
	 * When the state is updated, alert the decorators.
	 */
	public abstract void stateUpdated ();
	
	/**
	 * Each inner Decorator is going to have to maintain the graphics
	 * entity into which the actual drawing is to be done. We do it this
	 * way to avoid having to force a particular parameter to the outermost
	 * client. 
	 */
	public abstract void draw (Graphics g);
}
