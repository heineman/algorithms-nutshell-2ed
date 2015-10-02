package algs.model.list;

import java.util.Iterator;

/**
 * Provide minimal iterator to walk through the next pointers in the linked list.
 * 
 * @param <E> underlying parameterization of node
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ListIterator<E> implements Iterator<E> {
	
	/** Next value to return. */
	private Node<E> current;
	
	/** 
	 * Constructor for iterator takes the list as parameter.
	 *  
	 * @param l   list to be walked through.
	 */
	public ListIterator (List<E> l) {
		current = l.head();
	}

	/** 
	 * Return the value of the next node in the list and advances the Iterator.
	 */
	public E next() {
		E retVal = current.value;
		current = current.next;
		return retVal;
	}
	
	/** 
	 * Determine if there are more Nodes in the list to be reported.
	 */
	public boolean hasNext() {
		return (current != null);
	}

	/**
	 * Remove operation not supported for List objects.
	 */
	public void remove() {
		throw new UnsupportedOperationException ("remove not supported for ListIterator");
	}

}
