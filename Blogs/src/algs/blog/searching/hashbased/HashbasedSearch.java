package algs.blog.searching.hashbased;

import algs.blog.searching.search.ICollectionSearch;

/**
 * Base class for any Hash-based search that uses open addressing to resolve collisions by 
 * storing all elements in the hash table.
 * <p>
 * To insert an element into a slot that contains a value, simply inspect the next  
 * slot to use (modular arraySize) until an empty slot is found.
 * <p>
 * Depends upon the existing {@link Object#hashCode()} method, whose value is normalized
 * to the size of the {@link #storage} array. Also uses {@link Object#equals(Object)} to 
 * compare between two elements.
 * <p>
 * Note that deletion is difficult to support because it would destroy the chaining 
 * that occurs when elements collide.
 * <p>
 * This class depends on having an instance of class {@link Probe} that defines the strategy
 * for selecting each subsequent bin when resolving collisions.
 * 
 * @param <E>   Element type
 * @author George Heineman
 */
public class HashbasedSearch<E> implements ICollectionSearch<E> {

	/** Elements stored in array. */
	final E[] storage;
	
	/** Number of actual elements. */
	int num;
	
	/** Strategy to locate the ith bin. */
	Probe probe;
	
	/**
	 * The Hashbased Search algorithm requires a strategy for identifying subsequent
	 * bins should they be occupied during insert.
	 * 
	 * @param initialSize
	 * @param strategy
	 */
	@SuppressWarnings("unchecked")
	public HashbasedSearch (int initialSize, Probe strategy) {
		storage = (E[]) new Object[initialSize];
		probe = strategy;
	}
	
	/** Return number of elements in collection. */
	public int size() {
		return num;
	}
	
	/** Return capacity of collection. */
	public int capacity() {
		return storage.length;
	}
	
	/**
	 * Insert element into collection.
	 * <p>
	 * Should the count of attempted slots reach the array size, declare that the 
	 * element cannot be added (either because of a poor hash function or because
	 * the array is full).
	 * <p>
	 * If element already exists within collection, then the collection is unchanged and 
	 * we return number of probes to locate the element.
	 * 
	 * @param e               Element to insert   
	 * @return                   Returns number of probes to find location.
	 * @throws RuntimeException  should no empty location be found, given the delta step
	 */
	public int insert(E e) throws RuntimeException {
		// compute initial bin location
		int hash = (e.hashCode() & 0x7FFFFFFF) % storage.length;
		int h = hash;
		for (int p = 1; p <= storage.length; p++) {
			if (storage[h] == null) {
				storage[h] = e;
				num++;
				return p;
			} else if (storage[h].equals(e)) {
				return p;
			}
			
			// advance to next bin
			h = probe.next(hash, p);
		}
		
		throw new RuntimeException ("Unable to insert element: " + num + " slots taken out of " + storage.length + " with probe:" + probe);
	}
	
	@Override
	public boolean exists(E target) {
		int hash = (target.hashCode() & 0x7FFFFFFF) % storage.length;
		int h = hash;
		
		// we may have to search up to the length of the array...
		for (int ct = 1; ct <= storage.length; ct++) {
			if (storage[h] == null) {
				return false;
			}
			
			if (target.equals(storage[h])) {
				return true;
			}
			
			// advance to next bin
			h = probe.next(hash, ct);
		}
		
		return false;   // doesn't exist.
	}

	public String info() {
		// that's it
		StringBuilder sb = new StringBuilder("Report: size: " + storage.length + ", elements: " + num);
		int numEmpty = 0;
		for (int i = 0; i < storage.length; i++) {
			if (storage[i] == null) { numEmpty++; }
		}
		sb.append(", empty bins:" + numEmpty);
		
		int size = 0;
		int min = num;
		int max = -1;
		for (int i = 0; i < storage.length; i++){ 
			E list = storage[i];
			if (list == null) {
				numEmpty++;
				continue;
			}
			
			// one probe
			int hash = (list.hashCode() & 0x7FFFFFFF) % storage.length;
			int h = hash;
			int chain = 1;
			
			while (storage[h] != list) {
				// advance to next bin
				h = probe.next(hash, chain);
				chain++;
			}
			
			size += chain;
			if (chain < min) { min = chain; }
			if (chain > max) { max = chain; }
		}
		
		float avg = size;
		int base = num;
		if (base == 0) {
			sb.append(", average:0");
			sb.append(", minListSize:0");
			sb.append(", maxListSize:0");
		} else {
			avg /= base;
			sb.append (", average:" + avg);
			sb.append(", minListSize:" + min);
			sb.append(", maxListSize:" + max);
		}		
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "HashBasedSearch array[" + storage.length + "] with " + num + " elements and probe:" + probe;
	}
	
}
