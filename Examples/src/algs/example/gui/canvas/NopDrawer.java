package algs.example.gui.canvas;

import java.awt.Graphics;

/**
 * This nop canvas is used to ensure we always have an innermost drawer,
 * even if it does nothing.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NopDrawer extends DrawingCanvas {
	
	/**
	 * Any decorator can request for entire state to be redrawn
	 */
	public void update() { }
	
	/**
	 * When the state is updated, alert the decorators.
	 */
	public void stateUpdated () { }
	
	/**
	 * Each inner Decorator is going to have to maintain the graphics
	 * entity into which the actual drawing is to be done. We do it this
	 * way to avoid having to force a particular parameter to the outermost
	 * client. 
	 */
	public void draw (Graphics g) { }
}
