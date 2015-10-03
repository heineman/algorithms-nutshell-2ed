package algs.example.model.pseudocodeExample;

import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;

import org.junit.Test;

import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;


import junit.framework.TestCase;

public class PrepareGameTreeFigures extends TestCase {
	@Test
	public void testAlphaBeta() {
		IGameState state = new MiniState (1);
		
		Hashtable<Integer,Integer> table = new Hashtable<Integer,Integer>();
		
		// populate known states and scores
		table.put(6,2);
		table.put(8,7);
		table.put(9,2);
		table.put(10,3);
		table.put(11,6);
		table.put(12,2);
		
		Hashtable<Integer,Collection<IGameMove>> moves = 
			new Hashtable<Integer,Collection<IGameMove>>();
		moves.put(1, Arrays.asList(new IGameMove[]{new MiniMove(2), new MiniMove(3)}));
		moves.put(2, Arrays.asList(new IGameMove[]{new MiniMove(4), new MiniMove(5)}));
		moves.put(3, Arrays.asList(new IGameMove[]{new MiniMove(6), new MiniMove(7)}));
		moves.put(4, Arrays.asList(new IGameMove[]{new MiniMove(8), new MiniMove(9)}));
		moves.put(5, Arrays.asList(new IGameMove[]{new MiniMove(10)}));
		moves.put(7, Arrays.asList(new IGameMove[]{new MiniMove(11), new MiniMove(12)}));
		
		
		IPlayer player1 = new MiniPlayer('X', moves);
		IPlayer player2 = new MiniPlayer('O', moves);
		
		player1.score(new MiniScoring(player1, table));
		player2.score(new MiniScoring(player1, table)); // to negate!
		
	    // 3 ply lookahead.
	    algs.model.gametree.AlphaBetaEvaluation ae = new algs.model.gametree.AlphaBetaEvaluation(3);
		IGameMove move = ae.bestMove (state, player1, player2);
		
		System.out.println ("move:" + move);
		
	}
}
