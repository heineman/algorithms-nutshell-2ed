package algs.blog.graph.freeCell;
import algs.model.searchtree.DepthTransition;
import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Evaluation function for FreeCell boards used by AStar since it
 * takes into account the g(n) Depth from initial position.
 * 
 * @author George Heineman
 */
public class FreeCellEvaluator implements IScore {
		
		/**
		 * @see algs.model.searchtree.IScore#score(INode)
		 */
		public void score(INode state) {
			state.score(eval(state));
		}
		
		/**
		 * 
		 * @see algs.model.searchtree.IScore#score(INode)
		 * @param state
		 */
		public int eval(INode state) {
			FreeCellNode node = (FreeCellNode) state;
			
			// what cards can be placed in the foundation piles?
			int neededCards[] = new int[4];
			neededCards[0] = node.foundationEncoding[0]+1;
			neededCards[1] = node.foundationEncoding[1]+1;
			neededCards[2] = node.foundationEncoding[2]+1;
			neededCards[3] = node.foundationEncoding[3]+1;
			
			// simple computation: How buried are the aces? Estimate that is
			// the number of moves we need
			int numBuried = 0;
			for (int c = 0; c < 8; c++) {
				int nc = node.cols[c].num; 
				for (int i = 0; i < nc; i++) {
					int card = node.cols[c].cards[i];
					int suit = ((card-1)%4);       // subtract 1 since '0' is invalid card.
					int rank = 1 + ((card-1)>>2);  // rank extracted this way.
					
					for (int s = 0; s < 4; s++) {
						if ((s == suit) && (neededCards[s] == rank)) {
							// found it. Now count how many bury it
							numBuried += nc-i-1;
						}
					}
				}
			}

			int numVacancies = node.numberVacant();
			
			// compute g^(n) -- how many moves so far
			int gn = 0;
			DepthTransition t = (DepthTransition) state.storedData();
			if (t != null) { 
				gn = t.depth; 
			}
			
			// If no vacancies, assume we have much more work to do.
			if (numVacancies == 0) { numBuried *= 2; } // just got harder!
			
			// As more cards are placed in foundation, then gn becomes smaller
			gn += (26 - neededCards[0] - neededCards[1] - neededCards[2] - neededCards[3]);
			
			// offset by the number of buried cards that have yet to be moved.
			return gn + numBuried;
		}
	}
