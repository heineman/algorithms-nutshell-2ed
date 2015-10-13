package algs.model.performance.pq_random;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import algs.model.tests.common.TrialSuite;

public class DriverMain {
	
	public static Item sentinel = new Item (-1,-1);
	
	public static void main (String []args) throws FileNotFoundException {
		// a sentinel-added value is a 'dmn' request.
		ArrayList<Item> priorities = new ArrayList<Item>();
		
		// pq.id.100K1.1
		// pq.id.100K4.1
		// pq.id.10K1.2
		// pqsort.100K.3
		
		// do this to retrieve large cases via internet.
		//java.net.URL u;
		//Scanner s = new Scanner (u.openStream());
		
		File f = new File ("tests\\algs\\model\\performance\\pq_random\\pqsort.1K.1");
		Scanner sc = new Scanner (f);
		while (sc.hasNext()) {
			String s = sc.nextLine();
			if (s.startsWith("pqh")) continue;
			if (s.startsWith("com")) continue;
			
			if (s.startsWith("dmn")) {
				priorities.add(sentinel);
			} else {
				StringTokenizer st = new StringTokenizer(s, " ");
				st.nextToken();
				Double d = Double.parseDouble(st.nextToken());
				Integer i = Integer.parseInt(st.nextToken());
				priorities.add(new Item (i, d));
			}
		}
		sc.close();
		
		// Now ready to measure PQ.
		IPQueue ipq = new ArrayPQ();
		//IPQueue ipq = new BalancedTreePQ();
		
		TrialSuite minTS = new TrialSuite ();
		TrialSuite insTS = new TrialSuite ();
		long n0 = System.currentTimeMillis();
		for (int i = 0; i < priorities.size(); i++) {
			Item it = priorities.get(i);
			if (it == sentinel) {
				long n1 = System.currentTimeMillis();
				ipq.minimum();
				long n2 = System.currentTimeMillis();
				minTS.addTrial(1, n1, n2);
			} else {
				long n1 = System.currentTimeMillis();
				ipq.insert(it.priority, it.item);
				long n2 = System.currentTimeMillis();
				insTS.addTrial(1, n1, n2);
			}
		}
		long e0 = System.currentTimeMillis();
		System.out.println ((e0 - n0) + " milliseconds for completion.");
		System.out.println ("min");
		System.out.println (minTS.computeTable());
		System.out.println ("insert");
		System.out.println (insTS.computeTable());
	}
}
