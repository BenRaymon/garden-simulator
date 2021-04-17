import java.util.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GardenTest {
	
	Garden testGarden = new Garden();

	@Test
	public void testgetNumPlots() {
		assertTrue(testGarden.getNumPlots() > -1 );
	}
	
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
	
	@Test
	public void testGetPlots() {
		assertEquals(testGarden.getPlots().getClass(), ArrayList.class);
	}
	
	@Test
	public void testGetLepsSupported() {
		assertEquals(testGarden.getLepsSupported(), 0);
	}
	
	@Test
	public void testaddCoordsToPlot() {
		Point testPoint1 = new Point();
		Point testPoint2 = new Point(5.0, 5.0);
		ArrayList<Point> testPoints = new ArrayList<Point>();
		testPoints.add(testPoint1);
		testPoints.add(testPoint2);
		assertTrue(testGarden.addCoordsToPlot(testPoints));
	}
	
	@Test
	public void testnewPlot() {
		// TODO: implement test
		fail("do this later cause it's a void method and I don't have time");
	}
	
	@Test
	public void testGetPlantsInGarden() {
		assertEquals(testGarden.getPlantsInGarden().getClass(), ArrayList.class);
	}

	@Test
	public void testgetAllPlants() {
		assertEquals(testGarden.getAllPlants().getClass(), HashMap.class);
	}
	
	@Test
	public void testgetPlant() {
		// TODO: properly test for a plant that actually exists
		assertEquals(testGarden.getPlant("test").getClass(), Plant.class);
	}
}
