package algs.example.chapter5;

/**
 * It is technically possible that a string could have its hashCode exactly equal to
 * Integer.MIN_VALUE. Should this happen, it would be impossible to have this string 
 * be placed in a hash if the hashCode were based on the following logic:
 * 
 *    hash = Math.abs(s.hashCode()) % b
 *    
 * The reason is because in Java, Math.abs(Integer.MIN_VALUE) is equal to the same.
 * This is an odd consequence of the way integer values are stored.
 * 
 * So the goal of this class is to try to build a string containing only 
 * lowercase letters which computes hashCode of Integer.MIN_VALUE.
 *
 * I am leaving this partial code example here (in all of its incomplete glory) because this
 * is where I stopped after I found the string "GzDxg@" whose hashCode equals Integer.MIN_VALUE;
 * 
 * I had hoped to find an all alphabetic sequence, but the two closest hits all ended with "z" as the
 * final letter, which made this impossible. Good luck if you can find one!
 * 
 * @author heineman
 */
public class FindStringHash {
	public static void main(String[] args) {
		// this produces MIN_VALUE
		System.out.println(Math.abs(Integer.MIN_VALUE));
		
		// this still produces MIN_VALUE
		int min = 0 - Integer.MIN_VALUE;
		System.out.println(min);

		// start with idea of trying to add characters until overflow is reached
		// and then refine earlier characters based on how far away the result 
		// is from Integer.MIN_VALUE.

		// start with middle of alphabet.
		String word = "m";
		int power = 1;
		int numPower = 1;
		while (word.hashCode() > 0) {
			word = word + "m";
			if (power > 0) {
				power *= 31;
				numPower++;
			}
		}

		int powers31[] = new int[numPower];
		powers31[0] = 1;
		System.out.println("Powers of 31:");
		for (int i = 1; i < numPower; i++) {
			powers31[i] = powers31[i-1]*31;
			System.out.print(powers31[i] + ",");
		}
		System.out.println(); 

		System.out.println(word + " has hashCode " + word.hashCode());

		// "GzDxg@".     HIT!
		// GzDxez:2147483644
		// HZcxez:2147483644
		// HYzzzz:2147247889
		// HTzzzz:2142630284
		// HVzzzz:2144477326
		
		System.out.println("Now trying all lowercase strings of 6 characters to find one");
		System.out.println("With highest hashcode.");
		String maxWord = "";
		System.out.println("I AAg@".hashCode() + ":" + Integer.MIN_VALUE);
		int maxH = 0;
		for (char c1 = 'G'; c1 <= 'G'; c1++) {
			StringBuffer sb = new StringBuffer(""+c1);
			for (char c2 = 'z'; c2 <= 'z'; c2++) {
				if (!Character.isAlphabetic(c2) && !Character.isDigit(c2) && c2 != '@') { continue; }
				sb.append(c2);
				for (char c3 = ' '; c3 <= 'z'; c3++) {
					if (!Character.isAlphabetic(c3) && !Character.isDigit(c3) && c3 != '@') { continue; }
					sb.append(c3);
					for (char c4 = ' '; c4 <= 'z'; c4++) {
						if (!Character.isAlphabetic(c4) && !Character.isDigit(c4) && c4 != '@') { continue; }
						sb.append(c4);
						for (char c5 = ' '; c5 <= 'z'; c5++) {
							if (!Character.isAlphabetic(c5) && !Character.isDigit(c5) && c5 != '@') { continue; }
							sb.append(c5);
							for (char c6 = ' '; c6 <= 'z'; c6++) {
								if (!Character.isAlphabetic(c6) && !Character.isDigit(c6) && c6 != '@') { continue; }
								sb.append(c6);
								String s = sb.toString();

								int h = s.hashCode();
								if (h == Integer.MIN_VALUE) {
									System.out.println("HIT:" + s);
									return;
								}
								if (h > maxH) {
									maxH = h;
									maxWord = s;

									System.out.println(maxWord + ":" + maxH);
								}

								sb.deleteCharAt(5);
							}
							sb.deleteCharAt(4);
						}
						sb.deleteCharAt(3);
					}
					sb.deleteCharAt(2);
				}
				sb.deleteCharAt(1);
			}
			sb.deleteCharAt(0);
		}

		// best word 
		long distance = maxWord.hashCode();
		distance -= Integer.MAX_VALUE;
		System.out.println("distance:" + distance);

		System.out.println("so go back to " + word + " (" + word.hashCode() + ") which has dist=" + distance);
		System.out.println("and try other prefixes");
		for (char ch = 'l'; ch >= 'a'; ch--) {
			String samp = ch + word.substring(1);
			int h2 = samp.hashCode();
			if (h2 > 0) {
				break;
			}
		}


		// in a similar way that you might make change for a dollar bill, make incremental
		// adjustments getting closer and closer to (Integer.MAX_VALUE - t) where 't' is 
		// a value between 0 and 13, and then we can simply revise the final character
		// to achieve the vale.
		System.out.println(word + ":" + word.hashCode());
		char ch = (word.charAt(word.length()-1));
		ch++;
		String other = word.substring(0, word.length()-1) + ch;
		System.out.println(other + ":" + other.hashCode());

		int p = numPower-1;
		while (distance > 13) {
			System.out.println(word + ":" + word.hashCode() + " distance:" + (Integer.MAX_VALUE - word.hashCode()));
			if (distance > powers31[p]) {
				String oldWord = word;
				word = increaseLetter(word, word.length()-p);

				long newH = word.hashCode();
				if (newH < 0) {
					word = oldWord;
					p--;
				}
			} else {
				p--;
			}
		}

	}

	/** Just increase the given position by 1. */
	static String increaseLetter(String word, int pos) {
		char ch = word.charAt(pos);
		ch++;
		return word.substring(0, pos) + ch + word.substring(pos+1);
	}
}
