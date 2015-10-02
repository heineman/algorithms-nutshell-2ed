package algs.model.gametree.debug;

import java.util.Comparator;

import algs.debug.Formatter;
import algs.debug.IGraphEntity;
import algs.debug.ISelectFont;
import algs.model.gametree.IComparator;

/**
 * Represents a Min or Max node in the debugging output.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MinMaxNode implements IGraphEntity, ISelectFont {
	/** Use the MIN/MAX comparator as the means to differentiate MIN from MAX nodes. */
	final Comparator<Integer> selector;
	
	/** Computed score for the node. */
	int value;
	
	/**
	 *  Construct node based upon its MIN/MAX type.
	 *  
	 * @param selector   MIN or MAX
	 * @exception   IllegalArgumentException   if neither MIN nor MAX is the actual parameter.
	 */
	public MinMaxNode (Comparator<Integer> selector) {
		if (selector != IComparator.MAX && selector != IComparator.MIN) {
			throw new IllegalArgumentException ("MinMaxNode needs one of either IComparator.MIN or IComparator.MAX");
		}
		
		this.selector = selector;
	}

	/** 
	 * Return the value of the node.
	 * @return   value of the node.
	 */
	public int value() {
		return value;
	}
	
	/** 
	 * Set the computed value for the node. 
	 * @param value   value to be associated with node. 
	 */
	public void value (int value) {
		this.value = value;
	}

	/** Show computed value with prefix of MIN/MAX as appropriate. */
	public String nodeLabel() {
		if (selector == IComparator.MAX) {
			return "MAX: " + Formatter.convert(value);
		} else {
			return "MIN: " + Formatter.convert(value);
		}
	}

	/** To properly draw INF/-INF in symbol font. */
	public String fontName() {
// When formatting becomes available again
//		
//		if (Formatter.isSymbol(score)) {
//			return "Symbol";
//		}
		
		return null;  // nothing special.
	}

	/** Default font size to use is ok. */
	public int fontSize() {
		return 0;
	}
}
