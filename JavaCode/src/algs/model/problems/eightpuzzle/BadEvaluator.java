package algs.model.problems.eightpuzzle;

import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Bad evaluation function.
 * <p>
 * Not sure this is even an admissable h(n) function to use.
 * <p>
 * Instead of considering the goal, take differences of distant
 * cells and sum. Note that we subtract in (what should be) decreasing
 * order of the results. Then compare against 16 (the ideal). For example,
 * 
 * <pre>
 *   8 1 -       6 - 3   = 3
 *   6 7 3       2 - 0   = 2
 *   2 5 4       5 - 1   = 4
 *               4 - 8   = -4
 *                       -----
 *                        5
 *                        
 *   1 4 8       7 - 0   = 7
 *   7 3 -       6 - 8   = -2
 *   6 5 2       5 - 4   = 1
 *               2 - 1   = 1
 *                        ----
 *                         7                    
 * </pre>
 *                        
 * Has distance of 19 from the ideal of 16.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BadEvaluator implements IScore {
	
	/**
	 * @see algs.model.searchtree.IScore#score(INode)
	 */
	public void score(INode state) {
		state.score(eval(state));
	}
	
	/**
	 * Description of this measure.
	 */
	public String toString() {
		return "Take difference of opposite cells (across from" + "\n" +  
			   "the center square) and compare against the " +    "\n" + 
			   "ideal of 16. Ignore blank cell.";
	}
	
	/**
	 * Eval = g(n) + W(n), where g(n) is length of the path from initial to
	 * node n, and W(n) computes difference of opposite cells.
	 * 
	 * @param state    state being evaluated
	 * @see algs.model.searchtree.IScore#score(INode)
	 */
	public int eval(INode state) {
		EightPuzzleNode node = (EightPuzzleNode) state;
		
		int sumDistance = 0;
		sumDistance += node.cell(1, 0) - node.cell(1, 2);
		sumDistance += node.cell(2, 0) - node.cell(0, 2);
		sumDistance += node.cell(2, 1) - node.cell(0, 1);
		sumDistance += node.cell(2, 2) - node.cell(0, 0);
		
		return Math.abs(16 - sumDistance);
	}
	
	/**
	 * Eval = g(n) + W(n), where g(n) is length of the path from initial to
	 * node n, and W(n) counts number of misplaced tiles in the state description
	 * 
	 * @param state    state being evaluated
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @return         String value representing evaluation of heuristic suitable for reading.
	 */
	public String evalString(INode state) {
		EightPuzzleNode node = (EightPuzzleNode) state;
		
		int sumDistance = 0;
		String eval = "";
		sumDistance += node.cell(1, 0) - node.cell(1, 2);  eval += "(" + node.cell(1,0) + "-" + node.cell(1,2) + ")+";
		sumDistance += node.cell(2, 0) - node.cell(0, 2);  eval += "(" + node.cell(2,0) + "-" + node.cell(0,2) + ")+"; 
		sumDistance += node.cell(2, 1) - node.cell(0, 1);  eval += "(" + node.cell(2,1) + "-" + node.cell(0,1) + ")+";
		sumDistance += node.cell(2, 2) - node.cell(0, 0);  eval += "(" + node.cell(2,2) + "-" + node.cell(0,0) + ")=";
		eval += sumDistance;
		
		return eval+"=|16-" + sumDistance + "|=" + (16-sumDistance);
	}
}
