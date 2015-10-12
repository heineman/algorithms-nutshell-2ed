package algs.model.performance.pq_random;

public class ArrayPQ implements IPQueue {

	int space = 64;
	int count = 0;
	Item[] items;
	
	public ArrayPQ() {
		items = new Item[space];
	}
	
	public void insert(double priority, int item) {
		if (count + 1 == space) {
			space *= 2;
			Item[] newItems = new Item[space];
			System.arraycopy(items, 0, newItems, 0, items.length);
			items = newItems;
		}
		
		// insert in order of priority
		for (int i = 0; i < count-1; i++) {
			if (priority < items[i].priority) {
				int numLeft = count-i;
				System.arraycopy(items, i, items, i+1, numLeft);
				items[i] = new Item (item, priority);
				count++;
				return;
			}
		}
		
		// just add to the end.
		items[count++] = new Item (item, priority);
	}

	public int minimum() {
		int item = items[0].item;
		System.arraycopy (items,1,items,0,count-1);
		count--;
		return item;
	}

}
