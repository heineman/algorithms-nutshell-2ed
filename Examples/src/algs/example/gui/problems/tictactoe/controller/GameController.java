/*
 * TicTacToe complete infrastructure.
 */
package algs.example.gui.problems.tictactoe.controller;

import java.awt.Graphics;

import algs.example.gui.problems.tictactoe.Drawer;
import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;
import algs.model.problems.tictactoe.model.*;


/**
 * Controls the play of a TicTacToe game.
 * 
 * Knows who goes first (typically X), and understands the allowed
 * moves (see interpretMove).
 * 
 * Much of this class evolved and grew as needs and responsibilities
 * were added.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GameController {
	
	/** The actual board object within the game state. */
	TicTacToeBoard board;
	
	/** The game state. */
	IGameState gameState;
	
	/** Entity to draw board. */ 
	Drawer drawer = new Drawer();
	
	/** Total number of moves made in game. */
	int numberMoves;	 
	
	/** Logic of the game. */
	Logic logic;

	/** Current status [one of IN_PROGRESS, DRAW, X_WINS, O_WINS] */
	int currentState;

	/** Current player. */
	IPlayer currentPlayer;

	/** XPlayer. */
	IPlayer xPlayer;
	
	/** OPlayer. */
	IPlayer oPlayer;
	
	/** Different results of playing a turn. */
	public static final int IN_PROGRESS = 0;
	public static final int DRAW = 1;
	public static final int X_WINS = 2;
	public static final int O_WINS = 3;
	
	/** Represents that it is Xs turn. */
	public static final int XTURN = 1;
	
	/** Represents that it is Os turn. */
	public static final int OTURN = 2;
	
	/**
	 * Constructs the controller and starts off with X ready to go.
	 * 
	 * @param logic    The Logic for the game variation (cannot be null).
	 */
	public GameController (Logic logic) {
		if (logic == null) {
			throw new RuntimeException ("GameController cannot have null logic.");
		}
		board = new TicTacToeBoard();
		gameState = new TicTacToeState (board, logic);
		this.logic = logic;
		
		reset();
	}
	
	/**
	 * Player is given the opportunity to make a move, and that
	 * move is returned (or null if forfeit game).
	 * 
	 * @return    false if player forfeits, otherwise true when valid move is made.
	 */
	boolean makeMove (IPlayer ip) {
		Player p = (Player) ip;
		
		IGameMove m = p.decideMove(gameState);
		if (m == null) {
			return false;  // player forfeits game.
		}
		
		if (!m.execute(gameState)) {
			return false;       // insisted on making invalid move! FORFEIT
		}
		return true; 
	}
	
	/**
	 * Causes controller to play a single turn, equivalent to a player making
	 * a single move.
	 *
	 * If move is invalid, then the game remains In progress.
	 * 
	 * If the number of moves exceeds the number allowed by the variation,
	 * then a draw is assmed.
	 */
	public int playTurn () {
		
		if (tooManyMoves()) {
			return setCompletionState(DRAW);
		}
		
		if (player() == xPlayer) {
			// swap to O and then make the move (if it is allowed).
			player(oPlayer);
		
			if (! makeMove(xPlayer)) {
				// still more moves to be made.
				return setCompletionState(IN_PROGRESS);
			}
			
			// move is made.
			advance();
			
			// check for X Victory
			if (gameState.isWin()) {
				return setCompletionState(X_WINS);
			}
		} else {
			// swap to X and then make the move (if it is allowed).
			player(xPlayer);
		
			// must be Os turn
			if (! makeMove(oPlayer)) {
				// still more moves to be made.
				return setCompletionState(IN_PROGRESS);
			}
			
			// move is made.
			advance();
			
			// check for O Victory
			if (gameState.isWin()) {
				return setCompletionState(O_WINS);
			}			
		}
		
		// if we get here, we are either drawn or still in progress
		if (gameState.isDraw()) {
			return setCompletionState(DRAW);
		}
		
		// still more moves to be made.
		return setCompletionState(IN_PROGRESS);
	}

	/**
	 * Return the opponent for the given player.
	 * 
	 * If invalid, or no opponent (!?) return null.
	 * 
	 * @param p    The desired Player whose opponent we require. 
	 * @return     The opponent Player (or null if invalid player to begin with)
	 */
	public IPlayer getOpponent (Player p) {
		if (p == null) return null;  // error
		if (p == xPlayer) {
			return oPlayer;
		}
		
		if (p == oPlayer) {
			return xPlayer;
		}
		
		// no idea
		return null;
	}

	/**
	 * Returns the OPlayer for the game being played.
	 */
	public IPlayer getOPlayer() {
		return oPlayer;
	}
	
	/**
	 * Determines the OPlayer for the game being played.
	 *    
	 * @param player   non-null player to be the designated O player
	 */
	public void setOPlayer(IPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException ("OPlayer cannot be null.");
		}
		
		oPlayer = player;
	}
	
	/**
	 * Determines the XPlayer for the game being played.
	 *    REQUIRES: player != null
	 * @param player
	 */
	public void setXPlayer(Player player) {
		if (player == null) {
			throw new IllegalArgumentException ("XPlayer cannot be null.");
		}
		
		xPlayer = player;
	}
	
	/**
	 * Returns the XPlayer for the game being played.
	 */
	public IPlayer getXPlayer() {
		return xPlayer;
	}	

 	/** Set the completion state of game. Returns same value to simplify the way this method is invoked. */
	public int setCompletionState(int state) {
		currentState = state;
		return currentState;		
	}
	
	public int currentState() {
		return currentState;
	}

    /**
     * Return Cell interpreting the (x,y) point
     * 
     * @param x
     * @param y
     */
    public Cell interpretXY(int x, int y) {
        if (drawer != null) {
	        return drawer.interpretXY (x, y);
	    }
	    
	    // nothing to say
	    return null;
    }

    /**
     * Given x,y coordinates, return the move equivalent
     * given the TicTacToe variation.
     * 
     * Returns null for invalid move. 
     *  
     * @param cell
     * @param p
     */
	public Move interpretMove (Cell cell, Player p) {
		return logic.interpretMove (gameState, cell.col, cell.row, p);    
	}
    
    /**
     * Draws the board.
     * 
     * Note that we no longer have to expose board state!
     * @param g
     */
    public void drawBoard(Graphics g) {
	    drawer.drawBoard (g, board);
    }

    public void reset() {
		numberMoves = 0;
		currentState = IN_PROGRESS;
		currentPlayer = xPlayer;
		
		// HACK: TODO: FIX
    	((TicTacToeState)gameState).reset(board = new TicTacToeBoard());
    }
    
    public void advance() {
		numberMoves++;
	}

    /** Some variations may have too many moves. */
	public boolean tooManyMoves() {
		return (numberMoves > logic.maxNumberMoves());
	}
    
	public int getCurrentState() {
		return currentState;
	}

	/** Return the current turn. */
	public int getCurrentTurn() {
		if (currentPlayer == xPlayer) {
			return XTURN;
		}
		
		return OTURN;
	}

	public TicTacToeBoard boardState() {
		return board;
	}
	
	public IPlayer player() {
		return currentPlayer;
	}
	
	public void xPlayer(IPlayer xp) {
		xPlayer = xp;
	}
	
	public void oPlayer(IPlayer op) {
		oPlayer = op;
	}
	
	/** Set the current player. */
	public void player(IPlayer player) {
		if (player != xPlayer && player != oPlayer) {
			throw new IllegalArgumentException ("Unable to set player to non-existing X or O player.");
		}
		
		currentPlayer = player;
	}

	/**
	 * Return opponent for the given player.
	 */
	public IPlayer opponent(IPlayer p) {
		if (p == xPlayer) { return oPlayer; }
		if (p == oPlayer) { return xPlayer; }
		
		// nothing special to say
		return null;
	}
	
	public IPlayer xPlayer() {
		return xPlayer;
	}
	
	public IPlayer oPlayer() {
		return oPlayer;
	}

}
