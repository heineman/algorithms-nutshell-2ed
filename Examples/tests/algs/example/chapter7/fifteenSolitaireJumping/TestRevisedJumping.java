package algs.example.chapter7.fifteenSolitaireJumping;

import java.util.Iterator;

import org.junit.Test;

import algs.example.chapter7.fifteenSolitaire.JumpingEvaluator;
import algs.example.chapter7.fifteenSolitaire.JumpingSolitaireState;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.AStarSearch;
import algs.model.searchtree.BreadthFirstSearch;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.IMove;
import algs.model.searchtree.Solution;
import algs.model.searchtree.states.StateStorageFactory;
import junit.framework.TestCase;


public class TestRevisedJumping extends TestCase {

	// purpose of this method is to show the score of the AStar heuristic for
	// a known solution to see how well it approximates the actual computation.
	@Test
	public void testEvaluateJumpHeuristic() {
		JumpingSolitaireState start = new JumpingSolitaireState(
				new boolean[] {
						     true,
						  true, true,
						true, false, true, 
					 true, true, true, true, 
				   true, true, true, true, true 
				});
		
			boolean blank[] = new boolean[] {
				         false,
					  false, false,
			        false, false, false, 
			     false, false, false, false, 
			 false, false, false, false, false
			};
			
			blank[12] = true;
			JumpingSolitaireState goal = new JumpingSolitaireState(blank);
			
			// must fake DepthTransition to properly compute g
			DepthFirstSearch dfs = new DepthFirstSearch();
			
			Solution sol = dfs.search(start, goal);
			System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
			
			if (sol.succeeded()) {
				JumpingEvaluator je = new JumpingEvaluator(15);
				System.out.println("start from: " + je.eval(start));
				System.out.println(start);
				DoubleLinkedList<IMove> mv = sol.moves();
				int depth = 1;
				for (Iterator<IMove> it = mv.iterator(); it.hasNext(); ) {
					IMove m = it.next();
			
					m.execute(start);
					System.out.println(start);
					DepthTransition dt = new DepthTransition(m, start, depth++);
					start.storedData(dt);
					System.out.println("next: " + je.eval(start));
				}
			}
	}
	
	
	@Test
	public void testAStarStartingFromMiddle () {
		JumpingSolitaireState start = new JumpingSolitaireState(
			new boolean[] {
					     true,
					  true, true,
					true, false, true, 
				 true, true, true, true, 
			   true, true, true, true, true 
			});
	
		boolean blank[] = new boolean[] {
			         false,
				  false, false,
		        false, false, false, 
		     false, false, false, false, 
		 false, false, false, false, false
		};
		
		blank[12] = true;
		JumpingSolitaireState goal = new JumpingSolitaireState(blank);
				
		AStarSearch afs = new AStarSearch(new JumpingEvaluator(15));
		Solution sol = afs.search(start, goal);
		System.out.println ("AStar:" + afs.numOpen + " open, " + afs.numClosed + " closed");
		
		if (sol.succeeded()) {
			System.out.println (sol.toString());
		}
	}
	
	
	@Test
	public void testBFSStartingFromMiddle () {
		JumpingSolitaireState start = new JumpingSolitaireState(
			new boolean[] {
					     true,
					  true, true,
					true, false, true, 
				 true, true, true, true, 
			   true, true, true, true, true 
			});
	
		boolean blank[] = new boolean[] {
			         false,
				  false, false,
		        false, false, false, 
		     false, false, false, false, 
		 false, false, false, false, false
		};
		
		blank[12] = true;
		JumpingSolitaireState goal = new JumpingSolitaireState(blank);
				
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		Solution sol = bfs.search(start, goal);
		System.out.println (bfs.numOpen + " open, " + bfs.numClosed + " closed");
		
		if (sol.succeeded()) {
			System.out.println (sol.toString());
		}
	}
	
	@Test
	public void testDFSStartingFromMiddle () {
		JumpingSolitaireState start = new JumpingSolitaireState(
			new boolean[] {
					     true,
					  true, true,
					true, false, true, 
				 true, true, true, true, 
			   true, true, true, true, true 
			});
	
		boolean blank[] = new boolean[] {
			     false,
				  false, false,
		        false, false, false, 
		     false, false, false, false, 
		 false, false, false, false, false
		};
		for (int i = 0; i < 15; i++) {
			System.out.println("DFS Trying " + i + "...");
			blank[i] = true;
			JumpingSolitaireState goal = new JumpingSolitaireState(blank);
					
			// go 13 deep
			DepthFirstSearch dfs = new DepthFirstSearch();
			Solution sol = dfs.search(start, goal);
			System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
			
			if (sol.succeeded()) {
				System.out.println (sol.toString());
				break; 
			}
			blank[i] = false; // try next one...
		}
	}
	

	@Test
	public void testDFSEndingInMiddle () {
		JumpingSolitaireState goal = new JumpingSolitaireState(
			new boolean[] {
					     false,
					  false, false,
					false, true, false, 
				 false, false, false, false, 
			   false, false, false, false, false 
			});
	
		boolean blank[] = new boolean[] {
			         true,
				  true, true,
		        true, true, true, 
		     true, true, true, true, 
		 true, true, true, true, true
		};
		for (int i = 0; i < 15; i++) {
			System.out.println("DFS Trying " + i + "...");
			blank[i] = false;
			JumpingSolitaireState start = new JumpingSolitaireState(blank);
					
			// go 13 deep
			DepthFirstSearch dfs = new DepthFirstSearch();
			dfs.storageType(StateStorageFactory.HASH);   // use hash for closed.
			Solution sol = dfs.search(start, goal);
			
			System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
			
			if (sol.succeeded()) {
				System.out.println(sol.toString());
				break; 
			}
			blank[i] = true; // try next one...
		}
	}

}
