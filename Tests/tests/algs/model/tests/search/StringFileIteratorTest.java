package algs.model.tests.search;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import algs.model.search.StringFileIterator;

import junit.framework.TestCase;

public class StringFileIteratorTest extends TestCase {

	public void testFileFailsToLoad() {
		String file = "resources" + java.io.File.separator +
		"algs" + java.io.File.separator +
		"model" + java.io.File.separator +
		"search" + java.io.File.separator +
		"__MISSING_FILE__RIGHT__SampleFile.txt";
		File f = new File(file);
		
		try {
			new StringFileIterator (f);
			fail ("Should protect against invalid file.");
		} catch (java.io.FileNotFoundException fnfe) {
			// success
		}
	}
	
	public void testFileLoad() {
		String file = "resources" + java.io.File.separator +
		"algs" + java.io.File.separator +
		"model" + java.io.File.separator +
		"search" + java.io.File.separator +
		"SampleFile.txt";
		
		File f = new File(file);
		Iterator<String> it = null;
		try {
			it = new StringFileIterator (f);
			assertTrue (it.hasNext());
			assertEquals ("Now is the time", it.next());
			assertEquals ("for all good men", it.next());
			assertEquals ("to come to the aid", it.next());
			assertEquals ("of their country", it.next());
			assertFalse (it.hasNext());
		} catch (java.io.FileNotFoundException fnfe) {
			fail("unable to locate proscribed file.");
		}
		
		try {
			it.next();
			fail ("Extended iterator past end of file");
		} catch (NoSuchElementException nsee) {
			// ok
		}
		
		try {
			it.remove();
			fail ("Remove should not be supported.");
		} catch (UnsupportedOperationException uoe) {
			// ok
		}
	}
}
