package algs.blog.searching.tests;
import algs.blog.searching.tree.BalancedTreeSearch;
import junit.framework.TestCase;

public class TestBalancedTree extends TestCase {

	public void testEmpty() {
		BalancedTreeSearch tree = new BalancedTreeSearch();
		tree.insert("eagle");
		tree.insert("cat");
		tree.insert("ant");
		tree.insert("dog");
		tree.insert("ball");
			
		System.out.println(tree.toString());
	}
}
