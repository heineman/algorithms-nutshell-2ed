package algs.example.chapter3;

import junit.framework.TestCase;

public class TestMaxDivideConquer extends TestCase {

	public void testOne() {
		assertEquals (1, MaxDivideConquer.maxElement(new int[] { 1 })); 
	}
	
	public void testTwo() {
		assertEquals (2, MaxDivideConquer.maxElement(new int[] { 1, 2 })); 
		assertEquals (2, MaxDivideConquer.maxElement(new int[] { 2, 1 }));
	}
	
	public void testThree() {
		assertEquals (3, MaxDivideConquer.maxElement(new int[] { 1, 2, 3 })); 
		assertEquals (3, MaxDivideConquer.maxElement(new int[] { 3, 2, 1 }));
		assertEquals (3, MaxDivideConquer.maxElement(new int[] { 1, 3, 2 }));
	}
	
	public void testMany () {
		for (int i = 0; i < 100; i++) {
			int many[] = new int[100];
			for (int j = 0; j < many.length; j++) {
				many[j] = -5;
			}
			many [i] = 10;
			assertEquals (10, MaxDivideConquer.maxElement(many));
		}
	}
}
