package algs.blog.graph.gui.view;

import algs.blog.graph.gui.controller.CardEnumeration;
import algs.blog.graph.gui.model.Card;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Load up all the images for a specific deck of cards (or the default if none selected).
 * <p>
 * @author George Heineman 
 */
public class CardImagesLoader implements Runnable {
	/** Where images will be stored */
	protected static final String imageDirectory = "artifacts" + java.io.File.separatorChar + "images" + java.io.File.separatorChar;

	/** Loaded object containing images. This is set by thread.*/
	protected static CardImages loadedImages = null;

	/** Default CardImages location. */
	protected static final String defaultName = "tiny";

	/** Has thread loaded all cards yet?. */
	protected boolean readyStatus = false;

	/** Default name for image of reverse of card. */
	protected static final String backResourceName = "Back";

	/** Deck type. Used within thread. */
	protected String deckType = defaultName;

	/** Peer for image observation. */
	protected Component peer = null;

	/** Thread in which the loadAllCards will run. */
	protected java.lang.Thread thread = null;

	/**
	 * Load deck. 
	 */
	public CardImagesLoader(Component peer) {
		this.peer = peer;
	}
	
	
	/**
	 * Return CardImages object for this deck (whether in cache or freshly loaded) using given ICardImageStatus observer.
	 * <p>
	 * If the deck is to be loaded, then peer is used as an <code>ImageObserver</code>.
	 * @param peer java.awt.Component 
	 * @return CardImages
	 */
	public static CardImages getDeck (Component peer) {
		if (loadedImages != null) {
			// make sure we signal that we have loaded!
			return loadedImages;
		}

		CardImagesLoader cil = new CardImagesLoader(peer);
		cil.start();

		// wait until cards are all fetched
		while (! cil.ready()) {
			try {
				Thread.sleep (250);  // wait until ready or interrupted.
			} catch (InterruptedException ie) {
				System.err.println ("CardImagesLoader::getDeck(). Unable to completely load deck: " + defaultName);
				break;
			}
		}

		// by this point, cil will complete and call 'output.end()'.
		loadedImages = cil.getLoadedCardImages();
		return loadedImages;
	}
	
	/**
	 * Return images loaded by thread.
	 * <p>
	 * This method is only accessible from within the view package.
	 * @return java.awt.Image
	 */
	synchronized CardImages getLoadedCardImages () {
		return loadedImages;
	}
	
	/**
	 * Determine if cards have been loaded.
	 */
	public boolean ready () {
		return readyStatus; 
	}
	
	/**
	 * Retrieve all images. This thread will self-terminate once all are loaded.
	 */
	public void run() {
		// The media Tracker ensures all images are fully loaded: This avoids the
		// arbitrary race conditions that may happen when running a plugin before all
		// images have been properly loaded,
		java.awt.MediaTracker mt = new java.awt.MediaTracker (peer);

		// Create a CardImages to house the deck of card images.
		CardImages ci = new CardImages();

		CardEnumeration ce = new CardEnumeration ();
		int idx = 1;
		while (ce.hasMoreElements()) { 
			Card c = (Card) ce.nextElement();
			String key = c.getName();

			// extract from resource file (prepend images directory)
			try {
				File f = new File (imageDirectory + deckType + "/" + key + ".gif");
				java.net.URL url = f.toURI().toURL();
				
				// java.net.URL url = this.getClass().getResource (str);
				Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
				mt.addImage (img, idx++);
				ci.setCardImage (c, img);
			} catch (MalformedURLException mue) {
				return;
			}
		}

		// Also get Back (already in the images directory)
		try {
			File f = new File (imageDirectory + deckType + "/" + backResourceName + ".gif");
			java.net.URL url = f.toURI().toURL();
			Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
			mt.addImage (img, idx++);
			ci.setCardReverse (img);
		} catch (MalformedURLException mue) {
			return;
		}
		
		try {
			mt.waitForAll();
		} catch (InterruptedException ie) {
		}

		// keep around so thread can return this value in synchronized method getLoadedCardImages
		loadedImages = ci;

		// we are done.
		readyStatus = true;
	}
	
	
	/**
	 * Launch the thread to execute run.
	 */
	public void start() {
		thread = new Thread(this); 
		thread.start(); 
	}
}
