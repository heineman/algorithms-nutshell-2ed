package algs.chapter7.table1;



import algs.model.problems.tictactoe.model.*;

/** 
 * Revised table eliminates stdev computation.
 * 
 *  
 * 
 */
public class Main {

	public static String reduced(int smaller, int original) {
		float f = 1.0f*(original - smaller)/original;
		int actual = Math.round(100*f);
		return actual + "%";				
	}
	
	public static void main(String[] args) {
		// create the TicTacToe game. Only instantiate the proper class
		// that you want to play.
		StraightLogic logic = new StraightLogic();

		// 2-move lookahead, using the BoardEvaluation function as described in Nilsson.
		Player xPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.XMARK, 6);
		xPlayer.logic(logic);
		xPlayer.score(new BoardEvaluation());

		// 2-move lookahead, for O.
		Player oPlayer = PlayerFactory.createPlayerWithPly(PlayerFactory.AlphaBeta, Player.OMARK, 6);
		oPlayer.logic(logic);
		oPlayer.score(new BoardEvaluation());

		///System.out.println("k\tMinimax states\tAlphaBeta states\tAgg. reduction");
		Cell[] cells = new Cell[9];
		int idx = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Cell c = new Cell (i,j);
				cells[idx++] = c;
			}
		}

		final int sz = idx;
		int i, j, k;

		System.out.println("Ply\tMinimax\tAlpBet\tAggRed.");

		// THREE MOVES
		int explored = 0, exploredMME = 0;
		for (i = 0; i < sz; i++) {
			for (j = 0; j < sz; j++) {
				if (j == i) continue;
				for (k = 0; k < sz; k++) {
					if (j == k) continue;
					if (i == k) continue;
					TicTacToeBoard board = new TicTacToeBoard();
					TicTacToeState state = new TicTacToeState(board, logic);

					new PlaceMark(cells[i].col,cells[i].row, (Player) xPlayer).execute(state);
					new PlaceMark(cells[j].col,cells[j].row, (Player) oPlayer).execute(state);
					new PlaceMark(cells[k].col,cells[k].row, (Player) xPlayer).execute(state);

					algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(6);
					/* IMove move = */ ae.bestMove(state, oPlayer, xPlayer);
					explored += ae.numStates;

					algs.model.gametree.MinimaxEvaluation mme = new algs.model.gametree.MinimaxEvaluation(6);
					/* IMove move2 = */ mme.bestMove(state, oPlayer, xPlayer);
					exploredMME += mme.numStates;


					// sometimes alpha beta returns NULL if there is no hope. For example, on
					// 
					//  XX.    the only defense is to play XXO  but then  XXO  means X wins
					//  O..                                O..            OX.     on next turn
					//  ...                                ...            ...
					//
					// alpha-beta detects this unfavorable situation and gives up. There is
					// nothing that can be done.

				}
			}
		}
		System.out.println ("6\t" + exploredMME + "\t" + explored + "\t" + reduced(explored, exploredMME));


		// TWO MOVE
		explored = exploredMME = 0;
		for (i = 0; i < sz; i++) {
			for (j = 0; j < sz; j++) {
				if (j == i) continue;

				TicTacToeBoard board = new TicTacToeBoard();
				TicTacToeState state = new TicTacToeState(board, logic);

				new PlaceMark(cells[i].col,cells[i].row, (Player) xPlayer).execute(state);
				new PlaceMark(cells[j].col,cells[j].row, (Player) oPlayer).execute(state);

				algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(7);
				/* IMove move = */ ae.bestMove(state, xPlayer, oPlayer);
				explored += ae.numStates;

				algs.model.gametree.MinimaxEvaluation mme = new algs.model.gametree.MinimaxEvaluation(7);
				/* IMove move2 = */ mme.bestMove(state, xPlayer, oPlayer);
				exploredMME += mme.numStates;

				// sometimes alpha beta returns NULL if there is no hope. For example, on
				// 
				//  XX.    the only defense is to play XXO  but then  XXO  means X wins
				//  O..                                O..            OX.     on next turn
				//  ...                                ...            ...
				//
				// alpha-beta detects this unfavorable situation and gives up. There is
				// nothing that can be done.
			}
		}
		System.out.println ("7\t" + exploredMME + "\t" + explored + "\t" + reduced(explored, exploredMME));

		// ONE MOVE
		explored = exploredMME = 0;
		for (i = 0; i < sz; i++) {
			TicTacToeBoard board = new TicTacToeBoard();
			TicTacToeState state = new TicTacToeState(board, logic);

			new PlaceMark(cells[i].col,cells[i].row, (Player) xPlayer).execute(state);

			algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(8);
			/* IMove move = */ ae.bestMove(state, oPlayer, xPlayer);
			explored += ae.numStates;

			algs.model.gametree.MinimaxEvaluation mme = new algs.model.gametree.MinimaxEvaluation(8);
			/* IMove move2 = */ mme.bestMove(state, oPlayer, xPlayer);
			exploredMME += mme.numStates;

			// sometimes alpha beta returns NULL if there is no hope. For example, on
			// 
			//  XX.    the only defense is to play XXO  but then  XXO  means X wins
			//  O..                                O..            OX.     on next turn
			//  ...                                ...            ...
			//
			// alpha-beta detects this unfavorable situation and gives up. There is
			// nothing that can be done.
		}
		System.out.println ("8\t" + exploredMME + "\t" + explored + "\t" + reduced(explored, exploredMME));




	}
}