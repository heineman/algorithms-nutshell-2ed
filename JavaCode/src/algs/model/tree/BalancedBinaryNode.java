package algs.model.tree;

import algs.debug.IGraphEntity;


/**
 * Standard node for an unbalanced binary tree.
 * <p>
 * Each node has a 'key' which is used to determine location of the node
 * within the balanced binary tree. Each node also has a 'value' which
 * can be anything else.
 * <p>
 * Use the mutator methods CAREFULLY!
 *
 * @param <K>     the base type of the keys stored by each node.
 * @param <V>     the base type of the values stored by the BinaryNode.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BalancedBinaryNode<K,V> implements IGraphEntity {

	/** Left son. */
	protected BalancedBinaryNode<K,V> left;
	
	/** Right son. */
	protected BalancedBinaryNode<K,V> right;
	
	/** Parent. */
	protected BalancedBinaryNode<K,V> parent;
	
	/** Key. */
	K key;
	
	/** Value. */
	V value;
	
	/** Value to use when node is a RED node. */
    public static final boolean RED   = false;
    
    /** Value to use when node is a BLACK node. */
    public static final boolean BLACK = true;

    /** Color. */
	protected boolean color = BLACK;
	
	/**
	 * Set node color.
	 * 
	 * @param color    true for BLACK; false for RED.
	 */
	public void color(boolean color) {
		this.color = color;
	}
	
	/**
	 * Get node color.
	 * @return false if Red; true if Black
	 * */
	public boolean color() {
		return color;
	}
	
	/**
     * Make a new node with given key, value, and parent, and with
     * <tt>null</tt> child links, and BLACK color.
     * 
	 * @param key         key value to be associated with value in the tree
	 * @param value       value to be associated with key
	 * @param parent      parent to which child will be inserted.
	 */
    public BalancedBinaryNode(K key, V value, BalancedBinaryNode<K,V> parent) {
		
		this.key = key;
		this.parent = parent;
		
		left = null;
		right = null;
		parent = null;
		this.value = value;
	}

	/** 
	 * Return left son. 
	 * @return left child 
	 */
	public BalancedBinaryNode<K,V> left() {
		return left;
	}

	/** 
	 * Return right son.
 	 * @return right child 
	 */
	public BalancedBinaryNode<K,V> right() {
		return right;
	}
	
	/**
	 * Set the right child.
	 * 
	 * @param newRight  new node to be right child.
	 */
	public void right(BalancedBinaryNode<K,V> newRight) {
		right = newRight;
	}
	
	/**
	 * Set the left child.
	 * 
	 * @param newLeft    new node to be left child.
	 */
	public void left(BalancedBinaryNode<K,V> newLeft) {
		left = newLeft;
	}
	
	/**
	 * Get parent (needed for rotations and the like).
	 * @return parent node for this node.
	 */
	public BalancedBinaryNode<K,V> parent() {
		return parent;
	}
	
	/**
	 * Set parent.
	 *
	 * @param newParent   move this node to be child to designated node.
	 */
	public void parent(BalancedBinaryNode<K,V> newParent) {
		parent = newParent;
	}
	
	/**
	 * Return the key for this node.
	 * @return key associated with node.
	 */
	public K key() {
		return key;
	}
	
	/**
	 * Return the value for this node.
	 * @return value associated with this node.
	 */
	public V value() {
		return value;
	}
	
	 /**
     * Replaces the value currently associated with the key with the given
     * value.
     *
     * @return the value associated with the key before this method was
     *           called.
     * @param value to associate with node
     */
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
	
	/**
	 * Return string representation of this node.
	 * @return human readable string
	 */
	public String toString () {
		return key + "=" + value;
	}

	/**
	 * Safely perform o1.equals(o2) even if both are null.
	 * 
	 * @param o1   object to compare
	 * @param o2   object against which to compare
	 * @return     return true if equals; false otherwise
	 */
    private boolean safeEquals (Object o1, Object o2) {
    	if (o1 == null && o2 == null) return true;
    	
    	if (o1 == null) return false;
    	if (o2 == null) return false;
    	return o1.equals(o2);
    }
    
    /**
     * Provide standard equals method.
     */
    @SuppressWarnings("unchecked")
	public boolean equals(Object o) {
        if (o instanceof BalancedBinaryNode) {
        	BalancedBinaryNode<K,V> bbn = (BalancedBinaryNode<K,V>) o;
        	
        	return safeEquals(key, bbn.key()) &&
        		safeEquals(value,bbn.value());
        }
        
        return false; // nope
    }

	public String nodeLabel() {
		StringBuilder sb = new StringBuilder("{");
		if (color == RED) { sb.append ("red"); } else { sb.append ("black"); }
		sb.append ("|" + value + "}");
		return sb.toString();
	}
}
