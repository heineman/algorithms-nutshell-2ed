package algs.model.performance.chapter7.search;


import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.states.StateHash;
import algs.model.searchtree.states.StateStorageFactory;
import algs.model.tests.common.TrialSuite;

/** 
 * Generate table of information containing search times based 
 * on the closed set structure used: (a) list; (b) tree; (c) hash
 */
public class Table_HashSizeEfficiencyMain {

	public static void main(String[] args) {
		TrialSuite hash10TS = new TrialSuite();
		TrialSuite hash100TS = new TrialSuite();
		TrialSuite hash1000TS = new TrialSuite();
		
		int numTrials = 10;
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		for (int i = 1; i < 30; i++) {
			System.out.println(i + "...");
			for (int t = 0; t < numTrials; t++) {
				// closed as Hash-10
				algs.model.searchtree.DepthFirstSearch dfs =
					new algs.model.searchtree.DepthFirstSearch(i);
				dfs.storageType(StateStorageFactory.HASH);
				StateHash.initialCapacity = 10;
				System.gc();
				long st = System.currentTimeMillis();
				/* sol = */ dfs.search(start, goal);
				long et = System.currentTimeMillis();
				
				hash10TS.addTrial(i, st, et);
				
				// closed as Hash-100
				dfs = new algs.model.searchtree.DepthFirstSearch(i);
				dfs.storageType(StateStorageFactory.HASH);
				StateHash.initialCapacity = 100;
				System.gc();
				st = System.currentTimeMillis();
				/* sol = */ dfs.search(start, goal);
				et = System.currentTimeMillis();
				
				hash100TS.addTrial(i, st, et);
				
				// closed as Hash-1000
				dfs = new algs.model.searchtree.DepthFirstSearch(i);
				dfs.storageType(StateStorageFactory.HASH);
				StateHash.initialCapacity = 1000;
				System.gc();
				st = System.currentTimeMillis();
				/* sol = */ dfs.search(start, goal);
				et = System.currentTimeMillis();
				
				hash1000TS.addTrial(i, st, et);
			}
		}
		
		System.out.println("hash-10:");
		System.out.println(hash10TS.computeTable());
		
		System.out.println("hash-100:");
		System.out.println(hash100TS.computeTable());
		
		System.out.println("hash-1000:");
		System.out.println(hash1000TS.computeTable());
			
	}
}
