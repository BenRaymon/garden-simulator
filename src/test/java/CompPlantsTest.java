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
	
	@Test
	public void testLowerRadius() {
		assertEquals(CompPlants.getLowerRadius("Salix Sericea"), double.class);
	}
	
	@Test
	public void testUpperRadius() {
		assertEquals(CompPlants.getUpperRadius("Salix Sericea"), double.class);
	}
	
	@Test 
	public void testgetLowerSize() {
		assertEquals(CompPlants.getLowerSize("Salix Sericea"), double.class);
	}
	
	@Test 
	public void testgetUpperSize() {
		assertEquals(CompPlants.getUpperSize("Salix Sericea"), double.class);
	}
	
	@Test
	public void testMoreLeps() {
		testPlant1.setScientificName("plantA");
		testPlant2.setScientitifcName("plantB");
		testPlant1.setLeps(10);
		testPlant2.setLeps(5);
		assertEquals(CompPlants.moreLeps(testPlant1,testPlant2),"plantA");
	}
}
