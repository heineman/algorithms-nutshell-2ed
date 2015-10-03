/**
 * @file Comparison.java   Time 100,000 allocations of a table of 100 strings.
 * @brief 
 *   Allocate 100,000 tables of String[] and time the result. Used as
 *   a baseline for comparing against comparison.cxx
 *
 * @author George Heineman
 * @date 6/15/08
 */
public class Comparison {

    /** Size of the table. */
    public static int MAX = 100;

    /** Return the table of String objects containing "george". */
    public static String[] strings() {
	String[]update = new String[MAX];
	for (int i = 0; i < MAX; i++) {
	    update[i] = new String ("george");
	}

	return update;
    }

    /** Launch the program and time results. */
    public static void main (String []args) {
	long first = System.currentTimeMillis();
	long sum=0;
	for (int i = 0; i < 100000; i++) {
	    String[] str = strings();
	    sum += str[0].charAt(0);
	}
	long done = System.currentTimeMillis();

	System.out.println ((done - first)*1000);
	System.out.println (sum);
    }
}
