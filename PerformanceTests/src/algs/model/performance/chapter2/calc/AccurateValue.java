package algs.model.performance.chapter2.calc;

public class AccurateValue extends Value {

	int sign;
	int []digits;
	
	protected AccurateValue () {
		this.sign = 0;
		this.digits = null;
	}
	
	public AccurateValue(Value v) {
		super();
		
		// zero.
		if (v == null) {
			sign = 0;
			digits = new int[1];
			return;
		}
		
		sign = ((AccurateValue) v).sign;
		digits = new int[((AccurateValue) v).digits.length];
		System.arraycopy(((AccurateValue) v).digits, 0, digits, 0, digits.length);
	}
	
	public AccurateValue (int sign, int [] newDigits) {
		this.sign = sign;
		this.digits = new int[newDigits.length];
		System.arraycopy(newDigits, 0, this.digits, 0, newDigits.length);
	}
	
	public AccurateValue(String val) {
		AccurateValue parsed = AccurateValue.acc_parseType(val);
		if (parsed == null) {
			throw new NumberFormatException (val + " is illegal for AccurateValue");	
		}
	
		sign = parsed.sign;
		digits = parsed.digits;
		parsed.digits = null;
	}	
	
	/**
	 * Determine if number is zero.
	 */
	protected boolean acc_isZero () {
		return sign==0;
	}

	/**
	 * Copy.
	 */
	AccurateValue acc_copy () {
		return new AccurateValue (sign, digits);
	}

	@Override
	public Value add (Value v) {
		AccurateValue result = new AccurateValue();
		acc_add(this, (AccurateValue) v, result);
		result.acc_reduceInner();
		return result;
	}

	@Override
	public Value[] divide(Value v) {
		AccurateValue quot = new AccurateValue();
		AccurateValue remainder = new AccurateValue();
		acc_divide((AccurateValue) v, quot, remainder);
		quot.acc_reduceInner();
		remainder.acc_reduceInner();
		return new Value[]{quot, remainder};
	}

	@Override
	public Value multiply(Value v) {
		AccurateValue result = new AccurateValue();
		acc_multiply(this, (AccurateValue) v, result);
		result.acc_reduceInner();
		return result;
	}

	@Override
	public Value subtract(Value v) {
		AccurateValue result = new AccurateValue();
		acc_subtract(this, (AccurateValue) v, result);
		result.acc_reduceInner();
		return result;
	}
	
	
	/**
	 * Assign this object to be the same value as n2.
	 * 
	 * n1's length must be >= n2's length
	 * 
	 * @param n
	 * @param n2
	 */
	protected void acc_assign (AccurateValue n2) {
	  // ensure n1 is at least as long as n2
	  acc_normalize (n2.digits.length);
	  int idx = digits.length-1;

	  for (int i = n2.digits.length-1; i >=0; i--) {
		  digits[idx--] = n2.digits[i];
	  }
	  
	  while (idx >= 0) {
		  digits[idx--] = 0;
	  }

	  // sign must be same.
	  sign= n2.sign;
	}

	/**
	 * Multiply a number by a single digit.
	 * 
	 * Note that result->length = n1->length + 1
	 */
	// intermediate calculations are free'd
	private static void acc_multiplyDigit (AccurateValue n1, int d, AccurateValue result) {
		AccurateValue n2 = new AccurateValue();
		n2.digits = new int[n1.digits.length];
		
		n2.digits[n2.digits.length-1] = d;
		n2.sign = 1;
		acc_multiply (n2, n1, result);
	}
	
	/**
	 * Multiply two integers accurately.
	 * 
	 * Doesn't have to worry about sign of result: managed by multiply()
	 * 
	 * 
	 * @param n1
	 * @param n2
	 * @param result
	 */
	public static void acc_realMultiply(AccurateValue n1, AccurateValue n2, AccurateValue result) {
	  int i, m, n, off;
	  int pos;

	  //  n1 = copy (n1);
	  //  n2 = copy (n2);
	  acc_align (n1, n2);

	  // expand to be large enough...
	  result.digits = new int[n1.digits.length*2+1];
	  pos = result.digits.length-1;

	  // clear all values....
	  for (i = 0; i < result.digits.length; i++) {
		  result.digits[i] = 0;
	  }
	  
	  for (m = n1.digits.length-1; m>=0; m--) {
	    off = n1.digits.length-1 - m;
	    if (n1.digits[m] == 0) {
	      continue; // skip zero multiplier! Won't affect total.
	    }

	    for (n = n2.digits.length-1; n>=0; n--) {
	      int prod = n1.digits[m]*n2.digits[n];
	      
	      result.digits[pos-off] += prod % 10;

	      if (result.digits[pos-off] > 9) {   // carry internally
		do {
		  result.digits[pos-off] -= 10;
		  result.digits[pos-off-1]++;
		} while (result.digits[pos-off] > 9);
	      }
	      
	      if (prod > 9) {
		result.digits[pos-off-1] += prod / 10;   // deal with carry sum later
	      }
	      off++;
	    }
	  }

	  // strip leading zeros...
	  result.acc_reduceInner ();
	}


	public static void acc_multiply(AccurateValue n1, AccurateValue n2, AccurateValue result) {
		result.sign = 0;
		result.digits = new int[n1.digits.length];

		// already zero.
		if (n2.acc_isZero() || n1.acc_isZero ()) {
			return;
		}

		// no more zeros to contend with.
		if (n1.sign == n2.sign) {
			acc_realMultiply (n1, n2, result);   // simple to handle
			result.sign = 1;
		} else {
			AccurateValue o1 = n1.acc_copy ();
			AccurateValue o2 = n2.acc_copy ();

			o1.sign = 1;
			o2.sign = 1;

			acc_realMultiply (o1, o2, result);

			result.sign = -1;
		}
	}

	/** 
	 * Return String representing n1/n2
	 *
	 * consumes and free's n1/n2.
	 * 
	 * Recursive iterations.
	 */
	private static String acc_subProcess (AccurateValue n1, AccurateValue n2) {
	  String buf;
	  int off, length;
	  int start;
	  AccurateValue innerSum, result;

	  // if n1->length is smaller than n2->length, we are going to be too small.
	  if (n1.digits.length < n2.digits.length) {
	    String extra = n1.toString();
	    String retVal = "+" + extra;
	    return retVal;
	  }
	  
	  // we have to extend, and reduce length. Note that this WILL terminate
	  // because we would have left earlier if n2 were greater than n1.
	  off = 0;
	  length = n2.digits.length;
	  while (n2.acc_compareToFull(0,length,n1,off) > 0) {
	    n2.acc_expand();
	    length = n2.digits.length;
				
	    if (length > n1.digits.length) {
	      // gone too far! We at last have our remainder! Return as "+remainder".
	      String extra = n1.toString();
	      String retVal = "+" + extra;
	      return retVal;
	    }
	  }
	  
	  // add 1 just in case we get too high
	  innerSum = new AccurateValue();
	  innerSum.digits = new int[n1.digits.length+1];
	  result = new AccurateValue();
	  result.digits = new int[length];
	  
	  // initial set.
	  if (n2.digits[0] == 0) { start = 9; } else { start = n1.digits[0]/n2.digits[0]; }
	  
	  acc_multiplyDigit (n2, start, innerSum);
	  innerSum.acc_reduceInner();
	  
	  if (innerSum.digits.length < n2.digits.length) {
	    // perfect fit. Expand back out to work with subtractDown. REALLY UGLY CODE
		  innerSum.acc_expand();   // old innerSum is free'd
	  } else {
	    // must reduce and continue....
	    while (n2.digits.length < innerSum.digits.length) {
	      start--;   // had gone too far! Back down.
	      innerSum.digits = new int[n1.digits.length+1]; 
	      acc_multiplyDigit (n2, start, innerSum);
	      innerSum.acc_reduceInner();
	    }
	  }
	  
	  // if false, we have to add up
	  boolean subtractDown = innerSum.acc_compareToLen(n1, innerSum.digits.length) > 0;
	  for (;;) {
	    String subp;
	    int numZerosToAdd;

	    if (subtractDown) {
	      start--;
	      acc_subtract(innerSum, n2, result);
	      innerSum = result.acc_copy();
	      
	      subtractDown = innerSum.acc_compareToLen(n1, innerSum.digits.length) > 0;
	    } else {
	      // found. Note that 'start' is the digit in the quotient. Subtract
	      // from and replace.
	      AccurateValue newTop = new AccurateValue();
	      newTop.digits = new int[innerSum.digits.length]; 
	      
	      newTop.acc_extract(0,n1,0,innerSum.digits.length);
	      newTop.sign = innerSum.sign;
	      
	      result.digits = new int[innerSum.digits.length]; 
	      acc_subtract(newTop, innerSum, result);
	      
	      // copy back in, overwriting the old digits. Take care that we
	      // deal with n1 as "20" and result of "2" when we want to copy in
	      // the number of digits to be copied is newTop->length
	      result.acc_normalize (newTop.digits.length);
	      n1.acc_extract(0,result,0,result.digits.length);  
	      
	      // Start with n2->length digits within n1, and expand to the right
	      // until we either reach the end (DONE!) or find a number that is 
	      // GREATER than n2.
	      numZerosToAdd = 0;
	      while (n1.acc_compareToRight (n2.digits.length+numZerosToAdd, n2, n2.digits.length) < 0) {
	    	  numZerosToAdd++;
	      }
	      
	      // if we get here and numZerosToAdd is > 1 then we can add. We know that
	      // it must at least be 1 since we are moving over one digit....
	     
	      // How do we know when we have gone too far? When there is no need 
	      // to go ahead and call subProcess? That is essential...
	      if (n1.acc_compareTo(n2) < 0) {
	    	  String extra = n1.toString ();
	    	  return (char)(start + '0') + "+" + extra;
	      }
		
	      buf = "" + (char)(start + '0');
	      int idx = 1;
	      while (idx < numZerosToAdd) {
	    	  buf = buf + "0";
	    	  idx++;
	      }

	      // consumes n1 & n2
	      n1.acc_reduceInner();
	      n2.acc_reduceInner();

	      subp = acc_subProcess(n1, n2);
	      return buf + subp;
	    }
	  }
	}

	/**
	 * Strip leading zeros to reduce.
	 * 
	 * Only internal manipulations are made.
	 *
	 * @param n
	 * @param n2
	 */
	public void acc_reduceInner () {
		int i;
		for (i = 0; i < digits.length; i++) {
			if (digits[i] != 0) {
				if (i == 0) {
					return; 
				}  // already non-zero left-most
			
				int newDigs[] = new int[digits.length-i];
				// leave as 'dead' the areas we aren't concerned about. 
				// Shifts values left
				for (int j = 0; j < digits.length-i; j++) {
					newDigs[j] = digits[i+j];
				}
				
				digits = newDigs;
				return;
			}
		}
		
		// if you get here, then we are ZERO
		digits = new int[1];
		sign = 0;
	}

	/**
	 * Return 0 if same, -1 if n1 is less than n2, and +1 is n1 > n2
	 * 
	 * compare len1 digits of n1 to same number of digits in n2, all starting
	 * at the appropriate offset (off1 or off2) in the different numbers.
	 * 
	 * @param n1
	 * @param n2
	 */
	int acc_compareToFull (int off1, int len1, AccurateValue n2, int off2) {
	  int i;

	  if (sign == 0 || n2.sign == 0) {
	    if (sign == n2.sign) { return 0; }   // both zero
	    if (sign == 0) { return -n2.sign; }  // first is zero
	    return -sign;                         // second is zero
	  }

	  // no zeros from here on out. Handle opposite values easily.
	  if (sign != n2.sign) {
	    if (sign < n2.sign) return -1;
	    return +1;
	  }

	  // same sign!
	  for (i = 0; i < len1; i++) {
	    if (n2.digits[off2+i] == digits[off1+i]) { 
	      continue;
	    }
	    
	    // Must be able to report by now...
	    if (n2.digits[off2+i] > digits[off1+i]) {
	      if (n2.sign == 1) { return -1; } else { return +1; }
	    } else {
	      if (n2.sign == 1) { return +1; } else { return -1; }
	    }
	  }
	  
	  // must be the same digits AND same sign, so must be equal
	  return 0;
	}

	/**
	 * Expand by one and release past (internal) memory.
	 *
	 *  This works internally without affecting the outer type pointer.
	 * 
	 * @param n
	 * @param n2
	 */
	void acc_expand () {
	  int len = digits.length;
	  int oldOnes[] = digits;  // save old one
	  digits = new int[len+1];
	  System.arraycopy(oldOnes, 0, digits, 1, len);
	}

	/**
	 * Return n1/n2 as result and quotient.
	 * 
	 * Note that n1 and n2 are unaffected.
	 * 
	 * Work this as reducing problem into smaller problems, from which the result
	 * is generated.
	 * 
	 * Take 0074 divide into 6928
	 * 
	 * 1. 074 divide into 692    (9)
	 * 2. 074 divide into 268    (3)
	 * 3. 74  divide into 46     (+46) 
	 * 
	 * @param n1
	 * @param n2
	 * @param result
	 * @param quotient
	 */
	// all intermediate allocations are freed
	public void acc_divide(AccurateValue n2, AccurateValue quotient, AccurateValue remainder) {
	  int i, idx;
	  String result;
	  int signResult = 1;

	  // make copies to leave 'n2' alone.
	  AccurateValue n1 = this.acc_copy ();
	  n2 = n2.acc_copy ();

	  acc_align (n1, n2);

	  // make sure results are both big enough...
	  quotient.digits = new int[n1.digits.length+1];
	  remainder.digits = new int[n1.digits.length+1];

	  if (n2.acc_isZero()) {
	    System.err.println ("ArithmeticException: Divide by Zero");
	    return;
	  } else if (n1.acc_isZero ()) {
	    // 0 divided by anything is zero...
	    return;
	  }
	  
	  // deal with sign and move all numbers (copies!) to be positive. Opposite
	  // numbers become opposite.
	  if (n1.sign != n2.sign) {
		  signResult = -1;
	  }
	  n1.sign = +1;
	  n2.sign = +1;

	  // place sign in quotient and remainder.
	  quotient.sign = signResult;
	  remainder.sign = signResult;

	  // if clearly greater, then copy integer remainder
	  if (n2.acc_compareTo(n1) > 0) {
		  idx = remainder.digits.length-1;
		    for (i = n1.digits.length-1; i >= 0; i-- ) {
		    	remainder.digits[idx] = n1.digits[i];
		    	idx--;
		    }
	    return;
	  }
	  
	  // Make sure we remove all leading zero's
	  n1.acc_reduceInner ();
	  n2.acc_reduceInner ();
	  
	  // we have to extend, and reduce length. Note that this WILL terminate
	  // because we would have left earlier if n2 were greater than n1.
	  while (n2.acc_compareToFull(0,n2.digits.length,n1,0) > 0) {
	    n2.acc_expand();
	  }
	  
	  // return string "quot+remainder";
	  // CONSUMES n1 + n2 so they don't need to be free'd here
	  result = acc_subProcess(n1, n2);
	  
	  // pack into quotient/remainder.
	  idx = result.indexOf("+");
	  if (idx == 0) {
	    for (i = 0; i < quotient.digits.length; i++) {
	    	quotient.digits[i]=0;
	    }
	    remainder.acc_pack(result);  // ever happen?
	  } else {
	    String str1 = result.substring(0,idx);
	    String str2 = result.substring(idx+1);
	    quotient.acc_pack(str1);
	    remainder.acc_pack(str2);
	  }

	  remainder.acc_reduceInner();
	  quotient.acc_reduceInner();

	  // Flip sign as appropriate. Note if sign is zero, then no effect! Cute...
	  // division done under positive numbers, so we need to fix sign here. 
	  quotient.sign *= signResult;
	  
	  // note that remainder will always have the sign of the initial number, unless it is already
	  // zero.
	  if (remainder.sign != 0) {
		  remainder.sign = this.sign;
	  }
	}

	/**
	 * Subtract n2 from n1 two integers accurately.
	 * 
	 * @param n1
	 * @param n2
	 * @param sum
	 */
	private static void acc_realSubtract(AccurateValue n1, AccurateValue n2, AccurateValue result) {
	  acc_align (n1, n2);

	  result.digits = new int[n1.digits.length];

	  int b = n1.digits.length-1;
	  int carry = 0;
	  int rc = n2.acc_compareTo(n1);
	  if (rc == 0) {
	    return;  // done.
	  }
			       
	  boolean smallerOrEqual = (rc <= 0);
	  while (b >= 0) {
	    
	    int s = n1.digits[b] - n2.digits[b];
	    if (!smallerOrEqual) { s = -s; }
	    s += carry;
	    if (s >= 0) {
	    	result.digits[b] = s;
	      carry = 0;
	    } else {
	      carry = -1;
	      result.digits[b] = s+10;
	    }
	    
	    b--;
	  }
	  
	  if (!smallerOrEqual) {
	    result.sign = -1;
	  } else {
	    if (carry < 0) {
	      result.sign = -1; 
	    } else {
	      // we know from above that result wasn't zero
	      result.sign = +1; 
	    }
	  }
	}

	/**
	 * Add numbers. Must have the same sign...
	 */
	public static void acc_realAdd (AccurateValue n1, AccurateValue n2, AccurateValue sum) {
		sum.digits = new int[n1.digits.length+1];
		sum.sign = 0;
		acc_align (n1, n2);

	  int b = n1.digits.length-1;
	  int carry = 0;

	  while (b >= 0) {
	    int s = n1.digits[b] + n2.digits[b] + carry;

	    sum.digits[b+1] = s%10;

	    if (s > 9) { carry = 1; } else { carry = 0; }
	    b--;
	  }

	  sum.digits[0] = carry;

	  // reduce to smallest rep
	  sum.acc_reduceInner ();
	  
	  // take one of them...
	  sum.sign = n1.sign;
	}


	/**
	 * Add two integers accurately.
	 * 
	 * Note that n1, n2, and sum can all be internally reallocated but not externally
	 * free'd
	 *
	 * @param n1
	 * @param n2
	 * @param sum
	 */
	void acc_add(AccurateValue n1, AccurateValue n2, AccurateValue sum) {
	  acc_align (n1, n2); // first ensure same length
	  
	  if (n1.acc_isZero ()) {
		  AccurateValue av = n2.acc_copy();
		  sum.sign = n2.sign;
		  sum.digits = av.digits;
		  return;
	  } else if (n2.acc_isZero ()) {
		  AccurateValue av = n1.acc_copy();
		  sum.sign = n1.sign;
		  sum.digits = av.digits;
		  return;	
	  }

	  // check signs.
	  if (n1.sign == n2.sign) {
		  acc_realAdd(n1, n2, sum);
	  } else {
	    AccurateValue o1 = n1.acc_copy ();
	    AccurateValue o2 = n2.acc_copy ();
	    if (o1.sign == -1) {
	      o1.sign = 1;
	      acc_subtract (o2, o1, sum);
	    } else {
	      o2.sign = 1;
	      acc_subtract (o1, o2, sum);
	    }
	  }
	}


	/**
	 * Subtract two numbers. If opposite signs, this becomes an add.
	 */
	public static void acc_subtract(AccurateValue n1, AccurateValue n2, AccurateValue sum) {
		if (n1.acc_isZero ()) {
			AccurateValue cp = n2.acc_copy();
			sum.digits = cp.digits;
			sum.sign = -n2.sign;
			return;
		} else if (n2.acc_isZero ()) {
			AccurateValue cp = n1.acc_copy();
			
			sum.digits = cp.digits;
			sum.sign = n1.sign;
			return;
		}

		// no more zeros to deal with.
		// check signs.
		if (n1.sign == n2.sign) {
			acc_realSubtract (n1, n2, sum);   // simple to handle
		} else {
			AccurateValue o1 = n1.acc_copy();
			AccurateValue o2 = n2.acc_copy();
			if (o1.sign == -1) {
				o2.sign = -1;
				acc_realAdd (o1, o2, sum);
			} else {
				o2.sign = 1;
				acc_realAdd (o1, o2, sum);
			}
		}

		sum.acc_reduceInner();
	}

	/**
	 * Return 0 if same, -1 if n1 is less than n2, and +1 is n1 > n2
	 * 
	 * Simply compare len digits in sequence.
	 * 
	 * Only call on positive numbers...
	 * 
	 * @param n1
	 * @param n2
	 */
	int acc_compareToLen (AccurateValue n2, int len) {
	  int i;

	  if (sign < 0 || n2.sign < 0) {
	    System.err.println ("WARNING: compareToLen called with negative number\n");
	  }

	  for (i = 0; i < len; i++) {
	    if (n2.digits[i] == digits[i]) { 
	      continue;
	    }
	    
	    // Must be able to report by now...
	    if (n2.digits[i] > digits[i]) {
	      return -1;
	    } else {
	      return +1;
	    }
	  }
	  
	  // must be the same!
	  return 0;
	}

	/**
	 * Return 0 if same, -1 if n1 is less than n2, and +1 is n1 > n2
	 * 
	 * If don't have same length, then take into account leading zeros.
	 * 
	 *   DOESN'T YET INCORPORATE SIGNs
	 * 
	 * @param n1
	 * @param n2
	 */
	int acc_compareTo (AccurateValue n2) {
	  int i;
	  int off1 = 0;
	  int off2 = 0;
	  int sz1, sz2;
	  boolean z1 = acc_isZero();
	  boolean z2 = n2.acc_isZero();
	  
	  if (z1 && z2) { return 0; } // both zero.
	  if (z1) { return -n2.sign; }      // second number is not zero.
	  if (z2) { return sign; }      // first number is not zero.
	  
	  // handle bad signs.
	  if (sign < 0 && n2.sign >= 0) {
	    return -1;    // neg. compared to positive
	  } else if (sign >= 0 && n2.sign < 0) {
	    return +1;    // pos. compared to negative
	  }
	  
	  // we switch numbers when comparing NEGATIVES
	  AccurateValue av1=this, av2=n2;
	  if (sign < 0) {
		  AccurateValue tmp = av1;
		  av1 = av2;
		  av2 = tmp;
	  }

	  while (av1.digits[off1] == 0) { off1++; }  // compute leading zeros.
	  while (av2.digits[off2] == 0) { off2++; }
	  
	  sz1 = av1.digits.length-off1;
	  sz2 = av2.digits.length-off2;
	  
	  if (sz1 < sz2) { return -1; }  // first number has less digits (after removing leading zeros)
	  if (sz2 < sz1) { return +1; }  // second number has less digits (after removing leading zeros)
	  
	  // same number of digits, compare 1-by-1
	  
	  for (i = 0; i < sz1; i++) {
	    if (av1.digits[off1+i] == av2.digits[off2+i]) { 
	      continue;
	    }
	    
	    // Must be able to report by now...
	    if (av2.digits[off2+i] > av1.digits[off1+i]) {
	      return -1;
	    } else {
	      return +1;
	    }
	  }
	  
	  // must be the same!
	  return 0;
	}

	/**
	 * Compare two numbers FROM THE RIGHT where each len determines the size of
	 * the numbers to be compared.
	 *
	 * In doing so, when we run out of digits from one number, we ensure
	 * that all of the remaining digits of the other are zero.
	 *
	 *   NOTE that this IGNORES SIGNS and compares digits exclusively!
	 *   YOU HAVE BEEN WARNED
	 */
	int acc_compareToRight (int len1, AccurateValue n2, int len2) {
	  int sz1, sz2;
	  int off1 = 0;
	  int off2 = 0;
	  int i;
	  boolean z1 = false, z2 = false;
	  if (acc_isZero()) {
		  off1 = digits.length;
		  z1 = true;
	  } else {
		  while (digits[off1] == 0) { off1++; }  // compute leading zeros.
	  }
	  if (n2.acc_isZero()) {
		  off2 = n2.digits.length;
		  z2 = true;
	  } else {
		  while (n2.digits[off2] == 0) { off2++; }
	  }
	  
	  if (z1 && z2) { return 0; }  // both zero
	  if (z1) { return +1; }       // first ZERO and second is not.
	  if (z2) { return -1; }       // second ZERO and first is not.
	  
	  sz1 = len1-off1;   // number of digits remaining
	  sz2 = len2-off2;

	  if (sz1 < sz2) { return -1; }  // first number has less digits (after removing leading zeros)
	  if (sz2 < sz1) { return +1; }  // second number has less digits (after removing leading zeros)

	  // same number of digits, compare 1-by-1
	  
	  for (i = 0; i < sz1; i++) {
		  if (off1+i >= digits.length) { return -1; } // first ran out of digits
		  if (off2+i >= n2.digits.length) { return +1; } // second ran out of digits
	    if (digits[off1+i] == n2.digits[off2+i]) { 
	      continue;
	    }
	    
	    // Must be able to report by now...
	    if (n2.digits[off2+i] > digits[off1+i]) {
	      return -1;
	    } else {
	      return +1;
	    }
	  }

	  return 0;  // MUST BE THE SAME. Had been -1 which was a mistake
	}


	/**
	 * Expand a to be at least as large as length.
	 * 
	 * @param a
	 * @param length
	 * @return
	 */
	void acc_normalize(int length) {
	  if (digits.length >= length) { return; }
	  
	  while (digits.length < length) {
	    acc_expand();
	  }
	}

	/**
	 * Normalize one and two (internally) so they are left-aligned with
	 * each other.
	 */
	public static void acc_align (AccurateValue c1, AccurateValue c2) {
	  c1.acc_normalize (c2.digits.length);
	  c2.acc_normalize (c1.digits.length);
	}

	// provide means for parsing string into type (or return -1)
	public static AccurateValue acc_parseType (String str) {
		AccurateValue ptr = new AccurateValue();
		ptr.sign = 1;  // assume will be positive.
		ptr.digits = new int[1];
		
		boolean allZeros = true;  // assume all zeros
	    if (str == null) { return null; }
	    
	    int numDigits = 0;
	    boolean signDone = false;

	    int len = str.length();
	    for (int i = 0; i < len; i++) {
	    	char ch = str.charAt(i);
	    	if (ch == '-') {
	    		if (signDone) {
	    			return null;      // sign appears twice
	    		}

	    		signDone = true;
	    		ptr.sign = -1;
	    		continue;
	    	}
	    

	    	if (ch == '+') {
	    		if (signDone) {
	    			return null;      // sign appears twice
	    		}

	    		signDone = true;
	    		ptr.sign = +1;
	    		continue;
	    	}

	    	if (ch == '.') {
	    		break;
	    	}

	    	if (ch < '0' || ch > '9') {
	    		return null;
	    	}
	    	
	    	numDigits++;
	    	if (ch != '0') { allZeros = false; }
	  }

	  // for when user types in "-"
	  if (numDigits == 0) {
	    return null;
	  }

	  // looks good. Was newType(numDigits);
	  ptr.digits = new int[numDigits];

	  int i = 0, off = 0;
	  if (ptr.sign == -1) { off++; i++; }
	  for (; i < len && i-off < numDigits ; i++) {
		  ptr.digits[i-off] = str.charAt(i)-'0';
	  }
	  if (allZeros) {
	    ptr.sign = 0;
	  } 
	  
	  return ptr;
	}

	public String toString () {
		if (sign == 0) { return "0"; }
		
		StringBuilder sb = new StringBuilder();
		if (sign < 0) {
			sb.append('-');
		}
		
		for (int i = 0; i < digits.length; i++) {
			sb.append ((char)(digits[i] + '0'));
		}

		String s = sb.toString();
		return s;
	}

	/**
	 * Pack from string into array.
	 * 
	 * Make sure you cover case where 's' is zero. for SIGN.
	 *  
	 * @param n
	 * @param s
	 */
	void acc_pack (String s) {
	  for (int i = 0; i < digits.length; i++) {
		  digits[i] = 0;
	  }
	  sign = 0;
	  
	  int idx = digits.length-1;
	  for (int i = s.length()-1; i >= 0; i--) {
		  char ch = s.charAt(i);
		  if (i == 0 && ch == '-') {
			  sign = -1;
		  } else {
			  if (ch != '0' && sign == 0) {
				  sign = 1;
			  }
		
			  digits[idx--] = ch - '0';
		  }
	  }
	}
		
	/**
	 * Extract len2 digits from n2 at off2 and copy into n1 at off1 
	 * 
	 * If 'n1' is zero after this method, set the sign bit to zero
	 * 
	 * @param n
	 * @param n2
	 */
	void acc_extract (int off1, AccurateValue n2, int off2, int len2) {
		for (int i = 0; i < len2; i++) {
			digits[off1+i] = n2.digits[off2+i];
		}
		
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] != 0) { return; }
		}
		
		sign = 0;
	}
		
	/**
	 * Set to zero.
	 * 
	 * @param n
	 */
	void acc_clear () {
		sign = 0;
		for (int i = 0; i < digits.length; i++) {
			digits[i] = 0;
	  }
	}

}

