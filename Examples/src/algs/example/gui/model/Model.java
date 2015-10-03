package algs.example.gui.model;

/**
 * Defines a model which can be extended on a problem-by-problem basis.
 * 
 * @param <E>   type of entity
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class Model<E> {
	
	/** 
	 * Entities which are being considered for intersection.
	 * 
	 * Note that items[0] is a potential location where a dynamically created
	 * element is to be found. If no such item exists, then its value is computed 
	 * from defaultEntity().
	 */
	protected E[]  items;
	
	/** Listener (no more than 1). */
	protected IModelUpdated<E> listener;
	
	/** 
	 * Return the array of entities for processing.
	 * 
	 * It is assumed that the items are untouched by those who get access to it
	 * via this method.  No way to enforce this.
	 */
	public E[] items () {
		return items;
	}
	
	/**
	 * Set the items for the model. 
	 * 
	 * Note that there is room set aside for the dynamic entity. 
	 * 
	 * @param items
	 */
	public abstract void setItems(E[] items);
	
		
	/** Tell the world about the model change. */
	protected void modelUpdated() {
		if (listener == null) { return; }
		
		listener.modelUpdated(this);
	}

	
	/** Register a listener with the model. */
	public void setListener (IModelUpdated<E> list) {
		this.listener = list;
	}

	
}