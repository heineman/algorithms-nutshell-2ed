package algs.example.model.pseudocodeExample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import algs.model.gametree.IGameState;
import algs.model.gametree.IGameMove;
import algs.model.gametree.IPlayer;
import algs.model.gametree.IGameScore;

public class MiniPlayer implements IPlayer {

	char mark;
	
	IGameScore score;
	
	Hashtable<Integer,Collection<IGameMove>> moves;
	
	MiniPlayer (char mark, Hashtable<Integer,Collection<IGameMove>> moves) {
		this.mark = mark;
		this.moves = moves;
	}
	
	public int eval(IGameState state) {
		return score.score(state, this);
	}

	public void score(IGameScore score) {
		this.score = score;
	}

	public Collection<IGameMove> validMoves(IGameState state) {
		Collection<IGameMove> col = moves.get(((MiniState)state).state);
		if (col == null) {
			return new ArrayList<IGameMove>();
		}
		
		return col;
	}

}
