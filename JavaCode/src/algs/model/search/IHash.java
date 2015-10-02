package algs.model.search;

/**
 * Default interface for producing the slot within the hashtable for an element.
 * 
 * Note that we can't use the default hashCode() method since that generates only
 * the raw key() information.
 * 
 * @param <V>   type of object being hashed.
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IHash<V> {
	
	/** 
	 * Compute the proper index into a hashtable.
	 * @param  v   value to be hashed and (presumably) inserted into hashtable.
	 * @return integer value suitable for hashtable 
	 */ 
	int hash(V v);
}
