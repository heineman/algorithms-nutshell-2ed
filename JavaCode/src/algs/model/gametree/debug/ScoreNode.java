package algs.model.gametree.debug;

import algs.debug.Formatter;
import algs.debug.IGraphEntity;
import algs.debug.ISelectFont;

/**
 * This node is used when depicting debugging information about the score 
 * assigned to a node.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ScoreNode implements IGraphEntity, ISelectFont {
	/** Store private score associated with node. */
	private int score;
	
	/** 
	 * Construct a ScoreNode in the visualization to represent scored node.
	 * 
	 * @param sc   score
	 */
	public ScoreNode (int sc) {
		this.score = sc;
	}
	
	/** 
	 * Return the score for this node.
	 * @return integer score associated with node. 
	 */
	public int score() { return score; }
	
	/** Label for this node encodes the score. */
	public String nodeLabel() {
		return Formatter.convert(score);
	}
	
	/** To properly draw Infinity symbols in symbol font. */
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
