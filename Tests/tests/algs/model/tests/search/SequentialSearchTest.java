package algs.model.tests.search;

import java.util.ArrayList;

import org.junit.Test;

import algs.model.search.SequentialSearch;

import junit.framework.TestCase;

/**
 * Test Sequential Search
 * 
 */
public class SequentialSearchTest extends TestCase {
	
	@Test
	public void testSimple() {
		SequentialSearch<String> ss = new SequentialSearch<String>();
		String[] strings = new String[] { "alpha", "beta", "gamma", "delta" };
		
		assertTrue (ss.sequentialSearch(strings, "alpha"));
		assertTrue (ss.sequentialSearch(strings, "delta"));
		
		// failing conditions
		assertFalse (ss.sequentialSearch(strings, "iota"));
		
		// just checking
		assertFalse (ss.sequentialSearch(strings, null));
	}

	@Test
	public void testIterable() {
		SequentialSearch<String> ss = new SequentialSearch<String>();
		String[] ar = new String[] { "alpha", "beta", "gamma", "delta" };
		ArrayList<String> strings = new ArrayList<String>();
		for (String s : ar) {
			strings.add(s);
		}
		
		assertTrue (ss.sequentialSearch(strings, "alpha"));
		assertTrue (ss.sequentialSearch(strings, "delta"));
		
		// failing conditions
		assertFalse (ss.sequentialSearch(strings, "iota"));
		
		// just checking
		assertFalse (ss.sequentialSearch(strings, null));
	}
	
}
