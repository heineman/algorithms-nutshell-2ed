package algs.model.tests.network.matching;

import java.util.Iterator;

import org.junit.Test;

import algs.model.network.matching.BipartiteMatching;
import algs.model.network.matching.Pair;

import junit.framework.TestCase;

public class CormenTest extends TestCase {

	@Test
	public void test() {
		String[]setS = {"L1", "L2", "L3", "L4", "L5" };
		String[]setT = {"R1", "R2", "R3", "R4" };
		String[][] edges = new String[][] {
			{"L1", "R1"},
			{"L2", "R1"},
			{"L2", "R3"},
			{"L3", "R2"},
			{"L3", "R3"},
			{"L3", "R4"},
			{"L4", "R3"},
			{"L5", "R3"}
		};
		
		Pair[] solution = new Pair[] {
				new Pair ("L1", "R1"),
				new Pair ("L2", "R3"),
				new Pair ("L3", "R2")
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
	
}
