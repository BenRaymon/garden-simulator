import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class CompPlantsTest {
	
	// these tests currently only test for return types
	// and will need to be expanded upon later when we can
	
	Plant testPlant1 = new Plant();
	Plant testPlant2 = new Plant();
	
	@Test
	public void testgetInfo() {
		//add plant tot list first
		Garden.getAllPlants().put("testPlant", testPlant1);
		Garden.getAllPlants().put("testPlant2", testPlant2);
		assertEquals(CompPlants.getInfo("testPlant"), String.class);
	}
	
	@Test
	public void testmoreLeps() {
		assertEquals(CompPlants.moreLeps(testPlant2, testPlant1), String.class);
	}
}
