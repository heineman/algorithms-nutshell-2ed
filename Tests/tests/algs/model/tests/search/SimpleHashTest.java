package algs.model.tests.search;

import org.junit.Test;

import algs.model.search.SimpleHash;
import junit.framework.TestCase;

public class SimpleHashTest extends TestCase {

	@Test
	public void testSimpleHash() {
		SimpleHash sh = new SimpleHash(11);
		assertEquals (11, sh.getTableSize());
	}

	@Test
	public void testHash() {
		SimpleHash sh = new SimpleHash(11);
		String test = "A";  // ascii 65, and 65 mod 11 = 10
		assertEquals (10, sh.hash(test));
		
		// test wrap around to validate negative hashes are ok.
		String test2 = "AAAAAAAAAAAAAAAAAAAAAAAAA";
		assertEquals (2, sh.hash(test2));
	}

}
