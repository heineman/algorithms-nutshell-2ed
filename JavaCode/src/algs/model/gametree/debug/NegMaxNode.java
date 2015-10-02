package algs.model.gametree.debug;

import algs.debug.Formatter;
import algs.debug.IGraphEntity;
import algs.debug.ISelectFont;

/**
 * Represents a NegMax node in the debugging output.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class NegMaxNode implements IGraphEntity, ISelectFont {
	/** Computed value for this node. */
	int value;
	
	/** 
	 * Return the computed value.
	 * @return computed value of NegMaxNode. 
	 */
	public int value() {
		return value;
	}
	
	/** 
	 * Set the computed value.
	 * @param value   new NegMax value for node. 
	 */
	public void value (int value) {
		this.value = value;
	}

	/** Return node label that properly shows value. */
	public String nodeLabel() {
		return "NEGMAX: " + Formatter.convert(value);
	}
	
	/** To properly draw Negmax node, there is now no need for a special font. */
	public String fontName() {
		return null;  // nothing special.
	}

	/** 
	 * Default font size to use is ok.
	 * @return 0 to select default font size. 
	 */
	public int fontSize() {
		return 0;
	}
}
