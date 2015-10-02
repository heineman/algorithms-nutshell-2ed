package algs.model.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Return an Iterator&lt;String&gt; for the strings in a File.
 * 
 * Useful to populate a Hashtable with initial values.
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StringFileIterator implements Iterator<String> {

	/** Use scanner to process file. If null, then no elements. */
	Scanner sc;
	
	/** 
	 * On constructor set up the Scanner, if possible.
	 * 
	 * @param f   File from which to read strings, one per line.
	 * @exception  FileNotFoundException  if file cannot be opened.
	 */
	public StringFileIterator(File f) throws FileNotFoundException {
		sc = new Scanner(f);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		if (sc == null) { return false; }
		
		return sc.hasNextLine();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public String next() throws NoSuchElementException {
		if (sc == null) {
			throw new NoSuchElementException ("End of file reached.");
		}
		
		String s = sc.nextLine();
		
		// actively take steps to close down.
		if (!sc.hasNextLine()) {
			sc.close();
			sc = null;
		}
		
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException("Unable to delete from StringFileIterator.");
	}
}
