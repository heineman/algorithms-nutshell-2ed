package algs.model.search;

import java.util.LinkedList;

/**
 * HashTable that uses list collision to store objects whose keys collide.
 * <p>
 * Keys are distinct from the values being stored.
 * 
 * @author George Heineman
 * @author Gary Pollice
 *
 * @param K   type of element to be stored as an entry in the hash table.
 * @param V   type of element to be a value associated with an entry in the hash table.
 * 
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AssociativeHashTable<K,V> extends HashTable<K,V> {

	/** Helper class for storing entries in linked lists. */
	class Entry<K2,V2> {
		final K2 key;
		
		V2 value;
		
		Entry(K2 k, V2 v) {
			this.key = k;
			this.value = v;
		}
		
	}
	
	/** Store multiple elements via linked list. */
	LinkedList<Entry<K,V>> []table;
	
	/** Range of integers in a table. */
	int[] intHashTable;
	
	/**
	 * Construct initial Hash Table using desired hash method.
	 * 
	 * @param tableSize     desired size.
	 * @param hashMethod    method to use when hashing elements.
	 */
	@SuppressWarnings("unchecked")
	public AssociativeHashTable(int tableSize, IHash<K> hashMethod) {
		super(tableSize, hashMethod);
		
		table = new LinkedList[tableSize];
	}

	/**
	 * Construct initial Hash Table using default hash method that relies
	 * on a properly formed hashCode() implementation.
	 * 
	 * @param tableSize     desired size.
	 */
	public AssociativeHashTable(int tableSize) {
		this(tableSize, new StandardHash<K>(tableSize));
	}
	
	/**
	 * Search for the desired value in the HashTable.
	 * <p>
	 * Only succeeds if V overrides the equals (Object o) method 
	 * 
	 * @param k  the searched-for key
	 * @return <code>true</code> if element v is found in the HashTable; <code>false</code>
	 * otherwise.
	 */
	public boolean search(K k) {
		int h = hashMethod.hash(k);
		LinkedList<Entry<K,V>> list = (LinkedList<Entry<K,V>>) table[h];
		if (list == null) { return false; }
		
		int sz = list.size();
		for (int i = 0; i < sz; i++) {
			Entry<K,V> e = list.get(i);
			if (e.key.equals(k)) {
				return true;
			}
		}
		
		// nope
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.search.IHashtableAccess#add(java.lang.Object)
	 */
	public V add(K k, V v) {
		int h = hashMethod.hash(k);
		if (table[h] == null) {
			table[h] = new LinkedList<Entry<K,V>>();
			table[h].add(new Entry<K,V>(k,v));
			count++;
			return null;
		}

		// Add element into linked list for bin 'h'. If already present
		// then we must replace.
		LinkedList<Entry<K,V>> list = (LinkedList<Entry<K,V>>) table[h];
		int sz = list.size();
		for (int i = 0; i < sz; i++) {
			Entry<K,V> e = list.get(i);
			
			// if key value is the same, we are replacing the value but this doesn't increase
			// the count of the elements in the hashtable.
			// Defect Report: V1.0
			if (e.key.equals(k)) {
				V oldvalue = e.value;
				e.value = v;
				return oldvalue;
			}
		}
		
		table[h].add(new Entry<K,V>(k,v));
		count++;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.search.IHashtableAccess#remove(java.lang.Object)
	 */
	public V remove(K k) {
		int h = hashMethod.hash(k);
		if (table[h] == null) { return null; }

		LinkedList<Entry<K,V>> list = (LinkedList<Entry<K,V>>) table[h];
		int sz = list.size();
		for (int i = 0; i < sz; i++) {
			Entry<K,V> e = list.get(i);
			if (e.key.equals(k)) {
				V v = e.value;
				list.remove(i);
				count--;
				return v;
			}
		}
		
		return null;
	}

	@Override
	public String report() {
		return "associated hash table report.";
	}
}
