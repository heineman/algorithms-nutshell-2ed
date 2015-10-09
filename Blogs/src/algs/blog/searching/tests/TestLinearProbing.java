package algs.blog.searching.tests;
import algs.blog.searching.hashbased.HashbasedSearch;
import algs.blog.searching.hashbased.LinearProbe;
import junit.framework.TestCase;

public class TestLinearProbing extends TestCase {

	public void testEmpty() {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(30, new LinearProbe(30, 1));
		assertFalse (lp.exists("hypoplankton"));
	}
	
	public void testOne() {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(30, new LinearProbe(30, 1));
		lp.insert("hypoplankton");
		assertTrue (lp.exists("hypoplankton"));
	}
	
	public void testTwo() {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(30, new LinearProbe(30, 1));
		assertEquals (1,lp.insert("hypoplankton"));
		assertEquals (2,lp.insert("unheavenly"));
		
		// inserted one delta ahead 
		assertTrue (lp.exists("hypoplankton"));
		assertTrue (lp.exists("unheavenly"));
	}
	
	public void testFull() {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(3, new LinearProbe(3, 1));
		lp.insert("hypoplankton");
		lp.insert("unheavenly");
		lp.insert("nothing");
		assertEquals (lp.capacity(), lp.size());
	}
	
	public void testFailure() {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(3, new LinearProbe(3, 1));
		assertEquals (1, lp.insert("hypoplankton"));
		assertEquals (2, lp.insert("unheavenly"));
		assertEquals (1, lp.insert("nothing"));

		try {
			lp.insert("final");
			fail ("Failed on detecting full table");
		} catch (Exception e) {
			
		}
	}
	
	public void testEarlyCycle()  {
		HashbasedSearch<String> lp = new HashbasedSearch<String>(4, new LinearProbe(4, 2));
		lp.insert("perfect"); // these both hash to 3
		lp.insert("imperfect");
		
		
		try {
			lp.insert("perfection");  // hashes to 3 also
			fail ("Failed on detecting full table");
		} catch (Exception e) {
			
		}
	}
}
