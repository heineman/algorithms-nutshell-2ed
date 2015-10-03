package algs.example.gui.problems.segmentIntersection.model;

import java.util.Hashtable;
import algs.example.gui.model.IModelUpdated;
import algs.model.IPoint;
import algs.model.list.List;
import algs.model.problems.segmentIntersection.IntersectionDetection;

/**
 * Contains the model relevant to the intersection problem.
 * 
 * At heart we rely on line segment intersection. However, since any 
 * set of objects can be modeled as a (rough) set of line segments, we make
 * it possible to produce a truly generic approach towards intersecting entities.
 * 
 * To support the dynamic intersection of additional elements, the model keeps
 * track of an "active entity" which is currently being drawn in real time on 
 * the screen.
 * 
 * @param <E>  The entity type of the underlying model.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class Model<E> extends algs.example.gui.model.Model<E> {

	/** intersections computed by the detector. */
	protected Hashtable<IPoint, List<E>> intersections;
	
	/** store most recently used intersection detection. */
	IntersectionDetection detector;
	
	/**
	 * Set the items for the model. 
	 * 
	 * Note that there is room set aside for the dynamic entity, and this
	 * is handled by appropriate subclass. 
	 * 
	 * @param items
	 */
	public abstract void setItems(E[] items);
	
	/** Return the located intersections, once algorithm has computed them. */
	public Hashtable<IPoint,List<E>> intersections() {
		return intersections;
	}
	
	/** 
	 * Compute the intersections in the model using the given algorithm.
	 * 
	 * @param det   detection algorithm to use
	 * @return      number of detected intersections.
	 */
	public int computeIntersections(IntersectionDetection det) {
		detector = det;
		return applyIntersections();
	}
	
	/** Real work is done in subclasses. */
	protected abstract int applyIntersections();

	/**
	 * Set the dynamic entity being managed by the model.
	 * 
	 * @param e    Set new entry, or null to clear/remove old one
	 */
	public void setDynamicEntity (E e) {
		// no items to contend with! Must leave now.
		if (items == null) {
			return;
		}
		
		if (e == null) {
			// truly no change
			if (items[0] == defaultEntity()) { return; }
			
			items[0] = defaultEntity();
		} else {
			items[0] = e;
		}
		
		// reapply intersection algorithm
		applyIntersections();
		
		//alert listeners
		modelUpdated();
	}
	
	/** 
	 * Returns the dynamic entity, or null if none set.
	 * 
	 * Note that the defaultEntity is never exposed outside of the model. 
	 * @return null if no dynamic entity, or the actual entity.
	 */
	public E getDynamicEntity() {
		if (items[0] == defaultEntity()) {
			return null;
		}
		
		return items[0];
	}
	
	/**
	 * Return the default entity, which shouldn't possibly intersect with any
	 * other entity in items[] 
	 */
	public abstract E defaultEntity();

	/** Return (in English) the type of object being compared for intersections. */
	public abstract String types();
	
	/** Register a listener with the model. */
	public void setListener (IModelUpdated<E> list) {
		this.listener = list;
	}

	/** Returns the time it took algorithm to compute intersection. */
	public String algorithmTime() {
		if (detector == null) { return ""; }
		
		return "" + detector.time();
	}
	
}