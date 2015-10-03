package algs.example.chapter7;

import java.util.Iterator;

import org.junit.Test;

import algs.example.chapter7.fifteenSolitaire.fixed.JumpingEvaluator;
import algs.example.chapter7.fifteenSolitaire.fixed.JumpingSolitaireState;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.AStarSearch;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.IMove;
import algs.model.searchtree.Solution;

import junit.framework.TestCase;

public class TestVariableJumping extends TestCase {
	
	boolean verbose = false;  // set to true to see all solutions.
	
	@Test
	public void testFifteen() {
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
				if (!verbose) {
					System.out.println(sol.toString());
				} else {
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
			} else {
				System.out.println("no solution");
			}
	}
	
	@Test
	public void testTwentyOne() {
		JumpingSolitaireState start = new JumpingSolitaireState(
				new boolean[] {
						     true,
						  true, true,
						true, false, true, 
					 true, true, true, true, 
				   true, true, true, true, true,
				 true, true, true, true, true, true
				});
		
			boolean blank[] = new boolean[] {
				         false,
					  false, false,
			        false, false, false, 
			     false, false, false, false, 
			 false, false, false, false, false,
		  false, false, false, false, false, false
			};
			
			blank[4] = true;
			JumpingSolitaireState goal = new JumpingSolitaireState(blank);
			
			AStarSearch afs = new AStarSearch(new JumpingEvaluator(21));
			Solution sol = afs.search(start, goal);
			System.out.println ("AStar:" + afs.numOpen + " open, " + afs.numClosed + " closed");
			
			if (sol.succeeded()) {
				if (!verbose) {
					System.out.println(sol.toString());
				} else {
					JumpingEvaluator je = new JumpingEvaluator(21);
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
			} else {
				System.out.println("no solution");
			}
	}
	
	@Test
	public void testTwentyOneAnotherHeuristic() {
		JumpingSolitaireState start = new JumpingSolitaireState(
				new boolean[] {
						     true,
						  true, true,
						true, false, true, 
					 true, true, true, true, 
				   true, true, true, true, true,
				 true, true, true, true, true, true
				});
		
			boolean blank[] = new boolean[] {
				         false,
					  false, false,
			        false, false, false, 
			     false, false, false, false, 
			 false, false, false, false, false,
		  false, false, false, false, false, false
			};
			
			blank[4] = true;
			JumpingSolitaireState goal = new JumpingSolitaireState(blank);
			
			AStarSearch afs = new AStarSearch(new JumpingEvaluator(21));
			Solution sol = afs.search(start, goal);
			System.out.println ("AStar:" + afs.numOpen + " open, " + afs.numClosed + " closed");
			System.out.println(sol.toString());
	}
	
	
	// COSTLY with existing Heuristic. AVOID
	@Test
	public void testTwentyEight() {
		JumpingSolitaireState start = new JumpingSolitaireState(
				new boolean[] {
						     true,
						  true, true,
						true, false, true, 
					 true, true, true, true, 
				   true, true, true, true, true,
				 true, true, true, true, true, true,
			   true, true, true, true, true, true, true
				});
		
			boolean blank[] = new boolean[] {
				         false,
					  false, false,
			        false, false, false, 
			     false, false, false, false, 
			 false, false, false, false, false,
		  false, false, false, false, false, false,
		false, false, false, false, false, false, false
			};
			
			blank[24] = true;
			JumpingSolitaireState goal = new JumpingSolitaireState(blank);
			
			AStarSearch afs = new AStarSearch(new JumpingEvaluator(28));
			Solution sol = afs.search(start, goal);
			System.out.println ("AStar:" + afs.numOpen + " open, " + afs.numClosed + " closed");
			
			if (sol.succeeded()) {
				if (!verbose) {
					System.out.println(sol.toString());
				} else {
					JumpingEvaluator je = new JumpingEvaluator(28);
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
			} else {
				System.out.println("no solution");
			}
	}
}
