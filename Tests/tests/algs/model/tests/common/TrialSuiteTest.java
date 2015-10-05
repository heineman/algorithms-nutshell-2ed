package algs.model.tests.common;

import static org.junit.Assert.*;

import java.util.Iterator;
import org.junit.Test;

public class TrialSuiteTest {

	@Test
	public void testGetCount() {
		TrialSuite ts = new TrialSuite();

		// nothing yet
		assertEquals (0, ts.getCount(5));

		// first one in
		ts.addTrial(5, 0, 10);
		assertEquals (1, ts.getCount(5));

		// second one in
		ts.addTrial(5, 0, 20);
		assertEquals (2, ts.getCount(5));

		// third one in, finally kicks in the idea that min/max are to be thrown out. So we reduce by two
		ts.addTrial(5, 0, 20);
		assertEquals (1, ts.getCount(5));

	}

	@Test
	public void testHeader() {
		assertEquals ("n,average,min,max,stdev,#", TrialSuite.buildHeader());
	}

	@Test
	public void testRows() {
		TrialSuite ts = new TrialSuite();
		
		
		assertEquals ("", ts.getRow(5));

		// nothing here yet
		assertEquals ("*", ts.getAverage(5));

		// add one
		ts.addTrial(5, 0, 20);
		assertEquals ("5,20,20,20,0.0,1", ts.getRow(5));

		// add second
		ts.addTrial(5, 0, 10);
		assertEquals ("5,15.0,10,20,5.0,2", ts.getRow(5));

		// add third: note stdev=0 because we've thrown out 10 & 30
		ts.addTrial(5, 0, 30);
		assertEquals ("5,20.0,10,30,0.0,1", ts.getRow(5));

		// add fourth
		ts.addTrial(5, 0, 40);
		assertEquals ("10", ts.getMinimum(5));
		assertEquals ("40", ts.getMaximum(5));
		assertEquals ("25.0", ts.getAverage(5));
		assertTrue (Math.abs(7.07 - Double.valueOf(ts.getDeviation(5))) < 0.01);
	}

	@Test
	public void testTable() {
		TrialSuite ts = new TrialSuite();
		assertEquals (TrialSuite.buildHeader()+"\n", ts.computeTable());

		// add one
		ts.addTrial(5, 0, 20);
		assertEquals (TrialSuite.buildHeader() + "\n" + "5,20,20,20,0.0,1\n", ts.computeTable());
	}

	@Test
	public void testHistogram() {
		TrialSuite ts = new TrialSuite();
		assertEquals (TrialSuite.buildHeader()+"\n", ts.histogram());

		// add one
		ts.addTrial(5, 0, 20);
		assertEquals (TrialSuite.buildHeader() + "\nTable for:5\n" + TrialSuite.histogramSeparator + "\n" +  
				"20\t:1" + "\n" + TrialSuite.histogramSeparator + "\n\n", ts.histogram());
	}
	
	@Test
	public void testKeys() {
		TrialSuite ts = new TrialSuite();

		Iterator<Long> it = ts.keys();
		assertFalse (it.hasNext());

		// add one
		ts.addTrial(5, 0, 10);
		it = ts.keys();
		assertTrue (it.hasNext());
		assertTrue (5 == it.next());

		// not essential to be adding a second one to validate, but do anyway
		ts.addTrial(15, 0, 10);
		it = ts.keys();
		long val1, val2;
		assertTrue (it.hasNext());
		val1 = it.next();
		assertTrue (it.hasNext());
		val2 = it.next();
		assertTrue ((val1 == 5 && val2 == 15) || (val1 == 15 && val2 == 5));
	}


	@Test
	public void testHistogramSimple() {
		TrialSuite ts = new TrialSuite();
		ts.addTrial(5, 0, 10);

		String s = ts.buildTable(5);
		assertEquals ("10\t:1\n", s);		
	}

	@Test
	public void testHistogramMultiple() {
		TrialSuite ts = new TrialSuite();
		ts.addTrial(5, 0, 20);
		ts.addTrial(5, 0, 10);
		ts.addTrial(5, 0, 20);
		ts.addTrial(5, 0, 30);

		String s = ts.buildTable(5);
		assertEquals ("10\t:1\n20\t:2\n30\t:1\n", s);

	}


	@Test
	public void testAddTrial() {
		TrialSuite ts = new TrialSuite();
		ts.addTrial(5, 0, 10);
		String s = ts.getRow(5);

		// simple case
		assertEquals ("5,10,10,10,0.0,1", s);
	}
	
	@Test
	public void testTrialHelper() {
		TrialSuite ts = new TrialSuite();
		ts.addTrial(5, 0, 10);
		ts.addTrial(6, 0, 20);
		
		
		TrialSuite ts2 = new TrialSuite();
		ts2.addTrial(5, 0, 30);
		ts2.addTrial(6, 0, 10);
		
		
		String result = TrialSuiteHelper.combine(new TrialSuite[] { ts, ts2});
		assertEquals ("5,10,30\n6,20,10\n", result);
	}

	@Test
	public void testEmptyTrialHelper() {
		assertEquals ("", TrialSuiteHelper.combine(null));
		assertEquals ("", TrialSuiteHelper.combine(new TrialSuite[0]));
		TrialSuite ts = new TrialSuite();
		ts.addTrial(5, 0, 10);
		ts.addTrial(6, 0, 20);
		assertEquals (ts.computeTable(), TrialSuiteHelper.combine(new TrialSuite[]{ts}));
	}
	
}
