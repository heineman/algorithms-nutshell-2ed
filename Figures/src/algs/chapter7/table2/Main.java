package algs.chapter7.table2;

import algs.model.problems.eightpuzzle.BadEvaluator;
import algs.model.problems.eightpuzzle.FairEvaluator;
import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.problems.eightpuzzle.GoodEvaluator;
import algs.model.problems.eightpuzzle.WeakEvaluator;
import algs.model.searchtree.Solution;
import algs.model.searchtree.AStarSearch;

public class Main {
	public static void main(String[] args) {
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{1,4,8},{7,3,0},{6,5,2}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});
		
		GoodEvaluator ge = new GoodEvaluator();
		AStarSearch as = new AStarSearch(ge);
		
		// table formatting
		System.out.println ("Measure Name\tEvaluation of h(n)\t\t\tStatistics");
		
		Solution sol = as.search(start, goal);
		System.out.println(ge.getClass().getSimpleName() + "\t" + ge.evalString(start) + "\t\t\t\t" +
				sol.numMoves() + "-move solution, closed:" + as.numClosed + ", open:" + as.numOpen);
		
		FairEvaluator fe = new FairEvaluator();
		as = new AStarSearch(fe);
		
		sol = as.search(start, goal);
		System.out.println(fe.getClass().getSimpleName() + "\t" + fe.evalString(start) + "\t\t\t\t\t" +
				sol.numMoves() + "-move solution, closed:" + as.numClosed + ", open:" + as.numOpen);
	
		WeakEvaluator we = new WeakEvaluator();
		as = new AStarSearch(we);
		
		sol = as.search(start, goal);
		System.out.println(we.getClass().getSimpleName() + "\t" + we.evalString(start) + "\t\t\t\t\t" +
				sol.numMoves() + "-move solution, closed:" + as.numClosed + ", open:" + as.numOpen);
		
		BadEvaluator be = new BadEvaluator();
		as = new AStarSearch(be);
		
		sol = as.search(start, goal);
		System.out.println(be.getClass().getSimpleName() + "\t" + be.evalString(start) + "\t" +
				sol.numMoves() + "-move solution, closed:" + as.numClosed + ", open:" + as.numOpen);
		
		System.out.println("\nGoodEvaluator Description:");
		System.out.println(ge.toString());

		System.out.println("\nFairEvaluator Description:");
		System.out.println(fe.toString());
		
		System.out.println("\nWeakEvaluator Description:");
		System.out.println(we.toString());
		
		System.out.println("\nBadEvaluator Description:");
		System.out.println(be.toString());
	}
	
}
