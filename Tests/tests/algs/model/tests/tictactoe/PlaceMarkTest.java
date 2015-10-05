package algs.model.tests.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.problems.tictactoe.model.Move;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

public class PlaceMarkTest {

	@Test
	public void testStraightLogic() {
		StraightLogic logic = new StraightLogic();
		
		// basic belief.
		assertTrue (logic.toString() != null);
		assertTrue (logic.rules() != null);
	}
	
	@Test
	public void testBasics() {
		Player p = new RandomPlayer(Player.XMARK);
		
		TicTacToeBoard ttb = new TicTacToeBoard();
		StraightLogic logic = new StraightLogic();
		TicTacToeState tts = new TicTacToeState(ttb, logic);
		
		// try each one in turn. 
		int[][] places = new int[][] {{1, 10}, {1, -1}, {-1, 2}, {10, 2}};
		for (int i = 0; i < places.length; i++) {
			PlaceMark pm = new PlaceMark(places[i][0], places[i][1], p);
			assertFalse(pm.isValid(tts));
			assertTrue (p == pm.getPlayer());
			
			assertFalse (pm.equals (null));
			assertFalse (pm.equals ("Garbage"));
			
			Move mv = logic.interpretMove(tts, places[i][0], places[i][1], p);
			assertTrue (mv == null);
		}
		
		// validate equals
		
		
	}

}
