package algs.example.scheduler;

import java.util.*;


/**
 * Application to showcase scheduling on small example.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Main {
	
	/**
	 * Launch example.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main (String []args) {
		
		// create a meeting scheduler.
		StoreScheduler ss = new StoreScheduler();
		
		// Mondays from [close,open) so [9,13) means 9AM to 1PM
		TimeBlock key1 = new TimeBlock (2, 540, 780);
		
		// Tuesdays from [close,open) so [11,18) means 11AM to 6PM
		TimeBlock key2 = new TimeBlock (2, 660, 1080);
		
		// Wednesdays from [close,open) so [12,20) means 12PM to 8PM
		TimeBlock key3 = new TimeBlock (2, 720, 1200);
		
		ss.add(key1, new Employee("Alice"));
		ss.add(key2, new Employee("Bob"));
		ss.add(key3, new Employee("Carl"));
		
		// find the employees able to work Tuesdays (2) between 10:00 AM and 11:01 AM 
		for (Iterator<EmployeeInterval> it = ss.employees(new TimeBlock (2, 600, 661)); it.hasNext(); ) {
			EmployeeInterval empl = it.next();
			System.out.println (empl);
		}		
	}
}
