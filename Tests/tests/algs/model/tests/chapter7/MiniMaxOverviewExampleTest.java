package algs.model.tests.chapter7;

import org.junit.Test;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class MiniMaxOverviewExampleTest extends TestCase {

	
	@Test
	public void testMinimaxTwoPly() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
	    Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.XMARK, 2);
	    xPlayer.logic(logic);
	    xPlayer.score(new BoardEvaluation());
	    
	    // 2-move lookahead, for O.
	    Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.MiniMax, Player.OMARK, 2);
	    oPlayer.logic(logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    TicTacToeBoard board = new TicTacToeBoard();
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
    // pre-initialize board to 1.0 moves
	    new PlaceMark(1,0, (Player) xPlayer).execute(state);
	    new PlaceMark(1,1, (Player) oPlayer).execute(state);
	    new PlaceMark(0,1, (Player) xPlayer).execute(state);

	     // two ply lookahead.
	    algs.model.gametree.debug.MinimaxEvaluation me = new algs.model.gametree.debug.MinimaxEvaluation(2);
		TicTacToeDebugger std = new TicTacToeDebugger();
		me.debug(std);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		System.out.println (std.getInputString());
		
		// Only looking ahead, it fails to select (0,0) as best
		assertTrue (((PlaceMark)move).getColumn()!=0 && 
				((PlaceMark)move).getRow() != 0);
	}
}
