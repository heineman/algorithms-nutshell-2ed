package algs.debug;

import algs.model.gametree.MoveEvaluation;

/**
 * Useful class for formatting special characters.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Formatter {

	/**
	 * Helper method to determine if special font is needed.
	 * 
	 * At one point we were able to embed infinity symbols into the DOTTY output, but this is 
	 * not platform independent, so we have eliminated this capability. Should it become available
	 * in the future, we can bring this back in.
	 * @param value     value to be considered as a special symbol
	 * @return          true if this value can be a special symbol; false otherwise
	 */
	public static final boolean isSymbol (int value) {
		//			if (value == MoveEvaluation.minimum()) {
		//				return false;
		//			} else if (value == MoveEvaluation.maximum()) {
		//				return false;
		//			}

		return false;  // nothing special.			
	}

	/**
	 * Helper method for converting values into properly visible labels.
	 * 
	 * These special characters are displayed in Symbol font.
	 * 
	 * @param      value to appear within output. 
	 * @return     human-readable string for given value.
	 */
	public static final String convert (int value) {
		if (value == MoveEvaluation.minimum()) {
			return "-INF";  // - INF using an em-dash. UTF char \u221E doesn't work
		} else if (value == MoveEvaluation.maximum()) {
			return "INF";   // (Yen-Symbol) that is char for INF in Symbol font. UTF char \u221E doesn't work
		}

		return "" + value;
	}	
}
