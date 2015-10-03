package algs.example.chapter7.fifteenSolitaireJumping;


import org.junit.Test;

import algs.example.chapter7.fifteenSolitaire.fixed.JumpingSolitaireState;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.Solution;
import junit.framework.TestCase;

/**
 * Generate all solutions for the fifteen puzzle, starting from any position.
 *
 */
public class AllSolutions extends TestCase {

	@Test
	public void testAllSolutions() {
		boolean[] full = new boolean[] {
					     true,
					  true, true,
					true, true, true, 
				 true, true, true, true, 
			   true, true, true, true, true 
			};
	
		boolean blank[] = new boolean[] {
			         false,
				  false, false,
		        false, false, false, 
		     false, false, false, false, 
		 false, false, false, false, false
		};
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				full [i] = false;
				blank [j] = true;
				
				JumpingSolitaireState start = new JumpingSolitaireState(full);
			    JumpingSolitaireState goal = new JumpingSolitaireState(blank);
					
			    // go as deep as necessary
			    DepthFirstSearch dfs = new DepthFirstSearch();
			    Solution sol = dfs.search(start, goal);
			    
			    if (sol.succeeded()) {
			    	System.out.println("open:" + i + " target: " + j);
			    	System.out.println ("   " + dfs.numOpen + " open, " + dfs.numClosed + " closed");
					System.out.println ("   " + sol.toString());
			    }
			    
			    full [i] = true; // try next one...
			    blank [j] = false;
			}
		}
	}

}
