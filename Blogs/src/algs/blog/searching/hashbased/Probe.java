package algs.blog.searching.hashbased;

/**
 * Strategy for choosing the ith bin to probe during insert or search.
 * 
 * @author George Heineman
 */
public abstract class Probe {

	/** Table size. */
	public final int tableSize;
	
	/**
	 * A probe must know the size of the table in which it is being used.
	 * 
	 * @param tableSize   size of table (must be non-negative).
	 */
	public Probe (int tableSize) {
		if (tableSize < 0) {
			throw new IllegalArgumentException("Invalid table size (" + tableSize + "): must be non-negative.");
		}
		
		this.tableSize = tableSize;
	}
	
	/**
	 * Return the ith bin to investigate.
	 * <p>
	 * Note that if i is 0, then the value of h must be returned.
	 * 
	 * @param h  Initial hash value, must be in range [0, tableSize)
	 * @param i  A non-negative integer
	 * @return   Next bin to investigate
	 */
	public abstract int next (int h, int i);
}
