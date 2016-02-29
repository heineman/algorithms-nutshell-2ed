package algs.chapter2.example1;

/** 
 * Launch application to exercise code in Example 2-1
 *  
 * @author George Heineman
 * @version 1.0, 7/17/08
 * @since 1.0
 */
public class Main {

	/** Was a maximum number printed out? */
	static boolean printed = false;
	
	/** print each one out? Only true for SMALL examples. */
	static boolean printAll = false;

	/** Compute number of turns to find n when it is guaranteed to be in range [low,high]. */
	public static int turns(int n, int low, int high) {
		int turns = 0;

		// Continue while there is a potential number to guess
		while (high >= low) { 
			// Prepare midpoint of [low,high] as the guess.
			turns++;
			int mid = (low + high) / 2;
			if (mid == n) {
				return turns;
			} else if (mid < n) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}

		return turns;
	}

	/** Launch application. */
	public static void main (String []args) {
		int BIGNUM = 1000000;
		int maxGuess = 20;

		int i, max = 0;
		float sum = 0;
		for (i = 1; i <= BIGNUM; i++) {
			int k = turns(i, 1, BIGNUM);
			if (printAll) {
				System.out.println(i + " guess in " + k + " turns.");
			}
			if (k > maxGuess) {
				System.err.println ("Failed to guess " + i + " in " + maxGuess + " turns or less (needed " + k + ")");
			} else if (k == maxGuess) {
				if (!printed) {
					printed = true;
					System.out.println(i + " needed " + maxGuess + " turns.");
				}
			}
			if (k > max) {
				max = k;
			}
			sum += k; 
		}

		System.out.println ("average number:" + sum/BIGNUM);
		System.out.println ("max number:" + max);
	}
}