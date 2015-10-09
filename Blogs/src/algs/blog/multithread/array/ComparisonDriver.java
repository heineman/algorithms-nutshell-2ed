package algs.blog.multithread.array;

import algs.model.IPoint;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.RandomSelector;
import algs.model.data.points.UniformGenerator;
import algs.model.tests.common.TrialSuite;
import algs.model.tests.common.TrialSuiteHelper;

/**
 * Compare execution of various quicksort implementations for this month's blog.
 * 
 * @author George Heineman
 * @version 1.0, 6/1/09
 */
public class ComparisonDriver  {
	/** Numbers in range 1 .. 16777216 */
	static int BASE = 16777216;

	/** Number of threads to allow. */
	static int NUM_THREADS = 1;
	
	/**
	 * Run a traditional quick sort implementation.
	 * <p>
	 * All incoming points are assumed to be drawn from the unit square.
	 * 
	 * @param size              size of array to construct
	 * @param set               record times using this suite set
	 * @param pts               generated points whose (x,y) vals are used in input set
	 * @param selector          which pivot index method to use.
	 */
	public static void runTrialNormal (int size, TrialSuite set,
			IPoint[]pts, IPivotIndex selector) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}

		// default
		algs.model.array.QuickSort<Integer> qs = new algs.model.array.QuickSort<Integer>(ar);
		qs.setPivotMethod(selector);
		
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
	 * Run a quick sort implementation using a fixed single thread helper.
	 * <p>
	 * All incoming points are assumed to be drawn from the unit square.
	 * 
	 * @param size              size of array to construct
	 * @param set               record times using this suite set
	 * @param pts               generated points whose (x,y) vals are used in input set
	 * @param selector          which pivot index method to use.
	 * @param ratio             ratio for setting threshold
	 */
	public static void runTrialOneHelper (int size, TrialSuite set,
			IPoint[]pts, IPivotIndex selector, int ratio) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}

		// use fixed one-thread helper
		QuickSortOneHelper<Integer> qs = new QuickSortOneHelper<Integer>(ar);
		qs.setThresholdRatio(ratio);
		qs.setPivotMethod(selector);
		
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
	 * Change the number of helper threads inside to try different configurations.
	 * <p>
	 * Set to NUM_THREADS by default.
	 * 
	 * @param size              size of array to construct
	 * @param set               record times using this suite set
	 * @param pts               generated points whose (x,y) vals are used in input set
	 * @param selector          which pivot index method to use.
	 */
	public static void runTrialParallel (int size, TrialSuite set,
			IPoint[]pts, IPivotIndex selector) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}

		// default
		QuickSort<Integer> qs = new QuickSort<Integer>(ar);
		qs.setPivotMethod(selector);
		qs.setNumberHelperThreads(NUM_THREADS);
		
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
		int MAX_R = 21;
		int NUM_TRIALS = 20;
		
		UniformGenerator ug = new UniformGenerator();
		TrialSuite last = new TrialSuite();
		TrialSuite random = new TrialSuite();
		TrialSuite lastMT = new TrialSuite();
		TrialSuite randomMT = new TrialSuite();
		
		TrialSuite[] lastO = new TrialSuite[MAX_R];
		TrialSuite[] randomO = new TrialSuite[MAX_R];
		
		for (int r = 0; r < MAX_R; r++) {
			lastO[r] = new TrialSuite();
			randomO[r] = new TrialSuite();
		}
		
		// Number of  trials over sizes from 65536 to 1048576
		for (int size = 65536; size <= 1048576; size *= 2) {
			System.out.println (size + "...");
			for (int t = 0; t < NUM_TRIALS; t++) {
				// create random arrays of given SIZE with range [0 .. 32768]
				IPoint[]pts = ug.generate(size/2);  // 500 points = 1000 random [0,1] values
				runTrialNormal (size, last, pts, new LastSelector());
				runTrialNormal (size, random, pts, new RandomSelector());
				
				runTrialParallel (size, lastMT, pts, new LastSelector());
				runTrialParallel (size, randomMT, pts, new RandomSelector());
				
				for (int r = 0; r < MAX_R; r++) {
					int ratio = r+1;
					if (r == MAX_R-1) {
						ratio = Integer.MAX_VALUE;
					}
					runTrialOneHelper (size, lastO[r], pts, new LastSelector(), ratio);
					runTrialOneHelper (size, randomO[r], pts, new RandomSelector(), ratio);
				}
			}
		}
		
		// last one always
		System.out.println ("LAST");
		System.out.println (last.computeTable());
		
		// random one always
		System.out.println ("RANDOM");
		System.out.println (random.computeTable());
		
		// last one always
		System.out.println ("LAST-MULTI-THREAD");
		System.out.println (lastMT.computeTable());
		
		// random one always
		System.out.println ("RANDOM-MULTI-THREAD");
		System.out.println (randomMT.computeTable());
		
		// concatenate all together.
		String lastT = TrialSuiteHelper.combine(lastO);
		String randomT = TrialSuiteHelper.combine(randomO);

		System.out.println("LAST-ONE-HELPER(r)");
		System.out.println(lastT);
		
		System.out.println("RANDOM-ONE-HELPER(r)");
		System.out.println(randomT);
	}
}
