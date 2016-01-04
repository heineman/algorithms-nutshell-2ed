package algs.blog.searching.main;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

import algs.model.search.StringFileIterator;

/**
 * Construct sample word list such that we generate up to 8000 unique
 * strings whose triple of first,second-to-last,and middle letters are
 * distinct. Restrict only to letters [a-t] to maximize density of words
 * in the set.
 *
 * Input:  args[0] is text file with words, one per line. Word list does
 *         not have to be in lower case, since it is converted here.
 * Output: print word list to stdout.
 *
 * Sample:
 *
 *   java ConstructThree $ADKHOME/Blogs/artifacts/searching/words.english.txt
 *
 * If all letters [a-z] are allowed, then this program will locate 11,097
 * words, but this amounts to just about 63% of the available 17576 spots.
 * If we restrict only to letters [a-t] for the index characters, then we
 * locate 6,021 words (for a 75% density) out of a possible 8,000.
 * <p>
 * Output is placed in a file called "keys_3.txt" in the current directory.
 */
public class ConstructThree {

    public static void main (String []args) throws Exception {
    	PrintWriter pw = new PrintWriter(new File ("keys_3.txt"));
        String [][][]words = new String[20][20][20];

        // Iterate over each string in file and store if one not
        // already found for (first,second-to-last) character.
        Iterator<String> it = new StringFileIterator(new File (args[0]));

        while (it.hasNext()) {
            String w = it.next();
            if (w.length() > 2) {
	            String cmp = w.toLowerCase();

	            int firstIndex = (int)(cmp.charAt(0)-'a');
	            int middleIndex = (int)(cmp.charAt(-1+w.length()/2)-'a');
	            int lastIndex = (int)(cmp.charAt(w.length()-2)-'a');
	
	            if (firstIndex < 20 && middleIndex < 20 && lastIndex < 20) {
	                if (words[firstIndex][middleIndex][lastIndex] == null) {
	                    words[firstIndex][middleIndex][lastIndex] = w;
	                    pw.println (w);
	                }
	            }
            }
        }
        
        pw.flush();
        pw.close();
    }
}
