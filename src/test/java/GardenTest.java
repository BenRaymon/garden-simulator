import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GardenTest {
	
	Garden testGarden = new Garden();

	@Test
	public void testGetSpent() {
		assertEquals(testGarden.getSpent(), 0, 0);
	}
	
	@Test
	public void testSetSpent() {
		testGarden.setSpent(10.5);
		assertEquals(testGarden.getSpent(), 10.5, 0);
	}
	
	@Test
	public void testGetBudget() {
		assertEquals(testGarden.getBudget(), 0, 0);
	}
	
	@Test
	public void testSetBudget() {
		testGarden.setBudget(55.5);
		assertEquals(testGarden.getBudget(), 55.5, 0);
	}
	
	@Test
	public void testGetLepsSupported() {
		assertEquals(testGarden.getLepsSupported(), 0);
	}
	
	@Test
	public void testGetPlots() {
		assertEquals(testGarden.getPlots().getClass(), ArrayList.class);
	}
	
	@Test
	public void testGetPlantsInGarden() {
		assertEquals(testGarden.getPlantsInGarden().getClass(), ArrayList.class);
	}
	
	@Test
	public void testAddPlot() {
		testGarden.addPlot(new Plot(new Options(1, 1, 1)));
		assertEquals(testGarden.getPlots().size(), 1);
	}
	
	@Test
	public void testLoadPlants() {
		testGarden.loadPlants();
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testLoadPlots() {
		testGarden.loadPlots();
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateSpent() {
		testGarden.setSpent(10);
		testGarden.updateSpent(5);
		assertEquals(testGarden.getSpent(), 15.0, 0);
	}
	
	

}
