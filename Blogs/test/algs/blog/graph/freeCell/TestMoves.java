package algs.blog.graph.freeCell;

import java.util.Iterator;

import junit.framework.TestCase;


import algs.blog.graph.freeCell.Column;
import algs.blog.graph.freeCell.FreeCellNode;
import algs.blog.graph.freeCell.moves.ColumnToColumnMove;
import algs.blog.graph.freeCell.moves.ColumnToFreeMove;
import algs.model.list.DoubleLinkedList;
import algs.model.searchtree.IMove;

public class TestMoves extends TestCase {
	
	public void testBigMove() {
		FreeCellNode fcn = new FreeCellNode(
				new short[]{0, 0, 0, 0},
					new short[]{0,0,0,0},
				new Column[] {
						Column.fromString("5D 4S 3H 2C"),
						new Column(),
						new Column(),
						Column.fromString("5H"),
						Column.fromString("TD 4C"),
						Column.fromString("QS QC 3D"),
						new Column(),
						new Column()});
		
		fcn.sortMap();
		System.out.println(fcn);	
		
		DoubleLinkedList<IMove> moves = fcn.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove m = it.next();
			System.out.println(m);
			m.execute(fcn);
			m.undo(fcn);
		}
		assertEquals (14, moves.size());
		System.out.println(fcn);	
	}
	
	public void testColumns() {
		FreeCellNode fcn = new FreeCellNode(
				new short[]{0, FreeCellNode.fromCard("TH"), 
						FreeCellNode.fromCard("KH"),
						FreeCellNode.fromCard("KS")},
					new short[]{1, 0, 3, 1},
				new Column[] {
						Column.fromString("KD"),
						Column.fromString("QC JD JC 9D 9S AD 5S 4D 3S 2D"),
						Column.fromString("KC QD JS TD"),
						Column.fromString("3C 6H 6C 7C 2S 3D JH TC 9H 8C 7D 6S 5H"),
						Column.fromString("4C QS 8S 7H"),
						Column.fromString("2C 6D 4S 4H TS 8D"),
						Column.fromString("5D"),
						Column.fromString("5C 9C QH 8H 7S")});
		
		fcn.sortMap();
		System.out.println(fcn);	
		
		DoubleLinkedList<IMove> moves = fcn.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			IMove m = it.next();
			m.execute(fcn);
			m.undo(fcn);
		}
		assertEquals (10, moves.size());
		System.out.println(fcn);	
	}
	
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
		
		// 11982
		short[] deals = new short[]{2,3,14,0,5,23,39,43,9,10,5,9,31,26,1,2,14,22,19,13,22,10,14,8,9,17,16,8,19,1,3,8,8,4,17,8,3,2,3,9,5,8,8,3,6,1,3,4,0,2,1,0};

		short wLeft = 52;
		for (short i = 0; i < deals.length; i++) {
			int j = deals[i];
			dealt[(i%8)+1][i/8] = cards[j];
			cards[j] = cards[--wLeft];
		}			
		
		
		Column[] cols = new Column[8];
		for (short c = 0; c < 8; c++) {
			cols[c] = new Column();
		}
		
		
		for (short r = 0; r <= 6; r++) {
			for (short c = 1; c <= 8; c++) {
				System.out.print(dealt[c][r] + ",");
				if (dealt[c][r] != 0) {
					cols[c-1].add(dealt[c][r]);
				}
			}
			System.out.println();
		}
		
		FreeCellNode fcn = new FreeCellNode(new short[]{0,0,0,0},new short[]{0,0,0,0},cols);
		
		System.out.println(fcn.toString());
		
		DoubleLinkedList<IMove> moves = fcn.validMoves();
		assertEquals (11, moves.size());
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		
		// Move the 4Clubs to the 4Hearts
		IMove move = new ColumnToColumnMove(6, 7, 1);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		// Move KClubs to free
		move = new ColumnToFreeMove(0);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		// Move JDiamonds to free
		move = new ColumnToFreeMove(0);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		System.out.println(fcn);
		
		moves = fcn.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		assertEquals (10, moves.size());
		System.out.println("------------------------");
		
		// move two more cards into freecell (QDiamonds, Ten Clubs
		move = new ColumnToFreeMove(5);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		move = new ColumnToFreeMove(5);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		moves = fcn.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		assertEquals (1, moves.size());
		
		// move final move
		move = new ColumnToColumnMove(4, 3, 1);
		move.isValid(fcn); // preps the move to be executed
		move.execute(fcn);
		
		moves = fcn.validMoves();
		for (Iterator<IMove> it = moves.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		assertEquals (0, moves.size());
	}
}
