package algs.blog.graph.freeCell;

import java.util.StringTokenizer;


/** 
 * Represents a column of cards in Free Cell.
 * 
 * @author George Heineman
 */
public class Column {
	// BE CAREFUL!
	public int num = 0;
	
	public short[] cards = new short[20];    // maximum size

	// cache value for speedy access
	String cached = null;
	
	/** Default constructor. */
	public Column() {
		
	}
	
	/** for debugging. */
	public static Column fromString(String s) {
		StringTokenizer st = new StringTokenizer(s, " ");
		Column col = new Column();
		while (st.hasMoreTokens()) {
			col.add(FreeCellNode.fromCard(st.nextToken()));
		}
		
		return col;
	}
	
	/** Pre-build one. */
	public Column (short initial[]) {
		num = 0;
		for (int i = 0; i < initial.length; i++) {
			cards[i] = initial[i];
			num++;
		}
	}
	
	// returns rank of the bottom-most card (or zero if empty).
	public short rank () {
		if (num == 0) { return 0; }
		return (short)(1 + ((cards[num-1]-1)>>2));
	}
	
	// returns color of the bottom-most card (true for SPADES/CLUBS, false for HEARTS/DIAMONDS.
	// CAREFUL. NO CHECK IS MADE FOR EMPTY COLUMN.
	public boolean isBlack () {
		int s = ((cards[num-1]-1)%4);
		return (s == FreeCellNode.CLUBS || s == FreeCellNode.SPADES);
	}
	
	// NO CHECK IS MADE TO SEE IF THIS IS ACCURATE!
	public void add (short card) {
		if (card> 52 || card == 0) {
			System.err.println("LKSD");
		}
		
		cards[num++] = card;
		cached = null;
	}
	
	// NO CHECK IS MADE TO SEE IF THIS IS ACCURATE!
	/** Remove the topmost card. */
	public short remove () {
		short card = cards[--num];
		cards[num] = 0;  // remove.
		return card;
	}
	
	
	public Column copy() {
		Column col = new Column();
		col.num = num;
		short[] cc = new short[cards.length];
		for (int i = 0; i < num; i++) {
			cc[i] = cards[i];
		}
		col.cards = cc;
		
		return col;
	}
	
	/** Create in reverse order to have keys be different early. */
	public String toString () {
		if (cached != null) { return cached; }

		StringBuilder sb = new StringBuilder();
		for (int i = num-1; i >= 0; i--) {
			sb.append (FreeCellNode.out(cards[i]));
		}
		
		cached = sb.toString();
		return cached;
	}

	public short get(short r) {
		if (r > num) { return 0; }
		return cards[r];
	}

}
