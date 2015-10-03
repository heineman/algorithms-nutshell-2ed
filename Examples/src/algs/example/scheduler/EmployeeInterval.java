package algs.example.scheduler;

import algs.model.interval.DiscreteInterval;

/**
 * Represents the time block for an employee. 
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class EmployeeInterval extends DiscreteInterval {

	/** The employee. */
	Employee employee;
	
	public EmployeeInterval(Employee empl, int left, int right) {
		super(left, right);
		
		this.employee = empl;
	}
	
	public Employee getEmployee () {
		return employee;
	}

	public final static String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; 
	
	public String toString() {
		int left = getLeft();
		int right = getRight();
		int day = left/1440;    // extract.
		left = left - 1440*day;
		right = right - 1440*day;
		
		int leftHour = left/60;
		int leftMinute = left - leftHour*60;
		
		int rightHour = right/60;
		int rightMinute = right - rightHour*60;
		
		StringBuilder sb = new StringBuilder();
		sb.append (employee.toString() + " @ [" + days[day] + " - ");
		if (leftHour < 10) sb.append('0');
		sb.append(leftHour + ":");
		if (leftMinute <10) sb.append('0');
		sb.append(leftMinute + " - ");
		
		if (rightHour < 10) sb.append('0');
		sb.append(rightHour + ":");
		if (rightMinute <10) sb.append('0');
		sb.append(rightMinute + "]");
		return sb.toString();
	}
	
}
