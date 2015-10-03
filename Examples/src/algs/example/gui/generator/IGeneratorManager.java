package algs.example.gui.generator;

/**
 * Describes interface to be exported by the GeneratorPanel
 * 
 * @param <E>    type of element in the generators.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface IGeneratorManager<E> {
	
	/** Declare that the user has generated a set of elements using a generator. */
	void generate(E[] items);
	
}
