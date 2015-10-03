package algs.example.gui.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import algs.example.gui.model.Model;
import algs.model.IPoint;

/**
 * AWT Canvas subclass to be able to draw a set of elements as defined by 
 * an ArrayList of E elements. 
 * <p>
 * Specific drawing operations are delegated to the subclasses.
 * 
 * All decorators are to draw on top of this, starting with 'root'
 * 
 * @param <E>    Type of entity being drawn on the canvas.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class ElementCanvas<E> extends Canvas {
	
	/**
	 * Generated Serialization ID to keep Eclipse happy.
	 */
	private static final long serialVersionUID = -2208836019911785114L;

	/** Screen image into which we will draw tree to avoid flicker. */
	protected Image screenImage;
	
	/** Entities being managed by this canvas. */
	protected Model<E> model;
	
	/** Knows the decorator at the top of the chain. */
	DrawingCanvas root;
	
	/**
	 * Set the elements to be drawn by this canvas.
	 * 
	 * @param model   The model that backs the canvas
	 */
	public void setModel(Model<E> model) {
		this.model = model;
	}
	
	/**
     * Helper method to ensure that the off-screen image is available.
     *
     * Because of the way Java AWT works, you can't create an Image object
     * until the Application is visible; however, this leads to a catch-22,
     * so we perform "lazy computation" by creating the image once it is 
     * really needed.
     */
    protected void ensureImageAvailable() {
       // not yet created the background image. Must do now; can't do until
       // we have a valid Graphics object.
       if (screenImage == null) {
    	   screenImage = this.createImage(this.getWidth(), this.getHeight());
       }
    }
	
    /**
     * Set the decorator chain for this canvas.
     * 
     * Decouple from the canvas constructor so it can be executed separately
     * as needed.
     *  
     * @param drawing
     */
    public void setDrawer(DrawingCanvas drawing) {
    	this.root = drawing;
    }
    
    /**
     * Core functionality to draw a point in the canvas.  
     * 
     * @param sc
     * @param p
     */
    public void drawPoint(Graphics sc, IPoint p) {
		// Convert to Cartesian coordinates
		double x1 = p.getX();
		double y1 = p.getY();
		
		sc.drawOval((int)x1-2, (int)y1-2, 5, 5);
	}
	
    /**
     * Draw the parameterized type element.
     * 
     * Delegated to subclass where it belongs.
     *  
     * @param sc
     * @param e
     */
    public abstract void drawElement(Graphics sc, E e);
    
    /**
     * Draws the current state to an off-screen buffer and then requests
     * a repaint of the canvas to show it to the user.
     */
    public void redrawState() {
    	ensureImageAvailable();
		// nothing to draw into? Must stop here.
		if (screenImage == null) return;

		// clear the image.
		Graphics sc = screenImage.getGraphics();
		sc.setColor(Color.white);
		sc.fillRect(0, 0, this.getWidth(), this.getHeight());
		
        drawState(sc);

        // have decorators take over now
        if (root != null) { root.draw(sc); }
        
        // force the copy of the new state into our visible region.
        repaint();
    }
    
    /**
     * Draw the state by drawing each element.
     * 
     * @param   sc   Graphics context into which to draw
	 */
	public void drawState(Graphics sc) {
		sc.setColor(Color.black);
		
		// draw all points, but only if items have been generated...
		E[] its = model.items();
		
		if (its == null) { return; }
		for (E e : its) {
			drawElement(sc, e);
		}
	}
	
	public void paint(Graphics g) {
		// not yet visible (RARELY occurs)
		if (g == null) { return; }
		
		int w = getWidth();
		int h = getHeight();
		g.drawImage(screenImage, 0, 0, w, h, this);
	}
}
