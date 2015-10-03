package algs.chapter5.example5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

import algs.model.array.Selection;

/**
 * See if any of the SimpleString objects within the words.english.txt file of 
 * 213,557 words has any two whose hashCode() method are identical.
 * 
 * @author George Heineman
 */
public class DuplicateHashcode {

	public static void main (String []args) {
		
		try {
			Hashtable<Integer,SimpleString> hashes = new Hashtable<Integer,SimpleString>();
			
			
			String loc = "resources" + java.io.File.separatorChar +  
			 			 "algs" + java.io.File.separatorChar +
			 			 "chapter5" + java.io.File.separatorChar +
			 			 "words.english.txt";
			Scanner sc = new Scanner (new File (loc));
			int idx = 0;
			int numDuplicate = 0;
			while (sc.hasNextLine()) {
				SimpleString ss = new SimpleString(sc.nextLine());
				int h = ss.hashCode();
				idx++;
				
				if (hashes.containsKey(h)) {
					System.out.println("Duplicate Found!");
					System.out.println("  1. " + hashes.get(h));
					System.out.println("  2. " + ss.toString() + " [default hashCode:" + ss.toString().hashCode() + "]");
					System.out.println("Shared hashCode:" + h);
					numDuplicate++;
				} else {
					hashes.put(h, ss);
				}
			}
			sc.close();
			
			System.out.println("Processed " + idx + " words and found " + numDuplicate + " duplicate hashcode values.");
			
			// count how many are only apart by one.
			if (numDuplicate != 0) {
				Integer largeArray[] = new Integer[hashes.size()];
				idx = 0;
				for (int h : hashes.keySet()) {
					largeArray[idx++] = h;
				}
				
				Selection.qsort(largeArray, 0, largeArray.length-1);
				
				int count = 0;
				for (int i = 1; i < largeArray.length; i++) {
					if (largeArray[i] - largeArray[i-1] == 1) {
						count++;
					}
				}
				
				System.out.println("There are " + count + " pairs of words whose hash is 1 away from the other.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
