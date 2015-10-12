package algs.model.performance.convexhull;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.UniqueGenerator;
import algs.model.problems.convexhull.AklToussaint;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.tests.common.TrialSuite;

public class RunTrialAklToussaintMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Generator<IPoint> g = new UniqueGenerator();
		
		int NUM_TRIALS = 10;
		int MAX_SIZE = 3;
		
		IConvexHull alg = new ConvexHullScan();
		float hullSizes[] = new float[MAX_SIZE];
		float reducedSetSizes[] = new float[MAX_SIZE];
		
		TrialSuite ts = new TrialSuite();
		TrialSuite akl = new TrialSuite();
		TrialSuite rts = new TrialSuite();
		for (int size = 1024, idx = 0; idx < MAX_SIZE; size *= 2, idx++) { 
			for (int t = 0; t < NUM_TRIALS; t++) { 
				IPoint points[] = g.generate(size);
				
				// Compute time for regular algorithm
				long now = System.currentTimeMillis();
				IPoint []hull = alg.compute(points);
				long now2 = System.currentTimeMillis();
				ts.addTrial(size, now, now2);

				// compute average hull size
				hullSizes[idx] += hull.length;
				
				// remove points that are within the quadrilateral formed
				// by the extreme points at (x,y) axes.
				now = System.currentTimeMillis();
				points = AklToussaint.reduce(points);
				now2 = System.currentTimeMillis();
				akl.addTrial(size, now, now2);
	
				// compute average reduced set.
				reducedSetSizes[idx] += points.length;
				
				// compute hull again on reduced set.
				now = System.currentTimeMillis();
				hull = alg.compute(points);
				now2 = System.currentTimeMillis();
				rts.addTrial(size, now, now2);
			}
			// move ahead!
			size *= 2;
		}
		
		System.out.println ("REPORTS:");
		System.out.println ("Time to compute initial hull");
		System.out.println (ts.computeTable());
		
		System.out.println ("Time to compute akl heuristics");
		System.out.println (akl.computeTable());
		
		System.out.println ("Time to compute reduced hull");
		System.out.println (rts.computeTable());
		
		System.out.println ("\nsize\tavg. hullsize\tavg. reduced size");
		
		for (int size = 1024, idx = 0; idx < MAX_SIZE; size *= 2, idx++) {
			System.out.println (size + ".\t" + hullSizes[idx]/NUM_TRIALS + "\t" +
					reducedSetSizes[idx]/NUM_TRIALS);
		}
	}
}
