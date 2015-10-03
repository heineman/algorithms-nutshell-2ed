package algs.example.model.performance.tictactoe;

import algs.example.gui.problems.tictactoe.controller.GameController;
import algs.model.problems.tictactoe.model.IntelligentAgent;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;

/**
 * Engine to launch repetitions of individual Tic Tac Toe variations
 * without requiring either a GUI or text-based UI, so one can evaluate
 * the success of auto-players.
 */
public class TicTacToeEngine {
	/** Determines if any visible output is shown. Needed for interactive players! */
	public static final boolean showGraphicalOutput = false;
	
	/**
	 * Play a tournament of numGames using the given board and two players, one 
	 * representing X and the other O
	 *  
	 * @param c         controller managing the game
	 * @param numGames  the number of games to play
	 */
	public static void tournament (GameController c, int numGames) {
		int xWins = 0;
		int oWins = 0;
		int draws = 0;
		
		for (int i = 0; i < numGames; i++) {
			c.reset();
			
			if (showGraphicalOutput) {
				System.out.println ("Game " + i);
				System.out.println ("----------------------------------");
			}
			
			while (true) {
				int rc = c.playTurn();
				
				if (showGraphicalOutput) {
			    	System.out.println (c.boardState() + "\n");
				}

				if (rc == GameController.IN_PROGRESS) {
					continue;
				}
				
				// if we get here, then we are done.
				switch (rc) {
					case GameController.X_WINS:
						xWins++;
		        		break;
		        		
		        	case GameController.O_WINS:
		        		oWins++;
		        		break;
		        		
		        	case GameController.DRAW:
		        		draws++;
		        		break;
		        }
				
				// break out of the while loop.
				break;
			}
		}
		
		System.out.println ("Statistics [" + new java.util.Date() + "]");
		System.out.println ("(xWins:" + xWins + ", oWins:" + oWins + ", draws:" + draws);
	}
	
	/**
	 * Create a tic tac toe object representing the game, and program
	 * some playable moves on the board. After each move, output the
	 * state of the game by using the 'toString()' method of the
	 * TicTacToeBoard.
	 * 
	 * @param args
	 */
	public static void main (String []args) {

	    // create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		System.out.println ("Against Random player");
	    GameController gc = new GameController (logic);
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 4);
	    Player oPlayer = PlayerFactory.createPlayer(PlayerFactory.Random, Player.OMARK);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    tournament (gc, 100);
	    
	    System.out.println ("Lookahead 4 vs. Lookahead 1");
	    gc = new GameController (logic);
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 4);
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 1);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    ((IntelligentAgent) oPlayer).opponent(xPlayer);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	    tournament (gc, 100);
	    
	    System.out.println ("Lookahead 5 vs. Lookahead 2");
	    gc = new GameController (logic);
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 5);
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 2);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    ((IntelligentAgent) oPlayer).opponent(xPlayer);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	    tournament (gc, 100);
	    
	    System.out.println ("Lookahead 3");
	    gc = new GameController (logic);
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 3);
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 3);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    ((IntelligentAgent) oPlayer).opponent(xPlayer);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	    
	    tournament (gc, 100);
	    
	    System.out.println ("Lookahead 4");
	    gc = new GameController (logic);
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 4);
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 4);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    ((IntelligentAgent) oPlayer).opponent(xPlayer);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	    tournament (gc, 100);
	    
	    System.out.println ("Lookahead 4[X], 5[O]");
	    gc = new GameController (logic);
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 5);
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 5);
	    ((IntelligentAgent) xPlayer).opponent(oPlayer);
	    ((IntelligentAgent) oPlayer).opponent(xPlayer);
	    gc.setXPlayer(xPlayer);
	    xPlayer.logic(logic);
	    gc.setOPlayer(oPlayer);
	    oPlayer.logic(logic);
	   
	    tournament (gc, 100);
	}

}
