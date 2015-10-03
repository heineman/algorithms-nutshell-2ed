package algs.chapter5.example5;

/**
 * Simplified String class to show how hashCode method works.
 *
 * Adapted from the Open JDK source code for java.lang.String
 * 
 * @author George Heineman
 */
public class SimpleString {
	
	/** Characters that make up the string. */
	char[] chars;
	
	/** cache computed value (if not computed, then 0). */
	int hash = 0;
	
	/**
	 * Store copy of character array.
	 * 
	 * @param chars
	 */
	public SimpleString (char []chars) {
		this.chars = new char[chars.length];
		for (int i = 0; i < chars.length; i++){ 
			this.chars[i] = chars[i];
		}
	}
	
	/** 
	 * Produce char[] array from existing java.lang.String object.
	 * 
	 * @param s
	 */
	public SimpleString (String s) {
		this.chars = s.toCharArray();
	}
	
	/**
	 * Compute and return hashCode() value for the SimpleString object.
	 * 
	 * Cache for faster access later since object is immutable.
	 */
	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			for (int i = 0; i < chars.length; i++) {
				h = 31*h + chars[i];
			}
			hash = h;
		}
		return h;
	}
	
	/** Reasonable toString method. */
	public String toString () {
		return new String (chars);
	}
}
