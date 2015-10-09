package algs.blog.graph.search;

import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * Evaluates search termination by comparing board states against a lone
 * goal state.
 * 
 * @param <K> 
 * @author George Heineman
 */
public class GoalDirectedStagedDeepening<K> extends StagedDeepening<K> {
	/** Goal we are trying to reach. */
	INode goal;

	/**
	 * Target a specific goal in mind, using the provided evaluation score
	 * @param goal
	 */
	public GoalDirectedStagedDeepening (INode goal, IScore eval, IVisitor visitor) {
		super(visitor);
		
		this.goal = goal;
		this.eval = eval;
	}
	
	/**
	 * Target a specific goal in mind, using the provided evaluation score
	 * @param goal
	 */
	public GoalDirectedStagedDeepening (INode goal, IScore eval) {
		super(null);
		
		this.goal = goal;
		this.eval = eval;
	}
	
	@Override
	public boolean searchComplete(INode n) {
		return n.equals(goal);
	}
	
}
