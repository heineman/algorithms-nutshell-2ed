package algs.example.gui.generator;

/**
 * Interface to govern the regular communication of an action (and the error
 * message in case the action fails).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IOutput {
	/** Error message. */ 
	void error (String s);
	
	/** Regular message. */
	void message (String s);
}
