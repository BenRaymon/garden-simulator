import static org.junit.Assert.*;

import org.junit.Test;

public class PlotTest {
	
	Options op = new Options(2.0, 1.0, 3.0);
	Plot p = new Plot(op);
	
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
		p.addRecommendedPlant("plant", new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't'));
		assertEquals(p.getRecommendedPlants().get("plant").getCommonName(), "name");
		assertEquals(p.getRecommendedPlants().get("plant").getScientificName(), "sciName");
	}
	
	@Test
	public void testRemoveRecommendedPlant() {
		if (p.getRecommendedPlants().size() > 0) {
			int oldSize = p.getRecommendedPlants().size();
			// the test
			p.removeRecommendedPlant("plant");
			assertEquals(p.getRecommendedPlants().size(), oldSize - 1);
		} else {
			p.addRecommendedPlant("plant", new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't'));
			assertEquals(p.getRecommendedPlants().size(), 1);
			p.removeRecommendedPlant("plant");
			assertEquals(p.getRecommendedPlants().size(), 0);
		}
	}
	
	
	@Test
	public void testPlantToPlot() {
		//fail("Not yet implemented");
		Plant testPlant = new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't');
		p.addPlantToPlot("plant", testPlant);
		assertEquals(p.getPlantsInPlot().containsKey("plant"),true);
		int oldSize = p.getPlantsInPlot().size();
		p.removePlantFromPlot("plant");
		assertEquals(p.getPlantsInPlot().size(), oldSize - 1);
	}
	
	@Test
	public void testGetOptions() {
		assertEquals(1, p.getOptions().getSoilTypes()[1]);
		assertEquals(1, p.getOptions().getSunLevels()[0]);
		assertEquals(1, p.getOptions().getMoistures()[2]);

		assertEquals(0, p.getOptions().getSoilTypes()[0]);
		assertEquals(0, p.getOptions().getSunLevels()[1]);
		assertEquals(0, p.getOptions().getMoistures()[0]);

		assertEquals(0, p.getOptions().getSoilTypes()[2]);
		assertEquals(0, p.getOptions().getSunLevels()[2]);
		assertEquals(0, p.getOptions().getMoistures()[1]);
	}
	
	@Test
	public void testSetOptions() {
		p.setOptions(new Options(1.0, 2.0, 1.0));
		assertEquals(1, p.getOptions().getSoilTypes()[0]);
		assertEquals(1, p.getOptions().getSunLevels()[1]);
		assertEquals(1, p.getOptions().getMoistures()[0]);

		assertEquals(0, p.getOptions().getSoilTypes()[1]);
		assertEquals(0, p.getOptions().getSunLevels()[0]);
		assertEquals(0, p.getOptions().getMoistures()[1]);

		assertEquals(0, p.getOptions().getSoilTypes()[2]);
		assertEquals(0, p.getOptions().getSunLevels()[2]);
		assertEquals(0, p.getOptions().getMoistures()[2]);
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
