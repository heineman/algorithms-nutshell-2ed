package algs.appendixA.example1;

import java.text.NumberFormat;

import algs.model.tests.common.TrialSuite;

/**
 * Generates execution times for large number of additions.
 *  
 * @author George Heineman
 */
public class Main {
	public static void main(String[] args) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(4);

		long min        =  8000000;
		long delta      =  2000000;
		long max        = 16000000;
		int NUM_TRIALS  = 30;
		int NUM_ENTRIES = 5;

		System.out.println("Table A-2");
		System.out.println("n\t\tavg.\tmin\tmax\tstdev\t#");
		TrialSuite ts = new TrialSuite();

		long maxTime = 0;
		long minTime = Long.MAX_VALUE;
		
		// record each <idx,time> so it can be used to populate table
		int fullRecord[][] = new int[NUM_TRIALS*NUM_ENTRIES][2];
		int ridx = 0;
		int idx = 0;
		for (long len = min; len <= max; len += delta, idx++) {
			for (int t = 0; t < NUM_TRIALS; t++) {
				System.gc();
				long now = System.currentTimeMillis();

				/** Task to be timed. */
				@SuppressWarnings("unused")
				long sum = 0;
				for (int x = 1; x <= len; x++) { sum += x; } 

				long end = System.currentTimeMillis();
				ts.addTrial(len, now, end);

				// aggregate
				int time = (int)(end - now);
				if (time < minTime) { minTime = time; }
				if (time > maxTime) { maxTime = time; }
				
				// record full information
				fullRecord[ridx][0] = idx;
				fullRecord[ridx][1] = time;
				ridx++;
			}

			System.out.println(len + "\t" + nf.format(Double.valueOf(ts.getAverage(len))) + "\t" +
					nf.format(Double.valueOf(ts.getMinimum(len))) + "\t" + nf.format(Double.valueOf(ts.getMaximum(len))) + "\t" +  
					nf.format(Double.valueOf(ts.getDeviation(len))) + "\t" + ts.getCount(len));
		}        

		// update the individual values
		int[][]table = new int[NUM_ENTRIES][(int)(maxTime+1)];
		for (int i = 0; i < ridx; i++) {
			table[fullRecord[i][0]][fullRecord[i][1]]++;
		}
		
		System.out.println("\nTable A-3");
		
		System.out.print("time(ms)\t");
		for (long len = min; len <= max; len += delta) {
			System.out.print(len + "\t");
		}
		System.out.println();
		for (long t = minTime; t <= maxTime; t++) {
			boolean print = false;
			for (int c = 0; c < NUM_ENTRIES; c++) {
				if (table[c][(int)t] != 0) {
					print = true;
				}
			}
			
			if (!print) { continue; }
			
			// output the row
			System.out.print(t + "\t");
			for (int c = 0; c < NUM_ENTRIES; c++) {
				System.out.print(table[c][(int)t] + "\t");
			}
			System.out.println();
			
		}
	}

}
