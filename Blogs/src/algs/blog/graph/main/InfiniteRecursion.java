package algs.blog.graph.main;

/**
 * In default Java Virtual machine, stops after depth of 6,408. 
 * 
 * Be Careful Executing this function on a shared machine!
 * 
 * @author George Heineman
 */
public class InfiniteRecursion {
	static int ct = 0;
	
	public static void main (String[]args) {
		System.out.println(++ct);
		main(args);
	}
}
