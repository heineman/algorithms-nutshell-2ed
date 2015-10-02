package algs.model.data.nd;

import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.nd.Hyperpoint;

/**
 * Wrapper generator that converts IPoint arrays into IMultiPoint object arrays.
 * <p>
 * Useful to enable IPoint generators to be reused for KD tree examples.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ConvertToND extends Generator<IMultiPoint> {

	/** Inner generator. */
	final Generator<IPoint> pointGen;
	
	/** Parameters for wrappeer are derived from inner generator. */
	@Override
	public String[] parameters() {
		return pointGen.parameters();
	}
	
	public ConvertToND(Generator<IPoint> pointGen) {
		this.pointGen = pointGen;
	}

	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Generator<IMultiPoint> construct(String[] args) {
		// for the inner generator, find the method which is its constructor
		// and use it to construct things.

		try {
			Class<?> c = pointGen.getClass();
			java.lang.reflect.Method cons = c.getDeclaredMethod("construct", new Class[]{args.getClass()});
			Generator<IPoint> newOne = (Generator<IPoint>) cons.invoke(pointGen, new Object[]{ args});
			return new ConvertToND(newOne);
		} catch (Exception e) {
			return new ConvertToND(pointGen);
		}
	}
	
	/**
	 * Utility function to construct an IMultiPoint[] array from an IPoint[] array.
	 *
	 * @param pts   initial array of {@link IPoint} objects
	 * @return      array of {@link IMultiPoint} equivalent objects (using {@link Hyperpoint} implementation).
	 */
	public static IMultiPoint[] convert(IPoint[] pts) {
		IMultiPoint[] ret = new IMultiPoint[pts.length];
		
		double[] params = new double[2];
		for (int i = 0; i < pts.length; i++) {
			params[0] = pts[i].getX();
			params[1] = pts[i].getY();
			
			ret[i] = new Hyperpoint(params);
		}
		
		return ret;
	}

	/** Invoke inner generator and convert all to IMultiPoint. */
	@Override
	public IMultiPoint[] generate(int size) {
		return convert (pointGen.generate(size));
	}


}
