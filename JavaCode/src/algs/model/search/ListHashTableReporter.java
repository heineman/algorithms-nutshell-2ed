package algs.model.search;

import java.util.LinkedList;

/**
 * Generate useful statistics about ListHashTable object.
 * 
 *    Slot Usage
 *    Load Factor
 *    Weighted average of chains [min, max, avg]
 * 
 * @param <V>   type of object being placed into the HashTable
 * 
 * @author George Heineman
 * @author Gary Pollice
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ListHashTableReporter<V> {
	
	/** HashTable under inspection. */
	ListHashTable<V> table;
	
	/** Need to keep track of ints used. */
	int [] intHashTable;
	
	/** Number of entries in the table. */
	int numEntries;
	
	/** 
	 * Load up the HashTable for this reporter.
	 * @param ht    initial state to use for reporter. 
	 */
	public ListHashTableReporter (ListHashTable<V> ht) {
		this.table = ht;
	}
	
	/**
	 * Generate and return the collision report.
	 * 
	 * @return readable string as collision report. 
	 */
	public String report() {
		StringBuffer report = new StringBuffer();
		intHashTable = new int[table.tableSize];
		
		calculateSlotUsage();
		
		int max = maxCollisions();
		int[] slotUsageCount = new int[max+1];
		for (int i : intHashTable) {
			slotUsageCount[i]++;
		}
		
		report.append("Total number of entries: " + numEntries + "\n");
		report.append("   Load factor:" + (numEntries/(1.0f*table.tableSize) + "\n\n"));
		float weightedAvg = 0.0f;
		int numNonEmpty = 0;
		
		report.append("Number of hits\tNumber of slots\n");
		for (int j = 0; j <= max; j++) {
			if (slotUsageCount[j] != 0) {
				report.append(j + "\t" + slotUsageCount[j] + "\n");
				
				// only increase if this bin actually has strings.
				if (j != 0) {
					weightedAvg += slotUsageCount[j]*j;
					numNonEmpty += slotUsageCount[j];
				}
			}
		}
		weightedAvg /= numNonEmpty;
		report.append("\n  Slot statistics: min=" + minCollisions() + ", max=" + max + ", weightedAvg:" + weightedAvg);
		
		return report.toString();
	}
	
	/** Compute usages based upon the table. */
	private void calculateSlotUsage() {

		numEntries = 0;
		for (int i = 0; i < table.table.length; i++) {
			LinkedList<V> list = (LinkedList<V>) table.table[i];
			
			if (list != null) {
				numEntries += list.size();
				intHashTable[i] += list.size();
			}
		}
	}
	
	/** Return minimum number of collisions in the HashTable. */
	private int minCollisions()	{
		int min = table.tableSize;
		for (int i : intHashTable) {
			if (i < min) {
				min = i;
			}
		}
		
		return min;
	}
	
	/** Return maximum number of collisions in the HashTable. */
	private int maxCollisions()	{
		int max = 0;
		for (int i : intHashTable) {
			if (i > max) {
				max = i;
			}
		}
		
		return max;
	}

}
