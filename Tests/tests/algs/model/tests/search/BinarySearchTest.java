package algs.model.tests.search;

import org.junit.Test;

import algs.model.search.BinarySearch;
import junit.framework.TestCase;

/**
 * Test Binary Search
 */
public class BinarySearchTest extends TestCase {
	
	@Test
	public void testSimple() {
		BinarySearch<Integer> bs = new BinarySearch<Integer>();
		Integer[] collection = new Integer[] { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16 };
		for (int i = 1; i <= collection.length; i++) {
			assertTrue (bs.search(collection, i));
		}
		
		assertFalse (bs.search(collection, -2));
		assertFalse (bs.search(collection, 22));
		
		// just checking
		assertFalse (bs.search(collection, null));
	}
	
	// note that compare for String class returns negative and positive
	// numbers (not just -1 or +1) so we include its testing here.
	@Test
	public void testStrings() {
		BinarySearch<String> bs = new BinarySearch<String>();
		String[] collection = new String[] { 
				"1","10","11","12","13","14","15","16","2","3","4","5","6","7","8","9" };
		
		for (int i = 1; i <= collection.length; i++) {
			assertTrue (bs.search(collection, ""+i));
		}
		
		assertFalse (bs.search(collection, "-2"));
		assertFalse (bs.search(collection, "22"));
		
		// just checking
		assertFalse (bs.search(collection, null));
	}
}
