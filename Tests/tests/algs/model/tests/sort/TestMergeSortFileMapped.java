package algs.model.tests.sort;


import org.junit.Test;

import junit.framework.TestCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import algs.model.sort.MergeSortFileMapped;

public class TestMergeSortFileMapped extends TestCase {

	@Test
	public void testMerge() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		File f = new File (tmpDir, "TestIntegerFile.bin");
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
			dos.writeInt(9);
			dos.writeInt(1);
			dos.writeInt(3);
			dos.writeInt(6);
			dos.writeInt(2);
			dos.writeInt(7);
			dos.writeInt(8);
			dos.writeInt(5);
			dos.writeInt(4);
			dos.close();
	
			MergeSortFileMapped.mergesort(f);

			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			for (int i = 1; i <= 9; i++) {
				int val = dis.readInt();
				assertEquals (i, val);
			}
			dis.close();
			assertTrue(f.delete());
			
		} catch (Exception e) {
			fail (e.getMessage());
		}
		
	}
}
