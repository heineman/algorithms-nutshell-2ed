package algs.model.tests.common;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class HistPairTest  extends TestCase {

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
	
	
	public void testUpdates() {
		HistPair hp = new HistPair (23, 88);
		FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:\\users\\heineman\\Desktop\\ERROR.txt");
			myWriter.write("Output:" + hp.getCount());
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(hp.getCount());
		assertEquals (88, hp.getCount());
		hp.addCount();
		System.out.println(hp.getCount());
		assertEquals (89, hp.getCount());
	}
	
	@Test
	public void testComparisons() {
		HistPair hp = new HistPair (23, 88);
		HistPair hp2 = new HistPair (23, 88);
		
		System.out.println(hp.compareTo(hp2));
		assertEquals (0, hp.compareTo(hp2));
		
		HistPair hp3 = new HistPair (30, 10);
		assertEquals (+1, hp3.compareTo(hp));
		assertEquals (-1, hp.compareTo(hp3));
		
	}
}
