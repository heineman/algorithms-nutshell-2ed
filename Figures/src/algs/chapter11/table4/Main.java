package algs.chapter11.table4;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Compute Knuth's walk for n-queen problem estimation.
 * 
 * http://www.research.att.com/~njas/sequences/A000170
 * 
 * @author George Heineman
 */
public class Main {

	// actual values (0th entry is padded to match with index of n in the for loop. 
	static BigDecimal solutions[] = new BigDecimal[]{
		new BigDecimal("0"),
		new BigDecimal("1"),
		new BigDecimal("0"),
		new BigDecimal("0"),
		new BigDecimal("2"),
		new BigDecimal("10"),
		new BigDecimal("4"),
		new BigDecimal("40"),
		new BigDecimal("92"),
		new BigDecimal("352"),
		new BigDecimal("724"),
		new BigDecimal("2680"),
		new BigDecimal("14200"),
		new BigDecimal("73712"),
		new BigDecimal("365596"),
		new BigDecimal("2279184"),
		new BigDecimal("14772512"),
		new BigDecimal("95815104"),
		new BigDecimal("666090624"), 
		new BigDecimal("4968057848"),
		new BigDecimal("39029188884"),
	};

	// format table with comma groups
	static NumberFormat tf;

	/** Generate table. */
	public static void main (String []args) {
		tf = NumberFormat.getInstance();
		tf.setGroupingUsed(true);
		tf.setMaximumFractionDigits(0);

		int LOW_T = 1024; 
		int HIGH_T = 65536;
		int maxN = solutions.length-1;  // eliminate extra padding in the count...

		System.out.println("n\t\tActual number\tEst. T=1024\tEst. T=8192\tEst. T=65,536");
		
		for (int n = 1; n <= maxN; n++) {
			BigDecimal results[] = new BigDecimal[4];

			for (int idx=0, m = LOW_T; m <= HIGH_T; m *= 8, idx++) {
				results[idx] = new BigDecimal(0);

				for (int t = 0; t < m; t++) {
					// compute estimate of number of n-by-n queens
					Board b = new Board(n);

					int r = 0;
					BigDecimal lastEstimate = new BigDecimal(1);
					while (r < n) {
						int numChildren = b.numChildren(r);

						// no more to go, so no solution found.
						if (!b.randomNextBoard(r)) {
							lastEstimate = new BigDecimal(0);
							break; 
						}

						lastEstimate = lastEstimate.multiply(new BigDecimal(numChildren));
						r++;
					}

					results[idx] = results[idx].add(lastEstimate);
				}

				results[idx] = results[idx].divide(new BigDecimal(m));
			}

			System.out.println(tab(n) + tab(solutions[n]) + tab(results[0]) + tab(results[1]) + tab(results[2]));

		}
	}

	// helper method for tabbing issues
	private static String tab(long i) {
		String res = tf.format(i);
		if (res.length() < 9) {
			return res + "\t\t";
		} else {
			return res + "\t";
		}
	}

	// helper method for tabbing issues
	private static String tab(BigDecimal bd) {
		String res = tf.format(bd);
		if (res.length() < 9) {
			return res + "\t\t";
		} else {
			return res + "\t";
		}
	}
}
