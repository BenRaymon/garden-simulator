import java.util.*;
import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

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
		assertEquals(CompPlants.getInfo("testPlant").getClass(), String.class);
	}
	
	@Test
	public void testGetLepInfo() {
		Plant testLepInfo = new Plant();
		testLepInfo.setLepsSupported(100);
		Garden.getAllPlants().put("testLepInfo", testLepInfo);
		assertEquals(CompPlants.getLepInfo("testLepInfo"), 100);
	}
	
	
	@Test
	public void testLowerRadius() {
		Garden.getAllPlants().put("Viola Cucullata", testPlant1);
		assertEquals(CompPlants.getLowerRadius("Viola Cucullata"), 0.0,0);
	}
	
	@Test
	public void testUpperRadius() {
		Garden.getAllPlants().put("Viola Cucullata", testPlant1);
		assertEquals(CompPlants.getUpperRadius("Viola Cucullata"), 0.0,0);
	}
	
	@Test 
	public void testgetLowerSize() {
		Garden.getAllPlants().put("Viola Cucullata", testPlant1);
		assertEquals(CompPlants.getLowerSize("Viola Cucullata"), 0.0,0);
	}
	
	@Test 
	public void testgetUpperSize() {
		Garden.getAllPlants().put("Viola Cucullata", testPlant1);
		assertEquals(CompPlants.getUpperSize("Viola Cucullata"), 0.0,0); //6.0
	}
	
	@Test
	public void testMoreLeps() {
		testPlant1.setScientificName("plantA");
		testPlant2.setScientificName("plantB");
		testPlant1.setLepsSupported(10);
		testPlant2.setLepsSupported(5);
		assertEquals(CompPlants.moreLeps(testPlant1,testPlant2),"plantA");
		
		testPlant1.setLepsSupported(3);
		assertEquals(CompPlants.moreLeps(testPlant1, testPlant2), "plantB");
		
		testPlant1.setLepsSupported(5);
		assertEquals(CompPlants.moreLeps(testPlant1, testPlant2), "Equal");
	}
}
