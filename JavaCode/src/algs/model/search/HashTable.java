package algs.model.search;

import java.util.Iterator;

/**
 * Provides the abstract base class for hash tables.
 * <p>
 * The two known subclasses are ListHashTable and ProbeHashTable.
 * 
 * @param K   type of element to be stored as an entry in the hash table.
 * @param V   type of element to be a value associated with an entry in the hash table.
 * 
 * @author Gary Pollice
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class HashTable<K,V> implements IHashtableAccess<K,V> {
	
	/** Known size of the table. */
	int tableSize;
	
	/** Method for hashing based on Key type. */
	IHash<K> hashMethod;
	
	/** Known number of entries in the hash table. */
	int count;
	
	/**
	 * Construct an empty HashTable of the given size.
	 * 
	 * The hash method to use is provided as an argument. The structure of
	 * the hash table is provided by the appropriate subclass.
	 * 
	 * @param tableSize     initial size of the hash table.
	 * @param hashMethod    method to use when hashing objects.
	 */
	public HashTable(int tableSize, IHash<K> hashMethod) {
		this.tableSize = tableSize;
		this.hashMethod = hashMethod;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.search.IHashtableAccess#size()
	 */
	public int size() {
		return count;
	}	
	
	
	/**
	 * Bulk remove elements from the Hash Table from the Iterator of key values.
	 * 
	 * @param it   Iterator of the elements to be removed from the Hash Table.
	 */
	public void remove(Iterator<K> it) {
		
		// Pull each value from the iterator and add.
		while (it.hasNext()) {
			remove(it.next());
		}
	}
	
	/**
	 * Every Hash Table has the ability to report interesting statistics about
	 * itself.
	 * <p>
	 * Defined by subclasses
	 * @return string representation of hash table statistics.
	 */
	public abstract String report();
}
