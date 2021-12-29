package algs.model.tests.kdtree;

import org.junit.Test;

import algs.model.kdtree.DimensionalComparator;
import junit.framework.TestCase;

public class DimensionalComparatorTest extends TestCase {

	@Test
	public void testDimensionalComparator() {
		try {
			new DimensionalComparator(0);
			fail ("Mustn't allow less than 1-dimensions for comparator");
		} catch (Exception e) {
			
		}
	}

}
