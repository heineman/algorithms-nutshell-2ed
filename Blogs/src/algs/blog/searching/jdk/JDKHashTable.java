package algs.blog.searching.jdk;
import java.util.Hashtable;

import algs.blog.searching.search.ICollectionSearch;

/**
 * Wrapper of default JDK Hashtable.
 * <p>
 * Depends upon the existing {@link Object#hashCode()} method, whose value is normalized
 * to the size of the {@link #storage} array. Also uses {@link Object#equals(Object)} to 
 * compare between two elements.
 * <p>
 * Note that deletion is difficult to support because it would destroy the chaining 
 * that occurs when elements collide.
 * 
 * @param <E>   Element type
 * @author George Heineman
 */
public class JDKHashTable<E> implements ICollectionSearch<E> {

	/** Elements stored in array. */
	final Hashtable<E,Boolean> table;
	
	public JDKHashTable (int initialSize) {
		table = new Hashtable<E,Boolean>(initialSize);
	}
	
	/** Return number of elements in collection. */
	public int size() {
		return table.size();
	}

	/**
	 * Insert element into collection.
	 * @param elem        Element to insert   
	 * @return            old value should it have already existed.
	 */
	public boolean insert(E elem) {
		Boolean b = table.get(elem);
		if (b == null || b == false) { return false; }
		return true;
	}
	
	
	@Override
	public boolean exists(E target) {
		return table.containsKey(target);
	}

}
