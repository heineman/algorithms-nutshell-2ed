package algs.model.gametree;

import java.util.Comparator;

/**
 * Defines a comparator function for scores on a gameTree board.
 * <p>
 * Provides default implementations of {@link #MIN} and {@link #MAX}.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IComparator extends Comparator<Integer> {

	/**
	 * Defines the initial value to use when iterating over a set of 
	 * scores (using the comparator).
	 * <p>
	 * For example, for MAX the initial value is to be {@link Integer#MAX_VALUE}
	 * @return special value which is to be considered the default value (will change for MIN and MAX levels).
	 */
	int initialValue();
	
	/**
	 * Return opposite comparator.
	 * <p>
	 * Designed to be used with MIN and MAX.
	 * @return comparator which is opposite of the current one.
	 */
	IComparator opposite();
	
	/** 
	 * MAX comparator takes highest score. If a new GameState is compared 
	 * against a non-existing one (that is, m1 == null), then the new MoveEvaluation
	 * is selected.
	 */
	public static final IComparator MAX = new IComparator() {
		public int compare(Integer sc1, Integer sc2) {
			// handle null cases.
			if (sc1 == null) {
				if (sc2 == null) { return 0; } // why not
				return -1; 
			}
			if (sc2 == null) { return +1; }
			
			if (sc1 > sc2) return +1;
			if (sc1 < sc2) return -1;
			return 0;
		}
		
		/** Useful for debugging. */
		public String toString () { return "MAX"; }

		/** When processing MAX, initial value is MIN_VALUE. */
		public int initialValue() {
			return Integer.MIN_VALUE;
		}

		/** Opposite of MAX is MIN. */
		public IComparator opposite() {
			return MIN;
		}
	};

	/** 
	 * MIN comparator takes lowest score. If a new GameState is compared 
	 * against a non-existing one (that is, m1 == null), then the new MoveEvaluation
	 * is selected.
	 */
	public static final IComparator MIN = new IComparator() {
		public int compare(Integer sc1, Integer sc2) {
			// handle null
			if (sc1 == null) {
				if (sc2 == null) { return 0; } // why not?
				return +1; 
			}
			if (sc2 == null) { return -1; }
			
			if (sc1 < sc2) return +1;
			if (sc1 > sc2) return -1;
			return 0;
		}
		
		/** Useful for debugging. */
		public String toString () { return "MIN"; }

		/** When processing MIN, initial value is MAX_VALUE. */
		public int initialValue() {
			return Integer.MAX_VALUE;
		}
		
		/** Opposite of MIN is MAX. */
		public IComparator opposite() {
			return MAX;
		}
	};
}
