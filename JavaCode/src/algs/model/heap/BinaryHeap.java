package algs.model.heap;

/**
 * A Binary Heap that can be used as a Priority Queue since it enables elements
 * to have its priority updated while in queue.
 * <p>
 * The elements in the queue have an integer ID and given PRIORITY.
 * 
 * @param <E>  Type of entity to insert into Heap, which must provide Comparable
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BinaryHeap<E extends Comparable<E>> {

	class ElementH {
		/** user-defined information to be stored by id. */
		int            id;            
		
		/** key which will be the priority. */
		Comparable<E>     priority;      
	}
	
	/** number of elements in binary heap */
	int _n;

	/** values. */
	Object[] _elements;

	/** positions. */
	int[] _pos;

	/**
	 * Construct Binary Heap of the given size.
	 * <p>
	 * Since we cannot parameterize Element[] construction, this method
	 * suppresses warnings.
	 * 
	 * @param n    size of array.
	 */
	public BinaryHeap (int n) {
		_n = 0;  // initially none in the heap

		// simplify algorithm to consider position 1 as being the 'root'
		_elements = new Object [n+1];
		for (int k = 0; k < n+1; k++) {
			_elements[k] = new ElementH();
		}
		_pos = new int[n+1];
	}

	/**
	 * Determines if Binary Heap is empty.
	 * @return true if binary heap is empty. 
	 */
	public boolean isEmpty() { 
		return _n == 0;
	}
	
	/** 
	 * Modify BinaryHeap and return its smallest element. 
	 */
	@SuppressWarnings("unchecked")
	private ElementH _smallest() { 
		ElementH el = new ElementH();
		ElementH root = (ElementH) _elements[1];
		el.id = root.id;
		el.priority = root.priority;
		
		int pIdx;

		// heap will have one less entry, and we want to place leftover one
		// in proper location.
		ElementH last = (ElementH) _elements[_n];
		_n--;

		root.id = last.id;
		root.priority = last.priority;

		pIdx = 1;
		int child = pIdx*2;
		while (child <= _n) {
			// select smaller of two children
			ElementH sm = (ElementH) _elements[child];
			if (child < _n) {
				if (sm.priority.compareTo((E) ((ElementH)_elements[child+1]).priority) > 0) {
					sm = (ElementH) _elements[++child];
				}
			}

			// are we in the right spot? Leave now
			if (last.priority.compareTo((E)sm.priority) <= 0) { break; }

			// otherwise, swap and move up
			ElementH eIdx = (ElementH) _elements[pIdx];
			eIdx.id = sm.id;
			eIdx.priority = sm.priority;
			_pos[sm.id] = pIdx;

			pIdx = child;
			child = 2*pIdx;
		}

		// insert into spot vacated by moved element (or last one)
		ElementH eIdx = (ElementH) _elements[pIdx];
		eIdx.id = last.id;
		eIdx.priority = last.priority;
		_pos[last.id] = pIdx;
		
		return el;
	}

	/**
	 * Return the PRIORITY of the smallest entry after modifying the heap.
	 * 
	 * @return integer priority of smallest entry in the Binary Heap 
	 */
	public Comparable<E> smallest() { 
		ElementH el = _smallest();
		
		return el.priority;
	}

	/**
	 * Return the integer IDENTIFIER of the smallest entry after modifying the heap.
	 * 
	 * @return value of smallest entry in the Binary Heap 
	 */
	public int smallestID() { 
		ElementH el = _smallest();
		
		return el.id;
	}
	
	/**
	 * Insert the element (id) with given priority.
	 * <p>
	 * Inserting too many items into a Binary Heap will cause exception.
	 * 
	 * @param id    unique identifier to use (guaranteed to be in range 0..n-1)
	 * @param priority  initial priority for this value.
	 */
	@SuppressWarnings("unchecked")
	public void insert (int id, Comparable<E> priority) { 
		int i;

		// add to end of the heap. If 1 then the first element.
		i = ++_n;
		ElementH p = new ElementH();
		while (i > 1) {
			int pIdx = i/2;
			ElementH eIdx = (ElementH) _elements[pIdx];
			p.id = eIdx.id;
			p.priority = eIdx.priority;

			// are we in the right spot? Leave now
			if (priority.compareTo((E)p.priority) > 0) { break; }

			// otherwise, swap and move up
			ElementH ei = (ElementH) _elements[i];
			ei.id = p.id;
			ei.priority = p.priority;
			_pos[p.id] = i;
			i = pIdx;
		}

		// insert into spot vacated by moved element (or last one)
		ElementH ei = (ElementH) _elements[i];
		ei.id = id;
		ei.priority = priority;
		_pos[id] = i;
	}

	/**
	 * Decrease the priority of the known element in the Binary Heap with the
	 * given identifier.
	 * <p>
	 * No check is done to see if the new "reducedPriority" is in fact actually
	 * less than the actual priority of the element in the Binary Heap. Be careful
	 * that you only call with proper smaller element. 
	 * 
	 * @param id                unique identifier in the range 0..n-1
	 * @param reducedPriority   reduced priority to use, must be smaller than existing.
	 */
	public void decreaseKey (int id, Comparable<E> reducedPriority) {
		int size = _n;

		// truncate heap (temporarily) and act like the binary heap up to
		// but not including this one is all that exists (cute, huh?)
		_n = _pos[id] - 1;

		// now we insert and the binary heap is shuffled appropriately
		insert(id, reducedPriority);

		// since the newPriority must be lower, we can expand back and
		// we still have a working binary heap
		_n = size;
	}
}

