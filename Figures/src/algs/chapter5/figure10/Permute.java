package algs.chapter5.figure10;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Permute implements Iterator<Integer[]> {
	
	/** Size of the input. */
	private int n;

	/** original elements. */
	private int [] elements;
	
	/** Iterator returns a value. This is it. */
	private Integer [] ar;
	
	/** Ongoing permutation computed here. One larger in size for computation. */
	private int [] permutation;

	private boolean next = true;

	// int[], double[] array won't work :-(
	public Permute (int [] els) {
		n = els.length;
		elements = new int [n]; 
		ar = new Integer[n];
		for (int i = 0; i < n; i++) {
			elements[i] = els[i];
			ar[i] = els[i];
		}

		// seed the permutation.
		permutation = new int [n+1];
		for (int i=0; i<n+1; i++) {
			permutation [i]=i;
		}
	}

	private void formNextPermutation () {
		for (int i=0; i<n; i++) {
			// i+1 because perm[0] always = 0
			// perm[]-1 because the numbers 1..size are being permuted
			ar[i] = elements[permutation[i+1]-1];
		}
	}

	public boolean hasNext() {
		return next;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	private void swap (final int i, final int j) {
		final int x = permutation[i];
		permutation[i] = permutation [j];
		permutation[j] = x;
	}

	// does not throw NoSuchElement; it wraps around!
	public Integer[] next() throws NoSuchElementException {

		formNextPermutation ();  // copy original elements

		int i = n-1;
		while (permutation[i]>permutation[i+1])
			i--;

		if (i==0) {
			next = false;
			for (int j=0; j<n+1; j++) {
				permutation [j]=j;
			}
			return ar;
		}

		int j = n;

		while (permutation[i]>permutation[j]) j--;
		swap (i,j);
		int r = n;
		int s = i+1;
		while (r>s) { swap(r,s); r--; s++; }

		return ar;
	}
}
