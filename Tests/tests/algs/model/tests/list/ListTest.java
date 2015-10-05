package algs.model.tests.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import algs.model.list.List;
import algs.model.list.Node;

import junit.framework.TestCase;

public class ListTest extends TestCase {

	@Test
	public void testIterator() {
		List<String> list = new List<String>();
		list.append("a");
		list.append("b");
		
		Iterator<String> it = list.iterator();
		assertTrue (it.hasNext());
		assertEquals ("a", it.next());
		assertTrue (it.hasNext());
		assertEquals ("b", it.next());
		assertFalse (it.hasNext());
		
		try {
			it.remove();
			fail ("Remove for ListIterator should throw exception.");
		} catch (UnsupportedOperationException uoe) {
			
		}
	}
	
	@Test
	public void testConcat() {
		List<String> list = new List<String>();
		List<String> list2 = new List<String>();
		
		list.concat(list2);
		assertEquals (0, list.size());
		list2 = new List<String>();
		list2.append("a");
		list2.append("b");
		
		list.append("c");
		
		list.concat(list2);
		assertEquals ("c", list.remove());
		assertEquals ("a", list.remove());
		assertEquals ("b", list.remove());
		
		// test concat on empty list
		list = new List<String>();
		list2 = new List<String>();
		list2.append("a");
		list2.append("b");
		list.concat(list2);
		assertEquals(2, list.size());
		assertEquals ("a", list.remove());
		assertEquals ("b", list.remove());
		assertEquals (0, list.size());
	}
	
			
	
	@Test
	public void testRemove() {
		List<String> list = new List<String>();
		
		try {
			list.remove();
			fail();
		} catch (NoSuchElementException nsme) {
			
		}
		
		list.append("Test");
		String s = list.remove();
		assertEquals ("Test", s);
		
		list.append ("First");
		list.append ("Second");
		assertEquals ("First", list.remove());
		assertEquals ("Second", list.remove());
		
	}
		
	
	@Test
	public void testNode() {
		List<String> list = new List<String>();
		list.append("First");
		list.append("Second");
		Node<String> n = list.head();
		Node<String> v = n.next();
		
		assertEquals ("First", n.value());
		assertEquals ("Second", v.value());
	}
	
	@Test
	public void testList() {
		List<String> list = new List<String>();

		
		assertTrue(list.isEmpty());
		assertEquals (0, list.size());
		assertEquals ("List[0]", list.toString());
		assertNull (list.contains("NOTHING"));
		assertNull (list.contains(null));
		
		try {
			list.append(null);
			fail ("Doesn't catch bad usage.");
		} catch (IllegalArgumentException e) {
		}
		
		assertNull(list.head());
		list.append("Test");
		assertEquals (1, list.size());
		assertNull (list.contains("NOTHING"));
		assertNotNull (list.contains("Test"));
		
		assertEquals ("List[1]: Test", list.toString());
		list.append("Nothing");
		assertEquals ("List[2]: Test", list.toString());
		assertNull (list.contains(null));
		
		String first = list.remove();
		assertEquals (first, "Test");
		assertEquals ("List[1]: Nothing", list.toString());
		
		int sz = list.size();
		list.concat(null);
		assertEquals (sz, list.size());
		
	}
}
