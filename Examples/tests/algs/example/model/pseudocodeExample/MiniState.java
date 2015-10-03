package algs.example.model.pseudocodeExample;

import algs.debug.IGraphEntity;
import algs.model.gametree.IGameState;

/**
 * Used for pseudo code examples.
 * 
 * @author George Heineman
 */
public class MiniState implements IGameState, IGraphEntity {

	/** All state is encoded as single integer. */
	int state;
	
	protected MiniState(int state) {
		this.state = state;
	}
	
	public IGameState copy() {
		return new MiniState(state);
	}

	public boolean equivalent(IGameState state) {
		return this.state == ((MiniState)state).state;
	}

	public boolean isDraw() {
		return false;
	}

	public boolean isWin() {
		return false;
	}

	public String nodeLabel() {
		return ""+state;
	}
	
	public String toString () { return "[" + state + "]"; }

	// debugging interface
	private int _ctr;
	public void incrementCounter() { _ctr++; }
	public int counter() { return _ctr; } 
}
