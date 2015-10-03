/**
 * @file AdditionExample.java   Java Example showing four add implementations.
 * @brief
 * Code to show alternative implementations for adding two n-digit numbers.
 * 
 * @author George Heineman
 * @date 6/15/08
 */
public class AdditionExample {

    /** Output number (for debugging). */
	private static void output (int []n) {
		for (int i : n) {
			System.out.print(i);
		}
		System.out.println();
	}
	

    /** Add implementation number 1. */
	private static void add(int[] n1, int[] n2, int[] sum) {
		int b = n1.length-1;
		int carry = 0;
		while (b >= 0) {
			int s = n1[b] + n2[b] + carry;
			sum[b+1] = s % 10;
			if (s > 9) { carry = 1; } else { carry = 0; }
			b--;
		}
		
		sum[0] = carry;
	}
	
    /** Add implementation number 2. */
	private static void add2(int[] n1, int[] n2, int[] sum) {
		int b = n1.length-1;
		sum[b+1]=0;  // prime the carry bit
		while (b >= 0) {
			int s = n1[b] + n2[b] + sum[b+1];
			sum[b+1] = s % 10;
			if (s > 9) { sum[b] = 1; } 
			b--;
		}
	}
	
    /** Add implementation number 3. */
	private static void alt(int[] n1, int[] n2, int[] sum) {
		int b = n1.length;
		sum[b]=0; // prime the carry bit
		while (--b >= 0) {
			int s = n1[b] + n2[b] + sum[b+1];
			sum[b+1] = s % 10;
			sum[b] = s/10;
		}
	}
	
    /** Add implementation number 4. */
	private static void last(int[] n1, int[] n2, int[] sum) {
		int b = n1.length;
		int carry = 0;
		while (--b >= 0) {
			int s = n1[b] + n2[b] + carry;
			if (s > 9) {
				sum[b+1] = s-10;
				carry = 1;
			} else {
				sum[b+1] = s;
				carry = 0;
			}
		}
		
		sum[0] = carry;
	}
	
	/** 
	 * Generate random number of size n directly into num
	 * 
	 * @param num 
	 * @param n 
	 */
	public static void randomNumber (int[] num, int n) {
		for (int j = 0;j < n; j++) {
			num[j] = (int) (Math.random()*10);
		}
	}
	
    /** Launch this program by generating table. */
    public static void main (String []args) {
	new AdditionExample().generateTable();

    }

    /** Generate Table comparing each approach in time. */
	public void generateTable() {

		// Trials
	    long checkSum = 0;   // ensure no code is compiled away...
 	        int n = 256;
		int MAX_SIZE = 1048576;
		int NUM_TRIALS = 10000;
		
		while (n <= MAX_SIZE) {
			System.out.println ("Trying " + n + "...");
			// generate numbers and space for storage
			int[] n1 = new int[n];
			int[] n2 = new int[n];
			randomNumber(n1, n);
			randomNumber(n2, n);
			int[] sum = new int[n+1];
			
			int[] copy1 = new int[n];
			int[] copy2 = new int[n];
			System.arraycopy(n1, 0, copy1, 0, n);
			System.arraycopy(n2, 0, copy2, 0, n);
			
			// BASELINE
			System.gc();
			long baseS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				// NOP
 			        checkSum += n1[0];

				// circular shift (n1 left, n2 right)
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[n-1];
				System.arraycopy(n2, 0, n2, 1, n-1);
				n2[0] = c;
			}
			long baseE = System.currentTimeMillis();
			
			// ADD
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);
			long addS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				add(n1,n2,sum);
 			        checkSum += sum[0];
				
				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[n-1];
				System.arraycopy(n2, 0, n2, 1, n-1);
				n2[0] = c;
			}
			long addE = System.currentTimeMillis();
			
			// ALT
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);			
			long altS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				alt(n1,n2,sum);
 			        checkSum += sum[0];
				
				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[n-1];
				System.arraycopy(n2, 0, n2, 1, n-1);
				n2[0] = c;
			}
			long altE = System.currentTimeMillis();
			
			// LAST
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);
			long lastS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				last(n1,n2,sum);
 			        checkSum += sum[0];
				
				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[n-1];
				System.arraycopy(n2, 0, n2, 1, n-1);
				n2[0] = c;
			}
			long lastE = System.currentTimeMillis();
			
			long baseLine = (baseE - baseS);
			System.out.println (n + ",Base:" + baseLine + ",ms.");
			System.out.println (n + ",Add*:" + (addE - addS-baseLine) + ",ms.");
			System.out.println (n + ",Alt*:" + (altE - altS-baseLine) + ",ms.");
			System.out.println (n + ",Last*:" + (lastE - lastS-baseLine) + ",ms.");
			
			// advance
			n = n * 2;
		}
		System.out.println ("Checksum:" + checkSum);
	}
}
