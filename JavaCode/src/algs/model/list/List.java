package algs.model.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * List of objects.
 * 
 * @param <E> the underlying Node parameterization
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class List<E> implements Iterable<E> {

	/** Head. */
	private Node<E> head;
	
	/** Tail. */
	private Node<E> tail;
	
	/** Number of elements in list. */
	int size;
	
	/** Construct an empty list. */
	public List() {
		head = tail = null;
		size = 0;
	}
	
	/** 
	 * Return whether the list is empty. 
	 *
	 * @return true if list is empty; false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/** 
	 * Return size of the list. 
	 *
	 * @return size of the list
	 */
	public int size() {
		return size;
	}
	
	/** 
	 * Return head of the list. 
	 *
	 * @return  first Node in the list.
	 */
	public Node<E> head() {
		return head;
	}
	
	/** 
	 * Append element to the end of the list.
	 * 
	 * @param e    element to be appended.
	 */
	public void append (E e) {
		if (e == null) {
			throw new IllegalArgumentException ("Unable to append 'null' to a list.");
		}
		
		Node<E> node = new Node<E> (e);
		
		if (head == null) {
			head = tail = node;
		} else {
			tail.next = node;
			tail = tail.next;
		}
		
		size++;
	}
	
	/** 
	 * Determine membership by returning element if found. 
	 * <p>
	 * We return the object in the list, rather than boolean, since complex 
	 * objects that meet 'equals' may, in fact, contain additional information
	 * and the external code might want to have the actual object in the list.
	 * 
	 * @param e    sought for object.
	 * @return     element E that matches target
	 */
	public E contains (E e) {
		if (head == null) { return null; }
		
		Node<E> n = head;
		while (n != null) {
			if (n.value.equals(e)) {
				return n.value;
			}
			
			n = n.next;
		}
		
		return null;
	}
	
	/** 
	 * Remove element from the front of the list and return it.
	 * 
	 * @exception    NoSuchElementException if list is empty.
	 * @return       first element E in the list.
	 */
	public E remove () {
		if (head == null) {
			throw new NoSuchElementException ("Nothing in List");
		}
		
		E result = head.value;
		
		// only one in the list? Take care of it.
		if (head == tail) {
			head = tail = null;
		} else {
			// simply update head.
			head = head.next;
		}
		
		size--;
		return result;
	}

	/**
	 * Concatenate a list to the end of our list. 
	 * <p>
	 * If list is null, then no operation (or error) occurs. Note that concatenating
	 * list A to B does not affect the passed in list parameter. Thus you may end up
	 * with some interesting intermingled objects if you are not careful.
	 * 
	 * @param list    List to be concatenated to the end of the list.
	 */
	public void concat(List<E> list) {
		// silently ignore requests to concatenate null or empty lists.
		if (list == null || list.size == 0) {
			return;
		}
		
		if (head == null) {
			head = list.head;
			tail = list.tail;
			size += list.size;
			return;
		}
		
		// attach in
		Node<E> newTail = list.tail;
		tail.next = list.head;
		tail = newTail;
		
		size += list.size;
	}

	/** 
	 * String representation. 
	 * 
	 * @return  meaningful string representation of object.
	 */
	public String toString() {
		if (head == null) { return "List[0]"; }
		
		return "List[" + size + "]: " + head.value.toString(); 
	}

	/** 
	 * Return iterator over the list. 
	 *
	 * @return iterator for elements in the list.
	 */
	public Iterator<E> iterator() {
		return new ListIterator<E>(this);
	}
}