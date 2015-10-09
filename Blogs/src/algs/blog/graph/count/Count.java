package algs.blog.graph.count;

import algs.model.tests.common.*;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.*;

/**
 * Compute Knuth's walk for free cell estimation from a given INode.
 *
 * @author George Heineman
 */
public class Count {

	/**
	 * Given a board state, use Knuth's random walk algorithm to estimate the
	 * total size of the search tree N moves into the future, based solely on 
	 * the ability of {@link INode} objects to return a valid set of moves. 
	 * 
	 * @param node     current board state.
	 * @param depth    maximum depth into the future to inspect
	 */
	public static void computeCount (INode node, int depth) {

		// Run an increasing number of trials, leading to increasing accuracy of the
		// results. Only look forward a fixed number of 16 moves.
		int LOW_T = 1024;
		int HIGH_T = 524288;

		// total number of children of these board states.
		long aggregate = 0;

		// number of board states considered in this random exploration
		long aggregateCount = 0;

		for (int n = 1; n <= depth; n++) {
			for (int m = LOW_T; m <= HIGH_T; m *= 8) {
				TrialSuite ts = new TrialSuite();
				for (int t = 0; t < m; t++) {
					INode here = node.copy();

					int r = 0;
					long lastEstimate = 1;
					while (r < n) {
						DoubleLinkedList<IMove> moves = here.validMoves();
						int numChildren = moves.size();

						if (numChildren == 0) {
							lastEstimate = 0;
							break;
						}

						// select one valid move at random.
						int rnd = (int)(Math.random() * numChildren);
						DoubleNode<IMove> tmp = moves.first();
						while (--rnd > 0) {
							tmp = tmp.next();
						}

						// compute statistics on average number of moves...
						aggregateCount++;
						aggregate += numChildren;


						IMove nm = tmp.value();
						nm.execute(here);

						// estimate that all children at this level have same number of children
						lastEstimate = lastEstimate*numChildren;
						r++;
					}

					ts.addTrial(n, 0, lastEstimate);
				}

				System.out.println(n + "," + m + "," + ts.computeTable());
			}
		}

		double avg = aggregate;
		avg /= aggregateCount;

		System.out.println("Average number of moves:" + avg);
	}
}
