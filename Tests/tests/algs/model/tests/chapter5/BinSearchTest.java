package algs.model.tests.chapter5;

import org.junit.Test;

import algs.model.search.BinarySearch;

import junit.framework.TestCase;

public class BinSearchTest extends TestCase {

	@Test
	public void testBinary() {
		int ns[] = new int[] { 10000, 20000, 40000, 100000, 500000, 1000000 };
		
		for (int i = 0; i < ns.length; i++) {
			
			Integer ar[] = new Integer[ns[i]];
			
			// populate
			for (int j=0; j < ar.length; j++) {
				ar[j] = j+1;
			}
			
			// now binary search
			BinarySearch<Integer> bs = new BinarySearch<Integer>();
			
			// values to search.
			int v = 9;
			
			assertTrue(bs.search(ar, v));
		}
		
	}
	
}
