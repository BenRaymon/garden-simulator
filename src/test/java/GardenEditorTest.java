import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GardenEditorTest {
	
	//Garden testGarden = new Garden();
	Plant testPlant = new Plant();
	GardenEditor testGardenEditor = new GardenEditor();
	Options option = new Options(1,2,2); 
	Plot plot1 = new Plot(option);
	ArrayList<Plot> plots = new ArrayList<Plot>();
	
	ArrayList<Plant> plantList = new ArrayList<Plant>();
	
	Garden testGarden = new Garden("test name", 0.0, 10.0, plots, 0, plantList, 1.0);
	
	
	// TODO: These are just stub test for stub functions, needs
	// actual implementation
	
	@Test
	public void testselectPlant() {
		Point p = new Point(0,0);
		Plant plant = new Plant("a","b","c","d","e",1,2,1,1,option,6,4,'f');
		Garden.getAllPlants().put("b", plant);
		plot1.addPlant(p, plant);
		testGarden.getPlots().add(plot1);
		GardenEditor.setSelectedPlant(plant);
		assertEquals(GardenEditor.getSelectedPlant(), plant);
		GardenEditor.setSelectedPlant("b", p);
		assertEquals(GardenEditor.getSelectedPlant(),plant);
	}
	
	@Test
	public void testplacePlant() {
		assertEquals(testGardenEditor.placePlant(), 0);
	}
	
	@Test
	public void testisValidPlacement() {
		Point testPoint = new Point();
		testPoint.setX(10);
		testPoint.setY(10);
		plot1.addPlant(new Point(10,10), testPlant);
		double vBuf = 0.0;
		double hBuf = 0.0;
		assertEquals(testGardenEditor.inPlot(testPoint, testGarden.getPlots(), vBuf, hBuf), 0);
	}
	
	@Test
	public void testinPlot() {
		Point p = new Point(0,0);
		assertEquals();
	}
	
	@Test
	public void testcalculateScale() {
		System.out.println(GardenEditor.calculateScale());
	}
	
	@Test
	public void testtransformPlots() {
		
	}
}
