package algs.example.convexhull.imageBound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import algs.model.IPoint;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.twod.TwoDPoint;

/**
 * Load up an image from a file containing a rectangular input.
 * 
 *    '!' is used for 'region already seen'
 *    '@' is used as point on convex hull.
 *    '#' and higher are the regions.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class CharImageLoad {
	
	public static final char ON = (char) 31;
	
	public static final char SEEN = '!';
	
	public static final char HULL = '@';
	
	public static final char FirstRegion = '#';
	
	/**
	 * Load up a char-based image from the given file.
	 *
	 * @param file     File that contains character-based image.
	 * 
	 * @exception FileNotFoundException if file is non-existent.
	 * @exception RuntimeException if the image contained in the file does not
	 *            represent a rectangular image containing the given characters. 
	 */
	public static char[][] loadImage(File file) 
		throws FileNotFoundException, RuntimeException {
		
		ArrayList<String> rows = BooleanImageLoad.loadImage(file);
		String s = rows.get(0);
		
		char [][]img = new char [rows.size()][s.length()];
		
		for (int i = 0; i < rows.size(); i++) {
			s = rows.get(i);
			
			for (int j = 0; j < s.length(); j++) {
				img[i][j] = s.charAt(j);
			}
		}

		// done.
		return img;
	}
	
	/**
	 * Load up a char-based image from the given file.
	 *
	 * @param file     File that contains character-based image.
	 * 
	 * @exception FileNotFoundException if file is non-existent.
	 * @exception RuntimeException if the image contained in the file does not
	 *            represent a rectangular image containing the given characters. 
	 */
	public static char[][] loadImage(File file, char offChar) 
		throws FileNotFoundException, RuntimeException {
		
		ArrayList<String> rows = BooleanImageLoad.loadImagePad(file, offChar);
		String s = rows.get(0);
		
		char [][]img = new char [rows.size()][s.length()];
		
		for (int i = 0; i < rows.size(); i++) {
			s = rows.get(i);
			
			for (int j = 0; j < s.length(); j++) {
				img[i][j] = s.charAt(j);
			}
		}

		// done.
		return img;
	}

	/** Find the first ON pixel or return null. */
	private static IPoint locate (char[][] img) {
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				if (img[i][j] == ON) { return new TwoDPoint (i,j); }
			}
		}
		
		return null;
	}
	
	/**
	 * Assuming ' ' is the empty char, go through and assign contiguous image regions
	 * in the character image map that use 'onChar' as the onBit.
	 * 
	 * A contiguous region is based on the eight-cardinal directions in a 3x3 grid from
	 * center point.
	 * 
	 * First convert all 'onChar' into (char 31).
	 * 
	 * Start with '!' which is character 33, and work upwards from there (skipping the
	 *  
	 * 
	 * @param img      Image
	 * @param offChar  char to use as Off 
	 */
	public static int identifyRegions(char[][] img, char offChar) {
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				if (img[i][j] != offChar) { img[i][j] = ON; }
			}
		}
		
		// now start with FirstRegion, and weave through, from top left to bottom right
		// until we find an ON pixel. Use that as a seed to spread out to locate
		// other contiguous pixels (recursively)
		char next = FirstRegion;
		IConvexHull alg = new ConvexHullScan();
		while (true) {
			IPoint p = locate(img);
			if (p == null) return (next-FirstRegion);  // number seen so far.
			
			Collection<IPoint> points = new LinkedList<IPoint>();
			points.add(p);
			spread(img, (int) p.getX(), (int) p.getY(), points, next);
			
			// compute hull.
			IPoint[] raw = points.toArray(new TwoDPoint[]{});
			IPoint[] hull = alg.compute (raw);
			
			// Update hull points.
			for (IPoint h : hull) {
				img[(int)h.getX()][(int)h.getY()] = HULL;
			}

			next++;
		}
	}

	/** Output on the console. */
	public static void output(char[][]img) {
		// output
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[0].length; j++) {
				System.out.print(img[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void spread(char[][] img, int r, int c, Collection<IPoint> points, char next) {
		img[r][c] = next;
		
		for (int di = -1; di <= 1; di++) {
			if (r+di >=0 && r+di < img.length) {
				for (int dj = -1; dj <= 1; dj++) {
					if ((di == 0) && (dj == 0)) continue;   // nothing to do.
					
					if (c+dj >= 0 && c+dj < img[0].length) {
						if (img[r+di][c+dj] == ON) {
							img[r+di][c+dj] = next;
							points.add(new TwoDPoint(r+di,c+dj));
							spread (img, r+di, c+dj, points, next); // recurse
						}
					}
				}
			}
		}
		
	}

}
