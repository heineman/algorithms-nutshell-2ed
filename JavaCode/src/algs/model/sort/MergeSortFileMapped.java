package algs.model.sort;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * Demonstrates efficient merge sort by using the ability to memory map files for rapid access. 
 * 
 * There are some windows-specific code, unfortunately, that is needed to properly release resources.
 *  
 * @author George Heineman
 * @version 2.0, 8/6/15
 * @since 2.0
 */
public class MergeSortFileMapped {

	static void copyFile(File src, File dest) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream (dest);
		byte[] bytes = new byte[4*1048576];
		int numRead;
		while ((numRead = fis.read(bytes)) > 0) {
			fos.write(bytes, 0, numRead);
		}
		fis.close();
		fos.close();
	}

	// inelegant need to manually close on some operating systems (Windows)
	static void closeDirectBuffer(MappedByteBuffer mbb) {
	    try {
	        java.lang.reflect.Method cleaner = mbb.getClass().getMethod("cleaner");
	        cleaner.setAccessible(true);
	        java.lang.reflect.Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
	        clean.setAccessible(true);
	        clean.invoke(cleaner.invoke(mbb));
	    } catch(Exception ex) { }	   
	}
	
	public static void mergesort (File A) throws IOException {
		File copy = File.createTempFile("Mergesort", ".bin");
		copyFile (A, copy);

		RandomAccessFile src = new RandomAccessFile(A, "rw");
		RandomAccessFile dest = new RandomAccessFile(copy, "rw");
		FileChannel srcC = src.getChannel();
		FileChannel destC = dest.getChannel();
		MappedByteBuffer srcMap = srcC.map (FileChannel.MapMode.READ_WRITE, 0, src.length());
		MappedByteBuffer destMap = destC.map (FileChannel.MapMode.READ_WRITE, 0, dest.length());

		mergesort (destMap, srcMap, 0, (int) A.length());
		
		// The following two invocations are needed only on Windows Platform:
		closeDirectBuffer (srcMap);
		closeDirectBuffer (destMap);
		src.close();
		dest.close();
		copy.deleteOnExit();
	}

	static void mergesort(MappedByteBuffer A, MappedByteBuffer result, 
			int start, int end) throws IOException {

		if (end - start < 8) { 
			return; 
		}

		if (end - start == 8) {
			result.position(start);
			int left = result.getInt();
			int right = result.getInt();
			if (left > right) {
				result.position (start);
				result.putInt (right);
				result.putInt (left);
			}
			return;
		}

		int mid = (end + start)/8*4;
		mergesort (result, A, start, mid);
		mergesort (result, A, mid, end);

		result.position(start);
		for (int i = start, j = mid, idx=start; idx < end; idx += 4) {
			int Ai = A.getInt (i);
			int Aj = 0;
			if (j < end) { Aj = A.getInt (j); }
			if (j >= end || (i < mid && Ai < Aj)) { 
				result.putInt (Ai);
				i += 4;
			} else {
				result.putInt (Aj);
				j += 4;
			}
		}
	}
}