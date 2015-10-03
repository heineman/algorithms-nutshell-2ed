package algs.chapter11.figure1;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import algs.model.IPoint;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.MultiThreadQuickSort;
import algs.model.data.points.UniformGenerator;
import algs.model.tests.common.TrialSuite;

/**
 * Compare execution of various multithreaded Quicksort implementations.
 * 
 * RandomSelector can be used instead of LastSelector but it increases costs noticeably because
 * of the extra computations, which don't actually improve the results in the end.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class GenerateTable  {
	/** Numbers in range 1 .. 16777216 */
	static int BASE = 16777216;

	/**
	 * Change the number of helper threads inside to try different configurations.
	 * <p>
	 * Set to NUM_THREADS by default.
	 * 
	 * @param size              size of array to construct
	 * @param set               record times using this suite set
	 * @param pts               generated points whose (x,y) vals are used in input set
	 * @param selector          which pivot index method to use.
	 */
	public static void runTrialParallel (int size, TrialSuite set, IPoint[]pts, IPivotIndex selector,
			int numThreads, int ratio) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}

		// default
		MultiThreadQuickSort<Integer> qs = new MultiThreadQuickSort<Integer>(ar);
		qs.setPivotMethod(selector);
		qs.setNumberHelperThreads(numThreads);
		qs.setThresholdRatio(ratio);

		System.gc();
		long start = System.currentTimeMillis();
		qs.qsort(0,size-1);
		long end = System.currentTimeMillis();
		set.addTrial(size, start, end);

		for (int i = 0; i < ar.length-1; i++) {
			assert (ar[i] <= ar[i+1]);
		}		
	}

	/**
	 * Actual driver which generates a bunch of tables. Interesting to 
	 * compare selectors of ('first' and 'random') especially with multiple
	 * threads. It turns out that the cost of using a random-number computation 
	 * is noticeable.
	 */
	public static void main (String[] args) {
		NumberFormat format =  DecimalFormat.getInstance();
		format.setMinimumFractionDigits(2);
		
		int NUM_TRIALS = 10;

		UniformGenerator ug = new UniformGenerator();
		TrialSuite multiThreaded = new TrialSuite();

		int size = 1048576;
		int end = 10;
		Float[][] table = new Float[end+1][end];
		
		for (int nt = 0; nt < end; nt++) {
			for (int r = 1; r < end; r++) { 
				for (int t = 0; t < NUM_TRIALS; t++) {
					// create random arrays of given SIZE/2 since we take x and y coordinates separately
					IPoint[]pts = ug.generate(size/2);  
					runTrialParallel (size, multiThreaded, pts, new LastSelector(), nt, r);
				}
				table[nt][r-1] = Float.valueOf(multiThreaded.getAverage(size));
			}
		}
		
		// last one always
		System.out.print(",");
		for (int r = 1; r < end; r++) {
			System.out.print(r + ",");
		}
		System.out.println();
		for (int nt = 0; nt < end; nt++) {
			System.out.print(nt + ",");
			for (int r = 1; r < end; r++) { 
				System.out.print(format.format(table[nt][r-1]));
				System.out.print(",");
			}
			System.out.println();
		}
	}
}
