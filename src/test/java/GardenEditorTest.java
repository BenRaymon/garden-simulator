import static org.junit.Assert.*;
import org.junit.Test;

public class GardenEditorTest {
	
	Garden testGarden = new Garden();
	Plant testPlant = new Plant();
	GardenEditor testGardenEditor = new GardenEditor();
	
	// TODO: These are just stub test for stub functions, needs
	// actual implementation
	
	@Test
	public void testselectPlant() {
		assertEquals(testGardenEditor.selectPlant(), null);
	}
	
	@Test
	public void testplacePlant() {
		assertEquals(testGardenEditor.placePlant(), 0);
	}
	
	@Test
	public void testisValidPlacement() {
		Point testPoint = new Point();
		double vBuf = 0.0;
		double hBuf = 0.0;
		assertEquals(testGardenEditor.inPlot(testPoint, testGarden.getPlots(), vBuf, hBuf), 0);
	}
	
	@Test
	public void testupdateStats() {
		assertEquals(testGardenEditor.updateStats(), 0);
	}
	
	@Test
	public void testsearch() {
		assertEquals(testGardenEditor.search(), 0);
	}
	
	@Test
	public void testsetPos() {
		Point testPoint = new Point();
		assertEquals(testGardenEditor.setPos(testPoint), 0);
	}
}
