package algs.example.scheduler;

import java.util.Collection;
import java.util.Iterator;

import algs.model.IBinaryTreeNode;
import algs.model.IInterval;
import algs.model.interval.SegmentTree;
import algs.model.interval.StoredIntervalsNode;

/**
 * A retail store manager is responsible for creating the work schedule to ensure proper 
 * staffing levels. The store is open for 14 hours each day, from 8AM to 10PM, and eight
 * employees must be working at any given time. There are twenty employees who are each 
 * contracted to work 40 hours per week, and there must be eight employees working during 
 * each hour.
 * 
 * Each employee submits a list of time blocks when they can work, for example, "Monday 
 * from 8AM to 2PM". To ensure continuity in the store, the manager requests that each
 * proposed time block must be at least four consecutive hours.
 * 
 * The manager receives all employee requests and puts together the weekly schedule. A 
 * timeblock data structure can represent each proposed time block and could be used to 
 * instantiate key objects to be used in a Hashtable. However, this fails to account for 
 * multiple employees that submit identical time blocks.
 * 
 * @param  T 
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class StoreScheduler<T extends IBinaryTreeNode<T>> {
	
	/** 
	 * Create SegmentTree Index for the time segments.
	 * 
	 * Note that within the nodes for these segments, we maintain 
	 */
	SegmentTree<StoredIntervalsNode<T>> times;
	
	/** Min range. */
	public static int min = 0;
	
	/** Max range. */
	public static int max = 10079;
	
	/**
	 * Construct a Store Scheduler.
	 */
	public StoreScheduler () {
		
		// construct the segments.
		times = new SegmentTree<StoredIntervalsNode<T>>(min, max, StoredIntervalsNode.getConstructor());
	}
	
	/**
	 * Each meeting time is a contiguous block from a given minute [0-1439].
	 * 
	 * Since the time covers a seven day period, we create a full line as shown.
	 * 
	 * Sunday:       0  -  1439
	 * Monday:    1440  -  2879
	 * Tuesday:   2880  -  4319
	 * Wednesday: 4320  -  5759
	 * Thursday:  5760  -  7199
	 * Friday:    7200  -  8639
	 * Saturday:  8640  - 10079
	 * 
	 */
	public static IInterval timeInterval (Employee empl, TimeBlock timeblock) {
		int left = timeblock.day*1440 + timeblock.start;
		int right = timeblock.day*1440 + timeblock.end;
		return new EmployeeInterval(empl, left, right);
	}
	
	/**
	 * Add the proposed timeblock for the given employee as a potential time during 
	 * which the employee could be scheduled to work.
	 * 
	 * @param timeblock    A fully formed timeblock
	 * @param empl         Employee information
	 */
	public void add (TimeBlock timeblock, Employee empl) {
		times.add(timeInterval(empl, timeblock));
	}
		
	/**
	 * Return set of Employees associated with the given timeblock
	 * 
	 * @param timeblock
	 */
	public Iterator<EmployeeInterval> employees(TimeBlock timeblock) {
		StoredIntervalsNode<?> root = times.getRoot();
		final Collection<IInterval> ivals = root.gather(timeInterval(null, timeblock));
		
		// create an iterator that extracts from the interval iterator the employees.
		return new Iterator<EmployeeInterval>() {

			Iterator<IInterval> it = ivals.iterator();
			
			public boolean hasNext() { return it.hasNext(); }

			public EmployeeInterval next() {
				return (EmployeeInterval) it.next();
			}

			public void remove() { throw new UnsupportedOperationException("no remove");}
			
		};
	}
}
