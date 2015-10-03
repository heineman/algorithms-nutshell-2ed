package algs.example.gui.model;

import algs.model.IRectangle;

/**
 * Return the active rectangle being manipulated by the user.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IActiveRectangle {

	/** 
	 * A mouse controller enables the user to drag a rectangle across the 
	 * canvas and this method returns it.
	 */
	IRectangle getActiveRectangle();
	
}
