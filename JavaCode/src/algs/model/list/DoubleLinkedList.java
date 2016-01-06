package algs.model.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Maintain doubly-linked list of entities.
 * <p>
 * If no Comparator is provided when the {@link DoubleLinkedList} object was constructed,
 * then insert degrades to append.
 * <p>
 * Note that using append(E) voids the sorted property of this list
 * that can be maintained by insert(E) and users should not use both
 * of these methods on the same object.
 * 
 * @param <E>   Base element type stored with the nodes. 
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DoubleLinkedList<E> implements Iterable<E> {
	/** First in list. */
	DoubleNode<E>   head;
	
	/** Last in list. */
	DoubleNode<E>   last;
	
	/** Number in list. */
	int size;

	/** Comparator for the basic elements. */
	final Comparator<E> comparator;
		
	/** 
	 * Return first element in list. 
	 *
	 * @return first element in list.
	 */
	public DoubleNode<E> first() {
		return head;
	}
	
	/** 
	 * Return last element in list. 
	 *
 	 * @return last element in list.
 	 */
	public DoubleNode<E> last() {
		return last;
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
	 * @return  size of the linked list.
	 */
	public int size() {
		return size;
	}
	
	/** Construct double linked list with no comparator (defaults to append on insert). */
	public DoubleLinkedList () {
		comparator = null;
		size = 0;
	}
	
	/** 
	 * Return iterator to elements in the list. 
	 *
	 * @return iterator for elements in the linked list.
	 */
	public Iterator<E> iterator () {
		return new DoubleLinkedListIterator<E> (this);
	}
	
	/**
	 * Construct double linked list with comparator. 
	 * <p>
	 * If the comparator always returns '+1' on all inputs, then this list will prepend on insert.
	 * If the comparator always returns '-1' on all inputs, then this list will append on insert.
	 * <p>
	 * The comparator compares E o1 and E o2 where o1 points to the value already in the list, and o2
	 * points to the value that desires to be added. While compare return -1, the pointer in the list
	 * will advance. Once compare returns +1, then the value will be inserted just ahead of the node
	 * pointed to when the comparison is invoked.
	 * <p>
	 * To have an ascending list, therefore, you must make sure that your compare returns a negative number
	 * while o1 is &lt; o2, and returns a positive number while o1 is &gt; o2. Note that when o1.equals(o2), then 
	 * pointer will advance so this newly added item will appear after other items that .equals() that object.  
	 * <pre><code>
	 *    Comparator&lt;E&gt; comparator = new Comparator&lt;E&gt;() {
	 *       public int compare(E o1, E o2) {
	 *         return o1.compareTo(o2);
	 *       }
	 *    };
	 * </code></pre>
	 * 
	 * @param comp    The comparator function. If null, then we default to prePend.
	 */
	public DoubleLinkedList (Comparator<E> comp) {
		if (comp == null) {
			comparator = new Comparator<E>() {
				public int compare(E o1, E o2) {
					return +1;
				}		
			};
		} else {
			comparator = comp;
		}
		size = 0;
	}
	
	/**
	 * If links have been manually modified, then we must validate the size
	 * is properly set and there is no circular link.
	 *<p>
	 * This method, typically, is never called unless the user is performing
	 * some advanced pointer modifications.
	 */
	public void validate() {
		int newSize = 0;
		DoubleNode<E> p = head;
		DoubleNode<E> check = head;
		
		// iterate through entire list, and advance check by two, while p is advanced by one.
		// if they ever are EQUAL then we have an infinite list!
		while (p != null) {
			p = p.next;
			newSize++;
			
			if (check != null) {
				check = check.next;
				if (check != null) { check = check.next; }
			}
			
			if (check != null && check == p) {
				throw new IllegalStateException("Double Linked List has circular reference.");
			}
		}
		
		size = newSize;
	}
	
	/**
	 * Insert an entity based upon the comparator.
	 * <p>
	 * If there is no comparator selected, then insert becomes a simple 'append'.
	 * 
	 * @param e   The Element to be inserted.
	 */
	public void insert(E e) {
		if (e == null) {
			throw new IllegalArgumentException ("Cannot insert 'null' into DoubleLinkedList.");
		}
		
		if (comparator == null) {
			append(e);
			return;
		}
		
		DoubleNode<E> newNode = new DoubleNode<E>(e);
		
		if (head == null) {
			head = newNode;
			last = newNode;
			size++;
			return;
		}
		
		size++;
		DoubleNode<E> p = head;
		while (p != null) {
			int cmp = comparator.compare(p.value, e);
		
			if (cmp <= 0) {
				// advance
				p = p.next;
			} else {
				// must drop just before p.
				if (p == head) {
					// this must become the new head in the list
					p.prev = newNode;
					newNode.next = p;
					head = newNode;
					return;
				} else {
					newNode.prev = p.prev;
					p.prev.next = newNode;
					p.prev = newNode;
					newNode.next = p;
					return;
				}
			}
		}
			
		// If we get here, then we must drop after the last one.
		last.next = newNode;
		newNode.prev = last;
		last = newNode;
	}

	/** 
	 * Determine membership by returning element if found. 
	 * <p>
	 * Check for membership using E.equals(E) rather than the default == method. Note, however, 
	 * the the remove(E) method removes solely based on ==.
	 * 
	 * By definition, contains(null) is null.
	 * 
	 * @param e     element to be sought
	 * @return      element in list matching e.
	 */
	public E contains (E e) {
		if (e == null) return null;
		
		if (head == null) { return null; }
		
		DoubleNode<E> n = head;
		while (n != null) {
			if (n.value.equals(e)) {
				return n.value;
			}
			
			n = n.next;
		}
		
		return null;
	}
	
	
	/**
	 * Append the given value to the end of this list.
	 * <p>
	 * Only used by insert when comparator is null.
	 * 
	 * @param e    element to be appended to the end of the list.
	 */
	private void append(E e) {
		if (head == null) {
			head = last = new DoubleNode<E>(e);
		} else {
			DoubleNode<E> newNode = new DoubleNode<E>(e);
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
		}
		
		size++;
	}
	
	/**
	 * Concatenate the two lists. 
	 * <p>
	 * If list is null, then no operation (or error) occurs. Note that concatenating
	 * list A to B does not affect the passed in list parameter. Thus you may end up
	 * with some interesting intermingled objects if you are not careful.
	 * 
	 * @param list    List to be appended to the end.
	 */
	public void concat(DoubleLinkedList<E> list) {
		// silently ignore requests to concatenate null
		if (list == null) {
			return;
		}
		
		if (head == null) {
			head = list.first();
			last = list.last();
			return;
		}
		
		if (list.head == null) {
			return;
		}
		
		DoubleNode<E> newTail = list.last();
		list.head.prev = last;
		last.next = list.head;
		last = newTail;
		
		size += list.size;
	}
	
	/** 
	 * Given DoubleNode already known to exist in the list, remove it.
	 * <p>
	 * USE WITH CARE! Then ask yourself if you really need to use it! 
	 * <p>
	 * Sanity Check: Make sure that you remove a node that already exists within
	 * the list! No check is made to ensure that the given node already exists
	 * within the list. If this happens, may throw
	 * {@link NullPointerException}NullPointerException or create a list whose
	 * size is negative.
	 *
	 * @exception NullPointerException if node doesn't already belong to the list.
	 * @param  node    the node to be snipped out of the list.
	 * @return         true if node is in the list; false otherwise
	 */
	public boolean remove (DoubleNode<E> node) {
		if (head == node) {
			removeFirst();
			return true;
		}
		
		if (last == node) {
			removeLast();
			return true;
		}
		
		DoubleNode<E> prev = node.prev;
		
		prev.next = node.next;
		if (prev.next != null) {
			prev.next.prev = prev;
		}
		
		size--;
		return true;
	}
	
	/** 
	 * This will remove given element known to already be in the list. 
	 * <p>
	 * Comparison based solely on '=='. This is safer than removing a node.
	 * 
	 * @param e    the element to be removed from the list.
	 * @return     true if the element was returned; false otherwise.
	 */
	public boolean remove (E e) {
		if (e == null) { return false; }
		if (head == null) { return false; }   // sanity check.
		
		if (head.value == e) {
			removeFirst();
			return true;
		}
		
		if (last.value == e) {
			removeLast();
			return true;
		}
		
		// not in the list.
		if (head.next == null) { return false; }
		
		DoubleNode<E> prev = head;
		DoubleNode<E> n = head.next;
		
		// Find it in the list, and remove.
		while (n != null) {
			if (n.value == e) {
				prev.next = n.next;
				if (prev.next != null) {
					prev.next.prev = prev;
				}
				
				size--;
				return true;
			}
			
			n = n.next;
			prev = prev.next;
		}
		
		return false;
	}
	
	/** 
	 * Remove first element without altering sort order. 
	 *
	 * @return    first element in the linked list.
	 */
	public E removeFirst () {
		if (head == null) {
			throw new NoSuchElementException ("Nothing in List");
		}
		
		E value = head.value;
		
		head = head.next;
		if (head != null) {
			head.prev = null;
		} else {
			last = null;  // emptied out the list!
		}
		
		size--;
		return value;
	}
	
	/** 
	 * Remove last element without altering sort order. 
	 *
	 * @return  tail of the list
	 */
	public E removeLast () {
		if (head == null) {
			throw new NoSuchElementException ("Nothing in List");
		}
		
		E value = last.value;
		
		last = last.prev;
		if (last != null) {
			last.next= null;
		} else {
			head = null;  // emptied out the list!
		}
		
		size--;
		return value;
	}

	/**
	 * Useful string for debugging. 
	 * 
	 * @return meaningful string representation.
	 */
	public String toString() {
		if (head == null) { return "DoubleLinkedList[0]"; }
		
		return "DoubleLinkedList[" + size + "]: " + head.value.toString(); 
	}

	/**
	 * Return list in order as an array.
	 * 
	 * If not enough room is available in storage, an Exception is thrown. If there is 
	 * excess storage, then the contents of the first unneeded position in storage will be set to null.
	 * 
	 * @param storage    initial storage to use when converting list into array.
	 * @return   array representing the contents of the double linked list.
	 * @since 2.0
	 */
	public E[] toArray(E[] storage) {
		int idx = 0;
		DoubleNode<E> ptr = head;
		while (ptr != null) {
			storage[idx++] = ptr.value;
			ptr = ptr.next;
		}
		if (idx < storage.length) {
			storage[idx] = null;
		}
		
		return storage;
	}
}
