package algs.example.gui.problems.nearestNeighbor.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.nearestNeighbor.model.Model;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDPoint;

/**
 * Mouse Handler to interact with Element Canvas.
 * 
 * @param <E>   Underlying type of element to be drawn on the canvas.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MouseHandler<E> extends java.awt.event.MouseAdapter implements MouseMotionListener {
	/** Store the point of contact [AWT]. */
	java.awt.Dimension offset;

	/** interactive Output goes here. */
	protected IOutput output;
	
	/** Store the target canvas. */
	protected ElementCanvas<E> canvas;

	/** Model of entities. */
	Model model;
	
	public MouseHandler (ElementCanvas<E> c, IOutput output, Model model) {
		this.canvas = c;
		this.output = output;
		
		this.model = model;
	}
	
        /** Ignore dragged events... */
        public void mouseDragged(MouseEvent e){}

	/** Automatically track and process nearest queries. */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		// we need to convert the AWT mouse coordinate into Cartesian coordinates
		// so it can be comparable with the Cartesian coordinates in the model.
		int ht = canvas.getHeight();
		model.computeNearest(new Hyperpoint(new TwoDPoint(x,ht - y)));
		if (model.getNearest() == null) { return; }
		
		canvas.redrawState();
		canvas.repaint();
	}
}

