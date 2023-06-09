import java.util.*;
import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class GardenTest {
	
	Garden testGarden = new Garden();
	Garden testGarden2 = new Garden("test name", 0.0, 10.0, new ArrayList<Plot>(), 0, new ArrayList<Plant>(), 1.0);

	@Test
	public void testGetPlant() {
		testGarden.newPlot(new Options(1,1,1));
		testGarden.addPlantToPlot(0, new Point(0,0), new Plant());
		assertEquals(testGarden.getPlant(0,new Point(0,0)).getClass(), Plant.class);
	}
	
	@Test
	public void testGetPlantsInGarden() {
		testGarden.newPlot(new Options(1,1,1));
		testGarden.addPlantToPlot(0, new Point(0,0), new Plant());
		assertEquals(testGarden.getPlantsInGarden().size(), 1);
	}
	
	@Test
	public void testGetNumPlots() {
		testGarden.newPlot(new Options(2,3,1));
		assertTrue(testGarden.getNumPlots() > -1 );
	}
	
	@Test
	public void testGetPlots() {
		testGarden.newPlot(new Options(1,2,3));
		assertEquals(testGarden.getPlots().get(0).getClass(), Plot.class);
		assertTrue(testGarden.getPlots().size() >= 1);
	}
	
	@Test
	public void testNewPlot() {
		testGarden.newPlot(new Options(1, 1, 1));
		assertEquals(testGarden.getNumPlots(), 1);
	}
	
	@Test
	public void testGetPlotOptions() {
		Options o = new Options(1,2,3);
		testGarden.newPlot(o);
		assertEquals(testGarden.getPlotOptions(0), o);
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
	public void testUpdateSpent() {
		testGarden.setSpent(10);
		testGarden.updateSpent(5);
		assertEquals(testGarden.getSpent(), 15.0, 0);
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
	public void testAddCoordsToPlot() {
		Point testPoint1 = new Point();
		Point testPoint2 = new Point(5.0, 5.0);
		ArrayList<Point> testPoints = new ArrayList<Point>();
		testPoints.add(testPoint1);
		testPoints.add(testPoint2);
		testGarden.newPlot(new Options(2,3,1));
		testGarden.addCoordsToPlot(testPoints);
		assertTrue(testGarden.getPlots().get(testGarden.getPlots().size() - 1) != null);
	}
	
	@Test
	public void testAddPlantToPlot() {
		testGarden.newPlot(new Options(1,2,3));
		testGarden.addPlantToPlot(testGarden.getPlots().size() - 1, new Point(0,0), new Plant());
		testGarden.addPlantToPlot(testGarden.getPlots().size() - 1, new Point(1,0), new Plant());
		assertEquals(testGarden.getPlots().get(testGarden.getPlots().size() - 1).getPlantsInPlot().size(), 2, 0);
	}
	
	@Test
	public void testRemovePlantFromPlot() {
		testGarden.newPlot(new Options(1,2,3));
		testGarden.addPlantToPlot(testGarden.getPlots().size() - 1, new Point(0,0), new Plant());
		testGarden.addPlantToPlot(testGarden.getPlots().size() - 1, new Point(1,0), new Plant());
		assertEquals(testGarden.getPlots().get(testGarden.getPlots().size() - 1).getPlantsInPlot().size(), 2, 0);
		testGarden.removePlantFromPlot(testGarden.getPlots().size() - 1, new Point(0,0));
		testGarden.removePlantFromPlot(testGarden.getPlots().size() - 1, new Point(1,0));
		assertEquals(testGarden.getPlots().get(testGarden.getPlots().size() - 1).getPlantsInPlot().size(), 0, 0);
	}
	
	@Test
	public void testIsPlantInPlot() {
		testGarden.newPlot(new Options(1,2,3));
		Plant p = new Plant();
		p.setPosition(new Point(0,1));
		assertFalse(testGarden.isPlantInPlot(testGarden.getPlots().size() - 1, p));
		testGarden.addPlantToPlot(testGarden.getPlots().size() - 1, new Point(0,0), p);
		assertTrue(testGarden.isPlantInPlot(testGarden.getPlots().size() - 1, p));
	}
	
	@Test
	public void testGenerateShoppingListData() {
		testGarden2.newPlot(new Options(1,1,1));
		Plant testP = new Plant();
		Plant testP2 = new Plant();
		testP.setCost(10);
		testP.setCommonName("Test name");
		testP.setScientificName("Sci name");

		testP2.setCost(10);
		testP2.setCommonName("Test name");
		testP2.setScientificName("Sci name");
		
		testGarden2.addPlantToPlot(0, new Point(0,0), testP);
		testGarden2.addPlantToPlot(0, new Point(10,10), testP2);
		HashMap<String, PlantShoppingListData>  data = testGarden2.generateShoppingListData();
		PlantShoppingListData testResult = data.get(testP.getCommonName());
		assertEquals(testResult.getCount(), 2);
		assertEquals(testResult.getCommonName(), testP.getCommonName());
		assertEquals(testResult.getScientificName(), testP.getScientificName());
		assertEquals(testResult.getCost(), 20,0);
		
		testResult.setCommonName("New Name");
		testResult.setCost(5);
		testResult.setCount(10);
		testResult.setScientificName("New Sci");
		assertEquals(testResult.getCount(), 10);
		assertEquals(testResult.getCommonName(), "New Name");
		assertEquals(testResult.getScientificName(), "New Sci");
		assertEquals(testResult.getCost(), 5.0,0);
	}
	
	@Test
	public void testSetScale() {
		testGarden.setScale(10.0);
		assertEquals(testGarden.getScale(), 10.0, 0);
	}
	
	@Test
	public void testGetScale() {
		testGarden.setScale(50.0);
		assertEquals(testGarden.getScale(), 50.0, 0);
	}
	
	@Test
	public void testGetName() {
		testGarden.setName("Test Name");
		assertEquals(testGarden.getName(), "Test Name");
	}
	@Test
	public void testSetName() {
		testGarden.setName("Test SET Name");
		assertEquals(testGarden.getName(), "Test SET Name");
	}

	@Test
	public void testGetAllPlants() {
		assertEquals(Garden.getAllPlants().getClass(), ConcurrentHashMap.class);
	}
	
	@Test
	public void testStaticGetPlant() {
		Garden.getAllPlants().put("TEST", new Plant());
		assertEquals(Garden.getPlant("TEST").getClass(), Plant.class);
	}
	
	@Test
	public void testSetRecommendedPlants() {
		HashMap<String, Plant> newMap = new HashMap<String, Plant>();
		newMap.put("test", new Plant());
		
		testGarden.setRecommendedPlants(newMap);
		assertEquals(testGarden.getRecommendedPlants(), newMap);
	}
}
