package algs.model.tests.network;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.model.network.EdgeInfo;
import algs.model.network.VertexStructure;

public class VertexStructureTest {

	@Test
	public void testConstruct() {
		VertexStructure vs = new VertexStructure();
		assertEquals ("forward:List[0], backward:List[0]", vs.toString());
		
		EdgeInfo ei = new EdgeInfo (0, 1, 10);
		vs.addForward(ei);
		
		EdgeInfo ei2 = new EdgeInfo (0, 1, 7);
		vs.addBackward(ei2);
		
		assertEquals ("forward:List[1]: " + ei + ", backward:List[1]: " + ei2, vs.toString());
		
		
	}

}
