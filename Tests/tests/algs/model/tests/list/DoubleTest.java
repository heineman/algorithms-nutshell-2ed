package algs.model.tests.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import junit.framework.TestCase;

public class DoubleTest extends TestCase {


	@Test
	public void testInsertComparator() {
		// Use the built in scoring as our comparator.
		Comparator<String> comparator = new Comparator<String>() {
			// o1 is the element IN the list. o2 is the new element to be added.
			// if you want ASCENDING, then ensure that you return the 
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
		
		// default to prepend.
		DoubleLinkedList<String> list2 = new DoubleLinkedList<String>(null);
		String s1 = new Integer(123).toString();
		String s2 = new Integer(122+1).toString();
		list2.insert(s1);
		list2.insert(s2);
		assertTrue (s2 == list2.first().value());
		
		DoubleLinkedList<String> list = new DoubleLinkedList<String>(comparator);
		
		list.insert("First");
		assertEquals ("First", list.first().value());
		assertNotNull (list.contains("First"));
		assertNull (list.contains("NOTHING"));
		assertNull (list.contains(null));
		
		list.insert("Bed");
		System.out.println (list);
		assertEquals ("Bed", list.first().value());
		
		list.insert("Table");
		assertEquals ("Table", list.last().value());
		DoubleNode<String> n = list.last();
		
		list.insert("Step");
		assertEquals ("Step", n.prev().value()); 
		assertNull (list.contains("NOTHING"));
		assertNotNull (list.contains("Bed"));
		assertNotNull (list.contains("Step"));
	}
	
	
	@Test
	public void testConcat() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		DoubleLinkedList<String> list2 = new DoubleLinkedList<String>();
		
		list.validate();
		
		list.concat(list2);
		assertEquals (0, list.size());
		list2 = new DoubleLinkedList<String>();
		list2.insert("a");
		list2.insert("b");
		
		list.insert("c");
		
		int sz = list.size();
		DoubleLinkedList<String> dll = new DoubleLinkedList<String>();
		list.concat(dll);
		assertEquals (sz, list.size());
		
		list.concat(list2);
		assertEquals ("c", list.removeFirst());
		assertEquals ("a", list.removeFirst());
		assertEquals ("b", list.removeFirst());
		
		assertEquals ("DoubleLinkedList[0]", list.toString());
		
	}
	
	@Test
	public void testStrictRemove() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		
		String s = null;
		list.remove(s);
		list.insert("Testing");
		s = list.contains("Testing");
		assertTrue (list.remove(s));
		
		// must actually be == to the object in the list, not just .equals.
		list.insert (new Integer (123).toString());
		assertFalse (list.remove(new Integer (123).toString()));
		
		String s2 = new String ("Bed");
		list.insert (s2);
		list.insert ("Step");
		list.insert ("Table");
		// must actually be == to the object in the list, not just .equals.
		list.insert (new Integer (123).toString());
		assertFalse (list.remove(new Integer (123).toString()));
		assertTrue (list.remove(s2));
		
	}
	
	
	@Test
	public void testRemove() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		
		try {
			list.removeFirst();
			fail();
		} catch (NoSuchElementException nsme) {
			
		}
		
		list.insert("Test");
		String s = list.removeFirst();
		assertEquals ("Test", s);
		
		list.insert ("First");
		list.insert ("Second");
		assertEquals ("First", list.removeFirst());
		assertEquals ("Second", list.removeFirst());
		assertEquals (0, list.size());
		
		list.insert ("First");
		list.insert ("Second");
		assertEquals ("Second", list.removeLast());
		assertEquals ("First", list.removeLast());
		assertEquals (0, list.size());
		
		// verify we have tested remove last
		list.insert ("First");
		list.insert ("Second");
		assertTrue (list.remove("Second"));
		assertFalse(list.remove("Second"));
	}
		
	@Test
	public void testCircular() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("First");
		list.insert("Second");
		DoubleNode<String> n = list.first();
		DoubleNode<String> v = n.next();
		
		assertEquals ("[First]", n.toString());
		
		assertEquals (v.prev(), n);
		assertEquals (n.next(), v);
		
		v.next(n);
		
		try {
			list.validate();
			fail("List is circular and not detected!");
		} catch (IllegalStateException ise) {
			
		}
	}
	
	@Test
	public void testNode() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("First");
		list.insert("Second");
		DoubleNode<String> n = list.first();
		DoubleNode<String> v = n.next();
		
		assertEquals ("First", n.value());
		assertEquals ("Second", v.value());
		
		list.insert("Third");
		DoubleNode<String> t = list.last();
		assertEquals ("Third", t.value());
		
		t.prev(n);
		n.next(t);
		list.validate();
		
		assertEquals (2, list.size());
		assertEquals ("First", list.first().value());
		assertEquals ("Third", list.last().value());
	}
	
	@Test
	public void testList() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();

		try {
			list.removeFirst();
			fail ("Doesn't catch bad usage.");
		} catch (NoSuchElementException e) {
		}
		
		try {
			list.removeLast();
			fail ("Doesn't catch bad usage.");
		} catch (NoSuchElementException e) {
		}
		
		assertTrue(list.isEmpty());
		assertEquals (0, list.size());
		assertEquals ("DoubleLinkedList[0]", list.toString());
		
		try {
			list.insert(null);
			fail ("Doesn't catch bad usage.");
		} catch (IllegalArgumentException e) {
		}
		
		assertNull(list.first());
		list.insert("Test");
		assertEquals (1, list.size());
		
		assertEquals ("DoubleLinkedList[1]: Test", list.toString());
		list.insert("Nothing");
		assertEquals ("DoubleLinkedList[2]: Test", list.toString());
		
		String first = list.removeFirst();
		assertEquals (first, "Test");
		assertEquals ("DoubleLinkedList[1]: Nothing", list.toString());
		
		int sz = list.size();
		list.concat(null);
		assertEquals (sz, list.size());
	}
	

	@Test
	public void testIterator() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("Test");
		for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
			assertEquals ("Test", it.next());
		}
	}
		
	
	@Test
	public void testArrayStorage() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		assertTrue (list.isEmpty());
		list.insert("Test");
		assertFalse (list.isEmpty());
		String[] result = list.toArray(new String[1]);
		assertEquals (1, result.length);
		assertEquals ("Test", result[0]);
		
		list = new DoubleLinkedList<String>();
		list.insert("Test");
		result = new String[] { "a", "b", "c", "d" };
		list.toArray(result);
		assertEquals (4, result.length);
		assertEquals ("Test", result[0]);
		assertTrue (result[1] == null); // other values are untouched.
	}
	
	@Test
	public void testBoundaries() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		assertFalse (list.remove("Test")); // list is empty.
		
		assertTrue (list.contains("Test") == null);
	}
	
	@Test
	public void testValidateSmallCycle() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("Test");
		list.insert("Second");
		DoubleNode<String> first = list.first();
		DoubleNode<String> second = list.last();
		
		// create cycle
		second.next(first);
		
		try {
			list.validate();
			fail ("Failed to detect cycle");
		} catch (IllegalStateException ise) {
			// success
		}
	}
	
	@Test
	public void testSeparateComparator() {
		Comparator<String> comp = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
			
		};
		DoubleLinkedList<String> list = new DoubleLinkedList<String>(comp);
		list.insert("C");
		list.insert("A");
		list.insert("M");
		list.insert("T");
		list.insert("D");
		
		String[] result = list.toArray(new String[5]);
		assertEquals ("A", result[0]);
		assertEquals ("C", result[1]);
		assertEquals ("D", result[2]);
		assertEquals ("M", result[3]);
		assertEquals ("T", result[4]);
	}
	
	
	@Test
	public void testValidateLongerCycle() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("Test");
		list.insert("Second");
		DoubleNode<String> first = list.first();
		DoubleNode<String> second = list.last();
		list.insert("third");
		list.insert("fourth");
		list.insert("fifth");
		
		// create cycle
		second.next(first);
		
		try {
			list.validate();
			fail ("Failed to detect cycle");
		} catch (IllegalStateException ise) {
			// success
		}
	}
	
	@Test
	public void testRemoveOddsAndEnds() {
		DoubleLinkedList<String> list = new DoubleLinkedList<String>();
		list.insert("Test");
		
		DoubleNode<String> n = list.first();
		list.remove(n);
		assertTrue (list.isEmpty());
		
		list = new DoubleLinkedList<String>();
		list.insert("Test");
		list.insert("third");
		list.insert("DONE");
		n = list.first();
		DoubleNode<String> n2 = n.next();
		DoubleNode<String> n3 = list.last();
		
		// unrelated list...
		DoubleLinkedList<String> list2 = new DoubleLinkedList<String>(); 
		try {
			list2.remove(n);
			fail ("DoubleLinkedList shouldn't handle removal of other lists' head.");
		} catch (NullPointerException npe) {
			
		}
		
		try {
			list2.remove(n3);
			
		} catch (NullPointerException nsee) {
			fail ("DoubleLinkedList should handle removal of other lists' tail.");
		}
		
		// unable to detect. Hope for the best. This actually modifies list!
		list2.remove(n2);
		System.out.println ("Be Careful when using DoubleLinkedList.remove(Node).");
		assertEquals (-2, list2.size());  // HAH! list2 was empty and then we "removed" two elements.
		assertEquals (3, list.size());    // although this is still fine.
		
	}
}
