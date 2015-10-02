package algs.debug;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import algs.debug.drawers.DefaultNodeDrawer;
import algs.debug.drawers.GoalNodeDrawer;
import algs.debug.drawers.InitialNodeDrawer;
import algs.debug.drawers.UnexploredNodeDrawer;
import algs.model.list.List;
import algs.model.list.Node;
import algs.model.tree.IVisitor;
import algs.model.tree.debug.BinaryTreeDebugger;

/**
 * Produces a text string that can be used as input to the DOT program
 * [http://www.graphviz.org].
 * <p>
 * If the generated text string is too large it is placed into a file
 * in the current working directory and the file name into this file 
 * is returned as the text string.
 * <p>
 * This class operates using the Visitor Design pattern exclusively. As an 
 * example we show the {@link BinaryTreeDebugger} class, which provides the 
 * logic to display a binary tree (and balanced binary trees). By extending
 * the DottyDebugger class, you only need to declare the type of edge to use
 * by overriding {@link DottyDebugger#edgeType()}. Then access the API provided
 * by DottyDebugger to visit nodes using {@link DottyDebugger#visitNode(IGraphEntity)}
 * and edges using {@link DottyDebugger#visitEdge(IGraphEntity, IGraphEntity)} 
 * methods. In this example, the {@link BinaryTreeDebugger} class implements the
 * {@link IVisitor} interface which means it can be a visitor that traverses each
 * node of the binary tree. When asked to visit a node, {@link IVisitor#visit(algs.model.tree.BinaryNode, algs.model.tree.BinaryNode)},
 * BinaryTreeDebugger first invokes the inherited methods {@link DottyDebugger#visitNode(IGraphEntity)}
 * on node n and its parent, but only if they have not yet been visited before. Note
 * that {@link DottyDebugger#nodes} is inherited from the DottyDebugger class. 
 * 
 * This precaution is needed for arbitrary graphs but you actually need it here
 * since visiting a tree with three nodes (a root with both left and right child) you 
 * need to ensure that you only invoke {@link DottyDebugger#visitNode(IGraphEntity)} using 
 * the parent root node a single time. Once both nodes are visited then it is 
 * possible to visit the edge between them, using {@link DottyDebugger#visitEdge(IGraphEntity, IGraphEntity)}.   
 * 
 * <pre>
 * public class BinaryTreeDebugger extends DottyDebugger implements 
 *     IVisitor, IBalancedVisitor {
 *
 *  // Declare that edges are forward edges (and not undirected).
 *  public String edgeType() { return "dir=forward"; }
 *	
 *  // Visit (parent, child) pair by visit each node individually, then the edge.
 *  public void visit(BinaryNode parent, BinaryNode n) {
 *    // if not yet seen, visit node. Only have to check parent (for root).
 *    if (parent != null &amp;&amp; !nodes.contains(parent)) {
 *      visitNode(parent);
 *    }
 *		
 *    if (!nodes.contains(n)) {
 *      visitNode(n);
 *    }
 *		
 *    if (parent != null) {
 *      visitEdge(parent, n);		
 *    }
 *  }
 *
 *  // Corresponding method for balanced binary trees.
 *  public void visit(BalancedBinaryNode parent, BalancedBinaryNode n) {
 *    // if not yet seen, visit node. Only have to check parent (for root).
 *    if (parent != null &amp;&amp; !nodes.contains(parent)) {
 *      visitNode(parent);
 *    }
 *	
 *    if (!nodes.contains(n)) {
 *      visitNode(n);
 *    }
 *		
 *    if (parent != null) {
 *      visitEdge(parent, n);		
 *    }
 *  }
 * }
 * </pre>
 * 
 * The full set of available debugger methods can be found in the {@link IDebugSearch}
 * interface. They are:
 * <ul>
 * <li>Visit methods -- IDebugSearch{@link #visitNode(IGraphEntity)} and {@link IDebugSearch#visitEdge(IGraphEntity, IGraphEntity)})
 *   allow the debugger to add a node to the resulting output or an edge. Be sure to only invoke
 *   {@link IDebugSearch#visitEdge(IGraphEntity, IGraphEntity)} if you have previously
 *   visited both nodes in the edge relationship.
 * <li>Mark node methods -- {@link IDebugSearch#markStart(IGraphEntity)} identifies the node considered to be
 *   the "start" of the output. This typically is drawn as the top-most node in the graph, which works
 *   well for search trees and game tree. {@link IDebugSearch#markGoal(IGraphEntity)} is the corresponding
 *   method to declare the target node which is often appropriate for search trees. In an output graph
 *   there may be only a single start and goal node identified using these methods. 
 *   {@link IDebugSearch#markUnexplored(IGraphEntity)} identifies a node which can be drawn in grayscale
 *   signifying that the node was (for some reason) not explored or considered for use. An additional
 *   mark method, {@link IDebugSearch#markUnexplored(IGraphEntity)} draws the node with a full grayscale
 *   background that signifies a node that may have been considered or processed but no longer is of use.
 *   This last mark method ultimately was never used by any existing debugger we wrote.
 * <li>Mark edge -- An edge can be marked which draws it in bold width, which typically signifies in a 
 *   search tree that the edge is part of some solution. Non-marked edges are drawn in default thin line
 *   width.     
 * <li>Complete method -- once the debugger has provided all of its information, it
 *   invokes {@link IDebugSearch#complete()} which might have some final computations
 *   that need to be performed.
 * </ul>
 * <p>
 * There are several ways to configure this class for your needs.
 * <ol>
 * <li> {@link #ordering(String)} is used to layout the nodes. Choose one
 *   of two values to select {@link #DepthFirstOrdering} ({@value #DepthFirstOrdering}) or
 *     {@link #BreadthFirstOrdering} ({@value #BreadthFirstOrdering}).
 * <li> {@link #rank(String)} is used to change orientation. Set to "LR" if the 
 *   ordering should be properly done from left to right instead of from top to bottom.
 * </ol>
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DottyDebugger implements IDebugSearch {
	public static int _ctr = 0;
	
	/** Start key. */
	public String start;
	
	/** Goal key. */
	public String goal;

	/** Nodes that appear in the search output but which were never explored. */
	public ArrayList<String> unexplored = new ArrayList<String>();
	
	/** Nodes. */
	protected Hashtable<String,IGraphEntity> nodes = new Hashtable<String,IGraphEntity>(); 
	
	/** Edges. */
	protected List<EdgePair> edges = new List<EdgePair>(); 
	
	/** Max string size to return from getInputString(). */
	public static int TooLarge = 1024;
	
	/** Ordering of children. */
	String ordering = BreadthFirstOrdering;
	
	/** Rank of the drawing (can be changed to LeftRight). */
	String optionalRank = "";

	/** These fonts are embeddable within PDF documents. */
	public static final int defaultFontSize = 10;
	public static final String defaultFontName = "TimesNewRomanPSMT";
	
	/** Ordering types. */
	public static final String DepthFirstOrdering = "in";
	public static final String BreadthFirstOrdering = "out";
	
	/** Uniq file names for output. */
	private static int fileCounter = 1;
	
	/** Default node drawer. */
	private INodeDrawer defaultNodeDrawer = new DefaultNodeDrawer();
	
	/** Unexplored nodes drawn with this drawer. */
	private INodeDrawer unexploredNodeDrawer = new UnexploredNodeDrawer();
	
	/**
	 * Ordering of children. IN for breadth-first, OUT for depth-first.
	 * 
	 * @param ord  either DepthFirstOrdering or BreadthFirstOrdering
	 */
	public void ordering(String ord) {
		ordering = ord;
	}
	
	/**
	 * Rank orientation of the figure.
	 * 
	 * @param r  "LR" to make the graph left to right
	 */
	public void rank(String r) {
		optionalRank = "rankdir = " + r + ";";
	}
	
	/**
	 * Return the number of nodes in the generated tree.
	 * @return number of nodes in the tree.
	 */
	public int numNodes() {
		return nodes.size();
	}
	
	/** 
	 * Default to having nodes with complex record shapes.
	 * 
	 * Subclasses should override as needed.
	 * @return string type representing the node.
	 */
	public String nodeType() {
		return "record";
	}
	
	/** 
	 * Edges should be undirected.
	 * 
	 * Subclasses should override as needed.
	 * @return   representation of edge type as undirected
	 */
	public String edgeType() {
		return "dir=none";
	}
	
	/** 
	 * Resulting string available after calling complete. 
	 *
	 * Note if there are more than {@link #TooLarge} nodes in the tree, a File is 
	 * created and that is returned instead of this string.
	 * @return contents of entire DOTTY input for processing.
	 */
	public String getInputString() {
		StringBuilder graph = new StringBuilder();
		
		String s = "digraph g {\n" + optionalRank + "\nordering=" + ordering;
		
		// select default fonts, too.
		s += ";\nnode [ shape=" + nodeType() + " style=solid fontsize=" + defaultFontSize + " fontname=\"" + defaultFontName + "\"];\nedge [ " + edgeType() + "];\n"; 
		graph.append(s);

		// set start, if so selected.
		if (start != null && nodes.get(start) != null) {
			addNode (graph, nodes.get(start), new InitialNodeDrawer());
			graph.append("node [color=black];\n");   // back to normal.
		}
		
		// now output all nodes, in any order, until the goal is reached.
		for (Iterator<String> it = nodes.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			IGraphEntity target = nodes.get(key);
			if (key != start && key != goal) {
				if (unexplored.contains(key)) {
				 	addNode (graph, target, unexploredNodeDrawer);
				} else {
					addNode(graph, target, defaultNodeDrawer);
				}
				graph.append ("\n");
			}
		}
		
		// now output all edges, in any order. If an edge has a label, then
		// it is inserted, otherwise label='' is added
		Node<EdgePair> node = edges.head();
		boolean isMarked = false;
		while (node != null) {
			EdgePair ep = node.value();
			
			// change label formatting.
			if (isMarked != ep.marked) {
				if (ep.marked) {
					graph.append("edge [style=bold];\n");
					isMarked = true;
				} else {
					graph.append("edge [style=solid];\n");
					isMarked = false;
				}
			}
			
			// here comes the edge
			graph.append (ep.startKey + " -> " + ep.endKey);
			
			if (ep.isLabeled()) {
				graph.append(" [label=" + ep.label + "]"); 
			}
			
			// close out edge
			graph.append (";\n");
			
			node = node.next();
		}
		
		// finally output goal state, in special way, if it is present.
		if (goal != null) {
			addNode (graph, nodes.get(goal), new GoalNodeDrawer());
			graph.append ("\n");
		}
		
		graph.append("}");
		
		if (numNodes() > TooLarge) {
			try {
				java.io.File f = new java.io.File ("DottyOutput" + fileCounter);
				fileCounter++;
				java.io.PrintWriter pw = new java.io.PrintWriter (f);
				pw.write(graph.toString());
				pw.close();
				return f.getAbsolutePath();
			} catch (java.io.IOException _e) {
				// default to original behavior below
			}
		} 
	
		// default behavior is to return original string.
		return graph.toString();
	}

	private void addNode(StringBuilder graph, IGraphEntity node, INodeDrawer drawer) {
		graph.append(getKey(node) + " " + drawer.draw(node));		
	}
	
	/** All is ready to be processed. This debugger does not take advantage of this feature. */
	public void complete() { }

	/** 
	 * Helper function to reverse locate the key. 
	 *
	 * @param  value   entity for which we want to retrieve its key.
	 * @return String associated with given entity value
	 */
	protected String getKey (IGraphEntity value) {
		for (Iterator<String> it = nodes.keySet().iterator(); it.hasNext(); ) {
			String key = it.next();
			IGraphEntity target = nodes.get(key);
			if (target == value) {
				return key;
			}
		}
		
		return null;
	}
	
	/** 
	 * Mark the start node. 
	 * 
	 * @param st    designated start node.
	 */
	public void markStart(IGraphEntity st) {
		start = getKey(st);
	}
	
	/**
	 * Mark the goal node found. 
 	 * 
	 * @param gl    designated goal node. 
	 */
	public void markGoal(IGraphEntity gl) {
		goal = getKey(gl);
	}
	
	/**
	 * Mark node as being unexplored.
	 * 
	 * @param n    node that is not explored.
	 */
	public void markUnexplored(IGraphEntity n) {
		unexplored.add(getKey(n));
	}
	
	/** 
	 * Mark node as being visited. 
	 * 
	 * @param n   node being visited. 
	 */
	public void visitNode(IGraphEntity n) {
		String tag = "n" + (_ctr++);
		nodes.put (tag,n);
	}
	
	/** 
	 * If called multiple times, multiple edges result. 
	 * 
	 * @param n1  source of edge
	 * @param n2  target of edge
	 */
	public void visitEdge(IGraphEntity n1, IGraphEntity n2) {
		String k1 = getKey(n1);
		String k2 = getKey(n2);
		edges.append(new EdgePair (k1, k2));
	}
	
	/** 
	 * Assign label to the edge upon creation. 
	 * @param n1     source of edge
	 * @param n2     target of edge
	 * @param label  label for edge
	 */
	public void visitEdge(IGraphEntity n1, IGraphEntity n2, String label) {
		String k1 = getKey(n1);
		String k2 = getKey(n2);
		EdgePair ep = new EdgePair (k1, k2);
		ep.label(label);
		edges.append(ep);
	}

	/** 
	 * Mark special edges in bold. 
	 *
	 * @param n1  source of edge
	 * @param n2  target of edge
	 */
	public void markEdge(IGraphEntity n1 ,IGraphEntity n2) {
		String k1 = getKey(n1);
		String k2 = getKey(n2);
		Node<EdgePair> n = edges.head();
		while (n != null) {
			EdgePair ep = n.value();
			if (ep.startKey.equals(k1) && ep.endKey.equals(k2)) {
				ep.mark();
				break;
			}
			
			n = n.next();
		}
	}


}
