package algs.model.tests.gametree;

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

public class AlphaBetaDebugTest extends TestCase {

	@Test
	public void testAlphaBeta() {
		for (int lookahead = 0; lookahead < 3; lookahead++) {
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
		    
		    // pre-initialize board to 1.5 moves
		    new PlaceMark(0,2, (Player) xPlayer).execute(state);
		    new PlaceMark(2,0, (Player) oPlayer).execute(state);
		    new PlaceMark(1,2, (Player) xPlayer).execute(state);
		     
		    algs.model.gametree.debug.AlphaBetaEvaluation ae = 
		    	new algs.model.gametree.debug.AlphaBetaEvaluation(lookahead);
		    TicTacToeDebugger std = new TicTacToeDebugger();
			ae.debug(std);
			
			IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
			System.out.println ("best move:" + move);
			System.out.println (std.getInputString());
			
			// we really know that this move is a placemark: Suitable response for 1-lookahead
			///   1-lookahead [0,1]
			///   2-lookahead [2,0]
			switch (lookahead) {
			case 0:
				// can't see that X is going to win the NEXT MOVE! only sees how to 
				// maximize its chances of winning.
				assertEquals (1, ((PlaceMark)move).getColumn());
				assertEquals (1, ((PlaceMark)move).getRow());
				break;
			case 1:
				// Still can't see it
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
	
}
