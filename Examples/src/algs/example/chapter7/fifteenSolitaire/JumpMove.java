package algs.example.chapter7.fifteenSolitaire;

import algs.model.searchtree.IMove;
import algs.model.searchtree.INode;

/**
 * Jump removes the 'over' peg and the 'from' peg, adding in the new peg
 * to the 'to' location.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class JumpMove implements IMove {

	public final int from, over, to;
	
	public JumpMove (int from, int over, int to) {
		this.from = from;
		this.over = over;
		this.to = to;
	}
	
	/** make the move */
	public boolean execute(INode state) {
		JumpingSolitaireState jss = (JumpingSolitaireState) state;
		jss.filled[from] = false;
		jss.filled[over] = false;
		jss.filled[to] = true;
		return true;
	}

	public boolean isValid(INode state) {
		JumpingSolitaireState jss = (JumpingSolitaireState) state;
		return jss.filled[from] && jss.filled[over] && !jss.filled[to];
	}


	public boolean undo(INode state) {
		JumpingSolitaireState jss = (JumpingSolitaireState) state;
		jss.filled[from] = true;
		jss.filled[over] = true;
		jss.filled[to] = false;
		return true;
	}

	public String toString () {
		return from + " -[" + over + "]- " + to;
	}
	
}
