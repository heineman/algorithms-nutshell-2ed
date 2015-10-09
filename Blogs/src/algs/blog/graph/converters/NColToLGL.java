package algs.blog.graph.converters;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The LGL package draws trees better in LGL format, but you cannot generate
 * this formation while the graph is being drawn, since it would consume too
 * much space. This converter function converts from NCOL to LGL
 * 
 * http://bioinformatics.icmb.utexas.edu/lgl/
 * 
 * @author George Heineman
 *
 */
public class NColToLGL {
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
			System.out.print("LGL to file:" );
			args[1] = sc.nextLine();
			sc.close();
		}
		
		Scanner sc = new Scanner (new File (args[0]));
		PrintWriter pw = new PrintWriter (new File (args[1]));
		
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
		
		// output
		for (int i = 0; i <= maxId; i++) {
			ArrayList<Integer> exist = map.get(i);
			if (exist == null || exist.size() == 0) {
				continue;
			}
			pw.println("#" + i);
			for (int j = 0; j < exist.size(); j++) {
				pw.println (exist.get(j));
			}
		}
		
		pw.flush();
		pw.close();
		
	}
}
