package algs.chapter5.table7;

import java.io.File;
import java.util.Iterator;
import algs.model.search.StringFileIterator;

/**
 * Use profiling to determine how many times default java rehashes.  
 * <p>
 * Argument to pass in is either a float representing the load. With no
 * argument present, the default is to construct a hashtable sufficient in
 * size to store the entire dictionary.
 * 
 * @author George Heineman
 */
public class Main {
	/** dictionary information. */
	static int numWords = 213557; 
	static String []searchWords = new String[numWords];
	static String []badWords = new String[numWords];
	
	// how many rehashes
	int numRehashes;
	
	public static void main(String[] args) throws java.io.FileNotFoundException {
		new Main().doit (0.75f);
		new Main().doit (4.0f);
	}

	/**
	 * Reset rehash count
	 */
	void reset() {
		numRehashes = 0;
	}

	
	/**
	 * Produce tabular information for multiple runs.
	 * 
	 * @param alpha ratio for hashtable
	 */
	private void doit(float alpha) throws java.io.FileNotFoundException {
		// construct the base set of words to search. Construct similar BAD 
		// set whose last character is a "*" which makes it invalid.
		int idx = 0;
		String loc = "resources" + java.io.File.separatorChar +  
					 "algs" + java.io.File.separatorChar +
					 "chapter5" + java.io.File.separatorChar +
					 "words.english.txt";

		System.out.println("Loading dictionary...");
		Iterator<String> it = new StringFileIterator(new File (loc));
		while (it.hasNext()) {
			String s = it.next();
			searchWords[idx] = s;
			badWords[idx++] = s.substring(0,s.length()-1) + "*";
		}
		
		for (int k = 12; k <= 20; k++) {
			int b = (int) (Math.pow(2, k))-1;
			
			// Natively create HashTable<String,Boolean> with a load capacity
			// that prevents rehash().
			float lf;
			if (alpha == 0) {
				lf = 1+1.0f*numWords/b;
			} else {
				lf = alpha;
			}
			
			// Extend Hashtable to accumulate rehash requests. Can do this because this method is
			// only protected, not private.
			java.util.Hashtable<String,Boolean> ht = 
				new java.util.Hashtable<String,Boolean> (b, lf) {
				
				/** Keep Eclipse happy. */
				private static final long serialVersionUID = 1L;

				@Override
				protected void rehash() {
					numRehashes++;
					super.rehash();
				}

			};
			
			// we know the size of this list: 213,557 words.
			System.gc();
			reset();
			for (int i = 0; i < searchWords.length; i++) {
				ht.put(searchWords[i], Boolean.TRUE);
			}
			
			// output info
			System.out.println(b + "," + alpha + "," + numRehashes);
		}

	}
}
