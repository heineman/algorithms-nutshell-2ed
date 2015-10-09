package algs.blog.graph.freeCell;

import algs.blog.graph.freeCell.Column;
import algs.blog.graph.freeCell.FreeCellNode;

import junit.framework.TestCase;

public class TestOutput extends TestCase {

	public void testInitial() {

		// Gives cards AC - KC, AD - KD, AH - KH, AS - KS
		short[] cards = new short[52];
		for (int i = 0; i < 52; i++) {
			cards[i] = (short)(1+i);
		}
		
		// now process the deal number
		short[][] dealt = new short[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dealt[i][j] = 0;
			}
		}
		short[] deals = new short[]{2,3,14,0,5,23,39,43,9,10,5,9,31,26,1,2,14,22,19,13,22,10,14,8,9,17,16,8,19,1,3,8,8,4,17,8,3,2,3,9,5,8,8,3,6,1,3,4,0,2,1,0};

		short wLeft = 52;
		for (short i = 0; i < deals.length; i++) {
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
				System.out.print(dealt[c][r] + ",");
				if (dealt[c][r] != 0) {
					cols[c-1].add(dealt[c][r]);
				}
			}
			System.out.println();
		}
		
		FreeCellNode fcn = new FreeCellNode(new short[]{0,0,0,0},new short[]{0,0,0,0},cols);
		
		System.out.println(fcn.toString());
		
		System.out.println("key:" + fcn.key());
	}
	
}
