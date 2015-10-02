package algs.model.tests.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Represents a suite of timed trials.
 * <p>
 * Automatically throws away the lowest and highest of all trials sizes when
 * computing the average.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TrialSuite {
	
	/** Trials grouped by size n. */
	TreeMap<Long,ArrayList<Long>> timings = new TreeMap<Long,ArrayList<Long>>();
	
	/** Histogram separator. */
	public static final String histogramSeparator = "---------------------------------------";
	
	public final static String buildHeader() {
		return "n,average,min,max,stdev,#";
	}
	
	/**
	 * Construct histogram table for the values in the trial suite with given n.
	 * 
	 * @param n    trial size for which table is to be built.
	 * @return human readable string
	 */
	public String buildTable(long n) {
		ArrayList<Long> times = timings.get(n);
		long min, max;
		Hashtable<Long,HistPair> table = new Hashtable<Long,HistPair>();
		
		min = max = times.get(0);
		HistPair pair = new HistPair(min, 1);
		table.put (min, pair);
		
		// compute number of independent solutions.
		for (int i = 1; i < times.size(); i++) {
			long t = times.get(i);
			pair = table.get(t);
			if (pair == null) {
				table.put (t, new HistPair(t, 1));
			} else {
				pair.addCount();
			}
			
			if (t < min) { min = t; }
			if (t > max) { max = t; }
		}
		
		// pull out and sort
		Comparable<?>[]vals = new Comparable[0];
		vals = table.values().toArray(vals);
		// vals could be sorted?
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vals.length; i++) {
			sb.append(vals[i]).append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Return the number of entries for given row.
	 * 
	 * Takes into account the fact that the best and worst are to be dropped when processing
	 * averages.
	 * 
	 * @param n   the desired row.
	 * @return    the number of entries in the given row.
	 */
	public int getCount(long n) {
		ArrayList<Long> times = timings.get(n);
		
		if (times == null) { return 0; }
		if (times.size() <= 2) { return times.size(); }
		
		return times.size() - 2;
	}
	
	/**
	 * Helper method to build row in table for given entry.
	 * 
	 * Always returns a string of six values separated by commas.
	 * 
	 * @param n   Entry for which String row is built
	 * @return  human readable string representing row n,mean,min,max,stdev,count
	 */
	private String buildRow(long n) {
		ArrayList<Long> times = timings.get(n);
		
		long sum, min, max;

		if (times == null || times.size() == 0) { return n + ",*,*,*,*,0"; }
		
		sum = 0;
		min = max = sum = times.get(0);
		
		if (times.size() == 1) { return n + "," + min + "," + min + "," + max + "," + 0.0 + "," + 1; }

		int minIdx = 0;
		int maxIdx = 0;
		for (int i = 1; i < times.size(); i++) {
			long t = times.get(i);
			if (t < min) { min = t; minIdx = i;}
			if (t > max) { max = t; maxIdx = i;}
			sum += t;
		}
		
		if (times.size() == 2) {
			double diff = Math.abs(max - min);
			diff = diff / 2;
			double avg = max + min;
			avg = avg / 2;
			return n + "," + avg + "," + min + "," + max + "," + diff + "," + 2; 
		}
	
		// throw away lowest and highest.
		sum = sum - min - max;
		int ct = times.size() - 2;

		double mean = sum;
		mean = mean / ct;
		double calc = 0;
		for (int i = 0; i < times.size(); i++) {
			if (i == minIdx || i == maxIdx) continue;
			calc += (times.get(i) - mean)*(times.get(i) - mean);
		}
		//sqrt((1/[n-1])*sum[(xi-mean)^2]) FORMULA FROM EXCEL SPREADSHEET
		if (ct == 1) {
			// if only one left, then calculated deviation is zero
			calc = 0;
		} else {
			calc /= (ct-1);
			calc = Math.sqrt(calc);
		}
		
		return n + "," + mean + "," + min + "," + max + "," + calc + "," + ct;
	}
	
	/**
	 * Expose the set of keys maintained by the table.
	 * @return Iterator of values representing the sizes of the runs stored by the table.
	 */
	public Iterator<Long> keys() {
		return timings.keySet().iterator();
	}
	
	/**
	 * Return a single average, if it exists in the table.
	 * 
	 * @param n   the desired row.
	 * @return  string representing average value in row.
	 */
	public String getAverage(long n) {
		String row = buildRow(n);
		
		StringTokenizer st = new StringTokenizer(row, ",");
		st.nextToken(); // skip n
		return st.nextToken();   // got average!
	}
	
	/**
	 * Return maximum value for entry, if it exists in the table.
	 * 
	 * @param n   the desired row.
	 * @return maximum value as a string
	 */
	public String getMaximum(long n) {
		String row = buildRow(n);
		
		StringTokenizer st = new StringTokenizer(row, ",");
		st.nextToken(); // skip n
		st.nextToken(); // skip avg
		st.nextToken(); // skip MIN
		return st.nextToken();   // got max!
	}
	
	/**
	 * Return minimum value for entry, if it exists in the table.
	 * 
	 * @param n   the desired row.
	 * @return  string representing minimum value in row.
	 */
	public String getMinimum(long n) {
		String row = buildRow(n);
		
		StringTokenizer st = new StringTokenizer(row, ",");
		st.nextToken(); // skip n
		st.nextToken(); // skip avg
		return st.nextToken();   // got minimum!
	}
	
	/**
	 * Return a single standard deviation, if it exists in the table.
	 * 
	 * @param n   the desired row.
	 * @return  string representing stdev value in row.
	 */
	public String getDeviation(long n) {
		String row = buildRow(n);
		
		StringTokenizer st = new StringTokenizer(row, ",");
		st.nextToken(); // skip n
		st.nextToken(); // skip avg
		st.nextToken(); // skip min
		st.nextToken(); // skip max
		return st.nextToken(); // got deviation!
	}
	
	/**
	 * Return a single row, if it exists in the table.
	 * 
	 * @param n   the desired row.
	 * @return  string representing entire row
	 */
	public String getRow(long n) {
		Iterator<Long> it = timings.keySet().iterator();
		while (it.hasNext()) {
			long existing = it.next();
				
			if (existing == n) {
				return buildRow(n);
			}
		}
		
		// nothing to return.
		return "";  
	}
	
	/** 
	 * Produce full table of information.
	 * @return string representing entire table 
	 */
	public String computeTable() {
		StringBuilder sb = new StringBuilder();
		sb.append(buildHeader());
		sb.append("\n");
		
		Iterator<Long> it = timings.keySet().iterator();
		while (it.hasNext()) {
			long n = it.next();
			
			sb.append(buildRow(n));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Generate Full histogram of unique values within the TrialSuite, divided by n.
	 * @return human readable string containing histogram. 
	 */
	public String histogram() {
		StringBuilder sb = new StringBuilder();
		sb.append(buildHeader());
		sb.append("\n");
		
		Iterator<Long> it = timings.keySet().iterator();
		while (it.hasNext()) {
			long n = it.next();
			
			sb.append("Table for:" + n).append("\n");
			sb.append(histogramSeparator).append("\n");
			sb.append(buildTable(n));
			sb.append(histogramSeparator).append("\n\n");
		}
		return sb.toString();
	}
	
	/**
	 * Record the timing of a trial of size n that started at startTime and 
	 * completed at endTime.
	 * 
	 * @param n           size of executed trial 
	 * @param startTime   when trial started
	 * @param endTime     when trial completed
	 */
	public void addTrial (long n, long startTime, long endTime) {
		ArrayList<Long> trials = timings.get(n);
		if (trials == null) {
			trials = new ArrayList<Long>();
			timings.put(n,trials);
		}
		
		// add to the set.
		trials.add(new Long(endTime-startTime));
	}


}
