package algs.example.scheduler;

/**
 * Employee to be scheduled. 
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Employee {
	
	/** Employee name. */
	String name;
	
	public Employee (String s) {
		this.name = s;
	}
	
	/** Return reasonable representation. */
	public String toString () {
		return name;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o.getClass() == getClass()) {
			Employee empl = (Employee) o;
			return name.equals (empl.name);
		}
		
		// incomparable.
		return false;
	}
}
