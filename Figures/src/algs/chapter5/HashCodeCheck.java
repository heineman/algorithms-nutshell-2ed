package algs.chapter5;

/**
 * Generate the negative hashCode method for string referenced in text
 * in chapter 5 (p. 124)
 * 
 * @author George Heineman
 */
public class HashCodeCheck {
	public static void main(String[] args) {
		String s = "aaaaaa";
		System.out.println("Hashcode for " + s + " is: ");
		System.out.println(s.hashCode());
	}
}
