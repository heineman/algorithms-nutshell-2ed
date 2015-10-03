package algs.chapter5.table4;

import java.io.File;
import java.util.Iterator;

import algs.model.search.ListHashTableReporter;
import algs.model.search.ListHashTable;
import algs.model.search.StringFileIterator;

public class DistributionWith1045875 {
	
	public static void main (String[] args) throws java.io.FileNotFoundException {
		
		ListHashTable<String> ht = new ListHashTable<String>(1045875);
		
		System.out.println("\nUsing StandardHash.hashCode");
		
		System.out.println("Loading word list...");
		
		String loc = "resources" + java.io.File.separatorChar +  
		 			 "algs" + java.io.File.separatorChar +
		 			 "chapter5" + java.io.File.separatorChar +
	 			     "words.english.txt";
		Iterator<String> it = new StringFileIterator(new File (loc));
		ht.load(it);
		
		ListHashTableReporter<String> reporter = new ListHashTableReporter<String>(ht);
		System.out.println(reporter.report());
	}
}
