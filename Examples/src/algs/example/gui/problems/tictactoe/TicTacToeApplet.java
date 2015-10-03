package algs.example.gui.problems.tictactoe;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import algs.example.gui.problems.tictactoe.variations.annihilate.AnnihilateLogic;
import algs.example.gui.problems.tictactoe.variations.neighbor.NeighborLogic;
import algs.example.gui.problems.tictactoe.variations.slide.SlideLogic;
import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;


/**
 * GUI for TicTacToe.
 * <p>
 * Allows different types of players to be constructed to play head-to-head
 * competition of variations of tic tac toe. If both players are automated 
 * then a tournament can be constructed to determine winning strategies.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TicTacToeApplet extends Applet {

	/**
	 * To keep Eclipse happy.
	 */
	private static final long serialVersionUID = -4023583279512574298L;

	/** Controller for managing the game play. */
	PlayGameController controller;
	
	/** GUI information. */
	List gameTypes;
	Choice oPlayChoice;
	Choice xPlayChoice;
	TextArea output;
	Button tournamentButton;
	
	private TextField xPly = null;
	private Label xPlyLabel = null;
	private TextField oPly = null;
	private Label oPlyLabel = null;
	
	public static String Human = "Human";
	
	/**
	 * Every applet has an init() method to initialize itself
	 */
	public void init() {
		// Tell Applet that we will take full responsibility for layout
		oPlyLabel = new Label();
		oPlyLabel.setBounds(new Rectangle(330, 203, 38, 16));
		oPlyLabel.setText("Ply:");
		xPlyLabel = new Label();
		xPlyLabel.setBounds(new Rectangle(330, 152, 35, 16));
		xPlyLabel.setText("Ply:");
		setLayout (null);
		
		Label l = new Label ("Select Game Type:");
		l.setBounds (220, 5, 140, 20);
		add (l);

		// Take Action
		Button rulesButton = new Button ("Rules");
		rulesButton.setBounds (380, 30, 80, 20);
		add (rulesButton);
		rulesButton.addActionListener (new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String s = gameTypes.getSelectedItem();
				Logic log = getGameLogic(s);
				if (log != null) {
					output (log.rules());
				}
			}
			
		});
		
		gameTypes = new List (5);
		populateGames (gameTypes);
		
		gameTypes.setBounds(220,30,140,90);
		add (gameTypes);
		
		Label l2 = new Label ("X Player:");
		l2.setBounds (220, 125, 150, 20);
		add (l2);
		
		// Choices  
		xPlayChoice = new Choice();
		xPlayChoice.add(PlayerFactory.Random);
		xPlayChoice.add(PlayerFactory.AlphaBeta);
		xPlayChoice.add(PlayerFactory.MiniMax);
		xPlayChoice.add(PlayerFactory.NegMax);
		xPlayChoice.add(Human);
		xPlayChoice.setBounds (220, 150, 98, 21);
		add (xPlayChoice);

		// default to random
		xPlayChoice.select(PlayerFactory.Random);
		this.add(getXPly(), null);
		this.add(xPlyLabel, null);
		xPly.setVisible(false);
		xPlyLabel.setVisible(false);

		xPlayChoice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String sel = xPlayChoice.getSelectedItem();
					
					// show relevant entries
					xPly.setVisible(sel != PlayerFactory.Random);
					xPlyLabel.setVisible(sel != PlayerFactory.Random);
					xPly.setText("");
				}
			}
		});
		
		Label l3 = new Label ("O Player:");
		l3.setBounds (220, 175, 150, 20);
		add (l3);
		
		// Choices  
		oPlayChoice = new Choice();
		oPlayChoice.add(PlayerFactory.Random);
		oPlayChoice.add(PlayerFactory.AlphaBeta);
		oPlayChoice.add(PlayerFactory.MiniMax);
		oPlayChoice.add(PlayerFactory.NegMax);
		oPlayChoice.add(Human);
		oPlayChoice.setBounds (220, 200, 96, 21);
		add (oPlayChoice);		

		// default to random
		oPlayChoice.select(PlayerFactory.Random);
		this.add(getOPly(), null);
		this.add(oPlyLabel, null);
		oPly.setVisible(false);
		oPlyLabel.setVisible(false);
		
		oPlayChoice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String sel = oPlayChoice.getSelectedItem();
					
					// hide irrelevant entries
					oPly.setVisible(sel != PlayerFactory.Random);
					oPlyLabel.setVisible(sel != PlayerFactory.Random);
					oPly.setText("");
				}
			}
		});
		
		// Output information for status, scores, etc....
		output = new TextArea (6,30);
		output.setBounds (10, 230, 200, 120);
		add (output);
		
		// Take Action
		Button playButton = new Button ("New Game");
		playButton.setBounds (255, 280, 120, 20);
		add (playButton);
		
		// Tourname if all players are automatic
		tournamentButton = new Button ("Tournament");
		tournamentButton.setBounds (255, 320, 120, 20);
		add (tournamentButton);
		
		// Tell Play about the proper controller
		controller = new PlayGameController (this);
		playButton.addActionListener(controller);
		addMouseListener (controller);
		
		// play a tournament.
		tournamentButton.addActionListener(new TournamentController(this));		
		
		// set our size.
		setSize (500,400);
	}
	
	/**
	 * Update List to contain all game types.
	 * @param gameTypes
	 */
	private void populateGames(List gameTypes) {
		gameTypes.add("Annihilate");
		gameTypes.add("Neighbor");
		gameTypes.add("Slide");
		gameTypes.add("Straight");
		
		gameTypes.select(3);  // straight.
	}

	/**
	 * Given the name of a game type, return the appropriate
	 * controller to manage those game instances.
	 * 
	 * The controller returned is GameController of one of its
	 * subclasses.
	 * 
	 * @param    game   Game type selected by user.
	 * @return   new Logic object that represents the selected game type.
	 */
	public Logic getGameLogic(String game) {
		if (game == null) {
			return null;
		}
		
		if (game.equals ("Annihilate")) {
			return new AnnihilateLogic();
		}  else  if (game.equals ("Neighbor")) {
			return new NeighborLogic();
		} else if (game.equals ("Slide")) {
			return new SlideLogic();
		} else if (game.equals ("Straight")) {
			return new StraightLogic();
		}
		
		// nothing to say!
		return null;
	}	

	
	/**
	 * Every applet has a paint(g) method in which it can paint
	 * things.
	 * 
	 * We paint the current board state by appealing to the controller.
	 * 
	 * @param g   Graphics object into which to draw.
	 */
	public void paint (Graphics g) {
		if (controller != null) {
			controller.drawBoard(g);
		}
	}

	/**
	 * Returns the game type that was selected.
	 * 
	 * @return  String representing the current game type selected (or null of none).
	 */
	public String getSelectedGameType() {
		return gameTypes.getSelectedItem();
	}

	/**
	 * Returns the entity (Computer or Human) playing O.
	 * 
	 * @return  Choice for O player (or null if none selected).
	 */
	public String getOPlayChoice() {
		return oPlayChoice.getSelectedItem();
	}
	
	/**
	 * Returns the entity (Computer or Human) playing X.
	 * 
	 * @return  Choice for X player (or null if none selected).
	 */
	public String getXPlayChoice() {
		return xPlayChoice.getSelectedItem();
	}

	/**
	 * Appends output to the output text area.
	 * 
	 * @param string    string to be appended to the text area.
	 */
	public void output(String string) {
		output.append(string + "\n");		
	}

	/**
	 * This method initializes xPly	
	 * 	
	 */
	public TextField getXPly() {
		if (xPly == null) {
			xPly = new TextField();
			xPly.setBounds(new Rectangle(368, 152, 25, 20));
		}
		return xPly;
	}

	/**
	 * This method initializes oPly	
	 * 	
	 */
	public TextField getOPly() {
		if (oPly == null) {
			oPly = new TextField();
			oPly.setBounds(new Rectangle(369, 203, 25, 20));
		}
		return oPly;
	}
}
