package algs.blog.searching.hashbased;

/**
 * In quadratic probing, one investigates a series of bins using a quadratic 
 * formula.
 * <p>
 * The computation of {@link #next(int, int)} is guaranteed to return an 
 * integer in the range [0, tableSize) where tableSize is the size of the 
 * array being probed.
 * 
 * @param <E> type of element. 
 * @author George Heineman
 */
public class QuadraticProbe extends Probe {

	/** Coefficients for bin searching. */
	public final float c1;
	public final float c2;
	
	/**
	 * Construct quadratic probe with given coefficients.
	 * <p>
	 * Resulting computation is <code>h + c1*i + c2*i*i</code>
	 * 
	 * @param tableSize   table Size
	 * @param c1          linear coefficient
	 * @param c2          quardatic coefficient
	 */
	public QuadraticProbe (int tableSize, float c1, float c2) {
		super(tableSize);
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public int next(int h, int i) {
		if (i == 0) { return h; }
		
		return ((int)(h + c1*i + c2*i*i)) % tableSize;		
	}
	
	/** Reasonable string. */
	@Override
	public String toString () {
		return "Quadratic Probe[" + c1 + "," + c2 + "] for table of size " + tableSize;
	}

}
