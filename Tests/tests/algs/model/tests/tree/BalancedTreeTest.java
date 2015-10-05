package algs.model.tests.tree;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.tree.BalancedBinaryNode;
import algs.model.tree.BalancedTree;
import algs.model.twod.TwoDPoint;

public class BalancedTreeTest {


	@Test
	public void testNullEntries() {
		BalancedTree<String,String> bt = new BalancedTree<String,String>();
		assertEquals ("", bt.toString());

		try {
			bt.insert(null, null);
			fail ("BalancedTree should disallow null key values with default comparator.");
		} catch (NullPointerException npe) {
			// prevent inserts of null values with default comparator.
		}
		
		assertEquals (0, bt.size());
		
		BalancedBinaryNode<String, String> entry = bt.getEntry(null);
		assertTrue(null == entry);
		
		BalancedTree<IPoint,String> bt2 = new BalancedTree<IPoint,String>(IPoint.xy_sorter); 
		
		bt2.insert(null, "anotherOne");
		BalancedBinaryNode<IPoint,String> entry2 = bt2.getEntry(null);
		assertEquals ("anotherOne", entry2.value());

		bt2.insert(new TwoDPoint(10, 13), "testing");
		bt2.insert(new TwoDPoint(4,8), null);
		
		entry2 = bt2.getEntry(new TwoDPoint(4,8));
		assertTrue (null == entry2.value());
	}
	
	@Test
	public void testBalancedTree() {
		BalancedTree<Integer,String> bt = new BalancedTree<Integer,String>();
		assertEquals ("", bt.toString());

		assertFalse (bt.iterator().hasNext());

		// doesn't matter what is passed in: when tree is empty, these are all null.
		assertTrue (null == bt.pred(null));
		assertTrue (null == bt.getPrecedingEntry(113));
		assertTrue (null == bt.getSuccessorEntry(113));
		assertTrue (null == bt.minimum());
		assertTrue (null == bt.root());
		assertTrue (null == bt.successor(null));		
		
		// default comparator becomes null.
		assertTrue (null == bt.comparator());
		
		try {
			bt.iterator().remove();
			fail ("Should prevent remove using iterator.");
		} catch (Exception e) {

		}

		// dummy for checing visitation on empty tree
		bt.accept(null);

		String vals[] = {"delta", "echo", "foxtrot", "alpha", "bravo", "charlie",  "golf",
				"romeo", "sierra", "tango", "hotel", "india", 
				"mike",  "papa", "quebec",
				"juliet", "kilo", "lima","uniform", "victor", "whiskey",
				"xray", "november", "oscar", "yankee", "zulu"};

		// insert them all
		for (String s : vals) {
			bt.insert (s.hashCode(), s);
		}
		assertEquals ("(((((-1148903753=juliet)-948714977=quebec)-902523150=sierra((-816693433=victor(-737875419=yankee))-671677902=foxtrot(-286926412=uniform)))3107365=echo(((3178594=golf)3292001=kilo(3321809=lima))3351542=mike((3433378=papa)3687602=xray((3750404=zulu)92909918=alpha))))93998218=bravo(((95468472=delta)99467700=hotel(100346167=india(106035056=oscar)))108696186=romeo((110127177=tango)739067762=charlie(1316689092=whiskey(1639129394=november)))))", bt.toString());

		// search should all succeed
		int badOne = 99;
		for (String s : vals) {
			assertEquals (s, bt.search(s.hashCode()));
			if (s.hashCode() == badOne) {
				fail ("Random string had same hashcode. Change badOne!");
			}
		}
		assertTrue (null == bt.search(badOne));

		// simple check
		String iterOrder[] = new String[vals.length];
		int idx = 0;
		for (Iterator<String> it = bt.iterator(); it.hasNext(); ) {
			String s = it.next();
			iterOrder [idx++] = s;
			BalancedBinaryNode<Integer, String> node = bt.getEntry(s.hashCode());
			assertEquals (s, node.value());
		}

		// now test out preceding entries
		for (int i = 0; i < iterOrder.length; i++) {
			BalancedBinaryNode<Integer, String> prev = bt.getPrecedingEntry(iterOrder[i].hashCode());
			if (i == 0) { 
				assertTrue (prev == null);
			} else {
				assertEquals(iterOrder[i-1], prev.value());
			}
		}

		// now delete one by one FROM original LIST to make sure we try different orders.
		int sz = bt.size();
		for (String s: vals) {
			bt.remove(s.hashCode());
			assertEquals (sz-1, bt.size());
			sz--;

		}
	}

	@Test
	public void testDuplicates() {
		BalancedTree<Integer,String> bt = new BalancedTree<Integer,String>();
		assertTrue (bt.setAllowDuplicates(true));
		assertTrue (bt.allowDuplicates());
		
		String vals[] = new String[] { "echo", "charlie", "delta" };
		for (String s : vals) {
			bt.insert(117, s);
		}

		assertEquals ("((117=delta)117=charlie(117=echo))", bt.toString());

		String extract[] = new String[vals.length];
		for (int i = 0; i < extract.length; i++) {
			extract[i] = bt.remove(117);

			boolean found = false;
			for (String s : vals) {
				if (s.equals (extract[i])) { found = true; }
			}

			assertTrue (found);
		}

		// empty
		assertEquals (0, bt.size());
	}

	@Test
	public void testMultiple() {
		BalancedTree<Integer,String> bt = new BalancedTree<Integer,String>();
		assertFalse (bt.allowDuplicates());
		bt.insert (13, "sample");
		assertEquals ("sample", bt.search(13));
		
		// note unable to change after elements in tree
		assertFalse (bt.setAllowDuplicates(true));
		
		bt.insert (13, "testing");
		assertEquals ("testing", bt.search(13));

		assertEquals (1, bt.size());
		assertEquals ("testing", bt.minimum());
		assertEquals (0, bt.size());
		
		bt.insert (13, "testing");
		assertEquals (1, bt.size());
		bt.clear();
		assertEquals (0, bt.size());
	}

}
