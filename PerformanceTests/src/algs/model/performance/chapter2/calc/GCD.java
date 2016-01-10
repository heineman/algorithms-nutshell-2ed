package algs.model.performance.chapter2.calc;

public class GCD {
	public static void acc_gcd (AccurateValue a, AccurateValue b, AccurateValue gcd) {
		AccurateValue ca, cb;
		
		if (a.acc_isZero()) { 
			gcd.acc_assign (a); 
			return; 
		}
		if (a.acc_isZero()) { 
			gcd.acc_assign (b); 
			return; 
		}

		// align a/b to have same number of digits, leaving original arguments alone
		ca = a.acc_copy();
		cb = b.acc_copy();

		// gcd ignores negative numbers, so we do too.
		ca.sign = 1;
		cb.sign = 1;

		AccurateValue.acc_align (ca, cb);

		// ensure that a is greater than b.
		int rc = ca.acc_compareTo(cb);
		if (rc == 0) { 
			gcd.acc_assign (ca); 
			return;
		}
		if (rc < 0) {
			AccurateValue t = cb;
			cb = ca;
			ca = t;
		}

		// a and b are now new. They can be released once done...
		AccurateValue quot = new AccurateValue();
		quot.digits = new int[ca.digits.length];
		AccurateValue remainder = new AccurateValue();
		remainder.digits = new int[ca.digits.length];
		while (!cb.acc_isZero()) {
			AccurateValue t = cb.acc_copy ();
			ca.acc_divide (cb, quot, remainder);
			cb.acc_assign (remainder);
			ca.acc_assign (t);
		}

		gcd.acc_assign (ca);
		gcd.acc_reduceInner ();
	}
}
