package algs.example.chapter7.fifteenSolitaire;

/**
 * Class to support the solution of the peg-puzzle
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Pair {
	
	public final int row;
	public final char diagonal;
	
	/** Construct and make final. */
	public Pair (char diagonal, int row) {
		this.diagonal = diagonal;
		this.row = row;
	}
	
	/** Reasonable toString. */
	public String toString () {
		return "" + diagonal + row;
	}
	
	/** Return NW location or null if invalid. */
	public Pair NW () {
		char d2 = (char)(diagonal - 1);
		int r2 = row - 1;
		if (r2 < 0) return null;
		if (d2 < 'A') return null;
		return new Pair (d2, r2);
	}
	
	/** Return NE location or null if invalid. */
	public Pair NE () {
		int r2 = row - 1;
		if (r2 < 0) return null;
		return new Pair (diagonal, r2);
	}
	
	/** Return E location or null if invalid. */
	public Pair E () {
		char d2 = (char)(diagonal + 1);
		return new Pair (d2, row);
	}
	
	/** Return SE location or null if invalid. */
	public Pair SE () {
		char d2 = (char)(diagonal + 1);
		int r2 = row + 1;
		return new Pair (d2, r2);
	}
	
	/** Return SW location or null if invalid. */
	public Pair SW () {
		int r2 = row + 1;
		if (r2 < 0) return null;
		return new Pair (diagonal, r2);
	}
	
	/** Return NW location or null if invalid. */
	public Pair W () {
		char d2 = (char)(diagonal - 1);
		if (d2 < 'A') return null;
		return new Pair (d2, row);
	}
	
	/** Reasonable hashcode. */
	public int hashCode() {
		return (256*diagonal)+ row;
	}
	
	/** Standard equals method. */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Pair) {
			Pair p = (Pair) o;
			return p.row == row && p.diagonal == diagonal;
		}
		
		return false;
	}
	
}
