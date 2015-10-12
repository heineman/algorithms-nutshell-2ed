package algs.model.performance.pq_random;

public class Item {
	final int item;
	final double priority;
	
	public Item (int item, double p) {
		this.item = item;
		this.priority = p;
	}
	
	public boolean equals (Object o) {
		if (o == null) return false;
		if (o instanceof Item) {
			Item i = (Item) o;
			return i.item == item && i.priority == priority;
		}
		
		return false;
	}
	public String toString () { return item + ":" + priority; }
}
