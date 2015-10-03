package algs.example.convexhull.imageBound;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Compute convex hull and number of distinct regions for given sample
 * ASCII image.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Another {

	public static void main(String[] args) throws FileNotFoundException {
		String loc = "resources" + java.io.File.separatorChar +
					 "algs" + java.io.File.separatorChar +
					 "chapter9" + java.io.File.separatorChar;
		File f = new File (loc, "sample.1");
		char [][]img = CharImageLoad.loadImage(f, ' ');
		
		// convert each contiguous region in to a number. Note that we use ' ' as 
		// the default EMPTY region.
		int numR = CharImageLoad.identifyRegions (img, ' ');
		CharImageLoad.output(img);
		System.out.println (numR + " regions found.");
	}

}
