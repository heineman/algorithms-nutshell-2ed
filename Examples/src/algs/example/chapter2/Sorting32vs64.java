package algs.example.chapter2;

import java.util.Arrays;

/**
 * Show timing difference when sorting 32-bit int versus 64-bit long. 
 * 
 * Note that short-timing is really fast because of all of the duplicate values.
 * 
 * @author George Heineman
 * @version 2.0, 5/11/15
 * @since 2.0
 */
public class Sorting32vs64 {
	
	static int numTrials = 40;
	
	public static void main(String[] args) {
		System.out.println("N\tShort\tInt\tLong");
		
		for (int size = 262144; size <= 8388608; size *= 2) {
			int[] intArray = new int[size];
			long[] longArray = new long[size];
			short[] shortArray = new short[size];
			long totalIntTime = 0;
			long totalLongTime = 0;
			long totalShortTime = 0;
			
			for (int trial = 0; trial < numTrials; trial++) {
				for (int i = 0; i < size; i++) {
					shortArray[i] = (short) (Short.MIN_VALUE + (Math.random()*(2*Short.MAX_VALUE-1)));
					intArray[i] = (int) shortArray[i];
					longArray[i] = (long) intArray[i];
				}
				
				System.gc();
				long now = System.currentTimeMillis();
				Arrays.sort(shortArray);
				long then = System.currentTimeMillis();
				totalShortTime += (then - now);
				
				System.gc();
				now = System.currentTimeMillis();
				Arrays.sort(intArray);
				then = System.currentTimeMillis();
				totalIntTime += (then - now);

				System.gc();
				now = System.currentTimeMillis();
				Arrays.sort(longArray);
				then = System.currentTimeMillis();
				totalLongTime += (then - now);
			}
			
			System.out.println(size + "\t" + (1.0*totalShortTime)/numTrials + "\t" + (1.0*totalIntTime)/numTrials + "\t" + (1.0*totalLongTime)/numTrials);
		}
	}
}
