package algs.model.problems.tictactoe.model;

import algs.debug.IGraphEntity;
import algs.model.gametree.IGameState;

/**
 * The TicTacToe state is determined by a board and the specific logic being
 * used for that board state.
 * <p>
 * To simplify debugging and dot output, this class supports the IGraphEntity interface.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TicTacToeState implements IGameState, IGraphEntity {

	/** state of the game board. */
	TicTacToeBoard  board;
	
	/** Logic of this game. */
	Logic logic;
	
	/** Additional stored information, as needed. */
	Object stored;
	
	/**
	 * The game state is dependent upon a tic-tac-toe board, together with the
	 * logic being used to govern the game.
	 * <p>
	 * Because tic-tac-toe variations may need to store additional state information
	 * with each state, the decision is made to allow the logic to store this 
	 * additional state. We must ensure that copy() properly deals with logic, then.
	 * 
	 * @param board  Board state to use
	 * @param logic  Logic governing the game. May have additional state information.
	 */
	public TicTacToeState(TicTacToeBoard board, Logic logic) {
		this.board = board;
		this.logic = logic;
		
		logic.initializeState(this);
	}
	
	/** 
	 * Copy full state information. 
	 *
	 * This includes debugging information.
	 * @return copy of this state 
	 */
	public TicTacToeState copy () {
		TicTacToeState state = new TicTacToeState (new TicTacToeBoard(board), logic.copy());
		
		state._ctr = this._ctr;
		return state;
	}
	
	/**
	 * Expose board state. Not part of the IGameState interface.
	 * @return the {@link TicTacToeBoard} object for this state
	 */
	public TicTacToeBoard board() {
		return board;
	}
	
	/**
	 * External state may be found in logic. Not part of the IGameState interface.
	 * @return logic associated with state which might store additional information 
	 */
	public Logic logic() {
		return logic;
	}

	/**
	 * Expose Board state as a string.
	 * @return board state using its toString method.
	 */
	public String toString () {
		String s = board.toString() + "\n";
		
		s += "stored Data:\n" + stored;
		return s;
	}
	
	// delegated methods to the board.
	public boolean isWin () { return board.gameWon(); }
	public boolean isDraw() { return board.isDraw(); }
	
	
	/**
	 * Resets to new game, with new board state and X once again starting.
	 * 
	 * Note that the storedData with this state is also updated.
	 * @param newBoard   new state to use
	 */
	public void reset (TicTacToeBoard newBoard) {
		board = newBoard;
	}

	/**
	 * Determine whether the state is the same by comparing the
	 * board state under eight different rotations and reflections.
	 * @param gameState other state against which to compare.
	 * @return true if the two game states are equivalent
	 */
	public boolean equivalent(IGameState gameState) {
		if (gameState == null) { return false; }
		
		TicTacToeState state = (TicTacToeState) gameState;
		return board.sameBoard(state.board);
	}
	
	/** 
	 * Store external (optional) state information with this TicTacToe state.
	 * 
	 * @param o   object to be stored
	 * @return    previous data that had been stored with state (or null if none).
	 */
	public Object storedData(Object o) { 
		Object last = stored;
		stored = o; 
		return last;
	} 
	
	/** 
	 * Return external (optional) state information that may have been
	 * stored with this state.
	 * @return information stored with the state (or null if none).
	 */
	public Object storedData() { return stored; } 
	
	// debugging interface
	private int _ctr;
	public void incrementCounter() { _ctr++; }
	public int counter() { return _ctr; } 
	
	/**
	 * Note that gameState changes constantly, so we can do nothing
	 * more than grab information and cache it here. For this purpose,
	 * we store information with each gameState. If that info
	 */
	public String nodeLabel() {
		// dotty requires column-ordered state, so we rotate information.
		StringBuilder sb = new StringBuilder();
		for (int c = 0; c <= TicTacToeBoard.MaxC; c++) {
			sb.append("{");
			for (int r = 0; r <= TicTacToeBoard.MaxR; r++) {
				char val = board.get(c, r);
				if (val == TicTacToeBoard.EMPTY) {
					sb.append(" ");
				} else {
					sb.append (val);
				}
				
				if (r <= TicTacToeBoard.MaxR-1) { sb.append ("|"); }
			}
			sb.append("}");
			if (c <= TicTacToeBoard.MaxC-1) { sb.append ("|"); }
		}
		
		return sb.toString();
	}
}
