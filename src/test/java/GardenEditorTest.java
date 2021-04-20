import static org.junit.Assert.*;
import org.junit.Test;

public class GardenEditorTest {
	
	Garden testGarden = new Garden();
	GardenEditor testGardenEditor = new GardenEditor(testGarden);
	
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
		assertEquals(testGardenEditor.inPlot(testPoint), 0);
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
