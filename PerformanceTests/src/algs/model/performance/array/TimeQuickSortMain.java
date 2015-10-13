package algs.model.performance.array;

import algs.model.IPoint;
import algs.model.array.FirstSelector;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.MedianSelector;
import algs.model.array.PISelector;
import algs.model.array.QuickSort;
import algs.model.array.RandomSelector;
import algs.model.data.points.UniformGenerator;
import algs.model.tests.common.TrialSuite;


public class TimeQuickSortMain  {
	
	int BASE = 16777216;

	public static void main(String[] args) {
		new TimeQuickSortMain().testQS();		
	}
	
	private void runTrial (int size, TrialSuite set,
			IPoint[]pts, IPivotIndex selector) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}
		
		QuickSort<Integer> qs = new QuickSort<Integer>(ar);
		qs.setMinimumSize(6);
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
	
	public void testQS() {
		UniformGenerator ug = new UniformGenerator();
		TrialSuite pi = new TrialSuite();
		TrialSuite first = new TrialSuite();
		TrialSuite last = new TrialSuite();
		TrialSuite random = new TrialSuite();
		TrialSuite median = new TrialSuite ();
		
		// 50 trials over sizes from 10 to 1000000
		int numDigits = 1;
		for (int size = 10; size <= 1000000; size *= 10) {
			System.out.println (size + "...");
			if (numDigits < 6) { numDigits ++; }
			for (int t = 0; t < 20; t++) {
				// create random arrays of given SIZE with range [0 .. 32768]
				IPoint[]pts = ug.generate(size/2);  // 500 points = 1000 random [0,1] values
				runTrial (size,first, pts, new FirstSelector());
				runTrial (size, last, pts, new LastSelector());
				runTrial (size, pi, pts, new PISelector(numDigits));
				runTrial (size, random, pts, new RandomSelector());
				runTrial (size, median, pts, new MedianSelector());	
			}
		}
		
		// random one (based upon pi)
		System.out.println ("PI");
		System.out.println (pi.computeTable());
		
		// first one always
		System.out.println ("FIRST");
		System.out.println (first.computeTable());
		
		// last one always
		System.out.println ("LAST");
		System.out.println (last.computeTable());
		
		// random one always
		System.out.println ("RANDOM");
		System.out.println (random.computeTable());
		
		// median one always
		System.out.println ("MEDIAN");
		System.out.println (median.computeTable());
	}
}
