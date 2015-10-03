package algs.example.model.pseudocodeExample;

import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;

/**
 * Used to contrive example game trees. In this trivial game, the state is a single integer
 * that can be manipulated. 
 * 
 * @author George Heineman
 */
public class MiniMove implements IGameMove {

	int newState;
	
	int oldState;
	
	MiniMove (int newState) {
		this.newState = newState;
	}
	
	public boolean execute(IGameState state) {
		oldState = ((MiniState)state).state;
		((MiniState)state).state = newState;
		return true;
	}

	public boolean isValid(IGameState state) {
		return true;
	}

	public boolean undo(IGameState state) {
		((MiniState)state).state = oldState;
		return true;
	}

	public String toString () { return "move to:" + newState; }
}
