package algs.model.search;

import java.util.Iterator;

/**
 * Sequential Search in Java (both for indexed collections as well as 
 * collections accessed via iterators).
 *
 * @param <T>   elements of the collection being searched are of this type.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SequentialSearch<T> {

	/**
	 * Apply the brute-force Sequential Search to search the
	 * indexed collection (of type T) for the given target item.
	 * 
	 * @param collection    indexed collection of type T being searched
	 * @param t             non-null target item to locate.
	 * @return <code>true</code> if target item exists within the collection;
	 *         <code>false</code> otherwise.
	 */
	public boolean search (T[] collection, T t) {
		for (T item : collection) {
			if (item.equals(t)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Apply the brute-force Sequential Search to search the
	 * iterable collection (of type T) for the given target item.
	 * 
	 * @param collection    iterable collection of type T being searched
	 * @param t             non-null target item to locate
	 * @return <code>true</code> if target item exists within the collection;
	 *         <code>false</code> otherwise.
	 */
	public boolean search (Iterable<T> collection, T t) {
		Iterator<T> iter = collection.iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(t)) {
				return true;
			}
		}
		
		return false;
	}
	
}
