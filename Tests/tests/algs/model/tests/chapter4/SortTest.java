package algs.model.tests.chapter4;

import org.junit.Test;

import algs.model.array.Selection;
import algs.model.heap.HeapSort;

import junit.framework.TestCase;

public class SortTest extends TestCase {

	@Test
	public void testHeapSortBaseCase() {
		// copy
		Integer []copy = new Integer[8];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = new Integer(i);
		}
		
		// baseline for sorting. Should return false
		HeapSort<Integer> hs = new HeapSort<Integer>();
		assertFalse (hs.sort(copy, 4, 4));
		
		// this should also fail
		assertFalse (hs.sort(copy, 4, 4, null));
	}
	
	@Test
	public void testComparableSort() {
		for (int t = 0; t < 10; t++) {
			Integer []ivs = new Integer[16];
			
			for (int i = 0; i < ivs.length; i++) {
				ivs[i] = (int) (Math.random()*1000);
			}
			
			// copy
			Integer []copy = new Integer[ivs.length];
			for (int i = 0; i < copy.length; i++) {
				copy[i] = ivs[i];
			}
			
			// baseline for sorting.
			assertTrue (new HeapSort<Integer>().sort(copy, 0, ivs.length-1));
			
			// check against Qsort.
			Selection.qsort(ivs, 0, ivs.length-1);
			
			// compare
			for (int i = 0; i < ivs.length; i++) {
				if (!ivs[i].equals(copy[i])) {
					fail("incomparable sort methods.");
				}
			}
		}
	}
	
}
