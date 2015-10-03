package algs.example.gui.problems.tictactoe.controller;

import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.*;

/**
 * Represents an interactive player whose moves come from the keyboard.
 * 
 * Purpose of player is to interact with user, via keyboard, whenever
 * the makeMove method is invoked.
 * 
 * Subclasses of InteractivePlayer can override 'interpretMove' to 
 * properly determine the move as entered by the user.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class InteractivePlayer extends Player {

	/**
	 * Construct an Interactive player who determines a move by querying user for input.
	 * 
	 * @param mark     Mark to be used for the player.
	 */
	public InteractivePlayer(char mark) {
		super(mark);
	}

	/**
	 * Reads a sequence of characters representing a desired move and returns that
	 * Move or null if input sequence is invalid.
	 * 
	 * @param  board        Current TicTacToe Board
	 * @return Move         Move as interpreted by interpretMove.
	 * @throws Exception    If String typed does not contain integers, or if unable to 
	 *                      access input from keyboard
	 */
	Move readMove (IGameState board, Logic logic) {
	    // This magical incantation enables us to process input characters from
	    // System.in as Strings that can be read in line by line.
		java.io.BufferedReader in = new java.io.BufferedReader (new java.io.InputStreamReader (System.in));
		
		// read in a single line of input from the user (up to, but not including, the carriage return)
		// Expect user to type "col row"
		String s;
		try {
			System.out.println ("Enter move [col row] or press enter to forfeit move: ");
			s = in.readLine();
		} catch (java.io.IOException ioe) {
			// invalid input of some kind, return null
			return null;
		}
		
		java.util.StringTokenizer st = new java.util.StringTokenizer (s);
		int col = -1;
		int row = -1;
		boolean grabbedCol = false;
		boolean grabbedRow = false;
		if (st.hasMoreTokens()) {
			col = Integer.parseInt (st.nextToken());
			grabbedCol = true;
		}
		if (st.hasMoreTokens()) {
			row = Integer.parseInt (st.nextToken());
			grabbedRow = true;
		}
		
		// handle error case when the player does not enter both col and row
		if (!grabbedRow || !grabbedCol) {
			return null;
		}
		
		// Interpret move; subclasses will likely override method.
		return logic.interpretMove (board, col, row, this);
	}
	


	/**
	 * The interactive player retrieves desired move from the keyboard and tries
	 * to make the move on the board.
	 */
	public Move decideMove (IGameState gameState) {
		while (true) {
			try {
				Move m = readMove(gameState, logic);
			
				// no move entered? We must state that we forfeit our move.
				if (m == null) {
					return null;
				}
			
				// Determine if move is valid.
				if (m.isValid(gameState)) {
					return m;
				}
			} catch (Exception e) {
				e.printStackTrace();  // offer something useful.
			}
			
			// if we get here, move is invalid
			System.err.println ("You entered invalid move. Try again.");
		}
	}

}
