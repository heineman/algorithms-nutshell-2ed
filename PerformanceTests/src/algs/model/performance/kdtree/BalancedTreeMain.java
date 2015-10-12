package algs.model.performance.kdtree;


/* Run on new laptop. Not sure how to present results; is this going to be a variation
 * of RangeQuery?
 *
d:2 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.020080321285140562,0,1,0.14041614485776782,498

i:1
n,average,min,max,stdev,#
2048,0.060240963855421686,0,1,0.2381719915397235,498

d:3 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.01606425702811245,0,3,0.12584911555855885,498

i:1
n,average,min,max,stdev,#
2048,0.02208835341365462,0,1,0.14711872669160664,498

i:2
n,average,min,max,stdev,#
2048,0.08835341365461848,0,1,0.2840935664979889,498

d:4 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.008032128514056224,0,1,0.08935124420472917,498

i:1
n,average,min,max,stdev,#
2048,0.02610441767068273,0,1,0.15960617130951602,498

i:2
n,average,min,max,stdev,#
2048,0.06827309236947791,0,1,0.25246756015866784,498

i:3
n,average,min,max,stdev,#
2048,0.14859437751004015,0,1,0.35604584535206946,498

d:5 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.012048192771084338,0,1,0.10921073018836894,498

i:1
n,average,min,max,stdev,#
2048,0.020080321285140562,0,2,0.14041614485776724,498

i:2
n,average,min,max,stdev,#
2048,0.04417670682730924,0,1,0.20569415502651156,498

i:3
n,average,min,max,stdev,#
2048,0.10040160642570281,0,1,0.3008369267500374,498

i:4
n,average,min,max,stdev,#
2048,0.19477911646586346,0,2,0.3964287907334137,498

d:6 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.004016064257028112,0,1,0.06330863799546559,498

i:1
n,average,min,max,stdev,#
2048,0.028112449799196786,0,1,0.16546030971252193,498

i:2
n,average,min,max,stdev,#
2048,0.05421686746987952,0,1,0.22667283161459695,498

i:3
n,average,min,max,stdev,#
2048,0.0963855421686747,0,1,0.2954159975721293,498

i:4
n,average,min,max,stdev,#
2048,0.1827309236947791,0,4,0.44939370276860563,498

i:5
n,average,min,max,stdev,#
2048,0.28714859437751006,0,1,0.45288645248127934,498

d:7 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.01606425702811245,0,1,0.12584911555855877,498

i:1
n,average,min,max,stdev,#
2048,0.03413654618473896,0,1,0.1817624354130474,498

i:2
n,average,min,max,stdev,#
2048,0.05421686746987952,0,1,0.22667283161459656,498

i:3
n,average,min,max,stdev,#
2048,0.05622489959839357,0,1,0.23058713795795366,498

i:4
n,average,min,max,stdev,#
2048,0.10240963855421686,0,2,0.30349111686433816,498

i:5
n,average,min,max,stdev,#
2048,0.17269076305220885,0,6,0.40902444269671645,498

i:6
n,average,min,max,stdev,#
2048,0.25502008032128515,0,2,0.4363107862655513,498

d:8 numTrials:500
i:0
n,average,min,max,stdev,#
2048,0.012048192771084338,0,1,0.10921073018836895,498

i:1
n,average,min,max,stdev,#
2048,0.010040160642570281,0,1,0.09979656567587104,498

i:2
n,average,min,max,stdev,#
2048,0.030120481927710843,0,1,0.17109067083875978,498

i:3
n,average,min,max,stdev,#
2048,0.05421686746987952,0,1,0.22667283161459684,498

i:4
n,average,min,max,stdev,#
2048,0.0642570281124498,0,1,0.24545680774345846,498

i:5
n,average,min,max,stdev,#
2048,0.11244979919678715,0,4,0.3162367094891588,498

i:6
n,average,min,max,stdev,#
2048,0.19879518072289157,0,1,0.399494844776357,498

i:7
n,average,min,max,stdev,#
2048,0.3333333333333333,0,1,0.4718785324952956,498


 
 
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.array.Selection;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.tests.common.TrialSuite;

public class BalancedTreeMain {
	// random number generator.
	static Random rGen;

	
	/** 
	 * generate array of n d-dimensional points whose coordinates are integer
	 * values in the range 0 .. scale-1 and all values are ODD.
	 */
	public static IMultiPoint[] randomOddPoints (int n, int d, int scale) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < d; j++) {
				int it = (int)(rGen.nextDouble()*scale);
				if (it %2 == 0) { if (it == scale) { it = 1;} else { it++; } }
				sb.append(it);
				if (j < d-1) { sb.append (","); }
			}
			points[i] = new Hyperpoint(sb.toString());
		}
		
		return points;
	}
	
	/** 
	 * generate array of n d-dimensional points whose coordinates are integer
	 * values in the range 0 .. scale-1 
	 */
	public static IMultiPoint[] randomPoints (int n, int d, int scale) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < d; j++) {
				sb.append((int)(rGen.nextDouble()*scale));
				if (j < d-1) { sb.append (","); }
			}
			points[i] = new Hyperpoint(sb.toString());
		}
		
		return points;
	}
	
	private static DimensionalNode buildBalanced (IMultiPoint[] points, int depth, int maxDepth) {
		if (points.length == 0) return null;

		// Select axis based on depth so that axis cycles through all valid values
		int axis = depth;
		if (axis == maxDepth+1) { axis = 1; depth = 1; }
		final int compAxis = axis;
		Comparator<IMultiPoint> comp = new Comparator<IMultiPoint>() {

			// make sure == is in the greater side, for enabling consistent 
			// traversal of the KD tree.
			public int compare(IMultiPoint o1, IMultiPoint o2) {
				if (FloatingPoint.lesser(o1.getCoordinate(compAxis), o2.getCoordinate(compAxis))) {
					return -1;
				}
				return +1;				
			}
		};
		
		// sort by axis.
		Selection.qsort(points, 0, points.length-1, comp);
		
		// Start at the middle
		int med = points.length/2;
		
		// Note that some points == to the median may be on the left and right,
		// so we will ultimately have to search both sides when key is equal
		// to the split.
		
		IMultiPoint median = points[med];
		IMultiPoint []belowPoints = null;
		IMultiPoint []abovePoints = null;
		
		if (med > 0) {
			belowPoints = new IMultiPoint[med];
			System.arraycopy(points, 0, belowPoints, 0, med);
		}
		abovePoints = new IMultiPoint[points.length-med-1];
		System.arraycopy(points, med+1, abovePoints, 0, abovePoints.length);
		
		DimensionalNode dn = new DimensionalNode(axis, median);
		if (belowPoints != null) { dn.setBelow(buildBalanced(belowPoints, depth+1, maxDepth)); }
		if (abovePoints != null) { dn.setAbove(buildBalanced(abovePoints, depth+1, maxDepth)); }
		return dn;
	}
	
	/**
	 * Description of algorithm taken from Bentley's original paper.
	 * 
	 * @param tree
	 * @param relevant
	 * @param keys
	 */
	private static void partialMatch(DimensionalNode node, boolean[] relevant, double[] keys, ArrayList<IMultiPoint> results) {
		// if node matches, then emit results.
		IMultiPoint pt = node.point;
		boolean match = true;
		for (int i = 0; i < relevant.length; i++) {
			if (!relevant[i]) continue;
			
			if (!FloatingPoint.same(pt.getCoordinate(i+1), keys[i])) {
				match = false;
				break;
			}
		}
		
		// if MATCH then we have a partial match. Add to results.
		if (match) {
			results.add(pt);
		}
		
		// Now, if the coordinate/dimension of this node is the space-splitting
		// of a key in our query then we can choose to only go down one child; 
		// otherwise we have no choice but to recurse into both.
		int d = node.dimension;
		if (relevant[d-1]) {
			// isBelow checks for strict less than. This means that (by default) 
			// equal coordinates are going to appear in the above Subtree
			if (FloatingPoint.lesserEquals(keys[d-1], pt.getCoordinate(d))) {
				DimensionalNode below = node.getBelow();
				if (below != null) {
					partialMatch(below, relevant, keys, results);
				}
			}
			if (FloatingPoint.greaterEquals(keys[d-1], pt.getCoordinate(d))) {
				DimensionalNode above = node.getAbove();
				if (above != null) {
					partialMatch(above, relevant, keys, results);
				}
			}
		} else {
			// must go down both.
			DimensionalNode below = node.getBelow();
			if (below != null) {
				partialMatch(below, relevant, keys, results);
			}
			DimensionalNode above = node.getAbove();
			if (above != null) {
				partialMatch(above, relevant, keys, results);
			}
		}
	}
	
	public static void main3 (String []args) {
		// 5^5
		int d = 3;
		IMultiPoint[] points = new IMultiPoint[5*5*5];
		int idx = 0;
		for (int i = 0; i < 5; i++ ) {
			for (int ii = 0; ii < 5; ii++ ) {
				for (int iii = 0; iii < 5; iii++ ) {
					points[idx++] = new Hyperpoint(i+","+ii+","+iii);
				}
			}
		}
		
		DimensionalNode node = buildBalanced(points, 1, d);
		KDTree tree = new KDTree(d);
		tree.setRoot(node);

		boolean []relevant = new boolean[d];
		for (int i = 0; i < relevant.length; i++) {relevant[i] = true; }
		double []keys = new double[]{4,0,0};
		
		for (int i = 0; i < d; i++) {
			ArrayList<IMultiPoint> results = new ArrayList<IMultiPoint>();
			partialMatch(tree.getRoot(), relevant, keys, results);
			int sz = results.size();
		
			System.out.println (i + ": " + sz);
			relevant[i] = false;
		}
	}
	
	
	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int d = 2;
		int maxD = 10;
		int n = 2048;       // was 2048
		int scale = 256;   // was 256
		int NUM_TRIALS = 500;
		
		// create set of points. Note that the search will always ensure that no
		// points are found.
		for (; d <= maxD; d++) {
			TrialSuite []ts = new TrialSuite[maxD+1];
			//ts[d] = new TrialSuite();
			// create n random points in d dimensions, whose integer coordinates
			// in all dimensions are in the range 0..scale-1; all indices are odd.
			IMultiPoint[] points = randomOddPoints (n, d, scale);
		
			// Perform the search.
			DimensionalNode node = buildBalanced(points, 1, d);
			KDTree tree = new KDTree(d);
			tree.setRoot(node);
			
			// do a search where only some of the coordinates are specified. Like
			// search 4-dimensional points for (*, 2, 10, *) where only the 2nd and
			// 3rd dimensions are relevant for the query at hand. Try sets of queries
			// for each one starting with full dimensions, then reducing the relevant 
			// dimensions one at a time.
			boolean []relevant = new boolean[d];
			for (int i = 0; i < relevant.length; i++) {relevant[i] = true; }
			double []keys = new double[d];
			
			for (int i = 0; i < relevant.length; i++) {
				ts[i] = new TrialSuite();
				for (int t = 0; t < NUM_TRIALS; t++) {
					// construct a random query OF EVEN indices ONLY.
					for (int k = 0; k < keys.length; k++) {
						keys[k] = (int)(rGen.nextDouble()*scale);
						if (keys[k] %2 == 1) keys[k]++; 
					}

					// prepare for trial.
					System.gc();
					long start = System.currentTimeMillis();
				
					// work
					ArrayList<IMultiPoint> results = new ArrayList<IMultiPoint>();
					partialMatch(tree.getRoot(), relevant, keys, results);
					long end = System.currentTimeMillis();
					ts[i].addTrial(n, start, end);
				}
				
				relevant[i] = false; // turn this one off, and continue.
			}
			
			// When we get here, we have produced a full set of suite results.
			System.out.println ("d:" + d + " numTrials:" + NUM_TRIALS);
			for (int i = 0; i < relevant.length; i++) {
				System.out.println ("i:" + i);
				System.out.println (ts[i].computeTable());
			}
		}
	}

}
