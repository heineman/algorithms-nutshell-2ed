package algs.blog.searching.main;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

//import com.sun.org.apache.xpath.internal.compiler.Keywords;


import algs.blog.searching.gperf.GPerfThree;
import algs.blog.searching.gperf.GPerfTwo;
import algs.blog.searching.hashbased.HashbasedSearch;
import algs.blog.searching.hashbased.LinearProbe;
import algs.blog.searching.hashbased.Probe;
import algs.blog.searching.hashbased.QuadraticProbe;
import algs.blog.searching.search.ICollectionSearch;
import algs.blog.searching.special.SpecialHashKeys2;
import algs.blog.searching.special.SpecialHashKeys3;
import algs.blog.searching.special.SpecialHashbasedSearch;
import algs.blog.searching.tests.HashtableReport;
import algs.blog.searching.tree.BalancedTreeSearch;
import algs.model.search.StringFileIterator;
import algs.model.tests.common.TrialSuite;

/**
 * Compare variety of implementations.
 * <ol>
 * <li>JDK Hashtable 
 * <li>Linear Probing (with various delta values and table sizes)
 * <li>Quadratic Probing (with two coefficient pairs).
 * <li>Perfect Hashing (as computed by GPERF)
 * <li>Special Hashing (which takes advantage of the special structure of keys2 and keys3
 * </ol>
 * 
 * Set up the run configuration for this class to receive command line arguments that include:
 * 
 * $ADKHOME\Figures\resources\algs\chapter5\words.english.txt" keys_2.txt keys_3.txt
 * 
 * @author George Heineman
 */
public class Main {
	static File BASE = new File ("artifacts", "searching");
	
	/** Full set of words in the dictionary. */
	static String[] fullWords;
	
	/** Known key words in the smaller collection (sometimes keys2, sometimes keys3). */
	static String[] keyWords;

	/** Run all experiments with these number of trials. */
	public static final int NUM_TRIALS = 30;
	
	/**
	 * args[0] points to dictionary, args[1] points to keys2 and args[2] points to keys3
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length == 0) {
			String [] newArgs = {"words.english.txt", "keys_2.txt", "keys_3.txt"};
			args = newArgs;
		}
		
		fullWords = loadStringArray(new File (BASE, args[0]));
		
		performTwoKeys(new File (BASE, args[1]));
		performThreeKeys(new File (BASE, args[2]));
	}
	
	/**
	 * Load up an array of words from the given file.
	 * <p>
	 * Each word appears, one per line.
	 * 
	 * @param file
	 * @return non-null array of Strings from the given file.
	 */
	static String[] loadStringArray(File file) throws java.io.FileNotFoundException {
		// Load up words
		Iterator<String> it = new StringFileIterator(file);
		ArrayList<String> wordsAL = new ArrayList<String>();
		while (it.hasNext()) {
			wordsAL.add(it.next());
		}
		
		String[] words = wordsAL.toArray(new String[]{});
		wordsAL.clear();
		wordsAL = null;
		
		return words;
	}
	
	/**
	 * Carry out set of experiments on the keys2 source.
	 * 
	 * @param keys3       Location file of the keys2 collection of 641 special words.
	 */
	static void performTwoKeys(File keys2) throws java.io.FileNotFoundException {
		keyWords = loadStringArray(keys2);

		// Show case efficiency of special hashing...
		SpecialHashKeys2 sp = new SpecialHashKeys2();
		trial_specialBuild(sp);
		trial_search(sp, 641);
		
		// show Gperf trial
		GPerfTwo gperf = new GPerfTwo();
		trial_search(gperf, 641);
		
		// carry out the trials.
		trial_JDK(641, 11);                 // default initial hash table size
		trial_JDK(641, keyWords.length*4);  // try one that initially has 4 times the space.
		trial_JDK(641, 4413);               // try to create with perfect hash size.
		
		BalancedTreeSearch treeCollection = tree_build();	
		trial_search(treeCollection, 641);
		
		
		// produce same number as for perfect hashing. Here, delta is 1.
		LinearProbe probe = new LinearProbe (4413, 1);
		HashbasedSearch<String> keysLP = trial_build(4413, probe);
		trial_search(keysLP, 641);
		
		// produce same number as for perfect hashing. Here, delta is 1.
		QuadraticProbe quadProbe = new QuadraticProbe (4413, 0.5f, 0.5f);
		QuadraticProbe quadProbe2 = new QuadraticProbe (4413, 1, 1);
		
		HashbasedSearch<String> keysQP = trial_build(4413, quadProbe);
		trial_search(keysQP, 641);
		keysQP = trial_build(4413, quadProbe2);
		trial_search(keysQP, 641);

		// Try a sequence of experiments as the size of the array decreases from a scaled
		// 15-times as large, down to just over the base amount.
		float mult = 15;
		for (int i = 0; i < 10; i++){
			int size = (int)(keyWords.length * mult);
			
			LinearProbe lp = new LinearProbe (size, 1);
			HashbasedSearch<String> st = trial_build(size, lp);
			trial_search(st, 641);
			
			// try on each one...
			quadProbe = new QuadraticProbe (size, 0.5f, 0.5f);
			quadProbe2 = new QuadraticProbe (size, 1, 1);
			
			try {
				keysQP = trial_build(size, quadProbe);
				trial_search(keysQP, 641);
				
				keysQP = trial_build(size, quadProbe2);
				trial_search(keysQP, 641);
			} catch (RuntimeException re) {
				// might not be able to fill, if the quadratic formulae fail to 
				// properly iterate through the other bin positions
				System.err.println(re.getMessage());
			}
			
			mult = mult - (mult-1.0f)/2.0f;
		}
		
		// try linear probe with various delta values and prime base sizes (roughly doubled)
		int[] bases = new int[] { 641, 1283, 2557, 5113 };
		for (int p = 0; p < bases.length; p++) {
			System.out.println("Table Size:" + bases[p] + "\n");
			for (int delta = 1; delta < 10; delta++) {
				LinearProbe lp = new LinearProbe (bases[p], delta);
				trial_build(bases[p], lp);
			}
		}
	}
	

	/**
	 * Carry out set of experiments on the keys3 source.
	 * 
	 * @param keys3       Location file of the keys3 collection of 6,021 special words.
	 */
	static void performThreeKeys(File keys3) throws java.io.FileNotFoundException {
		keyWords = loadStringArray(keys3);

		// Show case efficiency of special hashing...
		SpecialHashKeys3 sp = new SpecialHashKeys3();
		trial_specialBuild(sp);
		trial_search(sp, 6021);
		
		// show Gperf trial
		GPerfThree gperf = new GPerfThree();
		trial_search(gperf, 6021);
		
		// carry out the trials.
		trial_JDK(6021, 11);                 // default initial hash table size
		trial_JDK(6021, keyWords.length*4);  // try one that initially has 4 times the space.
		trial_JDK(6021, 264240);             // try to create with perfect hash size.
		
		BalancedTreeSearch treeCollection = tree_build();	
		trial_search(treeCollection, 6021);
		
		
		// produce same number as for perfect hashing. Here, delta is 1.
		LinearProbe probe = new LinearProbe (264240, 1);
		HashbasedSearch<String> keysLP = trial_build(264240, probe);
		trial_search(keysLP, 6021);

		// produce same number as for perfect hashing. Here, delta is 1.
		QuadraticProbe quadProbe = new QuadraticProbe (264240, 0.5f, 0.5f);
		QuadraticProbe quadProbe2 = new QuadraticProbe (264240, 1, 1);
		
		HashbasedSearch<String> keysQP = trial_build(264240, quadProbe);
		trial_search(keysQP, 6021);
		keysQP = trial_build(264240, quadProbe2);
		trial_search(keysQP, 6021);		
		
		float mult = 15;
		for (int i = 0; i < 10; i++){
			int size = (int)(keyWords.length * mult);
			LinearProbe lp = new LinearProbe (size, 1);
			HashbasedSearch<String> st = trial_build(size, lp);
			trial_search(st, 6021);
						
			// try on each one...
			// try on each one...
			quadProbe = new QuadraticProbe (size, 0.5f, 0.5f);
			quadProbe2 = new QuadraticProbe (size, 1, 1);

			keysQP = trial_build(size, quadProbe);
			trial_search(keysQP, 6021);
			keysQP = trial_build(size, quadProbe2);
			trial_search(keysQP, 6021);
			
			mult = mult - (mult-1.0f)/2.0f;
		}
		
		// try linear probe with various delta values and prime base sizes (roughly doubled)
		int[] bases = new int[] { 6029, 12263, 24527, 59053 };
		for (int p = 0; p < bases.length; p++) {
			System.out.println("Table Size:" + bases[p] + "\n");
			for (int delta = 1; delta < 10; delta++) {
				LinearProbe lp = new LinearProbe (bases[p], delta);
				trial_build(bases[p], lp);
			}
		}
	}

	/**
	 * Run a trial on the key words in {@link #keyWords} using the specially constructed
	 * collection {@link SpecialHashKeys2}.
	 * <p>
	 * Carries out a series of experiments and returns a {@link HashbasedSearch} structure
	 * that is indicative of the type of object created.
	 * 
	 * @return  The computed {@link ICollectionSearch} entity to manage special search.
	 */
	private static void trial_specialBuild(SpecialHashbasedSearch hbs) {
		TrialSuite ts = new TrialSuite();
		System.out.println("Build Special Array for:" + hbs);
		
		// Time to build array.
		for (int i = 0; i < NUM_TRIALS; i++) {
			// reset for upcoming trial
			hbs.reset();
			System.gc();
			
			// run trial.
			long before = System.currentTimeMillis();
			
			for (int w = 0; w < keyWords.length; w++) {
				hbs.insert(keyWords[w]);
			}
			long after = System.currentTimeMillis();

			ts.addTrial(keyWords.length, before, after);
		}
		
		// generate information.
		System.out.println(hbs.info());
		System.out.println(ts.computeTable());
	}
	
	/**
	 * Carry out a search for all known words within the given collection and 
	 * produce report on execution times.
	 * <p>
	 * It is assumed that the table does not change during the search execution, otherwise
	 * the individual trials executed within this method would be biased.
	 * 
	 * @param collection    Collection being searched
	 * @param num           The expected number of words that will be found within this table.
	 */
	private static void trial_search(ICollectionSearch<String> collection, int num) {
		TrialSuite searchTS = new TrialSuite();
		
		System.out.println("Search trial:" + collection);
		
		for (int t = 0; t < NUM_TRIALS; t++) {
			int found = 0;
			long before = System.currentTimeMillis();
			for (int w = 0; w < fullWords.length; w++) {
				if (collection.exists(fullWords[w])) {
					found++;
				}
			}
			long after = System.currentTimeMillis();
			searchTS.addTrial(num, before, after);
			if (num != found) {
				System.err.println("Error in search: proper number of elements not found:" + found);
			}
		}
		
		System.out.println(searchTS.computeTable());
	}
	
	/**
	 * Run a trial on the key words in {@link #keyWords} using the given {@link Probe}
	 * strategy with an initial array size and given linear delta.
	 * <p>
	 * Carries out a series of experiments and returns a {@link HashbasedSearch} structure
	 * that is indicative of the type of object created.
	 * 
	 * @param   size      The desired initial size.
	 * @param   strategy  The probe strategy to use.
	 * @return  The computed {@link HashbasedSearch} structure for the {@link Keywords}.
	 */
	private static HashbasedSearch<String> trial_build(int size, Probe strategy) {
		if (size != strategy.tableSize) {
			throw new IllegalArgumentException ("Probe strategy assumes different hashtable size:" + 
					strategy.tableSize + " vs. " + size);
		}
		
		TrialSuite ts = new TrialSuite();
		System.out.println("Build Array of size " + size + " with probe strategy: " + strategy);
		
		// Time to build array.
		HashbasedSearch<String> probe = null;
		for (int i = 0; i < NUM_TRIALS; i++) {
			// reset for upcoming trial
			probe = null;
			System.gc();
			
			// run trial.
			long before = System.currentTimeMillis();
			probe = new HashbasedSearch<String>(size, strategy);
			
			for (int w = 0; w < keyWords.length; w++) {
				probe.insert(keyWords[w]);
			}
			long after = System.currentTimeMillis();

			ts.addTrial(keyWords.length, before, after);
		}
		
		// generate information.
		System.out.println(probe.info());
		System.out.println(ts.computeTable());
		return probe;
	}

	/**
	 * Run a trial on the key words in {@link #keyWords} with an initial hashtable size
	 * as specified.
	 * <p>
	 * Note that the JDK is able to rehash as the number of elements exceeds a threshold.
	 * <p>
	 * Carries out a series of experiments and returns a hashtable that is indicative of
	 * the type of hashtable created by the JDK.
	 * 
	 * @param   num    Number of expected elements to locate from fullWords.
	 * @param   size   The desired initial size.
	 * @return  The computed hashtable for the keywords.
	 */
	@SuppressWarnings("deprecation")
	private static Hashtable<String,Boolean> trial_JDK(int num, int size) {
		TrialSuite buildJDKTS = new TrialSuite();

		System.out.println("JDK time to build hashtable with initial size:" + size);
		
		// Time to build hashtable. Garbage collect before taking timing measurements.
		Hashtable<String,Boolean> table = new Hashtable<String,Boolean>(size);
		for (int i = 0; i < NUM_TRIALS; i++) {
			// reset for the upcoming trial.
			table.clear();
			table = null;
			System.gc();
			
			// carry out trial.
			long before = System.currentTimeMillis();
			table = new Hashtable<String,Boolean>(size);
			for (int w = 0; w < keyWords.length; w++) {
				table.put(keyWords[w], Boolean.TRUE);
			}
			long after = System.currentTimeMillis();

			buildJDKTS.addTrial(keyWords.length, before, after);
		}
		
		// Generate report for this JDK
		HashtableReport report;
		try {
			report = HashtableReport.report(table);
			System.out.println(report.info());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println ("Build Time:");
		System.out.println(buildJDKTS.computeTable());
		
		TrialSuite searchTS = new TrialSuite();
		
		for (int t = 0; t < NUM_TRIALS; t++) {
			int found = 0;
			long before = System.currentTimeMillis();
			for (int w = 0; w < fullWords.length; w++) {
				if (table.containsKey(fullWords[w])) {
					found++;
				}
			}
			long after = System.currentTimeMillis();
			searchTS.addTrial(found, before, after);
			if (num != found) {
				System.err.println("Error in search: proper number of elements not found:" + found);
			}
		}
		
		System.out.println ("Search Time:");
		System.out.println(searchTS.computeTable());
		
		return table;
	}
	

	
		private static BalancedTreeSearch tree_build() {
			
			TrialSuite ts = new TrialSuite();
			System.out.println("Build BalancedTree");
			
			// Time to build array.
			BalancedTreeSearch tree = null;
			for (int i = 0; i < NUM_TRIALS; i++) {
				// reset for upcoming trial
				tree = null;
				System.gc();
				
				// run trial.
				long before = System.currentTimeMillis();
				tree = new BalancedTreeSearch();
				
				for (int w = 0; w < keyWords.length; w++) {
					tree.insert(keyWords[w]);
				}
				long after = System.currentTimeMillis();

				ts.addTrial(keyWords.length, before, after);
			}
			
			// generate information.
			System.out.println(tree);
			System.out.println(ts.computeTable());
			return tree;
		}
}
