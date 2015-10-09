package algs.blog.searching.gperf;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import algs.blog.searching.search.ICollectionSearch;

/* C code produced by gperf version 3.0.3 */
/* Command-line: ./src/gperf ../three.keys  */
/* Computed positions: -k'1-11,$' */
public class GPerfThree  implements ICollectionSearch<String> {

	public static final int TOTAL_KEYWORDS = 6027;
	public static final int MIN_WORD_LENGTH = 2;
	public static final int MAX_WORD_LENGTH = 23;
	public static final int MIN_HASH_VALUE = 89;
	public static final int MAX_HASH_VALUE = 264239;

	/**
	 * Words which are to be loaded up from file. Cannot inline the array here because
	 * the Java compiler does not handle more than 65,536 pre-defined constants in a single
	 * Java file. 
	 */
	String []wordlist = null; 

	/**
	 * We have to load up the words from disk because Java restricts the size of Classes 
	 * that seek to use auto-initialized 
	 */
	public GPerfThree() {
		File file = new File (GPerfThree.class.getName() + ".wordList");
		try {
			// allocate space and load up from file.
			wordlist = new String[MAX_HASH_VALUE+1];
			Scanner sc = new Scanner (file);
			int idx = 0;
			while (sc.hasNext()) {
				String s = sc.nextLine();
				StringTokenizer st = new StringTokenizer(s, ",",true);
				while (st.hasMoreTokens()) {
					String tok = st.nextToken();
					if (tok.equals (",")) {
						wordlist[idx++] = null;
					} else {
						wordlist[idx++] = tok;
						// drain the comma
						if (st.hasMoreTokens()) { st.nextToken(); }
					}
				}
			}

			sc.close();
		} catch (IOException ioe) {
			System.err.println("Unable to access file containing words for hashtable:" + file);
		}
	}


	static int asso_values[] =
	{
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240,     15,  29694,   2180,
		5,      0,    620,   5585,   1500,     40,  42018,  13618,      0,    915,
		20,     90,   7599,  19899,     55,    155,    325,  13172,  44208,  21484,
		57488,   3315,  58263,  82141,  22833,  58758,  57178,  87311, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240, 250240,
		250240, 250240, 250240, 250240, 250240, 250240, 250240
	};

	static char bytes[] = new char[MAX_WORD_LENGTH];
	static int hash (String str) {
		int len = str.length();
		int hval = len;
		str.getChars(0, (hval < MAX_WORD_LENGTH) ? hval : MAX_WORD_LENGTH, bytes, 0);

		switch (hval) {
		default:
			hval += asso_values[bytes[10]];
			/*FALLTHROUGH*/
		case 10:
			hval += asso_values[bytes[9]];
			/*FALLTHROUGH*/
		case 9:
			hval += asso_values[bytes[8]];
			/*FALLTHROUGH*/
		case 8:
			hval += asso_values[bytes[7]];
			/*FALLTHROUGH*/
		case 7:
			hval += asso_values[bytes[6]];
			/*FALLTHROUGH*/
		case 6:
			hval += asso_values[bytes[5]];
			/*FALLTHROUGH*/
		case 5:
			hval += asso_values[bytes[4]+3];
			/*FALLTHROUGH*/
		case 4:
			hval += asso_values[bytes[3]];
			/*FALLTHROUGH*/
		case 3:
			hval += asso_values[bytes[2]];
			/*FALLTHROUGH*/
		case 2:
			hval += asso_values[bytes[1]+5];
			/*FALLTHROUGH*/
		case 1:
			hval += asso_values[bytes[0]+11];
			break;
		}

		return hval + asso_values[bytes[len-1]];
	}




	@Override
	public boolean exists(String target) {
		int len = target.length();
		if (len <= MAX_WORD_LENGTH && len >= MIN_WORD_LENGTH)
		{
			int key = hash (target);

			if (key <= MAX_HASH_VALUE && key >= 0)
			{
				String s = wordlist[key];

				if (s != null && s.charAt(0) == target.charAt(0) && s.equals(target)) {
					return true;
				}
			}
		}

		return false;
	}


}
