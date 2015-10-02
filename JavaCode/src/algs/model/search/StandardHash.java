package algs.model.search;

/**
 * Implements standard hashCode computation on objects.
 * 
 * @param <V>   Type on which hashCode is invoked.
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StandardHash<V> implements IHash<V> {

	/** Size of the table. */
	int tableSize;
	
	/** 
	 * Hash needs to know size of the table. 
	 * @param tableSize    size of the hash to create. 
	 */
	public StandardHash (int tableSize) {
		this.tableSize = tableSize;
	}
	
	/**
	 * Compute the hash of the given value.
	 * 
	 * @param v   value to be hashed.
	 * @return    integer hased value suitable for hashtable.
	 */
	public int hash(V v) {
		int h = v.hashCode();
		
		if (h < 0) h = 0 - h;
		return h % tableSize;
	}

}
