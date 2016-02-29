package algs.example.chapter3;

import java.util.NoSuchElementException;


/**
 * Use divide and conquer structure to locate maximum element in an array.
 * 
 * @author George Heineman
 * @version 2.0, 5/12/15
 * @since 2.0
 */
public class MaxDivideConquer {
	
	static int numTrials = 40;
	
	public static void main(String[] args) {
		System.out.println("n\tRec.\tDirect");
		for (int size = 262144; size <= 8388608; size *= 2) {
			int[] intArray = new int[size];
			long totalIntTime = 0;
			long totalDirectTime = 0;
			
			// all in reverse order
			for (int trial = 0; trial < numTrials; trial++) {
				for (int i = 0; i < size; i++) {
					intArray[i] = i;
				}
				
				System.gc();
				long now = System.currentTimeMillis();
				int max = maxElement(intArray);
				long then = System.currentTimeMillis();
				totalIntTime += (then - now);

				System.gc();
				now = System.currentTimeMillis();
				int max2 = intArray[0];
				for (int i = 1; i < size; i++) {
					if (intArray[i] > max2) {
						max2 = intArray[i];
					}
				}
				then = System.currentTimeMillis();
				totalDirectTime += (then - now);
				
				if (max != max2) {
					throw new RuntimeException ("Results don't match");
				}
			}
			
			System.out.println(size + "\t" + (1.0*totalIntTime)/numTrials + "\t" + (1.0*totalDirectTime)/numTrials);
		}
	}
	
	/**
	 * Return largest element in the array using helper method for divide and conquer.
	 * 
	 * @param vals contains elements to be inspected.
	 * @return largest element in the array
	 */
	public static int maxElement (int[] vals) {
		if (vals.length == 0) {
			throw new NoSuchElementException("No Max Element in Empty Array.");
		}
		return maxElement(vals, 0, vals.length);
	}
	
	/**
	 * Return largest element in vals[left,right) using divide and conquer.
	 * 
	 * @param vals     contains elements to be inspected.
	 * @param left     left edge of range (inclusive)
	 * @param right    right edge of range (exclusive)
	 * @return largest element in the array
	 */
	static int maxElement (int[] vals, int left, int right) {
		if (right - left == 1) { 
			return vals[left];
		}
		
		// compute subproblems
		int mid = (left + right)/2;
		int max1 = maxElement(vals, left, mid);
		int max2 = maxElement(vals, mid, right);
		
		// Resolution: compute result from results of subproblems
		if (max1 > max2) { return max1; }
		return max2;		
	}
	
}
