package algs.model.data.points;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.twod.TwoDPoint;

/**
 * Generator of IPoint objects that loads requested points from a designated
 * file (rather then through some computation).
 * <p>
 * Tries to honor the 'size' parameter when generating. That is, if there 
 * are more points in the file than 'size' it will be truncated. However if
 * the file contains fewer points, the returned array will be smaller than
 * requested.
 * <p>
 * Format of file: Individual lines with space-separated doubles
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LoadFromFileGenerator extends Generator<IPoint> {

	/** parameters for the constructor. */
	private String[] params = { "file" };
	
	@Override
	public String[] parameters() {
		return params;
	}
	
	/** File to contain the info, one per line with four doubles for x1,y1, x2,y2 */
	File file;
	
	public LoadFromFileGenerator (String s) {
		this.file = new File (s);
	}
	
	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@Override
	public Generator<IPoint> construct(String[] args) {
		return new LoadFromFileGenerator(args[0]);
	}
	
	@Override
	public IPoint[] generate(int size) {
		ArrayList<IPoint> als = new ArrayList<IPoint>();
		Scanner sc = null;
		try {
			sc = new Scanner (file);
			int idx = 0;
			while (sc.hasNext() && idx < size) {
				idx++;
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				
				als.add(new TwoDPoint(x, y));
			}
			sc.close();
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException ("Unable to load data from file:" + file);
		}
			
		return als.toArray(new IPoint[]{});
	}

}
