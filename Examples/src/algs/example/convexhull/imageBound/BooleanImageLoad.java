package algs.example.convexhull.imageBound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Load up an image from a file containing a rectangular input.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BooleanImageLoad {
	
	/**
	 * Helper function to load up images.
	 * 
	 * @param file      file that contains ASCII characters
	 */
	static ArrayList<String> loadImage (File file) throws FileNotFoundException, RuntimeException {
		if (file == null) return null;
		
		Scanner sc;
		sc = new Scanner(file);
		
		ArrayList<String> rows = new ArrayList<String>();
		String s = sc.nextLine();
		int len = s.length();
		int idx = 1;
		
		// load up each row into the ArrayList and ensure all have the proper length.
		rows.add(s);
		while (sc.hasNext()) {
			idx++;
			
			s = sc.nextLine();
			if (s.length() != len) {
				sc.close();
				throw new RuntimeException ("Line " + idx + " only has " + s.length() + " characters (should have " + len + ")");
			}
			
			rows.add(s);
		}
		
		sc.close();
		return rows;
	}
	
	/**
	 * Helper function to load up images, and pad too-short lines with given padCharacter.
	 * 
	 * @param file      file that contains ASCII characters
	 * @param padChar   the character to use as a no-character
	 */
	static ArrayList<String> loadImagePad (File file, char padChar) throws FileNotFoundException, RuntimeException {
		if (file == null) return null;
		
		Scanner sc;
		sc = new Scanner(file);
		
		ArrayList<String> rows = new ArrayList<String>();
		String s = sc.nextLine();
		int len = s.length();
		
		// load up each row into the ArrayList and ensure all have the proper length.
		rows.add(s);
		while (sc.hasNext()) {
			s = sc.nextLine();
			if (s.length() != len) {
				StringBuilder sb = new StringBuilder(s);
				for (int i = 0; i < len-s.length(); i++) {
					sb.append(padChar);
				}
				s = sb.toString();
			}
			
			rows.add(s);
		}
		
		sc.close();
		return rows;
	}
	
	/**
	 * Load up an image from the given file, where only the off character is known.
	 *
	 * Pads short lines with offChar.
	 *
	 * @param file     File that contains boolean image.
	 * @param offChar  Character that represents a 'false' in the image.
	 * 
	 * @exception FileNotFoundException if file is non-existent.
	 * @exception RuntimeException if the image contained in the file does not
	 *            represent a rectangular image containing the given characters. 
	 */
	public static boolean[][] loadImage(File file, char offChar) 
		throws FileNotFoundException, RuntimeException {
		
		ArrayList<String> rows = loadImagePad(file, offChar);
		String s = rows.get(0);
		
		boolean [][]img = new boolean [rows.size()][s.length()];
		
		for (int i = 0; i < rows.size(); i++) {
			s = rows.get(i);
			
			for (int j = 0; j < s.length(); j++) {
				char c = s.charAt(j);
				if (c == offChar) {
					img[i][j] = false;
				} else {
					img[i][j] = true;
				}
			}
		}

		// done.
		return img;
	}

	/**
	 * Load up an image from the given file where both on and off characters are known.
	 *
	 * @param file     File that contains boolean image.
	 * @param onChar   Character that represents a 'true' in the image.
	 * @param offChar  Character that represents a 'false' in the image.
	 * 
	 * @exception FileNotFoundException if file is non-existent.
	 * @exception RuntimeException if the image contained in the file does not
	 *            represent a rectangular image containing the given characters. 
	 */
	public static boolean[][] loadImage(File file, char onChar, char offChar) 
		throws FileNotFoundException, RuntimeException {
		
		ArrayList<String> rows = loadImage(file);
		String s = rows.get(0);
		
		boolean [][]img = new boolean [rows.size()][s.length()];
		
		for (int i = 0; i < rows.size(); i++) {
			s = rows.get(i);
			
			for (int j = 0; j < s.length(); j++) {
				char c = s.charAt(j);
				if (c == onChar) {
					img[i][j] = true;
				} else if (c == offChar) {
					img[i][j] = false;
				} else {
					throw new RuntimeException ("Line " + i + " has an invalid character '" + c + "' at location " + j + ". Must only have <" + onChar + "," + offChar + "> characters."); 
				}
			}
		}

		// done.
		return img;
	}
}
