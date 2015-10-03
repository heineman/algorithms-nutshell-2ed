package algs.example.chapter7.fifteenSolitaire.ordered;

import java.util.Comparator;
import algs.model.searchtree.IMove;
import algs.example.chapter7.fifteenSolitaire.JumpMove;

/**
 * For arbitrary triangles, compute weights that look like the following:
 * 
 *             1
 *            2 2
 *           3 3 3
 *          3 4 4 3
 *         2 3 4 3 2
 *        1 2 3 3 2 1
 *        
 * @author George Heineman
 *
 */
public class OrderMoves implements Comparator<IMove> {
	
	/** weights. */
	final int[] weights;
	
	/** Construct knowing the weight for each position. */
	public OrderMoves (int n) {
		int k = n*(n+1)/2;
		this.weights = new int[k];
		
		// compute half-way point.
		int half = n/2;
		
		// assign weights to all and then reduce the triangles on the corners
		for (int i = 0; i < k; i++) { weights[i] = half+1; }
		
		
		int idx = 0;
		// top triangle
		int base = 0;
		for (int i = 0; i < half; i++) {
			base++;
			for (int j = 0; j <= i; j++) {
				weights[idx++] = base; 
			}
		}
		
		// remaining two triangles are computed in reverse.
		int offset = -1;  // first time through must be 0 for even squares; 1 for odd squares
		int adjust = 0;
		if (n % 2 == 1) { adjust = 1; }
		idx = k-1;
		for (int i = half-1; i >= 0; i--) {
			offset++;
			for (int j = 0; j <= i; j++) {
				weights[idx--] = 1+j+offset;
			}
			
			idx -= (offset + adjust);
			
			for (int j = 0; j <= i; j++) {
				weights[idx--] = half-j;
			}
		}
	}
	
	/**
	 * If moves are assigned scores representing the pegs at play, then 
	 * we can select moves that are near the "center" which suggest that game
	 * try to keep things moving without heading into off-beat tangents.
	 */
	public int compare(IMove o1, IMove o2) {
		JumpMove j1 = (JumpMove) o1;
		JumpMove j2 = (JumpMove) o2;
		
		int sc1 = weights[j1.from] + weights[j1.over] + weights[j1.to];
		int sc2 = weights[j2.from] + weights[j2.over] + weights[j2.to];
		
		// place higher one first. That is, if SC1 is higher then returned
		// value will be negative, suggesting that J1 comes before J2.
		return sc2 - sc1;
	}
	
}
