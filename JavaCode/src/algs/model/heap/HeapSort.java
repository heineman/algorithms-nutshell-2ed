package algs.model.heap;

import java.util.Comparator;

/**
 * Implementation of HeapSort using BinaryHeap.
 * 
 * @param <E>    type of the values over which HeapSort processes.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class HeapSort<E extends Comparable<E>> {
	
	/**
	 * Sort using Heapsort method.
	 * 
	 * left must be strictly less than right, otherwise return false.
	 * 
	 * @param ar       Array of Comparable objects
	 * @param left     The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right    The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 * @return false   if invalid arguments passed in; true otherwise on success.
	 */
	public boolean sort (Comparable<E>[]ar, int left, int right) {
		if (right <= left) { return false; }
		
		// Form a heap (this could be done in place, but this is simpler coding)
		BinaryHeap<E> heap = new BinaryHeap<E>(right-left+1);
		
		int i = left;
		while (i <= right) {
			heap.insert(i, ar[i]);
			i++;
		}
		
		i = left;
		while (!heap.isEmpty()) {
			ar[i++] = heap.smallest();
		}
		
		return true;
	}
	
	/**
	 * Sort using Heapsort method with external comparator.
	 * 
	 * left must be strictly less than right, otherwise return false.
	 *	
	 * @param ar          Array of objects
	 * @param left        The left-bounds within which to sort (0 &le; left &lt; ar.length)
	 * @param right       The right-bounds within which to sort (0 &le; right &lt; ar.length)
	 * @param comparator  Externalize the comparison of two objects into this method.
	 * @return false if invalid arguments passed in; true otherwise on success.
	 */
	public boolean sort (E[]ar, int left, int right, Comparator<E> comparator) {
		if (right <= left) { return false; }
		
		// Form a heap (this could be done in place, but this is simpler coding)
		ExternalBinaryHeap<E> heap = new ExternalBinaryHeap<E>(right-left+1, comparator);
		Object[]sorted = new Object[right-left+1];
		int i = left;
		while (i <= right) {
			heap.insert(ar[i]);
			i++;
		}
		
		i = left;
		while (!heap.isEmpty()) {
			sorted[i++] = heap.smallest();
		}
		
		// copy back in.
		System.arraycopy(sorted, 0, ar, left, sorted.length);
		return true;
	}
}
