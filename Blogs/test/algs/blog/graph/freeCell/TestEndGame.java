package algs.blog.graph.freeCell;


import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.list.DoubleLinkedList;
import algs.model.list.DoubleNode;
import algs.model.searchtree.IMove;
import algs.model.tree.BalancedTree;

import junit.framework.TestCase;

public class TestEndGame extends TestCase {

	FreeCellNode goal, start, fcn;
	
	BalancedTree<short[],Boolean> prev;
	
	public void testInitial() {

		
		fcn = new FreeCellNode();
		fcn.insertFoundation((short)49);  // King Clubs;
		fcn.insertFoundation((short)50);  // King Diamonds;
		fcn.insertFoundation((short)51);  // King Hearts;
		fcn.insertFoundation((short)48);  // Queen Spades;
		fcn.cols[0].add((short)52);
		start = (FreeCellNode) fcn.copy();
		
		System.out.println(fcn.toString());
		
		System.out.println("key:" + fcn.key());
		
		goal = new FreeCellNode();
		goal.insertFoundation((short)49);  // King Clubs;
		goal.insertFoundation((short)50);  // King Diamonds;
		goal.insertFoundation((short)51);  // King Hearts;
		goal.insertFoundation((short)52);  // King Spades;
		
		// 10: 9937,0,39320
		// 15: 11611,0,44734
		// 20: 11763,0,45406
		// 40: 11763,0,45412
		prev = new BalancedTree<short[],Boolean>(FreeCellNode.comparator());
		assertTrue(search ());
		//DepthFirstSearch dfs = new DepthFirstSearch(35);
		//Solution sol = dfs.search(fcn, goal);
//		System.out.println("NumClosed:" + dfs.numClosed);
//		System.out.println("NumOpen:" + dfs.numOpen);
//		System.out.println("NumMoves:" + dfs.numMoves);
		
		//System.out.println(sol.toString());
		
	}
	
//	Solve-State(state, prev_states, ret)
//    if (state == empty_state)
//        Push(ret, state)
//        return SOLVED
//    for each move possible on state
//        new_state <- apply(state, move)
//        if (new_state in prev_states)
//            ; Do nothing
//        else
//            add new_state to prev_states
//            if (Solve-State(new_state, prev_states, ret) == SOLVED)
//                Push(ret, state)
//                return SOLVED
//    return UNSOLVED

	java.util.Stack<IMove> moveStack = new java.util.Stack<IMove>();

	
	private void unwind() {
		while (!moveStack.empty()) {
			System.out.println(moveStack.pop());
		}
	}
	
	private boolean search() {
		if (fcn.equals(goal)) {
			unwind();
			return true;
		}
		
		DoubleLinkedList<IMove> s = fcn.validMoves();
		DoubleNode<IMove> st = s.first();
		while (st != null) {
			IMove move = st.value();
			move.execute(fcn);  // make move
			short[] key = (short[]) fcn.key();
			if (prev.search(key) == null) {
				prev.insert(key, Boolean.TRUE);      // record that we've been here
				moveStack.push(move); // record move
				if (search ()) {            // keep going. Might solve!
					return true;
				} else {
					move.undo(fcn);
					moveStack.pop();        // try another move
				}
			} else {
				move.undo(fcn);
			}
			
			st = st.next();                 // advance
		}
		
		return false;                       // dead-end.
	}
	
}
