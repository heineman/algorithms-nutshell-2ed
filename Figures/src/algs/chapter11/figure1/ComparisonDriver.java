package algs.chapter11.figure1;

import algs.model.IPoint;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.MultiThreadQuickSort;
import algs.model.data.points.UniformGenerator;
import algs.model.tests.common.TrialSuite;
import algs.model.tests.common.TrialSuiteHelper;

/**
 * Compare execution of various multithreaded Quicksort implementations.
 * 
 * RandomSelector can be used instead of LastSelector but it increases costs noticeably because
 * of the extra computations, which don't actually improve the results in the end.
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
		MultiThreadQuickSort<Integer> qs = new MultiThreadQuickSort<Integer>(ar);
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
		MultiThreadQuickSort<Integer> qs = new MultiThreadQuickSort<Integer>(ar);
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
		if (args.length > 0) {
			NUM_THREADS = Integer.valueOf(args[0]);
		}
		System.out.println("Number Threads:" + NUM_THREADS);
		int MAX_R = 21;
		int NUM_TRIALS = 20;
		int MIN_N = 65536;
		int MAX_N = 1048576;
		
		UniformGenerator ug = new UniformGenerator();
		TrialSuite singleThreaded = new TrialSuite();
		TrialSuite multiThreaded = new TrialSuite();
		
		TrialSuite[] lastO = new TrialSuite[MAX_R];
		
		for (int r = 0; r < MAX_R; r++) {
			lastO[r] = new TrialSuite();
		}
		
		for (int size = MIN_N; size <= MAX_N; size *= 2) {
			for (int t = 0; t < NUM_TRIALS; t++) {
				// create random arrays of given SIZE/2 since we take x and y coordinates separately
				IPoint[]pts = ug.generate(size/2);  
				runTrialNormal (size, singleThreaded, pts, new LastSelector());
				
				runTrialParallel (size, multiThreaded, pts, new LastSelector());
				
				for (int r = 0; r < MAX_R; r++) {
					int ratio = r+1;
					if (r == MAX_R-1) {
						ratio = Integer.MAX_VALUE;
					}
					runTrialOneHelper (size, lastO[r], pts, new LastSelector(), ratio);
				}
			}
		}
		
		// last one always
		System.out.println ("SingleThreaded");
		System.out.println (singleThreaded.computeTable());
		
		// last one always
		System.out.println ("MultiThreaded(r=5)");
		System.out.println (multiThreaded.computeTable());
		
		// concatenate all together.
		String lastT = TrialSuiteHelper.combine(lastO);
		System.out.println("MultiThreaded(r=1..20,MaxInt)");
		System.out.println(lastT);
	}
}
