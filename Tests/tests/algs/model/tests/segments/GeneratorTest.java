package algs.model.tests.segments;

import org.junit.Test;

import algs.model.ILineSegment;
import algs.model.data.Generator;
import algs.model.data.segments.LoadFromFileGenerator;
import algs.model.twod.TwoDLineSegment;

import junit.framework.TestCase;

public class GeneratorTest extends TestCase {

	private void validate(Generator<ILineSegment> gen) {
		// try to ask for 10 (even though we know there are only 6)
		ILineSegment[] ils = gen.generate(10);
		
		assertEquals (6, ils.length);
		
		assertEquals (new TwoDLineSegment(0,13,0,3), ils[0]);
		assertEquals (new TwoDLineSegment(0,13,12,1), ils[1]);
		assertEquals (new TwoDLineSegment(8,7,0,3), ils[2]);
		assertEquals (new TwoDLineSegment(8,7,13,2), ils[3]);
		assertEquals (new TwoDLineSegment(12,9,9,0), ils[4]);
		assertEquals (new TwoDLineSegment(0,3,12,3), ils[5]);

	}
	
	@Test
	public void testFileGenerator() {
		String s = "resources" + java.io.File.separatorChar + 
		           "algs" + java.io.File.separatorChar +
		           "model" + java.io.File.separatorChar +
		           "data" + java.io.File.separatorChar +
		           "segments" +  java.io.File.separatorChar +
		           "Chapter9.txt";
		
		LoadFromFileGenerator gen = new LoadFromFileGenerator(s);
		
		assertTrue (gen.parameters() != null);
		validate(gen);
		
		// try with construct
		gen.construct(new String[]{ s});
		validate(gen);
		
		// try with impossible file
		gen = new LoadFromFileGenerator(s + " __ Doesn't Exist __");
		ILineSegment results[] = gen.generate(20);
		assertEquals (0, results.length);
	}
}
