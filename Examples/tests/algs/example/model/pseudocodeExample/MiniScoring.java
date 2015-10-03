package algs.example.model.pseudocodeExample;

import java.util.Hashtable;

import algs.model.gametree.IGameState;
import algs.model.gametree.IPlayer;
import algs.model.gametree.IGameScore;

public class MiniScoring implements IGameScore {

	/** should score be told from player's point of view or opponent? */
	IPlayer player;
	
	/** Table of known scores. */
	Hashtable<Integer,Integer> table;
	
	MiniScoring (IPlayer player, Hashtable<Integer,Integer> table) {
		this.player = player;
		this.table = table;
	}
	
	/** Look up score from table. */
	public int score(IGameState state, IPlayer player) {
		boolean negate = false;
		
		if (this.player != player) {
			negate = true;
		}
		
		int value = table.get(((MiniState)state).state);
		if (negate) value = -value;
		
		return value;
	}

}
