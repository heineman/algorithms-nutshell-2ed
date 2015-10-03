package algs.chapter2.table2;

/**
 * Java Example of Newton's method.
 * 
 * Java implementation of Newton's method that also shows the binary digits
 * of the resulting floating point value to provide a sense of the
 * convergence of the approach.
 * 
 * @author George Heineman
 * @date 6/15/08
 */
public class Newton {
	// Note that f(answer) = 0
	static final String root = "-0.1893027580583891";
	static final String bits = Long.toBinaryString(Double.doubleToRawLongBits(Double.valueOf(root)));
	
	/** The function whose roots are to be computed. */
	static double f(double x) {
		return x*Math.sin(x)-5*x-Math.cos(x);
	}

	/** The derivative of the function whose roots are to be computed. */
	static double fd(double x) {
		return x*Math.cos(x)+2*Math.sin(x)-5;
	}

	/** Launch Newton's method for ten steps. */
	public static void main (String []args) {
		
		System.out.println("n\tXn in decimal\t\tXn in bits (binary digits)");
		
		// compute table and determine accurate digits
		double x = 0;
		for (int i = 0; i < 10; i++) {
			outputRow (i, x);
			x = x - f(x)/fd(x);
		}
	}
	
	/**
	 * Output accurate digits (decimal and binary) within [ and ]
	 * 
	 * @param iter  iteration number
	 * @param x     computed value
	 */
	private static void outputRow (int iter, double x) {
		// first the decimal digits
		String target = "" + x;
		String comp = compare(target, root);
		System.out.print(iter + "\t" + comp);
		if (comp.length() < 12) { System.out.print("\t\t"); }
		System.out.print("\t");
		
		System.out.println (compare (Long.toBinaryString(Double.doubleToRawLongBits(x)), bits));
	}
	
	/**
	 * Insert "[" and "]" around the correct digits, when read from left to right.
	 * 
	 * @param target
	 * @param correct
	 * @return
	 */
	private static String compare (String target, String correct) {
		int idx = 0;
		int match = -1;
		
		// find last digit which matches (may be -1)
		while (idx < target.length() && idx < correct.length()) {
			if (target.charAt(idx) != correct.charAt(idx)) {
				break;
			}
			
			match++;
			idx++;
		}
		
		if (match == -1) { return target; }
		if (idx == target.length()) { return "[" + target + "]"; }
		if (idx == correct.length()) { return "[" + correct + "]"; }
		return "[" + target.substring(0, match+1) + "]" + target.substring (match+1);
	}
}
