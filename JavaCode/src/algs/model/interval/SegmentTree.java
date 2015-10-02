package algs.model.interval;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

import algs.model.IInterval;
import algs.model.problems.EnclosingIntervalSearch;
import algs.model.tree.InorderTraversal;


/**
 * Given a fixed set of [1..N] values, the segment tree is a rooted binary tree that 
 * manages intervals [begin,end] on a line, where left &le; begin &lt; end &lt; right.
 * <p>
 * The initial tree is constructed from an initial domain [left,right) which completely 
 * defines the domain from which all segments are drawn.
 * <p>
 * In this base implementation, the SegmentTreeNode only maintains a count
 * with each node. The class is extensible by subclasses of SegmentTreeNode.
 * <p>
 * Note that the remove operation is not capable of removing "parts" of segments
 * from the SegmentTree. That is, if the Tree only contains the segment [2,10) and a
 * request is made to remove segment [4,8), you will not get a tree with segments [2,4)
 * and [8, 10). Further, we only maintain counts with each segment, so there is no way
 * to manipulate the structure of the SegmentTree once it has been created.
 * <p>
 * Note that the constructor to be used is drawn right from the parameterized class, since
 * T extends SegmentTreeNode. If an extended type is used, it MUST provide its own 
 * constructor.
 * 
 * TODO: Really need to investigate this data structure some more. Isn't it possible that 
 * we could add three segments [1,5) and [5, 10) and [1,10) into the tree? And then when it 
 * comes time to remove [1,10) from the Tree, how is it going to be possible to check to 
 * see whether the SegmentTree "contains" the segment [1,10) again? After all, the entire 
 * region is still covered by the other two segments. The reason why this data structure is
 * ultimately useful is because of the subclasses of the {@link SegmentTreeNode} class, which 
 * can be extended to store actual references to the segments rather than counts.
 * <p>
 * The class {@link EnclosingIntervalSearch} shows one effective application of subclasses to this
 * base structure.
 * 
 * @param <T>  Type of the nodes for the tree. T must extend {@link SegmentTreeNode}.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class SegmentTree<T extends SegmentTreeNode<?>> extends AbstractSet<IInterval> implements Set<IInterval> {

	/** Root of the tree. */
	T root;
	
	/** Default constructor, unless overridden during constructor. */
	@SuppressWarnings("rawtypes")
	IConstructor<?> constructor = new IConstructor() {

		public SegmentTreeNode construct(int left, int right) {
			return new SegmentTreeNode (left, right);
		}
	};
	
	/**
	 * Construct the rooted tree to manage segments drawn from the [left, right) domain.
	 * <p>
	 * Note that the tree represents the semi-closed range which includes value 'left',
	 * but does not include value 'right'.
	 * 
	 * @param left    Left-most value allowed for a line segment
	 * @param right   Right-most value allowed for a line segment
	 * @exception     IllegalArgumentException if [left, right) is an invalid interval.
	 */
	public SegmentTree (int left, int right) {
		init(left, right);
	}

	/**
	 * Construct the rooted tree to manage segments drawn from the [left, right) domain.
	 * 
	 * Note that the tree represents the semi-closed range which includes value 'left',
	 * but does not include value 'right'.
	 * 
	 * @param left        Left-most value allowed for a line segment
	 * @param right       Right-most value allowed for a line segment
	 * @param constructor Constructor to use when constructing nodes for this tree.
	 * @exception     IllegalArgumentException if [left, right) is an invalid interval.
	 */
	public SegmentTree (int left, int right, IConstructor<?> constructor) {
		this.constructor = constructor;
		init(left, right);
	}
	
	/** 
	 * Initialize the tree.
	 * 
	 * Manages segments drawn from the [left, right) domain.
	 * 
	 * @param left
	 * @param right
	 */
	@SuppressWarnings("unchecked")
	private void init(int left, int right) {
		// create the root
		root = (T) constructor.construct (left, right);
		
		// recursively expand left and right
		expandLeft(root);
		expandRight(root);
	}
	
	/**
	 * For those SegmentTrees that demand different class of nodes as the base element
	 * of the Tree, this method tells us the constructor being used.
	 * 
	 * @return     {@link IConstructor} object used for construction.
	 */
	public IConstructor<?> getConstructor() {
		return constructor;
	}
	
	/**
	 * Return the root of the tree.
	 * 
	 * @return     the root of the tree.  
	 */ 
	public T getRoot() {
		return root;
	}
	
	/**
	 * Expand the SegmentTree to the right of the given node.
	 * 
	 * Recursively expands both left and right.
	 * 
	 * Overwrites any right child that existed prior to this invocation
	 * @param node
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void expandRight(SegmentTreeNode node) {
		if ((node.rson = computeRightChild(node)) != null) {
			expandLeft(node.rson);
			expandRight(node.rson);
		}
	}

	/**
	 * Expand the SegmentTree to the left of the given node.
	 *
	 * Recursively expands both left and right.
	 * 
	 * Overwrites any left child that existed prior to this invocation
	 * 
	 * @param node
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void expandLeft(SegmentTreeNode node) {
		if ((node.lson = computeLeftChild(node)) != null) {
			expandLeft(node.lson);
			expandRight(node.lson);
		}
	}

	/**
	 * Compute the left child node, if it exists
	 * 
	 * @return the {@link SegmentTreeNode} for the left child, or null if none exists.
	 */
	SegmentTreeNode<?> computeLeftChild(SegmentTreeNode<?> node) {
		if (node.right - node.left > 1) {
			return constructor.construct(node.left, (node.left+node.right)/2);
		}
		
		return null;
	}
	
	/**
	 * Compute the right child node, if it exists
	 * 
	 * @return the Node for the right child, or null if none exists.
	 */
	SegmentTreeNode<?> computeRightChild(SegmentTreeNode<?> node) {
		if (node.right - node.left > 1) {
			return constructor.construct((node.left+node.right)/2, node.right);
		}
		
		return null;
	}
	
	/**
	 * Returns the count of elements in the tree.
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Helper method for size.
	 * 
	 * @param n    root of tree for which we are counting itself and descendants.
	 * 
	 * @return   number of descendants plus 1 for self
	 */
	private int size(SegmentTreeNode<?> n) {
		if (n == null) { return 0; }
		
		return 1 + size(n.lson) + size(n.rson);		
	}

	/**
	 * Create string representation of the Tree.  
	 */
	public String toString() {
		if (root == null) { return "()"; }
		
		return formatNode(root);
	}

	/**
	 * Format the node, recursively.
	 * 
	 * @param node    desired node to be expressed as a String.
	 * @return
	 */
	private String formatNode(SegmentTreeNode<?> node) {
		StringBuilder response = new StringBuilder ("(");
		if (node.lson != null) { response.append(formatNode(node.lson)); }
		response.append (node.toString());
		if (node.rson != null) { response.append(formatNode(node.rson)); }
		response.append (")");
		
		// flatten.
		return response.toString();		
	}

	/**
	 * Used to validate IInterval before being incorporated into this data structure.
	 * 
	 * Note that IInterval may contain invalid (or potentially malicious) methods that do not
	 * satisfy their specifications. At this time, it is not possible to validate the entire
	 * behavior of IInterval. This method is only focused on the structure of the [left, right) range.
	 * 
	 * @param interval   proposed IInterval object to be validated.
	 * 
	 * @exception  IllegalArgumentException  if (interval.getLeft() &ge; interval.getRight())
	 */
	public void checkInterval (IInterval interval) {
		int begin = interval.getLeft();
		int end = interval.getRight();
		
		if (begin >= end) {
			throw new IllegalArgumentException ("Invalid SegmentTreeNode insert: begin (" +
					begin +	") must be strictly less than end (" + end + ")");
		}
	}
	
	/**
	 * Add the given interval to the tree.
	 *
	 * @param    e    Interval to add.
 	 * @exception     IllegalArgumentException  if interval is invalid.
 	 * @return        true on successful addition, false otherwise.
	 */
	@Override
	public boolean add(IInterval e) {
		checkInterval(e);
		
		// Note: root must exist at this point
		return root.insert(e);
	}

	/**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that <tt>(o==null ? e==null :
     * o.equals(e))</tt>, if the collection contains one or more such
     * elements.  Returns <tt>true</tt> if the collection contained the
     * specified element (or equivalently, if the collection changed as a
     * result of the call).<p>
     * 
     * We override this method to improve the efficiency of the implementation.
     * You can remove it to witness the extra performance costs from using
     * the iterator (which for a binary tree is inorder traversal) to remove
     * individual intervals from the tree.
     * 
     * Note the object is not checked for whether it is a valid IInterval.
     */
	@Override
	public boolean remove(Object o) {
		// can't do anything with non-intervals
		if (!(o instanceof IInterval)) { return false; } 
		IInterval e = (IInterval) o;
		
		// empty!
		if (root == null) { return false; }
		
		// Note: root must exist at this point
		return root.remove(e);
	}
	
	/**
	 * Use in-order traversal over the tree.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Iterator<IInterval> iterator() {
		// so we have a Tree. Do the in-order traversal. HOWEVER these traversals do 
		// not enable for the removal of nodes.
		return new InorderTraversal(root);
	}
	
	
	/**
     * This method must be rewritten because it is used to determine if the given IInterval is 
     * stored within the Segment tree. Note that if we didn't overwrite this method, the
     * comparison would be against the internal nodes of the segment tree, not the value(s) being
     * contained by those nodes.
     * <p>
     * Subclasses can override this 'contains' to actually peek into the extra information that
     * is going to be stored by the appropriate nodes.
     * <p>
     * Note the object is not checked for whether it is a valid IInterval.
     * <p>
     * @param o     {@link IInterval} object to be searched for.
     * @exception   ClassCastException if o does not implement the IInterval interface
     * @see java.util.AbstractCollection#contains(Object)
     */
    public boolean contains(Object o) {
    	// can't contain null or any non-IInterval object.
    	if (o == null) return false;
    	if (!(o instanceof IInterval)) { return false; }
    	
		Iterator<IInterval> it = iterator();
	    while (it.hasNext()) {
	    	IInterval intv = it.next();
	    	if (intv.equals((IInterval) o)) {
	    		return true;
	    	}
	    }
    
	    // nope.
		return false;
    }
}
