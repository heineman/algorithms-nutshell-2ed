package algs.model.search;

/**
 * Binary Search in Java given a presorted array of the parameterized type. 
 *
 * @param T   elements of the collection being searched are of this type.
 *            The parameter T must implement Comparable.  
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BinarySearch<T extends Comparable<T>> {

	/** 
	 * Search for non-null target in collection. 
	 * @param collection    array of values of type T
	 * @param target        target element to be searched
	 * @return true if collection contains target
	 */
	public boolean search(T[] collection, T target) {
		// null is never included in the collection
		if (target == null) { return false; }

		int low = 0, high = collection.length - 1;
		while (low <= high) {
			int mid = (low + high)/2;
			int rc = target.compareTo(collection[mid]);

			if (rc < 0) {
				// target is less than collection[i]
				high = mid - 1;
			} else if (rc > 0) {
				// target is greater than collection[i]
				low = mid + 1;
			} else {
				// found the item.
				return true;
			}
		}

		return false;
	}
}
