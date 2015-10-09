package algs.blog.searching.search;
/**
 * Interface to define a search for a target element within
 * a collection.
 * <p>
 * This interface has no existence outside of this monthly blog, 
 * which is why it is defined within the default package.
 * 
 * @param <E>   element type.
 * @author George Heineman
 */
public interface ICollectionSearch<E> {
	
	/**
	 * Determine if the given target element exists in a collection.
	 * 
	 * @param target  non-null element to be searched for.
	 * @return <code>true</code> if the target element exists within 
	 *    the base collection; <code>false</code> otherwise.
	 */
	boolean exists(E target);
}
