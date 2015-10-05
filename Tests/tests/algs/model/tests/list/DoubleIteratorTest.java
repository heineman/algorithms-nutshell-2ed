package algs.model.tests.list;

import java.util.Iterator;

import org.junit.Test;

import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;

import junit.framework.TestCase;

public class DoubleIteratorTest extends TestCase {

	@Test
	public void testIterator() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("First");
		list.insert("Second");
		list.insert("Third");
		DoubleNode<String> n = list.first();
		DoubleNode<String> v = n.next();
		
		assertEquals ("First", n.value());
		assertEquals ("Second", v.value());
		
		Iterator<String> it = list.iterator();
		try {
			it.remove();
			// fails
			fail("Iterator must fail-fast on multiple remove attempts");
		} catch (IllegalStateException ise) {
			
		}
		
		// remove in reverse order to showcase all functions.
		while (list.size() > 0) {
			it = list.iterator();
			int idx = list.size()-1;
			if (idx > 0) {
				while (idx-- > 0) {
					assertTrue(it.hasNext());
					it.next();
				}
				
				it.remove();
			} else {
				assertTrue (it.hasNext());
				it.next();
				it.remove();
			}
		}
		
		try {
			it.remove();
			// fails
			fail("Iterator must fail-fast on multiple remove attempts");
		} catch (IllegalStateException ise) {
			
		}
		
		
		assertEquals (0, list.size());
	}
	
}
