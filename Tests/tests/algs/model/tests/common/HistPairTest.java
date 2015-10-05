package algs.model.tests.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class HistPairTest {

	@Test
	public void testConstructor() {
		HistPair hp = new HistPair (23, 88);
		assertEquals (23L, hp.time);
		assertEquals (88, hp.count);
		assertEquals (88, hp.getCount());
		
	}
	
	@Test
	public void testToString() {
		HistPair hp = new HistPair (23, 88);
		assertEquals ("23\t:88", hp.toString());
	}
	
	
	@Test
	public void testUpdates() {
		HistPair hp = new HistPair (23, 88);
		assertEquals (88, hp.getCount());
		hp.addCount();
		assertEquals (89, hp.getCount());
	}
	
	@Test
	public void testComparisons() {
		HistPair hp = new HistPair (23, 88);
		HistPair hp2 = new HistPair (23, 88);
		
		assertEquals (0, hp.compareTo(hp2));
		
		HistPair hp3 = new HistPair (30, 10);
		assertEquals (+1, hp3.compareTo(hp));
		assertEquals (-1, hp.compareTo(hp3));
		
	}
}
