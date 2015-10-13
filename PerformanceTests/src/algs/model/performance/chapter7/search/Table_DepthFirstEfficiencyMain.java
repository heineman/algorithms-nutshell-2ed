package algs.model.performance.chapter7.search;


import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;
import algs.model.tests.common.TrialSuite;

/** 
 * Generate table of information containing search times based 
 * on the closed set structure used: (a) list; (b) hash. Note that
 * Tree is unsuitable to use as closed set since the search is keyed on the score
 * of the node, which is not suitable.
 */
public class Table_DepthFirstEfficiencyMain  {

	public static void main (String []args) {
		TrialSuite listTS = new TrialSuite();
		TrialSuite hashTS = new TrialSuite();
		
		int numTrials = 10;
		
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{2,4,5},{0,7,6}
		});
		
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		for (int i = 8; i < 30; i++) {
			System.out.println(i + "...");
			Solution sol;
			
			for (int t = 0; t < numTrials; t++) {
				String listS, hashS;
				
				// closed as LIST
				algs.model.searchtree.DepthFirstSearch dfs =
					new algs.model.searchtree.DepthFirstSearch(i);
				dfs.storageType(StateStorageFactory.QUEUE);
				System.gc();
				long st = System.currentTimeMillis();
				sol = dfs.search(start, goal);
				long et = System.currentTimeMillis();
				
				listTS.addTrial(i, st, et);
				listS = sol.toString();
				
				// closed as HASH
				dfs = new algs.model.searchtree.DepthFirstSearch(i);
				dfs.storageType(StateStorageFactory.HASH);
				System.gc();
				st = System.currentTimeMillis();
				sol = dfs.search(start, goal);
				et = System.currentTimeMillis();
				
				hashTS.addTrial(i, st, et);
				hashS = sol.toString();
				
				assert (listS.equals(hashS));
			}
		}
		
		System.out.println("linked list:");
		System.out.println(listTS.computeTable());
		
		System.out.println("hash:");
		System.out.println(hashTS.computeTable());
				
	}
}
