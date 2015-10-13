package algs.model.performance.convexhull;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.*;
import algs.model.heap.HeapSort;
import algs.model.problems.convexhull.IConvexHull;
import algs.model.problems.convexhull.andrew.ConvexHullScan;
import algs.model.problems.convexhull.balanced.BalancedTreeAndrew;
import algs.model.problems.convexhull.heap.HeapAndrew;
import algs.model.tests.common.TrialSuite;

public class Main {
	
	/*
	Generator:algs.model.data.points.CircleGenerator

	-----------------------------------------
	1...
	2...
	3...
	4...
	5...
	6...
	Andrew
	n,average,min,max,stdev,#
	1024,2.611111111111111,0,31,6.011155207454269,18
	2048,6.111111111111111,0,31,7.888106377466155,18
	4096,8.722222222222221,0,16,8.035257275104772,18
	8192,19.166666666666668,0,32,6.801816366409265,18
	16384,39.0,31,47,8.231932086840425,18
	32768,85.94444444444444,78,109,8.069348120199509,18

	Heap
	n,average,min,max,stdev,#
	1024,3.5,0,31,6.741007081333488,18
	2048,3.5,0,16,6.741007081333488,18
	4096,7.722222222222222,0,16,7.954322210601313,18
	8192,15.61111111111111,15,16,0.5016313257045503,18
	16384,33.833333333333336,31,47,6.070662334249088,18
	32768,71.22222222222223,62,110,8.040745908884437,18

	Balanced
	n,average,min,max,stdev,#
	1024,2.611111111111111,0,16,6.011155207454269,18
	2048,0.0,0,15,0.0,18
	4096,7.777777777777778,0,16,8.01142974355687,18
	8192,19.055555555555557,15,32,6.725475904632389,18
	16384,38.333333333333336,31,63,7.646606321453597,18
	32768,79.0,78,125,3.757345746510897,18

	Generator:algs.model.data.points.UniformGenerator

	-----------------------------------------
	1...
	2...
	3...
	4...
	5...
	6...
	Andrew
	n,average,min,max,stdev,#
	1024,2.0526315789473686,0,31,5.347125362695477,38
	2048,4.473684210526316,0,31,7.1081865331091105,38
	4096,9.078947368421053,0,16,7.854699330001283,38
	8192,19.31578947368421,0,32,7.640946818215994,38
	16384,41.921052631578945,31,47,7.412121478225099,38
	32768,92.94736842105263,78,110,10.187293008133734,38

	Heap
	n,average,min,max,stdev,#
	1024,2.9210526315789473,0,31,6.2314875190324965,38
	2048,4.184210526315789,0,16,7.09722162295159,38
	4096,8.578947368421053,0,16,7.831438992921911,38
	8192,16.05263157894737,15,32,2.5356631247504193,38
	16384,36.13157894736842,31,47,7.378460443793533,38
	32768,76.89473684210526,62,110,9.196913329685191,38

	Balanced
	n,average,min,max,stdev,#
	1024,2.8947368421052633,0,16,6.176631959353631,38
	2048,2.9210526315789473,0,16,6.231487519032495,38
	4096,8.578947368421053,0,16,7.831438992921911,38
	8192,19.289473684210527,15,32,6.7540093987755325,38
	16384,39.1578947368421,31,63,7.799952584018991,38
	32768,85.94736842105263,78,125,9.449574188665007,38

*/
	
	/**  CIRCLEGENERATOR
Balanced
n,average,min,max,stdev,#
1024,3.875,0,47,7.180081575342402,8
2048,2.0,0,16,5.656854249492381,8
4096,7.875,0,16,8.425090080061036,8
8192,21.625,15,32,8.05228450281569,8

Andrew
n,average,min,max,stdev,#
1024,2.0,0,31,5.656854249492381,8
2048,3.75,0,63,6.943650748294136,8
4096,7.625,0,297,8.158037228927766,8
8192,15.5,0,1141,0.5345224838248488,8

Circle: 20 trials

Balanced
n,average,min,max,stdev,#
1024,2.611111111111111,0,47,6.011155207454269,18
2048,2.611111111111111,0,16,6.011155207454269,18
4096,9.61111111111111,0,16,7.897422430499183,18
8192,20.055555555555557,15,32,7.124706162958353,18

Andrew
n,average,min,max,stdev,#
1024,0.8333333333333334,0,31,3.535533905932739,18
2048,2.5555555555555554,0,78,5.8833985998712786,18
4096,6.0,0,343,7.745966692414834,18
8192,13.944444444444445,0,1157,5.092927301339774,18

UNIFORM GENERATOR (10 trials)

 Balanced
n,average,min,max,stdev,#
1024,4.0,0,47,7.406560798180411,8
2048,5.75,0,16,7.9417522355855805,8
4096,9.75,0,16,8.08437646681195,8
8192,19.75,15,32,6.943650748294136,8
16384,50.875,47,63,7.180081575342402,8

Andrew
n,average,min,max,stdev,#
1024,1.875,0,16,5.303300858899107,8
2048,5.875,0,16,8.114141095290721,8
4096,5.75,0,16,7.9417522355855805,8
8192,13.75,0,31,5.574175147179049,8
16384,29.5,15,47,5.477225575051661,8

UNIFORM GENERATOR (20 trials)

n,average,min,max,stdev,#
1024,1.7777777777777777,0,47,5.174093334108438,18
2048,2.6666666666666665,0,16,6.135719910778963,18
4096,13.88888888888889,0,16,5.074606781508112,18
8192,21.61111111111111,15,32,7.837608345354046,18
16384,50.44444444444444,46,63,6.635219950449998,18

Andrew
n,average,min,max,stdev,#
1024,1.6666666666666667,0,16,4.850712500726659,18
2048,2.5,0,16,5.752237416355278,18
4096,8.61111111111111,0,16,7.934577262833181,18
8192,15.666666666666666,0,31,0.485071250072666,18
16384,32.05555555555556,31,46,3.5058308106927547,18
	 */
	// compare two
	@SuppressWarnings("unchecked")
	public static void main (String []args) {

		// to avoid floating point issues with very large sets, we use a
		// large circle generator.
		Generator<IPoint> generator[] = new Generator[2]; 
		generator[0] = new CircleGenerator(1000);
		generator[1] = new UniformGenerator();
		
		TrialSuite sorting = new TrialSuite();
		TrialSuite andrew = new TrialSuite();
		TrialSuite heap   = new TrialSuite();
		TrialSuite balanced = new TrialSuite();
		int NUM_TRIALS = 20;
		
		System.out.println ("Trials with " + generator);
		for (int g = 0; g < generator.length; g++) {
			System.out.println ("Generator:" + generator[g] + "\n");
			System.out.println ("-----------------------------------------");
			int n = 1024;
			
			IConvexHull convexHullScan = new ConvexHullScan();
			IConvexHull heapConvexHull = new HeapAndrew();
			IConvexHull balancedConvexHull = new BalancedTreeAndrew();
			for (int i = 1; i < 7; i++) {
				IPoint []points = generator[g].generate(n);
			
				System.out.println (i + "...");
			
				System.gc();  // prepare
				for (int t = 0; t < NUM_TRIALS; t++) {
					System.gc();
					long now = System.currentTimeMillis();
					IPoint[] hulls0 = convexHullScan.compute(points);
					long now2 = System.currentTimeMillis();
					andrew.addTrial(n, now, now2);
					
					System.gc();
					now = System.currentTimeMillis();
					IPoint[] hulls1 = heapConvexHull.compute(points);
					now2 = System.currentTimeMillis();
					heap.addTrial(n, now, now2);
					
					System.gc();
					now = System.currentTimeMillis();
					IPoint[] hulls2 = balancedConvexHull.compute(points);
					now2 = System.currentTimeMillis();
					balanced.addTrial(n, now, now2);
					
					System.gc();
					now = System.currentTimeMillis();
					new HeapSort<IPoint>().sort(points, 0, n-1, IPoint.xy_sorter);
					now2 = System.currentTimeMillis();
					sorting.addTrial(n, now, now2);


					if (hulls0.length != hulls1.length || hulls0.length != hulls2.length) {
						System.err.println ("FAILED with different sizes");
					} 
					
					// assert all arrays are the same.
					new HeapSort<IPoint>().sort(hulls0, 0, hulls0.length-1, IPoint.xy_sorter);
					new HeapSort<IPoint>().sort(hulls1, 0, hulls1.length-1, IPoint.xy_sorter);
					new HeapSort<IPoint>().sort(hulls2, 0, hulls2.length-1, IPoint.xy_sorter);
					
					for (int h = 0; h < hulls0.length; h++) {
						if (!hulls0[h].equals(hulls1[h])) {
							System.err.println ("FAILED with different points:" + hulls0[h] + "," + hulls1[h]);
							outputDiff(hulls0,hulls1);
						}
						if (!hulls0[h].equals(hulls2[h])) {
							System.err.println ("FAILED with different points:" + hulls0[h] + "," + hulls2[h]);
							outputDiff(hulls0,hulls1);
						}
					}
				}
				
				n *= 2; // continue!
			}

			System.out.println ("Andrew");
			System.out.println (andrew.computeTable());
			
			System.out.println ("Heap");
			System.out.println (heap.computeTable());
			
			System.out.println ("Balanced");
			System.out.println (balanced.computeTable());
			
			System.out.println ("Sorting only");
			System.out.println (sorting.computeTable());
		}
	}

	private static void outputDiff(IPoint[] hulls0, IPoint[] hulls1) {
		for (int i = 0; i < hulls0.length; i++) {
			if (hulls0[i].equals(hulls1[i])) { continue; }
			
			System.out.println (i + ". "+ hulls0[i] + " : " + hulls1[i] + " **");
		}
		System.exit(2);
	}
}
