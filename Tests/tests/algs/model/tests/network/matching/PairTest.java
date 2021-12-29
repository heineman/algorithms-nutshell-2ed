package algs.model.tests.network.matching;

import org.junit.Test;

import algs.model.network.matching.Pair;
import junit.framework.TestCase;

public class PairTest extends TestCase {

	@Test
	public void testPair() {
		Pair p = new Pair("Sample", "Test");
		Pair p2 = new Pair ("Sample", "Test");
		Pair p3 = new Pair ("Sample", "No Match");
		
		assertEquals (p, p2);
		assertFalse (p.equals (p3));
		assertFalse (p.equals ("garbage"));
		assertFalse (p.equals(null));
	}


}
