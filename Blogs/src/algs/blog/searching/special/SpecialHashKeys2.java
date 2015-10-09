package algs.blog.searching.special;

/**
 * Specially configured collection that ensures a single probe to determine location
 * of an item for keys2 (but pretty much only for keys2)!
 * 
 * @author George Heineman
 */
public class SpecialHashKeys2 extends SpecialHashbasedSearch {

	/** Elements stored in array. */
	private String[] storage;
	
	/** keys2 contains 641 strings. */
	public static final int SIZE = 641;
		
	public SpecialHashKeys2 () {
		storage = new String[26*26];
	}
	
	
	/** Return number of elements in collection. */
	public int size() {
		return SIZE;
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
	 * If element already exists within collection, return its position. That is,
	 * no duplicates are allowed, yet we silently ignore requests to repeatedly add
	 * the same element into the collection.
	 * 
	 * @param elem        Element to insert   
	 * @return            Returns number of probes to find location.
	 * @throws Exception  should no empty location be available, throw exception 
	 */
	@Override
	public int insert(String elem) {
		int firstIndex = (int)(elem.charAt(0)-'a');
        int lastIndex = (int)(elem.charAt(elem.length()-2)-'a');
        storage[firstIndex*26+lastIndex] = elem;
        return 1;
	}	
	
	@Override
	public boolean exists(String target) {
		int firstIndex = (int)(target.charAt(0)-'a');
        int lastIndex = (int)(target.charAt(target.length()-2)-'a');
        return target.equals(storage[firstIndex*26+lastIndex]);
	}

	public String info() {
		// that's it
		StringBuilder sb = new StringBuilder("Report: size: " + storage.length + ", elements: " + SIZE);
		int numEmpty = 0;
		for (int i = 0; i < storage.length; i++) {
			if (storage[i] == null) { numEmpty++; }
		}
		sb.append(", empty bins:" + numEmpty);
		
		
		return sb.toString();
	}

	@Override
	public void reset() {
		storage = new String[26*26];		
	}
	
	public String toString() {
		return "SpecialHashKeys2";
	}
	
}
