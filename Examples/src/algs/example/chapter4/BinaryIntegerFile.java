package algs.example.chapter4;

import java.io.*;

import algs.model.sort.MergeSortFileMapped;

/**
 * Demonstrates merge sort on an external file containing collections of integers in binary format.
 * 
 * This program generates a timing table. 
 *  
 * @author George Heineman
 * @version 2.0, 8/6/15
 * @since 2.0
 */
public class BinaryIntegerFile {
	public static void main(String[] args) throws IOException {
		for (int numIntegers = 16384; numIntegers <= 1048576; numIntegers *= 2) {
			String tmpDir = System.getProperty("java.io.tmpdir");
			File f = new File (tmpDir, "IntegerFile.bin");
			System.out.println(f);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
			for (int i = 0; i < numIntegers; i++) {
				dos.writeInt((int)(Math.random()*numIntegers));
			}
			dos.close();

			long now = System.currentTimeMillis();
			MergeSortFileMapped.mergesort(f);
			System.out.println(numIntegers + "," + (System.currentTimeMillis() - now) + " ms.");
			
			f.delete();
		}
	}
}