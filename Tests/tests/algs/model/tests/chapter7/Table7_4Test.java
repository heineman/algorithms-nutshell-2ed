package algs.model.tests.chapter7;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.problems.eightpuzzle.EightPuzzleNode;
import algs.model.searchtree.*;

/**
 * Attempt head to head comparison of BFS/DFS.
 * 
 * For N=2,4,8,16 path lengths
 *    Generate T=10 random boards with those moves
 *         Run BFS
 *         Run DFS-depth-2*N
 *         Run DFS-depth-N
 *         Run AStarSearch
 *         
 * Compare by (a) Number of nodes searched; (b) solution length found.         
 * 
 * Sample output results on home PC
  
2,44,30,72,50,2.0,2.0,2.0,2.0
3,149,61,162,70,3.0,3.0,3.0,3.0
4,261,120,639,90,4.0,4.0,4.4,4.0
5,421,248,2228,110,5.0,5.0,6.4,5.0
6,830,365,3874,130,6.0,6.0,10.6,6.0
7,1337,524,6894,141,6.6,6.6,7.25(2),6.6
8,2091,780,9564,171,7.6,7.7777777(1),14.0(1),7.6
9,4283,1316,19705,239,9.0,9.0(1),13.25(2),9.0
10,6894,2051,29236,241,9.8,9.666667(4),16.0(4),9.8
11,11676,3194,97161,488,10.8,11.0(5),20.333334(7),11.4
12,15129,4943,63994,665,12.0,12.0(1),24.0(5),15.4
13,25468,6828,120836,576,12.4,NaN(10),21.857143(3),13.2
14,39565,8562,135193,482,13.6,14.0(5),26.222221(1),14.6
15,62229,10597,168981,512,14.2,14.2(5),25.75(2),14.2

 * @author George Heineman
 */
public class Table7_4Test extends TestCase {

	EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	
	@Test
	public void testFailureCase() {
		// 8 moves away
		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{8,1,3},{7,2,5},{0,4,6}
		});
		
		// can't find! Because the horizon effect. Need more detailed analysis to
		// see WHICH was the failed one.
		DepthFirstSearch dfs2 = new DepthFirstSearch(13);
		Solution ds2 = dfs2.search(start.copy(), goal);
		assertFalse (ds2.succeeded());
	}
	
}
