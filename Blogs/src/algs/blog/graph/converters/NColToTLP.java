package algs.blog.graph.converters;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Tulip graph format.
 * <p>
 * http://www.labri.fr/perso/auber/projects/tulip/tlpformat.php
 * 
 * @author George Heineman
 *
 */
public class NColToTLP {
	/**
	 * In: File args[0]
	 * Out: File args[1]
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			args = new String[2];
			Scanner sc = new Scanner(System.in);
			System.out.print("NCOL from file:" );
			args[0] = sc.nextLine();
			System.out.print("TLP to file:" );
			args[1] = sc.nextLine();
			sc.close();
		}
		
		Scanner sc = new Scanner (new File (args[0]));
		PrintWriter pw = new PrintWriter (new File (args[1]));
		pw.println ("(tlp \"2.0\"");
		pw.println ("(author \"George Heineman \")");

		
		Hashtable<Integer,ArrayList<Integer>> map = new Hashtable<Integer,ArrayList<Integer>>(); 
		
		int maxId = -1;
		while (sc.hasNextLine()) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			sc.nextLine();
			
			ArrayList<Integer> exist = map.get(n1);
			if (exist == null) {
				exist = new ArrayList<Integer>();
				map.put(n1,exist);
			}
			
			exist.add(n2);
			if (n2 > maxId) {
				maxId = n2;
			}
			if (n1 > maxId) {
				maxId = n1;
			}
		}
		sc.close();
		
		// output ALL nodes first. 
		pw.print("(nodes ");
		for (int i = 0; i <= maxId; i++) {
			pw.print(i + " ");
		}
		pw.println(")");
		
		// output
		int edgeid=1;
		for (int i = 0; i <= maxId; i++) {
			ArrayList<Integer> exist = map.get(i);
			if (exist == null || exist.size() == 0) {
				continue;
			}
			
			for (int j = 0; j < exist.size(); j++) {
				pw.println("(edge " + edgeid + " " + i + " " + exist.get(j) + ")");
				edgeid++;
			}
		}
		pw.println (")");
		
		pw.flush();
		pw.close();
		
	}
}
