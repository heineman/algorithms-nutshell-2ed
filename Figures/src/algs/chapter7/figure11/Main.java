package algs.chapter7.figure11;

import algs.model.gametree.IEvaluation;
import algs.model.problems.tictactoe.debug.TicTacToeDebugger;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

/** 
 * Produce AlphaBeta Tree for sample board Figure 7-1
 * 
 *  xo.
 *  ...
 *  ...
 * 
 */
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
	    new PlaceMark(0,0, (Player) xPlayer).execute(state);
	    new PlaceMark(1,0, (Player) oPlayer).execute(state);

	    // look as far ahead as needed
	    //algs.model.gametree.AlphaBetaEvaluation me = new algs.model.gametree.AlphaBetaEvaluation(7);
	    //algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(3);
	    System.out.println(board);
		for (int ply = 2; ply <= 8; ply++) {
			IEvaluation me = new algs.model.gametree.MinimaxEvaluation(ply);
			me.bestMove (state, xPlayer, oPlayer);
			int exploredMm = ((algs.model.gametree.MinimaxEvaluation)me).numStates;
			
			me = new algs.model.gametree.AlphaBetaEvaluation(ply);
			me.bestMove (state, xPlayer, oPlayer);
			int exploredAb = ((algs.model.gametree.AlphaBetaEvaluation)me).numStates;
			
			System.out.println(ply + "\t" + exploredMm + "\t" + exploredAb);
		}
		
		// full lookahead
	    algs.model.gametree.debug.AlphaBetaEvaluation me = new algs.model.gametree.debug.AlphaBetaEvaluation(7);
		TicTacToeDebugger std = new TicTacToeDebugger();
		me.debug(std);
		me.bestMove (state, xPlayer, oPlayer);
		System.out.println (std.getInputString());
		
		board = new TicTacToeBoard();
	    state = new TicTacToeState(board, logic);
	    
	    // pre-initialize board to 1.0 moves
	    new PlaceMark(2,2, (Player) xPlayer).execute(state);
	    new PlaceMark(1,2, (Player) oPlayer).execute(state);
	    System.out.println(board);

	    // look as far ahead as needed
	    //algs.model.gametree.AlphaBetaEvaluation me = new algs.model.gametree.AlphaBetaEvaluation(7);
	    //algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(3);
	   
		for (int ply = 2; ply <= 8; ply++) {
			IEvaluation eval = new algs.model.gametree.MinimaxEvaluation(ply);
			eval.bestMove (state, xPlayer, oPlayer);
			int exploredMm = ((algs.model.gametree.MinimaxEvaluation)eval).numStates;
			
			eval = new algs.model.gametree.AlphaBetaEvaluation(ply);
			eval.bestMove (state, xPlayer, oPlayer);
			int exploredAb = ((algs.model.gametree.AlphaBetaEvaluation)eval).numStates;
			
			System.out.println(ply + "\t" + exploredMm + "\t" + exploredAb);
		}
		
	}
}