import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class PlotTest {
	
	Options op = new Options(2.0, 1.0, 3.0);
	Plot p = new Plot(op);
	
	@Test
	public void testAddGetCoordinate() {
		//fail("Not yet implemented");
		System.out.println("TESTING");
		p.setCoordinates(new ArrayList<Point>());
		p.addCoordinate(new Point(1.0,1.0));
		System.out.println(p.getCoordinates().get(0).getX());
		assertEquals(p.getCoordinates().get(0).getX(), 1.0, 1.0);
		assertEquals(p.getCoordinates().get(0).getX(), 1.0, 1.0);
	}
	
	@Test
	public void testRemoveCoordinate() {
		//fail("Not yet implemented");
		p.setCoordinates(new ArrayList<Point>());
		p.addCoordinate(new Point(1.0,1.0));
		p.removeCoordinate(new Point(1.0, 1.0));
		assertTrue(p.getCoordinates().size() == 0);
	}
	
	@Test
	public void testAddRecommendedPlant() {
		//fail("Not yet implemented");
		p.setRecommended(new HashMap<String, Plant>());
		p.addRecommendedPlant("plant", new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't'));
		assertEquals(p.getRecommendedPlants().get("plant").getCommonName(), "name");
		assertEquals(p.getRecommendedPlants().get("plant").getScientificName(), "sciName");
	}
	
	@Test
	public void testRemoveRecommendedPlant() {
		p.setRecommended(new HashMap<String, Plant>());
		p.addRecommendedPlant("plant", new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't'));
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
		p.setPlantsinPlot(new HashMap<Point,Plant>());
		//fail("Not yet implemented");
		Plant testPlant = new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't');
		Point point = new Point(0,0);
		p.addPlant(point, testPlant);
		assertEquals(p.getPlantsInPlot().size(),1);
		p.removePlant(point);
		assertEquals(p.getPlantsInPlot().size(), 0);
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
	public void testgetPlant() {
		p.setPlantsinPlot(new HashMap<Point,Plant>());
		//fail("Not yet implemented");
		Plant testPlant = new Plant("name", "sciName", "genera", "fam", "color", 0, 0,0, 0, op, 0, 0, 't');
		Point point = new Point(0,0);
		p.addPlant(point, testPlant);
		assertEquals(p.getPlant(point),testPlant);
	}
	
	@Test
	public void testfilterCoords() {
		ArrayList<Point> coords = new ArrayList<Point>();
		coords.add(new Point());
		coords.add(new Point());
		coords.add(new Point());
		coords.add(new Point());
		coords.add(new Point());
		p.setCoordinates(coords);
		assertEquals(p.filterCoords(2).size(),3);
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
	public void testPlotBoundaries() {
		// Necessary to test recommended plants
		BackgroundLoader bl = null;
		bl = new BackgroundLoader("bkgloader", View.getImages(),View.getLepImages(), Garden.getAllPlants(), Garden.getLepsByPlant(), Garden.getAllLeps());
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Options o = new Options(1.0, 1.0, 1.0);
		Plot pl = new Plot(o);
		ArrayList<Point> coords = new ArrayList<Point>();
		coords.add(new Point(0, 0));
		coords.add(new Point(0, 100));
		coords.add(new Point(100, 0));
		coords.add(new Point(100, 100));
		pl.setCoordinates(coords);
		pl.setOriginalCoordinates(coords);
		assertEquals(4, pl.getOriginalCoordinates().size());
		
		pl.calculatePlotBoundaries();
		assertEquals(0, pl.getTop(), 0.01);
		assertEquals(0, pl.getLeft(), 0.01);
		assertEquals(100, pl.getBottom(), 0.01);
		assertEquals(100, pl.getRight(), 0.01);
		assertEquals(50, pl.getCx(), 0.01);
		assertEquals(50, pl.getCy(), 0.01);
		
		pl.setTop(0.0);
		pl.setBottom(0.0);
		pl.setLeft(0.0);
		pl.setRight(0.0);
		pl.setCx(0.0);
		pl.setCy(0.0);
		assertEquals(0, pl.getTop(), 0.01);
		assertEquals(0, pl.getLeft(), 0.01);
		assertEquals(0, pl.getBottom(), 0.01);
		assertEquals(0, pl.getRight(), 0.01);
		assertEquals(0, pl.getCx(), 0.01);
		assertEquals(0, pl.getCy(), 0.01);
		
		
		pl.createRecommendedPlants();
		assertEquals(26, pl.getRecommendedPlants().size());
	}

}
