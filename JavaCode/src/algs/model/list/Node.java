package algs.model.list;

/**
 * Node in a singly-linked list.
 *  
 * @param <E> underlying parameterization of node
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Node<E> {

	/** Value stored by node.. */
	final E value;
	
	/** Next. */
	Node<E>  next;
	
	/** 
	 * Node constructed with value.
	 * 
	 * @param e  value to store with node.
	 */
	public Node (E e) {
		value = e;
	}

	/**
	 * Return the value. 
	 *
	 * @return value associated with node.
	 */
	public E value() {
		return value;
	}
	
	/**
	 * Return the next one in the list. 
	 * 
	 * @return next node in the list.
	 */
	public Node<E> next() {
		return next;
	}

}
