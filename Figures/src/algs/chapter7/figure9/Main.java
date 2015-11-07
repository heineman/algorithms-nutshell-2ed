package algs.chapter7.figure9;

import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

public class Main {

	public static void main(String[] args) {
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
	    
	    // pre-initialize board to 1.0 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(2,0, (Player) oPlayer).execute(state);
	    new PlaceMark(2,2, (Player) xPlayer).execute(state);

	     // two ply lookahead.
	    algs.model.gametree.debug.AlphaBetaEvaluation me = new algs.model.gametree.debug.AlphaBetaEvaluation(2);
		TicTacToeDebugger std = new TicTacToeDebugger();
		me.debug(std);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		
		System.out.println (std.getInputString());
		
		// send to stderr
		System.err.println ("best move:" + move);
		
		// Only looking ahead, this is the best it can come up with.
		assert (0 == ((PlaceMark)move).getColumn());
		assert (0 == ((PlaceMark)move).getRow());
	}
}
