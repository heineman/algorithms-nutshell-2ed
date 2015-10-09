package algs.blog.graph.gui.view;

import algs.blog.graph.gui.model.Card;

import java.awt.Image;


/**
 * Responsible for returning images for cards (as well as reverse).
 * <p>
 * This class is only responsible for storing images that some other entity
 * has created.
 * <p>
 * Calculates card size based on reverseImage.
 * 
 * @author George Heineman 
 */
public class CardImages {

	/** The background image. */
	protected Image backImage = null;
	
	/** Store each image locally once retrieved. */
	protected java.util.Hashtable<String,Image> imageCache = new java.util.Hashtable<String,Image>();
	
	/** Calculated overlap value (-1 if not yet calculated) */
	protected int calculatedOverlap = -1;
	/** 
	 * Name of this set of card images.
	 * @since V2.1
	 */
	protected String name = null;
	
	/** Calculated height value (-1 if not yet calculated) */
	protected int calculatedHeight = -1;
	
	/** Calculated width value (-1 if not yet calculated) */
	protected int calculatedWidth = -1;
	
/**
 * Create CardImages object with no associated images.
 */
public CardImages() {
	super();
}
/**
 * The key is the resource name (such as "10H" or "Back").
 * The CardImageLoader will have already placed images into the cache.
 * <p>
 * @return Image
 * @param key    Typically the Card.toString() value for a given Card.
 */
protected Image cacheLookup(String key) {
	return (Image) imageCache.get (key);
}
/**
 * The key is the resource name (such as "10H" or "Back").
 * The CardImageLoader will be calling this method.
 * <p>
 * @param key     Key for storing the card (typically the toString() value from Card).
 * @param image   The image for the given card.
 */
protected void cacheStore(String key, Image image) {
	imageCache.put (key, image);
}
/**
 * Extracts the card resource based upon the name.
 * @return java.awt.Image
 * @param rank   the rank of the desired card.
 * @param suit   the suit of the desired card.
 */
public Image getCardImage(int rank, int suit) {
	// if rank/suit are invalid, the following method will throw IllegalArgumentException
	String rep = Card.toString (rank, suit);
	return cacheLookup (rep);
}
/**
 * Get the card image given a Card Object.
 * <p>
 * @return Image
 * @param c    card whose image is being requested.
 */
public java.awt.Image getCardImage(Card c) {
	if (c == null) {
		throw new IllegalArgumentException ("CardImages::getCardImage() given null Card object.");
	}
	
	String rep = c.getName ();
	return cacheLookup (rep);
}
/**
 * Get the reverse image for a card.
 * <p>
 * @return Image
 */
public Image getCardReverse() {
	return backImage;
}
/**
 * Return the individual height (in pixels) of each card.
 * <p>
 * @return int
 */
public int getHeight() {
	if (calculatedHeight == -1) {
		if (backImage != null) {
			calculatedHeight = backImage.getHeight (null);
		}
	}

	// was 96
	return calculatedHeight;
}
/**
 * Return Name for this deck class.
 * <p>
 * @return String    deck name.
 */
public String getName () {
	return name;
}
/**
 * Overlap is roughly 23% of the cardHeight.
 * <p>
 * @return int    size in pixels.
 */
public int getOverlap() {
	if (calculatedOverlap == -1) {
		calculatedOverlap = (int) (getHeight() * .23);
	}

	// was 22
	return calculatedOverlap;
}
/**
 * Return the width of each card.
 * <p>
 * @return int    size in pixels.
 */
public int getWidth() {
	if (calculatedWidth == -1) {
		if (backImage != null) {
			calculatedWidth = backImage.getWidth (null);
		}
	}

	//was 71
	return calculatedWidth;
}
/**
 * Used by CardImagesLoader to set the appropriate image once loaded.
 * <p>
 * @param c    the card whose image is being stored.
 * @param img   the image of the given Card object.
 */
public void setCardImage(Card c, Image img) {
	if (c == null) {
		throw new IllegalArgumentException ("CardImages::setCardImage() received null Card object.");
	}
	
	if (img == null) {
		throw new IllegalArgumentException ("CardImages::setCardImage() received null Image object.");
	}

	String key = c.getName();
	cacheStore (key, img);
}
/**
 * Set the reverse image for a card.
 * <p>
 * All calculations for card width, height, and offset are based on this card's dimensions.
 * <p>
 * @param reverseImage   image for the reverse of a card.
 */
public void setCardReverse(Image reverseImage) {
	//cacheStore (backResourceName, reverseImage);
	if (reverseImage == null) {
		throw new IllegalArgumentException ("CardImages::setCardReverse() received null image.");
	}
	
	backImage = reverseImage;
}
/**
 * Set Name for this deck class.
 * <p>
 * @param name      deck name.
 */
public void setName (String name) {
	if (name == null) {
		throw new IllegalArgumentException ("CardImages::setName() received null parameter.");
	}
	
	this.name = name;
}

/**
 * Manually set the overlap to use.
 * <p>
 * @return overlap    size in pixels.
 */
public void setOverlap(int overlap) {
	if (overlap <= 0) {
		throw new IllegalArgumentException ("overlap value must be positive");
	}
	
	calculatedOverlap = overlap;
}
}
