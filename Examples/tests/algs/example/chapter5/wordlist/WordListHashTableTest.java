package algs.example.chapter5.wordlist;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import algs.model.search.ListHashTable;
import algs.model.search.ListHashTableReporter;
import algs.model.search.SimpleHash;
import algs.model.search.StringFileIterator;

/**
 * Test the hash table utility functions.
 * 
 * @author George Heineman
 * @author Gary Pollice
 */
public class WordListHashTableTest {
	
	@Test
	public void testCollisionReportWithStandardHash() throws IOException {
		ListHashTable<String> ht = new ListHashTable<String>(31);
		
		String loc = "resources" + java.io.File.separatorChar +
					 "algs" + java.io.File.separatorChar + 
					 "chapter5" + java.io.File.separatorChar;
		Iterator<String> it = new StringFileIterator(new File (loc, "sample.txt"));
		ht.load(it);
		
		ListHashTableReporter<String> reporter = new ListHashTableReporter<String>(ht);
		assertTrue (reporter.report() != null);
	}

	/**
	 * Test method for {@link ListHashTable}
	 */
	@Test
	public void testCollisionReportWithSimpleHash() throws IOException {

		ListHashTable<String> ht = new ListHashTable<String>(31, new SimpleHash(31));
		
		String loc = "resources" + java.io.File.separatorChar +
					 "algs" + java.io.File.separatorChar + 
					 "chapter5" + java.io.File.separatorChar;

		Iterator<String> it = new StringFileIterator(new File (loc, "sample.txt"));
		ht.load(it);
		
		ListHashTableReporter<String> reporter = new ListHashTableReporter<String>(ht);
		assertTrue (reporter.report() != null);
	}
	
	/**
	 * Test loading the words with list collision handling.
	 */
	@Test
	public void testLoadingWithListHandlingCollisions() throws Exception {
		ListHashTable<String> ht = new ListHashTable<String>(31, new SimpleHash(31));
		
		String loc = "resources" + java.io.File.separatorChar +
					 "algs" + java.io.File.separatorChar + 
					 "chapter5" + java.io.File.separatorChar;

		Iterator<String> it = new StringFileIterator(new File (loc, "sample.txt"));
		ht.load(it);
		
		assertTrue (ht.search("aaronic"));
		assertFalse(ht.search("akjdfow"));
	}
}
