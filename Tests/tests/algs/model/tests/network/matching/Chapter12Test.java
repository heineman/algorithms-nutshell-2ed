package algs.model.tests.network.matching;

import java.util.Iterator;

import org.junit.Test;

import algs.model.network.matching.BipartiteMatching;
import algs.model.network.matching.Pair;

import junit.framework.TestCase;

public class Chapter12Test extends TestCase {

	@Test
	public void test() {
		String[]setS = {"a", "b", "c", "d", "e" };
		String[]setT = {"1", "2", "3", "4", "5" };
		String[][] edges = new String[][] {
			{"a", "1"},
			{"a", "2"},
			{"b", "1"},
			{"b", "3"},
			{"b", "4"},
			{"c", "2"},
			{"c", "5"},
			{"d", "2"},
			{"d", "4"},
			{"e", "5"},
		};
		Pair[] solution = new Pair[] {
				new Pair ("a", "1"),
				new Pair ("b", "3"),
				new Pair ("c", "2"),
				new Pair ("d", "4"),
				new Pair ("e", "5"),
		};
		
		BipartiteMatching mc = new BipartiteMatching(setS, setT, edges);
		Iterator<Pair> pairs = mc.compute();
		int ct = 0;
		while (pairs.hasNext()) {
			System.out.println (pairs.next());
			ct++;
		}
		
		assertEquals (solution.length, ct);
	}
	
	@Test
	public void testErrorConditionsBipartiteMatching() {
		String[]setS = {"a", "b", "c", "d", };
		String[]setBadT = {"1", "2", "3", "4", "d" };
		String[]setOKT = {"1", "2", "3", "4", "5" };
		String[]setT2 = {"1", "2", "3" };
		String[]setT3 = {"X", "Y", "Z", "W", };
		
		String[][] badEdges = new String[][] {
				{"a", "1", "43"},
				{"a", "2"},
				{"b", "1"},
				{"b", "3"},
				{"b", "4"},
				{"c", "2"},
				{"c", "5"},
				{"d", "d"},
				{"d", "4"},
				{"e", "5"},
			};
		
		String[][] edges = new String[][] {
			{"a", "1"},
			{"a", "2"},
			{"b", "1"},
			{"b", "3"},
			{"b", "4"},
			{"c", "2"},
			{"c", "5"},
			{"d", "d"},
			{"d", "4"},
			{"e", "5"},
		};
		
		try {
			new BipartiteMatching(setS, setBadT, edges);
			fail ("Sets are not disjoint. Should fail");
		} catch (RuntimeException rte) {
			// ok
		}

		try {
			new BipartiteMatching(setBadT, setS, edges);
			fail ("Sets are not disjoint. Should fail");
		} catch (RuntimeException rte) {
			// ok
		}
		
		
		try {
			new BipartiteMatching(setS, setT2, edges);
			fail ("Sets are wrong size. Should fail");
		} catch (RuntimeException rte) {
			// ok
		}
		

		try {
			new BipartiteMatching(setS, setT3, edges);
			fail ("Edges are not drawn from the set. Should fail");
		} catch (RuntimeException rte) {
			// ok
		}
		
		try {
			new BipartiteMatching(setS, setOKT, badEdges);
			fail ("Edges are not pairs. Should fail");
		} catch (RuntimeException rte) {
			// ok
		}
	}
	
}
