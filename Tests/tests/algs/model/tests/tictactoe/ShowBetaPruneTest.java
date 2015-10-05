package algs.model.tests.tictactoe;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import algs.model.gametree.AlphaBetaEvaluation;
import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.IntelligentAgent;
import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

/**
 * Override intelligent behavior to simulate specific board configurations.
 * 
 * @author George Heineman
 */
class SpecializedXBPlayer extends IntelligentAgent {

	public SpecializedXBPlayer(char mark, Logic logic) {
		super(mark, null);
		logic(logic);
	}
	
	// override valid moves.
	public Collection<IGameMove> validMoves(IGameState state) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		ArrayList<IGameMove> al = new ArrayList<IGameMove>();
		if (board.equals(ShowBetaPruneTest.boards[0])) {
			al.add(new PlaceMark(0, 0, this));
			al.add(new PlaceMark(1, 0, this));
		} else if (board.equals (ShowBetaPruneTest.boards[2])) {
			al.add(new PlaceMark(0, 1, this));
			al.add(new PlaceMark(2, 2, this));
		} else if (board.equals (ShowBetaPruneTest.boards[3])) {
			al.add(new PlaceMark(1, 1, this));
			al.add(new PlaceMark(1, 0, this));
		}		
		return al;
	}
	
}

class SpecializedOBPlayer extends IntelligentAgent {

	public SpecializedOBPlayer(char mark, Logic logic) {
		super(mark, null);
		logic(logic);
	}
	
	// override valid moves.
	public Collection<IGameMove> validMoves(IGameState state) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		ArrayList<IGameMove> al = new ArrayList<IGameMove>();
		if (board.equals(ShowBetaPruneTest.boards[1])) {
			al.add(new PlaceMark(1, 1, this));
			al.add(new PlaceMark(1, 2, this));
		} 
		return al;
	}
	
}

/** 
 * Specific Worked example, with reduced move sets to showcase just what I want 
 * to show.
 * 
 * @author George
 *
 */
public class ShowBetaPruneTest extends TestCase {
	static char o = 'O';
	static char x = 'X';
	static char _ = ' ';
	
	// vertical ordering.
	public static TicTacToeBoard boards[] = new TicTacToeBoard[] {
			new TicTacToeBoard(new char[][]{{_,_,_},{_,_,_},{_,_,_}}),
			
			new TicTacToeBoard(new char[][]{{x,_,_},{_,_,_},{_,_,_}}),
			new TicTacToeBoard(new char[][]{{x,_,_},{_,o,_},{_,_,_}}),
			new TicTacToeBoard(new char[][]{{x,_,_},{_,_,o},{_,_,_}}),
			
			new TicTacToeBoard(new char[][]{{x,_,_},{_,x,o},{_,_,_}}),
			new TicTacToeBoard(new char[][]{{x,_,_},{x,_,o},{_,_,_}}),
			
			new TicTacToeBoard(new char[][]{{x,x,_},{_,o,_},{_,_,_}}),
			new TicTacToeBoard(new char[][]{{x,_,_},{_,o,_},{_,_,x}}),
			
	};

	
	@Test
	public void testAlphaBeta() {
		// create the TicTacToe game. Only instantiate the proper class
	    // that you want to play.
		StraightLogic logic = new StraightLogic();
		
		IPlayer xPlayer = new SpecializedXBPlayer(x, logic);
		xPlayer.score(new BoardEvaluation());
		
		IPlayer oPlayer = new SpecializedOBPlayer (o, logic);
	    oPlayer.score(new BoardEvaluation());
	    
	    ((SpecializedXBPlayer) xPlayer).opponent(oPlayer);
	    ((SpecializedOBPlayer) oPlayer).opponent(xPlayer);

	    // start at this initial state.
	    TicTacToeBoard board = new TicTacToeBoard(boards[0]);
	    TicTacToeState state = new TicTacToeState(board, logic);
	    
	    // two ply lookahead.
	    AlphaBetaEvaluation ae = new AlphaBetaEvaluation(2);
		
		IGameMove move = ae.bestMove (state, xPlayer, oPlayer);
		System.out.println ("best move:" + move);
	}
}
