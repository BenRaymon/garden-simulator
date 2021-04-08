import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest{
	
	Controller control = new Controller();
	@Test
	public void testDrawPlot() {
		//fail("Not Yet Implemented");
		assertTrue(control.dragPlant());
	}
	
	@Test
	public void testDragPlot() {
		assertTrue(control.drawPlot());
	}
}