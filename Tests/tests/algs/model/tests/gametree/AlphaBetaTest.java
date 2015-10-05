package algs.model.tests.gametree;

import org.junit.Test;

import algs.debug.DottyDebugger;
import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.IntelligentAgent;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class AlphaBetaTest extends TestCase {

	@Test
	public void testAlphaBeta() {
		for (int lookahead = 1; lookahead <= 2; lookahead++) {
			// create the TicTacToe game. Only instantiate the proper class
			// that you want to play.
			StraightLogic logic = new StraightLogic();

			// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
			Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, lookahead);
			xPlayer.logic(logic);
			xPlayer.score(new BoardEvaluation());

			// 2-move lookahead, for O.
			Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, lookahead);
			oPlayer.logic(logic);
			oPlayer.score(new BoardEvaluation());

			TicTacToeBoard board = new TicTacToeBoard();
			TicTacToeState state = new TicTacToeState(board, logic);

			// pre-initialize board to 1.5 moves
			new PlaceMark(0,2, (Player) xPlayer).execute(state);
			new PlaceMark(2,0, (Player) oPlayer).execute(state);
			new PlaceMark(1,2, (Player) xPlayer).execute(state);

			algs.model.gametree.AlphaBetaEvaluation ae = 
				new algs.model.gametree.AlphaBetaEvaluation(lookahead);

			IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
			System.out.println ("best move:" + move);

			// we really know that this move is a placemark: Suitable response for 1-lookahead
			///   1-lookahead [0,1]
			///   2-lookahead [2,0]
			switch (lookahead) {
			case 1:
				// can't see that X is going to win the NEXT MOVE! only sees how to 
				// maximize its chances of winning.
				assertEquals (1, ((PlaceMark)move).getColumn());
				assertEquals (1, ((PlaceMark)move).getRow());
				break;
			case 2:
				// Now sees it! So block it
				assertEquals (2, ((PlaceMark)move).getColumn());
				assertEquals (2, ((PlaceMark)move).getRow());
				break;
			}
		}
	}

	// need to consider three move look ahead to be able to force debug to enter logic for 
	// alpha/beta pruning
	@Test
	public void testAlphaBetaDebug() {
		for (int lookahead = 1; lookahead <= 4; lookahead++) {
			// create the TicTacToe game. Only instantiate the proper class
			// that you want to play.
			StraightLogic logic = new StraightLogic();

			// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
			Player xPlayer = new IntelligentAgent (Player.XMARK, new algs.model.gametree.debug.AlphaBetaEvaluation(lookahead)); 
			xPlayer.logic(logic);
			xPlayer.score(new BoardEvaluation());

			// 2-move lookahead, for O.
			Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, lookahead);
			oPlayer.logic(logic);
			oPlayer.score(new BoardEvaluation());

			TicTacToeBoard board = new TicTacToeBoard();
			TicTacToeState state = new TicTacToeState(board, logic);

			// pre-initialize board to 1 moves
			new PlaceMark(0,2, (Player) xPlayer).execute(state);
			new PlaceMark(2,0, (Player) oPlayer).execute(state);

			algs.model.gametree.debug.AlphaBetaEvaluation ae = 
				new algs.model.gametree.debug.AlphaBetaEvaluation(lookahead);
			DottyDebugger dd = new DottyDebugger();
			ae.debug(dd);

			// someone needs to invoke the complete method to show it does nothing.
			dd.complete();
			
			
			IGameMove move = ae.bestMove (state, xPlayer, oPlayer);
			System.out.println ("best move:" + move);

			// we really know that this move is a placemark: Suitable response for 1-lookahead
			///   1-lookahead [0,1]
			///   2-lookahead [2,0]
			switch (lookahead) {
			case 1:
				// can't see that X is going to win the NEXT MOVE! only sees how to 
				// maximize its chances of winning.
				assertEquals (1, ((PlaceMark)move).getColumn());
				assertEquals (1, ((PlaceMark)move).getRow());
				break;
			case 2:
				// Still nothing effective over first move
				assertEquals (1, ((PlaceMark)move).getColumn());
				assertEquals (1, ((PlaceMark)move).getRow());
				break;
			case 3:
				// Now sees it! So block it
				assertEquals (0, ((PlaceMark)move).getColumn());
				assertEquals (0, ((PlaceMark)move).getRow());
				break;

			case 4:
				// Now sees it! So block it
				assertEquals (0, ((PlaceMark)move).getColumn());
				assertEquals (0, ((PlaceMark)move).getRow());
				break;
			}
		}
	}

}
