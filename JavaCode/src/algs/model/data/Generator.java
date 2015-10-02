package algs.model.data;


/**
 * Generator of a fixed number of elements.
 * <p>
 * @param <E>   the type of entity being generated.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class Generator<E> {
	
	/** 
	 * Generate a set of elements according to specialized criteria defined by
	 * the subclass.
	 * <p>
	 * There is no guarantee that the elements are unique.
	 * 
	 * @param size   The number of elements to be created.
	 * @return       array of elements constructed.
	 */
	public abstract E[] generate (int size);
		
	/**
	 * Given the string arguments, construct the desired generator as specified
	 * by the appropriate sub-class.
	 *  
	 * @param args   sub-class specific arguments to use
	 * @return       {@link Generator} object constructed from the given parameters.
	 */
	public abstract Generator<E> construct (String[]args);
	
	/** 
	 * Declares the name of the parameters used when constructing the generator
	 * in order from left to right.
	 * @return array of String parameters.
	 */
	public abstract String[] parameters();
	
	/**
	 * Respond with the name of the appropriate subclass.
	 * @return    class name of the generator.
	 */
	public String toString() {
		return getClass().getName();
	}
}
