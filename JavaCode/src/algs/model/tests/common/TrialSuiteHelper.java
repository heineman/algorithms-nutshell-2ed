package algs.model.tests.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Helper methods for presenting trial suite data.
 * 
 * @author George Heineman
 * @since June 1 2009
 */
public class TrialSuiteHelper {

	static NumberFormat format = null;
	
	/**
	 * Combine the set of trials (which all must use the same number of rows) into 
	 * a single table that only shows the average results.
	 * 
	 * @param trials   results of a set of runs
	 * @return human readable string
	 */
	public static String combine(TrialSuite[] trials) {
		if (trials == null || trials.length == 0) { return ""; }
		if (trials.length == 1) { return trials[0].computeTable(); }
		
		if (format == null) {
			format = DecimalFormat.getInstance();
			format.setMaximumFractionDigits(4);
		}
		boolean extractHeader = true;
			
		// compute headers.
		Hashtable<String,String> results = new Hashtable<String,String>();
		ArrayList<String> order = new ArrayList<String>();
		for (int t = 0; t < trials.length; t++) {
			// last one always
			Scanner sc = new Scanner (trials[t].computeTable());
			sc.nextLine(); // skip header
			while (sc.hasNextLine()) {
				StringTokenizer st = new StringTokenizer(sc.nextLine(), ",");
				String ct = st.nextToken(); // extract if required
				String avg = st.nextToken();
				
				if (extractHeader) {
					order.add(ct);
					results.put(ct, format.format(Double.valueOf(avg)));
				} else {
					String s = results.get(ct);
					s += "," + format.format(Double.valueOf(avg));
					results.put (ct, s);
				}
			}
			sc.close();
			
			extractHeader = false; // done
		}
		
		// combine
		String retValue = "";
		for (String s : order) {
			retValue += s + "," + results.get(s) + "\n";
		}
		
		return retValue;
	}
}
