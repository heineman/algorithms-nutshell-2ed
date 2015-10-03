package algs.example.chapter5;

/**
 * Shows how the {@link String#hashCode()} method can actually
 * return a negative number.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ModuloSurprise {
	
	/**
	 * Run until the string of 'aaaa's returns a hashCode
	 * that is negative (but only for first 1023 characters).
	 */
	public static void main (String []args) {
		int x = -5;
		
		System.out.println(x + " % " + 3 + " = " + (x % 3));
		
		String s = "a";
		
		while (s.length() < 1024) {
			int h = s.hashCode();
			
			s = s + "a";
			if (h < 0) {
				System.out.println("hash of \"" + s + "\" is " + h);
				break;
			}
		}
	}
}
