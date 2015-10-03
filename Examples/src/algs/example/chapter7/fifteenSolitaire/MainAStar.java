package algs.example.chapter7.fifteenSolitaire;

import java.util.Iterator;

import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;
import algs.model.searchtree.Solution;
import algs.model.searchtree.AStarSearch;

/**
 * Show solution to basic triangular solitaire.
 * 
 * With n rows, there are n(n+1)/2 pegs to move. Do this for boards with 15
 * and 21 pegs, but don't attempt with 28 since it consumes too many resources.
 * 
 * n=5     24 open  571 closed
 * n=6    128 open 1231 closed
 * 
 * runs out of heap space for n=7, unfortunately.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MainAStar {

	
	public static void main(String[] args) {
		
		for (int n = 5; n < 8; n++) {

			// force a recalculation when n changes.
			JumpingSolitaireState.compMoves = null;
			
			System.out.println(n + ":");
			// produce START board full of pegs except middle one on third row.
			boolean[] start = new boolean [n*(n+1)/2];
			for (int i = 0; i < start.length; i++) { start[i] = true; }
			start[4] = false;
			
			// See where we can end up... Start with empty board. If n is 
			// odd then place in middle of last row. If n is even then place
			// in the first hole on the top row.
			boolean blank[] = new boolean [n*(n+1)/2];
			if (n % 2 == 0) {
				blank[0] = true;
			} else {
				int spot = (n+1)*(n/2);   // simple formula!
				blank[spot] = true;
			}

			// compute these first so the computed Moves is ready for the JumpingEvaluator.
			// strange and unexpected dependency. Ah, the price of being generic.
			JumpingSolitaireState stateStart = new JumpingSolitaireState(start);
			JumpingSolitaireState stateGoal = new JumpingSolitaireState(blank);

			// use DFS to place. Will decreasingly be helpful!
			AStarSearch asts = new AStarSearch(new JumpingEvaluator(n*(n+1)/2));
			Solution sol = asts.search(stateStart, stateGoal);
			
			System.out.println (asts.numOpen + " open, " + asts.numClosed + " closed");
	
			DoubleLinkedList<IMove> mv = sol.moves();
			for (Iterator<IMove> it = mv.iterator(); it.hasNext(); ) {
				IMove m = it.next();
	
				m.execute(stateStart);
				System.out.println(stateStart);
			}
		}
	}
}
