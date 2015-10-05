package algs.model.tests.gametree;

import org.junit.Test;

import algs.model.gametree.IComparator;
import algs.model.gametree.IGameMove;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.PlayerFactory;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

public class MiniMaxTest extends TestCase {
	
	@Test
	public void testComparator() {
		assertEquals ("MAX", IComparator.MAX.toString());
		assertEquals ("MIN", IComparator.MIN.toString());
		
		assertEquals (+1, IComparator.MAX.compare(5, 3));
		assertEquals (-1, IComparator.MAX.compare(3, 5));
		assertEquals (0, IComparator.MAX.compare(5, 5));
		
		// weird cases
		assertEquals (+1, IComparator.MAX.compare(5, null));
		assertEquals (-1, IComparator.MAX.compare(null, 5));
		assertEquals (0, IComparator.MAX.compare(null, null));
		
		assertEquals (-1, IComparator.MIN.compare(5, null));
		assertEquals (+1, IComparator.MIN.compare(null, 5));
		assertEquals (0, IComparator.MIN.compare(null, null));
		
	}
	
	@Test
	public void testNoMove() {
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
	    
	    // pre-initialize board to be full
	    new PlaceMark(0,0, (Player) xPlayer).execute(state);
	    new PlaceMark(0,1, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);
	    new PlaceMark(1,0, (Player) oPlayer).execute(state);
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(1,2, (Player) xPlayer).execute(state);
	    new PlaceMark(2,0, (Player) oPlayer).execute(state);
	    new PlaceMark(2,1, (Player) xPlayer).execute(state);
	    new PlaceMark(2,2, (Player) oPlayer).execute(state);
	    
	    // look ahead so far that moves will run out.
	    algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(5);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		assertTrue (move == null);
	}
	
	@Test
	public void testEndGame() {
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
	    
	    // pre-initialize board to nearly be done
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);
	    new PlaceMark(2,0, (Player) oPlayer).execute(state);
	    new PlaceMark(1,0, (Player) xPlayer).execute(state);

	    // look ahead so far that moves will run out.
	    algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(5);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// Only looking ahead, this is the best it can come up with.
		assertEquals (1, ((PlaceMark)move).getColumn());
		assertEquals (2, ((PlaceMark)move).getRow());
	}
	
	@Test
	public void testMinimaxDebug() {
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
	    
	     // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.MinimaxEvaluation me =
	    	new algs.model.gametree.MinimaxEvaluation(2);

	    IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// we really know that this move is a placemark
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
	}
	
	@Test
	public void testMinimaxOnePly() {
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
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // one ply lookahead.
	    algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(1);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// Only looking ahead, this is the best it can come up with.
		assertEquals (1, ((PlaceMark)move).getColumn());
		assertEquals (2, ((PlaceMark)move).getRow());
		
		// for closure on toString methods
		me.toString();
	}
	
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
	    
	    // pre-initialize board to 2.5 moves
	    new PlaceMark(1,1, (Player) xPlayer).execute(state);
	    new PlaceMark(0,0, (Player) oPlayer).execute(state);
	    new PlaceMark(0,2, (Player) xPlayer).execute(state);

	    // two ply lookahead.
	    algs.model.gametree.MinimaxEvaluation me = new algs.model.gametree.MinimaxEvaluation(2);
		IGameMove move = me.bestMove (state, oPlayer, xPlayer);
		System.out.println ("best move:" + move);
		
		// Only looking ahead, this is the best it can come up with.
		assertEquals (2, ((PlaceMark)move).getColumn());
		assertEquals (0, ((PlaceMark)move).getRow());
		
		// for closure on toString methods
		me.toString();
	}
}
