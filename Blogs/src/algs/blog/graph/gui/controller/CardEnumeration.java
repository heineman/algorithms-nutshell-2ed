package algs.blog.graph.gui.controller;

import algs.blog.graph.gui.model.Card;

/**
 * Returns an Enumeration of Card objects, from ACE-Club through KING-spades.
 * 
 * @author George Heineman
 */
public class CardEnumeration implements java.util.Enumeration<Card> {

	/** The current rank. */
	protected int rank;

	/** The current suit. */
	protected int suit;

	/**
	 * Construct a CardEnumeration that will enumerate over all cards in 
	 * a normal playing deck, starting from ACE of CLUBS through to KING
	 * of SPADES.
	 */
	public CardEnumeration() {
		super();

		/** Start at the ACE of CLUBS */
		rank = Card.ACE;
		suit = Card.CLUBS;
	}
	
	/**
	 * Returns true if there are more Cards in this Enumeration.
	 */
	public boolean hasMoreElements() {
		if (suit <= Card.SPADES) return true;

		return false;
	}
	
	/**
	 * Returns the current Card in the Enumeration and moves on.
	 */
	public Card nextElement() {
		Card c = new Card (rank, suit);

		/** Enumerate over each rank, and then by suit. */
		if (++rank > Card.KING) {
			suit++;
			rank = Card.ACE;
		}

		return c;
	}
}
