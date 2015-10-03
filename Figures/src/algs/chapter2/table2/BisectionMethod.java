package algs.chapter2.table2;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Java Example of Bisection method for root-finding.
 * 
 *  
 * Java implementation of Bisection method that also shows the binary digits
 * of the resulting floating point value to provide a sense of the
 * convergence of the approach.
 * 
 * @author George Heineman
 * @date 8/26/15
 */
public class BisectionMethod {
	// Note that f(answer) = 0
	static final double root = -0.1893027580583891;
	static final String rootS = "" + root;
	static NumberFormat df = DecimalFormat.getInstance(); 
	
	/** The function whose roots are to be computed. */
	static double f(double x) {
		return x*Math.sin(x)-5*x-Math.cos(x);
	}


	/** Launch Bisection method for ten steps. */
	public static void main (String []args) {
		df.setMaximumFractionDigits(7);
		double a = -1;
		double b = +1;
		
		System.out.println("n\ta\tb\tc\tf(c)");
		
		// compute table and determine accurate digits
		for (int i = 1; i <= 20; i++) {
			double c = (a+b)/2;
			System.out.println(i + "\t" + df.format(a) + "\t" + df.format(b) + "\t" + df.format(c) + "\t" + df.format(f(c)));
			
			int signfa = (int) Math.signum(f(a));
			int signfc = (int) Math.signum(f(c));
			if (signfa == signfc) {
				a = c;
			} else {
				b = c;
			}
		}
	}
	
}
