package algs.model.tests.data;

import org.junit.Test;
import algs.model.data.segments.DoubleGenerator;

import junit.framework.TestCase;

/** 
 * Specific Worked example, with reduced move sets to showcase just what I want 
 * to show.
 * 
 * @author George Heineman
 *
 */
public class GeneratorTest extends TestCase {
		
	// generators must ensure toString is meaningful and accurate. This method
	// inherited so we only test once.
	@Test
	public void testGeneratorMethods() {
		DoubleGenerator dg = new DoubleGenerator(100,2);
		assertEquals (DoubleGenerator.class.getName(), dg.toString());
	}
	
}
