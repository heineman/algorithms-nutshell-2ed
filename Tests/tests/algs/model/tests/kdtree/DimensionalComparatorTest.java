package algs.model.tests.kdtree;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.kdtree.DimensionalComparator;

public class DimensionalComparatorTest {

	@Test
	public void testDimensionalComparator() {
		try {
			new DimensionalComparator(0);
			fail ("Mustn't allow less than 1-dimensions for comparator");
		} catch (Exception e) {
			
		}
	}

}
