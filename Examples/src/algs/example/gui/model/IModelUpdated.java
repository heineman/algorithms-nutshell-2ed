package algs.example.gui.model;

/**
 * Whenever the model is updated, an alert is given to a listener that
 * implements this interface.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IModelUpdated<E> {
	
	/** 
	 * The given model has been updated.
	 * 
	 * @param model
	 */
	void modelUpdated (Model<E> model);
}
