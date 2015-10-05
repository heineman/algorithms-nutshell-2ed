package algs.model.tests.data;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.IMultiPoint;
import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.nd.ConvertToND;
import algs.model.data.nd.UniformGenerator;
import algs.model.data.points.HorizontalLineGenerator;
import algs.model.data.points.VerticalLineGenerator;
import algs.model.data.points.LoadFromFileGenerator;
import algs.model.data.segments.DoubleGenerator;
import algs.model.data.segments.IntegerGenerator;
import algs.model.twod.TwoDPoint;

public class GeneratorsTest extends TestCase {

	@Test
	public void testIntegerGenerator() {
		IntegerGenerator ig = new IntegerGenerator(1, 1);
		assertTrue (ig.parameters() != null);
		
		// forces reaction to equal set.
		ILineSegment[] set = ig.generate(10);
		assertEquals (10, set.length);
		for (ILineSegment ils : set) {
			System.out.println(ils);
		}
	}
	
	@Test
	public void testDoubleGenerator() {
		DoubleGenerator dg = new DoubleGenerator(1,1);
		
		// because len == max, it is very likely that a majority of these
		// points will have (0,0) as one of their line segment endpoints, due to 
		// the algorithm used to generate
		ILineSegment[] set = dg.generate(10);
		assertEquals (10, set.length);
		for (ILineSegment ils : set) {
			System.out.println(ils);
		}
	}
	
	
	@Test
	public void testConvert2ND() {
		HorizontalLineGenerator hlg = new HorizontalLineGenerator(99);
		assertEquals (1, hlg.parameters().length);
		
		// make sure pass through parameters...
		ConvertToND cnd = new ConvertToND(hlg);
		assertEquals (1, cnd.parameters().length);
		
		IMultiPoint points[] = cnd.generate(10);
		
		assertEquals (10, points.length);
		double y = points[0].getCoordinate(2);  // y-value of line.
		for (IMultiPoint p : points) {
			assertEquals (y, p.getCoordinate(2));
		}
		
		// try to construct
		Generator<IPoint> t = hlg.construct(new String[]{"99"});
		assertTrue (t != null);
		
		// try to construct ConvertToND
		Generator<IMultiPoint> t2 = cnd.construct(new String[]{});
		assertTrue (t2 != null);
		
		// even try the error condition to see if it is trapped.
		Generator<IMultiPoint> t3 = cnd.construct(new String[]{"99"});
		assertTrue (t3 != null);
	}
	
	
	@Test
	public void testHorizontal() {
		HorizontalLineGenerator hlg = new HorizontalLineGenerator(99);
		assertTrue (hlg.toString() != null);
		hlg = (HorizontalLineGenerator) hlg.construct(new String[]{"99"});

		TwoDPoint points[] = hlg.generate(2);
		assertEquals (2, points.length);
		assertEquals (points[0].getY(), points[1].getY());
	}
	
	@Test
	public void testVertical() {
		VerticalLineGenerator vlg = new VerticalLineGenerator(99);
		assertTrue (vlg.toString() != null);
		vlg = (VerticalLineGenerator) vlg.construct(new String[]{"99"});

		assertEquals ("x-value", vlg.parameters()[0]);
		
		TwoDPoint points[] = vlg.generate(2);
		assertEquals (2, points.length);
		assertEquals (points[0].getX(), points[1].getX());
	}
	
	@Test
	public void testUniform() {
		UniformGenerator ug = new UniformGenerator(2, 100);
		assertTrue (ug.toString() != null);
		ug = (UniformGenerator) ug.construct(new String[]{"2","100"});

		IMultiPoint points[] = ug.generate(10);
		assertEquals (10, points.length);
		assertTrue (ug.parameters() != null);
		
		ug = (UniformGenerator) ug.construct(new String[]{"2","100"});
		assertTrue (ug != null);
		
		for (IMultiPoint mp : points) {
			assertEquals (2, mp.dimensionality());
			assertTrue (mp.getCoordinate(1) >= 0);
			assertTrue (mp.getCoordinate(1) <= 100);
			
			assertTrue (mp.getCoordinate(2) >= 0);
			assertTrue (mp.getCoordinate(2) <= 100);
		}
	}
	
	private void validate(Generator<IPoint> gen) {
		// try to ask for 10 (even though we know there are only 4)
		IPoint[] points = gen.generate(10);
		
		assertEquals (4, points.length);
		
		assertEquals (new TwoDPoint(0,13), points[0]);
		assertEquals (new TwoDPoint(8,7), points[1]);
		assertEquals (new TwoDPoint(12,9), points[2]);
		assertEquals (new TwoDPoint(0,3), points[3]);
	}
	
	@Test
	public void testFileGenerator() {
		String s = "resources" + java.io.File.separatorChar + 
		           "algs" + java.io.File.separatorChar +
		           "model" + java.io.File.separatorChar +
		           "data" + java.io.File.separatorChar +
		           "points" +  java.io.File.separatorChar +
		           "SampleFile.txt";
		
		LoadFromFileGenerator gen = new LoadFromFileGenerator(s);
		
		assertTrue (gen.parameters() != null);
		validate(gen);
		
		// try with construct
		gen.construct(new String[]{ s});
		validate(gen);
		
		// try with impossible file
		gen = new LoadFromFileGenerator(s + " __ Doesn't Exist __");
		try {
			gen.generate(2);
			fail ("Shouldn't allow bad file to be accepted.");
		} catch (RuntimeException re) {
			// ok
		}
	}
	
}
