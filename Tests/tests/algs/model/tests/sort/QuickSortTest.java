package algs.model.tests.sort;

import org.junit.Test;

import algs.model.array.FirstSelector;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.MedianSelector;
import algs.model.array.MultiThreadQuickSort;
import algs.model.array.PISelector;
import algs.model.array.QuickSort;
import algs.model.array.RandomSelector;
import junit.framework.TestCase;


public class QuickSortTest extends TestCase {
	
	IPivotIndex[] selectors = new IPivotIndex[]{
			new FirstSelector(), 
			new LastSelector(), 
			new PISelector(5), 
			new RandomSelector(),
			new MedianSelector(),
	};
	
	private Integer[] createRandom(int num) {
		Integer[] set = new Integer[num];
		for (int i = 0; i < num; i++) {
			set[i] = (int)(Math.random()*100000);
		}
		
		return set;
	}
	
	@Test
	public void testBasic() {
		int  numTrials = 10;
		int  numPoints = 100;
		for (int i = 0; i < numTrials; i++) {
			for (int ms = 0; ms < 10; ms++) {
				
				Integer[]rnd = createRandom(numPoints);
				
				// try for first one.
				Integer[] copy = new Integer[rnd.length];
				System.arraycopy(rnd, 0, copy, 0, rnd.length);
				
				MultiThreadQuickSort<Integer> qs = new MultiThreadQuickSort<Integer>(copy);
				qs.setNumberHelperThreads(4);
				qs.setThresholdRatio(5);    /** 20% of the size. */
				qs.setPivotMethod(selectors[0]);
				qs.qsort(0, rnd.length-1);
				
				for (int s = 1; s < selectors.length; s++) {
					Integer[] copy2 = new Integer[rnd.length];
					System.arraycopy(rnd, 0, copy2, 0, rnd.length);
				
					QuickSort<Integer> qs2 = new QuickSort<Integer>(copy2);
					qs2.setPivotMethod(selectors[s]);
					qs2.setMinimumSize(ms);
					qs2.qsort(0, rnd.length-1);
					
					// check
					for (int x = 0; x < rnd.length-1; x++) {
						if (copy2[x].intValue() != copy[x].intValue()) {
							fail ("different selectors had different order @" + x + "[" + copy2[x] + "," + copy[x] + "]");
						}
					}
				}
				
			}
		}
			
	}
}
