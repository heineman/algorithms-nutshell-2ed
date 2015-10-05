package algs.model.tests.heap;

import org.junit.Test;

import junit.framework.TestCase;
import algs.model.heap.BinaryHeap;

public class HeapTest extends TestCase {
	
	@Test
	public void test2() {
		int id;
		
		  BinaryHeap<Integer> heap = new BinaryHeap<Integer>(8);
		  assertTrue (heap.isEmpty());
		  heap.insert(0, 2);
		  assertFalse (heap.isEmpty());

		  heap.insert(1, Integer.MAX_VALUE);
		  heap.insert(2, Integer.MAX_VALUE);
		  heap.insert(3, Integer.MAX_VALUE);
		  heap.insert(4, Integer.MAX_VALUE);
		  heap.insert(5, Integer.MAX_VALUE);
		  heap.insert(6, Integer.MAX_VALUE);
		  heap.insert(7, 1);


		  id = (Integer) heap.smallestID();
		  assertEquals (7, id);
		  id = (Integer) heap.smallestID();
		  assertEquals (0, id);
		  
		  // try this one as well.
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertEquals (Integer.MAX_VALUE, heap.smallest());   //six of them!
		  assertTrue (heap.isEmpty());
	}
	
	@Test
	public void testHeap() {
		 BinaryHeap<Integer> heap = new BinaryHeap<Integer>(10);
		 assertTrue (heap.isEmpty());
		 
		 heap.insert (0, 10);
		 assertTrue (!heap.isEmpty());

		  heap.insert (1, 5);
		  assertEquals (5, heap.smallest());
		  assertEquals (10, heap.smallest());
		  assertTrue (heap.isEmpty());

		  heap.insert (2, 9);
		  heap.insert (3, 2);
		  heap.insert (4, 15);
		  heap.insert (5, 11);
		  heap.insert (6, 1);

		  assertEquals (1, heap.smallest());
		  assertEquals (2, heap.smallest());
		  heap.decreaseKey (4, 3);
		  assertEquals (3, heap.smallest());
		  assertEquals (9, heap.smallest());
		  assertEquals (11, heap.smallest());
		  assertTrue (heap.isEmpty());
	}

}
