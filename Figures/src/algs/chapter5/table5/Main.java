package algs.chapter5.table5;

import java.io.File;
import java.util.Iterator;

import algs.model.search.ListHashTable;
import algs.model.search.StringFileIterator;
import algs.model.tests.common.TrialSuite;

/** 
 * Compute statistics on the alg.model.search.HashTable example.
 * 
 * Run with extra Memory Space to be consistent with other runs.
 * 
 * @author George Heineman
 * @author Gary Pollice
 */
public class Main {
	
	public static void main (String[] args) throws java.io.FileNotFoundException {
		
		int NUM_TRIALS = 100;
		
		TrialSuite ts = new TrialSuite();
		TrialSuite bad_ts = new TrialSuite();
		
		String []searchWords = new String[213557];
		String []badWords = new String[213557];
		
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
			ListHashTable<String> ht = new ListHashTable<String>(sz);
			
			// we know the size of this list: 213,557 words.
			it = new StringFileIterator(new File (loc));
			ht.load(it);

			for (int t = 0; t < NUM_TRIALS; t++) {
				System.gc();
				long now = System.currentTimeMillis();
				for (int i = 0; i< searchWords.length; i++) {
					if (!ht.search(searchWords[i])) {
						System.out.println("FAILURE to find known word.");
						System.exit(0);
					}
				}
				long after = System.currentTimeMillis();
				ts.addTrial(sz, now, after);
				
				System.gc();
				now = System.currentTimeMillis();
				for (int i = 0; i< badWords.length; i++) {
					if (ht.search(badWords[i])) {
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
