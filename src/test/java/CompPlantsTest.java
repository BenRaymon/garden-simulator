import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class CompPlantsTest {
	
	// these tests currently only test for return types
	// and will need to be expanded upon later when we can
	
	Plant testPlant1 = new Plant();
	Plant testPlant2 = new Plant();
	CompPlants testComp = new CompPlants();
	
	@Test
	public void testgetInfo() {
		assertEquals(testComp.getInfo("testPlant"), String.class);
	}
	
	@Test
	public void testmoreLeps() {
		assertEquals(testComp.moreLeps(testPlant2, testPlant1), String.class);
	}
}
