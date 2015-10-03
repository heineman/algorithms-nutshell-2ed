package algs.appendixA.table7;

import algs.model.tests.common.TrialSuite;

/**
 * Compute timing with Nanosecond timers.
 *  
 * @author George Heineman
 */
public class Main {
	
	/**
	 * Generate table.
	 * 
	 * @param args
	 */
	public static void main (String[]args) {
		TrialSuite tsM = new TrialSuite();
		TrialSuite tsN = new TrialSuite();
		System.gc();
		
		long min   =  8000000;
		long max   = 16000000;
		long delta = 2000000;
		
		for (long len = min; len <= max; len += delta) {
			for (int i = 0; i < 30; i++) {
				long nowM = System.currentTimeMillis();
				long nowN = System.nanoTime();
				@SuppressWarnings("unused")
				long sum = 0;
				for (int x = 1; x <= len; x++) {
					sum += x;
				}
				
				long endM = System.currentTimeMillis();
				long endN = System.nanoTime(); 
				tsM.addTrial(len, nowM, endM);
				tsN.addTrial(len, nowN, endN);
			}
		}
		
		System.out.println ("MILLISECONDS");
		System.out.println (tsM.computeTable());
		
		System.out.println ("NANOSECONDS");
		System.out.println (tsN.computeTable());
		
		System.out.println ("MILLISECOND HISTOGRAM");
		System.out.println (tsM.histogram());
		
		System.out.println ("NANOSECOND HISTOGRAM");
		System.out.println (tsN.histogram());
	}
}

