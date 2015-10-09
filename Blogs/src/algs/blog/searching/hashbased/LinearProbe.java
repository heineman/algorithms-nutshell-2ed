package algs.blog.searching.hashbased;

/**
 * In linear probing, one investigates bins a fixed delta distance away.
 * <p>
 * The computation of {@link #next(int, int)} is guaranteed to return an 
 * integer in the range [0, tableSize) where tableSize is the size of the 
 * array being probed.
 * 
 * @author George Heineman
 */
public class LinearProbe extends Probe {
	
	/** Linear probe step. */
	public final int delta;

	/** 
	 * Constructor for linear probe.
	 * @param tableSize   array size
	 * @param delta       delta to inspect bins linearly. Must be in range [1,tableSize)
	 */
	public LinearProbe (int tableSize, int delta) {
		super (tableSize);
		
		if (delta < 1 || delta >= tableSize) {
			throw new IllegalArgumentException("Invalid Delta (" + delta + ") must be in range [1," + tableSize + ")");
		}
		
		this.delta = delta;
	}
	
	@Override
	public int next(int h, int i) {
		if (i == 0) { return h; }
		
		return (h + i*delta) % tableSize;
	}
	
	/** Reasonable string. */
	@Override
	public String toString () {
		return "Linear Probe[" + delta + "] for table of size " + tableSize;
	}

}
