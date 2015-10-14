package algs.blog.graph.freeCell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Initialize deal to one of the pre-determined 32,000 deals in MS FreeCell.
 * 
 * @author George Heineman
 */
public class Deal {

	/** Return the goal node. */
	public static FreeCellNode goal() {
		FreeCellNode goal = new FreeCellNode();
		goal.insertFoundation((short)49);  // King Clubs;
		goal.insertFoundation((short)50);  // King Diamonds;
		goal.insertFoundation((short)51);  // King Hearts;
		goal.insertFoundation((short)52);  // King Spades;
		
		return goal;
	}
	
	static FreeCellNode initialize(int[] deals) {
		// Gives cards AC - KC, AD - KD, AH - KH, AS - KS
		short[] cards = new short[52];
		for (short i = 0; i < 52; i++) {
			cards[i] = (short)(1+i);
		}

		// now process the deal number
		short[][] dealt = new short[9][9];
		for (short i = 0; i < 9; i++) {
			for (short j = 0; j < 9; j++) {
				dealt[i][j] = 0;
			}
		}

		int wLeft = 52;
		for (int i = 0; i < deals.length; i++) {
			int j = deals[i];
			dealt[(i%8)+1][i/8] = cards[j];
			cards[j] = cards[--wLeft];
		}			


		Column[] cols = new Column[8];
		for (int c = 0; c < 8; c++) {
			cols[c] = new Column();
		}


		for (int r = 0; r <= 6; r++) {
			for (int c = 1; c <= 8; c++) {
				if (dealt[c][r] != 0) {
					cols[c-1].add(dealt[c][r]);
				}
			}
		}

		return new FreeCellNode(new short[]{0,0,0,0}, new short[]{0,0,0,0}, cols);
	}

	/** Enable iteration over entire set of 32000 deals. */
	public static final DealIterator iterator(File dealFile) throws FileNotFoundException {
		return new DealIterator(dealFile);
	}
	
	public static final FreeCellNode initialize(File dealFile, int dealNumber) throws IOException {
		java.util.Scanner sc = new java.util.Scanner(dealFile); 
		for (int i = 0; i < 32000; i++) {
			String line = sc.nextLine();
			if (i < dealNumber) {
				continue;
			}

			StringTokenizer st = new StringTokenizer(line, "., ");
			// get task no.
			int val = Integer.valueOf(st.nextToken()); // bypass number
			assert (val == i);

			// construct deal "shuffle" sequence
			int[] deals = new int[52];
			int idx = 0;
			while (st.hasMoreTokens()) {
				deals[idx++] = Integer.valueOf(st.nextToken());
			}

			// prepare the initial board.
			sc.close();
			return initialize(deals);
		}
		
		// not found!
		sc.close();
		return null;
	}
}
