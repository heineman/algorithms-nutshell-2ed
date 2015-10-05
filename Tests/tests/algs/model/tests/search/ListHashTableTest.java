package algs.model.tests.search;

import java.util.ArrayList;

import org.junit.Test;

import algs.model.search.ListHashTable;
import algs.model.search.StandardHash;
import junit.framework.TestCase;

/**
 * Test our Hash Table implementation
 */
public class ListHashTableTest extends TestCase {

	@Test
	public void testSimple() {
		ListHashTable<String> ht = new ListHashTable<String>(16, new StandardHash<String>(16));

		assertEquals (0, ht.size());
		ht.add("4");
		assertEquals (1, ht.size());

		assertTrue (ht.search("4"));
		assertFalse (ht.search("43"));
		ht.add("43");
		assertTrue (ht.search("43"));

		ht.remove("43");
		assertFalse (ht.search("43"));		
	}

	@Test
	public void testDuplicateAdd() {
		ListHashTable<String> ht = new ListHashTable<String>(16, new StandardHash<String>(16));

		assertEquals (0, ht.size());
		ht.add("4");
		assertEquals (1, ht.size());

		String s = "4";
		ht.add(s, s);
		assertEquals (1, ht.size());		
	}

	@Test
	public void testBadAdd() {
		ListHashTable<String> ht = new ListHashTable<String>(16);
		try {
			ht.add("string", "xxx");
			fail();
		} catch (IllegalArgumentException iae) {

		}
	}

	@Test
	public void testLoad() {
		ListHashTable<String> ht = new ListHashTable<String>(16);

		assertEquals (0, ht.size());
		String[] set = new String[]{ "hypoplankton", "unheavenly", "c", "d" };
		ArrayList<String> as = new ArrayList<String>();
		for (String s : set) {
			as.add(s);
		}

		ht.load(as.iterator());
		assertEquals (4, ht.size());

	}

	@Test
	public void testRemove() {
		ListHashTable<String> ht = new ListHashTable<String>(16, new StandardHash<String>(16));

		// nothing there yet
		assertTrue (ht.remove("hypoplankton") == null);

		assertEquals (0, ht.size());
		ht.add("hypoplankton");
		assertEquals (1, ht.size());

		ht.add("unheavenly");
		assertEquals (2, ht.size());

		// validate there
		String ss = ht.remove("hypoplankton");
		assertEquals (ss, "hypoplankton");

		// validate no longer there
		assertEquals (1, ht.size());
		assertTrue (ht.remove("hypoplankton") == null);
	}

	@Test
	public void testBulkRemove() {
		ListHashTable<String> ht = new ListHashTable<String>(16, new StandardHash<String>(16));

		ArrayList<String> set = new ArrayList<String>();
		set.add("4");
		set.add("10");
		set.add("432");

		for (String s : set) {
			ht.add(s);
		}
		assertEquals (3, ht.size());

		// remove bulk
		ht.remove(set.iterator());
		assertEquals (0, ht.size());
	}

	@Test
	public void testReporter() {
		ListHashTable<String> ht = new ListHashTable<String>(16);
		ht.add("4");
		String s = ht.report();

		assertTrue (s != null);
	}

}
