package algs.chapter9.figure7;

import java.util.Hashtable;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineSweep dba = new LineSweep();
		Hashtable<ILineSegment,String> map = new Hashtable<ILineSegment,String>();
		
		// showed a problem
		ILineSegment[] segments = new ILineSegment[] {
				new TwoDLineSegment(0,13,0,3),           /* S1 */
				new TwoDLineSegment(0,13,12,1),          /* S2 */
				new TwoDLineSegment(8,7,0,3),            /* S3 */
				new TwoDLineSegment(8,7,13,2),           /* S4 */
				new TwoDLineSegment(12,9,9,0),           /* S5 */
				new TwoDLineSegment(0,3,12,3),           /* S6 */
		};
		
		map.put(segments[0], "S1");
		map.put(segments[1], "S2");
		map.put(segments[2], "S3");
		map.put(segments[3], "S4");
		map.put(segments[4], "S5");
		map.put(segments[5], "S6");
		
		// Show results from figure 9-15. Debug to see the intersections in order
		Hashtable<IPoint, List<ILineSegment>>res = dba.intersections(segments);
		assert (7 == res.size());
		
		for (IPoint ip : res.keySet()) {
			System.out.println("intersection: " + ip);
			for (ILineSegment il : res.get(ip)) {
				System.out.println("  " + map.get(il));
			}
		}
	}

}
