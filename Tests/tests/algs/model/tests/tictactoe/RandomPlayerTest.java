package algs.model.tests.tictactoe;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import algs.model.gametree.IGameMove;
import algs.model.gametree.IGameState;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.Move;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.RandomPlayer;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

public class RandomPlayerTest {

	@Test
	public void testFactory() {
		// X is the random player
		Player p = PlayerFactory.createPlayer("garbage", Player.XMARK);
		assertTrue (p == null);
		
		try {
			PlayerFactory.createPlayerWithPly("garbage", Player.XMARK, 6);
			fail ("Should detect invalid type");
		} catch (IllegalArgumentException iae) {
			
		}
		
	}
	
	@Test
	public void testRandomPlayerWithFaultyLogic() {
		// X is the random player
		Player xPlayer = PlayerFactory.createPlayer(PlayerFactory.Random, Player.XMARK);
		Logic faulty = new Logic() {

			// doesn't like any moves
			@Override
			public Move interpretMove(IGameState board, int col, int row,
					Player player) {
				return null;
			}

			@Override
			public Logic copy() {
				return this;
			}

			@Override
			public String rules() {
				return "Doesn't like any moves. Faulty Logic.";
			}
			
		};

		xPlayer.logic(faulty);
		xPlayer.score(new BoardEvaluation());
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, faulty);

		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		oPlayer.logic(new StraightLogic());
		oPlayer.score(new BoardEvaluation());
		assertEquals (Player.XMARK, oPlayer.getOpponentMark());
		
		// validate that we can handle faulty logic in framework.
		IGameMove m = xPlayer.decideMove(state);
		assertTrue (null == m);

	}
	
	@Test
	public void testRandomPlayerAndWithWin() {
		// X is the random player
		Player xPlayer = PlayerFactory.createPlayer(PlayerFactory.Random, Player.XMARK);
		StraightLogic logic = new StraightLogic();

		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		oPlayer.logic(logic);
		oPlayer.score(new BoardEvaluation());
		assertEquals (Player.XMARK, oPlayer.getOpponentMark());
		
		// pre-initialize board to 2.5 moves
		new PlaceMark(1,1, (Player) xPlayer).execute(state);
		new PlaceMark(0,0, (Player) oPlayer).execute(state);
		new PlaceMark(0,2, (Player) xPlayer).execute(state);

		// validate that random move is available.
		Collection<IGameMove> validOnes = xPlayer.validMoves(state);

		// try ten random ones...
		for (int i = 0; i < 10; i++) {
			IGameMove move = xPlayer.decideMove(state);
			assertTrue (validOnes.contains(move));
		}

		// make some additional moves
		new PlaceMark(2,0, (Player) oPlayer).execute(state);
		new PlaceMark(1,0, (Player) xPlayer).execute(state);
		new PlaceMark(1,2, (Player) oPlayer).execute(state);
		new PlaceMark(2,1, (Player) xPlayer).execute(state);
		new PlaceMark(2,2, (Player) oPlayer).execute(state);
		new PlaceMark(0,1, (Player) xPlayer).execute(state);  // wins the game

		// validate that no moves are available.
		Collection<IGameMove> moves2 = xPlayer.validMoves(state);
		assertTrue (moves2.isEmpty());
		assertTrue (xPlayer.decideMove(state) == null);
	}

	@Test
	public void testRandomPlayerDraw() {
		// X is the random player
		RandomPlayer xPlayer = new RandomPlayer(Player.XMARK);
		StraightLogic logic = new StraightLogic();

		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		oPlayer.logic(logic);
		oPlayer.score(new BoardEvaluation());

		// pre-initialize board to 2.5 moves
		new PlaceMark(1,1, (Player) xPlayer).execute(state);
		new PlaceMark(0,0, (Player) oPlayer).execute(state);
		new PlaceMark(0,2, (Player) xPlayer).execute(state);
		new PlaceMark(2,0, (Player) oPlayer).execute(state);
		new PlaceMark(1,0, (Player) xPlayer).execute(state);
		new PlaceMark(1,2, (Player) oPlayer).execute(state);
		new PlaceMark(2,1, (Player) xPlayer).execute(state);
		new PlaceMark(0,1, (Player) oPlayer).execute(state);
		new PlaceMark(2,2, (Player) xPlayer).execute(state);  // draws the game

		// validate that no moves are available.
		Collection<IGameMove> moves2 = xPlayer.validMoves(state);
		assertTrue (moves2.isEmpty());
		assertTrue (xPlayer.decideMove(state) == null);
	}

	@Test
	public void testHashes() {
		RandomPlayer xPlayer = new RandomPlayer(Player.XMARK);
		StraightLogic logic = new StraightLogic();

		xPlayer.logic(logic);
		assertTrue (logic == xPlayer.logic());
		xPlayer.score(new BoardEvaluation());
			
		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
		oPlayer.logic(logic);
		BoardEvaluation be = new BoardEvaluation();
		oPlayer.score(be);
		assertTrue (be == oPlayer.score());
		
		// while at it, validate hashing (that does something!)
		assertEquals (oPlayer.hashCode(), oPlayer.hashCode());

		PlaceMark pm = new PlaceMark(1,1, (Player) xPlayer);
		PlaceMark pm2 = new PlaceMark(1,1, (Player) xPlayer);
		assertEquals (pm.hashCode(), pm2.hashCode());
	}

	@Test
	public void testMarks() {
		try {
			new RandomPlayer(Player.EMPTY);
			fail ("shouldn't allow EMPTY as mark");
		} catch (Exception e) {
			
		}
		
		try {
			new RandomPlayer('c');
			fail ("shouldn't allow 'c' as mark");
		} catch (Exception e) {
			
		}
		
		StraightLogic logic = new StraightLogic();

		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		Player xPlayer = new RandomPlayer(Player.XMARK);
		xPlayer.logic(logic);
		assertEquals (Player.OMARK, xPlayer.getOpponentMark());
		
		// default logic assigns 0 as score without any further enhancing.
		assertEquals (0, xPlayer.score().score(state, xPlayer));
		assertEquals (0, xPlayer.eval(state));
		
	}
	
}
