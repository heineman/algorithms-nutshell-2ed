package algs.blog.searching.main;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import algs.model.search.StringFileIterator;

/**
 * Construct sample word list such that we generate up to 676 unique
 * strings whose pair of first and second-to-last letters are distinct.
 *
 * Input:  args[0] is text file with words, one per line. Word list does
 *         not have to be in lower case, since it is converted here.
 * Output: print word list to stdout.
 *
 * Sample:
 *
 *   java ConstructTwo $ADKHOME/Blogs/artifacts/searching/words.english.txt
 *
 * Generates a set of 641 words. 94.8% dense.
 */
public class ConstructTwo {

    public static void main (String []args) throws Exception {
    	PrintWriter pw = new PrintWriter(new File ("keys_2.txt"));
        String [][]words = new String[26][26];

        // Iterate over each string in file and store if one not
        // already found for (first,second-to-last) character.
        Iterator<String> it = new StringFileIterator(new File (args[0]));

        while (it.hasNext()) {
            String w = it.next();
            if (w.length() > 2) {
	            String cmp = w.toLowerCase();
	            
	            int firstIndex = (int)(cmp.charAt(0)-'a');
	            int lastIndex = (int)(cmp.charAt(w.length()-2)-'a');
	            if (words[firstIndex][lastIndex] == null) {
	                words[firstIndex][lastIndex] = w;
	                pw.println (w);
	            }
            }
        }
        
        pw.flush();
        pw.close();
    }
}
