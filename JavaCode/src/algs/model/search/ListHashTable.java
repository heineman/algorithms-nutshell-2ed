package algs.model.search;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * HashTable that uses list collision to store objects whose keys collide.
 * <p>
 * Stores the objects as the keys themselves.
 * <p>
 * @param V   type of element to be the element stored in the Hash table
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ListHashTable<V> extends HashTable<V,V> {

	/** Store multiple elements via linked list. */
	LinkedList<V> []table;

	/**
	 * Construct initial Hash Table using desired hash method.
	 * 
	 * @param tableSize     desired size.
	 * @param hashMethod    method to use when hashing elements.
	 */
	@SuppressWarnings("unchecked")
	public ListHashTable(int tableSize, IHash<V> hashMethod) {
		super(tableSize, hashMethod);

		table = new LinkedList[tableSize];
	}

	/**
	 * Construct initial Hash Table using default hash method that relies
	 * on a properly formed hashCode() implementation.
	 * 
	 * @param tableSize     desired size.
	 */
	public ListHashTable(int tableSize) {
		this(tableSize, new StandardHash<V>(tableSize));
	}

	/**
	 * Search for the desired value in the HashTable.
	 * <p>
	 * Only succeeds if V overrides the equals (Object o) method 
	 * 
	 * @param v  the searched-for value
	 * @return <code>true</code> if element v is found in the HashTable; <code>false</code>
	 * otherwise.
	 */
	public boolean search(V v) {
		int h = hashMethod.hash (v);
		LinkedList<V> list = (LinkedList<V>) table[h];
		if (list == null) { return false; }

		return list.contains(v);
	}

	/**
	 * Bulk load elements into the Hash Table from the Iterator.
	 * 
	 * If previous objects were associated with the keys from these items
	 * they are discarded.
	 * 
	 * @param it   Iterator of the elements to be added into the Hash Table.
	 */
	public void load(Iterator<V> it) {

		// Pull each value from the iterator and add.
		while (it.hasNext()) {
			V v = it.next();
			add(v, v);
		}
	}

	/** Reduced for Figure 5-6 in second edition. */
	public void load_Figure5_6(Iterator<V> it) {
		table = (LinkedList<V>[]) new LinkedList[tableSize];

		// Pull each value from the iterator and find desired bin h.
		// Add to existing list or create new one into which value is added.
		while (it.hasNext()) {
			V v = it.next();
			int h = hashMethod.hash (v);
			if (table[h] == null) {
				table[h] = new LinkedList<V>();
			}
			table[h].add(v);
			count++;  
		}
	}

	/**
	 * ListHashTable objects add elements who are themselves keys.
	 * 
	 * @param k   the key (and value) of the element to be added.
	 */
	public void add(V k) {
		add(k,k);
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.search.IHashtableAccess#add(java.lang.Object)
	 */
	public V add(V k, V v) {
		if (k != v) {
			throw new IllegalArgumentException ("A ListHashTable stores elements as the keys and entries. k must == v");
		}

		int h = hashMethod.hash(v);
		if (table[h] == null) {
			table[h] = new LinkedList<V>();
		}

		// Add element into linked list for bin 'h'
		LinkedList<V> list = (LinkedList<V>) table[h];
		if (list.contains(v)) {
			return v;
		}
		list.add(v);
		count++;  // increment count
		return v;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.search.IHashtableAccess#remove(java.lang.Object)
	 */
	public V remove(V v) {
		int h = hashMethod.hash(v);
		if (table[h] == null) { return null; }

		// Remove element from linked list for bin 'h'
		LinkedList<V> list = (LinkedList<V>) table[h];
		if (list.remove(v)) {
			count--;  // decrement count

			return v;
		} else {
			return null;
		}
	}

	@Override
	public String report() {
		return new ListHashTableReporter<V>(this).report();
	}

}
