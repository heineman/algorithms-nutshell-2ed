package algs.blog.graph.search;


import algs.blog.graph.freeCell.FreeCellNode;
import algs.model.searchtree.INode;

/**
 * Visitor which analyzes the board states uncovered while searching for a
 * FreeCell solution.
 * <p>
 * Of interest, we seek:
 * <ol>
 * <li>Which cards were never moved
 * <li>Which cards never made it into a FreeCell
 * <li>What is the maximum for each Foundation. 
 * </ol>
 * @author George Heineman
 */
public class AnalyzeState implements IVisitor {

	/** Count of states. */
	int count;
	
	/** Full state in FreeCell represented in this array. */
	boolean[] common = null;
	
	/** values that are unchanged. */
	short[] values = null;
	
	/** cards that moved to free cell. */
	short[] freeCellCards = new short[53];
	
	/** max rank for each foundation suit. */
	short[] maxRank = new short[4];
	
	public AnalyzeState() {
		// seed the array. If a value is set to zero, then that card was moved
		// at some point to a free Cell
		for (short i = 1; i <= 52; i++) {
			freeCellCards[i] = i;
		}
	}
	
	public void report () {
		
		System.out.println("Cards that didn't move to a free cell");
		for (int i = 1; i <= 52; i++) {
			if (freeCellCards[i] > 0) {
				System.out.print(FreeCellNode.out(i) + " ");
			}
		}
		System.out.println();
		System.out.println("Max Ranks achieved:");
		for (int i = 0; i < 4; i++) {
			System.out.print(maxRank[i] + " ");
		}
		System.out.println();
		
		System.out.println();
		System.out.println("Positions that are common across all " + count + " states");
		
		// note: Intentionally skip positions [0-3] since these freeCells will
		// never be common across all states.
		System.out.print("Foundation Cells: ");
		String suits="    CDHS";
		for (int i = 4; i <= 7; i++) {
			if (common[i]) {
				System.out.print(suits.charAt(i));
			}
		}
		System.out.println();

		System.out.println("Tableau columns:");
		for (int r = 0; r < 19; r++) {
			for (int c = 0; c < 8; c++) {
				int i = 8 + 19*c + r;
				if (common[i])
					System.out.print(FreeCellNode.out(values[i]));
				else {
					System.out.print("..");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		
	}
	
	@Override
	public void visitNode(INode n, int id) {
		count++;
		
		short[] key = (short[])((FreeCellNode)n).rawkey();
		
		// [0-3] are free cell encoding, [4-7] are foundation Encodings.
		for (int i = 4; i <= 7; i++) {
			if (key[i] > maxRank[i-4]) {
				maxRank[i-4] = key[i];
			}
		}
		
		for (int i = 0; i <= 3; i++) {
			if (key[i] != 0) {
				freeCellCards[key[i]] = 0;  // had moved.
			}
		}
	
		if (common == null) {
			// first time, set the values
			values = new short[key.length];
			common = new boolean [key.length];
			for (int i = 0; i < key.length; i++) {
				values[i] = key[i];
				common[i] = true;
			}
		} else {
			for (int i = 0; i < key.length; i++) {
				if (common[i] && key[i] != values[i]) {
					common[i] = false;
					values[i] = 0;
				}
			}
		}
	}
	
	@Override
	public void visitEdge(int parent, int child) {
		// ignored
	}

}
