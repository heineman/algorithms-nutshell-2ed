package algs.example.gui.problems.tictactoe.variations.slide;

/**
 * The slide state must store the current phase.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SlideState {
	/** Knows the current phase. */
	private int phase;
	
	/** 
     * Knows the current turnNumber.
     * <p>
     * Incremented after each turn
     */
    private int turnNumber;
    
    public SlideState() {
    	turnNumber = 1;
    	phase = SlideLogic.PLACE_PHASE;
    }
    
    public void setPhase (int p) {
    	this.phase = p;
    }
    
    public int getPhase() {
    	return phase;
    }
    
    public void advanceTurn () {
    	 turnNumber++;
         if (turnNumber == 9) {
         	phase = SlideLogic.SLIDE_PHASE;
         }
    }
    
    public void reverseTurn() {
    	 turnNumber--;
         if (turnNumber < 9) {
         	phase = SlideLogic.PLACE_PHASE;
         }
    }
}
