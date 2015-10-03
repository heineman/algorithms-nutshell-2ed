/**
 * @file SearchNull.java    Task to perform searches in unordered array with Null check.
 * @brief 
 *    Construct example collection from permutations of the string 'abcdef' and
 * allow command-line interface to search for a target string from within 
 * this collection and return its time in milliseconds. Search for NULL is
 * included in the timing
 *
 * @author George Heineman
 * @date 6/15/08
 */
public class SearchNull {

    /** Base string for permutations. */
    public static final String baseString = "abcdef";

    /** Sise of base string. */
    public static int elementSize = 6;

    /** Build up the array of string permutations of size n. */
    public static String[] buildInput (int n) {
	String[]strings = new String[n];
	for (int i = 0; i < n; i++) {
	    StringBuffer mixed = new StringBuffer (baseString);

	    for (int s = 0; s < elementSize; s++) {
		int j;
		char c,d;

		// Compute random value
		
		j = 1 + (int) (elementSize * Math.random());
		j %= elementSize;

		c = mixed.charAt(s);
		d = mixed.charAt(j);
		mixed.replace(s,s+1,""+d);
		mixed.replace(j,j+1,""+c);
	    }

	    strings[i] = new String(mixed);
	}

	return strings;
    }

    /** Search for target string in array. Check for NULL included. */
    public static boolean search (String[]strs, String target) {
	for (Object o : strs)
	    if (o != null && o.equals (target)) 
		return true;
	return false;
    }


    /** Launch the main search application. */
    public static void main (String []args) {
	if (args.length == 0) {
	    System.out.println ("Usage: java Search n target");
	    System.out.println ("   n is the number of random permutations of " + baseString + " to use as collection.");
	    System.out.println ("   target is ideally a string of six characters\n");
	    System.exit(-1);
	}

	int n = Integer.valueOf(args[0]);
	String target = args[1];

	String []els = buildInput(n);

	long now = System.currentTimeMillis();
	boolean b = search(els, target);
	long time = System.currentTimeMillis() - now;
	System.out.println ("search time in ms:" + time);
	System.out.println ("search result:" + b);
    }
}
