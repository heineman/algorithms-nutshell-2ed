package algs.example.chapter9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import algs.model.ILineSegment;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.AugmentedNode;
import algs.model.problems.segmentIntersection.LineState;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.twod.TwoDLineSegment;
import algs.model.twod.TwoDPoint;

/**
 * Supporting class to batch-test line segment intersection algorithms.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Debug {

	/**
	 * Given an input file of "a b c d" values representing points of each
	 * line segment, generate all intersections and produce a report.
	 * 
	 * @param f
	 */
	public boolean executeFile (File f) {
		LineSweep dba = new LineSweep();
		
		ArrayList<ILineSegment> als = new ArrayList<ILineSegment>();
		
		try {
			Scanner sc = new Scanner (f);
			while (sc.hasNext()) {
				double[]vals = new double[4];
				for(int i = 0; i < vals.length;i++) {
					vals[i] = sc.nextDouble();
				}
				
				als.add(new TwoDLineSegment(new TwoDPoint(vals[0], vals[1]),
						new TwoDPoint(vals[2], vals[3])));
			}
			sc.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println ("Unable to locate file:" + f);
			return false;
		}
		
		Hashtable<IPoint, List<ILineSegment>> res = dba.intersections(als.iterator());
		
		for (IPoint ip : res.keySet()) {
			List<ILineSegment> ilss = res.get(ip);
			System.out.println (ip);
			for (ILineSegment ils : ilss) {
				System.out.println ("  " + ils);
			}
			System.out.println();
		}
		
		// something was calculated.
		return true;
	}
	
	/**
	 * Debugging method to ensure that all min/max are properly set in the tree.
	 */
	public boolean validate(LineState state) {
		AugmentedNode<ILineSegment> s = state.root();
		if (s == null) return true;
		
		return validateNode(s);
	}

	/** Helper method. */
	private boolean validateNode(AugmentedNode<ILineSegment> n) {
		// nothing to do.
		if (n == null) return true;
		
		ILineSegment min = n.min();
		ILineSegment max = n.max();
		
		if (n.value() != null) {
			if (!min.equals(n.value())) { error(n); return false; }
			if (!max.equals(n.value())) { error(n); return false; }
			return true;
		}
		
		// ensure children are ok.
		if (!validateNode(n.left())) {
			return false;
		}
		if (!validateNode(n.right())) {
			return false;
		}
		
		// when we get here, we just compare.
		if (n.left() != null) {
			if (!min.equals(n.left().min())) { error (n); return false; }
		}
		if (n.right() != null) {
			if (!max.equals(n.right().max())) { error (n); return false; }
		}
		
		return true;
	}
	
	/** Helper method. */
	private void error(AugmentedNode<ILineSegment> n) {
		System.out.println ("Ill-formed node.");
		System.out.println (n);
	}

	private String left4(Double d) {
		String s = Double.toString(d);
		int m = 4; if (s.startsWith("-")) m = 5;
		if (s.length() < m) { m = s.length(); }
		
		return s.substring(0,m);
		
	}
	
	// do inorder traversal, and print out only for leaf nodes
	public void printLeaves (AugmentedNode<ILineSegment> n) {
		if (n.left() != null) { printLeaves(n.left()); }
		
		if (n.value() != null) {
			ILineSegment ils = n.value();
			StringBuilder sb = shortString(ils);
			
			System.out.print (sb.toString() + " ");
		}
		
		if (n.right() != null) { printLeaves(n.right()); }
	}
	
	public StringBuilder shortString(IPoint p) {
		StringBuilder sb = new StringBuilder ("");
		sb.append (left4(p.getX()));
		sb.append (",");
		sb.append (left4(p.getY()));
		return sb;
	}
	
	private StringBuilder shortString(ILineSegment ils) {
		StringBuilder sb = new StringBuilder ("<");
		sb.append (left4(ils.getStart().getX())); sb.append (",");
		sb.append (left4(ils.getStart().getY())); sb.append (",");
		sb.append (left4(ils.getEnd().getX())); sb.append (",");
		sb.append (left4(ils.getEnd().getY())); sb.append (">");
		return sb;
	}

	public void printLeavesInOrder(LineState state) {
		AugmentedNode<ILineSegment> root = state.root();
		if (root == null) { System.out.println ("<>\n"); return; }
		
		printLeaves (root);
		System.out.println();
	}
}
