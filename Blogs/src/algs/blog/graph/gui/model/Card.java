package algs.blog.graph.gui.model;

/**
 * Representation of a Card in the model.
 * <p>
 * The valid suits for a Card are {CLUBS, DIAMONDS, HEARTS, SPADES}, and the valid ranks
 * are 1 (ACE) through 13 (KING). Note that the rank for ACE is lower than other cards.
 * There is a method <code>boolean isAce()</code> that can be used to see if a Card object is an
 * ACE. <code>boolean isFaceCard()</code> returns whether a Card object is a JACK, QUEEN, or
 * KING.
 * <p>
 * @author George Heineman
 */
public class Card  {

	/** Rank of this card. */
	protected int rank;

	/** Specific rank value of an ACE */
	public final static int ACE   = 1;

	/** Specific rank value of a Two */
	public final static int TWO   = 2;

	/** Specific rank value of a Three */
	public final static int THREE = 3;

	/** Specific rank value of a Four */
	public final static int FOUR  = 4;

	/** Specific rank value of a Five */
	public final static int FIVE  = 5;

	/** Specific rank value of a Six */
	public final static int SIX   = 6;

	/** Specific rank value of a Seven */
	public final static int SEVEN = 7;

	/** Specific rank value of an Eight. */
	public final static int EIGHT = 8;

	/** Specific rank value of a Nine */	
	public final static int NINE  = 9;

	/** Specific rank value of a Ten */	
	public final static int TEN   = 10;

	/** Specific rank value of a Jack */
	public final static int JACK  = 11;

	/** Specific rank value of a Queen */
	public final static int QUEEN = 12;

	/** Specific rank value of a King */	
	public final static int KING  = 13;

	/** Suit for this card. */
	protected int suit;

	/** Specific suit value of Clubs */
	public static final int CLUBS    = 1;

	/** Specific suit value of Diamonds */
	public static final int DIAMONDS = 2;

	/** Specific suit value of Hearts. */
	public static final int HEARTS   = 3;

	/** Specific suit value of Spades. */
	public static final int SPADES   = 4;

	/** String abbreviation of Ace. */
	public static final String ACEabbreviation = "A";

	/** String representation of King. */
	public static final String KINGstring = "King";

	/** String abbreviation of King. */
	public static final String KINGabbreviation = "K";

	/** String abbreviation of Queen. */
	public static final String QUEENabbreviation = "Q";

	/** String abbreviation of Jack. */
	public static final String JACKabbreviation = "J";

	/** String abbreviation of Clubs. */
	public static final String CLUBSabbreviation = "C";

	/** String name of Clubs. */
	public static final String CLUBSname = "clubs";	

	/** String abbreviation of Diamonds. */
	public static final String DIAMONDSabbreviation = "D";

	/** String name of Diamonds. */
	public static final String DIAMONDSname = "diamonds";

	/** String abbreviation of Hearts. */
	public static final String HEARTSabbreviation = "H";

	/** String name of HEARTS. */
	public static final String HEARTSname = "hearts";

	/** String abbreviation of Spades. */
	public static final String SPADESabbreviation = "S";

	/** String name of Spades. */
	public static final String SPADESname = "spades";

	/**
	 * Construct a card with the given rank and suit.
	 * <p>
	 * By default, the card is face up and not selected. Since v1.7, the name is calculated on 
	 * demand in <code>getName()</code>, overridden by this class
	 * <p>
	 * @exception IllegalArgumentException if rank or suit is an invalid value.
	 */
	public Card(int rank, int suit) {
		if ((rank < ACE) || (rank > KING)) throw new IllegalArgumentException ("Rank \"" + rank + "\" is an invalid rank for a card.");
		if ((suit < CLUBS) || (suit > SPADES)) throw new IllegalArgumentException ("Suit \"" + suit + "\" is an invalid suit for a card.");

		this.suit = suit;
		this.rank = rank;
	}
	/**
	 * Construct a card that is a copy of the given Card. 
	 * <p>
	 * This includes faceup and selected Information (since V1.5.1). Since v1.7, the name is 
	 * calculated on demand by <code>getName()</code>, overriden by this class.
	 * <p>
	 * @param c ks.common.model.Card
	 */
	public Card(Card c) {
		if (c == null) throw new IllegalArgumentException ("Card constructor cannot be passed null argument.");
		rank = c.getRank();
		suit = c.getSuit();
	}
	/**
	 * Compares cards based on ranks. If returns zero, then cards have the
	 * same rank. If a negative number is returned, existing card is lesser
	 * than target by that amount; similar for positive results.
	 * <p>
	 * If c is a null card, then Integer.MAX_VALUE is returned.
	 * <p>
	 * @return int
	 * @param c ks.common.model.Card
	 */
	public int compareTo(Card c) {
		if (c == null) return Integer.MAX_VALUE;

		// This takes care of all possibilities
		return rank - c.getRank();
	}
	/**
	 * Return a string reflective of this Card.
	 * <p>
	 * This method overrides <code>getName()</code> method in Element.
	 * <p>
	 * @return String
	 * @since V1.7
	 */
	public String getName() {
		return toString (rank, suit);
	}
	/**
	 * Return the rank for this card.
	 * <p>
	 * @return int
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * Return the suit for this card.
	 * <p>
	 * @return int
	 */
	public int getSuit() {
		return suit;
	}
	/**
	 * Determine whether the card is an ACE.
	 * <p>
	 * @return boolean
	 */
	public boolean isAce() {
		return (rank == ACE);
	}
	/**
	 * Determine whether the Card is a Face Card (Jack, Queen, King).
	 * <p>
	 * @return boolean
	 */
	public boolean isFaceCard() {
		return ((rank == JACK) || (rank == QUEEN) || (rank == KING));
	}
	
	/**
	 * Determines whether the Card has an opposite color than the given suit.
	 * <p>
	 * If suit is not a valid suit, then an IllegalArgumentException is thrown.
	 * @return boolean
	 * @param otherSuit     suit to which we are being compared.
	 */
	public boolean oppositeColor (int otherSuit) {
		if ((otherSuit < CLUBS) || (otherSuit > SPADES)) throw new IllegalArgumentException ("Suit \"" + suit + "\" is an invalid suit for a card.");

		// same suit; can't be opposite color
		if (otherSuit == suit) return false;

		switch (otherSuit) {
		case CLUBS: 
			if (suit == SPADES) break; 
			return true;
		case DIAMONDS: 
			if (suit == HEARTS) break;
			return true;
		case HEARTS: 
			if (suit == DIAMONDS) break;
			return true;
		case SPADES: 
			if (suit == CLUBS) break;
			return true;
		}

		// if we get here, then colors are same.
		return false;
	}


	/**
	 * Determines whether the two cards have opposite colors.
	 * <p>
	 * @return boolean
	 * @param c ks.common.model.Card
	 */
	public boolean oppositeColor(Card c) {
		if (c == null) return false;

		return oppositeColor (c.getSuit()); 
	}

	/**
	 * Determine whether the two cards have the same color.
	 * <p>
	 * @return boolean
	 * @param c ks.common.model.Card
	 */
	public boolean sameColor(Card c) {
		if (c == null) return false;

		return sameColor (c.getSuit());
	}

	/**
	 * Determines whether the Card has the same color as the given suit.
	 * <p>
	 * @return boolean
	 * @param otherSuit   other suit to which we are being compared.
	 * @since V2.0
	 */
	public boolean sameColor(int otherSuit) {
		if ((otherSuit < CLUBS) || (otherSuit > SPADES)) throw new IllegalArgumentException ("Suit \"" + suit + "\" is an invalid suit for a card.");

		if (otherSuit == suit) return true;

		switch (otherSuit) {
		case CLUBS: 
			if (suit != SPADES) break;
			return true;
		case DIAMONDS:
			if (suit != HEARTS) break;
			return true;
		case HEARTS:
			if (suit != DIAMONDS) break;
			return true;
		case SPADES:
			if (suit != CLUBS) break;
			return true;
		}

		// If we get here, we must not be the same color.
		return false;
	}
	/**
	 * Determine whether two cards have the same rank.
	 * <p>
	 * If the card passed in is null, then false is returned.
	 * <p>
	 * @return boolean
	 * @param c ks.common.model.Card
	 */
	public boolean sameRank(Card c) {
		if (c == null) return false;

		return (rank == c.getRank());
	}
	/**
	 * Determine whether the two cards have the same suit.
	 * <p>
	 * If the card passed in is null, then false is returned. 
	 * @return boolean
	 * @param c ks.common.model.Card
	 */
	public boolean sameSuit(Card c) {
		if (c == null) return false;

		return (suit == c.getSuit());
	}
	
	
	

	/**
	 * Return a string reflective of this Card.
	 * If the card is faceDown, then the name of card is returned in brackets, i.e., "[10H]".
	 * If the card is selected, then the string is appended with a "*" character.
	 * <p>
	 * Creation date: (10/1/01 8:50:08 PM)
	 * @return java.lang.String
	 * @since V1.7 returns state information also. To return just the name, use getName().
	 */
	public String toString() {
		String name = toString (rank, suit);
		return name;
	}

	/**
	 * Static method for converting a specific suit identifier into its String representation.
	 *
	 * @return java.lang.String
	 * @param suit int
	 * @since V2.0
	 */
	public static String getSuitName (int suit) {
		switch (suit) {
		case CLUBS:     return (CLUBSname); 
		case DIAMONDS:  return (DIAMONDSname); 
		case HEARTS:    return (HEARTSname); 
		case SPADES:    return (SPADESname); 
		default:        throw new IllegalArgumentException ("Illegal Suit for Card::toString().");
		}
	}

	/**
	 * Static method for converting a specific rank/suit into its String representation.
	 *
	 * Creation date: (10/1/01 8:53:13 PM)
	 * @return java.lang.String
	 * @param rank int
	 * @param suit int
	 * @since V1.5.1 throws IllegalArgumentException on improper input
	 */
	public static String toString(int rank, int suit) {
		StringBuffer sb = new StringBuffer();

		// put rank first
		switch (rank) {
		case ACE:   sb.append (ACEabbreviation); break;
		case JACK:  sb.append (JACKabbreviation); break;
		case QUEEN: sb.append (QUEENabbreviation); break;
		case KING:  sb.append (KINGabbreviation); break;
		default:    
			if (rank < 2) throw new IllegalArgumentException ("Illegal Rank for Card::toString().");
		if (rank > 10) throw new IllegalArgumentException ("Illegal Rank for Card::toString().");
		sb.append (rank);
		}

		// next put suit
		switch (suit) {
		case CLUBS:     sb.append (CLUBSabbreviation); break;
		case DIAMONDS:  sb.append (DIAMONDSabbreviation); break;
		case HEARTS:    sb.append (HEARTSabbreviation); break;
		case SPADES:    sb.append (SPADESabbreviation); break;
		default:        throw new IllegalArgumentException ("Illegal Suit for Card::toString().");
		}

		return sb.toString();
	}

	/**
	 * Default equals method. Ignores 'faceup' and 'selected' values. 
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }

		if (o instanceof Card) {
			Card other = (Card) o; 
			return (other.rank == rank) && (other.suit == suit);
		}

		return false; // no good
	}

	/**
	 * Default hashCode method, in case cards are used as key value for hashtable.
	 */
	public int hashCode() {
		return 13*suit + rank;
	}
}
