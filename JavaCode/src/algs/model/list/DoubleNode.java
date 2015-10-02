package algs.model.list;


/**
 * Double Linked list of elements parameterized by class E.
 * 
 * @param <E>    Type of the values stored by the underlying nodes.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DoubleNode<E> {
	/** Prev in list. */
	DoubleNode<E>  prev;
	
	/** Value in this list. */
	final E value;
	
	/** Prev in list. */
	DoubleNode<E>  next;
		
	/** 
	 * Construct node from the given element.
	 * 
	 * @param e   Element to store with the node.
	 */
	public DoubleNode (E e) {
		this.value = e;
	}
	
	/**
	 * Return value stored with the node. 
	 *
	 * @return   value stored with node.
	 */
	public E value() {
		return value;
	}
	
	/** 
	 * Return previous. 
	 *
	 * @return  previous node.
	 */
	public DoubleNode<E> prev() {
		return prev;
	}
	
	/** 
	 * Return next
	 *
	 * @return  next node.
	 */
	public DoubleNode<E> next() {
		return next;
	}
	
	/** 
	 * Return meaningful string. 
	 *
	 * @return   meaningful string representation.
	 */
	public String toString () {
		return "[" + value + "]";
	}

	/**
	 * Modifies the previous link for this node.
	 * <p>
	 * This is a dangerous operation and exposed only because algorithms
	 * often must make specific changes whose correctness is known only to them.
	 * <p>
	 * If you modify this link directly, then the DoubleLinkedList may incorrectly
	 * have the number of items in the list. Make sure you call 'resetSize' after
	 * making any specific changes using this method.
	 * <p>
	 * You have been warned.
	 * 
	 * @param p   new value to use as previous.
	 */
	public void prev(DoubleNode<E> p) {
		prev = p;		
	}
	
	/**
	 * Modifies the next link for this node.
	 * <p>
	 * This is a dangerous operation and exposed only because algorithms
	 * often must make specific changes whose correctness is known only to them.
	 * <p>
	 * If you modify this link directly, then the DoubleLinkedList may incorrectly
	 * have the number of items in the list. Make sure you call 'resetSize' after
	 * making any specific changes using this method.
	 * <p>
	 * You have been warned.
	 * 
	 * @param n    new value to use as next
	 */
	public void next(DoubleNode<E> n) {
		next = n;		
	}
}