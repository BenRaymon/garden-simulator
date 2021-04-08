import static org.junit.Assert.*;
import org.junit.Test;

public class ControllerTest{
	
	Controller control = new Controller();
	@Test
	public void testDrawPlot() {
		//fail("Not Yet Implemented");
		assertEquals(control.dragPlant(),true);
	}
	
	@Test
	public void testDragPlot() {
		assertEquals(control.drawPlot(),true);
	}
}