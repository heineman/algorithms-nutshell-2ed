package algs.model.tests.tictactoe;

import org.junit.Test;

import algs.model.gametree.AlphaBetaEvaluation;
import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class PruneTest extends TestCase {
	TicTacToeState state;
	Player  oPlayer;
	Player  xPlayer;
	
	public void setUp() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 1.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(2,1, (Player) xPlayer).execute(state);
	}
	
	@Test
	public void testAlphaBetaOne() {
	    // one ply lookahead.
	    AlphaBetaEvaluation ae = new AlphaBetaEvaluation(1);
		
		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// we really know that this move is a placemark
		assertEquals (0, ((PlaceMark)move).getColumn());
		assertEquals (2, ((PlaceMark)move).getRow());
	}
	
}
