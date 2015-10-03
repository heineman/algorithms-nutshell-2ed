package algs.example.scheduler;

/**
 * A timeblock. 
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TimeBlock {
	
	/** Start minute of block. 0=12:00 AM, 1449=11:59 PM */
	int start;       
	
	/** End minute.  0=12:00 AM, 1449=11:59 PM */
	int end;      

	/** Day for the timeblock. 0=Sunday, 6=Saturday. */
	int day;            
	
	/** 
	 * Expected constructor with expected arguments.
	 *
	 * @param d    Day of the block
	 * @param s    Start minute
	 * @param e    End minute
	 */
	public TimeBlock (int d, int s, int e) {
		this.day = d;
		this.start = s;
		this.end = e;
	}

	/** String representation for segment tree debugging. */
	public String toString() {
		int left = day*1440 + start;
		int right = day*1440 + end;
		return "[" + left + "," + right + ")";
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o.getClass() == getClass()) {
			TimeBlock t = (TimeBlock) o;
			return t.start == start && t.end == end && t.day == day;
		}
		
		// incomparable.
		return false;
	}
	
}