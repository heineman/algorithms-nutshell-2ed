package algs.example.gui.problems.tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import algs.example.gui.problems.tictactoe.controller.GameController;

/**
 * Controls a TicTacToe game.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TournamentController extends PlayGameController implements ActionListener {

	/** Playing a tournament is an extensions of playing a game. */
	public TournamentController(TicTacToeApplet applet) {
		super(applet);
	}
	
	/**
	 * Play a tournament of numGames using the given board and two players, one 
	 * representing X and the other O
	 *  
	 * @param numGames  the number of games to play
	 */
	public void tournament (int numGames) {
		setup();

		if (!computerPlaysSelf) {
			applet.output("Unable to play tournament with human involved.");
			return;
		}

		int xWins = 0;
		int oWins = 0;
		int draws = 0;
		
		for (int i = 0; i < numGames; i++) {
			controller.reset();
			
			while (true) {
				int rc = controller.playTurn();
				
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
	
	/** Play 100 games in the tournament. */
	public void actionPerformed(ActionEvent e) {
		tournament(100);
	}

}
