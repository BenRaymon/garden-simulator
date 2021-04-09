import static org.junit.Assert.*;

import org.junit.Test;

public class PlotTest {
	
	Plot p = new Plot(new Options("taco", 1, 2));
	
	@Test
	public void testAddGetCoordinate() {
		//fail("Not yet implemented");
		p.addCoordinate(new Point(1.0,1.0));
		assertEquals(p.getCoordinates().get(0).getX(), 1, 0.1);
		assertEquals(p.getCoordinates().get(0).getX(), 1, 0.1);
	}
	
	@Test
	public void testRemoveCoordinate() {
		//fail("Not yet implemented");
		p.removeCoordinate(new Point(1.0, 1.0));
		assertTrue(p.getCoordinates().size() == 0);
	}
	
	@Test
	public void testAddRecommendedPlant() {
		//fail("Not yet implemented");
		p.addRecommendedPlant("plant", new Plant(new Options("taco", 1, 2), 69.0, 5, "blue", "large", 600.0, "scientificTaco", "taco", new Point(1.0, 2.0)));
		assertEquals(p.getRecommendedPlants().get("plant").getCommonName(), "taco");
		assertEquals(p.getRecommendedPlants().get("plant").getScientificName(), "scientificTaco");
	}
	
	@Test
	public void testRemoveRecommendedPlant() {
		if (p.getRecommendedPlants().size() > 0) {
			int oldSize = p.getRecommendedPlants().size();
			// the test
			p.removeRecommendedPlant("plant");
			assertEquals(p.getRecommendedPlants().size(), oldSize - 1);
		} else {
			p.addRecommendedPlant("plant", new Plant(new Options("taco", 1, 2), 69.0, 5, "blue", "large", 600.0, "scientificTaco", "taco", new Point(1.0, 2.0)));
			assertEquals(p.getRecommendedPlants().size(), 1);
			p.removeRecommendedPlant("plant");
			assertEquals(p.getRecommendedPlants().size(), 0);
		}
	}
	
	
	@Test
	public void testPlantToPlot() {
		//fail("Not yet implemented");
		Plant testPlant = new Plant(new Options("taco", 1, 2), 69.0, 5, "blue", "large", 600.0, "scientificTaco", "taco", new Point(1.0, 2.0));
		p.addPlantToPlot("plant", testPlant);
		assertEquals(p.getPlantsInPlot().containsKey("plant"),true);
		int oldSize = p.getPlantsInPlot().size();
		p.removePlantFromPlot("plant");
		assertEquals(p.getPlantsInPlot().size(), oldSize - 1);
	}
	
	@Test
	public void testGetOptions() {
		assertEquals("taco", p.getOptions().getSoilType());
		assertEquals(1, p.getOptions().getSunLevel());
		assertEquals(2, p.getOptions().getMoisture());
	}
	
	@Test
	public void testSetOptions() {
		p.setOptions(new Options("newTaco", 3, 4));
		assertEquals("newTaco", p.getOptions().getSoilType());
		assertEquals(3, p.getOptions().getSunLevel());
		assertEquals(4, p.getOptions().getMoisture());
	}
	
	@Test
	public void testCheckSpread() {
		//fail("Not yet implemented");
		assertTrue(p.checkSpread());
	}
	
	@Test
	public void testMovePlant() {
		//fail("Not yet implemented");
		assertTrue(p.movePlant());
		
	}

}
