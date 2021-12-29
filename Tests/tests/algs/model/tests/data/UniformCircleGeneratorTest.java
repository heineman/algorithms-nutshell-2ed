package algs.model.tests.data;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.data.points.UniformCircleGenerator;
import junit.framework.TestCase;

public class UniformCircleGeneratorTest extends TestCase {

	@Test
	public void testCircleGenerator() {
		UniformCircleGenerator cg = new UniformCircleGenerator();
		IPoint[] vals = cg.generate(4000);
		
		// all points within 0.5 of the middle
		algs.model.twod.TwoDPoint middle = new algs.model.twod.TwoDPoint(0.5,0.5);
		for (IPoint ip : vals) {
			algs.model.twod.TwoDPoint p = (algs.model.twod.TwoDPoint) ip; // this is really the object 
			assertTrue (middle.distance(p) <= 0.5);
		}
		
	}
}
