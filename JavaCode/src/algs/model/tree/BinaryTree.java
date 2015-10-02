package algs.model.tree;

import java.util.Iterator;

/**
 * Standard unbalanced binary tree.
 * 
 * Duplicates are allowed. The right child of a node in the tree is guaranteed to 
 * have its value be greater than or equal to its parent.
 * 
 * @param <T>     the base type of the values stored by the BinaryTree. Must be
 *                Comparable.
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class BinaryTree<T extends Comparable<T>> implements Iterable<T> {

	/** Root of the tree. */
	private BinaryNode<T> root;
	
	/** Static empty iterator object, as needed. */
	@SuppressWarnings("rawtypes")
	private static Iterator empty;
	
	/**
	 * Singleton instance of the empty iterator, available for use.
	 */
	private Iterator<T> empty() {
		if (empty == null) {
			empty = new Iterator<T>() {
				public boolean hasNext() { return false; }
				public T next() { throw new java.util.NoSuchElementException("Empty Iterator"); }
				public void remove() { throw new UnsupportedOperationException("Empty Iterator can't be modified"); }
			};
		}
		
		return empty;
	}
	
	/** Default BinaryTree constructor. */
	public BinaryTree() {
		root = null;
	}
	
	/**
	 * Helper method to construct appropriately typed BinaryNode for this value.
	 * 
	 * Specialized Trees may override this behavior, as needed.
	 */
	 BinaryNode<T> construct(T value) {
		return new BinaryNode<T>(value);
	}
	
	/**
	 * Helper method to properly set the root for the tree.
	 * 
	 * @param newRoot
	 */
	protected void setRoot (BinaryNode<T> newRoot) {
		root = newRoot;
	}
	
	/**
	 * Expose the root of the tree.
	 * @return root node of the binary tree.
	 */
	public BinaryNode<T> getRoot () {
		return root;
	}
	 
	/**
	 * Determine if the given value occurs in the tree
	 * 
	 * @param value  non-null desired value to search for
	 * @return       true if the value is stored in the Binary Tree
	 * @exception    IllegalArgumentException if value is null
     * @exception    ClassCastException if the specified object's type prevents it
     *               from being compared to this object.
	 */
	public boolean member (T value) {
		if (value == null) {
			throw new IllegalArgumentException ("BinaryTree cannot store 'null' values.");
		}
		
		// empty tree? Not found.
		if (root == null) {
			return false;
		}
		
		// compare and locate in proper location
		BinaryNode<T> node = root;
		while (node != null) {
			int c = value.compareTo(node.value);
		
			if (c == 0) {
				return true;
			}
			
			if (c < 0) {
				// Search to the left
				node = node.getLeftSon();
			} else {
				node = node.getRightSon();
			}
		}
		
		// not found.
		return false;
	}
	
	/**
	 * Remove the value from the tree.
	 * 
	 * @param value  non-null value to be removed
	 * @return true  if the value existed and was removed; otherwise return false
	 * @exception    IllegalArgumentException if value is null
     * @exception    ClassCastException if the specified object's type prevents it
     *               from being compared to this object.
	 */
	public boolean remove (T value) {
		if (value == null) {
			throw new IllegalArgumentException ("BinaryTree cannot store 'null' values.");
		}
		
		// empty tree? 
		if (root == null) {
			return false;
		}

		// compare and place in proper location
		BinaryNode<T> node = root;
		BinaryNode<T> parent = null;
		BinaryNode<T> n;
		boolean returnVal = false;
		
		// This somewhat strange while-loop logic was done to ensure 100% coverage in Coverlipse
		while (!returnVal) {
			int c = value.compareTo(node.value);
		
			if (c < 0) {
				// removal to the left: If no child then we are done, otherwise recurse
				if ((n = node.getLeftSon()) == null) {
					break;  // false
				} else {
					parent = node;
					node = n;  // iterate down to this one.
				}
			} else if (c > 0) {
				// removal to the right: If no child then we are done, otherwise recurse
				if ((n = node.getRightSon()) == null) {
					break; // false
				} else {
					parent = node;
					node = n;  // iterate down to this one.
				}				
			} else {
				// Multiple cases to consider:
				removeHelper (node, parent);
				returnVal = true;
				break;
			}
		}
		
		return returnVal;
	}
	
	/**
	 * Helper method to properly handle the multiple subcases when removing a node
	 * from the tree.
	 * <p>
	 * The key is to find the successor-value (pv) to the target-value (tv). You can 
	 * set the value of 'tv' to be 'pv' and then delete the original 'pv' node. Find the 
	 * minimum value in the right sub-tree and remove it. Use that value as the replacement 
	 * value for node tv.
	 * 
	 * @param target    Node to be removed
	 * @param parent    parent of target node (or null if target is the root).
	 */
	void removeHelper (BinaryNode<T> target, BinaryNode<T> parent) {
		BinaryNode<T> lnode = target.getLeftSon();
		BinaryNode<T> rnode = target.getRightSon();

		// 0. No children (i.e., a leaf node). Just pull it out
		// ------------------------------------------------------
		if (lnode == null && rnode == null) {
			// Deleting root
			if (parent == null) {
				setRoot(null);
				return;
			}
			
			// Simply extract from parent.
			if (parent.getLeftSon() == target) {
				parent.left = null;
			} else {
				parent.right = null;
			}
			
			return;
		}
		
		// 1. target has only a left child
		// --------------------------------------------------
		if (lnode != null && rnode == null) {
			if (parent == null) {
				setRoot(lnode);
				return;
			}
			
			// Simply relink with parent.
			if (parent.getLeftSon() == target) {
				parent.left = lnode;
			} else {
				parent.right = lnode;
			}
			
			return;
		}
		
		// 2. target has only a right child
		// --------------------------------------------------
		if (rnode != null && lnode == null) {
			if (parent == null) {
				setRoot(rnode);
				return;
			}
			
			// Simply relink with parent.
			if (parent.getLeftSon() == target) {
				parent.left = rnode;
			} else {
				parent.right = rnode;
			}
			
			return;
		}
		
		// 3. Tough Case. What if TWO children? Find the minimum value in the right
		//    sub-tree and remove it. Use that value as the replacement value for
		//    this target.
		BinaryNode<T> minNode = rnode;
		BinaryNode<T> rparent = null;
		while (minNode.getLeftSon() != null) {
			rparent = minNode;
			minNode = minNode.getLeftSon();
		}
		
		// if the right child has no left son, hence it is the next one. Since minNode has
		// no left child, we can just splice ourselves in. Take care about root!
		if (rparent == null) {
			target.value = minNode.value;
			target.right = minNode.right;
			return;
		} 
		
		// when we get here, rparent is parent of the new min node in the right sub-tree
		// once done, minNode is guaranteed to have no left child; this means it has either
		// ZERO or ONE children, so we recursively call removeHelper().
		T minValue = minNode.value;
		removeHelper (minNode, rparent);
		
		// That's it!
		target.value = minValue;
	}
	
	/**
	 * Insert the value into its proper location in the Binary tree.
	 * 
	 * No balancing is performed.
	 * 
	 * @param value   non-null value to be added into the tree.
	 * @exception    IllegalArgumentException if value is null
     * @exception    ClassCastException if the specified object's type prevents it
     *               from being compared to this object.
	 */
	public void insert (T value) {
		if (value == null) {
			throw new IllegalArgumentException ("BinaryTree cannot store 'null' values.");
		}
		
		BinaryNode<T> newNode = construct(value);
		
		// empty tree? This becomes the root.
		if (root == null) {
			setRoot(newNode);
			return;
		}
		
		// compare and place in proper location
		BinaryNode<T> node = root;
		BinaryNode<T> n;
		while (true) {
			int c = value.compareTo(node.value);
			if (c < 0) {
				// insert to the left: If no child then set, otherwise recurse
				if ((n = node.getLeftSon()) == null) {
					node.left = newNode;
					return;
				} else {
					node = n;  // iterate down to this one.
				}
			} else if (c >= 0) {
				// insert to the right: If no child then set, otherwise recurse
				if ((n = node.getRightSon()) == null) {
					node.right = newNode;
					return;
				} else {
					node = n;  // iterate down to this one.
				}				
			}
		}		
	}

	/**
	 * Create string representation of the Tree.  
	 * 
	 * Really only useful for debugging and testCase validation.
	 */
	public String toString() {
		if (root == null) { return "()"; }
		
		return formatNode(root);
	}

	/**
	 * Format the node, recursively.
	 * 
	 * @param node    desired node to be expressed as a String.
	 * @return human readable string
	 */
	private String formatNode(BinaryNode<T> node) {
		BinaryNode<T> n;
		StringBuilder response = new StringBuilder ("(");
		if ((n = node.getLeftSon()) != null) { response.append(formatNode(n)); }
		response.append (node.toString());
		if ((n = node.getRightSon()) != null) { response.append(formatNode(n)); }
		response.append (")");
		
		// flatten.
		return response.toString();
	}
	
	/**
	 * Use in-order traversal over the tree.
	 * @return iterator for values in the Binary Tree return in order.
	 */
	@SuppressWarnings("rawtypes")
	public Iterator<T> inorder() {
		// For those times when an iterator is invoked on empty tree, create
		// the null-iterator object (as static private member) and reuse.
		if (root == null) {
			return empty();
		}

		// so we have a Tree. Do the in-order traversal. HOWEVER these traversals do 
		// not enable for the removal of nodes.
		return new ValueExtractor<T> (new InorderTraversal(root));
	}
	
	/**
	 * Use pre-order traversal over the tree.
	 * @return iterator of values in the binary tree returned in pre-order.
	 */
	@SuppressWarnings("rawtypes")
	public Iterator<T> preorder() {
		// For those times when an iterator is invoked on empty tree, create
		// the null-iterator object (as static private member) and reuse.
		if (root == null) {
			return empty();
		}

		// so we have a Tree. Do the in-order traversal. HOWEVER these traversals do 
		// not enable for the removal of nodes.
		return new ValueExtractor<T> (new  PreorderTraversal(root));
	}
	
	/**
	 * Use post-order traversal over the tree.
	 * @return iterator of values in the binary tree returned in post-order.
	 */
	@SuppressWarnings("rawtypes")
	public Iterator<T> postorder() {
		// For those times when an iterator is invoked on empty tree, create
		// the null-iterator object (as static private member) and reuse.
		if (root == null) {
			return empty();
		}

		// so we have a Tree. Do the in-order traversal. HOWEVER these traversals do 
		// not enable for the removal of nodes.
		return new ValueExtractor<T> (new PostorderTraversal(root));
	}

	/**
	 * Provide useful in-order iteration over the values of the Binary Tree.
	 */
	public Iterator<T> iterator() {
		return inorder();
	}
}
