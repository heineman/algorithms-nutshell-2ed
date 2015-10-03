package algs.chapter5.table5;

import java.io.File;
import java.util.Iterator;

import algs.model.search.StringFileIterator;
import algs.model.tests.common.TrialSuite;

/** 
 * Compute average chains and compare against java.util.Hashtable
 * 
 * MUST run with extra Memory Space
 * 
 * set Load Capacity to n/b to prevent any rehash from occurring.
 * 
 * To see the size of the hash table *AFTER* all rehashing, must run in the
 * debugger manually (since those fields are private, after all).
 * 
 * @author George Heineman
 * @author Gary Pollice
 */
public class ExtendedNoRehash {
	
	public static void main (String[] args) throws java.io.FileNotFoundException {
		
		int NUM_TRIALS = 100;
		
		TrialSuite ts = new TrialSuite();
		TrialSuite bad_ts = new TrialSuite();
		
		int numWords = 213557; 
		String []searchWords = new String[numWords];
		String []badWords = new String[numWords];
		
		// construct the base set of words to search. Construct similar BAD 
		// set whose last character is a "*" which makes it invalid.
		int idx = 0;
		String loc = "resources" + java.io.File.separatorChar +  
					 "algs" + java.io.File.separatorChar +
					 "chapter5" + java.io.File.separatorChar +
					 "words.english.txt";

		Iterator<String> it = new StringFileIterator(new File (loc));
		while (it.hasNext()) {
			String s = it.next();
			searchWords[idx] = s;
			badWords[idx++] = s.substring(0,s.length()-1) + "*";
		}
		
		for (int k = 12; k <= 20; k++) {
			System.out.println(k + "...");
			int sz = (int) (Math.pow(2, k))-1;
			
			// Natively create HashTable<String,Boolean> with a load capacity
			// that prevents rehash().
			float lf = 1+1.0f*numWords/sz;
			java.util.Hashtable<String,Boolean> htnative = 
				new java.util.Hashtable<String,Boolean> (sz, lf); 
			
			System.out.println("load factor to avoid any rehash: " + lf);
			
			// we know the size of this list: 213,557 words.
			System.gc();
			it = new StringFileIterator(new File (loc));
			while (it.hasNext()) {
				String word = it.next();
				htnative.put(word, Boolean.TRUE);
			}

			
			for (int t = 0; t < NUM_TRIALS; t++) {
				System.gc();
				long now = System.currentTimeMillis();
				for (int i = 0; i< searchWords.length; i++) {
					if (!htnative.containsKey(searchWords[i])) {
						System.out.println("FAILURE to find known word.");
						System.exit(0);
					}
				}
				long after = System.currentTimeMillis();
				ts.addTrial(sz, now, after);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int i = 0; i< badWords.length; i++) {
					if (htnative.containsKey(badWords[i])) {
						System.out.println("FOUND bogus word.");
						System.exit(0);
					}
				}
				after = System.currentTimeMillis();
				bad_ts.addTrial(sz, now, after);
			}
		}
		
		System.out.println("p=1.0");
		System.out.println(ts.computeTable());
		
		System.out.println("p=0.0");
		System.out.println(bad_ts.computeTable());

	}
}
