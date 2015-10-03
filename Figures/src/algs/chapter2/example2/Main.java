package algs.chapter2.example2;

/** 
 * Launch application to exercise code in Example 2-2 and Example 2-3
 *  
 * @author George Heineman
 * @version 1.0, 7/17/08
 * @since 1.0
 */
public class Main {
	
	public static void add(int[] n1, int[] n2, int[] sum) {
		int position = n1.length-1;
		int carry = 0;
		while (position >= 0) {
			int total = n1[position] + n2[position] + carry;
			sum[position+1] = total % 10;
			if (total > 9) { carry = 1; } else { carry = 0; }
			position--;
		}
		
		sum[0] = carry;
	}
	
	public static void alt(int[] n1, int[] n2, int[] sum) {
		int position = n1.length;
		sum[position]=0; // prime the carry bit
		while (--position >= 0) {
			int total = n1[position] + n2[position] + sum[position+1];
			sum[position+1] = total % 10;
			sum[position] = total/10;
		}
	}
	
	public static void plus(int[] n1, int[] n2, int[] sum) {
		int position = n1.length;
		int carry = 0;
		while (--position >= 0) {
			int total = n1[position] + n2[position] + carry;
			if (total > 9) {
				sum[position+1] = total-10;
				carry = 1;
			} else {
				sum[position+1] = total;
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
	
	public static void main (String[] args) {
		// Trials
		int n = 256;
		int MAX_SIZE = 1048577;  // go up to 1048576
		int NUM_TRIALS = 10000;
		
		while (n < MAX_SIZE) {
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
				
				 // circular shift (n1 left, n2 right)
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
				
				 // circular shift (n1 left, n2 right)
                int c = n1[0];
                System.arraycopy(n1, 1, n1, 0, n-1);
                n1[n-1] = c;
                c = n2[n-1];
                System.arraycopy(n2, 0, n2, 1, n-1);
                n2[0] = c;				
			}
			long altE = System.currentTimeMillis();
			
			// PLUS
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);
			long plusS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				plus(n1,n2,sum);
				
				 // circular shift (n1 left, n2 right)
                int c = n1[0];
                System.arraycopy(n1, 1, n1, 0, n-1);
                n1[n-1] = c;
                c = n2[n-1];
                System.arraycopy(n2, 0, n2, 1, n-1);
                n2[0] = c;						
			}
			long plusE = System.currentTimeMillis();
			
			long baseLine = (baseE - baseS);
			System.out.println (n + ",Base:" + baseLine + ",ms.");
			System.out.println (n + ",Add*:" + (addE - addS-baseLine) + ",ms.");
			System.out.println (n + ",Alt*:" + (altE - altS-baseLine) + ",ms.");
			System.out.println (n + ",Plus*:" + (plusE - plusS-baseLine) + ",ms.");
			
			// advance
			n = n * 2;
		}
	}
}
