package algs.example.gui.problems.rangeQuery;

import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.IMultiPoint;
import algs.model.data.Generator;

/**
 * Helper class to convert {@link IMultiPoint} objects into 
 * {@link SelectableMultiPoint} objects.
 * <p>
 * A {@link SelectableMultiPoint} object can be selected, which enables the GUI
 * to draw the points differently if a point falls within a range query.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class ConvertToSelectable extends Generator<SelectableMultiPoint> {

	/** Inner generator. */
	final Generator<IMultiPoint> pointGen;
	
	/**
	 * Internal is responsible for parameters, not this external wrapper.
	 */
	@Override
	public String[] parameters() {
		return pointGen.parameters();
	}
	
	public ConvertToSelectable(Generator<IMultiPoint> pointGen) {
		this.pointGen = pointGen;
	}

	/** 
	 * Provide reflective behavior to construct instance of generator given 
	 * an array of string arguments. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Generator<SelectableMultiPoint> construct(String[] args) {
		// for the inner generator, find the method which is its constructor
		// and use it to construct things.

		try {
			Class<?> c = pointGen.getClass();
			java.lang.reflect.Method cons = c.getDeclaredMethod("construct", new Class[]{args.getClass()});
			Generator<IMultiPoint> newOne = (Generator<IMultiPoint>) cons.invoke(pointGen, new Object[]{ args});
			return new ConvertToSelectable(newOne);
		} catch (Exception e) {
			return new ConvertToSelectable(pointGen);
		}
	}
	
	/** Invoke inner generator and convert all to IMultiPoint. */
	@Override
	public SelectableMultiPoint[] generate(int size) {
		SelectableMultiPoint[] smp = new SelectableMultiPoint[size];
		
		IMultiPoint mp[] = pointGen.generate(size);
		
		for (int i = 0; i < size; i++) {
			smp[i] = new SelectableMultiPoint(mp[i]);
		}
		
		return smp;
	}
}
