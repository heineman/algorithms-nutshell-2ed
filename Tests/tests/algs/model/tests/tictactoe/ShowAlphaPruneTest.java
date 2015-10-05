package algs.model.tests.tictactoe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import algs.model.gametree.AlphaBetaEvaluation;
import algs.model.gametree.IEvaluation;
import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;
import algs.model.problems.tictactoe.model.BoardEvaluation;
import algs.model.problems.tictactoe.model.IntelligentAgent;
import algs.model.problems.tictactoe.model.Logic;
import algs.model.problems.tictactoe.model.PlaceMark;
import algs.model.problems.tictactoe.model.Player;
import algs.model.problems.tictactoe.model.StraightLogic;
import algs.model.problems.tictactoe.model.TicTacToeBoard;
import algs.model.problems.tictactoe.model.TicTacToeState;

import junit.framework.TestCase;

/**
 * Override intelligent behavior to simulate specific board configurations.
 * 
 * @author George Heineman
 */
class SpecializedXPlayer extends IntelligentAgent {

	// fixed algorithm (for testing) that returns null if no moves are available or the first
	// one of the valid moves.
	static IEvaluation alg = new IEvaluation() {

		@Override
		public IGameMove bestMove(IGameState state, IPlayer player,
				IPlayer opponent) {
			// by default, choose the first one
			Collection<IGameMove> moves = player.validMoves(state);
			if (moves == null) { return null; }
			if (moves.size() == 0) { return null; }

			return moves.iterator().next();
		}

	};

	public SpecializedXPlayer(char mark, Logic logic) {
		super(mark, alg);
		logic(logic);
	}

	// override valid moves.
	public Collection<IGameMove> validMoves(IGameState state) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		ArrayList<IGameMove> al = new ArrayList<IGameMove>();
		if (board.equals(ShowAlphaPruneTest.boards[1])) {
			al.add(new PlaceMark(1, 0, this));
			al.add(new PlaceMark(2, 0, this));
		} else if (board.equals (ShowAlphaPruneTest.boards[4])) {
			al.add(new PlaceMark(0, 1, this));
			al.add(new PlaceMark(1, 0, this));
		} 

		return al;
	}

}

/**
 * Programmed O for specifics
 * 
 */
class SpecializedOPlayer extends IntelligentAgent {

	// fixed algorithm (for testing) that returns null if no moves are available or the last
	// one of the valid moves.
	static IEvaluation alg = new IEvaluation() {

		@Override
		public IGameMove bestMove(IGameState state, IPlayer player,
				IPlayer opponent) {
			// by default, choose the first one
			Collection<IGameMove> moves = player.validMoves(state);
			if (moves == null) { return null; }
			if (moves.size() == 0) { return null; }

			IGameMove ret = null;
			Iterator<IGameMove> it = moves.iterator();
			while (it.hasNext()) {
				ret = it.next();
			}

			return ret;
		}

	};

	public SpecializedOPlayer(char mark, Logic logic) {
		super(mark, alg);
		logic(logic);
	}

	// override valid moves.
	public Collection<IGameMove> validMoves(IGameState state) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		ArrayList<IGameMove> al = new ArrayList<IGameMove>();
		if (board.equals(ShowAlphaPruneTest.boards[0])) {
			al.add(new PlaceMark(0, 1, this));
			al.add(new PlaceMark(2, 0, this));
			al.add(new PlaceMark(2, 2, this));
		} else if (board.equals (ShowAlphaPruneTest.boards[7])) {
			al.add(new PlaceMark(0, 1, this));
		}

		return al;
	}
}


/**
 * Choose any open spot. 
 * 
 * @author George Heineman
 */
class InclusivePlayer extends IntelligentAgent {

	// fixed algorithm (for testing) that returns null if no moves are available or the first
	// one of the valid moves.
	static IEvaluation alg = new IEvaluation() {

		@Override
		public IGameMove bestMove(IGameState state, IPlayer player,
				IPlayer opponent) {
			// by default, choose the first one
			Collection<IGameMove> moves = player.validMoves(state);
			if (moves == null) { return null; }
			if (moves.size() == 0) { return null; }

			return moves.iterator().next();
		}

	};

	public InclusivePlayer(char mark) {
		super(mark, alg);
	}

	// override valid moves.
	public Collection<IGameMove> validMoves(IGameState state) {
		TicTacToeState tttState = (TicTacToeState) state;
		TicTacToeBoard board = tttState.board();
		ArrayList<IGameMove> al = new ArrayList<IGameMove>();
		for (int c = 0; c < 3; c++) {
			for (int r = 0; r < 3; r++) {
				if (board.isClear(c, r)) {
					al.add(new PlaceMark (c, r, this));
				}
			}
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
public class ShowAlphaPruneTest extends TestCase {
	static char o = 'O';
	static char x = 'X';
	static char _ = ' ';

	public static TicTacToeBoard boards[] = new TicTacToeBoard[] {
		new TicTacToeBoard(new char[][]{{o,_,x},{_,x,_},{_,_,_}}),

		new TicTacToeBoard(new char[][]{{o,o,x},{_,x,_},{_,_,_}}),
		new TicTacToeBoard(new char[][]{{o,o,x},{x,x,_},{_,_,_}}),
		new TicTacToeBoard(new char[][]{{o,o,x},{_,x,_},{x,_,_}}),

		new TicTacToeBoard(new char[][]{{o,_,x},{_,x,_},{o,_,_}}),
		new TicTacToeBoard(new char[][]{{o,x,x},{_,x,_},{o,_,_}}),
		new TicTacToeBoard(new char[][]{{o,_,x},{x,x,_},{o,_,_}}),

		new TicTacToeBoard(new char[][]{{o,_,x},{_,x,_},{_,_,o}}),
		new TicTacToeBoard(new char[][]{{o,x,x},{_,x,_},{_,_,o}}),
	};


	@Test
	public void testAlphaBeta() {
		// create the TicTacToe game. Only instantiate the proper class
		// that you want to play.
		StraightLogic logic = new StraightLogic();

		SpecializedXPlayer xPlayer = new SpecializedXPlayer(x, logic);
		xPlayer.score(new BoardEvaluation());

		SpecializedOPlayer oPlayer = new SpecializedOPlayer (o, logic);
		oPlayer.score(new BoardEvaluation());

		xPlayer.opponent(oPlayer);
		oPlayer.opponent(xPlayer);

		// start at this initial state.
		TicTacToeBoard board = new TicTacToeBoard(boards[0]);
		TicTacToeState state = new TicTacToeState(board, logic);

		// two ply lookahead.
		AlphaBetaEvaluation ae = new AlphaBetaEvaluation(2);

		IGameMove move = ae.bestMove (state, oPlayer, xPlayer);
		assertEquals ("[Place O @ (2,2)]", move.toString());

		IGameMove move2 = oPlayer.decideMove(state);
		assertEquals (move, move2);
	}

	@Test
	public void testValidateIntelligentChoiceWin() {
		// create the TicTacToe game. Only instantiate the proper class
		// that you want to play.
		StraightLogic logic = new StraightLogic();

		InclusivePlayer xPlayer = new InclusivePlayer(x);
		xPlayer.score(new BoardEvaluation());

		InclusivePlayer oPlayer = new InclusivePlayer (o);
		oPlayer.score(new BoardEvaluation());

		// start at this initial state.
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// pre-initialize board to 3.5 moves
		new PlaceMark(1,1, (Player) xPlayer).execute(state);
		new PlaceMark(0,0, (Player) oPlayer).execute(state);
		new PlaceMark(0,2, (Player) xPlayer).execute(state);
		new PlaceMark(2,0, (Player) oPlayer).execute(state);
		new PlaceMark(1,2, (Player) xPlayer).execute(state);
		new PlaceMark(1,0, (Player) oPlayer).execute(state);// o WINS
		assertTrue (null == oPlayer.decideMove(state));
	}

	@Test
	public void testValidateIntelligentChoicDraw() {
		// create the TicTacToe game. Only instantiate the proper class
		// that you want to play.
		StraightLogic logic = new StraightLogic();

		InclusivePlayer xPlayer = new InclusivePlayer(x);
		xPlayer.score(new BoardEvaluation());

		InclusivePlayer oPlayer = new InclusivePlayer (o);
		oPlayer.score(new BoardEvaluation());

		// start at this initial state.
		TicTacToeBoard board = new TicTacToeBoard();
		TicTacToeState state = new TicTacToeState(board, logic);

		// each have valid moves.
		assertTrue (null != xPlayer.decideMove(state));
		assertTrue (null != oPlayer.decideMove(state));

		// pre-initialize board to 3.5 moves
		new PlaceMark(1,1, (Player) xPlayer).execute(state);
		new PlaceMark(0,0, (Player) oPlayer).execute(state);
		new PlaceMark(0,2, (Player) xPlayer).execute(state);
		new PlaceMark(2,0, (Player) oPlayer).execute(state);
		new PlaceMark(1,0, (Player) xPlayer).execute(state);
		new PlaceMark(1,2, (Player) oPlayer).execute(state);
		new PlaceMark(2,2, (Player) xPlayer).execute(state);

		new PlaceMark(2,1, (Player) oPlayer).execute(state);  
		new PlaceMark(0,1, (Player) xPlayer).execute(state);

		assertTrue (null == xPlayer.decideMove(state));
	}
}
