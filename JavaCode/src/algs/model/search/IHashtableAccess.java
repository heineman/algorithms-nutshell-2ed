package algs.model.search;

/**
 * Abstraction of methods that one expects a Hashtable to perform.
 * <p>
 * If subclasses are unable to perform the actions as specified in this
 * interface, they may throw an {@link IllegalStateException} to alert
 * users that they are no longer consistent.
 * 
 * @param K   type of element to be stored as an entry in the hash table.
 * @param V   type of element to be a value associated with an entry in the hash table.
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IHashtableAccess<K,V> {

	/** 
	 * Return the number of elements in the hash table.
	 * @return number of elements in hashtable 
	 */
	int size();
	
	/** 
	 * Determine if element exists within the hash table.
	 * 
	 * @param k  key of target item to be searched.
	 * @return <code>true</code> if element exists within the hash table; <code>false</code> otherwise.
	 */
	boolean search(K k);
	
	/**
	 * Remove the entry from the hash table.
	 * 
	 * @param k   key of target item to be removed.
	 * @return object of class V if previously associated in the Hash table
	 * with the given key; <code>null</code> otherwise.
	 */
	V remove(K k);
	
	/**
	 * Associate element v with key k.
	 * 
	 * @param k   key to be added to the Hash table entries
	 * @param v   target item to be associated with the key
	 * 
	 * @return <code>null</code> if no such element associated in the Hash Table
	 * with the given key; otherwise returns V objects that was previously associated
	 * with that object.
	 */
	V add(K k, V v);
	
}
