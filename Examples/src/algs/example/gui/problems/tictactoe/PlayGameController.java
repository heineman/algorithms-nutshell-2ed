package algs.example.gui.problems.tictactoe;

import algs.example.gui.problems.tictactoe.controller.GameController;
import algs.model.problems.tictactoe.model.*;

import java.awt.*;
import java.awt.event.*;

/**
 * This controller is the only one who knows about the originating Applet.
 * We want to separate knowledge of the Applet from the GameController.
 * <p>
 * There is a lot of interaction between PlayGameController and GameController,
 * which could be helped by merging the two, but it is preferred to keep
 * the knowledge of Applet from the rest of the code.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class PlayGameController implements ActionListener, MouseListener {
	/** Remember the applet so we can get information from the GUI. */
	TicTacToeApplet applet;
	
	/** Current GameController. */
	GameController controller;

	boolean computerGoesFirst = false;
	boolean computerPlaysSelf = true;

	
	/** 
	 * MousePlayer representing the user whose turn is active. If there
	 * are two human users, then this alternates between the two, by getting
	 * the players from the controller. 
	 */
	MousePlayer humanPlayer;
	
	/**
	 * Controller will need applet to carry out its duties.
	 * 
	 * @param applet
	 */
	public PlayGameController (TicTacToeApplet applet) {
		this.applet = applet;
	}
	
	
	protected void setup () {
		String gameType = applet.getSelectedGameType();
		Logic logic = applet.getGameLogic (gameType);
		if (logic == null) {
			applet.output ("You must select a game type.");
			return;
		}

		controller = new GameController (logic);
			
		// Find out the X Player
		String xType = applet.getXPlayChoice();
		
		// Find out the O Player
		String oType = applet.getOPlayChoice();
		
		computerGoesFirst = false;
		computerPlaysSelf = true;
		
		boolean setOpponentO = false;
		boolean setOpponentX = false;
		
		Player p;
		if (oType.equals (TicTacToeApplet.Human)) {
			// Even though this is a human player, it will be involved in
			// 
			p = new MousePlayer(Player.OMARK);
			p.score(new BoardEvaluation());
			
			computerPlaysSelf = false;
			humanPlayer = (MousePlayer) p;
		} else {
			p = PlayerFactory.createPlayer(oType, Player.OMARK);
			if (p == null) {
				int ply = 5;  // default
				try {
					ply = Integer.valueOf(applet.getOPly().getText());
				} catch (Exception _e) {
					
				}
				p = PlayerFactory.createPlayerWithPly(oType, Player.OMARK, ply);
				setOpponentO = true;
			}
		}
		controller.setOPlayer(p);
		
		if (xType.equals (TicTacToeApplet.Human)) {
			p = new MousePlayer(Player.XMARK);
			p.score(new BoardEvaluation());
			
			computerPlaysSelf = false;
			humanPlayer = (MousePlayer) p;
		} else {
			computerGoesFirst = true;
			p = PlayerFactory.createPlayer(xType, Player.XMARK);
			if (p == null) {
				int ply = 5;  // default
				try {
					ply = Integer.valueOf(applet.getXPly().getText());
				} catch (Exception _e) {
					
				}
				p = PlayerFactory.createPlayerWithPly(xType, Player.XMARK, ply);
				setOpponentX = true;
			}
		}
		controller.setXPlayer(p);
		
		// set logic.
		((Player)controller.getXPlayer()).logic(logic);
		((Player)controller.getOPlayer()).logic(logic);
				
		// set opponents.
		if (setOpponentX) {
			((IntelligentAgent) controller.getXPlayer()).opponent (controller.getOPlayer());
		}
		if (setOpponentO) {
			((IntelligentAgent) controller.getOPlayer()).opponent (controller.getXPlayer());
		}
	}
	
	/**
	 * Respond to when user pressed on the PlayButton.
	 * @param e    the Event that initiates the play of a game.
	 */
	public void actionPerformed(ActionEvent e) {
		setup();
		
		// now that players are set up, we need to have the XPlayer be the current Player
		controller.reset();
		
		// if xType is computer, have him go first now
		if (computerGoesFirst) {
			controller.playTurn();
		}

		if (computerPlaysSelf) {
			int i = 0;
			while (controller.playTurn() == GameController.IN_PROGRESS) {
				// prevent infinite loop by arbitrarily halting after 
				// 100 turns. Note the use of PRE-increment within if
				if (++i > 100) {
					break;
				}
			}
			
			int state = controller.getCurrentState();
			switch (state) {
			
				case GameController.DRAW:
					applet.output ("Game is drawn");
					break;
					
				case GameController.X_WINS:
					applet.output ("X Wins!");
					break;
				
				case GameController.O_WINS:
					applet.output ("O Wins!");
					break;
			}
		}
		
		// refresh view
		applet.repaint();
	}

	/**
	 * Responsible for drawing the board.
	 * @param g
	 */
	public void drawBoard (Graphics g) {
	    if (controller != null) {
			controller.drawBoard (g);
		}
	}
	
	/**
	 * React only to MouseClicked Events (where user presses and
	 * releases).
	 * 
	 * Can't be called until after the mousePlayer has been properly
	 * initialized.
	 * 
	 * @param  e    the MouseEvent created by user.
	 */
	public void mouseClicked(MouseEvent e) {
		// no game set up yet.
		if (controller == null) {
			return;
		}
		
		// prevent any action for a game that isn't in progress.
		if (controller.getCurrentState() != GameController.IN_PROGRESS) {
			return;
		}
		
		// If no human player (i.e., computer vs. computer) do nothing
		if (humanPlayer == null) {
			return;
		}
		
		// leave if it is not OUR turn.
		if ((controller.getCurrentTurn() == GameController.XTURN) &&
		    (humanPlayer.getMark() != Player.XMARK)) {
			return;
		} 
		if ((controller.getCurrentTurn() == GameController.OTURN) &&
			(humanPlayer.getMark() != Player.OMARK)) {
			return;
		}
			
		int x = e.getX();
		int y = e.getY();

		// invert the calculations from drawSpot()
		Cell cell = controller.interpretXY (x, y);
		Move m = controller.interpretMove(cell, humanPlayer);
		
		if (m == null) {
			applet.output ("Invalid move. Try again");
			return;
		}
		
		// once we set the move for the player, it will be retrieved
		// by the controller who then asks the player for his next
		// move. Pretty tricky, huh?
		humanPlayer.setMove (m);
		int rc = controller.playTurn();

		// refresh to ensure that move is visible again.
		applet.repaint();
		
		if (rc != GameController.IN_PROGRESS) {
			if (rc == GameController.DRAW) {
				applet.output ("Game is drawn");
			} else {
				applet.output ("Game over. You Win!");
			}
		} else {
			// update humanPlayer and deal with auto-play
			Player currentPlayer;
			int turn = controller.getCurrentTurn();
			if (turn == GameController.XTURN) {
				currentPlayer = (Player) controller.getXPlayer();
			} else {
				currentPlayer = (Player) controller.getOPlayer();
			}
			 
			if (currentPlayer instanceof MousePlayer) {
				humanPlayer = (MousePlayer) currentPlayer;
				return; 
			}
			
			// since the current player is NOT human, we auto play.
			rc = controller.playTurn();
			if (rc != GameController.IN_PROGRESS) {
				if (rc == GameController.DRAW) {
					applet.output ("Game is drawn");
				} else {
					applet.output ("Game over. You Lose!!!!!");
				}
			}
		}
		
	}

	/** Ignore the following since they are not part of the solution. */
	/** They need to be here BECAUSE we are implementing an interface. */
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
