package algs.model.tests.network.matching;

import java.util.Iterator;

import org.junit.Test;

import algs.model.network.matching.BipartiteMatching;
import algs.model.network.matching.Pair;

import junit.framework.TestCase;

public class DrozdekTest extends TestCase {

	@Test
	public void test() {
		String[]setS = {"u1", "u2", "u3", "u4", "u5", "u6" };
		String[]setT = {"v1", "v2", "v3", "v4", "v5", "v6" };
		String[][] edges = new String[][] {
			{"u1", "v4"},
			{"u1", "v2"},
			{"u2", "v1"},
			{"u2", "v2"},
			{"u3", "v2"},
			{"u3", "v3"},
			{"u3", "v4"},
			{"u3", "v5"},
			{"u4", "v3"},
			{"u4", "v4"},
			{"u4", "v5"},
			{"u5", "v2"},
			{"u5", "v6"},
			{"u6", "v6"},
		};
		
		Pair[] solution = new Pair[] {
				new Pair ("u1", "v4"),
				new Pair ("u2", "v1"),
				new Pair ("u3", "v3"),
				new Pair ("u4", "v5"),
				new Pair ("u5", "v2"),
				new Pair ("u6", "v6"),
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
