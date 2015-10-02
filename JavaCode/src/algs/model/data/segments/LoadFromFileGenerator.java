package algs.model.data.segments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/**
 * Generator of ILineSegment objects that loads requested points from a designated
 * file (rather then through some computation).
 * <p>
 * Tries to honor the 'size' parameter when generating. That is, if there 
 * are more points in the file than 'size' it will be truncated. However if
 * the file contains fewer points, the returned array will be smaller than
 * requested.
 * <p>
 * Format of file: Individual lines with space-separated doubles (four of them)
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LoadFromFileGenerator extends Generator<ILineSegment> {

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
	public Generator<ILineSegment> construct(String[] args) {
		return new LoadFromFileGenerator(args[0]);
	}
	
	@Override
	public ILineSegment[] generate(int size) {
		ArrayList<ILineSegment> als = new ArrayList<ILineSegment>();
		Scanner sc = null;
		try {
			sc = new Scanner (file);
			int idx = 0;
			while (sc.hasNext() && idx < size) {
				idx++;
				double x1 = sc.nextDouble();
				double y1 = sc.nextDouble();
				double x2 = sc.nextDouble();
				double y2 = sc.nextDouble();
				
				als.add(new TwoDLineSegment(new TwoDPoint(x1, y1), 
						new TwoDPoint(x2, y2)));
			}
		} catch (FileNotFoundException fnfe) {
			// not much to do...
			fnfe.printStackTrace();
		}
		
		if (sc != null) {
			sc.close();
		}
			
		return als.toArray(new ILineSegment[]{});
	}
}
