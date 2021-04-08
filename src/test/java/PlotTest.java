import static org.junit.Assert.*;

import org.junit.Test;

public class PlotTest {
	
	Plot p = new Plot();
	Point tempPoint = new Point();
	Point tempPoint2 = new Point();
	Options op = new Options("st",1,1);
	Plant testPlant = new Plant(op,0.0,1,"red","large",1.0,"name","name",tempPoint);

	
	@Test
	public void testAddGetCoordinate() {
		//fail("Not yet implemented");
		p.addCoordinate(tempPoint);
		p.addCoordinate(tempPoint2);
		assertEquals(p.getCoordinates()[0],tempPoint);
	}
	
	@Test
	public void testRemoveCoordinate() {
		//fail("Not yet implemented");
		
		p.removeCoordinate(tempPoint);
		assertEquals(testPlot.getCoordinates()[0],tempPoint2);
	}
	
	@Test
	public void testRecommendedPlant() {
		//fail("Not yet implemented");
		p.addPlantToPlot("plant", testPlant);
		assertEquals(p.getRecommendedPlants().containsKey("plant"),true);
		p.removePlantFromPlot("plant");
	}
	
	
	
	@Test
	public void testPlantToPlot() {
		//fail("Not yet implemented");
		p.addPlantToPlot("plant", testPlant);
		p.addPlantToPlot("plant2", testPlant);
		assertEquals(p.getPlantsInPlot().containsKey("plant"),true);
		p.removePlantFromPlot("plant");
	}
	
	
	@Test
	public void testOptions() {
		//fail("Not yet implemented");
		p.setOptions(op);
		assertEquals(p.getOptions(),op);

	}
	@Test
	public void testCheckSpread() {
		//fail("Not yet implemented");
		assertEquals(p.checkSpread(),true);
	}
	
	@Test
	public void testMovePlant() {
		//fail("Not yet implemented");
		assertEquals(p.movePlant(),true);
		
	}

}
