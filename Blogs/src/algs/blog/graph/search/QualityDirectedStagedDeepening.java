package algs.blog.graph.search;

import algs.model.searchtree.INode;
import algs.model.searchtree.IScore;

/**
 * There may be multiple goal states that are satisfactory. This class identifies
 * the solution is reached based upon a particular quality of the node state.
 * <p>
 * We use the convention that the solution is found when the scoring heuristic
 * returns 0 (which means, "0 moves away from solution").
 * 
 * @param <K>   Type of the key() value for nodes.
 * 
 * @author George Heineman
 */
public class QualityDirectedStagedDeepening<K> extends StagedDeepening<K> {
	
	/**
	 * Target a specific goal in mind, using the provided evaluation score
	 * @param goal
	 */
	public QualityDirectedStagedDeepening (IScore eval, IVisitor visitor) {
		super(visitor);
		this.eval = eval;
	}
	
	/**
	 * Target a specific goal in mind, using the provided evaluation score
	 * @param goal
	 */
	public QualityDirectedStagedDeepening (IScore eval) {
		super(null);
		
		this.eval = eval;
	}
	
	@Override
	public boolean searchComplete(INode n) {
		return (eval.eval(n)== 0);
	}
	
}
