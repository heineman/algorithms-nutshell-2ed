package algs.blog.searching.tests;
import algs.blog.searching.gperf.GPerfThree;
import junit.framework.TestCase;


public class TestGPerfThree extends TestCase {
	public void testContains() {
		GPerfThree gp = new GPerfThree();
		
		assertTrue (gp.exists("adda"));
		assertFalse(gp.exists("1234"));
	}
}
