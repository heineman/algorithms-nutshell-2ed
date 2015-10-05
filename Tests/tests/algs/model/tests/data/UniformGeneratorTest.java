package algs.model.tests.data;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.data.points.CircleGenerator;
import algs.model.data.points.UniformGenerator;

public class UniformGeneratorTest {

	@Test
	public void testParameters() {
		UniformGenerator ug = new UniformGenerator();
		assertEquals (0, ug.parameters().length);
		
		// arguments are actually not needed for this generator.
		assertTrue (null != ug.construct(new String[0]));
	}
	
	@Test
	public void testCircleGenerator() {
		CircleGenerator cg = new CircleGenerator();
		String[] params = cg.parameters();
		assertEquals (1, params.length);
		assertEquals ("radius", params[0]);
		
		CircleGenerator newCG = (CircleGenerator) cg.construct(new String[]{"2"});
		assertEquals (2, newCG.radius);
	}
}
