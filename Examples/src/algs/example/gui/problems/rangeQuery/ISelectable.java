package algs.example.gui.problems.rangeQuery;

/**
 * Determines if an entity is selected or not.
 * 
 * Each entity can be individually selected, and can have a mark associated 
 * with it (but only if selected).
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface ISelectable {
	/**
	 * Determines whether this entity is selected.
	 */
	boolean isSelected();
	
	/** 
	 * Selects or unselects the entity.
	 * 
	 * @param state   <code>true</code> to select; <code>false</code> to unselect.
	 */
	void select(boolean state);
	
	/** 
	 * An element can be selected and annotated with an integer.
	 * 
	 * When unselected (by means  of the {@link #select(boolean)} method) the
	 * mark is set back to zero. The mark passed into this method must be >0.
	 * 
	 * @param mark   integer to associate with entity (> 0).
	 */
	void select(int mark) throws IllegalArgumentException;
	
	/** Return mark associated with point, or 0 if point wasn't selected. */
	int getMark ();
}
