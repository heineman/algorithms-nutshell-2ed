package algs.model.list;

import java.util.Iterator;

/**
 * Provide minimal iterator to walk through the next pointers in the {@link DoubleLinkedList}.
 * <p>
 * 
 * @param <E>   Underlying type of the element values stored by each node
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DoubleLinkedListIterator<E> implements Iterator<E> {
	
	/** Next value to return. */
	private DoubleNode<E> current;
	
	/** Maintain reference to list for remove. */
	private final DoubleLinkedList<E> base;
	
	/** Count from the head of the list. */
	private int count;
	
	/** Is it safe to remove? */
	private boolean safeToRemove = false;
	
	/** 
	 * Constructor for the iterator over the list.
	 *  
	 * @param elist   List to be processed by the iterator.
	 */
	public DoubleLinkedListIterator (DoubleLinkedList<E> elist) {
		base = elist;
		current = elist.head;
		count = 0;
		safeToRemove = false;
	}

	/** Return next element in the iteration. */
	public E next() {
		count++;
		safeToRemove = true;
		E retVal = current.value;
		current = current.next;
		return retVal;
	}
	
	/** Determine if more elements exist in iteration. */
	public boolean hasNext() {
		return (current != null);
	}

	/**
	 * Remove the most recent element retrieved by the {@link #next()} iterator method.
	 * 
	 * @exception IllegalStateException if the <tt>next</tt> method has not
     *		  yet been called, or the <tt>remove</tt> method has already
     *		  been called after the last call to the <tt>next</tt>
     *		  method.
     */
	public void remove() {
		if (!safeToRemove) {
			throw new IllegalStateException ("Invalid invocation of remove.");
		}
		
		if (count == 1) {
			base.removeFirst();
		} else {
			// snip out
			current.prev.prev = current;
			base.size--;
		}
		
		count--;
		safeToRemove = false;
	}
}
