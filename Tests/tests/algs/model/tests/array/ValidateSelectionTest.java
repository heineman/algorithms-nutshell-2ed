package algs.model.tests.array;

import java.util.Comparator;

import junit.framework.TestCase;
import org.junit.Test;

import algs.model.array.Selection;

public class ValidateSelectionTest extends TestCase {
	
	/**
	 * Helper function to compare two arrays via equals method.
	 * 
	 * @param ar1    array containing set of objects
	 * @param ar2	 array containing set of objects
	 * @return       true if the arrays are of the same size and all objects are
	 *               properly .equals; false otherwise
	 */
	public static boolean compareArrays (Object ar1[], Object ar2[]) {
		if (ar1.length != ar2.length) { return false; }
		
		for (int i = 0; i < ar1.length; i++) {
			if (!ar1[i].equals(ar2[i])) {
				return false;
			}
		}
		
		// must be true
		return true;		
	}
	
	/** 
	 * Validate the minimum function.
	 */
	@Test
	public void testfindMinimum() {
		Integer vals[] = new Integer[] { 20, 40, 10, 50, 30, 22, 11, 99 };
		assertEquals (10, Selection.min(vals));
		
		assertEquals (10, Selection.min(new Integer[] { 10, 30, 50 }));  // try left one
		assertEquals (10, Selection.min(new Integer[] { 10, 30, 10 }));  // try right one
		
		try {
			Selection.min(null);
			fail ("min failed to detect null array.");
		} catch (NullPointerException npe) {
			
		}
		
		try {
			Selection.min(new Integer[0]);
			fail ("min failed to detect null array.");
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			
		}
	}
	
	
	/** 
	 * Validate the maximum function
	 */
	@Test
	public void testfindMaximum() {
		Integer vals[] = new Integer[] { 20, 40, 10, 50, 30, 22, 11, 99 };
		
		assertEquals (99, Selection.max(vals));
		
		assertEquals (90, Selection.max(new Integer[] { 90, 30, 50 }));  // try left one
		assertEquals (90, Selection.max(new Integer[] { 10, 30, 90 }));  // try right one

		try {
			Selection.max(null);
			fail ("max failed to detect null array.");
		} catch (NullPointerException npe) {
			
		}
		
		try {
			Selection.max(new Integer[0]);
			fail ("max failed to detect null array.");
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			
		}
	}
	
	/** 
	 * Test swap function.
	 */
	@Test
	public void testSwap() {
		Integer ar[] = new Integer[] { 20, 40, 10};
		
		assertEquals (20, (int) ar[0]);
		Selection.swap (ar, 0, 2);
		assertEquals (10, (int) ar[0]);
	}
	
	/**
	 * Test the selection function.
	 */
	/**
	 * 
	 */
	@Test
	public void testSelection() {
		Integer ar[] = new Integer[] { 20, 40, 10, 30, 50, 70 };
	
		assertEquals (10, Selection.select (ar, 1, 0, 5));
		assertEquals (20, Selection.select (ar, 2, 0, 5));
		assertEquals (30, Selection.select (ar, 3, 0, 5));
		assertEquals (40, Selection.select (ar, 4, 0, 5));
		assertEquals (50, Selection.select (ar, 5, 0, 5));		
		assertEquals (70, Selection.select (ar, 6, 0, 5));
		
		ar = new Integer[] { 10, 20, 30, 40, 50, 60 };
		ar = new Integer[] { 10, 20, 30, 40, 50, 60 };
		assertEquals (60, Selection.select (ar, 6, 0, 5));
		assertEquals (60, Selection.select (ar, 6, 0, 5, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
		// needed one for the inner if condition
		assertEquals (20, Selection.select (ar, 2, 0, 5, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));

		
		ar = new Integer[] { 60, 50, 40, 30, 20, 10};
		assertEquals (60, Selection.select (ar, 6, 0, 5));
		
		assertEquals (60, Selection.select (ar, 6, 0, 5, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
	}
	

	/**
	 * Test the qsort function.
	 * 
	 * Hardly what you would call "Comprehensive"!
	 */
	@Test
	public void testQsort() {
		Integer ar[] = new Integer[] { 70, 50, 40, 30, 20, 10 };
		Integer m[] = new Integer[] { 10, 20, 30, 40, 50, 70 };
	
		Selection.qsort(ar, 0, ar.length-1);
		assertTrue (compareArrays(ar,m));
		
		Selection.qsort(ar, 0, ar.length-1, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		});
		
		// test pivot algorithms.
		// public static int selectPivotIndex (Object ar[], int left, int right, ICompareTwo comparator) {
		ar = new Integer[] { 70, 50, 40, 30, 20, 10 };
		
		// in this list, 40 is the median [10,20,30] 40 [50, 70]
		assertEquals (2, Selection.selectPivotIndex(ar, 0, ar.length-1, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
		
		ar = new Integer[] { 10, 20, 30 };
		
		// in this list, 20 is the median [10] 20 [30]
		assertEquals (1, Selection.selectPivotIndex(ar, 0, ar.length-1, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
		
		ar = new Integer[] { 30, 10, 20 };
		
		// in this list, 20 is the median [10] 20 [30]
		assertEquals (2, Selection.selectPivotIndex(ar, 0, ar.length-1, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
		
		ar = new Integer[] { 20, 30, 10 };
		
		// in this list, 20 is the median [10] 20 [30]
		assertEquals (0, Selection.selectPivotIndex(ar, 0, ar.length-1, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
			
		}));
		
	}
	
	
}
