/*
 * Copyright 1997-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package algs.model.tree;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Balanced Tree, based on stripped down implementation of 
 * {@link java.util.TreeMap} which is itself an implementation of the
 * algorithm as described in Cormen, Leiserson, and Rivest's
 * <I>Introduction to Algorithms</I> (Cormen et al, 2001). The source code
 * for the original may be found in http://download.java.net/openjdk/jdk6
 * <p>
 * The original copyright notice from Sun Microsystems has been included within
 * this class file even though we have only extracted several methods from that 
 * class.
 * <p>
 * Red-Black tree based implementation of binary tree. Ensures that the
 * entries will be stored using the natural key order for the class K or
 * by the comparator provided at creation time, depending on which 
 * constructor is used.
 * <p>
 * If special balanced trees require additional processing, then extend the
 * rotateLeft and rotateRight trees.
 * <p>
 * An interesting on-line Applet describing Red/Black trees in action can be
 * found here [http://gauss.ececs.uc.edu/RedBlack/redblack.html]. Verified URL
 * August 2007.
 * <p>
 * IMPORTANT: This tree implementation by default assumes all keys are unique.
 * When calling insert (K,V) on a key that is already present, the old value is 
 * replaced with the new V value (and that old value is returned as the return
 * value of insert). To change this default behavior, set the "allowDuplicates"
 * property to be true; this property can only be changed while the tree is
 * empty (to avoid nasty trouble if the assumption were to change).
 * 
 * @param <K>     the base type of the keys stored by each node.
 * @param <V>     the base type of the values stored by the BinaryNode.
 * 
 * @author  Josh Bloch and Doug Lea   [Original authors of java.util.TreeMap]
 * @author George Heineman            [Extended that class for use here]
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class BalancedTree<K,V> {
	/** Comparator used to maintain order in this BalancedTree. */
    protected Comparator<? super K> comparator = null;

    /** Root node. */
    protected BalancedBinaryNode<K,V> root = null;

    /** Number of entries in the tree. */
    protected int size = 0;

    /** Allow duplicates? */
    protected boolean allowDuplicates = false;
    
    /** Constructs an empty Balanced Tree. */
    public BalancedTree() { }

    /**
     * Constructs a new, empty map, sorted according to the given comparator.
     * 
     * @param c the comparator that will be used to sort this map.  A
     *        <tt>null</tt> value indicates that the keys' <i>natural
     *        ordering</i> should be used.
     */
    public BalancedTree(Comparator<? super K> c) {
        this.comparator = c;
    }

    // Query Operations

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return size;
    }

    /**
     * Determine if this tree should allow entries with duplicate keys.
     * 
     * The operation succeeds only if the tree is empty.
     * 
     * @param b    true if the tree should allow duplicates; false otherwise
     * @return     true if the operation is a success; false otherwise.
     */
    public boolean setAllowDuplicates (boolean b) {
    	if (size() != 0) { return false; }
    	
    	allowDuplicates = b;
    	return true;
    }
    
    /**
     * Return whether tree allows entries with duplicate keys.
     * @return true if duplicates are allowed; false otherwise.
     */
    public boolean allowDuplicates () {
    	return allowDuplicates;
    }
    
    /**
     * Return the predecessor node to the given entry.
     * 
     * On an empty tree, returns null.
     * 
     * @param n   query node
     * @return    node which is predecessor to this node.
      */
    public BalancedBinaryNode<K,V> pred (BalancedBinaryNode<K,V> n) {
    	if (root == null) { return null; }
    	
    	// if we have left children, find the right-most child
    	if (n.left != null) {
    		n = n.left;
    		
    		while (n.right != null) {
    			n = n.right;
    		}
    		
    		return n;
    	}
    	
    	// move upwards until we find the node that is the right child of 
    	// some parent node.
    	while (n != root && n == n.parent.left) {
    		n = n.parent;
    	}
    	
    	// If we get to root, then we never were the right child, hence
    	// we always were the left child so there is no predecessor
    	if (n == root) { return null; }
    	
    	// this parent node is the predecessor
    	return n.parent;
    }
    
    /**
     * Returns the comparator used to order this map, or <tt>null</tt> if this
     * map uses its keys' natural order.
     *
     * @return the comparator associated with this sorted map, or
     *                <tt>null</tt> if it uses its keys' natural sort method.
     */
    public Comparator<? super K> comparator() {
        return comparator;
    }

    /**
     * Returns value associated with given key, or <tt>null</tt> if the map
     * does not contain an entry for the key.
     * <p>
     * If the tree allows duplicates, then the first one in the tree which matches
     * is returned.
     * 
     * @param k    target value for which associated value is retrieved.
     * @return value associated with the given key, or <tt>null</tt> if the map
     *                does not contain an entry for the key.
     * @throws NullPointerException key is <tt>null</tt> and this map uses
     *                  natural order, or its comparator does not tolerate
     *                  <tt>null</tt> keys.
     */
    public V search(K k) {
    	BalancedBinaryNode<K,V> p = root;
        while (p != null) {
            int cmp = compare(k, p.key);
            if (cmp == 0) {
                return p.value;
            } else if (cmp < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        
        // not found
        return null;
    }
    
    
    /**
     * Returns this map's entry for the given key, or <tt>null</tt> if the map
     * does not contain an entry for the key.
     * <p>
     * If the tree allows duplicates, then the first one in the tree which matches
     * is returned.
     * 
     * @param k   query key
     * @return this map's entry for the given key, or <tt>null</tt> if the map
     *                does not contain an entry for the key.
     * @throws NullPointerException key is <tt>null</tt> and this map uses
     *                  natural order, or its comparator does not tolerate *
     *                  <tt>null</tt> keys.
     */
    public BalancedBinaryNode<K,V> getEntry(K k) {
    	BalancedBinaryNode<K,V> p = root;
        while (p != null) {
            int cmp = compare(k, p.key);
            if (cmp == 0) {
                return p;
            } else if (cmp < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        
        // not found
        return null;
    }

    /**
     * Returns the entry for the greatest key less than the specified key; if
     * no such entry exists (i.e., the least key in the Tree is greater than
     * the specified key), returns <tt>null</tt>.
     * @param key   key representing an existing node in the tree
     * @return      node that precedes node associated with target key
     */
    public BalancedBinaryNode<K,V> getPrecedingEntry(K key) {
    	BalancedBinaryNode<K,V> p = root;
        if (p==null) {
            return null;
        }
            
        while (true) {
        	int cmp = compare(key, p.key);
            
            if (cmp > 0) {
            	// work to the right; if we run out of room, then we have found it
                if (p.right != null) {
                    p = p.right;
                } else {
                    return p;
                }
            } else {
            	// we have gone too far, cut back to left
                if (p.left != null) {
                    p = p.left;
                } else {
                	// no more left children? Find parent that is NOT a left child
                	// and return that.
                	BalancedBinaryNode<K,V> par = p.parent;
                	BalancedBinaryNode<K,V> ch = p;
                    while (par != null && ch == par.left) {
                        ch = par;
                        par = par.parent;
                    }
                    return par;
                }
            }
        }
    }

    /**
     * Returns the entry for the smallest key greater than the specified key; if
     * no such entry exists (i.e., the least key in the Tree is greater than
     * the specified key), returns <tt>null</tt>.
     * @param key   key representing an existing node in the tree
     * @return      node that succeeds node associated with target key
     */
    public BalancedBinaryNode<K,V> getSuccessorEntry(K key) {
    	BalancedBinaryNode<K,V> p = root;
        if (p==null) {
            return null;
        }
        
        while (true) {
            int cmp = compare(key, p.key);
            
            // if we are looking for SUCCESSOR, then being equal is not good enough! Continue on
            if (cmp < 0) {
            	// work to the left; if we run out of room, then we have found it
                if (p.left != null)
                    p = p.left;
                else
                    return p;
            } else {
            	// we have gone too far, cut back to right
                if (p.right != null) {
                    p = p.right;
                } else {
                	// no more right children? Find parent that is NOT a right child
                	// and return that.
                	BalancedBinaryNode<K,V> par = p.parent;
                	BalancedBinaryNode<K,V> ch = p;
                    while (par != null && ch == par.right) {
                        ch = par;
                        par = par.parent;
                    }
                    return par;
                }
            }
        }
    }
    
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for this key, the old
     * value is replaced and returned.
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     *
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key.  A <tt>null</tt> return can
     *         also indicate that the map previously associated <tt>null</tt>
     *         with the specified key.
     * @throws    ClassCastException key cannot be compared with the keys
     *            currently in the map.
     * @throws NullPointerException key is <tt>null</tt> and this map uses
     *         natural order, or its comparator does not tolerate
     *         <tt>null</tt> keys.
     */
    public V insert(K key, V value) {
    	BalancedBinaryNode<K,V> t = root;

    	if (key == null && comparator == null) {
    		throw new NullPointerException("BalancedTree cannot support null key values with default comparator.");
    	}
    	
        if (t == null) {
        	size++;
            root = construct (key, value, null);
            return null;
       }

        while (true) {
            int cmp = compare(key, t.key);
            
            if (!allowDuplicates && cmp == 0) {
                return t.setValue(value);
            }
            
            if (cmp <= 0) {
                if (t.left != null) {
                    t = t.left;
                } else {
                	size++;
                    t.left = construct (key, value, t);
                    fixAfterInsertion(t.left);
                    return null;
                }
            } else { // cmp > 0
                if (t.right != null) {
                    t = t.right;
                } else {
                	size++;
                    t.right = construct (key, value, t);
                    fixAfterInsertion(t.right);
                    return null;
                }
            }
        }
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
    protected BalancedBinaryNode<K,V> construct(K key, V value, BalancedBinaryNode<K,V> parent) {
    	return new BalancedBinaryNode<K,V>(key, value, parent);
	}

	/**
     * Removes the mapping for this key from this TreeMap if present.
     * <p>
     * If tree allows duplicates, then the first one that maps to the key
     * is returned.
     * 
     * @param  key key for which mapping should be removed
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key.  A <tt>null</tt> return can
     *         also indicate that the map previously associated
     *         <tt>null</tt> with the specified key.
     *
     * @throws    ClassCastException key cannot be compared with the keys
     *            currently in the map.
     * @throws NullPointerException key is <tt>null</tt> and this map uses
     *         natural order, or its comparator does not tolerate
     *         <tt>null</tt> keys.
     */
    public V remove(K key) {
    	BalancedBinaryNode<K,V> p = getEntry(key);
        if (p == null)
            return null;

        V oldValue = p.value;
        deleteEntry(p);
        return oldValue;
    }

    /**
     * Empty out the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }
    
    /**
     * Compares two keys using the correct comparison method for this BalancedTree.
     * <p>
     * 
     * @param k1   base key 
     * @param k2   key to compare against k1
     */
    @SuppressWarnings("unchecked")
	protected int compare(K k1, K k2) {
    	if (comparator == null) {
    		// default to base comparable.
    		return ((Comparable<K>)k1).compareTo(k2);
    	}
        return comparator.compare((K)k1, (K)k2);
    }

    /**
     * Returns the first Entry in the BalancedTree (according to the BalancedTree
     * key-sort function).  Returns null if the BalancedTree is empty.
     * @return    minimum value in the balanced tree.
     */
    public BalancedBinaryNode<K,V> firstNode() {
    	BalancedBinaryNode<K,V> p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }
    
    /**
     * Removes and returns minimum value in tree. Return null if tree is empty. 
     * @return   minimum value after it has been removed; null if empty.
     */
    public V minimum() {
    	BalancedBinaryNode<K,V> p = root;
    	if (p == null) { return null; }
    	
        while (p.left != null)
        	p = p.left;
        
        V oldValue = p.value;
        deleteEntry(p);
        return oldValue;
    }
    
    /**
     * Returns the root of the tree (or null if empty).
     * @return root of tree.
     */
    public BalancedBinaryNode<K,V> root() {
    	return root;
    }
   
    /**
     * Returns the last Entry in the tree.
     * @return maximum entry in tree.
     */
    public BalancedBinaryNode<K,V> lastNode() {
    	BalancedBinaryNode<K,V> p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
    }
    
    /**
     * Returns the successor of the specified Entry, or null if no such.
     * @param t    query node.
     * @return success to given node
     */
    public BalancedBinaryNode<K,V> successor(BalancedBinaryNode<K,V> t) {
        if (t == null)
            return null;

        // any children on the right? go there and find left-most child.
        if (t.right != null) {
        	BalancedBinaryNode<K,V> p = t.right;
            while (p.left != null) {
                p = p.left;
            }
            
            return p;
        } 

        // so no right children. Move on up to find a parent for
        // whom this node is NOT the right child, and return it (or null).
        BalancedBinaryNode<K,V> p = t.parent;
        BalancedBinaryNode<K,V> ch = t;
        while (p != null && ch == p.right) {
        	ch = p;
            p = p.parent;
        }
        return p;
    }

    /**
     * Balancing operations.
     *
     * Implementations of rebalancings during insertion and deletion are
     * slightly different than the CLR version.  Rather than using dummy
     * nilnodes, we use a set of accessors that deal properly with null.  They
     * are used to avoid messiness surrounding nullness checks in the main
     * algorithms.
     */

    private static <K,V> boolean colorOf(BalancedBinaryNode<K,V> p) {
        return (p == null ? BalancedBinaryNode.BLACK : p.color);
    }

    private static <K,V> BalancedBinaryNode<K,V> parentOf(BalancedBinaryNode<K,V> p) {
        return (p == null ? null: p.parent);
    }

    private static <K,V> void setColor(BalancedBinaryNode<K,V> p, boolean c) {
        if (p != null)
	    p.color = c;
    }

    private static <K,V> BalancedBinaryNode<K,V> leftOf(BalancedBinaryNode<K,V> p) {
        return (p == null) ? null: p.left;
    }

    private static <K,V> BalancedBinaryNode<K,V> rightOf(BalancedBinaryNode<K,V> p) {
        return (p == null) ? null: p.right;
    }

    /** From CLR **/
    protected void rotateLeft(BalancedBinaryNode<K,V> p) {
    	BalancedBinaryNode<K,V> r = p.right;
        p.right = r.left;
        if (r.left != null)
            r.left.parent = p;
        r.parent = p.parent;
        if (p.parent == null)
            root = r;
        else if (p.parent.left == p)
            p.parent.left = r;
        else
            p.parent.right = r;
        r.left = p;
        p.parent = r;
    }

    /** From CLR **/
    protected void rotateRight(BalancedBinaryNode<K,V> p) {
    	BalancedBinaryNode<K,V> l = p.left;
        p.left = l.right;
        if (l.right != null) l.right.parent = p;
        l.parent = p.parent;
        if (p.parent == null)
            root = l;
        else if (p.parent.right == p)
            p.parent.right = l;
        else p.parent.left = l;
        l.right = p;
        p.parent = l;
    }


    /** From CLR **/
    protected void fixAfterInsertion(BalancedBinaryNode<K,V> x) {
        x.color = BalancedBinaryNode.RED;

        while (x != null && x != root && x.parent.color == BalancedBinaryNode.RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
            	BalancedBinaryNode<K,V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == BalancedBinaryNode.RED) {
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(y, BalancedBinaryNode.BLACK);
                    setColor(parentOf(parentOf(x)), BalancedBinaryNode.RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(parentOf(parentOf(x)), BalancedBinaryNode.RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateRight(parentOf(parentOf(x)));
                }
            } else {
            	BalancedBinaryNode<K,V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == BalancedBinaryNode.RED) {
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(y, BalancedBinaryNode.BLACK);
                    setColor(parentOf(parentOf(x)), BalancedBinaryNode.RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(parentOf(parentOf(x)), BalancedBinaryNode.RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BalancedBinaryNode.BLACK;
    }

    /**
     * Delete node p, and then rebalance the tree.
     */
    protected void deleteEntry(BalancedBinaryNode<K,V> p) {
    	size--;

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
        	BalancedBinaryNode<K,V> s = successor (p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        BalancedBinaryNode<K,V> replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            p.left = p.right = p.parent = null;

            // Fix replacement
            if (p.color == BalancedBinaryNode.BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { // return if we are the only node.
            root = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            if (p.color == BalancedBinaryNode.BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }

    /** From CLR **/
    private void fixAfterDeletion(BalancedBinaryNode<K,V> x) {
        while (x != root && colorOf(x) == BalancedBinaryNode.BLACK) {
            if (x == leftOf(parentOf(x))) {
            	BalancedBinaryNode<K,V> sib = rightOf(parentOf(x));

                if (colorOf(sib) == BalancedBinaryNode.RED) {
                    setColor(sib, BalancedBinaryNode.BLACK);
                    setColor(parentOf(x), BalancedBinaryNode.RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib))  == BalancedBinaryNode.BLACK &&
                    colorOf(rightOf(sib)) == BalancedBinaryNode.BLACK) {
                    setColor(sib, BalancedBinaryNode.RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BalancedBinaryNode.BLACK) {
                        setColor(leftOf(sib), BalancedBinaryNode.BLACK);
                        setColor(sib, BalancedBinaryNode.RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(rightOf(sib), BalancedBinaryNode.BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { // symmetric
            	BalancedBinaryNode<K,V> sib = leftOf(parentOf(x));

                if (colorOf(sib) == BalancedBinaryNode.RED) {
                    setColor(sib, BalancedBinaryNode.BLACK);
                    setColor(parentOf(x), BalancedBinaryNode.RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BalancedBinaryNode.BLACK &&
                    colorOf(leftOf(sib)) == BalancedBinaryNode.BLACK) {
                    setColor(sib, BalancedBinaryNode.RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BalancedBinaryNode.BLACK) {
                        setColor(rightOf(sib), BalancedBinaryNode.BLACK);
                        setColor(sib, BalancedBinaryNode.RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BalancedBinaryNode.BLACK);
                    setColor(leftOf(sib), BalancedBinaryNode.BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BalancedBinaryNode.BLACK);
    }

    /**
	 * Accept a visitor for a inorder traversal.
	 * @param visitor    visitor to execute upon each node in the traversal.
	 */
	public void accept (IBalancedVisitor<K,V> visitor) {
		if (root == null) return;
		
		accept (null, root, visitor);
	}
	
	/** 
	 * Return string of sub-tree rooted at node. Recursive operation.
	 * @return human readable string. 
	 */
	private String toString (BalancedBinaryNode<K,V> node) {
		if (node == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder("(");
		sb.append (toString (node.left()));
		sb.append (node.toString());
		sb.append (toString (node.right()));
		return sb.append(")").toString();
	}
	
	/** Return parenthetical string of tree structure using inOrder traversal. */
	public String toString () {
		if (root == null) {
			return ""; 
		}
		
		return toString (root);
	}
	
	private void accept (BalancedBinaryNode<K,V> parent, 
			BalancedBinaryNode<K,V> node, IBalancedVisitor<K,V> visitor) {
		
		// go left
		if (node.left() != null) {
			accept (node, node.left(), visitor);
		}
		
		// self
		visitor.visit(parent, node);
		
		// go right
		if (node.right() != null) {
			accept (node, node.right(), visitor);
		}
	}
    
    // Expose Iterator interface, though it is inefficient.
    public Iterator<V> iterator () {
    	return new BalancedTreeIterator(this);
    }
    
    /**
     * Internal iterator over BalancedTree.
     * 
	 * @author George Heineman
	 * @version 1.0, 6/15/08
	 * @since 1.0
	 */
    class BalancedTreeIterator implements Iterator<V> {

    	BalancedBinaryNode<K,V> current;
    	
    	BalancedTreeIterator (BalancedTree<K,V> bt) {
    		current = bt.firstNode();
    	}
    	
    	public boolean hasNext() {
    		return current != null;
    	}

    	public V next() {
    		V value = current.value;
    		current = getSuccessorEntry(current.key);
    		return value;
    	}

    	/** Unsupported. */
    	public void remove() {
    		throw new UnsupportedOperationException("BalancedTree does not support removal via the Iterator");
    	}
    }
}
