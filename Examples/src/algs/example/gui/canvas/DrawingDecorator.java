package algs.example.gui.canvas;

import java.awt.Graphics;

/**
 * The abstract base class representing decorators being used as part of the
 * rendering process
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DrawingDecorator extends DrawingCanvas {

	/** The next one in the chain. */
	private DrawingCanvas inner;
	
	/** 
	 * When constructing a Decorator, you must pass in next one in chain.
	 * 
	 * @param inner   Next object along the decorator chain
	 */
	public DrawingDecorator(DrawingCanvas inner) {
		this.inner = inner;
	}
	
	
	/**
	 * When requested to draw state, pass this along to inner
	 */
	@Override
	public void draw(Graphics g) {
		inner.draw(g);
	}

	/**
	 * When an announced change in state occurs, pass along. 
	 */
	@Override
	public void stateUpdated() {
		inner.stateUpdated();
	}

	/** 
	 * To cause a state change, pass along
	 */
	@Override
	public void update() {
		inner.update();
	}
}
