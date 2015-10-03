package algs.example.gui.model;

import algs.model.kdtree.KDTree;

/**
 * Return a KD tree being supported by the model.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */

public interface IRetrieveKDTree {

	/** 
	 * Retrieve the KD tree supported by the model. 
	 */
	KDTree getTree();	
}
