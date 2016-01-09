package algs.model.performance.chapter2;


public class MultiplicationExampleMain {
	
	// computed tables.
	public static int table[][];
	public static int lookup2[][][][];
	public static int lookup[][][];
	public static int MaxLookup = 10;
	
	private static void output (int []n) {
		for (int i : n) {
			System.out.print(i);
		}
		System.out.println();
	}

	public static void multiply(int[] n1, int[] n2, int[] result) {
		int pos = result.length-1;

		// clear all values....
		for (int i = 0; i < result.length; i++) { result[i] = 0; }

		for (int m = n1.length-1; m>=0; m--) {
			int off = n1.length-1 - m;

			for (int n = n2.length-1; n>=0; n--) {
				int prod = n1[m]*n2[n];
				
				result[pos-off] += prod % 10;
				
				result[pos-off-1] += result[pos-off]/10 + prod/10;
				result[pos-off] %= 10;
								
				off++;
			}
		}
	}

	// generated code.
	public static void alt(int[] n1, int[] n2, int[] result) {
		int pos = result.length-1;

		// clear all values....
		for (int i = 0; i < result.length; i++) { result[i] = 0; }

		for (int m = n1.length-1; m>=0; m--) {
			int iPos = pos - (n1.length-1 - m);
			int iPosSubOne = iPos-1;
			if (n1[m] == 0) {
				continue; // skip zero multiplier! Won't affect total.
			}

			for (int n = n2.length-1; n>=0; n--, iPos--,iPosSubOne--) {
				int prod = n1[m]*n2[n];
				
				// GENERATED-BEGIN
				switch (prod) {
				case 0: break;
				case 1: result[iPos] += 1;break;
				case 2: result[iPos] += 2;break;
				case 3: result[iPos] += 3;break;
				case 4: result[iPos] += 4;break;
				case 5: result[iPos] += 5;break;
				case 6: result[iPos] += 6;break;
				case 7: result[iPos] += 7;break;
				case 8: result[iPos] += 8;break;
				case 9: result[iPos] += 9;break;
				case 10: result[iPosSubOne] += 1;break;
				case 11: result[iPos] += 1;result[iPosSubOne] += 1;break;
				case 12: result[iPos] += 2;result[iPosSubOne] += 1;break;
				case 13: result[iPos] += 3;result[iPosSubOne] += 1;break;
				case 14: result[iPos] += 4;result[iPosSubOne] += 1;break;
				case 15: result[iPos] += 5;result[iPosSubOne] += 1;break;
				case 16: result[iPos] += 6;result[iPosSubOne] += 1;break;
				case 17: result[iPos] += 7;result[iPosSubOne] += 1;break;
				case 18: result[iPos] += 8;result[iPosSubOne] += 1;break;
				case 19: result[iPos] += 9;result[iPosSubOne] += 1;break;
				case 20: result[iPosSubOne] += 2;break;
				case 21: result[iPos] += 1;result[iPosSubOne] += 2;break;
				case 22: result[iPos] += 2;result[iPosSubOne] += 2;break;
				case 23: result[iPos] += 3;result[iPosSubOne] += 2;break;
				case 24: result[iPos] += 4;result[iPosSubOne] += 2;break;
				case 25: result[iPos] += 5;result[iPosSubOne] += 2;break;
				case 26: result[iPos] += 6;result[iPosSubOne] += 2;break;
				case 27: result[iPos] += 7;result[iPosSubOne] += 2;break;
				case 28: result[iPos] += 8;result[iPosSubOne] += 2;break;
				case 29: result[iPos] += 9;result[iPosSubOne] += 2;break;
				case 30: result[iPosSubOne] += 3;break;
				case 31: result[iPos] += 1;result[iPosSubOne] += 3;break;
				case 32: result[iPos] += 2;result[iPosSubOne] += 3;break;
				case 33: result[iPos] += 3;result[iPosSubOne] += 3;break;
				case 34: result[iPos] += 4;result[iPosSubOne] += 3;break;
				case 35: result[iPos] += 5;result[iPosSubOne] += 3;break;
				case 36: result[iPos] += 6;result[iPosSubOne] += 3;break;
				case 37: result[iPos] += 7;result[iPosSubOne] += 3;break;
				case 38: result[iPos] += 8;result[iPosSubOne] += 3;break;
				case 39: result[iPos] += 9;result[iPosSubOne] += 3;break;
				case 40: result[iPosSubOne] += 4;break;
				case 41: result[iPos] += 1;result[iPosSubOne] += 4;break;
				case 42: result[iPos] += 2;result[iPosSubOne] += 4;break;
				case 43: result[iPos] += 3;result[iPosSubOne] += 4;break;
				case 44: result[iPos] += 4;result[iPosSubOne] += 4;break;
				case 45: result[iPos] += 5;result[iPosSubOne] += 4;break;
				case 46: result[iPos] += 6;result[iPosSubOne] += 4;break;
				case 47: result[iPos] += 7;result[iPosSubOne] += 4;break;
				case 48: result[iPos] += 8;result[iPosSubOne] += 4;break;
				case 49: result[iPos] += 9;result[iPosSubOne] += 4;break;
				case 50: result[iPosSubOne] += 5;break;
				case 51: result[iPos] += 1;result[iPosSubOne] += 5;break;
				case 52: result[iPos] += 2;result[iPosSubOne] += 5;break;
				case 53: result[iPos] += 3;result[iPosSubOne] += 5;break;
				case 54: result[iPos] += 4;result[iPosSubOne] += 5;break;
				case 55: result[iPos] += 5;result[iPosSubOne] += 5;break;
				case 56: result[iPos] += 6;result[iPosSubOne] += 5;break;
				case 57: result[iPos] += 7;result[iPosSubOne] += 5;break;
				case 58: result[iPos] += 8;result[iPosSubOne] += 5;break;
				case 59: result[iPos] += 9;result[iPosSubOne] += 5;break;
				case 60: result[iPosSubOne] += 6;break;
				case 61: result[iPos] += 1;result[iPosSubOne] += 6;break;
				case 62: result[iPos] += 2;result[iPosSubOne] += 6;break;
				case 63: result[iPos] += 3;result[iPosSubOne] += 6;break;
				case 64: result[iPos] += 4;result[iPosSubOne] += 6;break;
				case 65: result[iPos] += 5;result[iPosSubOne] += 6;break;
				case 66: result[iPos] += 6;result[iPosSubOne] += 6;break;
				case 67: result[iPos] += 7;result[iPosSubOne] += 6;break;
				case 68: result[iPos] += 8;result[iPosSubOne] += 6;break;
				case 69: result[iPos] += 9;result[iPosSubOne] += 6;break;
				case 70: result[iPosSubOne] += 7;break;
				case 71: result[iPos] += 1;result[iPosSubOne] += 7;break;
				case 72: result[iPos] += 2;result[iPosSubOne] += 7;break;
				case 73: result[iPos] += 3;result[iPosSubOne] += 7;break;
				case 74: result[iPos] += 4;result[iPosSubOne] += 7;break;
				case 75: result[iPos] += 5;result[iPosSubOne] += 7;break;
				case 76: result[iPos] += 6;result[iPosSubOne] += 7;break;
				case 77: result[iPos] += 7;result[iPosSubOne] += 7;break;
				case 78: result[iPos] += 8;result[iPosSubOne] += 7;break;
				case 79: result[iPos] += 9;result[iPosSubOne] += 7;break;
				case 80: result[iPosSubOne] += 8;break;
				case 81: result[iPos] += 1;result[iPosSubOne] += 8;break;
				case 82: result[iPos] += 2;result[iPosSubOne] += 8;break;
				case 83: result[iPos] += 3;result[iPosSubOne] += 8;break;
				case 84: result[iPos] += 4;result[iPosSubOne] += 8;break;
				case 85: result[iPos] += 5;result[iPosSubOne] += 8;break;
				case 86: result[iPos] += 6;result[iPosSubOne] += 8;break;
				case 87: result[iPos] += 7;result[iPosSubOne] += 8;break;
				case 88: result[iPos] += 8;result[iPosSubOne] += 8;break;
				case 89: result[iPos] += 9;result[iPosSubOne] += 8;break;
				case 90: result[iPosSubOne] += 9;break;
				case 91: result[iPos] += 1;result[iPosSubOne] += 9;break;
				case 92: result[iPos] += 2;result[iPosSubOne] += 9;break;
				case 93: result[iPos] += 3;result[iPosSubOne] += 9;break;
				case 94: result[iPos] += 4;result[iPosSubOne] += 9;break;
				case 95: result[iPos] += 5;result[iPosSubOne] += 9;break;
				case 96: result[iPos] += 6;result[iPosSubOne] += 9;break;
				case 97: result[iPos] += 7;result[iPosSubOne] += 9;break;
				case 98: result[iPos] += 8;result[iPosSubOne] += 9;break;
				case 99: result[iPos] += 9;result[iPosSubOne] += 9;break;
				};
				switch (result[iPos]) {
				case 0: break; 
				case 1: break; 
				case 2: break; 
				case 3: break; 
				case 4: break; 
				case 5: break; 
				case 6: break; 
				case 7: break; 
				case 8: break; 
				case 9: break; 
				case 10: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 11: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 12: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 13: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 14: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 15: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 16: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 17: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 18: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 19: result[iPosSubOne] += 1; result[iPos] -= 10; break; 
				case 20: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 21: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 22: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 23: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 24: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 25: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 26: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 27: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 28: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 29: result[iPosSubOne] += 2; result[iPos] -= 20; break; 
				case 30: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 31: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 32: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 33: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 34: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 35: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 36: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 37: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 38: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 39: result[iPosSubOne] += 3; result[iPos] -= 30; break; 
				case 40: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 41: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 42: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 43: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 44: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 45: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 46: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 47: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 48: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 49: result[iPosSubOne] += 4; result[iPos] -= 40; break; 
				case 50: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 51: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 52: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 53: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 54: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 55: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 56: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 57: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 58: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 59: result[iPosSubOne] += 5; result[iPos] -= 50; break; 
				case 60: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 61: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 62: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 63: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 64: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 65: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 66: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 67: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 68: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 69: result[iPosSubOne] += 6; result[iPos] -= 60; break; 
				case 70: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 71: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 72: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 73: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 74: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 75: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 76: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 77: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 78: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 79: result[iPosSubOne] += 7; result[iPos] -= 70; break; 
				case 80: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 81: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 82: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 83: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 84: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 85: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 86: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 87: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 88: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 89: result[iPosSubOne] += 8; result[iPos] -= 80; break; 
				case 90: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 91: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 92: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 93: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 94: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 95: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 96: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 97: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 98: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				case 99: result[iPosSubOne] += 9; result[iPos] -= 90; break; 
				};
				
				// GENERATED-END
			}
		}
	}

	
	public static int[][] computeTable() {
		int [][] ret = new int[10][10];
		for (int m = 0; m < 10; m++) {
			for (int n = 0; n < 10; n++) {
				ret[m][n] = m*n;
			}
		}
		return ret;
	}	
	
	// generate the switch.
	public static void computeSwitch() { 
		// result[pos-off] += prod % 10;
		System.out.println ("switch (prod) {");
		for (int i = 0; i < 100; i++) {
			System.out.print ("case " + i + ": ");
			if (i%10 != 0) {
				System.out.print ("result[iPos] += " + (i%10) + ";");
			}
			if ((i / 10) != 0) {
				System.out.print ("result[iPosSubOne] += " + (i/10) + ";");
			}
			System.out.println ("break;");
		}
		System.out.println ("};");
		
		// middle one
//		if (result[pos-off] > 9) {   // carry internally
//			do {
//				result[pos-off] -= 10;
//				result[pos-off-1]++;
//			} while (result[pos-off] > 9);
//		}
		System.out.println ("switch (result[iPos]) {");
		for (int i = 0; i < 100; i++) {
			int tens = 10*(i/10);
			if (tens == 0) {
				System.out.println ("case " + i + ": break; ");
			} else {
				System.out.println ("case " + i + ": result[iPosSubOne] += " + (i/10) + "; result[iPos] -= " + tens + "; break; ");
			}
		}
		System.out.println ("};");
		
		
		
	}
	
	/** 
	 * Generate random number of size n directly into num
	 * 
	 * @param num 
	 * @param n 
	 */
	public static void randomNumber (int[] num, int n) {
		for (int j = 0;j < n; j++) {
			num[j] = (int) (Math.random()*10);
		}
	}

	// Sample run...
	
//	2,Base:15,ms.
//	2,Multiply*:1,ms.
//	2,Alt*:16,ms.
//	Trying 4...
//	4,Base:0,ms.
//	4,Multiply*:31,ms.
//	4,Alt*:16,ms.
//	Trying 8...
//	8,Base:0,ms.
//	8,Multiply*:62,ms.
//	8,Alt*:32,ms.
//	Trying 16...
//	16,Base:16,ms.
//	16,Multiply*:250,ms.
//	16,Alt*:171,ms.
//	Trying 32...
//	32,Base:0,ms.
//	32,Multiply*:1047,ms.
//	32,Alt*:687,ms.
//	Trying 64...
//	64,Base:0,ms.
//	64,Multiply*:4125,ms.
//	64,Alt*:2781,ms.
//	Trying 128...
//	128,Base:0,ms.
//	128,Multiply*:16484,ms.
//	128,Alt*:10875,ms.
//	Trying 256...
//	256,Base:0,ms.
//	256,Multiply*:65844,ms.
//	256,Alt*:41656,ms.
//	Trying 512...
//	512,Base:16,ms.
//	512,Multiply*:268641,ms.
//	512,Alt*:165687,ms.
//	Trying 1024...
//	1024,Base:31,ms.
//	1024,Multiply*:1062063,ms.
//	1024,Alt*:639172,ms.
	
	public void generateTable() {

		// Trials
		int n = 2;
		int MAX_SIZE = 32;   // have been able to run up to 1024 in the past.
		int NUM_TRIALS = 10000;
		table = computeTable();
		while (n < MAX_SIZE) {
			System.out.println ("Trying " + n + "...");
			// generate numbers and space for storage
			int[] n1 = new int[n];
			int[] n2 = new int[n];
			randomNumber(n1, n);
			randomNumber(n2, n);
			int[] result = new int[2*n+1];

			int[] copy1 = new int[n];
			int[] copy2 = new int[n];
			System.arraycopy(n1, 0, copy1, 0, n);
			System.arraycopy(n2, 0, copy2, 0, n);

			// BASELINE
			System.gc();
			long baseS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				// NOP

				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[0];
				System.arraycopy(n2, 1, n2, 0, n-1);
				n2[n-1] = c;
			}
			long baseE = System.currentTimeMillis();

			// MULTIPLY
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);
			long multS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				multiply(n1,n2,result);

				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[0];
				System.arraycopy(n2, 1, n2, 0, n-1);
				n2[n-1] = c;
			}
			long multE = System.currentTimeMillis();

			// ALT
			System.gc();
			System.arraycopy(copy2, 0, n2, 0, n);
			System.arraycopy(copy1, 0, n1, 0, n);			
			long altS = System.currentTimeMillis();
			for (int i = 0; i < NUM_TRIALS; i++) {
				alt(n1,n2,result);

				// circular shift.
				int c = n1[0];
				System.arraycopy(n1, 1, n1, 0, n-1);
				n1[n-1] = c;
				c = n2[0];
				System.arraycopy(n2, 1, n2, 0, n-1);
				n2[n-1] = c;				
			}
			long altE = System.currentTimeMillis();


			long baseLine = (baseE - baseS);
			System.out.println (n + ",Base:" + baseLine + ",ms.");
			System.out.println (n + ",Multiply*:" + (multE - multS-baseLine) + ",ms.");
			System.out.println (n + ",Alt*:" + (altE - altS-baseLine) + ",ms.");

			// advance
			n = n * 2;
		}
	}
	
	// this code produces Java code that can be copied/pasted above. Then once this 
	// code is in place, you call generateTable instead.
	public static void main (String []args) {
		// generating code
		if (args.length == 0) {
			table = computeTable();
			computeSwitch();
		} else {
			// run the output
			new MultiplicationExampleMain().generateTable();
		}
		
		// sample test.
		int t = 3;
		int[] n1 = new int[t];
		int[] n2 = new int[t];
		int[] result = new int [2*t+1];
		int[] result2 = new int [2*t+1];
		randomNumber(n1, t);
		randomNumber(n2, t);
		output (n1);
		output (n2);
		multiply (n1, n2, result);
		alt (n1, n2, result2);
		output (result);
		output (result2);
		
		for (int i = 0; i < result.length; i++) {
			if (result[i] != result2[i]) {
				System.out.print ("res :"); output (result);
				System.out.print ("res2:"); output (result2);
				System.exit(0);
			}
		}
	}
}
