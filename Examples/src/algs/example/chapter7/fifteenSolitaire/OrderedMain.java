package algs.example.chapter7.fifteenSolitaire;

import java.util.Iterator;

import algs.example.chapter7.fifteenSolitaire.ordered.OrderMoves;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.DepthFirstSearch;
import algs.model.searchtree.IMove;
import algs.model.searchtree.Solution;

/**
 * Show solution to basic triangular solitaire by ordering moves.
 * 
 * With n rows, there are n(n+1)/2 pegs to move. Do this for boards with 15
 * and 21 pegs. 
 * 
 * Runs out of Heap space even with just n=7. However, for:
 * 
 *            ORDERED                  UNORDERED
 * n=5     23 open, 390 closed         24 open, 570 closed
 * n=6     59 open, 7401 closed 	   41 open, 19614 closed
 * 
 * Which is a nice improvement, but not enough to enable n=7 to be computed
 * within the default Java configuration.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class OrderedMain {
	

	
	public static void main(String[] args) {
		
		for (int n = 5; n < 9; n++) {
			// force a recalculation when n changes.
			JumpingSolitaireState.compMoves = null;
			
			// impose order on the moves.
			OrderMoves om = new OrderMoves(n);
			JumpingSolitaireState.order = om;
			
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

			// use DFS to place. Will decreasingly be helpful! Had best
			// set the limit to the number of pegs - 1.
			int numPegs = n*(n+1)/2;
			DepthFirstSearch dfs = new DepthFirstSearch(numPegs);

			JumpingSolitaireState stateStart = new JumpingSolitaireState(start);
			JumpingSolitaireState stateGoal = new JumpingSolitaireState(blank);
			Solution sol = dfs.search(stateStart, stateGoal);
			
			System.out.println (dfs.numOpen + " open, " + dfs.numClosed + " closed");
	
			DoubleLinkedList<IMove> mv = sol.moves();
			for (Iterator<IMove> it = mv.iterator(); it.hasNext(); ) {
				IMove m = it.next();
	
				m.execute(stateStart);
				System.out.println(stateStart);
			}
		}
	}
}
