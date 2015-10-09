package algs.blog.searching.special;

import algs.blog.searching.search.ICollectionSearch;

/**
 * This base class enables the {@link SpecialHashKeys2} and {@link SpecialHashKeys3}
 * classes to reuse code.
 * 
 * @author George Heineman
 */
public abstract class SpecialHashbasedSearch implements ICollectionSearch<String> {

	/** Define API for inserting key into the collection. */
	public abstract int insert(String elem);
	
	/** Reset this structure for future builds and/or searches. */
	public abstract void reset();
	
	/** Expose information about this collection. */
	public abstract String info();

}
