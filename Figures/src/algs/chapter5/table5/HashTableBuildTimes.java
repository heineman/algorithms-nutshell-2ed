package algs.chapter5.table5;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import algs.model.search.ListHashTable;
import algs.model.search.StringFileIterator;
import algs.model.tests.common.TrialSuite;

/**
 * It is so hard to provide timing when loading up from a file, so instead
 * we compute the build times for all hashtables separately.
 * 
 * @author George Heineman
 * @author Gary Pollice
 */
public class HashTableBuildTimes {
	
	public static void main(String[] args) throws java.io.FileNotFoundException {
		TrialSuite main_ts = new TrialSuite();
		TrialSuite extended_ts = new TrialSuite();  /* alpha=.75f */
		TrialSuite modest_ts = new TrialSuite();    /* alpha=4.0f */
		TrialSuite norehash_ts = new TrialSuite();    /* alpha=HIGH */
		int numWords = 213557; 
		
		String loc = "resources" + java.io.File.separatorChar +  
					 "algs" + java.io.File.separatorChar +
					 "chapter5" + java.io.File.separatorChar +
					 "words.english.txt";

		ArrayList<String> words = new ArrayList<String> ();
		Iterator<String> it = new StringFileIterator(new File (loc));
		while (it.hasNext()) {
			words.add(it.next());
		}
		
		int NUM_TRIALS = 100;
		for (int k = 12; k <= 20; k++) {
			long now, after;
			System.out.println(k + "...");
			int sz = (int) (Math.pow(2, k))-1;
			java.util.Hashtable<String,Boolean> htnative;
			
			for (int t = 0; t < NUM_TRIALS; t++) {
				System.gc();
				
				ListHashTable<String> ht = new ListHashTable<String>(sz);
				
				// we know the size of this list: 213,557 words.
				it = words.iterator();
				System.gc();
				
				// (1) only time the loading up of MAIN
				// --------------------------------------
				now = System.currentTimeMillis();
				ht.load(it);
				after = System.currentTimeMillis();
				main_ts.addTrial(sz, now, after);
				ht = null;
				
				// now time HashTable (default capacity of .75f)
				htnative = new java.util.Hashtable<String,Boolean> (sz); 
				
				it = words.iterator();
				System.gc();
				// (2) only time the loading up of Extended (capacity=.75)
				// --------------------------------------------------------
				now = System.currentTimeMillis();
				while (it.hasNext()) {
					htnative.put(it.next(), Boolean.TRUE);
				}
				after = System.currentTimeMillis();
				extended_ts.addTrial(sz, now, after);
				htnative = null;
				
				// now time HashTable (default capacity of 4.0f)
				htnative = new java.util.Hashtable<String,Boolean> (sz, 4.0f); 
				
				it = words.iterator();
				System.gc();
				// (3) only time the loading up of Extended Modest Rehash
				// ------------------------------------------------------
				now = System.currentTimeMillis();
				while (it.hasNext()) {
					htnative.put(it.next(), Boolean.TRUE);
				}
				after = System.currentTimeMillis();
				modest_ts.addTrial(sz, now, after);
				htnative = null;
				
				// now time HashTable (default capacity of HIGH so no rehash)
				float cap = numWords;
				cap /= sz;
				htnative = new java.util.Hashtable<String,Boolean> (sz, 1+cap); 
				
				it = words.iterator();
				System.gc();
				// (3) only time the loading up of Extended Modest Rehash
				// ------------------------------------------------------
				now = System.currentTimeMillis();
				while (it.hasNext()) {
					htnative.put(it.next(), Boolean.TRUE);
				}
				after = System.currentTimeMillis();
				norehash_ts.addTrial(sz, now, after);
				htnative = null;
			}
		}
		
		
		System.out.println("MAIN");
		System.out.println(main_ts.computeTable());	

		System.out.println("EXTENDED");
		System.out.println(extended_ts.computeTable());

		System.out.println("MODEST");
		System.out.println(modest_ts.computeTable());
		
		System.out.println("NO REHASH");
		System.out.println(norehash_ts.computeTable());
		
	}
}
