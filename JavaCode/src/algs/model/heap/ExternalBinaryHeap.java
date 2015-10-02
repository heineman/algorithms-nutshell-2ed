package algs.model.heap;

import java.util.Comparator;

/**
 * Declared as 'External' since all comparison is external via a provided
 * comparator class.
 * <p>
 * Functionality is identical to BinaryHeap
 * 
 * @param <E>    Type of element stored in the BinaryHeap. Doesn't need to be Comparable because this relies
 * on comparator helper functions.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ExternalBinaryHeap<E> {

	/** Number of elements in binary heap */
	int _n;

	/** Values. */
	E[] _elements;

	/** External comparator, if one has been assigned. */
	Comparator<E> comparator = null;

	/**
	 * Construct a Binary heap of given size.
	 * 
	 * All comparisons between elements are to use the provided Comparator
	 * 
	 * @param n       initial size of the BinaryHeap.
	 * @param comp    comparison function to use
	 */
	@SuppressWarnings("unchecked")
	public ExternalBinaryHeap (int n, Comparator<E> comp) {
		_n = 0;  // initially none in the heap

		// simplify algorithm to consider position 1 as being the 'root'
		_elements = (E[]) new Object [n+1];		
		
		this.comparator = comp;
	}
	
	/** 
	 * Determine if Binary Heap is empty.
	 * @return true if Binary Heap is empty. 
	 */
	public boolean isEmpty() { 
		return _n == 0;
	}
	
	/**
	 * Insert the object into the BinaryHeap
	 * @param obj   object to be inserted into the {@link BinaryHeap}
	 */
	public void insert (E obj) { 
		int i;

		// add to end of the heap. If 1 then the first element.
		i = ++_n;
		E p;
		while (i > 1) {
			int pIdx = i/2;
			p = _elements[pIdx];

			// are we in the right spot? Leave now
			if (comparator.compare(obj, p) > 0) { break; }

			// otherwise, swap and move up
			_elements[i] = p;
			i = pIdx;
		}

		// insert into spot vacated by moved element (or last one)
		_elements[i] = obj;
	}

	public E smallest() { 
		E id = _elements[1];
		int pIdx;

		// heap will have one less entry, and we want to place leftover one
		// in proper location.
		E last = _elements[_n];
		_n--;

		_elements[1] = last;

		pIdx = 1;
		int child = pIdx*2;
		while (child <= _n) {
			// select smaller of two children
			E sm = _elements[child];
			if (child < _n) {
				if (comparator.compare(sm, _elements[child+1]) > 0) {
					sm = _elements[++child];
				}
			}

			// are we in the right spot? Leave now
			if (comparator.compare(last, sm) <= 0) { break; }

			// otherwise, swap and move up
			_elements[pIdx] = sm;

			pIdx = child;
			child = 2*pIdx;
		}

		// insert into spot vacated by moved element (or last one)
		_elements[pIdx] = last;
		return id;	
	}
	
}
