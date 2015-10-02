package algs.model.problems.segmentIntersection;

import java.util.Comparator;

import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;

/**
 * The Balanced Binary Tree for this algorithm required internal nodes
 * to store (min, max) links to the leaf nodes, where actual segments
 * are to be stored.
 * 
 * @param <K>  The key to be used for the nodes in the tree. Note that both
 *             the key and the value of the nodes will be of type K for simplicity
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class AugmentedBalancedTree<K> extends BalancedTree<K,K> {
	
	/**
     * Helps with type casting
     */
    public AugmentedNode<K> root() { 
    	return (AugmentedNode<K>) root;
    }
	
	/**
     * Constructs an empty Balanced Tree.
     */
    public AugmentedBalancedTree() { 
    	super();
    }
	
	 /**
     * Constructs a new, empty tree sorted according to the given comparator.
     * 
     * @param c the comparator that will be used to sort this map.  A
     *        <tt>null</tt> value indicates that the keys' <i>natural
     *        ordering</i> should be used.
     */
    public AugmentedBalancedTree(Comparator<? super K> c) {
        super(c);
    }
	
	/**
     * Construct a node in the tree.
     * 
     * Subclass extensions of BalancedTree must override this method to
     * insert the appropriate node of the appropriate subclass.
     * 
     * @param key
     * @param value
     * @param parent
     */
    protected AugmentedNode<K> construct(K key, K value, AugmentedNode<K> parent) {
    	return new AugmentedNode<K>(key, value, parent);
	}
    
    /**
     * Overrides locate by using interior nodes as guides.
     */
    @Override
    public AugmentedNode<K> getEntry(K key) {
    	// not present!
		AugmentedNode<K> t = (AugmentedNode<K>) root();
   	    if (t == null) {
			return null;
    	}
   	    
   	    // find leaf entry.
   	    // Use interior nodes (i.e., whose value is null) to guide the location.
    	while (t.value() == null) {
   	    	int cmp = compare(t.right().min, key);
   	    	if (cmp > 0) {
   	    		t = t.left();
   	    	} else {
   	    		t = t.right();
   	    	}
   	    }
    	
    	if (t.value().equals(key)) {
    		return t;
    	}
    	
    	return null;
    }
    
    /**
     * Overrides deletion by finding the leaf node for this key.
     */
    @Override
    public K remove(K key) {
    	// not present!
		AugmentedNode<K> t = (AugmentedNode<K>) root();
   	    if (t == null) {
			return null;
    	}
   	    
   	    // find leaf entry.
   	    // Use interior nodes (i.e., whose value is null) to guide the location.
    	while (t.value() == null) {
   	    	int cmp = compare(t.right().min, key);
   	    	if (cmp > 0) {
   	    		t = t.left();
   	    	} else {
   	    		t = t.right();
   	    	}
   	    }
   	    
    	// we are at a leaf. Check if this is the proper one. If so, remove, otherwise return null.
    	K val = t.value();
    	if (compare(t.key(), key) == 0) {
	    	deleteEntry(t);
	    	return val;
    	} else {
    		return null;
    	}
    }
    
    
    /**
     * Must make sure that interior nodes contain no segments; rather, only
     * the leaves do. 
     * 
     * Thus we completely override insert. Note that the value to be inserted
     * is the same as the key - that is, K,V are the same.
     * 
     * @param key    key to be inserted (with same value)
     * @return       key of the inserted key
     */
    public K insert(K key) {
    	AugmentedNode<K> t = (AugmentedNode<K>) root;
    	if (t == null) {
    		root = new AugmentedNode<K>(key, key, null);
    		size++;
    		return root.key();
    	}
    	
    	// not root? Find where it should go. Use interior nodes (i.e., whose
    	// value is null) to guide the location.
    	//AugmentedNode<K> t = (AugmentedNode<K>) root();
   	    while (t.value() == null) {
   	    	AugmentedNode<K> nn = (AugmentedNode<K>)(t.right());
   	    	K val = nn.min;
   	    	int cmp = compare(val, key);
   	    	if (cmp > 0) {
   	    		t = (AugmentedNode<K>) t.left();
   	    	} else {
   	    		t = (AugmentedNode<K>) t.right();
   	    	}
   	    }
 	
   	    // OK. We are now at a leaf node. Do the magic to convert this node
   	    // into a sub-tree of size 3 (new parent, with a new child to be sibling
   	    // to the old one. Note that t could still be the root (a one-node tree) so pay attention
   	    // to that point.
   	    AugmentedNode<K> newParent = new AugmentedNode<K>(null, null, (AugmentedNode<K>)t.parent());
   	    AugmentedNode<K> newNode = new AugmentedNode<K>(key, key, newParent);
   	    
   	    if (t == root) {
   	    	// we have a new root
   	    	root = newParent;
   	    } else {
   	    	// hook in
   	    	if (newParent.parent().left() == t) {
   	    		newParent.parent().left(newParent);
   	    	} else {
   	    		newParent.parent().right(newParent);
   	    	}
   	    }
   	    
   	    // re-parent the node we found when looking where to place key.
   	    t.parent(newParent);
    	
   	    // now must sort out which is left and right child
   	    if (compare (t.value(), key) > 0) {
   	    	// t is right child
   	    	newParent.right(t);
   	    	newParent.left(newNode);
   	    } else {
   	    	// t is left child
   	    	newParent.left(t);
   	    	newParent.right(newNode);
   	    }
   	    
   	    // set min/max for the new parent.
		AugmentedNode<K> lf = (AugmentedNode<K>)(newParent.left());
		newParent.min = lf.min;
 		AugmentedNode<K> rt = (AugmentedNode<K>)(newParent.right());
		newParent.max = rt.max;
 	 
		// we have now inserted TWO nodes.
		size += 2;
		
   	 	// propagate the changes up. Start with newParent.
		propagate((AugmentedNode<K>)newParent.parent());
		
		// rebalance, starting with the new parent.
		fixAfterInsertion(newParent);
		return newNode.key();
    }
    
    /**
     * Update min/max segments up the tree.
     * 
     * @param t   Tree node from which to initiate the propagation.
     */
    private void propagate(AugmentedNode<K> t) {
    	AugmentedNode<K> lf;
    	AugmentedNode<K> rt;
    	
		while (t != null) {
			lf = t.left();
			rt = t.right();
			
			boolean updated = false;
			
			if (!t.min.equals(lf.min)) {
				t.min = lf.min;
				updated = true;
			}
			if (!t.max.equals(rt.max)) {
				t.max = rt.max;
				updated = true;
			}
			
			// continue?
			if (!updated) break;
			
			t = t.parent();
		}		
	}

	/**
     * Because we are using leaf nodes only to store the segments, we can be
     * guaranteed that the node p is going to be a leaf node. If this is 
     * not the case, then we will encounter problems, naturally!
     * 
     * We can casually throw away interior nodes of the tree, since they
     * don't actually store anything, right?
     */
    @Override
    protected void deleteEntry(BalancedBinaryNode<K,K> p) {
    	// if we are the root, we are also a leaf, which means we are the last
    	// one in the tree. LEAVE NOW!
    	if (p == root()) {
    		root = null;
    		size--;
    		return;
    	}
    	
		// Note that p's children all still have proper min/max set, so we must go
		// to p's current grandparent, to ensure that min/max is properly dealt with.
 		AugmentedNode<K> node = (AugmentedNode<K>) p;
		AugmentedNode<K> parent = node.parent();
		AugmentedNode<K> otherSib;
		
		if (node == node.parent().left()) {
			otherSib = node.parent().right();
		} else {
			otherSib = node.parent().left();
		}
		
	   	// We could be one of TWO children directly below the root. Since we are 
    	// also a leaf, then we know that our sibling will now become the root.
		// handle now. MAKE SURE YOU CHANGE COLOR to BLACK for maintaining RED/BLACK
		// invariant.  See CLR algorithm. No need to modify min/max, since already
		// properly set for the other sibling.
		if (node.parent() == root()) {
			otherSib.parent(null);   // detach to make root!
			otherSib.color(AugmentedNode.BLACK);
			root = otherSib;
			size -=2;  // we delete two nodes [node and p]
			return;
		}
		
		// Now we do the dirty work.
   		AugmentedNode<K> grandParent = parent.parent();
    		
   		// snip out appropriately [we are deleting p after all, right?]
		otherSib.parent(grandParent);
		if (p.parent() == grandParent.left()) {
			grandParent.left(otherSib);
		} else {
			grandParent.right(otherSib);
		}
		
		propagate(otherSib.parent());
		size-=2;  // have deleted two nodes [node and p]
    }
    
    @Override
    /** From CLR **/
    protected void fixAfterInsertion(BalancedBinaryNode<K,K> p) {
    	super.fixAfterInsertion(p);
    	
    	// p was the new parent of the one inserted. HE is fine. However, his sibling may
    	// have been rotated down, and has changed its location in the tree
    	propagate((AugmentedNode<K>)p);
    }
    
	@Override
    protected void rotateLeft(BalancedBinaryNode<K,K> p) {
		super.rotateLeft(p);
		
		// post process. This only works since we *know* the actual types are
		// subtypes. node was the old parent (p), and it has now become the left
		// child of parent. We must update min/max nodes of the two affected
		// nodes.
		AugmentedNode<K> node = (AugmentedNode<K>) p;
		AugmentedNode<K> parent = (AugmentedNode<K>) p.parent();
		
		node.max = (node.right()).max;
		node.min = (node.left()).min;
		
		parent.max = (parent.right()).max;
		parent.min = (parent.left()).min;
    }

	@Override
    protected void rotateRight(BalancedBinaryNode<K,K> p) {
		super.rotateRight(p);
		
		// post process: This only works since we *know* the actual types are
		// subtypes. node was the old parent (p), and it has now become the right
		// child of parent. We must update min/max nodes of the two affected
		// nodes.
		AugmentedNode<K> node = (AugmentedNode<K>) p;
		AugmentedNode<K> parent = (AugmentedNode<K>) p.parent();
	
		node.max = (node.right()).max;
		node.min = (node.left()).min;
		
		parent.max = (parent.right()).max;
		parent.min = (parent.left()).min;
    }
}
