package algs.blog.graph.freeCell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Iterator to process all 32,000 deals in MS FreeCell.
 * 
 * @author George Heineman
 */
public class DealIterator implements Iterator<FreeCellNode> {

	/** Scanner processing file. */
	Scanner sc;
	
	/** Which deal we are on. */
	int deal;
	
	public DealIterator(File dealFile) throws FileNotFoundException {
		sc = new Scanner(dealFile); 
		deal = 0;
	}
	
	/**
	 * Return the deal that that will be returned upon invoking next()
	 * 
	 * @return  the next deal number to be returned.
	 */
	public int getNextDealNumber() {
		return deal;
	}
	
	/**
	 * If the deal has more deals, this returns true; false otherwise.
	 */
	public boolean hasNext() {
		if (deal > 32000) {
			sc.close();
		}
		return (deal <= 32000);
	}

	/** Get the next deal. */
	public FreeCellNode next() {
		String line = sc.nextLine();
		StringTokenizer st = new StringTokenizer(line, "., ");
		
		// get task no.
		int val = Integer.valueOf(st.nextToken()); // bypass number
		if (val != deal) {
			throw new NoSuchElementException ("Deal number (" + deal + ") seems missing from input file for DealIterator.");
		}
		
		// construct deal "shuffle" sequence
		int[] deals = new int[52];
		int idx = 0;
		while (st.hasMoreTokens()) {
			deals[idx++] = Integer.valueOf(st.nextToken());
		}

		// Return the deal and prepare for next time.
		deal++;
		return Deal.initialize(deals);
	}

	/** Remove not supported. */
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported");
	}

}
