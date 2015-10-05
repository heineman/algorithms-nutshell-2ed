package algs.model.tests.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;

// not many tests in common across all Logic classes...
public class LogicTest {

	@Test
	public void testMaxNumberMoves() {
		Logic l = new StraightLogic();
		assertEquals (9, l.maxNumberMoves());
	}

	// just make sure player can evaluate a position to 0 if it has no score function.
	@Test
	public void testPlayer() {
		Player p = new RandomPlayer(Player.XMARK);
		p.score(null);
		assertEquals (0, p.eval(null));
	}
	
	
}
