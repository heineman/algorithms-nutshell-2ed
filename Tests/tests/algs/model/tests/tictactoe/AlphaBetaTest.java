package algs.model.tests.tictactoe;

import org.junit.Test;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class AlphaBetaTest extends TestCase {

	@Test
	public void testAlphaBetaNoMove() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to all full
	    new PlaceMark(0,0, (Player) xPlayer).execute(state);
	    new PlaceMark(0,1, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);
	    new PlaceMark(1,0, (Player) oPlayer).execute(state);
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(1,2, (Player) xPlayer).execute(state);
	    new PlaceMark(2,0, (Player) oPlayer).execute(state);
	    new PlaceMark(2,1, (Player) xPlayer).execute(state);
	    new PlaceMark(2,2, (Player) oPlayer).execute(state);
	    
	    // two ply lookahead.
	    algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(2);
		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		assertTrue (move == null);
	}

	
	@Test
	public void testAlphaBetaNonDebug() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(2);
		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// we really know that this move is a placemark
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
	}
	
	@Test
	public void testAlphaBeta() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.AlphaBetaEvaluation ae =
	    	new algs.model.gametree.AlphaBetaEvaluation(2);
		
		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// we really know that this move is a placemark
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
		
		// just to get coverage on the toString Method
		ae.toString();
	}
}
