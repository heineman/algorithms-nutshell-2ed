package algs.chapter3.table2;

public class Main {
	/**
	 * Computes information about floating-point representations
	 */
	public static void main(String[] args) {
		float f = 3.88f - 0.01f;
		System.out.println("3.88f - 0.01f = " + f);

		int i1 = Float.floatToIntBits(f);
		System.out.println ("answer is " + Integer.toHexString(i1) + " in hex");
		if (f == 3.87f) {
			System.out.println ("Same");
		}

		float g = 3.87f - 0.01f;
		System.out.println("3.87f - 0.01f = " + g);

		// Advanced note. How did I come up with 3.88?
		// Make the following code executable and watch
		// what the output is (it is unexpected).
//		float h = 3.99f;
//		while (h > 0) {
//		System.out.println (h);
//		h = h - 0.01f;
//		}

		// more advanced note:
		int bitvalue1 = Float.floatToIntBits(3.88f);
		System.out.println ("3.88f is " + Integer.toHexString(bitvalue1));

		// Bit 31 (the bit that is selected by the mask 0x80000000) represents the sign
		// of the floating-point number. Bits 30-23 (the bits that are selected by the mask 0x7f800000) 
		// represent the exponent. Bits 22-0 (the bits that are selected by the mask 0x007fffff)
		// represent the significand (sometimes called the mantissa) of the floating-point number.

		// output of above is 407851ec, which is
		// 01000000 01111000 01010001 11101100 (total of 32 bits)
		// s         mmmmmmm mmmmmmmm mmmmmmmm
		//  eeeeeee e
		//
		// "The power of two can be determined by interpreting the exponent
		// bits as a positive number, and then subtracting a bias from the positive number. 
		// For a float, the bias is 126."
		//              
		// "Because the binary number system has just two digits -- zero and one -- the most
		// significant digit of a normalized mantissa is always a one.
		// 
		// Mantissa = .[1]11110000101000111101100 = [1/2] + 1/4 + 1/8 + 1/16 + 1/32 + 1/1024 + ...
		//                     4096 65536 131072 524288 2097152 4194304
		// thus mantissa =  0.9699962139129638671875
		// 
		// sign = +
		// exponent = (128-126) = 2
		// 2^(2) * 0.9699962139129638671875
		// 
		//  = 3.87998485565185546875 (error of ~.00002)
		//

		int bitvalue2 = Float.floatToIntBits(0.01f);
		System.out.println ("0.01f is " + Integer.toHexString(bitvalue2));

		int i2 = Float.floatToIntBits(3.87f);
		System.out.println ("3.87  is " + Integer.toHexString(i2));

		// output of above is 3c23d70a, which is
		// 00111100 00100011 11010111 00001010
		// s         mmmmmmm mmmmmmmm mmmmmmmm
		//  eeeeeee e
		//
		// sign = +
		// exponent = (120 - 126) = -6
		// mantissa = .[1]01000111101011100001010
		//    = [1/2] + 1/8 + 1/128 256 512 1024 4096 16384 32768 65536 2097152 8388608
		//    = 0.63999998569488525390625
		// 2^(-6) * 0.63999998569488525390625
		//
		// = 0.00999999977648258209228515625

		// Now do the subtraction: We normalize exponents
		// and then do bit subtraction (to do so, complement
		// the second number,i.e., swap 0+1, then add). Since 
		// 3.88 exponent is 2 and 0.01 exponent is -6, the shift
		// is 8, so eight bits of precision are lost. Note that (I BELIEVE)
		// the Floating Point processor records the fact that information
		// was lost, so the calculation will record some 'dirty' bit in the
		// final bit of the computation. Thus the addition will not be CLEAN,
		// but rather the very last bit in 0.01c will be turned to a 1; see
		// {1} below.

		//        s         mmmmmmm mmmmmmmm mmmmmmmm
		//         eeeeeee e
		//
		// 3.88 mantissa [1]1111000 01010001 11101100
		// 0.01 mantissa [0]0000000 10100011 11010111 +   (00001010 LOST EIGHT bits!)
		// 0.01c         [1]1111111 01011100 0010100{1} -
		// 3.88+0.01c    [1]1110111 10101110 00010100                       
		//

		// // 3.88 mantissa [1]1111000 01010001 11101100
		// // 0.01c         [1]1111111 01011100 00101001
		//                  ----------------------------
		// ADDITION:        [1]1110111 10101110 00010101 

		// exp = 2 = (128-126) = 2
		// mantissa = [1/2] + 1/4 + 1/8 16 64 128 256 512 2048 8192 16384 32768 1048576 4194304 16777216
		//          = 
		// + 2^(2) * 0.967500030994415283203125
		//
		// Hex is 4077ae15 (NOTE: actual value should have been 4077ae14) 

		//  3.8700001239776611328125
		//
		// As you can see, not QUITE 3.87 as we would have hoped!

		// look at the smallest distance between floats. These four numbers 
		// are in increasing size.
		System.out.println ("0x" + Integer.toHexString(0x407851ec) + " : " + Float.intBitsToFloat(0x407851ec));  // 3.88
		System.out.println ("0x" + Integer.toHexString(0x407851ed) + " : " + Float.intBitsToFloat(0x407851ed));  // 3.8800004
		System.out.println ("0x" + Integer.toHexString(0x407851ee) + " : " + Float.intBitsToFloat(0x407851ee));  // 3.8800006
		System.out.println ("0x" + Integer.toHexString(0x407851ef) + " : " + Float.intBitsToFloat(0x407851ef));  // 3.8800008

		// note there is no way to cleanly represent 3.8700005! There will
		// always be an error in the calculation (of 0.0000001).
		
		// interesting to randomly generate some 32-bit values to see what
		// the floating point values are.
		int v;
		
		v = (int)(Math.random() * Integer.MAX_VALUE);
		System.out.println (Integer.toHexString(v));
		System.out.println (Float.intBitsToFloat(v));
		
		v = (int)(Math.random() * Integer.MAX_VALUE);
		System.out.println (Integer.toHexString(v));
		System.out.println (Float.intBitsToFloat(v));
		
		v = (int)(Math.random() * Integer.MAX_VALUE);
		System.out.println (Integer.toHexString(v));
		System.out.println (Float.intBitsToFloat(v));
		

	}

}
